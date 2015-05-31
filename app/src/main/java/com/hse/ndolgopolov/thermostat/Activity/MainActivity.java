package com.hse.ndolgopolov.thermostat.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.hse.ndolgopolov.thermostat.Adapter.DayAdapter;
import com.hse.ndolgopolov.thermostat.Model.Controller;
import com.hse.ndolgopolov.thermostat.Model.DaySchedule;
import com.hse.ndolgopolov.thermostat.Model.Globals;
import com.hse.ndolgopolov.thermostat.R;
import me.drakeet.library.UIButton;

import java.lang.reflect.Field;
import java.util.Calendar;

class RepeatListener implements View.OnTouchListener {

    private Handler handler = new Handler();

    private int initialInterval;
    private final int normalInterval;
    private final View.OnClickListener clickListener;

    private Runnable handlerRunnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, normalInterval);
            clickListener.onClick(downView);
        }
    };

    private View downView;

    /**
     * @param initialInterval The interval after first click event
     * @param normalInterval The interval after second and subsequent click
     *       events
     * @param clickListener The OnClickListener, that will be called
     *       periodically
     */
    public RepeatListener(int initialInterval, int normalInterval,
                          View.OnClickListener clickListener) {
        if (clickListener == null)
            throw new IllegalArgumentException("null runnable");
        if (initialInterval < 0 || normalInterval < 0)
            throw new IllegalArgumentException("negative interval");

        this.initialInterval = initialInterval;
        this.normalInterval = normalInterval;
        this.clickListener = clickListener;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.removeCallbacks(handlerRunnable);
                handler.postDelayed(handlerRunnable, initialInterval);
                downView = view;
                clickListener.onClick(view);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                handler.removeCallbacks(handlerRunnable);
                downView = null;
                return true;
        }

        return false;
    }

}

public class MainActivity extends ActionBarActivity {
    MainActivity thisActivity = this;
    TextView currTemp;
    TextView currTempLabel;
    TextView targetTemp;
    TextView schedule;
    TextView fakeDate;
    Controller controller = Globals.controller;
    ListView listView;
    UIButton editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        editButton = (UIButton) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, DayActivity.class);
                intent.putExtra("day", controller.fakeDate.get(Calendar.DAY_OF_WEEK)%7);
                startActivity(intent);
            }
        });

        listView = (ListView) findViewById(R.id.todayScheduleListView);
        // TODO pass today's schedule
        DaySchedule daySchedule = controller.weekSchedule.days[controller.fakeDate.get(Calendar.DAY_OF_WEEK)%7];
        listView.setAdapter(new DayAdapter(this, daySchedule));

        currTemp = (TextView) findViewById(R.id.currentTemperatureTextView);
        currTempLabel = (TextView) findViewById(R.id.currentTemperatureLabelTextView);
        targetTemp = (TextView) findViewById(R.id.targetTemperatureTextView);
        schedule = (TextView) findViewById(R.id.scheduleLabelTextView);
        fakeDate = (TextView)findViewById(R.id.fakeDate);
        TextView toolbarTextView = getActionBarTextView(mToolbar);
        Typeface roboto_light = Typeface.createFromAsset(this.getAssets(), "Roboto-Light.ttf");
        Typeface roboto_bold = Typeface.createFromAsset(this.getAssets(), "Roboto-Bold.ttf");

        currTemp.setTypeface(roboto_light);
        currTempLabel.setTypeface(roboto_light);
        targetTemp.setTypeface(roboto_light);
        schedule.setTypeface(roboto_light);
        toolbarTextView.setTypeface(roboto_bold);
        toolbarTextView.setTextSize(20);

        UIButton plusButton = (UIButton)findViewById(R.id.plusButton);
        plusButton.setOnTouchListener(new RepeatListener(400, 30, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controller.desiredTemperature < 30) {
                    controller.desiredTemperature += 0.1;
                    controller.isOverriden = true;
                    updateFromController();
                }
            }
        }));

        UIButton minusButton = (UIButton)findViewById(R.id.minusButton);
        minusButton.setOnTouchListener(new RepeatListener(400, 30, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controller.desiredTemperature > 5) {
                    controller.desiredTemperature -= 0.1;
                    controller.isOverriden = true;
                    updateFromController();
                }
            }
        }));

        ImageView lockImageView = (ImageView) findViewById(R.id.lockImageView);
        lockImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controller.isPermanentlyOverriden) {
                    v.setBackgroundResource(R.drawable.ic_lock_open);
                    controller.isPermanentlyOverriden = false;
                } else {
                    v.setBackgroundResource(R.drawable.ic_lock_closed);
                    controller.isPermanentlyOverriden = true;
                }

            }
        });
        //TEST!!!
//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(6);
//        controller.weekSchedule.addInterval(new Interval(2, 50, 3, 30),list);
//        updateFromController();
        final int minuteLength = 60000 / controller.timeScale;
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean c = true;
                while (c) {
                    try {
                        Thread.sleep(minuteLength);
                        controller.fakeDate.add(Calendar.MINUTE, 1);

                        controller.setDesiredTemperature();
                        controller.setCurrentTemperature();
                        updateFromController();

                        //Log.i("Update", "Called");

                    } catch (InterruptedException ex) {
                        c = false;//Убери, если будет вылетать
                    }
                }

            }
        }).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Calendar icon clicked
        if (id == R.id.action_calendar) {
            Intent intent = new Intent(this, WeekActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
//    public void clickPlus(View V){
//        controller.desiredTemperature += 0.1;
//        controller.isOverriden = true;
//        updateFromController();
//    }
//    public void clickMinus(View V){
//        controller.desiredTemperature -= 0.1;
//        controller.isOverriden = true;
//        updateFromController();
//    }

    private TextView getActionBarTextView(Toolbar toolbar) {
        TextView titleTextView = null;

        try {
            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(toolbar);
        } catch (NoSuchFieldException | IllegalAccessException e) {
        }

        return titleTextView;
    }
    public void updateFromController(){
        currTemp.post(new Runnable() {
            @Override
            public void run() {
                currTemp.setText(String.format("%.1f", controller.currentTemperature));
            }
        });
        targetTemp.post(new Runnable() {
            @Override
            public void run() {
                targetTemp.setText(String.format("%.1f", controller.desiredTemperature));
            }
        });
        //targetTemp.setText(String.format("%.1f",controller.desiredTemperature));
        //SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        //fakeDate.setText(dateFormat.format(controller.fakeDate));
        fakeDate.post(new Runnable() {
            @Override
            public void run() {
                fakeDate.setText(controller.fakeDate.get(Calendar.HOUR_OF_DAY) + ":" + controller.fakeDate.get(Calendar.MINUTE));
            }
        });
        //fakeDate.setText(controller.fakeDate.get(Calendar.HOUR_OF_DAY)+ ":"+controller.fakeDate.get(Calendar.MINUTE));

        //Log.i("Update",controller.fakeDate.get(Calendar.HOUR_OF_DAY) + ":" + controller.fakeDate.get(Calendar.MINUTE));

    }
    public void clickSchedule(View V){

    }
}



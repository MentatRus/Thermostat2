package com.hse.ndolgopolov.thermostat.Activity;

import android.app.Activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.gc.materialdesign.views.ButtonFlat;
import com.hse.ndolgopolov.thermostat.Adapter.WeekAdapterWithCheckbox;
import com.hse.ndolgopolov.thermostat.Model.Globals;
import com.hse.ndolgopolov.thermostat.R;
import com.rey.material.widget.Slider;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import java.util.Calendar;

/**
 * Created by Igor on 30.05.2015.
 */
public class EditIntervalActivity extends Activity {

    private ListView listView;
    private com.rey.material.widget.Button fromTime;
    private com.rey.material.widget.Button toTime;
    int beginHour = 0, beginMinute = 0, endHour = 0, endMinute = 0;

    private boolean newInterval;
    private ButtonFlat deleteButton;
    private int day;
    private int interval;

    private Slider sliderFrom;
    private Slider sliderTo;
    TimePickerDialog.OnTimeSetListener onTimeSetListenerFrom;
    TimePickerDialog.OnTimeSetListener onTimeSetListenerTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_interval_layout);
        //Log.i("Check", "1");
        init();
        //Log.i("Check", "4");


        beginHour = Globals.controller.fakeDate.get(Calendar.HOUR);
        beginMinute = Globals.controller.fakeDate.get(Calendar.MINUTE);
        endHour = (Globals.controller.fakeDate.get(Calendar.HOUR) + 1)%24;
        endMinute = Globals.controller.fakeDate.get(Calendar.MINUTE);


        sliderFrom = (Slider) findViewById(R.id.sliderFrom);

        sliderFrom.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider slider, boolean b, float v, float v1, int i, int i1) {
                TextView from = (TextView) findViewById(R.id.fromTimeTextView);
                from.setText(getStringTime(i1));
                beginHour = i1/60;
                beginMinute = i1 %60;

                if (i1 > sliderTo.getValue()) {
                    sliderTo.setValue(i1, true);
                }
            }
        });

        sliderTo = (Slider) findViewById(R.id.sliderTo);
        sliderTo.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider slider, boolean b, float v, float v1, int i, int i1) {
                TextView to = (TextView) findViewById(R.id.toTimeTextView);
                to.setText(getStringTime(i1));
                endHour = i1/60;
                endMinute = i1 % 60;

                if (i1 < sliderFrom.getValue()) {
                    sliderFrom.setValue(i1, true);
                }
            }
        });


        if (!newInterval) {
            sliderFrom.setValue(getBeginHour() * 60 + getBeginMinute(), true);
            sliderTo.setValue(getEndHour() * 60 + getEndMinute(), true);
        }
        else{
            sliderFrom.setValue(beginHour * 60 + beginMinute, true);
            sliderTo.setValue(beginHour * 60 + beginMinute, true);
        }

    }

    private String getStringTime(int i) {
        String result = "";
        int h = i / 60;
        int m = i % 60;

        String hour = h + "";
        String minute = (m < 10 ? "0" + m : m) + (m == 0 ? "0" : "");
        result += hour + ":" + minute;

        return result;
    }

    private int getBeginHour() {
        return Globals.controller.weekSchedule.days[day].intervals.get(interval).begin.get(Calendar.HOUR_OF_DAY);
    }

    private int getBeginMinute() {
        return Globals.controller.weekSchedule.days[day].intervals.get(interval).begin.get(Calendar.MINUTE);
    }

    private int getEndHour() {
        return Globals.controller.weekSchedule.days[day].intervals.get(interval).end.get(Calendar.HOUR_OF_DAY);
    }

    private int getEndMinute() {
        return Globals.controller.weekSchedule.days[day].intervals.get(interval).end.get(Calendar.MINUTE);
    }

    private void init() {
        //Log.i("Check", "2");

        Typeface roboto_light = Typeface.createFromAsset(this.getAssets(), "Roboto-Light.ttf");

        TextView title = (TextView) findViewById(R.id.titleTextView);
        TextView from = (TextView) findViewById(R.id.fromTextView);
        fromTime = (com.rey.material.widget.Button) findViewById(R.id.fromTimeTextView);
        TextView to = (TextView) findViewById(R.id.toTextView);
        toTime = (com.rey.material.widget.Button) findViewById(R.id.toTimeTextView);

        title.setTypeface(roboto_light);
        from.setTypeface(roboto_light);
        fromTime.setTypeface(roboto_light);
        fromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(onTimeSetListenerFrom, beginHour, beginMinute, true);
                timePickerDialog.show(getFragmentManager(), "Start time");
            }
        });
        toTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(onTimeSetListenerTo,endHour, endMinute, true);
                timePickerDialog.show(getFragmentManager(),"Finish time");
            }
        });

        to.setTypeface(roboto_light);
        toTime.setTypeface(roboto_light);

        deleteButton = (ButtonFlat) findViewById(R.id.deleteButton);

        newInterval = getIntent().getBooleanExtra("new", false);
        day = getIntent().getIntExtra("day", 0);
        interval = getIntent().getIntExtra("interval", 0);

        if (newInterval) {
            deleteButton.setVisibility(View.GONE);
            title.setText("New interval");
        }

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new WeekAdapterWithCheckbox(this));
        listView.setClickable(false);
        onTimeSetListenerFrom = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {
                fromTime.setText(getStringTime(i*60 + i1));
                beginHour = i;
                beginMinute = i1;
                if((beginHour * 60 + beginMinute) > (endHour * 60 + endMinute)){
                    endHour = beginHour;
                    endMinute = beginMinute;
                }
                sliderFrom.setValue(beginHour * 60 + beginMinute, true);
                sliderTo.setValue(endHour*60 + endMinute, true);
            }
        };
        onTimeSetListenerTo = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {
                toTime.setText(getStringTime(i*60 + i1));

                endHour = i;
                endMinute = i1;
                if((beginHour * 60 + beginMinute) > (endHour * 60 + endMinute)){
                    beginHour = endHour;
                    beginMinute = endMinute;
                }
                sliderFrom.setValue(beginHour * 60 + beginMinute, true);
                sliderTo.setValue(endHour*60 + endMinute, true);
            }
        };


        //Log.i("Check", "3");
    }

    public void clickAddInterval(View V){
        WeekAdapterWithCheckbox adapterWithCheckbox = (WeekAdapterWithCheckbox)listView.getAdapter();

        for (int i = 0; i < 7; i++){
            if(newInterval){
                if(adapterWithCheckbox.checkedDays[i]){
                    Globals.controller.weekSchedule.days[i].addInterval(beginHour, beginMinute, endHour, endMinute);
                    Log.i("Added to", i+"");
                }

            }
            else {
                if(adapterWithCheckbox.checkedDays[i]){
                    Globals.controller.weekSchedule.days[i].intervals.remove(interval);
                    Globals.controller.weekSchedule.days[i].addInterval(beginHour, beginMinute, endHour, endMinute);
                }
            }
        }

        finish();
    }
    public void clickDelete(View V){
        WeekAdapterWithCheckbox adapterWithCheckbox = (WeekAdapterWithCheckbox)listView.getAdapter();
        for (int i = 0; i < 7; i++) {
            if(adapterWithCheckbox.checkedDays[i]){
                Globals.controller.weekSchedule.days[i].intervals.remove(interval);
            }

        }
        finish();
    }
    public boolean isNewInterval() {
        return newInterval;
    }

    public int getDay() {
        return day;
    }

    public int getInterval() {
        return interval;
    }
    public void clickFromButton(View v){
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(onTimeSetListenerFrom, beginHour, beginMinute, true);
        timePickerDialog.show(getFragmentManager(), "Start time");
    }
    public void clickToButton(View v){
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(onTimeSetListenerTo,endHour, endMinute, true);
        timePickerDialog.show(getFragmentManager(),"Finish time");
    }
}

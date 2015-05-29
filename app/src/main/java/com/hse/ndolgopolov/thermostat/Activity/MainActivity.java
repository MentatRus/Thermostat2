package com.hse.ndolgopolov.thermostat.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hse.ndolgopolov.thermostat.Controller;
import com.hse.ndolgopolov.thermostat.R;

import java.lang.reflect.Field;


public class MainActivity extends ActionBarActivity {

    TextView currTemp;
    TextView currTempLabel;
    TextView targetTemp;
    TextView schedule;
    Controller controller = new Controller(this);
    private boolean locked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        controller.start();
    }

    private void init() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        currTemp = (TextView) findViewById(R.id.currentTemperatureTextView);
        currTempLabel = (TextView) findViewById(R.id.currentTemperatureLabelTextView);
        targetTemp = (TextView) findViewById(R.id.targetTemperatureTextView);
        schedule = (TextView) findViewById(R.id.scheduleLabelTextView);
        TextView toolbarTextView = getActionBarTextView(mToolbar);

        Typeface roboto_light = Typeface.createFromAsset(this.getAssets(), "Roboto-Light.ttf");
        Typeface roboto_bold = Typeface.createFromAsset(this.getAssets(), "Roboto-Bold.ttf");

        currTemp.setTypeface(roboto_light);
        currTempLabel.setTypeface(roboto_light);
        targetTemp.setTypeface(roboto_light);
        schedule.setTypeface(roboto_light);
        toolbarTextView.setTypeface(roboto_bold);
        toolbarTextView.setTextSize(20);

        ImageView lockImageView = (ImageView) findViewById(R.id.lockImageView);
        lockImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locked) {
                    v.setBackgroundResource(R.drawable.ic_lock_open);
                } else {
                    v.setBackgroundResource(R.drawable.ic_lock_closed);
                }

                locked = !locked;
            }
        });
        updateFromController();
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
    public void clickPlus(View V){
        controller.desiredTemperature += 0.1;
        controller.isOverriden = true;
        updateFromController();
    }
    public void clickMinus(View V){
        controller.desiredTemperature -= 0.1;
        controller.isOverriden = true;
        updateFromController();
    }

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
        currTemp.setText(String.format("%.1f",controller.currentTemperature));
        targetTemp.setText(String.format("%.1f",controller.desiredTemperature));
    }
}

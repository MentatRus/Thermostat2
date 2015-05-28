package com.hse.ndolgopolov.thermostat;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by Igor on 28.05.2015.
 */
public class WeekActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week_schedule_layout);

        init();
    }

    private void init() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        TextView dayTemp = (TextView) findViewById(R.id.dayTemperatureTextView);
        TextView dayTempTitle = (TextView) findViewById(R.id.dayTitleTextView);
        TextView nightTemp = (TextView) findViewById(R.id.nightTemperatureTextView);
        TextView nightTempTitle = (TextView) findViewById(R.id.nightTitleTextView);
        TextView temperatureTitle = (TextView) findViewById(R.id.temperatureTitleTextView);
        TextView scheduleTitle = (TextView) findViewById(R.id.scheduleTitleTextView);
        TextView toolbarTextView = getActionBarTextView(mToolbar);

        Typeface roboto_light = Typeface.createFromAsset(this.getAssets(), "Roboto-Light.ttf");
        Typeface roboto_bold = Typeface.createFromAsset(this.getAssets(), "Roboto-Bold.ttf");

        dayTemp.setTypeface(roboto_light);
        dayTempTitle.setTypeface(roboto_light);
        temperatureTitle.setTypeface(roboto_light);
        scheduleTitle.setTypeface(roboto_light);
        nightTemp.setTypeface(roboto_light);
        nightTempTitle.setTypeface(roboto_light);
        toolbarTextView.setTypeface(roboto_bold);
        toolbarTextView.setTextSize(20);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_week_schedule, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_upload: {
                break;
            }

            case R.id.action_save: {
                break;
            }
        }

        return super.onOptionsItemSelected(item);
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
}

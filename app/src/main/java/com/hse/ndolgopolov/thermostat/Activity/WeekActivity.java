package com.hse.ndolgopolov.thermostat.Activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.hse.ndolgopolov.thermostat.Adapter.WeekAdapter;
import com.hse.ndolgopolov.thermostat.Dialog.SetTemperatureDialog;
import com.hse.ndolgopolov.thermostat.R;

import java.lang.reflect.Field;

/**
 * Created by Igor on 28.05.2015.
 */
public class WeekActivity extends ActionBarActivity {

    private TextView dayTempTextView;
    private TextView nightTempTextView;
    private Button changeTempButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week_schedule_layout);

        init();

        changeTempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        final SetTemperatureDialog dialog = new SetTemperatureDialog(this);
        dialog.show();
        dialog.getPositiveButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dayTextView = (TextView) dialog.findViewById(R.id.dayTemperatureTextView);
                double dayTemp = Double.parseDouble(dayTextView.getText().toString());
                dayTempTextView.setText(dayTemp + "");

                TextView nightTextView = (TextView) dialog.findViewById(R.id.nightTemperatureTextView);
                double nightTemp = Double.parseDouble(nightTextView.getText().toString());
                nightTempTextView.setText(nightTemp + "");

                dialog.cancel();
            }
        });
    }


    private void init() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        dayTempTextView = (TextView) findViewById(R.id.dayTemperatureTextView);
        TextView dayTempTitle = (TextView) findViewById(R.id.dayTitleTextView);
        nightTempTextView = (TextView) findViewById(R.id.nightTemperatureTextView);
        TextView nightTempTitle = (TextView) findViewById(R.id.nightTitleTextView);
        TextView temperatureTitle = (TextView) findViewById(R.id.temperatureTitleTextView);
        TextView scheduleTitle = (TextView) findViewById(R.id.scheduleTitleTextView);
        TextView toolbarTextView = getActionBarTextView(mToolbar);

        Typeface roboto_light = Typeface.createFromAsset(this.getAssets(), "Roboto-Light.ttf");
        Typeface roboto_bold = Typeface.createFromAsset(this.getAssets(), "Roboto-Bold.ttf");

        dayTempTextView.setTypeface(roboto_light);
        dayTempTitle.setTypeface(roboto_light);
        temperatureTitle.setTypeface(roboto_light);
        scheduleTitle.setTypeface(roboto_light);
        nightTempTextView.setTypeface(roboto_light);
        nightTempTitle.setTypeface(roboto_light);
        toolbarTextView.setTypeface(roboto_bold);
        toolbarTextView.setTextSize(20);

        changeTempButton = (Button) findViewById(R.id.changeTemperatureButton);

        ListView listView = (ListView) findViewById(R.id.weekListView);
        WeekAdapter adapter = new WeekAdapter(this);
        listView.setAdapter(adapter);
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
package com.hse.ndolgopolov.thermostat.Activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.hse.ndolgopolov.thermostat.Adapter.DayAdapter;
import com.hse.ndolgopolov.thermostat.Model.DaySchedule;
import com.hse.ndolgopolov.thermostat.R;

/**
 * Created by Igor on 30.05.2015.
 */
public class DayActivity extends Activity {

    private TextView titleTextView;
    private ListView intervalsListView;
    private DayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_schedule_layout);

        intervalsListView = (ListView) findViewById(R.id.intervalsListView);

        //TODO: pass actual DaySchedule object
        adapter = new DayAdapter(this, new DaySchedule());
        intervalsListView.setAdapter(adapter);

        titleTextView = (TextView) findViewById(R.id.dayTitleTextView);
        String day = getIntent().getExtras().getString("day");
        titleTextView.setText(day);

        Typeface roboto_light = Typeface.createFromAsset(this.getAssets(), "Roboto-Light.ttf");
        titleTextView.setTypeface(roboto_light);
    }
}

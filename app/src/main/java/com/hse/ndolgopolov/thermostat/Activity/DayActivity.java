package com.hse.ndolgopolov.thermostat.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.hse.ndolgopolov.thermostat.Adapter.DayAdapter;
import com.hse.ndolgopolov.thermostat.Model.Globals;
import com.hse.ndolgopolov.thermostat.R;
import com.melnykov.fab.FloatingActionButton;

/**
 * Created by Igor on 30.05.2015.
 */
public class DayActivity extends Activity {

    private TextView titleTextView;
    private ListView intervalsListView;
    private DayAdapter adapter;
    private FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_schedule_layout);

        intervalsListView = (ListView) findViewById(R.id.intervalsListView);

        //TODO: pass actual DaySchedule object
        Intent intent = getIntent();
        int position = intent.getIntExtra("day", 0);
        adapter = new DayAdapter(this, Globals.controller.weekSchedule.days[position]);
        intervalsListView.setAdapter(adapter);

        titleTextView = (TextView) findViewById(R.id.dayTitleTextView);
        String day = getIntent().getExtras().getString("day");
        titleTextView.setText(day);

        Typeface roboto_light = Typeface.createFromAsset(this.getAssets(), "Roboto-Light.ttf");
        titleTextView.setTypeface(roboto_light);

        button = (FloatingActionButton) findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewIntervalActivity();
            }
        });
    }

    private void showNewIntervalActivity() {
        Intent intent = new Intent(this, EditIntervalActivity.class);
        intent.putExtra("new", true);
        startActivity(intent);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        adapter.notifyDataSetChanged();
    }
}

package com.hse.ndolgopolov.thermostat.Activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.hse.ndolgopolov.thermostat.Adapter.WeekAdapterWithCheckbox;
import com.hse.ndolgopolov.thermostat.R;
import com.rey.material.widget.Slider;

/**
 * Created by Igor on 30.05.2015.
 */
public class NewIntervalActivity extends Activity {

    private ListView listView;
    private TextView fromTime;
    private TextView toTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_interval_layout);

        init();

        Slider sliderFrom = (Slider) findViewById(R.id.sliderFrom);
        sliderFrom.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider slider, boolean b, float v, float v1, int i, int i1) {
                TextView from = (TextView) findViewById(R.id.fromTimeTextView);
                from.setText(getStringTime(i1));
            }
        });

        Slider sliderTo = (Slider) findViewById(R.id.sliderTo);
        sliderTo.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider slider, boolean b, float v, float v1, int i, int i1) {
                TextView to = (TextView) findViewById(R.id.toTimeTextView);
                to.setText(getStringTime(i1));
            }
        });
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

    private void init() {
        Typeface roboto_light = Typeface.createFromAsset(this.getAssets(), "Roboto-Light.ttf");

        TextView title = (TextView) findViewById(R.id.titleTextView);
        TextView from = (TextView) findViewById(R.id.fromTextView);
        fromTime = (TextView) findViewById(R.id.fromTimeTextView);
        TextView to = (TextView) findViewById(R.id.toTextView);
        toTime = (TextView) findViewById(R.id.toTimeTextView);

        title.setTypeface(roboto_light);
        from.setTypeface(roboto_light);
        fromTime.setTypeface(roboto_light);
        to.setTypeface(roboto_light);
        toTime.setTypeface(roboto_light);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new WeekAdapterWithCheckbox(this));
        listView.setClickable(false);
    }
}

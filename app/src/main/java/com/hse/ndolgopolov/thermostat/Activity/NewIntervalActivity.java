package com.hse.ndolgopolov.thermostat.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import com.hse.ndolgopolov.thermostat.Adapter.WeekAdapterWithCheckbox;
import com.hse.ndolgopolov.thermostat.Model.Globals;
import com.hse.ndolgopolov.thermostat.R;
import com.rey.material.widget.Slider;

/**
 * Created by Igor on 30.05.2015.
 */
public class NewIntervalActivity extends Activity {

    private ListView listView;
    private TextView fromTime;
    private TextView toTime;
    int beginHour =0, beginMinute = 0, endHour = 0, endMinute = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_interval_layout);
        //Log.i("Check", "1");
        init();
        //Log.i("Check", "4");

        Slider sliderFrom = (Slider) findViewById(R.id.sliderFrom);
        sliderFrom.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider slider, boolean b, float v, float v1, int i, int i1) {
                TextView from = (TextView) findViewById(R.id.fromTimeTextView);
                from.setText(getStringTime(i1));
                beginHour = i1/60;
                beginMinute = i1 %60;
            }
        });

        Slider sliderTo = (Slider) findViewById(R.id.sliderTo);
        sliderTo.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider slider, boolean b, float v, float v1, int i, int i1) {
                TextView to = (TextView) findViewById(R.id.toTimeTextView);
                to.setText(getStringTime(i1));
                endHour = i1/60;
                endMinute = i1 % 60;
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
        //Log.i("Check", "2");

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
        //Log.i("Check", "3");

    }
    public void clickAddInterval(View V){
        WeekAdapterWithCheckbox adapterWithCheckbox = (WeekAdapterWithCheckbox)listView.getAdapter();


        for (int i = 0; i < 7; i++){
            if(adapterWithCheckbox.checkedDays[i]){
                Globals.controller.weekSchedule.days[i].addInterval(beginHour, beginMinute,endHour,endMinute);
                Log.i("Added to", i+"");
            }
        }
        Intent intent = new Intent(this, WeekActivity.class);
        startActivity(intent);
    }
}
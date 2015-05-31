package com.hse.ndolgopolov.thermostat.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.hse.ndolgopolov.thermostat.Model.Globals;
import com.hse.ndolgopolov.thermostat.R;
import com.rey.material.app.Dialog;
import com.rey.material.widget.Slider;

/**
 * Created by Igor on 29.05.2015.
 */
public class SetTemperatureDialog extends Dialog {

    private TextView dayTemp;
    private TextView nightTemp;
    public SetTemperatureDialog(Context context) {
        super(context);

        this.title("Set temperature")
                .positiveAction("SET")
                .negativeAction("CANCEL")
                .contentView(R.layout.set_temperature_layout)
                .cancelable(true)
                .positiveActionTextColor(Color.parseColor("#3F51B5"))
                .negativeActionTextColor(Color.RED);

        dayTemp = (TextView) findViewById(R.id.dayTemperatureTextView);
        nightTemp = (TextView) findViewById(R.id.nightTemperatureTextView);

        dayTemp.setText(Globals.controller.weekSchedule.highTemperature + "");
        nightTemp.setText(Globals.controller.weekSchedule.lowTemperature + "");

        Slider sliderDay = (Slider) findViewById(R.id.sliderDay);
        sliderDay.setValue((float) Globals.controller.weekSchedule.highTemperature * 10, true);
        sliderDay.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider slider, boolean b, float v, float v1, int i, int i1) {

                dayTemp.setText(getDoubleValue(i1) + "");
            }
        });

        Slider sliderNight = (Slider) findViewById(R.id.sliderNight);
        sliderNight.setValue((float) Globals.controller.weekSchedule.lowTemperature * 10, true);
        sliderNight.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider slider, boolean b, float v, float v1, int i, int i1) {

                nightTemp.setText(getDoubleValue(i1) + "");
            }
        });

        mNegativeAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

    }

    public Button getPositiveButton() {
        return mPositiveAction;
    }

    private double getDoubleValue(int i) {
        return i / 10 + (double) (i % 10) / 10;
    }
}

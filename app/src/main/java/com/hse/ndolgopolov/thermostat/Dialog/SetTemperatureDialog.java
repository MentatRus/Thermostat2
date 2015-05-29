package com.hse.ndolgopolov.thermostat.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;
import com.hse.ndolgopolov.thermostat.R;
import com.rey.material.app.Dialog;
import com.rey.material.widget.Slider;

/**
 * Created by Igor on 29.05.2015.
 */
public class SetTemperatureDialog extends Dialog {

    public SetTemperatureDialog(Context context) {
        super(context);

        this.title("Set temperature")
                .positiveAction("SET")
                .negativeAction("CANCEL")
                .contentView(R.layout.set_temperature_layout)
                .cancelable(true)
                .positiveActionTextColor(Color.parseColor("#3F51B5"))
                .negativeActionTextColor(Color.RED);

        Slider sliderDay = (Slider) findViewById(R.id.sliderDay);
        sliderDay.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider slider, boolean b, float v, float v1, int i, int i1) {
                TextView dayTemp = (TextView) findViewById(R.id.dayTemperatureTextView);
                dayTemp.setText(getDoubleValue(i1) + "");
            }
        });

        Slider sliderNight = (Slider) findViewById(R.id.sliderNight);
        sliderNight.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider slider, boolean b, float v, float v1, int i, int i1) {
                TextView nightTemp = (TextView) findViewById(R.id.nightTemperatureTextView);
                nightTemp.setText(getDoubleValue(i1) + "");
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

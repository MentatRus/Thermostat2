package com.hse.ndolgopolov.thermostat.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.hse.ndolgopolov.thermostat.Model.Globals;
import com.hse.ndolgopolov.thermostat.R;
import com.rey.material.widget.CheckBox;

/**
 * Created by Igor on 30.05.2015.
 */
public class WeekAdapterWithCheckbox extends BaseAdapter {
    private Context context;
    public boolean[] checkedDays = new boolean[]{false,false,false,false,false,false,false};
    public WeekAdapterWithCheckbox(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Globals.weekDays.length;
    }

    @Override
    public Object getItem(int position) {
        return Globals.weekDays[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.single_element_with_checkbox, parent, false);
        }

        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox);

        cb.setEnabled(!Globals.controller.weekSchedule.days[position].isFull());

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedDays[position] = isChecked;
            }
        });

        TextView textView = (TextView) convertView.findViewById(R.id.dayTextView);
        textView.setText(Globals.weekDays[position]);

        Typeface roboto_light = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf");
        textView.setTypeface(roboto_light);

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }


}

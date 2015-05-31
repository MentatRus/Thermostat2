package com.hse.ndolgopolov.thermostat.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hse.ndolgopolov.thermostat.Model.DaySchedule;

import com.hse.ndolgopolov.thermostat.R;

/**
 * Created by Igor on 30.05.2015.
 */
public class DayAdapter extends BaseAdapter {
    private Context context;
    private DaySchedule schedule;

    public DayAdapter(Context context, DaySchedule schedule) {
        this.context = context;
        this.schedule = schedule;
    }

    @Override
    public int getCount() {
        return schedule.intervals.size();
    }

    @Override
    public Object getItem(int position) {
        return schedule.intervals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.single_element, parent, false);
        }

        Typeface roboto_light = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf");

        TextView textView = (TextView) convertView.findViewById(R.id.singleElementTextView);
        textView.setText(getItem(position).toString());
        textView.setTypeface(roboto_light);

        return convertView;
    }
}

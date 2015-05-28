package com.hse.ndolgopolov.thermostat.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.hse.ndolgopolov.thermostat.R;

/**
 * Created by Igor on 28.05.2015.
 */
public class WeekAdapter extends BaseAdapter {
    private String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private Context context;

    public WeekAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return weekDays.length;
    }

    @Override
    public Object getItem(int position) {
        return weekDays[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.week_single_element_in_listview, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.dayOfTheWeekTextView);
        textView.setText(weekDays[position]);

        Typeface roboto_light = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf");
        textView.setTypeface(roboto_light);

        return convertView;
    }
}

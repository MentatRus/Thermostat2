package com.hse.ndolgopolov.thermostat.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.hse.ndolgopolov.thermostat.Model.Globals;
import com.hse.ndolgopolov.thermostat.R;

/**
 * Created by Igor on 28.05.2015.
 */
public class WeekAdapter extends BaseAdapter {
    private Context context;

    public WeekAdapter(Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.single_element_in_listview, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.singleElementTextView);
        textView.setText(Globals.weekDays[position]);

        Typeface roboto_light = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf");
        textView.setTypeface(roboto_light);

        return convertView;
    }
}

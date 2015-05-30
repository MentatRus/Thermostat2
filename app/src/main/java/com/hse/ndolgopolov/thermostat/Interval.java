package com.hse.ndolgopolov.thermostat;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by Nickolay Dolgopolov on 30.05.2015.
 */
public class Interval implements Comparable<Interval> {
    Calendar begin;
    Calendar end;

    public Interval(int h1, int m1, int h2, int m2) {
        begin = Calendar.getInstance();
        end = Calendar.getInstance();
        begin.set(Calendar.HOUR, h1);
        end.set(Calendar.HOUR, h2);
        begin.set(Calendar.MINUTE, m1);
        end.set(Calendar.MINUTE, m2);
    }

    public Interval(Interval interval) {
        begin = Calendar.getInstance();
        end = Calendar.getInstance();
        int h1 = interval.begin.get(Calendar.HOUR);
        int h2 = interval.end.get(Calendar.HOUR);
        int m1 = interval.begin.get(Calendar.MINUTE);
        int m2 = interval.end.get(Calendar.MINUTE);
        begin.set(Calendar.HOUR, h1);
        end.set(Calendar.HOUR, h2);
        begin.set(Calendar.MINUTE, m1);
        end.set(Calendar.MINUTE, m2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interval)) return false;

        Interval interval = (Interval) o;

        if (!begin.equals(interval.begin)) return false;
        return end.equals(interval.end);

    }

    @Override
    public int hashCode() {
        int result = begin.hashCode();
        result = 31 * result + end.hashCode();
        return result;
    }

    @Override
    public int compareTo(Interval another) {
        return this.begin.compareTo(another.begin);
    }

    public boolean includesTime(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int m3 = hour*60 + minute;
//        Log.i("hour", hour+"");
//        Log.i("minute", minute+"");
//        Log.i("begin hour", begin.get(Calendar.HOUR) + "");
//        Log.i("end hour", end.get(Calendar.HOUR) + "");
//        Log.i("begin minute", begin.get(Calendar.MINUTE) + "");
//        Log.i("end minute", begin.get(Calendar.MINUTE) + "");
//        Log.i("Res", String.valueOf(begin.get(Calendar.HOUR) <= hour && begin.get(Calendar.MINUTE) <= minute &&
//                end.get(Calendar.HOUR) >= hour && begin.get(Calendar.MINUTE) >= minute));
//        return begin.get(Calendar.HOUR) <= hour && begin.get(Calendar.MINUTE) <= minute &&
//                end.get(Calendar.HOUR) >= hour && begin.get(Calendar.MINUTE) >= minute;
        int m1 = begin.get(Calendar.HOUR) * 60 + begin.get(Calendar.MINUTE);
        int m2 = end.get(Calendar.HOUR) * 60 + end.get(Calendar.MINUTE);
        return m3>= m1 && m3 <= m2;
    }
}

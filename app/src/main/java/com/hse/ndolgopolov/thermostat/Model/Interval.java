package com.hse.ndolgopolov.thermostat.Model;

import java.util.Calendar;

/**
 * Created by Nickolay Dolgopolov on 30.05.2015.
 */
public class Interval implements Comparable<Interval> {
    public Calendar begin;
    public Calendar end;

    public Interval(int h1, int m1, int h2, int m2) {
        begin = Calendar.getInstance();
        end = Calendar.getInstance();
        begin.set(Calendar.HOUR_OF_DAY, h1);
        end.set(Calendar.HOUR_OF_DAY, h2);
        begin.set(Calendar.MINUTE, m1);
        end.set(Calendar.MINUTE, m2);
    }

    public Interval(Interval interval) {
        begin = Calendar.getInstance();
        end = Calendar.getInstance();
        int h1 = interval.begin.get(Calendar.HOUR_OF_DAY);
        int h2 = interval.end.get(Calendar.HOUR_OF_DAY);
        int m1 = interval.begin.get(Calendar.MINUTE);
        int m2 = interval.end.get(Calendar.MINUTE);
        begin.set(Calendar.HOUR_OF_DAY, h1);
        end.set(Calendar.HOUR_OF_DAY, h2);
        begin.set(Calendar.MINUTE, m1);
        end.set(Calendar.MINUTE, m2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interval)) return false;

        Interval interval = (Interval) o;

//        if (!begin.equals(interval.begin)) return false;
//        return end.equals(interval.end);
        return toString().equals(interval.toString());
    }

    @Override
    public String toString() {
        String s1 = String.valueOf(begin.get(Calendar.HOUR_OF_DAY));
        s1 = s1.length()<2?"0"+s1:s1;
        String s2 = String.valueOf(begin.get(Calendar.MINUTE));
        s2 = s2.length()<2?"0"+s2:s2;
        String s3 = String.valueOf(end.get(Calendar.HOUR_OF_DAY));
        s3 = s3.length()<2?"0"+s3:s3;
        String s4 = String.valueOf(end.get(Calendar.MINUTE));
        s4 = s4.length()<2?"0"+s4:s4;
        String res = s1 +":"+s2+ " - "+s3+":"+s4;
        return res;
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
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int m3 = hour*60 + minute;
        int m1 = begin.get(Calendar.HOUR_OF_DAY) * 60 + begin.get(Calendar.MINUTE);
        int m2 = end.get(Calendar.HOUR_OF_DAY) * 60 + end.get(Calendar.MINUTE);
        return m3>= m1 && m3 <= m2;
    }

    public boolean isIntervalBeginning(Calendar calendar){
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        boolean isBegin = begin.get(Calendar.HOUR) == calendar.get(Calendar.HOUR) && begin.get(Calendar.MINUTE) == calendar.get(Calendar.MINUTE);
        boolean isEnd = end.get(Calendar.HOUR) == calendar.get(Calendar.HOUR) && end.get(Calendar.MINUTE) == calendar.get(Calendar.MINUTE);

        return isBegin || isEnd;
    }

    public boolean overlaps(Interval interval){
        return interval.includesTime(begin) || interval.includesTime(end) || includesTime(interval.begin) || includesTime(interval.end);
    }

    public static Interval mergeIntervals(Interval i1, Interval i2){
        if(!i1.overlaps(i2)) return null;
        Calendar begin = null;
        Calendar end = null;
        if( i1.begin.compareTo(i2.begin) == -1){
            begin = i1.begin;
        }
        else {
            begin = i2.begin;
        }

        if( i1.end.compareTo(i2.end) == 1){
            end = i1.end;
        }
        else {
            end = i2.end;
        }
        return new Interval(begin.get(Calendar.HOUR_OF_DAY), begin.get(Calendar.MINUTE),end.get(Calendar.HOUR_OF_DAY), end.get(Calendar.MINUTE));
    }
}

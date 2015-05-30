package com.hse.ndolgopolov.thermostat.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by Nickolay Dolgopolov on 28.05.2015.
 */
//ВАЖНО: класс интервал сравнивается по значениям (переопределен equals)
public class DaySchedule {
    public String name;
    public WeekSchedule week;
    public ArrayList<Interval> intervals = new ArrayList<Interval>();

    public ArrayList<Interval> getIntervals() {
        return intervals;
    }

    public boolean isFull() {
        return intervals.size() > 4;
    }

    public void addInterval(int h1, int m1, int h2, int m2) {
        Interval newInt = new Interval(h1, m1, h2, m2);
        if (intervals.contains(newInt)) {
            return;
        }
        intervals.add(newInt);
        Collections.sort(intervals);
    }

    public void addInterval(Interval interval) {
        Interval newInt = new Interval(interval);
        if (intervals.contains(newInt)) {
            return;
        }
        intervals.add(newInt);
        Collections.sort(intervals);
    }

    public Interval findInterval(Interval interval) {
        for (Interval interval1 : intervals) {
            if (interval1.equals(interval)) return interval1;
        }
        return null;
    }

    public boolean containsInterval(Interval interval) {
        for (Interval interval1 : intervals) {
            if (interval1.equals(interval)) return true;
        }
        return false;
    }

    public boolean includesTime(Calendar calendar) {
        for (Interval interval : intervals) {
            if (interval.includesTime(calendar)) return true;
        }
        return false;
    }
}

//public class Interval implements Comparable<Interval> {
//    Calendar begin;
//    Calendar end;
//
//    public Interval(int h1, int m1, int h2, int m2) {
//        begin = Calendar.getInstance();
//        end = Calendar.getInstance();
//        begin.add(Calendar.HOUR, h1);
//        end.add(Calendar.HOUR, h2);
//        begin.add(Calendar.MINUTE, m1);
//        end.add(Calendar.MINUTE, m2);
//    }
//
//    public Interval(Interval interval) {
//        begin = Calendar.getInstance();
//        end = Calendar.getInstance();
//        int h1 = interval.begin.get(Calendar.HOUR);
//        int h2 = interval.end.get(Calendar.HOUR);
//        int m1 = interval.begin.get(Calendar.MINUTE);
//        int m2 = interval.end.get(Calendar.MINUTE);
//        begin.add(Calendar.HOUR, h1);
//        end.add(Calendar.HOUR, h2);
//        begin.add(Calendar.MINUTE, m1);
//        end.add(Calendar.MINUTE, m2);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Interval)) return false;
//
//        Interval interval = (Interval) o;
//
//        if (!begin.equals(interval.begin)) return false;
//        return end.equals(interval.end);
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = begin.hashCode();
//        result = 31 * result + end.hashCode();
//        return result;
//    }
//
//    @Override
//    public int compareTo(Interval another) {
//        return this.begin.compareTo(another.begin);
//    }
//
//    boolean includesTime(Calendar calendar) {
//        int hour = calendar.get(Calendar.HOUR);
//        int minute = calendar.get(Calendar.MINUTE);
//        return begin.get(Calendar.HOUR) <= hour && begin.get(Calendar.MINUTE) <= minute &&
//                end.get(Calendar.HOUR) >= hour && begin.get(Calendar.MINUTE) >= minute;
//    }
//}

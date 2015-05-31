package com.hse.ndolgopolov.thermostat.Model;

import android.os.Parcel;

import java.util.*;

/**
 * Created by Nickolay Dolgopolov on 28.05.2015.
 */
public class WeekSchedule{
    public double highTemperature = 23;
    public double lowTemperature = 18.8;
    public DaySchedule[] days = new DaySchedule[7];

    public WeekSchedule() {
        for (int i = 0; i < 7; i++) {
            days[i] = new DaySchedule();
            days[i].week = this;
        }
    }
    public WeekSchedule(Parcel in){
        highTemperature = in.readDouble();
        lowTemperature = in.readDouble();

    }

    boolean isHighTemperature(Calendar calendar) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK )%7;
        //Log.i("Day_of_week", dayOfWeek+"");
        //Log.i("day of week includes time", String.valueOf(days[dayOfWeek].includesTime(calendar)));
        return days[dayOfWeek].includesTime(calendar);
    }

    void addInterval(int day, int h1, int m1, int h2, int m2) {
        days[day].addInterval(h1, m1, h2, m2);
    }

    Set<DaySchedule> daysWithInterval(Interval interval) {
        Set<DaySchedule> res = new HashSet<>();
        for (DaySchedule day : days) {
            if (day.intervals.contains(interval)) res.add(day);
        }
        return res;
    }

    /**
     * Возвращает индексы дней с тким же интервалом (можно юзать для генерации чекбоксов при редактировании интервала)
     *
     * @param interval
     * @return
     */
    List<Integer> dayNumbersWithIntervals(Interval interval) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (days[i].containsInterval(interval)) res.add(i);
        }
        return res;
    }

    Set<Interval> sameIntervals(Interval interval) {
        Set<Interval> res = new HashSet<>();
        for (DaySchedule day : days) {
            if (day.containsInterval(interval)) res.add(day.findInterval(interval));
        }
        return res;
    }

    public void addInterval(Interval interval, Collection<Integer> daysToEdit) {
        for (Integer day : daysToEdit) {
            days[day].addInterval(interval);
        }
    }

    /**
     * Редактировние. Когда юзер отметил нужные дни, редактирует все из них
     *
     * @param interval
     * @param newInterval
     * @param daysToEdit
     */
    void editInterval(Interval interval, Interval newInterval, Collection<Integer> daysToEdit) {
        for (Integer day : daysToEdit) {
            Interval oldInt = days[day].findInterval(interval);
            days[day].intervals.remove(oldInt);//ВНИМАНИЕ, может не работать
            days[day].intervals.add(new Interval(newInterval));
        }
    }

    void deleteInterval(Interval interval, Collection<Integer> daysToEdit) {
        for (Integer day : daysToEdit) {
            Interval oldInt = days[day].findInterval(interval);
            days[day].intervals.remove(oldInt);
        }
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
////        dest.writeArray(new Object[]{new Double(highTemperature),new Double(lowTemperature),days});
////        dest.w
//        dest.writeDouble(highTemperature);
//        dest.writeDouble(lowTemperature);
//        dest.writeArray(days);
//    }
//    public static final Parcelable.Creator<WeekSchedule> CREATOR = new Parcelable.Creator<WeekSchedule>(){
//        @Override
//        public WeekSchedule createFromParcel(Parcel source) {
//            return null;
//        }
//
//        @Override
//        public WeekSchedule[] newArray(int size) {
//            return new WeekSchedule[0];
//        }
    }

package com.hse.ndolgopolov.thermostat;

import java.util.*;

/**
 * Created by Nickolay Dolgopolov on 28.05.2015.
 */
public class WeekSchedule {
    public double highTemperature;
    public double lowTemperature;
    DaySchedule[] days = new DaySchedule[7];

    public WeekSchedule() {
        for (int i = 0; i < 7; i++) {
            days[i] = new DaySchedule();
            days[i].week = this;
        }
    }

    boolean isHighTemperature(Calendar calendar) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
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

    void addInterval(Interval interval, Collection<Integer> daysToEdit) {
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
}

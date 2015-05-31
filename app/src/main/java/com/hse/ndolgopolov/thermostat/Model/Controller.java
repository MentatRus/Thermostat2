package com.hse.ndolgopolov.thermostat.Model;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by Nickolay Dolgopolov on 29.05.2015.
 */

// ласс, который лежит в каждой форме. ƒоступ к расписани€м и прочему - через него
public class Controller {
    public boolean isOverriden = false;
    public boolean isPermanentlyOverriden = false;
    public boolean isDay = false;
    public int timeScale = 100;
    public WeekSchedule weekSchedule = new WeekSchedule();
    public double desiredTemperature = 0;
    public double currentTemperature = 22;
    public double temperatureChangeSpeed = 0.05;
    public Calendar fakeDate;
    //MainActivity mainActivity;
    public Controller() {
        weekSchedule = new WeekSchedule();
        fakeDate = Calendar.getInstance();
        //mainActivity = activity;
    }

    public void setDesiredTemperature() {
        double scheduleTemperature;
        if (weekSchedule.isHighTemperature(fakeDate))
            scheduleTemperature = weekSchedule.highTemperature;
        else scheduleTemperature = weekSchedule.lowTemperature;
        if(!isOverriden){
            desiredTemperature = scheduleTemperature;
            return;

        }
        if(isOverriden && !isPermanentlyOverriden){
            if(weekSchedule.isHighTemperature(fakeDate) != isDay){
                isDay = weekSchedule.isHighTemperature(fakeDate);
                desiredTemperature =scheduleTemperature;
                isOverriden = false;
            }
            return;
        }

    }

    public void setCurrentTemperature() {
        if (currentTemperature < desiredTemperature) currentTemperature += temperatureChangeSpeed;
        if (currentTemperature > desiredTemperature) currentTemperature -= temperatureChangeSpeed;
    }




}

package com.hse.ndolgopolov.thermostat;

import android.app.Activity;

import com.hse.ndolgopolov.thermostat.Activity.MainActivity;

import java.util.Calendar;

/**
 * Created by Nickolay Dolgopolov on 29.05.2015.
 */

//Класс, который лежит в каждой форме. Доступ к расписаниям и прочему - через него
public class Controller {
    public boolean isOverriden = false;
    public boolean isPermanentlyOverriden = false;
    public boolean isDay = false;
    public int timeScale = 100;
    public WeekSchedule weekSchedule;
    public double desiredTemperature = 23;
    public double currentTemperature = 22;
    public double temperatureChangeSpeed = 0.1;
    Calendar fakeDate;
    MainActivity mainActivity;
    public Controller(MainActivity activity) {
        weekSchedule = new WeekSchedule();
        fakeDate = Calendar.getInstance();
        mainActivity = activity;
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

    public void start() {

        final int minuteLength = 60000 / timeScale;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean c = true;
                while (c) {
                    try {
                        Thread.sleep(minuteLength);
                        setDesiredTemperature();
                        setCurrentTemperature();
                        mainActivity.updateFromController();
                    } catch (InterruptedException ex) {
                        c = false;//Убери, если будет вылетать
                    }
                }
            }
        });
    }


}

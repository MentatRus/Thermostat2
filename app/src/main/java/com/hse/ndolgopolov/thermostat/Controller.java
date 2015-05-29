package com.hse.ndolgopolov.thermostat;

import java.util.Calendar;

/**
 * Created by Nickolay Dolgopolov on 29.05.2015.
 */

//Класс, который лежит в каждой форме. Доступ к расписаниям и прочему - через него
public class Controller {
    boolean isOverriden = false;
    boolean isPermanentlyOverriden = false;
    boolean isDay = false;
    int timeScale = 100;
    WeekSchedule weekSchedule;
    double desiredTemperature = 23;
    double currentTemperature = 22;
    double temperatureChangeSpeed = 0.1;
    Calendar fakeDate;

    Controller() {
        weekSchedule = new WeekSchedule();
        fakeDate = Calendar.getInstance();
    }

    void setDesiredTemperature() {
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

    void setCurrentTemperature() {
        if (currentTemperature < desiredTemperature) currentTemperature += temperatureChangeSpeed;
        if (currentTemperature > desiredTemperature) currentTemperature -= temperatureChangeSpeed;
    }

    void start() {
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
                    } catch (InterruptedException ex) {
                        c = false;//Убери, если будет вылетать
                    }
                }
            }
        });
    }


}

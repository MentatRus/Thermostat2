package com.hse.ndolgopolov.thermostat.Model;

import java.util.Calendar;

/**
 * Created by Nickolay Dolgopolov on 29.05.2015.
 */

//�����, ������� ����� � ������ �����. ������ � ����������� � ������� - ����� ����
public class Controller {
    public boolean isOverriden = false;
    public boolean isPermanentlyOverriden = false;
    public boolean isDay = false;
    public int timeScale = 100;
    public WeekSchedule weekSchedule;
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
        //Log.i("Scheduled temperature", scheduleTemperature+"");
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

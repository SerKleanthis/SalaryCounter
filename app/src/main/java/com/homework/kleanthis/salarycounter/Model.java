package com.homework.kleanthis.salarycounter;

public class Model {

    private String day;
    private int startingTime;
    private int endTime;
    private double cash;

    public Model(){
    }

    public Model(String day, int startingTime, int endTime, double cash) {
        this.day = day;
        this.startingTime = startingTime;
        this.endTime = endTime;
        this.cash = cash;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(int startingTime) {
        this.startingTime = startingTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }
}

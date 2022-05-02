package com.timusandrei.analogclock.singletons;

import android.graphics.Color;

public class ColorSingleton {

    private int hourHand;
    private int minHand;
    private int secHand;
    private int milisecHand;
    private int backGround;
    private int indicators;

    private static ColorSingleton instance;

    private ColorSingleton() {
        hourHand = Color.BLACK;
        minHand = Color.BLACK;
        secHand = Color.RED;
        milisecHand = Color.BLACK;
        backGround = Color.WHITE;
        indicators = Color.BLACK;
    }

    public static ColorSingleton getInstance() {
        if (instance == null) {
            instance = new ColorSingleton();
        }
        return instance;
    }

    public int getHourHand() {
        return hourHand;
    }

    public void setHourHand(int hourHand) {
        this.hourHand = hourHand;
    }

    public int getMinHand() {
        return minHand;
    }

    public void setMinHand(int minHand) {
        this.minHand = minHand;
    }

    public int getSecHand() {
        return secHand;
    }

    public void setSecHand(int secHand) {
        this.secHand = secHand;
    }

    public int getMilisecHand() {
        return milisecHand;
    }

    public void setMilisecHand(int milisecHand) {
        this.milisecHand = milisecHand;
    }

    public int getBackGround() {
        return backGround;
    }

    public void setBackGround(int backGround) {
        this.backGround = backGround;
    }

    public int getIndicators() {
        return indicators;
    }

    public void setIndicators(int indicators) {
        this.indicators = indicators;
    }

}

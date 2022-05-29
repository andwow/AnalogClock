package com.timusandrei.analogclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.timusandrei.analogclock.singletons.ColorSingleton;

public class Timer extends SurfaceView implements Runnable {

    int hour = 0;
    int min = 0;
    int sec = 0;
    int milisec = 0;
    private boolean run = false;
    private Thread thread = null;
    private final SurfaceHolder surfaceHolder;
    private boolean running = false;
    private final ColorSingleton colors;

    private int limitHour;
    private int limitMin;
    private int limitSec;

    public Timer(Context context) {
        super(context);
        surfaceHolder = getHolder();
        colors = ColorSingleton.getInstance();
        limitHour = 0;
        limitMin = 0;
        limitSec = 0;
    }

    public void resetTimer() {
        run = false;
        hour = 0;
        min = 0;
        sec = 0;
        milisec = 0;
    }

    public void turnOnOffTimer() {
        if (run) {
            run = false;
        } else {
            run = true;
            Thread countThread = new Thread(this::increaseTimer);
            countThread.start();
        }
    }

    @Override
    public void run() {

        while (running) {
            if (surfaceHolder.getSurface().isValid()) {

                int middleX = getWidth() / 2;
                int middleY = getHeight() / 2;
                int radius = 500;
                int milisecMiddleX = middleX - radius / 3;
                int milisecMiddleY = middleY - radius / 4;
                int centerRadius = 20;

                Canvas canvas = surfaceHolder.lockCanvas();
                Paint paint = new Paint();

                paint.setColor(Color.BLACK);

                canvas.drawPaint(paint);

                paint.setColor(colors.getBackGround());

                canvas.drawCircle(middleX, middleY, radius, paint);

                paint.setColor(colors.getIndicators());

                canvas.drawCircle(milisecMiddleX, milisecMiddleY, 100, paint);

                paint.setColor(colors.getBackGround());

                canvas.drawCircle(milisecMiddleX, milisecMiddleY, 95, paint);

                paint.setColor(colors.getIndicators());
                paint.setTextSize(80);
                paint.setTextAlign(Paint.Align.CENTER);

                for (int i = 1; i <= 12; i++) {
                    float hourCoef = (float) Math.PI * 2 / 12 * (i - 3);
                    float len = 400;

                    float x = middleX + len * (float) Math.cos(hourCoef);
                    float y = middleY + len * (float) Math.sin(hourCoef);
                    canvas.drawText(String.valueOf(i), x, y + 30, paint);
                }

                for (int i = 1; i <= 60; i++) {
                    float minCoef = (float) Math.PI * 2 / 60 * i;
                    float len = 470;
                    float minRadius = 5;

                    if (i % 5 == 0) {
                        minRadius = 8;
                    }

                    float x = middleX + len * (float) Math.cos(minCoef);
                    float y = middleY + len * (float) Math.sin(minCoef);
                    canvas.drawCircle(x, y, minRadius, paint);
                }

                for (int i = 1; i <= 12; i++) {
                    float milisecCoef = (float) Math.PI * 2 / 12 * i;
                    float len = 85;
                    float minRadius = 4;

                    if (i % 3 == 0) {
                        minRadius = 6;
                    }

                    float x = milisecMiddleX + len * (float) Math.cos(milisecCoef);
                    float y = milisecMiddleY + len * (float) Math.sin(milisecCoef);
                    canvas.drawCircle(x, y, minRadius, paint);
                }

                float hourCoef = (float) Math.PI * 2 / 12 * (hour - 3);
                float minCoef = (float) Math.PI * 2 / 60 * (min - 15);
                float secCoef = (float) Math.PI * 2 / 60 * (sec - 15);
                float milisecCoef = (float) Math.PI / 1000 * 2 * (milisec - 250);

                paint.setColor(colors.getMilisecHand());
                paint.setStrokeWidth(3f);

                float len = 90;
                float x = milisecMiddleX + len * (float) Math.cos(milisecCoef);
                float y = milisecMiddleY + len * (float) Math.sin(milisecCoef);
                canvas.drawLine(milisecMiddleX, milisecMiddleY, x, y, paint);

                paint.setColor(colors.getHourHand());
                paint.setStrokeWidth(15f);

                len = 200;
                x = middleX + len * (float) Math.cos(hourCoef);
                y = middleY + len * (float) Math.sin(hourCoef);
                canvas.drawLine(middleX, middleY, x, y, paint);

                paint.setColor(colors.getMinHand());
                paint.setStrokeWidth(10f);

                len = 300;
                x = middleX + len * (float) Math.cos(minCoef);
                y = middleY + len * (float) Math.sin(minCoef);
                canvas.drawLine(middleX, middleY, x, y, paint);

                paint.setColor(colors.getSecHand());
                paint.setStrokeWidth(5f);

                len = 400;
                x = middleX + len * (float) Math.cos(secCoef);
                y = middleY + len * (float) Math.sin(secCoef);
                canvas.drawLine(middleX, middleY, x, y, paint);

                String time = String.format("%2s", hour).replace(' ', '0') + ":"
                        + String.format("%2s", min).replace(' ', '0') + ":"
                        + String.format("%2s", sec).replace(' ', '0') + ":"
                        + String.format("%3s", milisec).replace(' ', '0');

                paint.setColor(Color.WHITE);
                paint.setTextSize(70);
                paint.setTextAlign(Paint.Align.CENTER);

                x = middleX;
                y = middleY + radius + 125;

                canvas.drawText(time, x, y, paint);

                paint.setColor(colors.getSecHand());

                canvas.drawCircle(middleX, middleY, centerRadius, paint);

                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void increaseTimer() {
        if(limitHour < hour) {
            //Toast.makeText(getContext(), "Limit time greater than actual", Toast.LENGTH_SHORT).show();
            return;
        }
        if(limitHour == hour && limitMin < min) {
            //Toast.makeText(getContext(), "Limit time greater than actual", Toast.LENGTH_SHORT).show();
            return;
        }
        if(limitHour == hour && limitMin == min && limitSec <= sec) {
            //Toast.makeText(getContext(), "Limit time greater than actual", Toast.LENGTH_SHORT).show();
            return;
        }
        if(limitMin > 59 || limitSec > 59) {
            //Toast.makeText(getContext(), "Minutes or seconds greater than possible", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            while(run) {
                Thread.sleep(1);
                if (milisec < 999) {
                    ++milisec;
                } else if (sec < 59) {
                    milisec = 0;
                    ++sec;
                } else if (min < 59) {
                    milisec = 0;
                    sec = 0;
                    ++min;
                } else {
                    milisec = 0;
                    sec = 0;
                    min = 0;
                    ++hour;
                }
                if (hour == limitHour && min == limitMin && sec == limitSec) {
                    run = false;
                    MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.moogle2);
                    mp.setOnCompletionListener(mp1 -> {
                        mp1.reset();
                        mp1.release();
                        mp1 =null;
                    });
                    mp.start();
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        boolean retry = true;
        running = false;
        while(retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void resume() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public int getLimitHour() {
        return limitHour;
    }

    public void setLimitHour(int limitHour) {
        this.limitHour = limitHour;
    }

    public int getLimitMin() {
        return limitMin;
    }

    public void setLimitMin(int limitMin) {
        this.limitMin = limitMin;
    }

    public int getLimitSec() {
        return limitSec;
    }

    public void setLimitSec(int limitSec) {
        this.limitSec = limitSec;
    }

}

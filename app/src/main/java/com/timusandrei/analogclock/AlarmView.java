package com.timusandrei.analogclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.timusandrei.analogclock.singletons.ColorSingleton;

public class AlarmView extends SurfaceView implements Runnable {

    private int hour = 9;
    private int min = 0;
    private Thread thread = null;
    private final SurfaceHolder surfaceHolder;
    private boolean running = false;
    private final ColorSingleton colors;

    public AlarmView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        colors = ColorSingleton.getInstance();
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getHour() {
        return hour;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMin() {
        return min;
    }

    @Override
    public void run() {

        while (running) {
            if (surfaceHolder.getSurface().isValid()) {

                int middleX = getWidth() / 2;
                int middleY = getHeight() / 2;
                int radius = 500;
                int centerRadius = 20;

                Canvas canvas = surfaceHolder.lockCanvas();
                Paint paint = new Paint();

                paint.setColor(Color.BLACK);

                canvas.drawPaint(paint);

                paint.setColor(colors.getBackGround());

                canvas.drawCircle(middleX, middleY, radius, paint);

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

                float hourCoef = (float) Math.PI * 2 / 12 * (hour - 3);
                float minCoef = (float) Math.PI * 2 / 60 * (min - 15);

                paint.setColor(colors.getMinHand());
                paint.setStrokeWidth(10f);

                int len = 300;
                float x = middleX + len * (float) Math.cos(minCoef);
                float y = middleY + len * (float) Math.sin(minCoef);
                canvas.drawLine(middleX, middleY, x, y, paint);

                paint.setColor(colors.getHourHand());
                paint.setStrokeWidth(15f);

                len = 200;
                x = middleX + len * (float) Math.cos(hourCoef);
                y = middleY + len * (float) Math.sin(hourCoef);
                canvas.drawLine(middleX, middleY, x, y, paint);
                String time = String.format("%2s", hour).replace(' ', '0') + ":"
                        + String.format("%2s", min).replace(' ', '0');

                paint.setColor(Color.WHITE);
                paint.setTextSize(70);
                paint.setTextAlign(Paint.Align.CENTER);

                x = middleX;
                y = middleY + radius + 125;

                canvas.drawText(time, x, y, paint);

                paint.setColor(colors.getMinHand());

                canvas.drawCircle(middleX, middleY, centerRadius, paint);

                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void pause() {
        boolean retry = true;
        running = false;
        while (retry) {
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

}

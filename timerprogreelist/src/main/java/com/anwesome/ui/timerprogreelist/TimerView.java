package com.anwesome.ui.timerprogreelist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by anweshmishra on 16/06/17.
 */

public class TimerView extends View {
    private int timeLimit,time = 0,w,h;
    private Timer timer;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public TimerView(Context context,int timeLimit) {
        super(context);
        this.timeLimit = timeLimit;
    }
    public void onDraw(Canvas canvas) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            timer = new Timer();
        }
        canvas.drawColor(Color.parseColor("#1A237E"));
        timer.draw(canvas,Math.max(w,h)/6);
        time++;
    }
    public void update(float factor) {
        if(timer != null) {
            timer.update(factor);
        }
        postInvalidate();
    }
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            
        }
        return true;
    }
    private class Timer {
        private float deg = 0;
        public void draw(Canvas canvas,float r) {
            paint.setStrokeWidth(r/25);
            paint.setColor(Color.parseColor("#0288D1"));
            canvas.save();
            canvas.translate(w/2,h/2);
            Path path = new Path();
            for(int i=0;i<=deg;i++) {
                float x = (float)(r*Math.cos((i-90)*Math.PI/180)),y = (float)(r*Math.sin((i-90)*Math.PI/180));
                if(i == 0) {
                    path.moveTo(x, y);
                }
                else {
                    path.lineTo(x,y);
                }
                if(i == deg && deg!=360) {
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(x,y,r/15,paint);
                }
            }
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path,paint);
            canvas.restore();
        }
        public void update(float factor) {
            deg = 360*factor;
        }
    }
}

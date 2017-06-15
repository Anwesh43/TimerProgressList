package com.anwesome.ui.timerprogreelist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by anweshmishra on 16/06/17.
 */

public class TimerView extends View {
    private int timeLimit,time = 0,w,h;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public TimerView(Context context,int timeLimit) {
        super(context);
        this.timeLimit = timeLimit;
    }
    public void onDraw(Canvas canvas) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
        }
        canvas.drawColor(Color.parseColor("#1A237E"));
        time++;
    }
    public void update(float factor) {
        postInvalidate();
    }
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {

        }
        return true;
    }
}

package com.anwesome.ui.timerprogreelist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by anweshmishra on 16/06/17.
 */

public class ProgressView extends View{
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int maxTimer,completedTimer = 0;
    public ProgressView(Context context,int maxTimer) {
        super(context);
        this.maxTimer = maxTimer;
    }
    public void onDraw(Canvas canvas) {
        int w = canvas.getWidth(),h  = canvas.getHeight();
        paint.setStrokeWidth(h/4);
        paint.setColor(Color.GRAY);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(w/20,h/2,w-w/20,h/2,paint);
        paint.setColor(Color.GREEN);
        canvas.drawLine(w/20,h/2,w/20+(9*w/10)*((completedTimer)/maxTimer),h/2,paint);
    }
    public void updateCompletedTimer() {
        if(completedTimer < maxTimer) {
            completedTimer++;
            postInvalidate();
        }
    }
}

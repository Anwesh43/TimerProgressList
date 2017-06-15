package com.anwesome.ui.timerprogreelist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by anweshmishra on 16/06/17.
 */

public class TimerView extends View {
    private int timeLimit,time = 0,w,h;
    private Timer timer;
    private AnimationHandler animationHandler;
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
            animationHandler = new AnimationHandler();
        }
        canvas.drawColor(Color.parseColor("#1A237E"));
        timer.draw(canvas,Math.max(w,h)/6);
        if(time == 0) {
            animationHandler.start();
        }
        time++;
    }
    public void update(float factor) {
        if(timer != null) {
            timer.update(factor);
        }
        postInvalidate();
    }
    private class Timer {
        private float deg = 0;
        public void draw(Canvas canvas,float r) {
            paint.setStrokeWidth(r/25);
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
                if(Math.floor(deg) == i && deg!=360) {
                    paint.setColor(Color.parseColor("#0288D1"));
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(x,y,r/7,paint);
                    paint.setColor(Color.WHITE);
                    canvas.drawCircle(x,y,r/12,paint);
                }
            }
            paint.setColor(Color.parseColor("#0288D1"));
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path,paint);
            canvas.restore();
        }
        public void update(float factor) {
            deg = 360*factor;
        }
    }
    private class AnimationHandler extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {
        private ValueAnimator startAnim = ValueAnimator.ofFloat(0,1);
        public AnimationHandler() {
            startAnim.setDuration(timeLimit);
            startAnim.addUpdateListener(this);
            startAnim.addListener(this);
        }
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            update((float)valueAnimator.getAnimatedValue());
        }
        public void onAnimationEnd(Animator animator) {

        }
        public void start() {
            startAnim.start();
        }
    }
    public static void create(Activity activity,int timeLimit,int size) {
        TimerView timerView = new TimerView(activity,timeLimit);
        activity.addContentView(timerView,new ViewGroup.LayoutParams(size,size/2));
    }
}

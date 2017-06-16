package com.anwesome.ui.timerprogreelist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 16/06/17.
 */

public class TimerProgressList {
    private Activity activity;
    private TimerHandler timerHandler;
    private RelativeLayout mainContainer;
    private ScrollView scrollView;
    private TimerList timerList;
    private boolean isShown = false;
    private int w,h,n = 0;
    private ProgressView progressView;
    public TimerProgressList(Activity activity) {
        this.activity = activity;
        this.mainContainer = new RelativeLayout(activity);
        this.scrollView = new ScrollView(activity);
        initDimension(activity);
        this.timerList = new TimerList(activity);
        this.scrollView.addView(timerList,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.timerHandler = new TimerHandler();
    }
    public void initDimension(Context context) {
        DisplayManager displayManager = (DisplayManager)context.getSystemService(Context.DISPLAY_SERVICE);
        Display display = displayManager.getDisplay(0);
        Point size = new Point();
        display.getRealSize(size);
        w = size.x;
        h = size.y;
    }
    public void addTimer(int duration) {
        if(!isShown) {
            timerList.addTimer(duration);
            n++;
        }
    }
    public void show() {
        if(!isShown) {
            progressView = new ProgressView(activity,n);
            mainContainer.addView(progressView,new ViewGroup.LayoutParams(w,h/30));
            progressView.setX(0);
            progressView.setY(h/30);
            mainContainer.addView(scrollView,new ViewGroup.LayoutParams(w,9*h/10));
            scrollView.setY(h/12);
            activity.setContentView(mainContainer);
            isShown = true;
            timerHandler.start();
        }
    }
    private class TimerList extends ViewGroup{
        public TimerList(Context context) {
            super(context);
        }
        public void onMeasure(int wspec,int hspec) {
            int hMax = h/30;
            for(int i=0;i<getChildCount();i++) {
                View child = getChildAt(i);
                measureChild(child,wspec,hspec);
                hMax += (child.getMeasuredHeight()+h/30);
            }
            setMeasuredDimension(w,hMax);
        }
        public void onLayout(boolean reloaded,int a,int b,int wa,int has){
            int x = w/20,y = h/30;
            for(int i=0;i<getChildCount();i++) {
                View child = getChildAt(i);
                child.layout(x,y,x+child.getMeasuredWidth(),y+child.getMeasuredHeight());
                y += (child.getMeasuredHeight()+h/30);
            }
        }
        public void addTimer(int duration) {
            TimerView timerView = new TimerView(getContext(),duration);
            addView(timerView,new LayoutParams(9*w/10,9*w/20));
            if(timerHandler != null) {
                timerHandler.addTimer(timerView);
            }
            requestLayout();
        }
     }
    private class TimerHandler {
        private ConcurrentLinkedQueue<TimerView> timers = new ConcurrentLinkedQueue<>();
        private TimerView currTimer;
        public void start() {
            if(currTimer != null) {
                currTimer.start();
            }
        }
        public TimerView getFirstTimer() {
            for(TimerView timer:timers) {
                return timer;
            }
            return null;
        }
        public void addTimer(TimerView timerView) {
            if(currTimer == null) {
                currTimer = timerView;
            }
            timers.add(timerView);
            timerView.setOnAnimationEndListener(new TimerView.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd() {
                    if(currTimer != null) {
                        progressView.updateCompletedTimer();
                        timers.remove(currTimer);
                        currTimer = getFirstTimer();
                        start();
                    }
                }
            });
        }
    }
}

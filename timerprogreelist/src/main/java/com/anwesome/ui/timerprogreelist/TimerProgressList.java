package com.anwesome.ui.timerprogreelist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    private void hideActionBar() {
        if(activity instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity)activity).getSupportActionBar();
            if(actionBar != null) {
                actionBar.hide();
            }
        }
        else {
            android.app.ActionBar actionBar = activity.getActionBar();
            if(actionBar != null) {
                actionBar.hide();
            }
        }
    }
    private void adjustScreen() {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    public void addTimer(int duration,OnTimerCompletionListener onTimerCompletionListener) {
        if(!isShown) {
            timerList.addTimer(duration,onTimerCompletionListener);
            n++;
        }
    }
    public void show() {
        if(!isShown) {
            hideActionBar();
            adjustScreen();
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
            setMeasuredDimension(w,hMax+h/15);
        }
        public void onLayout(boolean reloaded,int a,int b,int wa,int has){
            int x = w/20,y = h/30;
            for(int i=0;i<getChildCount();i++) {
                View child = getChildAt(i);
                child.layout(x,y,x+child.getMeasuredWidth(),y+child.getMeasuredHeight());
                y += (child.getMeasuredHeight()+h/30);
            }
        }
        public void addTimer(int duration,OnTimerCompletionListener onTimerCompletionListener) {
            TimerView timerView = new TimerView(getContext(),duration);
            timerView.setOnTimerCompletionListener(onTimerCompletionListener);
            addView(timerView,new LayoutParams(9*w/10,9*w/20));
            if(timerHandler != null) {
                timerHandler.addTimer(timerView);
            }
            requestLayout();
        }
     }
    private class TimerHandler {
        private ScrollAnimationHandler scrollAnimationHandler;
        private ConcurrentLinkedQueue<TimerView> timers = new ConcurrentLinkedQueue<>();
        private TimerView currTimer;
        public TimerHandler() {
            scrollAnimationHandler = new ScrollAnimationHandler();
        }
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
                        currTimer.handleCompletion();
                        currTimer = getFirstTimer();
                        if(scrollAnimationHandler!=null) {
                            scrollAnimationHandler.start();
                        }
                    }
                }
            });
        }
        private class ScrollAnimationHandler extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {
            private ValueAnimator animator;
            private int y = 0;
            public ScrollAnimationHandler() {
                animator = ValueAnimator.ofInt(0,w/2+h/30);
                animator.setDuration(500);
                animator.addUpdateListener(this);
                animator.addListener(this);
            }
            public void onAnimationEnd(Animator animator) {
                if(timerHandler != null) {
                    timerHandler.start();
                }
            }
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                scrollView.scrollTo(0,y+(int)valueAnimator.getAnimatedValue());
            }
            public void start() {
                y = scrollView.getScrollY();
                animator.start();
            }
        }
    }
}

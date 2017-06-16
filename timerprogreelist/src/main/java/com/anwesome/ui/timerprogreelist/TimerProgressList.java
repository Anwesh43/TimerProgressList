package com.anwesome.ui.timerprogreelist;

import android.app.Activity;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.List;

/**
 * Created by anweshmishra on 16/06/17.
 */

public class TimerProgressList {
    private Activity activity;
    private RelativeLayout mainContainer;
    private ScrollView scrollView;
    private boolean isShown = false;
    public TimerProgressList(Activity activity) {
        this.activity = activity;
        this.mainContainer = new RelativeLayout(activity);
        this.scrollView = new ScrollView(activity);
    }
    public void addTimer(int duration) {
        if(!isShown) {

        }
    }
    public void show() {
        if(!isShown) {
            isShown = true;
        }
    }
}

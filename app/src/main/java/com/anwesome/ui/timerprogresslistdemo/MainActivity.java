package com.anwesome.ui.timerprogresslistdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anwesome.ui.timerprogreelist.TimerProgressList;
import com.anwesome.ui.timerprogreelist.TimerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimerProgressList timerProgressList = new TimerProgressList(this);
        for(int i=0;i<10;i++) {
            timerProgressList.addTimer(4000);
        }
        timerProgressList.show();
    }
}

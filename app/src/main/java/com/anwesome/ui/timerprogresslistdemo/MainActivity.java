package com.anwesome.ui.timerprogresslistdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anwesome.ui.timerprogreelist.OnTimerCompletionListener;
import com.anwesome.ui.timerprogreelist.TimerProgressList;
import com.anwesome.ui.timerprogreelist.TimerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimerProgressList timerProgressList = new TimerProgressList(this);
        for(int i=0;i<10;i++) {
            final int index = i;
            timerProgressList.addTimer(4000, new OnTimerCompletionListener() {
                @Override
                public void onTimerCompletion() {
                    Toast.makeText(MainActivity.this, String.format("%d timer completed",index+1), Toast.LENGTH_SHORT).show();
                }
            });
        }
        timerProgressList.show();
    }
}

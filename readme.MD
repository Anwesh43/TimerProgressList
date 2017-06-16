## TimerProgressList

### Android ui module to add a list of timers and tracking their progress

### Usage

#### Creating the list

```
    TimerProgressList timerProgressList = new TimerProgressList(this);
```

#### Adding a timer of a certain duration and OnCompletionListener

```
    timerProgressList.addTimer(duration,new OnTimerCompletionListener(){
        public void onTimerCompletion() {

        }
    })
```

#### Showing the list

```
    timerProgressList.show();
```

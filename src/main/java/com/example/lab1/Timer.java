
package com.example.lab1;

import javafx.animation.AnimationTimer;

public class Timer extends AnimationTimer
{
    private long time;
    private TimerSubscriber timerSubscriber;
    
    public Timer(final TimerSubscriber timerSubscriber) {
        this.timerSubscriber = timerSubscriber;
    }
    
    @Override
    public void handle(final long now) {
        if (this.time != 0L) {
            final long dns = now - this.time;
            this.timerSubscriber.update(dns);
        }
        this.time = now;
    }
    
    public interface TimerSubscriber
    {
        void update(final long p0);
    }
}

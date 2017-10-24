package com.bmeglathery.myfirstpet;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.Toast;

import static java.security.AccessController.getContext;

/**
 * This timer will handle the reduction and increase of
 * stats over time.
 *
 * Created by Brandon on 10/18/2017.
 */
public class PetTimer extends CountDownTimer {

    private long LIFETIME = 0;
    private Pet pet;

    /**
     * The duration of the timer should correspond to the total possible length
     * of the life of the pet - externalize this number and pass it in as a constant.
     *
     * @param millisInFuture - the duration in milliseconds of the <code>PetTimer</code>
     * @param countDownInterval - the interval between <code>onTick</code> events in milliseconds
     */
    public PetTimer(long millisInFuture, long countDownInterval, Pet p) {
        super(millisInFuture, countDownInterval);
        LIFETIME = millisInFuture;
        pet = p;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if(pet.changeStats(1))
            flagDead();
    }

    @Override
    public void onFinish() {

    }
    
    public void flagDead(){

    }
}

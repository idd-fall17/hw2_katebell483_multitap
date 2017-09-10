package com.example.androidthings.myproject;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import android.os.Handler;

/**
 * Template for IDD Fall 2017 HW2 (text entry device)
 * Created by bjoern on 9/5/17.
 */

public class Hw2TemplateApp extends SimplePicoPro {

    // global vars
    int counter = 0;
    char currChar;
    Gpio prev_pin;
    boolean still_triggering;

    // handler for printing chars to screen with delay
    Handler timerHandler1 = new Handler();
    Runnable timerRunnable1 = new Runnable() {
        @Override
        public void run() {
            printCharacterToScreen(currChar);
            counter = 0;
        }
    };

    // handler for debouncing effect
    Handler timerHandler2 = new Handler();
    Runnable timerRunnable2 = new Runnable() {
        @Override
        public void run() {
            still_triggering = false;
        }
    };

    @Override
    public void setup() {

        //set two GPIOs to each input
        pinMode(GPIO_128,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_128,Gpio.EDGE_BOTH);

        pinMode(GPIO_39,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_39,Gpio.EDGE_BOTH);

        pinMode(GPIO_37,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_37,Gpio.EDGE_BOTH);

        pinMode(GPIO_35,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_35,Gpio.EDGE_BOTH);

        pinMode(GPIO_34,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_34,Gpio.EDGE_BOTH);

        pinMode(GPIO_33,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_33,Gpio.EDGE_BOTH);

        pinMode(GPIO_32,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_32,Gpio.EDGE_BOTH);

        pinMode(GPIO_10,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_10,Gpio.EDGE_BOTH);

        pinMode(GPIO_172,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_172,Gpio.EDGE_BOTH);

        pinMode(GPIO_173,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_173,Gpio.EDGE_BOTH);
    }

    @Override
    public void loop() {
        //nothing to do here

    }

    @Override
    void digitalEdgeEvent(Gpio pin, boolean value) {
        println("digitalEdgeEvent"+pin+", "+value);

        // reset index if new button
        if(pin != prev_pin) {
            counter = 0;
        }

        if(value == LOW) return;

        // watch for bouncing
        if(still_triggering == true) return;
        still_triggering = true;
        timerHandler2.postDelayed(timerRunnable2, 100);

        // track current pin
        prev_pin = pin;

        // track next letter
        int curIdx3;
        int curIdx4;

        // reset current running timeout
        timerHandler1.removeCallbacks(timerRunnable1);

        // for keys with 3 letters
        curIdx3 = counter % 3;
        println("" + curIdx3 + "");

        // for keys with 4
        curIdx4 = counter % 4;

        // increment counter
        counter++;

        // SPACE
        if (pin==GPIO_33) {
            currChar = ' ';
        }

        // ABC
        else if (pin==GPIO_128) {
            char[] letters = {'a', 'b', 'c'};
            currChar = letters[curIdx3];

        // DEF
        } else if (pin==GPIO_39) {
            char[] letters = {'d','e','f'};
            currChar = letters[curIdx3];

        // GHI
        } else if (pin==GPIO_10) {
            char[] letters = {'g','h','i'};
            currChar = letters[curIdx3];

        // JKL
        } else if (pin==GPIO_37) {
            char[] letters = {'j','k','l'};
            currChar = letters[curIdx3];

        // MNO
        } else if (pin==GPIO_35) {
            char[] letters = {'m', 'n', 'o'};
            currChar = letters[curIdx3];

        // PQRS
        } else if (pin==GPIO_32) {
            char[] letters = {'p', 'q', 'r', 's'};
            currChar = letters[curIdx4];

        // TUV
        } else if (pin==GPIO_172) {
            char[] letters = {'t','u','v'};
            currChar = letters[curIdx3];

        // WXYZ
        } else if (pin==GPIO_34) {
            char[] letters = {'w', 'x', 'y', 'z'};
            currChar = letters[curIdx4];
        }

        // post char to screen with delay
        timerHandler1.postDelayed(timerRunnable1, 800);
    }

    void testInputs(Gpio pin, boolean value) {
        if((pin==GPIO_128 || pin==GPIO_39 || pin==GPIO_37 || pin==GPIO_35 || pin==GPIO_34 || pin==GPIO_33 || pin==GPIO_32 || pin==GPIO_10 || pin==GPIO_172 || pin==GPIO_173) && value==HIGH) {
            printCharacterToScreen('a');
        }


    }
}

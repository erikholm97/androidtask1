package com.example.task1;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
//Ref: https://www.youtube.com/watch?v=zmjfAcnosS0 Coding in Flow
public class MainActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 300000; // 5 min timer.

    private TextView countdownButton;
    private Button startPauseButton;
    private Button resetButton;
    private Button GoToActivit2;

    private CountDownTimer mCountDownTimer;

    private boolean runningTimer;

    private long timeLeftInMillisSeconds = START_TIME_IN_MILLIS;
    private long mEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoToActivit2 =  (Button) findViewById(R.id.goToActivity2);

        GoToActivit2.setOnClickListener(new View.OnClickListener() { // An onclickListener if the use presses the button thats binded to GotoActivity 2 and function starts.
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });



        countdownButton = findViewById(R.id.text_view_countdown);

        startPauseButton = findViewById(R.id.button_start_pause);
        resetButton = findViewById(R.id.button_reset);

        startPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Onclick listener for the start and pause button.
                if (runningTimer) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // onclicklistener that on press starts the function reset timer.
                resetTimer();
            }
        });

        updateCountDownText();

    }

    private void startTimer() { // Function that starts the timer.
        mEndTime = System.currentTimeMillis() + timeLeftInMillisSeconds; //

        mCountDownTimer = new CountDownTimer(timeLeftInMillisSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillisSeconds = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() { // Turns of the timer when the clock is finite.
                runningTimer = false;

            }
        }.start();

        runningTimer = true;
        startPauseButton.setText("pause");

    }

    private void pauseTimer() { // Function that pauses the timer.
        mCountDownTimer.cancel();
        startPauseButton.setText("Start");
        runningTimer = false;

    }

    private void resetTimer() { // Function that resets the timer.
        timeLeftInMillisSeconds = START_TIME_IN_MILLIS;
        updateCountDownText();

    }

    private void updateCountDownText() { // Function that reverts the countdown clock back to its original time when button is pressed.
        int minutes = (int) (timeLeftInMillisSeconds / 1000) / 60; // Minutes in the timer
        int seconds = (int) (timeLeftInMillisSeconds / 1000) % 60; // Seconds in the timer

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds); // saving the timeLeft to a string to display in the timer clock.

        countdownButton.setText(timeLeftFormatted); // Sets the text of the timer. // Sets the text in text_view_countdown.
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft", timeLeftInMillisSeconds);
        outState.putBoolean("timerRunning", runningTimer);
        outState.putLong("endTime", mEndTime);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        timeLeftInMillisSeconds = savedInstanceState.getLong("millisLeft");
        runningTimer = savedInstanceState.getBoolean("timerRunning");
        updateCountDownText();

        if (runningTimer) {
            mEndTime = savedInstanceState.getLong("endTime");
            timeLeftInMillisSeconds = mEndTime - System.currentTimeMillis();
            startTimer();
        }
    }
    public void openActivity2(){ // Function that creats an intent of Activity2.class that takes the user to Activity2 page.
        Intent intent = new Intent(this, Activity2.class);{
        startActivity(intent);
        }
    }


}
// Source https://www.youtube.com/watch?v=MDuGwI6P-X8 Coding In Flow YouTube.
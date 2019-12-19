package com.example.task1;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 300000;

    private TextView countdownButton;
    private Button startPauseButton;
    private Button resetButton;

    private CountDownTimer mCountDownTimer;

    private boolean runningTimer;

    private long timeLeftInMillisSeconds = START_TIME_IN_MILLIS;
    private long mEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + timeLeftInMillisSeconds;

        mCountDownTimer = new CountDownTimer(timeLeftInMillisSeconds, 1000) { //
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillisSeconds = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                runningTimer = false;
                updateButtons();
            }
        }.start();

        runningTimer = true;
        updateButtons();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        runningTimer = false;
        updateButtons();
    }

    private void resetTimer() {
        timeLeftInMillisSeconds = START_TIME_IN_MILLIS;
        updateCountDownText();
        updateButtons();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillisSeconds / 1000) / 60;
        int seconds = (int) (timeLeftInMillisSeconds / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        countdownButton.setText(timeLeftFormatted);
    }

    private void updateButtons() {
        if (runningTimer) {
            resetButton.setVisibility(View.INVISIBLE);
            startPauseButton.setText("Pause");
        } else {
            startPauseButton.setText("Start");

            if (timeLeftInMillisSeconds < 1000) {
                startPauseButton.setVisibility(View.INVISIBLE);
            } else {
                startPauseButton.setVisibility(View.VISIBLE);
            }

            if (timeLeftInMillisSeconds < START_TIME_IN_MILLIS) {
                resetButton.setVisibility(View.VISIBLE);
            } else {
                resetButton.setVisibility(View.INVISIBLE);
            }
        }
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
        updateButtons();

        if (runningTimer) {
            mEndTime = savedInstanceState.getLong("endTime");
            timeLeftInMillisSeconds = mEndTime - System.currentTimeMillis();
            startTimer();
        }
    }
}
// Source https://www.youtube.com/watch?v=MDuGwI6P-X8 Coding In Flow YouTube.
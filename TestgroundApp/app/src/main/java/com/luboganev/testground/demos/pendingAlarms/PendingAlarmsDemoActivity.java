package com.luboganev.testground.demos.pendingAlarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.luboganev.testground.BuildConfig;
import com.luboganev.testground.R;

import java.util.Calendar;

public class PendingAlarmsDemoActivity extends AppCompatActivity {

    private EditText secondsCodeEditTextView;
    private EditText messageExtraEditTextView;
    private EditText requestCodeEditTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendingalarmsdemo);
        secondsCodeEditTextView = (EditText) findViewById(R.id.editTextTime);
        messageExtraEditTextView = (EditText) findViewById(R.id.editTextMessage);
        requestCodeEditTextView = (EditText) findViewById(R.id.editTextRq);
    }

    public void onScheduleButtonClick(View v) {
        try {
            scheduleMessage(messageExtraEditTextView.getText().toString(),
                    Integer.parseInt(requestCodeEditTextView.getText().toString()),
                    Integer.parseInt(secondsCodeEditTextView.getText().toString()));
        } catch (Exception e) {
            Toast.makeText(this, "Input is wrong! " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onCheckIntentExistsButtonClick(View v) {
        try {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, Integer.parseInt(requestCodeEditTextView.getText().toString()),
                    IntentMessageHelper.buildDisplayMessageIntent(messageExtraEditTextView.getText().toString()), PendingIntent.FLAG_NO_CREATE);

            if (pendingIntent == null) {
                Toast.makeText(this, requestCodeEditTextView.getText().toString() + " does not exist", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, requestCodeEditTextView.getText().toString() + " exists", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Input is wrong! " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onCancelAlarmButtonClick(View v) {
        try {
            cancelMessage(messageExtraEditTextView.getText().toString(), Integer.parseInt(requestCodeEditTextView.getText().toString()));
        } catch (Exception e) {
            Toast.makeText(this, "Input is wrong! " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onCauseRuntimeExceptionButtonClick(View v) {
        throw new RuntimeException("Random crash");
    }

    public void onCauseANRButtonClick(View v) {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void scheduleMessage(String message, int requestCode, int seconds) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, seconds);

        // Generate a broadcast intent for showing a local notification and schedule it
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, requestCode,
                IntentMessageHelper.buildDisplayMessageIntent(message), PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), broadcast);
    }

    private void cancelMessage(String message, int requestCode) {
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, requestCode,
                IntentMessageHelper.buildDisplayMessageIntent(message), PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(broadcast);
    }

    public static class IntentMessageHelper {
        private static final String EXTRA_MESSAGE = "message";

        public static Intent buildDisplayMessageIntent(@NonNull String message) {
            Intent notificationIntent = new Intent(MessageBroadcastReceiver.INTENT_ACTION_DISPLAY_MESSAGE);
            notificationIntent.addCategory("android.intent.category.DEFAULT");
            notificationIntent.putExtra(EXTRA_MESSAGE, message);
            return notificationIntent;

        }

        public static String getMessage(Intent intent) {
            return intent.getStringExtra(EXTRA_MESSAGE);
        }
    }

    public static class MessageBroadcastReceiver extends BroadcastReceiver {
        public static final String INTENT_ACTION_DISPLAY_MESSAGE = BuildConfig.APPLICATION_ID + ".DISPLAY_MESSAGE";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(INTENT_ACTION_DISPLAY_MESSAGE)) {

                String message = IntentMessageHelper.getMessage(intent);
                if (message == null) {
                    Toast.makeText(context, "Message is null", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(context, "Message: " + message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

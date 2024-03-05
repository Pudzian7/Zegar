package com.example.zegar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextClock textClock;
    private Button buttonSetAlarm;
    private TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textClock = findViewById(R.id.textClock);
        buttonSetAlarm = findViewById(R.id.buttonSetAlarm);
        timePicker = findViewById(R.id.timePicker);

        buttonSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });
    }

    private void setAlarm() {
        Intent intent = new Intent(this, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Pobierz wybraną godzinę i minutę z TimePicker
        int hour = timePicker.getCurrentHour(); // Uzyskaj wybraną godzinę
        int minute = timePicker.getCurrentMinute(); // Uzyskaj wybraną minutę

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // Ustaw godzinę i minutę alarmu
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // Sprawdź, czy wybrana godzina jest wcześniejsza niż obecna godzina
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            // Jeśli tak, ustaw alarm na następny dzień
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        // Wyświetl komunikat potwierdzający ustawienie alarmu
        Toast.makeText(this, "Alarm ustawiony na " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
    }

}

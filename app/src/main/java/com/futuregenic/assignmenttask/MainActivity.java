package com.futuregenic.assignmenttask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private long start, lastLogin, lastLogout;
    String durationLogin;
    long elapsedTime;
    Button logoutBtn;

    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    TextView txtIn, txtOut, txtDiff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutBtn = findViewById(R.id.logoutBtn);
        txtIn = findViewById(R.id.txtIn);
        txtOut = findViewById(R.id.txtOut);
        txtDiff = findViewById(R.id.txtDiff);

        prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        start = prefs.getLong("loginTime",0);
        lastLogin = prefs.getLong("lastLogin",0);
        lastLogout = prefs.getLong("lastLogout",0);
        durationLogin = prefs.getString("duration", "");

        if (lastLogin != 0){
            txtIn.setText("Your Last Login Time Is: "+getDate(lastLogin, "hh:mm a"));
        } if (lastLogout != 0){
            txtOut.setText("Your Last Logout Time Is: "+getDate(lastLogout, "hh:mm a"));
        } if (!durationLogin.equals("") || !durationLogin.equals(null)){
            txtDiff.setText("Session Duration : "+durationLogin);
        }

        editor = getSharedPreferences("MyPrefsFile", MODE_PRIVATE).edit();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putLong("lastLogin",start);
                editor.putLong("lastLogout",System.currentTimeMillis());
                getDifference();
                editor.remove("loginTime");
                editor.remove("status");
                editor.apply();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finishAffinity();
            }
        });
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    private void getDifference(){
        int days, hours, min;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
//        Date date1 = simpleDateFormat.parse("08:00 AM");
//        Date date2 = simpleDateFormat.parse("04:00 PM");
        long difference = System.currentTimeMillis() - start;
        days = (int) (difference / (1000*60*60*24));
        hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
        min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
        hours = (hours < 0 ? -hours : hours);

        editor.putString("duration", "Hours "+ hours+" Minutes "+min);
        Log.i("======= Hours"," :: "+min);
    }
}
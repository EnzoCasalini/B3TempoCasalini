package com.example.b3tempocasalini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.b3tempocasalini.databinding.ActivityMainBinding;

import java.net.HttpURLConnection;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int ALARM_MANAGER_REQUEST_CODE = 2023;
    public static IEdfApi edfApi;
    ActivityMainBinding binding;

    private static final String CHANNEL_ID = "notif_channel_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Init Views
        binding.historyBt.setOnClickListener(this);

        // Create notification channel
        createNotificationChannel();


        // Init Retrofit client
        Retrofit retrofitClient = ApiClient.get();
        if (retrofitClient != null) {
            edfApi = retrofitClient.create(IEdfApi.class);
        } else {
            Log.e(LOG_TAG, "Unable to initialize Retrofit client");
            finish();
        }

        // Call the API.
        apiTempoDaysLeft();
        apiTempoDaysColor();
    }

    // Executed when activity comes back to "foreground".
    @Override
    protected void onResume(){
        super.onResume();
        // Call the API.
        apiTempoDaysLeft();
        apiTempoDaysColor();
    }


//    public void showHistory(View view) {
//
//    }


    // -- Methods to call the API -- \\

    private void apiTempoDaysLeft() {
        Call<TempoDaysLeft> call = edfApi.getTempoDaysLeft(IEdfApi.EDF_TEMPO_API_ALERT_TYPE);

        call.enqueue(new Callback<TempoDaysLeft>() {
            @Override
            public void onResponse(Call<TempoDaysLeft> call, Response<TempoDaysLeft> response) {
                Log.d("TAG", response.code() + "");

                TempoDaysLeft tdl = response.body();
                if (response.code() == HttpURLConnection.HTTP_OK && tdl != null) {
                    binding.blueDaysTv.setText(tdl.getParamNbJBleu().toString());
                    binding.whiteDaysTv.setText(tdl.getParamNbJBlanc().toString());
                    binding.redDaysTv.setText(tdl.getParamNbJRouge().toString());
                }
                else {
                    Log.w(LOG_TAG, "call to getTempoDaysLeft() failed with error code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<TempoDaysLeft> call, Throwable t) {
                Log.e(LOG_TAG, "Call to getTemposDaysLeft() failed");
            }
        });
    }

    private void apiTempoDaysColor() {
        Call<TempoDaysColor> call2 = edfApi.getTempoDaysColor(Tools.getNowDate("yyyy-MM-dd"), IEdfApi.EDF_TEMPO_API_ALERT_TYPE);

        call2.enqueue(new Callback<TempoDaysColor>() {
            @Override
            public void onResponse(Call<TempoDaysColor> call, Response<TempoDaysColor> response) {
                Log.d("TAG", response.code() + "");

                TempoDaysColor tdc = response.body();
                if (response.code() == HttpURLConnection.HTTP_OK && tdc != null) {
                    Log.d(LOG_TAG, "color of the day = " + tdc.getCouleurJourJ().toString());
                    Log.d(LOG_TAG, "color of the next day = " + tdc.getCouleurJourJ1().toString());
                    binding.todayDcv.setDayCircleColor(tdc.getCouleurJourJ());
                    binding.tomorrowDcv.setDayCircleColor(tdc.getCouleurJourJ1());
                    binding.todayDcv.setCaptionText(getString(R.string.dcv_today_tx, getString(tdc.getCouleurJourJ().getStringResId())));
                    binding.tomorrowDcv.setCaptionText(getString(R.string.dcv_tomorrow_tx, getString(tdc.getCouleurJourJ1().getStringResId())));
                    checkColor4Notif(tdc.getCouleurJourJ1());
                }
                else {
                    Log.w(LOG_TAG, "call to getTempoDaysColor() failed with error code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<TempoDaysColor> call, Throwable t) {
                Log.e(LOG_TAG, "Call to getTempoDaysColor() failed");
            }
        });
    }

    // ----------------------------- \\


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void checkColor4Notif(TempoColor nextDayColor) {
        if (nextDayColor == TempoColor.RED || nextDayColor == TempoColor.WHITE)
        {
//            Intent intent = new Intent(this, AlertDetails.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getString(R.string.notif_title))
                    .setContentText(getString(R.string.notif_text, getString(nextDayColor.getStringResId())))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    // .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(Tools.getNextNotifId(), builder.build());
        }
    }


    private void initAlarmManager() {

        // Create a pending intent.
        Intent intent = new Intent(this, TempoAlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(
                this,
                ALARM_MANAGER_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);


        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants--in this case, AlarmManager.INTERVAL_DAY.
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);

    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(this, HistoryActivity.class);
        startActivity(intent);
    }
}
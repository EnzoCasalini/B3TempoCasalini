package com.example.b3tempocasalini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.b3tempocasalini.databinding.ActivityMainBinding;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static IEdfApi edfApi;
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Init Views
        binding.historyBt.setOnClickListener(this);


        // Init Retrofit client
        Retrofit retrofitClient = ApiClient.get();
        if (retrofitClient != null) {
            edfApi = retrofitClient.create(IEdfApi.class);
        } else {
            Log.e(LOG_TAG, "Unable to initialize Retrofit client");
            finish();
        }

        // Create call
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

        Call<TempoDaysColor> call2 = edfApi.getTempoDaysColor("2022-12-12", IEdfApi.EDF_TEMPO_API_ALERT_TYPE);

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

//    public void showHistory(View view) {
//
//    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(this, HistoryActivity.class);
        startActivity(intent);
    }
}
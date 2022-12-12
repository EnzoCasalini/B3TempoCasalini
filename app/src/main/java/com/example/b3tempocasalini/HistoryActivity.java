package com.example.b3tempocasalini;

import static com.example.b3tempocasalini.MainActivity.edfApi;

import androidx.appcompat.app.AppCompatActivity;
import com.example.b3tempocasalini.databinding.ActivityHistoryBinding;

import android.os.Bundle;
import android.util.Log;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HistoryActivity extends AppCompatActivity {
    private static final String LOG_TAG = HistoryActivity.class.getSimpleName();
    ActivityHistoryBinding binding;

    // Data model
    List<TempoDate> tempoDates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (edfApi != null)
        {
            // Create call
            Call<TempoHistory> call = edfApi.getTempoHistory("2021", "2022");

            call.enqueue(new Callback<TempoHistory>() {
                @Override
                public void onResponse(Call<TempoHistory> call, Response<TempoHistory> response) {
                    tempoDates.clear();
                    if (response.code() == HttpURLConnection.HTTP_OK && response.body() != null)
                    {
                        tempoDates.addAll(response.body().getTempoDates());
                        Log.d(LOG_TAG, "nb elements = " + tempoDates.size());
                    }
                }

                @Override
                public void onFailure(Call<TempoHistory> call, Throwable t) {

                }
            });
        }
    }
}
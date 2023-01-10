package com.example.b3tempocasalini;

import static com.example.b3tempocasalini.MainActivity.edfApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.b3tempocasalini.databinding.ActivityHistory2Binding;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity2 extends AppCompatActivity {
    private static final String LOG_TAG = HistoryActivity.class.getSimpleName();
    ActivityHistory2Binding binding;

    // Data model
    List<TempoDate> tempoDates = new ArrayList<>();

    TempoDateAdapter2 tempoDateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistory2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Init recycler view
        binding.tempoHistory2Rv.setHasFixedSize(true); // On dit au recycler view que tous les éléments ont la même taille. Le fait de le mettre à true veut dire que : les changements ne pourront pas affecter les changements de la recycler view.
        binding.tempoHistory2Rv.setLayoutManager(new LinearLayoutManager(this)); // LinearLayoutManager qui permet d'arranger les élements sous forme de liste.
        tempoDateAdapter = new TempoDateAdapter2(tempoDates, this);
        binding.tempoHistory2Rv.setAdapter(tempoDateAdapter);

        if (edfApi != null)
        {
            // Call the API.
            apiTempoHistory();
        }
    }

    private void apiTempoHistory() {
        String yearNow = Tools.getNowDate("yyyy");
        String yearBefore = "";

        try {
            yearBefore = String.valueOf(Integer.parseInt(yearNow) - 1);
        }
        catch (NumberFormatException e) {
            Log.e(LOG_TAG, e.getMessage());
        }


        // On affiche la progressBar.
        binding.progressBarHistory2.setVisibility(View.VISIBLE);

        Call<TempoHistory> call = edfApi.getTempoHistory(yearBefore, yearNow);

        call.enqueue(new Callback<TempoHistory>() {
            @Override
            public void onResponse(Call<TempoHistory> call, Response<TempoHistory> response) {
                tempoDates.clear();
                if (response.code() == HttpURLConnection.HTTP_OK && response.body() != null)
                {
                    tempoDates.addAll(response.body().getTempoDates());
                    Log.d(LOG_TAG, "nb elements = " + tempoDates.size());
                }
                tempoDateAdapter.notifyDataSetChanged();
                // On cache la progressBar.
                binding.progressBarHistory2.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<TempoHistory> call, Throwable t) {

            }
        });
    }
}
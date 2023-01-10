package com.example.b3tempocasalini;

import static com.example.b3tempocasalini.MainActivity.edfApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.b3tempocasalini.databinding.ActivityHistoryBinding;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    TempoDateAdapter tempoDateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Init recycler view
        binding.tempoHistoryRv.setHasFixedSize(true); // On dit au recycler view que tous les éléments ont la même taille. Le fait de le mettre à true veut dire que : les changements ne pourront pas affecter les changements de la recycler view.
        binding.tempoHistoryRv.setLayoutManager(new LinearLayoutManager(this)); // LinearLayoutManager qui permet d'arranger les élements sous forme de liste.
        tempoDateAdapter = new TempoDateAdapter(tempoDates, this);
        binding.tempoHistoryRv.setAdapter(tempoDateAdapter);

        if (edfApi != null)
        {
            // Call the API.
            apiTempoHistory();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        // Call the API.
        apiTempoHistory();
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
        binding.progressBar.setVisibility(View.VISIBLE);

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
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TempoHistory> call, Throwable t) {

            }
        });
    }
}
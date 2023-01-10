package com.example.b3tempocasalini;

import static com.example.b3tempocasalini.MainActivity.edfApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.b3tempocasalini.databinding.ActivityHistory2Binding;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = HistoryActivity2.class.getSimpleName();
    ActivityHistory2Binding binding;

    // Data model
    List<TempoDate> tempoDates = new ArrayList<>();

    TempoDateAdapter2 tempoDateAdapter;
    String[] yearsArray = Tools.getLastNYearDate(6);

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

        Spinner spinner = (Spinner) binding.spinnerYears;
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, yearsArray);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        if (edfApi != null)
        {
            // Call the API.
            apiTempoHistory(2023);
        }
    }

    private void apiTempoHistory(int year) {
        String yearNow = String.valueOf(year);
        String yearBefore = "";

        try {
            yearBefore = String.valueOf(year - 1);
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
                binding.progressBarHistory2.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TempoHistory> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String year = (String) parent.getItemAtPosition(position);
        apiTempoHistory(Integer.valueOf(year));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(LOG_TAG, "Nothing to do here");
    }
}
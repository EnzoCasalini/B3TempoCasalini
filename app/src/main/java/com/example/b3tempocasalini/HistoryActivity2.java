package com.example.b3tempocasalini;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.b3tempocasalini.databinding.ActivityHistory2Binding;
import com.example.b3tempocasalini.databinding.ActivityHistoryBinding;

public class HistoryActivity2 extends AppCompatActivity {
    private static final String LOG_TAG = HistoryActivity.class.getSimpleName();
    ActivityHistory2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistory2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
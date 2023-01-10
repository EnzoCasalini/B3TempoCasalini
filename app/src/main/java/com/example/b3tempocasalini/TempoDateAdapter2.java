package com.example.b3tempocasalini;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b3tempocasalini.databinding.TempoDateItemBinding;

import java.util.List;

public class TempoDateAdapter2 extends RecyclerView.Adapter<TempoDateAdapter2.TempoDateViewHolder>{

    private List<TempoDate> localTempoDates;
    private Context context;

    // Constructeur
    public TempoDateAdapter2(List<TempoDate> tempoDates, Context context) {
        localTempoDates = tempoDates;
        this.context = context;
    }

    
    @NonNull
    @Override
    public TempoDateAdapter2.TempoDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tempo_date_item, parent, false);
        TempoDateItemBinding binding = TempoDateItemBinding.bind(v);
        return new TempoDateAdapter2.TempoDateViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull TempoDateAdapter2.TempoDateViewHolder holder, int position) {
        holder.binding.dateTv.setText(localTempoDates.get(position).getDate());
        holder.binding.colorFl.setBackgroundColor(ContextCompat.getColor(context, localTempoDates.get(position).getCouleur().getColorResId()));
    }


    @Override
    public int getItemCount() {
        return localTempoDates.size();
    }


    public class TempoDateViewHolder extends RecyclerView.ViewHolder { // ViewHolder's permettent de récupérer seulement les éléments de nos layouts dont on a besoin.
        TempoDateItemBinding binding;

        public TempoDateViewHolder(@NonNull TempoDateItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

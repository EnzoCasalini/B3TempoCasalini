package com.example.b3tempocasalini;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b3tempocasalini.databinding.ActivityMainBinding;
import com.example.b3tempocasalini.databinding.TempoDateItemBinding;

import java.util.List;

public class TempoDateAdapter extends RecyclerView.Adapter<TempoDateAdapter.TempoDateViewHolder> {

    private List<TempoDate> localTempoDates;
    private Context context;

    // Constructeur
    public TempoDateAdapter(List<TempoDate> tempoDates, Context context) {
        localTempoDates = tempoDates;
        this.context = context;
    }

    @NonNull
    @Override
    public TempoDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tempo_date_item, parent, false);
        TempoDateItemBinding binding = TempoDateItemBinding.bind(v);
        return new TempoDateViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull TempoDateViewHolder holder, int position) {
        holder.binding.dateTv.setText(localTempoDates.get(position).getDate());
        holder.binding.colorFl.setBackgroundColor(ContextCompat.getColor(context, localTempoDates.get(position).getCouleur().getResId()));
    }

/* Ancienne façon de faire avec le findViewById
    @Override
    public void onBindViewHolder(@NonNull TempoDateViewHolder holder, int position) {
        holder.dateTv.setText(localTempoDates.get(position).getDate());
        holder.colorFl.setBackgroundColor(ContextCompat.getColor(context, localTempoDates.get(position).getCouleur().getResId()));
    }
*/

    @Override
    public int getItemCount() {
        return localTempoDates.size();
    }


/* Ancienne façon de faire avec le findViewById
    public class TempoDateViewHolder extends RecyclerView.ViewHolder { // ViewHolder's permettent de récupérer seulement les éléments de nos layouts dont on a besoin.
        TextView dateTv;
        FrameLayout colorFl;

        public TempoDateViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTv = itemView.findViewById(R.id.date_tv);
            colorFl = itemView.findViewById(R.id.color_fl);
        }
    }
}
*/

    public class TempoDateViewHolder extends RecyclerView.ViewHolder { // ViewHolder's permettent de récupérer seulement les éléments de nos layouts dont on a besoin.
        TempoDateItemBinding binding;

        public TempoDateViewHolder(@NonNull TempoDateItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

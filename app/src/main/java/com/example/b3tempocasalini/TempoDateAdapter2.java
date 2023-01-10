package com.example.b3tempocasalini;

import static com.example.b3tempocasalini.Tools.formatTempoHistoryDate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b3tempocasalini.databinding.TempoDateItem2Binding;

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

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tempo_date_item_2, parent, false);
        TempoDateItem2Binding binding = TempoDateItem2Binding.bind(v);
        return new TempoDateAdapter2.TempoDateViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull TempoDateAdapter2.TempoDateViewHolder holder, int position) {
        holder.binding.dateValueTv.setText(formatTempoHistoryDate(localTempoDates.get(position).getDate()));
        holder.binding.colorValueTv.setText(localTempoDates.get(position).getCouleur().getStringResId());
        holder.binding.colorFl2.setBackgroundColor(ContextCompat.getColor(context, localTempoDates.get(position).getCouleur().getColorResId()));
        holder.binding.spaceFl.setBackgroundColor(ContextCompat.getColor(context, R.color.gray_600));
    }


    @Override
    public int getItemCount() {
        return localTempoDates.size();
    }


    public class TempoDateViewHolder extends RecyclerView.ViewHolder { // ViewHolder's permettent de récupérer seulement les éléments de nos layouts dont on a besoin.
        TempoDateItem2Binding binding;

        public TempoDateViewHolder(@NonNull TempoDateItem2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

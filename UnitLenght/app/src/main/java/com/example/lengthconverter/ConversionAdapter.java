// src/main/java/com/example/lengthconverter/ConversionAdapter.java
package com.example.lengthconverter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ConversionAdapter extends RecyclerView.Adapter<ConversionAdapter.ViewHolder> {
    private List<Conversion> conversions;

    public ConversionAdapter(List<Conversion> conversions) {
        this.conversions = conversions;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView conversionText;

        public ViewHolder(View view) {
            super(view);
            conversionText = view.findViewById(R.id.conversionText);
        }
    }

    @Override
    public ConversionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversion_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Conversion conversion = conversions.get(position);
        String display = String.format("%s %s = %s %s",
                conversion.getInput(),
                conversion.getFromUnit(),
                conversion.getResult(),
                conversion.getToUnit());
        holder.conversionText.setText(display);
    }

    @Override
    public int getItemCount() {
        return conversions.size();
    }
}

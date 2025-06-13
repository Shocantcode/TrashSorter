package com.example.apkapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SampahAdapter extends RecyclerView.Adapter<SampahAdapter.ViewHolder> {

    private final List<SampahData> dataList;

    public SampahAdapter(List<SampahData> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sampah, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SampahData data = dataList.get(position);
        holder.tvWaktu.setText(data.waktu);
        holder.tvJenis.setText(data.jenis);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvWaktu, tvJenis;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWaktu = itemView.findViewById(R.id.tvItemWaktu);
            tvJenis = itemView.findViewById(R.id.tvItemSampah);
        }
    }
}

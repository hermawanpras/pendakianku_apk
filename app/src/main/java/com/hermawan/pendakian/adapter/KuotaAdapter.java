package com.hermawan.pendakian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hermawan.pendakian.R;
import com.hermawan.pendakian.model.KuotaModel;

import java.util.List;

public class KuotaAdapter extends RecyclerView.Adapter<KuotaAdapter.ViewHolder> {
    public List<KuotaModel> list;

    public KuotaAdapter(List<KuotaModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.kuota_pendakian, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tanggal.setText(list.get(position).tgl_pendakian);
        holder.kuota.setText(list.get(position).kuota);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tanggal, kuota;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.tgl_pendakian);
            kuota = itemView.findViewById(R.id.kuota);
        }
    }
}

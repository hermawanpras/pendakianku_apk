package com.hermawan.pendakian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.hermawan.pendakian.R;
import com.hermawan.pendakian.api.response.DetailJalurResponse;
import com.hermawan.pendakian.model.KuotaModel;

import java.util.List;

public class KuotaJalurAdapter extends RecyclerView.Adapter<KuotaJalurAdapter.ViewHolder> {
    public List<DetailJalurResponse.DetailJalurModel> list;
    Context context;

    public KuotaJalurAdapter(List<DetailJalurResponse.DetailJalurModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.kuota_pendakian, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.chip.setText(list.get(position).getStatus());
        holder.tanggal.setText(list.get(position).getTanggalJalur());
        holder.kuota.setText("Kuota " + list.get(position).getKuotaSisa() + "/" + list.get(position).getTotalKuota());

        if (list.get(position).getStatus().equals("buka")) {
            holder.chip.setChipBackgroundColorResource(R.color.colorPrimary);
        } else {
            holder.chip.setChipBackgroundColorResource(R.color.design_default_color_error);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tanggal, kuota;
        Chip chip;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.tgl_pendakian);
            kuota = itemView.findViewById(R.id.kuota);
            chip = itemView.findViewById(R.id.statusChip);
        }
    }
}

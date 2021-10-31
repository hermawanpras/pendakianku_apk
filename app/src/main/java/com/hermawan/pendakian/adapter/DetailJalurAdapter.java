package com.hermawan.pendakian.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.hermawan.pendakian.EditDetailJalurActivity;
import com.hermawan.pendakian.R;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.DetailJalurResponse;

import java.util.List;

public class DetailJalurAdapter extends RecyclerView.Adapter<DetailJalurAdapter.ViewHolder> {
    private static List<DetailJalurResponse.DetailJalurModel> list;
    Context context;
    ApiInterface apiInterface;

    public DetailJalurAdapter(List<DetailJalurResponse.DetailJalurModel> list, Context context) {
        DetailJalurAdapter.list = list;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_detail_jalur, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, EditDetailJalurActivity.class);
                i.putExtra("id_detail_jalur", list.get(position).getIdDetailJalur());
                context.startActivity(i);
            }
        });

        holder.tanggal.setText(list.get(position).getTanggalJalur());
        holder.keterangan.setText(list.get(position).getKeterangan());
        holder.kuota.setText(list.get(position).getKuotaSisa() + " / " + list.get(position).getTotalKuota());
        holder.chip.setText(list.get(position).getStatus());

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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tanggal, keterangan, kuota;
        private MaterialCardView cv;
        Chip chip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.tanggalTv);
            keterangan = itemView.findViewById(R.id.ketTv);
            kuota = itemView.findViewById(R.id.kuotaTv);
            chip = itemView.findViewById(R.id.statusChip);
            cv = itemView.findViewById(R.id.jpCv);
        }
    }

}
package com.hermawan.pendakian.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.hermawan.pendakian.DetailPembayaranActivity;
import com.hermawan.pendakian.DetailPendakiActivity;
import com.hermawan.pendakian.R;
import com.hermawan.pendakian.api.response.PembayaranResponse;
import com.hermawan.pendakian.api.response.PendakiResponse;

import java.util.List;

public class PembayaranAdapter extends RecyclerView.Adapter<PembayaranAdapter.ViewHolder> {
    private static List<PembayaranResponse.PembayaranModel> list;
    Context context;

    public PembayaranAdapter(List<PembayaranResponse.PembayaranModel> list, Context context) {
        PembayaranAdapter.list = list;
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
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pembayaran, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailPembayaranActivity.class);
                i.putExtra("id_pembayaran", list.get(position).getIdPembayaran());
                i.putExtra("id_daftar", list.get(position).getIdDaftar());
                context.startActivity(i);
            }
        });

        holder.namaUserTv.setText(list.get(position).getNamaUser());
        holder.nominalTv.setText("Rp." + list.get(position).getNominal());

        if (list.get(position).getStatus().equals("0")) {
            holder.chip.setText("Pending");
            holder.chip.setChipBackgroundColorResource(R.color.color_button_default);
        } else {
            holder.chip.setText("Sukses");
            holder.chip.setChipBackgroundColorResource(R.color.colorAccent);
        }

        Glide.with(context)
                .load(context.getString(R.string.link) + list.get(position).getFotoProfil())
                .fitCenter()
                .into(holder.fotoIv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namaUserTv, nominalTv, namaGunungTv;
        private MaterialCardView cv;
        ImageView fotoIv;
        Chip chip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaUserTv = itemView.findViewById(R.id.namaBayarTv);
            nominalTv = itemView.findViewById(R.id.nominalTv);
            namaGunungTv = itemView.findViewById(R.id.namaGunungTv);
            chip = itemView.findViewById(R.id.chipStatus);
            fotoIv = itemView.findViewById(R.id.fotoIv);
            cv = itemView.findViewById(R.id.cv);
        }
    }

}
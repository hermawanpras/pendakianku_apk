package com.hermawan.pendakian.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.hermawan.pendakian.DetailPendakiActivity;
import com.hermawan.pendakian.R;
import com.hermawan.pendakian.api.response.BlacklistResponse;
import com.hermawan.pendakian.api.response.PendakiResponse;

import java.util.List;

public class BlacklistAdapter extends RecyclerView.Adapter<BlacklistAdapter.ViewHolder> {
    private static List<BlacklistResponse.BlacklistModel> list;
    Context context;

    public BlacklistAdapter(List<BlacklistResponse.BlacklistModel> list, Context context) {
        BlacklistAdapter.list = list;
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
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_blacklist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailPendakiActivity.class);
                i.putExtra("id_pendaki", list.get(position).getIdPendaki());
                i.putExtra("id_daftar", list.get(position).getIdDaftar());
                context.startActivity(i);
            }
        });

        holder.namaUserTv.setText(list.get(position).getNamaPendaki());
        holder.noIdenTv.setText(list.get(position).getNoIdentitas());
        holder.chip.setText(list.get(position).getStatus().equals("0") ? "Nonaktif" : "Aktif");
        holder.tglBlTv.setText("Blacklist mulai " + list.get(position).getTglMulai() + " sampai " + list.get(position).getTglSelesai());
        holder.ketTv.setText(list.get(position).getKeterangan());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namaUserTv, noIdenTv, tglBlTv, ketTv;
        private MaterialCardView cv;
        Chip chip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaUserTv = itemView.findViewById(R.id.namaTv);
            noIdenTv = itemView.findViewById(R.id.noIdentitasTv);
            tglBlTv = itemView.findViewById(R.id.tglBlTv);
            ketTv = itemView.findViewById(R.id.ketTv);
            chip = itemView.findViewById(R.id.chipStatus);
            cv = itemView.findViewById(R.id.cv);
        }
    }

}
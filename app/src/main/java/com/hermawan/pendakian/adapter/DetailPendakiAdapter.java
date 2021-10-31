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
import com.hermawan.pendakian.DetailPendaftaranPendakianActivity;
import com.hermawan.pendakian.DetailPendakiActivity;
import com.hermawan.pendakian.R;
import com.hermawan.pendakian.api.response.PendaftaranPendakianResponse;
import com.hermawan.pendakian.api.response.PendakiResponse;

import java.util.List;

public class DetailPendakiAdapter extends RecyclerView.Adapter<DetailPendakiAdapter.ViewHolder> {
    private static List<PendakiResponse.PendakiModel> list;
    Context context;

    public DetailPendakiAdapter(List<PendakiResponse.PendakiModel> list, Context context) {
        DetailPendakiAdapter.list = list;
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
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_detail_anggota, parent, false));
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
        holder.noTelpTv.setText(list.get(position).getNoTelp());
        holder.chip.setText(list.get(position).getStatusPendaki());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namaUserTv, noTelpTv;
        private MaterialCardView cv;
        Chip chip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaUserTv = itemView.findViewById(R.id.namaAnggotaTv);
            noTelpTv = itemView.findViewById(R.id.noTelpTv);
            chip = itemView.findViewById(R.id.chipStatus);
            cv = itemView.findViewById(R.id.cv);
        }
    }

}
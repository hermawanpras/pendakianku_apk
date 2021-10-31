package com.hermawan.pendakian.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.hermawan.pendakian.DetailPendaftaranPendakianActivity;
import com.hermawan.pendakian.R;
import com.hermawan.pendakian.api.response.PendaftaranPendakianResponse;

import java.net.Inet4Address;
import java.util.List;

public class PendaftaranPendakianAdapter extends RecyclerView.Adapter<PendaftaranPendakianAdapter.ViewHolder> {
    private static List<PendaftaranPendakianResponse.PendaftaranPendakianModel> list;
    Context context;

    public PendaftaranPendakianAdapter(List<PendaftaranPendakianResponse.PendaftaranPendakianModel> list, Context context) {
        PendaftaranPendakianAdapter.list = list;
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
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_admin_daftar_pendakian, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailPendaftaranPendakianActivity.class);
                i.putExtra("id_daftar", list.get(position).getIdDaftar());
                i.putExtra("id_info_jalur", list.get(position).getIdInfoJalur());
                context.startActivity(i);
            }
        });

        holder.namaUserTv.setText(list.get(position).getNamaUser());

        holder.tglDaftarTv.setText("Didaftarkan pada " +  list.get(position).getTglDaftarPendakian());
        holder.tglPendakianTv.setText(list.get(position).getTglMulaiPendakian() + " - " + list.get(position).getTglSelesaiPendakian());

        if (list.get(position).getStatusPendakian().equals("0")) {
            holder.chip.setText("Belum divalidasi");
        } else if (list.get(position).getStatusPendakian().equals("1")) {
            holder.chip.setText("Belum dibayar");
        }else if (list.get(position).getStatusPendakian().equals("2")) {
            holder.chip.setText("Sudah dibayar");
        } else if (list.get(position).getStatusPendakian().equals("3")) {
            holder.chip.setText("Sudah cekin");
        }else if (list.get(position).getStatusPendakian().equals("4")) {
            holder.chip.setText("Selesai");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namaUserTv, tglPendakianTv, tglDaftarTv;
        private MaterialCardView cv;
        Chip chip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaUserTv = itemView.findViewById(R.id.namaUserTv);
            tglPendakianTv = itemView.findViewById(R.id.tglPendakianTv);
            tglDaftarTv = itemView.findViewById(R.id.tglDaftarTv);
            chip = itemView.findViewById(R.id.statusChip);
            cv = itemView.findViewById(R.id.jpCv);
        }
    }

}
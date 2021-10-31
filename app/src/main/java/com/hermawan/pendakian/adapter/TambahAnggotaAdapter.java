package com.hermawan.pendakian.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.hermawan.pendakian.R;
import com.hermawan.pendakian.TambahAnggotaDetailActivity;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.DetailJalurResponse;

import java.util.List;

public class TambahAnggotaAdapter extends RecyclerView.Adapter<TambahAnggotaAdapter.ViewHolder> {
    private static int jumlah;
    String idDaftar;
    Context context;
    boolean clicked = false;

    public TambahAnggotaAdapter(int jumlah, String idDaftar, Context context) {
        TambahAnggotaAdapter.jumlah = jumlah;
        this.idDaftar = idDaftar;
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
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_anggota, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tambahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = true;
                Intent i = new Intent(context, TambahAnggotaDetailActivity.class);
                i.putExtra("id_daftar", idDaftar);
                context.startActivity(i);
            }
        });

        if (clicked) {
            holder.tambahBtn.setEnabled(false);
        }

        holder.nomorAnggotaTv.setText("Anggota " + (position+1));
    }

    @Override
    public int getItemCount() {
        return jumlah;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nomorAnggotaTv;
        Button tambahBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomorAnggotaTv = itemView.findViewById(R.id.nomorAnggotaTv);
            tambahBtn = itemView.findViewById(R.id.tambahAnggotaBtn);
        }
    }

}
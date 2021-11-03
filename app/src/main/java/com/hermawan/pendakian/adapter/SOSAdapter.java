package com.hermawan.pendakian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.hermawan.pendakian.R;
import com.hermawan.pendakian.api.response.SOSResponse;

import java.util.List;

public class SOSAdapter extends RecyclerView.Adapter<SOSAdapter.ViewHolder> {
    private List<SOSResponse.SOSModel> data;
    private OnItemClick onItemClick;

    public SOSAdapter(List<SOSResponse.SOSModel> data, OnItemClick onItemClick) {
        this.data = data;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sos, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.status.setText(data.get(position).isSeen == 0 ? "Belum Dilihat" : "Dilihat");

        holder.namaUserTv.setText(data.get(position).nama);
        holder.latLongTv.setText(data.get(position).latitude + " / " + data.get(position).longitude);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onClick(data.get(position).idSos, data.get(position).nama, data.get(position).latitude, data.get(position).longitude, data.get(position).isSeen);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namaUserTv, latLongTv;
        Chip status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaUserTv = itemView.findViewById(R.id.namaUserTv);
            status = itemView.findViewById(R.id.statusChip);
            latLongTv = itemView.findViewById(R.id.latLongTv);
        }
    }

    public interface OnItemClick {
        void onClick(String idSos, String nama, double latitude, double longitude, int isSeen);
    }
}

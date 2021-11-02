package com.hermawan.pendakian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.textViewStatus.setText(data.get(position).isSeen == 0 ? "Belum Dilihat" : "Dilihat");
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
        private TextView textViewStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
        }
    }

    public interface OnItemClick {
        void onClick(String idSos, String nama, double latitude, double longitude, int isSeen);
    }
}

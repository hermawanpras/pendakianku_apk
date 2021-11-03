package com.hermawan.pendakian;

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
import com.hermawan.pendakian.api.response.PembayaranResponse;
import com.hermawan.pendakian.api.response.UserResponse;

import java.util.List;

public class ListAdminAdapter extends RecyclerView.Adapter<ListAdminAdapter.ViewHolder> {
    private static List<UserResponse.UserModel> list;
    Context context;

    public ListAdminAdapter(List<UserResponse.UserModel> list, Context context) {
        ListAdminAdapter.list = list;
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
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_admin, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.cv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(context, DetailPembayaranActivity.class);
//                i.putExtra("id_user", list.get(position).getIdUser());
//                context.startActivity(i);
//            }
//        });

        holder.namaAdminTv.setText(list.get(position).getUsername());
        holder.emailAdminTv.setText(list.get(position).getEmail());

        Glide.with(context)
                .load(context.getString(R.string.base_url) + context.getString(R.string.link_profile) + list.get(position).getFotoProfil())
                .fitCenter()
                .into(holder.fotoIv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namaAdminTv, emailAdminTv;
        private MaterialCardView cv;
        ImageView fotoIv;
        Chip chip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaAdminTv = itemView.findViewById(R.id.namaAdminTv);
            emailAdminTv = itemView.findViewById(R.id.emailAdminTv);
            chip = itemView.findViewById(R.id.chipStatus);
            fotoIv = itemView.findViewById(R.id.gambarAdminIv);
            cv = itemView.findViewById(R.id.jpCv);
        }
    }

}
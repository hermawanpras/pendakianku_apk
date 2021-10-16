package com.hermawan.pendakian;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class UserGuideFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_guide, container, false);

        LinearLayout info = view.findViewById(R.id.info_pendakian);
        LinearLayout alat = view.findViewById(R.id.peralatan_pendakian);
        LinearLayout keselamatan = view.findViewById(R.id.keselamatan_pendakian);
        LinearLayout kompas = view.findViewById(R.id.kompas);
        LinearLayout tracking = view.findViewById(R.id.tracking_pendakian);
        LinearLayout sinyal_darurat = view.findViewById(R.id.sos);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),InfoGunungActivity.class);
                startActivity(intent);
            }
        });

        alat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),PeralatanActivity.class);
                startActivity(intent);
            }
        });

        keselamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),KeselamatanActivity.class);
                startActivity(intent);
            }
        });

        kompas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),KompasActivity.class);
                startActivity(intent);
            }
        });

        tracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),TrackingActivity.class);
                startActivity(intent);
            }
        });

        sinyal_darurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),SinyalDaruratActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}

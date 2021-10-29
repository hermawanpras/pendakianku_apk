package com.hermawan.pendakian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;

public class AdminCekActivity extends Fragment {

    MaterialCardView scan, pembayaran, pendaftaran;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_admin_cek, container, false);

        scan = view.findViewById(R.id.cekScanCv);
        pembayaran = view.findViewById(R.id.pembayaranCv);
        pendaftaran = view.findViewById(R.id.pendaftaranCv);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CekScanActivity.class));
            }
        });

        pembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CekPembayaranActivity.class));
            }
        });

        pendaftaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CekPendaftaranActivity.class));
            }
        });

        return view;
    }
}

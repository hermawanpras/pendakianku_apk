package com.hermawan.pendakian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.hermawan.pendakian.api.response.UserResponse;
import com.hermawan.pendakian.preference.AppPreference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminProfileActivity extends Fragment {

    CircleImageView fotoIv;
    TextView nama, email, noHp, alamat;
    MaterialButton btnKeluar, btnTambahAdmin, btnEditProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_admin_profile, container, false);

        fotoIv = view.findViewById(R.id.fotoIv);
        nama = view.findViewById(R.id.txtViewNama);
        email = view.findViewById(R.id.txtViewEmail);
        noHp = view.findViewById(R.id.txtNomorHP);
        alamat = view.findViewById(R.id.txtViewAlamat);
        btnTambahAdmin = view.findViewById(R.id.btnTambahAdmin);
        btnEditProfile = view.findViewById(R.id.btnEditProfileUser);

        btnKeluar = view.findViewById(R.id.btnKeluar);

        btnTambahAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListAdminActivity.class));
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditProfilActivity.class));
            }
        });

        UserResponse.UserModel u =  AppPreference.getUser(getContext());

        Glide.with(getContext())
                .load(getString(R.string.base_url) + getString(R.string.link_profile)+ u.fotoProfil)
                .centerCrop()
                .into(fotoIv);

        nama.setText(u.username);
        email.setText(u.email);
        noHp.setText(u.noTelp);
        alamat.setText(u.alamat);

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppPreference.removeUser(getContext());

                startActivity(new Intent(getActivity(), SignInActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }
}

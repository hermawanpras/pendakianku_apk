package com.hermawan.pendakian;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.BaseResponse;
import com.hermawan.pendakian.api.response.UserResponse;
import com.hermawan.pendakian.preference.AppPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileFragment extends Fragment {

    private TextView tvNama, tvAlamat, tvTelepon, tvEmail;
    private MaterialButton btnKeluar, btnEditProfile, btnTentangApp;
    ImageView profileIv;

    private ApiInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        tvNama = view.findViewById(R.id.txtViewNama);
        tvAlamat = view.findViewById(R.id.txtViewAlamat);
        tvTelepon = view.findViewById(R.id.txtNomorHP);
        tvEmail = view.findViewById(R.id.txtViewEmail);
        btnKeluar = view.findViewById(R.id.btnKeluar);
        btnEditProfile = view.findViewById(R.id.btnEditProfileUser);
        profileIv = view.findViewById(R.id.profile_image);
        btnTentangApp = view.findViewById(R.id.btnTentangApp);

        apiInterface = ApiClient.getClient();

        btnTentangApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TentangAplikasiActivity.class));
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditProfilActivity.class));
            }
        });

        UserResponse.UserModel model = AppPreference.getUser(getContext());
        tvNama.setText(model.username);
        tvAlamat.setText(model.alamat);
        tvTelepon.setText(model.noTelp);
        tvEmail.setText(model.email);

        Glide.with(getActivity())
                .load(getString(R.string.base_url) + getString(R.string.link_profile) + model.fotoProfil)
                .fitCenter()
                .into(profileIv);

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeToken();
                AppPreference.removeUser(v.getContext());
                Intent intent = new Intent(v.getContext(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        return view;
    }

    public void removeToken() {
        apiInterface.postToken(
                AppPreference.getUser(getContext()).idUser,
                ""
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.e("token", response.body().message);
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("token", t.getMessage());
            }
        });
    }
}

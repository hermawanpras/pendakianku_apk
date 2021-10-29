package com.hermawan.pendakian;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hermawan.pendakian.adapter.KuotaJalurAdapter;
import com.hermawan.pendakian.adapter.RiwayatPendakianAdapter;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.DetailJalurResponse;
import com.hermawan.pendakian.api.response.PendaftaranPendakianResponse;
import com.hermawan.pendakian.api.response.UserResponse;
import com.hermawan.pendakian.preference.AppPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHistoryFragment extends Fragment {

    private RecyclerView rv;
    private ApiInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_history, container, false);

        apiInterface = ApiClient.getClient();

        rv = view.findViewById(R.id.rv);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);

        getHistory();

        return view;
    }

    private void getHistory() {
        UserResponse.UserModel u = AppPreference.getUser(getContext());
        apiInterface.getHistory(
                u.idUser
        ).enqueue(new Callback<PendaftaranPendakianResponse>() {
            @Override
            public void onResponse(Call<PendaftaranPendakianResponse> call, Response<PendaftaranPendakianResponse> response) {
                if (response.body().status) {

                    List<PendaftaranPendakianResponse.PendaftaranPendakianModel> list = new ArrayList<>(response.body().data);

                    rv.setAdapter(new RiwayatPendakianAdapter(list, getContext()));
                }
            }

            @Override
            public void onFailure(Call<PendaftaranPendakianResponse> call, Throwable t) {

            }
        });
    }

}

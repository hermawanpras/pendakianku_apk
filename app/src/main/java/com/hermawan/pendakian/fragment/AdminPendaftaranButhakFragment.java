package com.hermawan.pendakian.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hermawan.pendakian.CekPendaftaranActivity;
import com.hermawan.pendakian.R;
import com.hermawan.pendakian.adapter.DetailJalurAdapter;
import com.hermawan.pendakian.adapter.PendaftaranPendakianAdapter;
import com.hermawan.pendakian.adapter.RiwayatPendakianAdapter;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.DetailJalurResponse;
import com.hermawan.pendakian.api.response.PendaftaranPendakianResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class AdminPendaftaranButhakFragment extends Fragment {

    RecyclerView rv;
    View view;
    ApiInterface apiInterface;

    public AdminPendaftaranButhakFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_pendaftaran_buthak, container, false);

        rv  = view.findViewById(R.id.rv);
        apiInterface = ApiClient.getClient();

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        getData();

        return view;
    }

    private void getData() {
        apiInterface.cekDaftar("", "2").enqueue(new Callback<PendaftaranPendakianResponse>() {
            @Override
            public void onResponse(Call<PendaftaranPendakianResponse> call, Response<PendaftaranPendakianResponse> response) {
                if (response.body().status) {
                    List<PendaftaranPendakianResponse.PendaftaranPendakianModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    rv.setAdapter(new PendaftaranPendakianAdapter(list, getContext()));
                }
            }

            @Override
            public void onFailure(Call<PendaftaranPendakianResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
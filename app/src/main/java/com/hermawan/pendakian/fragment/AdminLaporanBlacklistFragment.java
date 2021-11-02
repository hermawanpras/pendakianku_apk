package com.hermawan.pendakian.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hermawan.pendakian.R;
import com.hermawan.pendakian.TambahBlacklistActivity;
import com.hermawan.pendakian.adapter.BlacklistAdapter;
import com.hermawan.pendakian.adapter.DetailJalurAdapter;
import com.hermawan.pendakian.adapter.PendaftaranPendakianAdapter;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.BlacklistResponse;
import com.hermawan.pendakian.api.response.DetailJalurResponse;
import com.hermawan.pendakian.api.response.PendaftaranPendakianResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLaporanBlacklistFragment extends Fragment {

    RecyclerView rv;
    View view;
    ApiInterface apiInterface;
    Button tambahBlBtn;

    TextView tidakTv;

    public AdminLaporanBlacklistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_laporan_blacklist, container, false);

        rv  = view.findViewById(R.id.rv);
        apiInterface = ApiClient.getClient();
        tidakTv = view.findViewById(R.id.tidakTv);
        tambahBlBtn = view.findViewById(R.id.tambahBlBtn);

        tidakTv.setVisibility(View.GONE);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        getData();

        tambahBlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TambahBlacklistActivity.class));
            }
        });
        return view;
    }

    private void getData() {
        apiInterface.getBlacklist("")
                .enqueue(new Callback<BlacklistResponse>() {
            @Override
            public void onResponse(Call<BlacklistResponse> call, Response<BlacklistResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        List<BlacklistResponse.BlacklistModel> list = new ArrayList<>();

                        list.addAll(response.body().data);

                        rv.setAdapter(new BlacklistAdapter(list, getContext()));

                    } else {
                        tidakTv.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<BlacklistResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }
}
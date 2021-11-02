package com.hermawan.pendakian.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hermawan.pendakian.R;
import com.hermawan.pendakian.SOSActivity;
import com.hermawan.pendakian.adapter.PendaftaranPendakianAdapter;
import com.hermawan.pendakian.adapter.SOSAdapter;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.PendaftaranPendakianResponse;
import com.hermawan.pendakian.api.response.SOSResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLaporanSosFragment extends Fragment {

    RecyclerView rv;
    View view;
    ApiInterface apiInterface;

    public AdminLaporanSosFragment() {
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
        apiInterface.getSOS().enqueue(new Callback<SOSResponse>() {
            @Override
            public void onResponse(Call<SOSResponse> call, Response<SOSResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().status) {
                        rv.setAdapter(new SOSAdapter(response.body().data, new SOSAdapter.OnItemClick() {
                            @Override
                            public void onClick(String idSos, String nama, double latitude, double longitude, int isSeen) {
                                Intent intent = new Intent(getActivity(), SOSActivity.class);
                                intent.putExtra("idSos", idSos);
                                intent.putExtra("nama", nama);
                                intent.putExtra("latitude", latitude);
                                intent.putExtra("longitude", longitude);
                                intent.putExtra("isSeen", isSeen);
                                startActivity(intent);
                            }
                        }));
                    }
                }
            }

            @Override
            public void onFailure(Call<SOSResponse> call, Throwable t) {

            }
        });
    }
}
package com.hermawan.pendakian;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hermawan.pendakian.adapter.DetailJalurAdapter;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.DetailJalurResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class AdminPandermanFragment extends Fragment {

    RecyclerView rv;
    View view;
    ApiInterface apiInterface;

    public AdminPandermanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_panderman, container, false);

        rv  = view.findViewById(R.id.rv);
        apiInterface = ApiClient.getClient();

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        getData();

        return view;
    }

    private void getData() {
        apiInterface.getDetailJalurPanderman().enqueue(new Callback<DetailJalurResponse>() {
            @Override
            public void onResponse(Call<DetailJalurResponse> call, Response<DetailJalurResponse> response) {
                if (response.body().status) {
                    List<DetailJalurResponse.DetailJalurModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    Log.e(TAG, "onResponse: " + list.size() );

                    rv.setAdapter(new DetailJalurAdapter(list, getContext()));
                }
            }

            @Override
            public void onFailure(Call<DetailJalurResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
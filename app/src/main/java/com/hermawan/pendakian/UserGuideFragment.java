package com.hermawan.pendakian;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.location.LocationListener;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.BaseResponse;
import com.hermawan.pendakian.preference.AppPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserGuideFragment extends Fragment {
    private ApiInterface apiInterface;
    private LocationManager locationManager;
    private String latitude, longitude;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_guide, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Pesan");
        progressDialog.setMessage("Mohon tunggu sebentar...");

        apiInterface = ApiClient.getClient();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

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
                String[] strings = {"Gunung Buthak", "Gunung Panderman"};
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Pilih Jalur")
                        .setItems(strings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(v.getContext(),TrackingActivity.class);
                                switch (i) {
                                    case 0:
                                        intent.putExtra("ID_GUNUNG", "2");
                                        break;
                                    case 1:
                                        intent.putExtra("ID_GUNUNG", "1");
                                        break;
                                }
                                startActivity(intent);
                            }
                        })
                        .create()
                        .show();
            }
        });

        progressDialog.show();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    2000,
                    10,
                    locationListenerGPS);
            isLocationEnabled();
        }

        sinyal_darurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface.postSOS(
                        AppPreference.getUser(v.getContext()).idUser,
                        latitude,
                        longitude
                ).enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().status) {
                                new AlertDialog.Builder(v.getContext())
                                        .setTitle("Pesan")
                                        .setMessage("Pesan SOS telah terkirim, mohon menungggu bantuan petugas")
                                        .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        })
                                        .create()
                                        .show();
                            } else {
                                new AlertDialog.Builder(v.getContext())
                                        .setTitle("Pesan")
                                        .setMessage("Pesan SOS gagal terkirim, silahkan coba lagi")
                                        .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        })
                                        .create()
                                        .show();
                            }
                        } else {
                            new AlertDialog.Builder(v.getContext())
                                    .setTitle("Pesan")
                                    .setMessage("Pesan SOS gagal terkirim, silahkan coba lagi")
                                    .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Log.e("postSOS", t.getMessage());
                    }
                });
            }
        });

        return view;
    }

    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            String msg="New Latitude: "+latitude + " New Longitude: "+longitude;
//            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            Log.e("location", msg);
//            textView.setText(msg);
            UserGuideFragment.this.latitude = String.valueOf(latitude);
            UserGuideFragment.this.longitude = String.valueOf(longitude);
        }
    };

    private void isLocationEnabled() {
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
            alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            AlertDialog alert=alertDialog.create();
            alert.show();
        }
    }
}

package com.hermawan.pendakian;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.BaseResponse;
import com.hermawan.pendakian.api.response.PendaftaranPendakianResponse;
import com.hermawan.pendakian.util.RPResultListener;
import com.hermawan.pendakian.util.RuntimePermissionUtil;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CekScanActivity extends AppCompatActivity {

    // QREader
    private SurfaceView mySurfaceView;
    private QREader qrEader;

    private static final String cameraPerm = Manifest.permission.CAMERA;
    boolean hasCameraPermission = false;

    TextView text;
    Button detailBtn;

    String idDaftar;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_scan);
        hasCameraPermission = RuntimePermissionUtil.checkPermissonGranted(this, cameraPerm);

        mySurfaceView = (SurfaceView) findViewById(R.id.camera_view);
        text = findViewById(R.id.hasilScanTv);
        detailBtn = findViewById(R.id.detailPendakianBtn);

        apiInterface = ApiClient.getClient();

        detailBtn.setVisibility(View.INVISIBLE);

        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiInterface.getIdIj(idDaftar).enqueue(new Callback<PendaftaranPendakianResponse>() {
                    @Override
                    public void onResponse(Call<PendaftaranPendakianResponse> call, Response<PendaftaranPendakianResponse> response) {
                        if (response.body().status) {
                            Intent i = new Intent(CekScanActivity.this, DetailPendaftaranPendakianActivity.class);
                            i.putExtra("id_daftar", idDaftar);
                            i.putExtra("id_info_jalur", response.body().data.get(0).getIdInfoJalur());
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(Call<PendaftaranPendakianResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        if (hasCameraPermission) {
            // Setup QREader
            setupQREader();
        } else {
            RuntimePermissionUtil.requestPermission(CekScanActivity.this, cameraPerm, 100);
        }
    }

    void restartActivity() {
        startActivity(new Intent(CekScanActivity.this, CekScanActivity.class));
        finish();
    }

    void setupQREader() {
        // Init QREader
        // ------------
        qrEader = new QREader.Builder(this, mySurfaceView, new QRDataListener() {
            @Override
            public void onDetected(final String data) {
                Log.d("QREader", "Value : " + data);
                text.post(new Runnable() {
                    @Override
                    public void run() {
                        idDaftar = data;
                        text.setText("KB_" + data + "_PPB");
                        detailBtn.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).facing(QREader.BACK_CAM)
                .enableAutofocus(true)
                .height(mySurfaceView.getHeight())
                .width(mySurfaceView.getWidth())
                .build();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (hasCameraPermission) {

            // Cleanup in onPause()
            // --------------------
            qrEader.releaseAndCleanup();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (hasCameraPermission) {

            // Init and Start with SurfaceView
            // -------------------------------
            qrEader.initAndStart(mySurfaceView);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            RuntimePermissionUtil.onRequestPermissionsResult(grantResults, new RPResultListener() {
                @Override
                public void onPermissionGranted() {
                    if (RuntimePermissionUtil.checkPermissonGranted(CekScanActivity.this, cameraPerm)) {
                        restartActivity();
                    }
                }

                @Override
                public void onPermissionDenied() {
                    // do nothing
                }
            });
        }
    }

}
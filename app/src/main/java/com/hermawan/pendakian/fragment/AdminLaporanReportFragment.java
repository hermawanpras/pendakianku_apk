package com.hermawan.pendakian.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.hermawan.pendakian.R;
import com.hermawan.pendakian.adapter.DetailJalurAdapter;
import com.hermawan.pendakian.adapter.PendaftaranPendakianAdapter;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.DetailJalurResponse;
import com.hermawan.pendakian.api.response.PendaftaranPendakianResponse;
import com.hermawan.pendakian.api.response.ReportResponse;
import com.hermawan.pendakian.api.response.ReportUmurResponse;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class AdminLaporanReportFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    RecyclerView rv;
    View view;
    ApiInterface apiInterface;
    TextView totalPendakiTv, totalPendapatanTv, totalBlTv;

    AnyChartView gunungChartView, jkChartView, umurChartView;
    Pie pieGunung, pieJk, pieUmur;
    Spinner spin;
    Button pilihBtn;

    String[] bulan = {"Januari",
    "Februari",
    "Maret",
    "April",
    "Mei",
    "Juni",
    "Juli",
    "Agustus",
    "September",
    "Oktober",
    "November",
    "Desember"};

    String bulanPilihan;

    public AdminLaporanReportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_laporan_report, container, false);

        apiInterface = ApiClient.getClient();
        totalPendakiTv = view.findViewById(R.id.totalPendakiTv);
        totalBlTv = view.findViewById(R.id.totalBlacklistTv);
        totalPendapatanTv = view.findViewById(R.id.totalPendapatanTv);
        spin = view.findViewById(R.id.spinnerBln);
        pilihBtn = view.findViewById(R.id.pilihBtn);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, bulan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormatView = new SimpleDateFormat("MM", new Locale("id", "ID"));
        String b = simpleDateFormatView.format(calendar.getTime());

        spin.setSelection(Integer.parseInt(b) -1);

        bulanPilihan = b;

        initReport(bulanPilihan);

        pilihBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initReport(bulanPilihan);
            }
        });

        return view;
    }

    private void initReport(String b) {
        apiInterface.getReport(b).enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                if (response.body().status) {
                    ReportResponse.ReportModel rm = response.body().data.get(0);

                    totalPendakiTv.setText(rm.getTotalPendaki());
                    totalBlTv.setText(rm.getTotalBl());

                    int total = Integer.parseInt(rm.getTotalPendapatan() == null ? "0" : rm.getTotalPendapatan());
                    totalPendapatanTv.setText(NumberFormat.getCurrencyInstance(new Locale("en", "ID"))
                            .format(total));

                    jkChartView = view.findViewById(R.id.jkChartView);
                    APIlib.getInstance().setActiveAnyChartView(jkChartView);

                    pieJk = AnyChart.pie();
                    setUpJk(Integer.parseInt(rm.getTotalL()), Integer.parseInt(rm.getTotalP()));

                    gunungChartView = view.findViewById(R.id.gunungChartView);
                    APIlib.getInstance().setActiveAnyChartView(gunungChartView);
                    pieGunung = AnyChart.pie();
                    setUpGunung(Integer.parseInt(rm.getTotalPanderman()), Integer.parseInt(rm.getTotalButhak()));
                }
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                Toast.makeText(getContext(), "jer", Toast.LENGTH_SHORT).show();
            }
        });

        apiInterface.getReportUmur(b).enqueue(new Callback<ReportUmurResponse>() {
            @Override
            public void onResponse(Call<ReportUmurResponse> call, Response<ReportUmurResponse> response) {
                if (response.body().status) {
                    List<ReportUmurResponse.ReportUmurModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    umurChartView = view.findViewById(R.id.umurChartView);
                    APIlib.getInstance().setActiveAnyChartView(umurChartView);

                    pieUmur = AnyChart.pie();
                    setUpUmur(list);
                }
            }

            @Override
            public void onFailure(Call<ReportUmurResponse> call, Throwable t) {
                Toast.makeText(getContext(), "jer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpUmur(List<ReportUmurResponse.ReportUmurModel> rum) {
        pieUmur.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(getActivity(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        List<DataEntry> data = new ArrayList<>();

        for (int i = 0; i < rum.size(); i++) {
            data.add(new ValueDataEntry(rum.get(i).age + " tahun", Integer.parseInt(rum.get(i).total)));
        }

        pieUmur.data(data);

        pieUmur.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        umurChartView.setChart(pieUmur);
    }

    private void setUpJk(int l, int p) {
        pieJk.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(getActivity(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Laki-laki", l));
        data.add(new ValueDataEntry("Perempuan", p));

        pieJk.data(data);

        pieJk.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        jkChartView.setChart(pieJk);
    }

    private void setUpGunung(int p, int b) {
        pieGunung.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(getActivity(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Gunung Panderman", p));
        data.add(new ValueDataEntry("Gunung Buthak", b));

        pieGunung.data(data);

        pieGunung.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        gunungChartView.setChart(pieGunung);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                bulanPilihan = "1";
                break;
            case 1:
                bulanPilihan = "2";
                break;
            case 2:
                bulanPilihan = "3";
                break;
            case 3:
                bulanPilihan = "4";
                break;
            case 4:
                bulanPilihan = "5";
                break;
            case 5:
                bulanPilihan = "6";
                break;
            case 6:
                bulanPilihan = "7";
                break;
            case 7:
                bulanPilihan = "8";
                break;
            case 8:
                bulanPilihan = "9";
                break;
            case 9:
                bulanPilihan = "10";
                break;
            case 10:
                bulanPilihan = "11";
                break;
            case 11:
                bulanPilihan = "12";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
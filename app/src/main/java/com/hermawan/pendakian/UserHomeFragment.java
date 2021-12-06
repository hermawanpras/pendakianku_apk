package com.hermawan.pendakian;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.hermawan.pendakian.api.response.DetailJalurResponse;
import com.hermawan.pendakian.retrofit.IOpenWeatherMap;
import com.hermawan.pendakian.retrofit.RetrofitClient;
import com.hermawan.pendakian.adapter.WeatherForecastAdapter;
import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.OpenWeatherClient;
import com.hermawan.pendakian.api.OpenWeatherInterface;
import com.hermawan.pendakian.api.response.CurrentTimeResponse;
import com.hermawan.pendakian.api.response.DailyForecastResponse;
import com.hermawan.pendakian.api.response.InfoJalurResponse;
import com.hermawan.pendakian.common.Common;
import com.hermawan.pendakian.model.WeatherForecastResult;
import com.hermawan.pendakian.model.WeatherResult;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class UserHomeFragment extends Fragment {

    private ApiInterface apiInterface;
    private OpenWeatherInterface openWeatherInterface;
    private Common common;

    //weather
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private CardView viewPages;
    ImageView img_weather;
    TextView txt_city_name, txt_humidity, txt_sunrise, txt_sunset, txt_pressure, txt_temperatur, txt_description, txt_date_time, txt_wind, txt_geo_coords;
    LinearLayout weather_panel;
    ProgressBar loading;
    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;
    RecyclerView recycler_forecast;

    private final double LATITUDE = -7.888438338108585;
    private final double LONGITUDE = 112.49692414860539;

    public UserHomeFragment(){
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        apiInterface = ApiClient.getClient();
        openWeatherInterface = OpenWeatherClient.getClient();

        CardView viewPages = view.findViewById(R.id.view_page);
        TextView panderman = view.findViewById(R.id.panderman);
        TextView status_jlr = view.findViewById(R.id.status_jlr);
        TextView tgl_mulai = view.findViewById(R.id.tgl_mulai_jlr);
        TextView ket = view.findViewById(R.id.ket);
        TextView buthak = view.findViewById(R.id.buthak);
        TextView status_jlr1 = view.findViewById(R.id.status_jlr1);
        TextView tgl_mulai1 = view.findViewById(R.id.tgl_mulai_jlr1);
        TextView ket1 = view.findViewById(R.id.ket1);
        LinearLayout booking = view.findViewById(R.id.booking);
        LinearLayout booking1 = view.findViewById(R.id.booking1);
        LinearLayout linearLayout = view.findViewById(R.id.linear_layout);
        img_weather = (ImageView)view.findViewById(R.id.img_weather);
        txt_city_name = (TextView)view.findViewById(R.id.txt_city_name);
        txt_humidity = (TextView)view.findViewById(R.id.txt_humidity);
        txt_sunrise = (TextView)view.findViewById(R.id.txt_sunrise);
        txt_sunset = (TextView)view.findViewById(R.id.txt_sunset);
        txt_pressure = (TextView)view.findViewById(R.id.txt_pressure);
        txt_temperatur = (TextView)view.findViewById(R.id.txt_temperature);
        txt_description = (TextView)view.findViewById(R.id.txt_description);
        txt_date_time = (TextView)view.findViewById(R.id.txt_date_time);
        txt_wind = (TextView)view.findViewById(R.id.txt_wind);
        txt_geo_coords = (TextView)view.findViewById(R.id.txt_geo_coords);
        weather_panel = (LinearLayout)view.findViewById(R.id.weather_panel);
        loading = (ProgressBar)view.findViewById(R.id.loading);

        recycler_forecast = (RecyclerView)view.findViewById(R.id.recycler_forecast);
        recycler_forecast.setHasFixedSize(true);
        recycler_forecast.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));


//        Dexter.withActivity(this)
//                .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.ACCESS_FINE_LOCATION)
//                .withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        if (report.areAllPermissionsGranted()){
//                            buildLocationRequest();
//                            buildLocationCallBack();
//
//                            fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//                        Snackbar.make(linearLayout, "Permission Denied", Snackbar.LENGTH_LONG)
//                                .show();
//                    }
//                }).check();

//        getWeatherInformation();
//        getForecastWeatherInformation();

        getCurrentForecast();
        getDailyForecast();


        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String tanggal = sdf.format(c.getTime());

        apiInterface.getTanggal("1", tanggal).enqueue(new Callback<DetailJalurResponse>() {
            @Override
            public void onResponse(Call<DetailJalurResponse> call, Response<DetailJalurResponse> response) {
                if (response.body().status) {
                    DetailJalurResponse.DetailJalurModel panderman = response.body().data.get(0);
                    status_jlr.setText(panderman.getStatus());
                    tgl_mulai.setText(panderman.getTanggalJalur());
                    ket.setText(panderman.getKeterangan());

                    if (panderman.getStatus().equalsIgnoreCase("tutup")){
                        booking.setVisibility(View.GONE);

                    }

                    booking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(v.getContext(),UserBookingActivity.class);
                            intent.putExtra("ID_INFO_JALUR", panderman.getIdInfoJalur());
                            startActivity(intent);
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<DetailJalurResponse> call, Throwable t) {

            }
        });

        apiInterface.getTanggal("2", tanggal).enqueue(new Callback<DetailJalurResponse>() {
            @Override
            public void onResponse(Call<DetailJalurResponse> call, Response<DetailJalurResponse> response) {
                if (response.body().status) {
                    DetailJalurResponse.DetailJalurModel buthak = response.body().data.get(0);

                    status_jlr1.setText(buthak.getStatus());
                    tgl_mulai1.setText(buthak.getTanggalJalur());
                    ket1.setText(buthak.getKeterangan());

                    if (buthak.getStatus().equalsIgnoreCase("tutup")){
                        booking1.setVisibility(View.GONE);
                    }

                    booking1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(v.getContext(), UserBookingActivity.class);
                            intent.putExtra("ID_INFO_JALUR", buthak.getIdInfoJalur());
                            startActivity(intent);
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<DetailJalurResponse> call, Throwable t) {

            }
        });

        return view;
    }

    public void cek(){}

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    private void getCurrentForecast() {
        openWeatherInterface.getCurrentTime(
                String.valueOf(LATITUDE),
                String.valueOf(LONGITUDE),
                Common.APP_ID
        ).enqueue(new Callback<CurrentTimeResponse>() {
            @Override
            public void onResponse(Call<CurrentTimeResponse> call, Response<CurrentTimeResponse> response) {
                if (response.isSuccessful()) {
                    Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                        .append(response.body().weather.get(0).icon)
                        .append(".png").toString()).into(img_weather);

//                    load information
                    txt_city_name.setText(response.body().name);
                    txt_description.setText(new StringBuilder("Cuaca di ").append(response.body().name));
                    double celcius = Math.ceil(Float.parseFloat(String.valueOf(response.body().main.temp)) - 273.15F);
                    txt_temperatur.setText(new StringBuilder(String.valueOf((int) celcius)).append("°C").toString());
                    txt_date_time.setText(Common.convertUnixToDate(response.body().dt));
                    txt_pressure.setText(new StringBuilder(String.valueOf(response.body().main.pressure)).append(" hpa").toString());
                    txt_humidity.setText(new StringBuilder(String.valueOf(response.body().main.humidity)).append(" %").toString());
                    txt_sunrise.setText(Common.convertUnixToHour(response.body().sys.sunrise));
                    txt_sunset.setText(Common.convertUnixToHour(response.body().sys.sunset));
                    txt_geo_coords.setText(new StringBuilder("[").append(response.body().coord.toString()).append("]").toString());

                    //Display panel
                    weather_panel.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CurrentTimeResponse> call, Throwable t) {

            }
        });
    }

    public void getDailyForecast() {
        openWeatherInterface.getDailyForecast(
                String.valueOf(LATITUDE),
                String.valueOf(LONGITUDE),
                "current,minutely,hourly,alerts",
                Common.APP_ID
        ).enqueue(new Callback<DailyForecastResponse>() {
            @Override
            public void onResponse(Call<DailyForecastResponse> call, Response<DailyForecastResponse> response) {
                if (response.isSuccessful()) {
                    List<DailyForecastResponse.Daily> list = response.body().daily;
                    list.remove(0);
                    displayForecastWeather(list);
                }
            }

            @Override
            public void onFailure(Call<DailyForecastResponse> call, Throwable t) {

            }
        });
    }

    private void getForecastWeatherInformation() {
        compositeDisposable.add(mService.getForecastWeatherByLating(
                String.valueOf(LATITUDE),
                String.valueOf(LONGITUDE),
                "3283949caaf686434ed5dec14cd161b5",
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForecastResult>() {
                               @Override
                               public void accept(WeatherForecastResult weatherForecastResult) throws Exception {
//                                   displayForecastWeather(weatherForecastResult);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Log.d("Error",""+throwable.getMessage());
                               }
                           }
                )
        );
    }

//    private void displayForecastWeather(WeatherForecastResult weatherForecastResult) {
//        txt_city_name.setText(new StringBuilder(weatherForecastResult.city.name));
//        txt_geo_coords.setText(new StringBuilder(weatherForecastResult.city.coord.toString()));
//
//        WeatherForecastAdapter adapter = new WeatherForecastAdapter(getContext(), weatherForecastResult);
//        recycler_forecast.setAdapter(adapter);
//    }

    private void displayForecastWeather(List<DailyForecastResponse.Daily> list) {
        WeatherForecastAdapter adapter = new WeatherForecastAdapter(list);
        recycler_forecast.setAdapter(adapter);
    }

    private void getWeatherInformation() {
        compositeDisposable.add(mService.getWeatherByLating(
                String.valueOf(LATITUDE),
                String.valueOf(LONGITUDE),
                "3283949caaf686434ed5dec14cd161b5",
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {
//                        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
//                        .append(weatherResult.getWeather().get(0).getIcon())
//                        .append(".png").toString()).into(img_weather);

                        //load information
//                        txt_city_name.setText(weatherResult.getName());
//                        txt_description.setText(new StringBuilder("Cuaca di ")
//                        .append(weatherResult.getName().toString()));
//                        txt_temperatur.setText(new StringBuilder(
//                                String.valueOf(weatherResult.getMain().getTemp())).append("°C").toString());
//                        txt_date_time.setText(Common.convertUnixToDate(weatherResult.getDt()));
//                        txt_pressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append(" hpa").toString());
//                        txt_humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append(" %").toString());
//                        txt_sunrise.setText(Common.convertUnixToHour(weatherResult.getSys().getSunrise()));
//                        txt_sunset.setText(Common.convertUnixToHour(weatherResult.getSys().getSunset()));
//                        txt_geo_coords.setText(new StringBuilder("[").append(weatherResult.getCoord().toString()).append("]").toString());

                        //Display panel
                        weather_panel.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
//                        Toast.makeText(getActivity(), ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("error", throwable.getMessage());
                    }
                })
        );
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        //locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(5000);
//        locationRequest.setFastestInterval(3000);
//        locationRequest.setSmallestDisplacement(10.0f);
    }

    private void buildLocationCallBack() {
        locationCallback = new LocationCallback(){

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                //common.current_location = locationResult.getLastLocation();
            }
        };
    }
}

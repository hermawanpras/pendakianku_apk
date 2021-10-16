package com.hermawan.pendakian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hermawan.pendakian.R;
import com.hermawan.pendakian.api.response.DailyForecastResponse;
import com.hermawan.pendakian.common.Common;
import com.hermawan.pendakian.model.WeatherForecastResult;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder> {
    private List<DailyForecastResponse.Daily> list;

    public WeatherForecastAdapter(List<DailyForecastResponse.Daily> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_weather_forecast, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //load icon
        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
        .append(list.get(position).weather.get(0).icon)
        .append(".png").toString()).into(holder.img_weather);

        holder.txt_date_time.setText(new StringBuilder(Common.convertUnixToDate(list.get(position).dt)));

        holder.txt_description.setText(new StringBuilder(list.get(position).weather.get(0).description));
        double celcius = Math.ceil(Float.parseFloat(String.valueOf(list.get(position).temp.day)) - 273.15F);
        holder.txt_temperature.setText(new StringBuilder(String.valueOf((int) celcius)).append("°C"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txt_date_time, txt_description, txt_temperature;
        ImageView img_weather;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_weather = (ImageView)itemView.findViewById(R.id.img_weather);
            txt_date_time = (TextView)itemView.findViewById(R.id.txt_date);
            txt_description = (TextView)itemView.findViewById(R.id.txt_description);
            txt_temperature = (TextView)itemView.findViewById(R.id.txt_temperature);
        }
    }
}

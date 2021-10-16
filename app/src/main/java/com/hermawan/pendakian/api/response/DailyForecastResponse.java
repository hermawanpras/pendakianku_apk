package com.hermawan.pendakian.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyForecastResponse {
    @SerializedName("lat")
    public double lat;
    @SerializedName("lon")
    public double lon;
    @SerializedName("timezone")
    public String timezone;
    @SerializedName("timezone_offset")
    public int timezone_offset;
    @SerializedName("daily")
    public List<Daily> daily;

    public class Temp{
        @SerializedName("day")
        public double day;
        @SerializedName("min")
        public double min;
        @SerializedName("max")
        public double max;
        @SerializedName("night")
        public double night;
        @SerializedName("eve")
        public double eve;
        @SerializedName("morn")
        public double morn;
    }

    public class FeelsLike{
        @SerializedName("day")
        public double day;
        @SerializedName("night")
        public double night;
        @SerializedName("eve")
        public double eve;
        @SerializedName("morn")
        public double morn;
    }

    public class Weather{
        @SerializedName("id")
        public int id;
        @SerializedName("main")
        public String main;
        @SerializedName("description")
        public String description;
        @SerializedName("icon")
        public String icon;
    }

    public class Daily{
        @SerializedName("dt")
        public int dt;
        @SerializedName("sunrise")
        public int sunrise;
        @SerializedName("sunset")
        public int sunset;
        @SerializedName("moonrise")
        public int moonrise;
        @SerializedName("moonset")
        public int moonset;
        @SerializedName("moon_phase")
        public double moon_phase;
        @SerializedName("temp")
        public Temp temp;
        @SerializedName("feels_like")
        public FeelsLike feels_like;
        @SerializedName("pressure")
        public int pressure;
        @SerializedName("humidity")
        public int humidity;
        @SerializedName("dew_point")
        public double dew_point;
        @SerializedName("wind_speed")
        public double wind_speed;
        @SerializedName("wind_deg")
        public int wind_deg;
        @SerializedName("wind_gust")
        public double wind_gust;
        @SerializedName("weather")
        public List<Weather> weather;
        @SerializedName("clouds")
        public int clouds;
        @SerializedName("pop")
        public double pop;
        @SerializedName("rain")
        public double rain;
        @SerializedName("uvi")
        public double uvi;
    }
}

package com.hermawan.pendakian.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentTimeResponse {
    @SerializedName("coord")
    public Coord coord;
    @SerializedName("weather")
    public List<Weather> weather;
    @SerializedName("base")
    public String base;
    @SerializedName("main")
    public Main main;
    @SerializedName("visibility")
    public int visibility;
    @SerializedName("wind")
    public Wind wind;
    @SerializedName("clouds")
    public Clouds clouds;
    @SerializedName("dt")
    public int dt;
    @SerializedName("sys")
    public Sys sys;
    @SerializedName("timezone")
    public int timezone;
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("cod")
    public int cod;

    public class Coord{
        @SerializedName("lon")
        public double lon;
        @SerializedName("lat")
        public double lat;
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

    public class Main{
        @SerializedName("temp")
        public double temp;
        @SerializedName("feels_like")
        public double feels_like;
        @SerializedName("temp_min")
        public double temp_min;
        @SerializedName("temp_max")
        public double temp_max;
        @SerializedName("pressure")
        public int pressure;
        @SerializedName("humidity")
        public int humidity;
        @SerializedName("sea_level")
        public int sea_level;
        @SerializedName("grnd_level")
        public int grnd_level;
    }

    public class Wind{
        @SerializedName("speed")
        public double speed;
        @SerializedName("deg")
        public int deg;
        @SerializedName("gust")
        public double gust;
    }

    public class Clouds{
        @SerializedName("all")
        public int all;
    }

    public class Sys{
        @SerializedName("type")
        public int type;
        @SerializedName("id")
        public int id;
        @SerializedName("country")
        public String country;
        @SerializedName("sunrise")
        public int sunrise;
        @SerializedName("sunset")
        public int sunset;
    }
}
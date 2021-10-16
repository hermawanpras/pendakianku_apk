package com.hermawan.pendakian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;

import com.hermawan.pendakian.api.ApiClient;
import com.hermawan.pendakian.api.ApiInterface;
import com.hermawan.pendakian.api.response.TitikJalurResponse;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

public class TrackingActivity extends AppCompatActivity implements OnMapReadyCallback{

    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";
    private MapView mapView;
    private ApiInterface apiInterface;

    private List<Point> routeCoordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_tracking);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        apiInterface = ApiClient.getClient();
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(-118.39439114221236, 33.397676454651766)));
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(-54.14164, -33.981818)));
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(-56.990533, -30.583266)));

        apiInterface.getTitikJalur(
                "2"
        ).enqueue(new Callback<TitikJalurResponse>() {
            @Override
            public void onResponse(Call<TitikJalurResponse> call, Response<TitikJalurResponse> response) {
                Log.e("data", response.message());
                if (response.body().status) {
                    List<Point> jalur = new ArrayList<>();
                    for (TitikJalurResponse.TitikJalurModel model : response.body().data) {
                        jalur.add(Point.fromLngLat(Double.parseDouble(model.longitude), Double.parseDouble(model.latitude)));
                    }

                    Log.e("data", String.valueOf(response.body().data.size()));

                    mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/hermawanpras/ckutqwy3q3itq19o3bhca1nua")
                            // Add the SymbolLayer icon image to the map style
                            .withImage(ICON_ID, BitmapFactory.decodeResource(
                                    TrackingActivity.this.getResources(), R.drawable.mapbox_marker_icon_default))
                            // Adding a GeoJson source for the SymbolLayer icons.
                            .withSource(new GeoJsonSource(SOURCE_ID,
                                    FeatureCollection.fromFeatures(symbolLayerIconFeatureList)))
                            .withLayer(new SymbolLayer(LAYER_ID, SOURCE_ID)
                                    .withProperties(
                                            iconImage(ICON_ID),
                                            iconAllowOverlap(true),
                                            iconIgnorePlacement(true)
                                    )
                            ), new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
                            initRouteCoordinates(style);

                            // Create the LineString from the list of coordinates and then make a GeoJSON
                            // FeatureCollection so we can add the line to our map as a layer.
                            style.addSource(new GeoJsonSource("line-source",
                                    FeatureCollection.fromFeatures(new Feature[] {Feature.fromGeometry(
                                            LineString.fromLngLats(jalur)
                                    )})));

                            // The layer properties for our line. This is where we make the line dotted, set the
                            // color, etc.
                            style.addLayer(new LineLayer("linelayer", "line-source").withProperties(
                                    PropertyFactory.lineDasharray(new Float[] {0.01f, 2f}),
                                    PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
                                    PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND),
                                    PropertyFactory.lineWidth(5f),
                                    PropertyFactory.lineColor(Color.parseColor("#e55e5e"))
                            ));
// Map is set up and the style has loaded. Now you can add additional data or make other map adjustments.


                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<TitikJalurResponse> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void initRouteCoordinates(Style style) {
// Create a list to store our line coordinates.
        routeCoordinates = new ArrayList<>();
        apiInterface.getTitikJalur(
                "2"
        ).enqueue(new Callback<TitikJalurResponse>() {
            @Override
            public void onResponse(Call<TitikJalurResponse> call, Response<TitikJalurResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().status) {
                        for (TitikJalurResponse.TitikJalurModel model : response.body().data) {
                            routeCoordinates.add(Point.fromLngLat(Double.parseDouble(model.longitude), Double.parseDouble(model.latitude)));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TitikJalurResponse> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
//        routeCoordinates.add(Point.fromLngLat(-118.39439114221236, 33.397676454651766));
//        routeCoordinates.add(Point.fromLngLat(-118.39421054012902, 33.39769799454838));
//        routeCoordinates.add(Point.fromLngLat(-118.39408583869053, 33.39761901490136));
//        routeCoordinates.add(Point.fromLngLat(-118.39388373635917, 33.397328225582285));
//        routeCoordinates.add(Point.fromLngLat(-118.39372033447427, 33.39728514560042));
//        routeCoordinates.add(Point.fromLngLat(-118.3930882271826, 33.39756875508861));
//        routeCoordinates.add(Point.fromLngLat(-118.3928216241072, 33.39759029501192));
//        routeCoordinates.add(Point.fromLngLat(-118.39227981785722, 33.397234885594564));
//        routeCoordinates.add(Point.fromLngLat(-118.392021814881, 33.397005125197666));
//        routeCoordinates.add(Point.fromLngLat(-118.39090810203379, 33.396814854409186));
//        routeCoordinates.add(Point.fromLngLat(-118.39040499623022, 33.39696563506828));
//        routeCoordinates.add(Point.fromLngLat(-118.39005669221234, 33.39703025527067));
//        routeCoordinates.add(Point.fromLngLat(-118.38953208616074, 33.39691896489222));
//        routeCoordinates.add(Point.fromLngLat(-118.38906338075398, 33.39695127501678));
//        routeCoordinates.add(Point.fromLngLat(-118.38891287901787, 33.39686511465794));
//        routeCoordinates.add(Point.fromLngLat(-118.38898167981154, 33.39671074380141));
//        routeCoordinates.add(Point.fromLngLat(-118.38984598978178, 33.396064537239404));
//        routeCoordinates.add(Point.fromLngLat(-118.38983738968255, 33.39582400356976));
//        routeCoordinates.add(Point.fromLngLat(-118.38955358640874, 33.3955978295119));
//        routeCoordinates.add(Point.fromLngLat(-118.389041880506, 33.39578092284221));
//        routeCoordinates.add(Point.fromLngLat(-118.38872797688494, 33.3957916930261));
//        routeCoordinates.add(Point.fromLngLat(-118.38817327048618, 33.39561218978703));
//        routeCoordinates.add(Point.fromLngLat(-118.3872530598711, 33.3956265500598));
//        routeCoordinates.add(Point.fromLngLat(-118.38653065153775, 33.39592811523983));
//        routeCoordinates.add(Point.fromLngLat(-118.38638444985126, 33.39590657490452));
//        routeCoordinates.add(Point.fromLngLat(-118.38638874990086, 33.395737842093304));
//        routeCoordinates.add(Point.fromLngLat(-118.38723155962309, 33.395027006653244));
//        routeCoordinates.add(Point.fromLngLat(-118.38734766096238, 33.394441819579285));
//        routeCoordinates.add(Point.fromLngLat(-118.38785936686516, 33.39403972556368));
//        routeCoordinates.add(Point.fromLngLat(-118.3880743693453, 33.393616088784825));
//        routeCoordinates.add(Point.fromLngLat(-118.38791956755958, 33.39331092541894));
//        routeCoordinates.add(Point.fromLngLat(-118.3874852625497, 33.39333964672257));
//        routeCoordinates.add(Point.fromLngLat(-118.38686605540683, 33.39387816940854));
//        routeCoordinates.add(Point.fromLngLat(-118.38607484627983, 33.39396792286514));
//        routeCoordinates.add(Point.fromLngLat(-118.38519763616081, 33.39346171215717));
//        routeCoordinates.add(Point.fromLngLat(-118.38523203655761, 33.393196040109466));
//        routeCoordinates.add(Point.fromLngLat(-118.3849955338295, 33.393023711860515));
//        routeCoordinates.add(Point.fromLngLat(-118.38355931726203, 33.39339708930139));
//        routeCoordinates.add(Point.fromLngLat(-118.38323251349217, 33.39305243325907));
//        routeCoordinates.add(Point.fromLngLat(-118.3832583137898, 33.39244928189641));
//        routeCoordinates.add(Point.fromLngLat(-118.3848751324406, 33.39108499551671));
//        routeCoordinates.add(Point.fromLngLat(-118.38522773650804, 33.38926830725471));
//        routeCoordinates.add(Point.fromLngLat(-118.38508153482152, 33.38916777794189));
//        routeCoordinates.add(Point.fromLngLat(-118.38390332123025, 33.39012280171983));
//        routeCoordinates.add(Point.fromLngLat(-118.38318091289693, 33.38941192035707));
//        routeCoordinates.add(Point.fromLngLat(-118.38271650753981, 33.3896129783018));
//        routeCoordinates.add(Point.fromLngLat(-118.38275090793661, 33.38902416443619));
//        routeCoordinates.add(Point.fromLngLat(-118.38226930238106, 33.3889451769069));
//        routeCoordinates.add(Point.fromLngLat(-118.38258750605169, 33.388420985121336));
//        routeCoordinates.add(Point.fromLngLat(-118.38177049662707, 33.388083490107284));
//        routeCoordinates.add(Point.fromLngLat(-118.38080728551597, 33.38836353925403));
//        routeCoordinates.add(Point.fromLngLat(-118.37928506795642, 33.38717870977523));
//        routeCoordinates.add(Point.fromLngLat(-118.37898406448423, 33.3873079646849));
//        routeCoordinates.add(Point.fromLngLat(-118.37935386875012, 33.38816247841951));
//        routeCoordinates.add(Point.fromLngLat(-118.37794345248027, 33.387810620840135));
//        routeCoordinates.add(Point.fromLngLat(-118.37546662390886, 33.38847843095069));
//        routeCoordinates.add(Point.fromLngLat(-118.37091717142867, 33.39114243958559));

    }
}

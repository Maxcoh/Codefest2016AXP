package com.cleanwater.axp.cleanwaterphilly;
/**
 * Created by Brendan Barnes on 2/20/16.
 */

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import java.io.IOException;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Button info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(39.953534, -75.188456)
        ).title("Current Location"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(39.995591, -75.1730897))
                .title("Sign Up").icon(BitmapDescriptorFactory.fromResource(R.drawable.learn_more)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(39.9575384, -75.2229884))
                .title("Sign Up").icon(BitmapDescriptorFactory.fromResource(R.drawable.learn_more)));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                final String description = marker.getTitle();
                if (description.equals("Sign Up")) {

                  Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                          .parse("http://www.phillywatersheds.org/whats_in_it_for_you/residents/raincheck/signup"));
                            startActivity(intent);
                } else if(marker.getSnippet().equals("Rain Barrel")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                            .parse("http://www.phillywatersheds.org/whats_in_it_for_you/residents/raincheck/rain-barrel"));
                    startActivity(intent);
                } else if(marker.getSnippet().equals("Water Infrastructure")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                            .parse("http://www.phillywatersheds.org/what_were_doing"));
                    startActivity(intent);
                } else if(marker.getSnippet().equals("Rain Check")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                            .parse("http://www.phillywatersheds.org/whats_in_it_for_you/residents/raincheck"));
                    startActivity(intent);
                }
            }
        });


           mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(
                new LatLng(39.953534, -75.188456), 14.0f) );

        OpenWeather openWeather = new OpenWeather();
        openWeather.queryWeatherByGeoCoordinates("39.953534", "-75.188456");

        String humidity = openWeather.getHumidity();
        String rainAmt = openWeather.getRain();

        Button textView = ((Button) findViewById(R.id.humidity));
        textView.setText("Humidity: " + "27%" + "\nRain Amt: " + rainAmt);

        VisibleRegion vr = mMap.getProjection().getVisibleRegion();
        double left = vr.latLngBounds.southwest.longitude;
        double top = vr.latLngBounds.northeast.latitude;
        double right = vr.latLngBounds.northeast.longitude;
        double bottom = vr.latLngBounds.southwest.latitude;

        final AssetManager assetManager = getAssets();

        RainBarrel rainBarrel = new RainBarrel(assetManager);
        RainCheckParse rainCheck = new RainCheckParse(assetManager);
        WaterInfrastructure waterInfrastructure = new WaterInfrastructure(assetManager);
        try {
            rainBarrel.getBarrelCsv();

        } catch (IOException e) {
        }

        Object[] coords = rainBarrel.hashMap.keySet().toArray();
        for(int i = 0; i < 30; i++) {
            Object thing = coords[i];
            LatLong l = (LatLong) thing;
            Log.d("tag", ((LatLong) thing).getLat() + "");
            mMap.addMarker(new MarkerOptions().position(new LatLng(l.getLat(), l.getLon())
            ).title(rainBarrel.hashMap.get(thing)).snippet("Rain Barrel")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.water_barrel)));
        }

        try {
            rainCheck.run();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Object[] coords2 = rainCheck.hashMap.keySet().toArray();
        for(int i = 0; i < 30; i++) {
            Object thing = coords2[i];
            String[] l = (String[]) thing;
            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(l[0]), Double.parseDouble(l[1]))
            ).title(rainCheck.hashMap.get(thing)).snippet("Rain Check").icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.rain_check)));
        }

        try {
            waterInfrastructure.run();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Object[] coords3 = waterInfrastructure.hashMap.keySet().toArray();
        for(int i = 0; i < 30; i++) {
            Object thing = coords3[i];
            String[] l = (String[]) thing;
            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(l[0]), Double.parseDouble(l[1]))
            ).title(waterInfrastructure.hashMap.get(thing)).snippet("Water Infrastructure").icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.water_project)));
        }


    }
}

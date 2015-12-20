package com.example.june1.projects;

/**
 * Created by june1 on 2015-12-19.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Gmap extends AppCompatActivity {
// AIzaSyAv7EAaKGqTf7-jDeb-8qV_JUe8pXQbSv8


    private GoogleMap map;
    Button bt;
    Intent intents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmap);

        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("lat", 0);
        double lng = intent.getDoubleExtra("lng", 0);


        intents = new Intent(this, DataBase.class);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);

        LatLng SEOUL = new LatLng(lat, lng);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        Marker seoul = map.addMarker(new MarkerOptions().position(SEOUL).title("twosome place"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        Intent in = this.getIntent();
        Toast.makeText(this.getApplicationContext(), in.getStringExtra("p"), Toast.LENGTH_LONG).show();

        bt = (Button)this.findViewById(R.id.button);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intents);
            }
        });

    }
}
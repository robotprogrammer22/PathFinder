package com.pathfinder.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import com.google.maps.android.data.kml.KmlLayer;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback
{
    SupportMapFragment mapFrag;
    GoogleMap thisMap;
    KmlLayer buildingLayerKML;
    KmlLayer pedwayLayer;
    GeoJsonLayer bluePhoneLayer;
    GeoJsonLayer buildingLayerJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpNavComponents();
        setUpMapComponents();
        PathfinderDataHandler handler = new PathfinderDataHandler();
    }

    private void setUpNavComponents()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setUpMapComponents()
    {
        mapFrag = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
    }

    public void onMapReady(GoogleMap googleMap)
    {
        thisMap = googleMap;
        LatLng campus = new LatLng(35.180436, -111.654084);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(campus));
        googleMap.setMinZoomPreference(15);
        googleMap.setMaxZoomPreference(20);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            Toast.makeText(getApplicationContext(), "Settings selected", Toast.LENGTH_LONG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Context context = getApplicationContext();
        Log.d(null, "onNavigationItemSelected Called");

        if (id == R.id.buildings)
        {
            // Handle the camera action
            if(item.getTitle() == context.getResources().getString(R.string.building_hide))
            {
                item.setTitle(context.getResources().getString(R.string.building_select));
                removeBuildingLayer();
                Log.d("NAVITEM", "Buildings selected");
            }
            else
            {
                item.setTitle(context.getResources().getString(R.string.building_hide));
                addBuildingLayer();
                Log.d("NAVITEM", "Buildings deselected");
            }
        }
        else if (id == R.id.blue_phones)
        {
            if(item.getTitle() == context.getResources().getString(R.string.blue_phone_hide))
            {
                item.setTitle(context.getResources().getString(R.string.blue_phone_select));
                removeBluePhoneLayer();
                Log.d("NAVITEM", "Blue Phones selected");
            }
            else
            {
                item.setTitle(context.getResources().getString(R.string.blue_phone_hide));
                addBluePhoneLayer();
                Log.d("NAVITEM", "Blue Phones deselected");
            }
        }
        else if (id == R.id.bus_stops)
        {
            if(item.getTitle() == context.getResources().getString(R.string.bus_stop_hide))
            {
                item.setTitle(context.getResources().getString(R.string.bus_stop_select));
                Log.d("NAVITEM", "Bus Stops selected");
            }
            else
            {
                item.setTitle(context.getResources().getString(R.string.bus_stop_hide));
                Log.d("NAVITEM", "Bus Stops deselected");
            }
        }
        else if (id == R.id.pedways)
        {
            if(item.getTitle() == context.getResources().getString(R.string.pedway_hide))
            {
                item.setTitle(context.getResources().getString(R.string.pedway_select));
                Log.d("NAVITEM", "Buildings selected");
            }
            else
            {
                item.setTitle(context.getResources().getString(R.string.pedway_hide));
                Log.d("NAVITEM", "Buildings deselected");
            }
        }
        return true;
    }

    private void addBuildingLayer()
    {
        try
        {
            //buildingLayerKML = new KmlLayer(thisMap, R.raw.buildingshape, getApplicationContext());
            //buildingLayerKML.addLayerToMap();

            buildingLayerJSON = new GeoJsonLayer(thisMap, R.raw.buildings, getApplicationContext());
            buildingLayerJSON.addLayerToMap();
        }
        catch (JSONException e)
        {
            Log.e("EXCEPTION",e.getMessage(), e.getCause());
        }
        catch (IOException f)
        {
            Log.e("EXCEPTION",f.getMessage(), f.getCause());
        }
    }

    private void removeBuildingLayer()
    {
        try
        {
            buildingLayerKML.removeLayerFromMap();
        }
        catch(NullPointerException e)
        {
            Log.e("EXCEPTION", e.getMessage(), e.getCause());
        }
    }

    private void addBluePhoneLayer()
    {
        try
        {
            //buildingLayerKML = new KmlLayer(thisMap, R.raw.buildingshape, getApplicationContext());
            //buildingLayerKML.addLayerToMap();

            bluePhoneLayer = new GeoJsonLayer(thisMap, R.raw.bluephones, getApplicationContext());
            bluePhoneLayer.addLayerToMap();
        }
        catch (JSONException e)
        {
            Log.e("EXCEPTION",e.getMessage(), e.getCause());
        }
        catch (IOException f)
        {
            Log.e("EXCEPTION",f.getMessage(), f.getCause());
        }
    }

    private void removeBluePhoneLayer()
    {
        try
        {
            bluePhoneLayer.removeLayerFromMap();
        }
        catch(NullPointerException e)
        {
            Log.e("EXCEPTION", e.getMessage(), e.getCause());
        }
    }


    private void togglePedwayLayer()
    {

    }
}

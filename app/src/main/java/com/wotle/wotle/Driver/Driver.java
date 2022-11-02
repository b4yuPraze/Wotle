package com.wotle.wotle.Driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.wotle.wotle.API.PrefManager;
import com.wotle.wotle.Auth.Login;
import com.wotle.wotle.R;

public class Driver extends AppCompatActivity implements OnMapReadyCallback, LocationListener, DrawerLayout.DrawerListener, NavigationView.OnNavigationItemSelectedListener {

    static final float END_SCALE = 0.7f;

    private PrefManager pref;
    private ImageView myLoc, imgProfile, onOff, menuDriver, imgLogoutDriver;
    private TextView unameProfilSection, platProfileSection, merkProfileSection, saldoProfileSection, logoutDriver;

    private GoogleMap gMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationManager locationManager;
    private LatLng currentLocation;
    public Double currLat, currLong;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private NestedScrollView nestedScrollView;
    private LinearLayout contentView;


    int ALL_PERMISSION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        pref = new PrefManager(this);
        myLoc = findViewById(R.id.myLocDriver);
        imgProfile = findViewById(R.id.imageSectionDriver);
        onOff = findViewById(R.id.powerOnOfDriver);
        unameProfilSection = findViewById(R.id.namaSectionDriver);
        platProfileSection = findViewById(R.id.platSectionDriver);
        merkProfileSection = findViewById(R.id.merkMobilSectionDriver);
        saldoProfileSection = findViewById(R.id.saldoSectionDriver);
        menuDriver = findViewById(R.id.hamburgerMenuDriver);
        contentView = findViewById(R.id.content_view_home_driver);
        logoutDriver = findViewById(R.id.logoutDriver);
        imgLogoutDriver = findViewById(R.id.imgLogoutDriver);

        // Bottom Navigation View
        nestedScrollView = findViewById(R.id.bottom_sheet);

        // Menu hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView();
        drawerLayout.setDrawerListener(this);

        checkSelfPermission();

        if (pref.readData() == null) {
            startActivity(new Intent(this, Login.class));
            finish();
        }

        gettingCurrentMyLocation();
        initMap();

        Glide.with(this).load(pref.readData().get(0).getFoto_profile()).circleCrop().into(imgProfile);
        unameProfilSection.setText(pref.readData().get(0).getNama_lengkap());

        myLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLocation == null){
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    Toast.makeText(Driver.this, "Mohon Aktifkan GPS/ Lokasi", Toast.LENGTH_SHORT).show();
                }else{
                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLocation, 16);
                    gMap.animateCamera(update);
                }
            }
        });

        logoutDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref.logout();
                startActivity(new Intent(Driver.this, Login.class));
                finish();
            }
        });

        imgLogoutDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref.logout();
                startActivity(new Intent(Driver.this, Login.class));
                finish();
            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.getUiSettings().setMyLocationButtonEnabled(false);
        gMap.getUiSettings().setCompassEnabled(true);

        if (currentLocation != null || currLat != null || currLong != null){
            currentLocation = new LatLng(currLat, currLong);
//        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Desa Tambakbaya, Garawangi, Kuningan, Jawa Barat"));
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 500);
                return;
            }

            gMap.setMyLocationEnabled(true);
        }else{
            checkSelfPermission();
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void gettingCurrentMyLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        String provider = locationManager.getBestProvider(criteria, false);
        if (provider != null && !provider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 500);
                return;
            }
            Location location = locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider, 2000, 1, this);

            if (location != null){
                onLocationChanged(location);
            }else{
                Toast.makeText(getApplicationContext(), "Location not found!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Provider not found!", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigationView(){
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.setCheckedItem(R.id.navViewHome);

        menuDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        nestedScrollView.animate().translationY(nestedScrollView.getHeight()).setDuration(300);
        nestedScrollView.setVisibility(View.GONE);
        final float diffScaledOffset = slideOffset * ( 1- END_SCALE );
        final float offsetScale = 1 - diffScaledOffset;
        contentView.setScaleX(offsetScale);
        contentView.setScaleY(offsetScale);

        final float xOffset = drawerView.getWidth() * slideOffset;
        final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
        final float xTranslation = xOffset - xOffsetDiff;
        contentView.setTranslationX(xTranslation);
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        nestedScrollView.animate().translationY(nestedScrollView.getHeight()).setDuration(300);
        nestedScrollView.setVisibility(View.GONE);
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        nestedScrollView.animate().translationY(0).setDuration(0);
        nestedScrollView.setVisibility(View.VISIBLE);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getTitle().equals("Profile")){
            startActivity(new Intent(Driver.this, ProfileDriver.class));
            finish();
        }else if (item.getTitle().equals("Pengaturan")){
            startActivity(new Intent(Driver.this, SettingsDriver.class));
            finish();
        }

        return false;
    }

    private void checkSelfPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        }else{
            requestRequirementPermission();
        }
    }

    private void requestRequirementPermission(){
        final String[] permissions = new String[]{
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_NUMBERS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
        };

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, String.valueOf(permissions))){

            new AlertDialog.Builder(this).setTitle("Permission needed")
                    .setMessage("This permission is needed if you dont allow you cant open")
                    .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(Driver.this, permissions, ALL_PERMISSION);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();

        }else{
            ActivityCompat.requestPermissions(Driver.this, permissions, ALL_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ALL_PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(Driver.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Driver.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        currLat = location.getLatitude();
        currLong = location.getLongitude();
    }
}
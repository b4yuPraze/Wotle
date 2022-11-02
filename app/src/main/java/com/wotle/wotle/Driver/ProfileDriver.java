package com.wotle.wotle.Driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wotle.wotle.API.PrefManager;
import com.wotle.wotle.R;

public class ProfileDriver extends AppCompatActivity {

    private PrefManager pref;

    private ImageView profileUser, backProfileDriver;
    private TextView unameProfileDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_driver);
        pref = new PrefManager(this);

        profileUser = findViewById(R.id.profileDriver);
        backProfileDriver = findViewById(R.id.backProfileDriver);
        unameProfileDriver = findViewById(R.id.unameProfileDriver);


        Glide.with(this).load(pref.readData().get(0).getFoto_profile()).circleCrop().into(profileUser);
        unameProfileDriver.setText(pref.readData().get(0).getNama_lengkap());

        backProfileDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileDriver.this, Driver.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ProfileDriver.this, Driver.class));
        finish();
    }
}
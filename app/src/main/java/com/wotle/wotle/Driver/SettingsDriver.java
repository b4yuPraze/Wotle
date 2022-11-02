package com.wotle.wotle.Driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wotle.wotle.API.PrefManager;
import com.wotle.wotle.R;

public class SettingsDriver extends AppCompatActivity {

    private PrefManager pref;

    private LinearLayout accountSettingDriver;

    private ImageView backProfileDriverSettingDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_driver);
        pref = new PrefManager(this);

        accountSettingDriver = findViewById(R.id.accountSettingDriver);
        backProfileDriverSettingDriver = findViewById(R.id.backProfileDriverSettingDriver);

        accountSettingDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsDriver.this, MyProfile.class));
                finish();
            }
        });

        backProfileDriverSettingDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsDriver.this, Driver.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Driver.class));
        finish();
    }
}
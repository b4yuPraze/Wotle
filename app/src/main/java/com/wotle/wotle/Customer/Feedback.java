package com.wotle.wotle.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wotle.wotle.R;

public class Feedback extends AppCompatActivity {
    private TextView ksMasukkan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        ksMasukkan = findViewById(R.id.kasihMasukan);
        ksMasukkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://hybrid.uniku.ac.id/"));
                startActivity(intent);
            }
        });
    }
}
package com.wotle.wonderfulshuttle.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.wotle.wonderfulshuttle.R;

public class Sopir extends AppCompatActivity {

    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sopir);

        View bottomSheet = findViewById(R.id.bottom_sheet);

        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        System.out.println("Collapse");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        System.out.println("Dragging");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        System.out.println("Expanding");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        System.out.println("Settling");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                System.out.println("Slidding");
            }
        });

    }
}
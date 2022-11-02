package com.wotle.wotle.Auth;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.wotle.wotle.R;

public class ProgressButton {

    private ProgressBar progressBar;
    private Button btnLogin;

    ProgressButton(Context ctx, ProgressBar loading, Button buttonLogin){
        progressBar = loading;
        btnLogin = buttonLogin;
    }


    void buttonActivated(){
        progressBar.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.GONE);
    }
    void buttonInActive(){
        progressBar.setVisibility(View.GONE);
        btnLogin.setVisibility(View.VISIBLE);
    }
}

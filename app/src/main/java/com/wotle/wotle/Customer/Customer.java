package com.wotle.wotle.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wotle.wotle.API.PrefManager;
import com.wotle.wotle.Auth.Login;
import com.wotle.wotle.Model.DataModel;
import com.wotle.wotle.R;

import java.util.List;

public class Customer extends AppCompatActivity {

    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    PrefManager pref;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        googleSignInClient = GoogleSignIn.getClient(Customer.this, GoogleSignInOptions.DEFAULT_SIGN_IN);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        pref = new PrefManager(Customer.this);
        checkSession();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.beranda){
                    open_fragment(new FragmentHomeCustomer());
                    return true;
                }
                if (item.getItemId() == R.id.explore){
                    open_fragment(new FragmentExploreCustomer());
                    return true;
                }
                if (item.getItemId() == R.id.order){
                    open_fragment(new FragmentOrderCustomer());
                    return true;
                }
                if (item.getItemId() == R.id.profile){
                    open_fragment(new FragmentProfileCustomer());
                    return true;
                }
                return false;
            }
        });

        open_fragment(new FragmentHomeCustomer());

    }

    private void checkSession(){
        if (pref.readData() == null){
            logout();
        }else{
        }

    }

    Boolean open_fragment(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
            return true;
        }
        return false;
    }

    private void logout(){
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseAuth.getInstance().signOut();
                pref.logout();
                startActivity(new Intent(Customer.this, Login.class));
                finish();
            }
        });
    }
}
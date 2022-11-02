package com.wotle.wotle.Customer;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wotle.wotle.API.PrefManager;
import com.wotle.wotle.Auth.Login;
import com.wotle.wotle.Driver.Driver;
import com.wotle.wotle.Driver.ProfileDriver;
import com.wotle.wotle.Model.DataModel;
import com.wotle.wotle.R;

import java.lang.reflect.Type;
import java.util.List;

public class FragmentProfileCustomer extends Fragment {
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private PrefManager pref;
    private LinearLayout csPoint, csRefferal, ksMasukan, csBantuan, csKontak;

    private TextView tvUname, edProfile;
    private ImageView iv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_customer_fragment, container, false);
        if (view != null){
            tvUname = view.findViewById(R.id.unameProfile);
            edProfile = view.findViewById(R.id.editProfile);
            ksMasukan = view.findViewById(R.id.kasihMasukan);
            csPoint = view.findViewById(R.id.cusPoint);
            csRefferal = view.findViewById(R.id.cusRefferal);
            csBantuan = view.findViewById(R.id.pusatBantuan);
            csKontak = view.findViewById(R.id.kontakDarurat);

            iv = view.findViewById(R.id.imageProfile);
            pref = new PrefManager(getActivity());
            firebaseAuth = FirebaseAuth.getInstance();
            googleSignInClient = GoogleSignIn.getClient(view.getContext(), GoogleSignInOptions.DEFAULT_SIGN_IN);

            tvUname.setText(pref.readData().get(0).getNama_lengkap());
            Glide.with(getContext()).load(pref.readData().get(0).getFoto_profile()).circleCrop().into(iv);

//            checkUserLogged();
            tvUname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logout();
                }
            });

            edProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), Edit_Account.class));
                }
            });

           csPoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), Point.class));
                }
            });

            csRefferal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), Refferal.class));
                }
            });

            csKontak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), EmergencyContact.class));
                }
            });

            ksMasukan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    masukkan();
                }
            });

            csBantuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bantuan();
                }
            });

        }
        return view;
    }

//    private void readPreferences() {
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userLogged", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("dataUser", null);
//        Type type = new TypeToken<List<DataModel>>() {
//        }.getType();
//        dataUser = gson.fromJson(json, type);
//        if (dataUser == null) {
//            startActivity(new Intent(getContext(), Login.class));
//            getActivity().finish();
//        }
//    }
//
//    private void checkUserLogged() {
//        readPreferences();

//
//    }

    private void logout(){
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseAuth.getInstance().signOut();
                pref.logout();
                startActivity(new Intent(getContext(), Login.class));
                getActivity().finish();
            }
        });
    }

    private void masukkan(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://hybrid.uniku.ac.id/"));
        startActivity(intent);
    }

    private void Bantuan(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://hybrid.uniku.ac.id/"));
        startActivity(intent);
    }
}

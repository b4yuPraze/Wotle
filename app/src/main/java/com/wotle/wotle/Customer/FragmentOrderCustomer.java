package com.wotle.wotle.Customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wotle.wotle.API.PrefManager;
import com.wotle.wotle.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FragmentOrderCustomer extends Fragment {

    EditText destination, fullname, edTglBerangkat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_customer_fragment, container, false);
        destination = view.findViewById(R.id.titikTujuan);
        fullname = view.findViewById(R.id.namaPemesan);
        edTglBerangkat = view.findViewById(R.id.tglBerangkat);
        if(view != null){

            Bundle bundle = getArguments();
            if (bundle != null){
                String cpos = getArguments().getString("cpos");
                setDestinationFromHome(cpos);
            }



        }
        return view;
    }

    private void setDestinationFromHome(String pos){
        SimpleDateFormat sdf = new SimpleDateFormat("dd - MMM - yyyy HH:mm", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        if (pos.equals("0")){
            fullname.setText(new PrefManager(getContext()).readData().get(0).getNama_lengkap());
            destination.setText("Bekasi");
            edTglBerangkat.setText(currentDateandTime);
        }else if (pos.equals("1")){
            fullname.setText(new PrefManager(getContext()).readData().get(0).getNama_lengkap());
            destination.setText("Jakarta");
            edTglBerangkat.setText(currentDateandTime);
        }else if (pos.equals("2")){
            fullname.setText(new PrefManager(getContext()).readData().get(0).getNama_lengkap());
            destination.setText("Cikarang");
            edTglBerangkat.setText(currentDateandTime);
        }
    }

}

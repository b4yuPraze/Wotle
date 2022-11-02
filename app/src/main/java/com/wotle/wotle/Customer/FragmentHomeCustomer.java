package com.wotle.wotle.Customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.wotle.wotle.API.PrefManager;
import com.wotle.wotle.Adapter.SliderAdapter;
import com.wotle.wotle.Model.SliderItem;
import com.wotle.wotle.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentHomeCustomer extends Fragment {

    private PrefManager pref;
    private TextView uname;
    private ImageView iv;

    List<SliderItem> items;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_customer_fragment, container, false);

        if (view != null){
            pref = new PrefManager(view.getContext());
            uname = view.findViewById(R.id.unameHomeCustomer);
            iv = view.findViewById(R.id.imageProfileHome);
            setupAccountUserLogged();
            setupSlideViewPager(view);
        }

        return view;
    }

    private void setupSlideViewPager(View view){
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager2ImageSlider);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(20));
        compositePageTransformer.addTransformer(((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        }));

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setAdapter(new SliderAdapter(getSlideItem()));
        viewPager2.setCurrentItem(1, true);
    }

    private void setupAccountUserLogged(){
        uname.setText(pref.readData().get(0).getNama_lengkap());
        Glide.with(getContext()).load(pref.readData().get(0).getFoto_profile()).circleCrop().into(iv);
    }

    private List<SliderItem> getSlideItem(){
       items = new ArrayList<>();


        SliderItem sliderBekasi = new SliderItem();
        sliderBekasi.image = R.drawable.icon_background;
        sliderBekasi.titleSlideHome = "Bekasi";
        sliderBekasi.jumlahDriverPerkota = "Driver: 30";
        sliderBekasi.descriptionSlideHome = "Bekasi adalah ibu kota Indonesia dan beberapa industri pun berada di bekasi ...";
        items.add(sliderBekasi);

        SliderItem sliderJakarta = new SliderItem();
        sliderJakarta.image = R.drawable.gambar_jakarta;
        sliderJakarta.titleSlideHome = "Jakarta";
        sliderJakarta.jumlahDriverPerkota = "Driver: 25";
        sliderJakarta.descriptionSlideHome = "Jakarta adalah ibu kota Indonesia dan beberapa industri pun berada di jakarta ...";
        items.add(sliderJakarta);

        SliderItem sliderCikaranag = new SliderItem();
        sliderCikaranag.image = R.drawable.gambar_jakarta;
        sliderCikaranag.titleSlideHome = "Cikarang";
        sliderCikaranag.jumlahDriverPerkota = "Driver: 15";
        sliderCikaranag.descriptionSlideHome = "Cikarang adalah ibu kota Indonesia dan beberapa industri pun berada di cikarang ...";
        items.add(sliderCikaranag);

        return items;
    }

}

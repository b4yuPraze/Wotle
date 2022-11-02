package com.wotle.wotle.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.wotle.wotle.API.PrefManager;
import com.wotle.wotle.Customer.Customer;
import com.wotle.wotle.Customer.FragmentOrderCustomer;
import com.wotle.wotle.Model.SliderItem;
import com.wotle.wotle.R;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{

    private final List<SliderItem> sliderItems;
    EditText fullname, destination;

    public SliderAdapter(List<SliderItem> sliderItems) {
        this.sliderItems = sliderItems;

    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_container,
                        parent,
                        false
                )
        );
    }


    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setMovie(sliderItems.get(position));
        int cpos = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cpos == 0){
                    Bundle bundle = new Bundle();
                    bundle.putString("cpos", "0");
                    Fragment fragment = new FragmentOrderCustomer();
                    fragment.setArguments(bundle);
                    ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
                }else if (cpos == 1){
                    Bundle bundle = new Bundle();
                    bundle.putString("cpos", "1");
                    Fragment fragment = new FragmentOrderCustomer();
                    fragment.setArguments(bundle);
                    ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
                }else if (cpos == 2){
                    Bundle bundle = new Bundle();
                    bundle.putString("cpos", "2");
                    Fragment fragment = new FragmentOrderCustomer();
                    fragment.setArguments(bundle);
                    ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
                }
                Toast.makeText(view.getContext(), ""+cpos, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder{

        private final RoundedImageView imageViewPoster;
        private final TextView judul, jmlDriver, desc;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageSlide);
            judul = itemView.findViewById(R.id.titleSlideHome);
            jmlDriver = itemView.findViewById(R.id.jmlDriverSlideHome);
            desc = itemView.findViewById(R.id.descSlideHome);
        }

        void setMovie(SliderItem sliderItem){
            imageViewPoster.setImageResource(sliderItem.image);
            judul.setText(sliderItem.titleSlideHome);
            jmlDriver.setText(sliderItem.jumlahDriverPerkota);
            desc.setText(sliderItem.descriptionSlideHome);
        }
    }



}

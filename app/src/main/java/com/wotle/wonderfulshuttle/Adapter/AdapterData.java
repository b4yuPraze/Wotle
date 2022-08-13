package com.wotle.wonderfulshuttle.Adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wotle.wonderfulshuttle.Model.DataModel;

import java.util.List;

public class AdapterData {
    private Context ctx;
    private List<DataModel> dataUser;

    public AdapterData(Context ctx, List<DataModel> dataUser) {
        this.ctx = ctx;
        this.dataUser = dataUser;
    }

    public class HolderDataUser extends RecyclerView.ViewHolder{

        public HolderDataUser(@NonNull View itemView) {
            super(itemView);
        }


    }
}

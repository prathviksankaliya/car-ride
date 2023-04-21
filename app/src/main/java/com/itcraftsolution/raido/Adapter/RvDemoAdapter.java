package com.itcraftsolution.raido.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.itcraftsolution.raido.Models.DemoModel;
import com.itcraftsolution.raido.R;
import com.itcraftsolution.raido.databinding.RvSearchRideBinding;
import com.itcraftsolution.raido.databinding.RvdemoviewBinding;

public class RvDemoAdapter extends FirebaseRecyclerAdapter<DemoModel, RvDemoAdapter.viewHolder> {

    Context context;

    public RvDemoAdapter(@NonNull FirebaseRecyclerOptions<DemoModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull RvDemoAdapter.viewHolder holder, int position, @NonNull DemoModel model) {

//        if(model.getName() != null){
//
//            Toast.makeText(context, ""+model.getName(), Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(context, "model null", Toast.LENGTH_SHORT).show();
//        }
//        holder.binding.txDemo.setText("Name: " + model.getName() + "Car num: "+ model.getCarNum());
    }

    @NonNull
    @Override
    public RvDemoAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvdemoview, parent, false);
        return new viewHolder(view);
    }

    static class viewHolder extends RecyclerView.ViewHolder {
        RvdemoviewBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RvdemoviewBinding.bind(itemView);
        }
    }
}

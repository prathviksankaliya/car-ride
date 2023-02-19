package com.itcraftsolution.raido.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itcraftsolution.raido.Models.ResponceModel;
import com.itcraftsolution.raido.R;
import com.itcraftsolution.raido.databinding.RvAdminNotificationBinding;
import com.itcraftsolution.raido.databinding.RvUserNotificationBinding;

import java.util.ArrayList;

public class RvUserNotificationAdapter extends RecyclerView.Adapter<RvUserNotificationAdapter.viewHolder> {

    Context context;
    ArrayList<ResponceModel> list;

    public RvUserNotificationAdapter(Context context, ArrayList<ResponceModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RvUserNotificationAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_user_notification, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvUserNotificationAdapter.viewHolder holder, int position) {

        ResponceModel model = list.get(position);
        if(model.getStatus().equals("accept")){
            holder.binding.vBgRideReq.setBackgroundColor(context.getResources().getColor(R.color.green));
            holder.binding.vSignRidereq.setBackgroundResource(R.drawable.okk);
            holder.binding.txRideReqMsg.setText("Your Ride request is Accept from" + model.getUsername() + "Car Agent.");
        }else{
            holder.binding.vBgRideReq.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.binding.vSignRidereq.setBackgroundResource(R.drawable.close);
            holder.binding.txRideReqMsg.setText("Your Ride request is Decline from" + model.getUsername() + "Car Agent.");
        }
        holder.binding.txRideReqJourneyCity.setText(model.getJourney());
        holder.binding.txRideReqId.setText(model.getAgentid());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class viewHolder extends RecyclerView.ViewHolder {
        RvUserNotificationBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RvUserNotificationBinding.bind(itemView);
        }
    }
}

package com.itcraftsolution.raido.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.itcraftsolution.raido.Models.AgentDetails;
import com.itcraftsolution.raido.R;
import com.itcraftsolution.raido.databinding.RvSearchRideBinding;

import java.util.ArrayList;

public class RvUserActiveRideAdapter extends RecyclerView.Adapter<RvUserActiveRideAdapter.viewHolder> {


    Context context;
    ArrayList<AgentDetails> list;

    public RvUserActiveRideAdapter(Context context, ArrayList<AgentDetails> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_search_ride, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        AgentDetails model = list.get(position);
            holder.binding.txRideViaPlace.setText(model.getJourneyLoc());
            holder.binding.txRideSource.setText(model.getArrivalTime());
            holder.binding.txRideDestination.setText(model.getDepTime());
            holder.binding.txRideHrs.setText(model.getTotalJourney());
            holder.binding.txRideCarName.setText(model.getCarName());
            holder.binding.txRideSeats.setText(model.getEmptySeats());
            holder.binding.txRidePrice.setText(model.getRidePrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class viewHolder extends RecyclerView.ViewHolder {
            RvSearchRideBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RvSearchRideBinding.bind(itemView);
        }
    }
}

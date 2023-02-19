package com.itcraftsolution.raido.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itcraftsolution.raido.Adapter.RvAgentNotificationAdapter;
import com.itcraftsolution.raido.Adapter.RvUserNotificationAdapter;
import com.itcraftsolution.raido.Models.DemoModel;
import com.itcraftsolution.raido.Models.ResponceModel;
import com.itcraftsolution.raido.R;
import com.itcraftsolution.raido.databinding.FragmentUserNotificationBinding;
import com.itcraftsolution.raido.databinding.RvUserNotificationBinding;
import com.itcraftsolution.raido.spf.SpfUserData;

import java.util.ArrayList;

public class UserNotificationFragment extends Fragment {

    private FragmentUserNotificationBinding binding;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private SpfUserData spfUserData;
    private String agentId;
    private ArrayList<ResponceModel> list;
    private RvUserNotificationAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserNotificationBinding.inflate(getLayoutInflater());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Friend");
        spfUserData = new SpfUserData(requireContext());

        list = new ArrayList<>();
        loadData();
        return binding.getRoot();
    }
    private  void loadData(){

        agentId = spfUserData.getSpfAgentRideDetails().getString("agentId", null);
        databaseReference.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sp : snapshot.getChildren()){
                    ResponceModel model = sp.getValue(ResponceModel.class);
                    list.add(model);
                }
                adapter = new RvUserNotificationAdapter(requireContext(), list);
                binding.rvUserNotification.setAdapter(adapter);
                binding.rvUserNotification.setLayoutManager(new LinearLayoutManager(requireContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
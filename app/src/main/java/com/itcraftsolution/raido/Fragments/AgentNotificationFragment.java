package com.itcraftsolution.raido.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itcraftsolution.raido.databinding.FragmentAgentNotificationBinding;


public class AgentNotificationFragment extends Fragment {

    private FragmentAgentNotificationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAgentNotificationBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }
}
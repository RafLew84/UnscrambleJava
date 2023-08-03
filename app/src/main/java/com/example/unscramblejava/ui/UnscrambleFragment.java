package com.example.unscramblejava.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.unscramblejava.R;
import com.example.unscramblejava.databinding.FragmentUnscrambleBinding;


public class UnscrambleFragment extends Fragment {

    private FragmentUnscrambleBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUnscrambleBinding.inflate(inflater);



        return binding.getRoot();
    }
}
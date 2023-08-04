package com.example.unscramblejava.ui;

import static com.example.unscramblejava.data.DataProvider.MAX_NO_OF_WORDS;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.unscramblejava.R;
import com.example.unscramblejava.databinding.FragmentUnscrambleBinding;
import com.example.unscramblejava.viewmodel.GameViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class UnscrambleFragment extends Fragment {

    private GameViewModel viewModel;

    private FragmentUnscrambleBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUnscrambleBinding.inflate(inflater);

        viewModel = new ViewModelProvider(this).get(GameViewModel.class);


        viewModel.uiState.observe(getViewLifecycleOwner(), uiState -> {
            binding.wordCount.setText(uiState.getCurrentWordCount() + "/" + MAX_NO_OF_WORDS);
            binding.textViewUnscrambledWord.setText(uiState.getCurrentScrambledWord());
            binding.score.setText(String.valueOf(uiState.getScore()));

            if (uiState.isGameOver()) {
                showFinalScoreDialog(uiState.getScore());
            }
        });

        binding.skip.setOnClickListener(v -> viewModel.skipWord());
        binding.submit.setOnClickListener(v -> viewModel.checkUserGuess());
        binding.save.setOnClickListener(v -> viewModel.saveGame());
        binding.load.setOnClickListener(v -> viewModel.loadGame());
        binding.userInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.updateUserGuess(editable.toString());
            }
        });



        return binding.getRoot();
    }

    private void showFinalScoreDialog(int score) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Gratulacje")
                .setMessage("Wynik: " + score)
                .setCancelable(false)
                .setNegativeButton("Wyjdź", (dialog, which) -> exitGame())
                .setPositiveButton("Zagraj ponownie", (dialog, which) -> viewModel.resetGame())
                .show();
    }

    private void exitGame() {
        requireActivity().finish();
    }
}
package com.example.unscramblejava.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.unscramblejava.data.GameState;
import com.example.unscramblejava.data.GameStateSharedPref;

public class GameRepository {

    private final Application application;

    public GameRepository(Application application) {
        this.application = application;
    }

    public LiveData<GameState> loadGameState() {
        return GameStateSharedPref.getInstance(application).loadGameState();
    }

    public void saveGameState(GameState state) {
        GameStateSharedPref.getInstance(application).saveGameState(state);
    }
}

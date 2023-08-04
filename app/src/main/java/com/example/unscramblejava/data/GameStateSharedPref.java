package com.example.unscramblejava.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.unscramblejava.ui.GameUiState;

import java.util.HashSet;
import java.util.Set;

public class GameStateSharedPref {

    private static final String FILE_NAME = "game_state_shared_pref";

    private static GameStateSharedPref instance;
    private final SharedPreferences sharedPref;

    private final String CURRENT_SCRAMBLED_WORD_KEY = "current_scrambled_word";
    private final String CURRENT_WORD_COUNT_KEY = "current_word_count";
    private final String SCORE_KEY = "score";
    private final String IS_GUESSED_WORD_WRONG_KEY = "is_guessed_word_wrong";
    private final String IS_GAME_OVER_KEY = "is_game_over";
    private final String USED_WORDS_KEY = "used_words";
    private final String CURRENT_WORD_KEY = "current_word";

    private GameStateSharedPref(Application application) {
        sharedPref = application.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized GameStateSharedPref getInstance(Application application) {
        if (instance == null) {
            instance = new GameStateSharedPref(application);
        }
        return instance;
    }

    public void saveGameState(GameState gameState) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(CURRENT_SCRAMBLED_WORD_KEY, gameState.getGameUiState().getCurrentScrambledWord());
        editor.putInt(CURRENT_WORD_COUNT_KEY, gameState.getGameUiState().getCurrentWordCount());
        editor.putInt(SCORE_KEY, gameState.getGameUiState().getScore());
        editor.putBoolean(IS_GUESSED_WORD_WRONG_KEY, gameState.getGameUiState().isGuessedWordWrong());
        editor.putBoolean(IS_GAME_OVER_KEY, gameState.getGameUiState().isGameOver());
        editor.putStringSet(USED_WORDS_KEY, new HashSet<>(gameState.getUsedWords()));
        editor.putString(CURRENT_WORD_KEY, gameState.getCurrentWord());
        editor.apply();
    }

    public LiveData<GameState> loadGameState() {
        MutableLiveData<GameState> gameStateLiveData = new MutableLiveData<>();

        String currentScrambledWord = sharedPref.getString(CURRENT_SCRAMBLED_WORD_KEY, "");
        int currentWordCount = sharedPref.getInt(CURRENT_WORD_COUNT_KEY, 1);
        int score = sharedPref.getInt(SCORE_KEY, 0);
        boolean isGuessedWordWrong = sharedPref.getBoolean(IS_GUESSED_WORD_WRONG_KEY, false);
        boolean isGameOver = sharedPref.getBoolean(IS_GAME_OVER_KEY, false);
        Set<String> usedWordsSet = sharedPref.getStringSet(USED_WORDS_KEY, new HashSet<>());
        String currentWord = sharedPref.getString(CURRENT_WORD_KEY, "");

        GameUiState gameUiState = new GameUiState(
                currentScrambledWord,
                currentWordCount,
                score,
                isGuessedWordWrong,
                isGameOver
        );

        GameState gameState = new GameState(gameUiState, usedWordsSet, currentWord);
        gameStateLiveData.setValue(gameState);
        return gameStateLiveData;
    }
}
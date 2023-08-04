package com.example.unscramblejava.data;


import com.example.unscramblejava.ui.GameUiState;

import java.util.Set;

public class GameState {
    private GameUiState gameUiState;
    private Set<String> usedWords;
    private String currentWord;

    public GameState(GameUiState gameUiState, Set<String> usedWords, String currentWord) {
        this.gameUiState = gameUiState;
        this.usedWords = usedWords;
        this.currentWord = currentWord;
    }

    // Getters
    public GameUiState getGameUiState() {
        return gameUiState;
    }

    public Set<String> getUsedWords() {
        return usedWords;
    }

    public String getCurrentWord() {
        return currentWord;
    }

    // Setters
    public void setGameUiState(GameUiState gameUiState) {
        this.gameUiState = gameUiState;
    }

    public void setUsedWords(Set<String> usedWords) {
        this.usedWords = usedWords;
    }

    public void setCurrentWord(String currentWord) {
        this.currentWord = currentWord;
    }
}

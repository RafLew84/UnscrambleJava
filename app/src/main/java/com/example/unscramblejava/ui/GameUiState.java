package com.example.unscramblejava.ui;

public class GameUiState {
    private String currentScrambledWord;
    private int currentWordCount;
    private int score;
    private boolean isGuessedWordWrong;
    private boolean isGameOver;

    public GameUiState(String currentScrambledWord, int currentWordCount, int score,
                       boolean isGuessedWordWrong, boolean isGameOver) {
        this.currentScrambledWord = currentScrambledWord;
        this.currentWordCount = currentWordCount;
        this.score = score;
        this.isGuessedWordWrong = isGuessedWordWrong;
        this.isGameOver = isGameOver;
    }

    // Getters
    public String getCurrentScrambledWord() {
        return currentScrambledWord;
    }

    public int getCurrentWordCount() {
        return currentWordCount;
    }

    public int getScore() {
        return score;
    }

    public boolean isGuessedWordWrong() {
        return isGuessedWordWrong;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    // Setters
    public void setCurrentScrambledWord(String currentScrambledWord) {
        this.currentScrambledWord = currentScrambledWord;
    }

    public void setCurrentWordCount(int currentWordCount) {
        this.currentWordCount = currentWordCount;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setGuessedWordWrong(boolean guessedWordWrong) {
        isGuessedWordWrong = guessedWordWrong;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}

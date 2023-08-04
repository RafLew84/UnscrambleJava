package com.example.unscramblejava.viewmodel;

import static com.example.unscramblejava.data.DataProvider.SCORE_INCREASE;
import static com.example.unscramblejava.data.DataProvider.MAX_NO_OF_WORDS;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.unscramblejava.data.DataProvider;
import com.example.unscramblejava.data.GameState;
import com.example.unscramblejava.repository.GameRepository;
import com.example.unscramblejava.ui.GameUiState;

import java.util.HashSet;

public class GameViewModel extends AndroidViewModel {

    private final GameRepository repository;

    // Game UI state
    private final MutableLiveData<GameUiState> _uiState = new MutableLiveData<>();
    public LiveData<GameUiState> uiState = _uiState;

    private String userGuess = "";

    // Set of words used in the game
    private HashSet<String> usedWords = new HashSet<>();
    private String currentWord;

    public GameViewModel(@NonNull Application application) {
        super(application);
        repository = new GameRepository(application);
        resetGame();
    }

    public void saveGame() {

        GameState gameState = new GameState(
                _uiState.getValue(),
                usedWords,
                currentWord
        );
        repository.saveGameState(gameState);
    }

    public void loadGame() {
        GameState gameState = repository.loadGameState().getValue();
        if (gameState != null) {
            _uiState.setValue(new GameUiState(
                    gameState.getGameUiState().getCurrentScrambledWord(),
                    gameState.getGameUiState().getCurrentWordCount(),
                    gameState.getGameUiState().getScore(),
                    gameState.getGameUiState().isGuessedWordWrong(),
                    gameState.getGameUiState().isGameOver()
            ));
            usedWords = new HashSet<>(gameState.getUsedWords());
            currentWord = gameState.getCurrentWord();
        }
    }

    /*
     * Re-initializes the game data to restart the game.
     */
    public void resetGame() {
        usedWords.clear();
        _uiState.setValue(new GameUiState(
                pickRandomWordAndShuffle(),
                1,
                0,
                false,
                false
                ));
    }

    /*
     * Update the user's guess
     */
    public void updateUserGuess(String guessedWord) {
        userGuess = guessedWord;
    }

    /*
     * Checks if the user's guess is correct.
     * Increases the score accordingly.
     */
    public void checkUserGuess() {
        if (userGuess.equalsIgnoreCase(currentWord)) {
            if (_uiState.getValue() != null) {
                int updatedScore = _uiState.getValue().getScore() + SCORE_INCREASE;
                updateGameState(updatedScore);
            }
        } else {
            // User's guess is wrong, show an error
            GameUiState currentState = _uiState.getValue();
            if (currentState != null) {
                _uiState.setValue(new GameUiState(
                        currentState.getCurrentScrambledWord(),
                        currentState.getCurrentWordCount(),
                        currentState.getScore(),
                        true,
                        currentState.isGameOver()
                ));
            }
        }
        // Reset user guess
        updateUserGuess("");
    }

    /*
     * Skip to the next word
     */
    public void skipWord() {
        if (_uiState.getValue() != null){
            updateGameState(_uiState.getValue().getScore());
            // Reset user guess
            updateUserGuess("");
        }
    }

    /*
     * Picks a new currentWord and currentScrambledWord and updates UiState according to
     * current game state.
     */
    private void updateGameState(int updatedScore) {
        if (usedWords.size() == MAX_NO_OF_WORDS) {
            // Last round in the game, update isGameOver to true, don't pick a new word
            GameUiState currentState = _uiState.getValue();
            if (currentState != null) {
                _uiState.setValue(new GameUiState(
                        currentState.getCurrentScrambledWord(),
                        currentState.getCurrentWordCount(),
                        updatedScore,
                        false,
                        true
                ));
            }
        } else {
            // Normal round in the game
            GameUiState currentState = _uiState.getValue();
            if (currentState != null) {
                _uiState.setValue(new GameUiState(
                        pickRandomWordAndShuffle(),
                        currentState.getCurrentWordCount() + 1,
                        updatedScore,
                        false,
                        false
                ));
            }
        }
    }

    private String shuffleCurrentWord(String word) {
        char[] tempWord = word.toCharArray();
        // Scramble the word
        shuffleArray(tempWord);
        while (String.valueOf(tempWord).equals(word)) {
            shuffleArray(tempWord);
        }
        return String.valueOf(tempWord);
    }

    private void shuffleArray(char[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int index = (int) (Math.random() * (i + 1));
            char temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
    }

    private String pickRandomWordAndShuffle() {
        // Continue picking up a new random word until you get one that hasn't been used before
        currentWord = DataProvider.words.get((int) (Math.random() * DataProvider.words.size()));
        while (usedWords.contains(currentWord)) {
            currentWord = DataProvider.words.get((int) (Math.random() * DataProvider.words.size()));
        }
        usedWords.add(currentWord);
        return shuffleCurrentWord(currentWord);
    }
}
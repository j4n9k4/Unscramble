package com.example.unscramble.ui
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.unscramble.data.allWords
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.update

class GameViewModel: ViewModel(

) {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    private lateinit var currentWord: String
    private var usedWords: MutableSet<String> = mutableSetOf()

    var userGuess by mutableStateOf("")
        private set


    init {
        resetGame()
    }



    private fun pickRandomWordAndShuffle(): String{
        currentWord = allWords.random()
        if (usedWords.contains(currentWord)){
            return pickRandomWordAndShuffle()
        }
        else{
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }
    private fun shuffleCurrentWord(word: String): String{
        val tempWord = word.toCharArray()
        tempWord.shuffle()
        while(String(tempWord).equals(word))
        {
            tempWord.shuffle()
        }
        return String(tempWord)
    }
    fun resetGame(){
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    fun updateUserGuess(guessWord: String)
    {
        userGuess = guessWord
    }
    fun checkUserGuess()
    {
        if(userGuess.equals(currentWord, ignoreCase = true)) {

        }else {
            _uiState.update {currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        updateUserGuess("")
    }
}





package arturo.fonseca.quizapp

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
    class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
        val BancoPreguntas = listOf(

            Pregunta(R.string.pregunta2, true),
            Pregunta(R.string.pregunta3, false),
            Pregunta(R.string.pregunta4, true)

        )
        //GUARDA EN EL INDICE
        private var currentIndex: Int
            get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?:0
            set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

        val currentQuestionAnswer: Boolean
            get() = BancoPreguntas[currentIndex].respuesta

        val currentQuestionText: Int
            get() = BancoPreguntas[currentIndex].textoPreguntaResId

        fun moveToNext() {
            currentIndex = (currentIndex + 1) % BancoPreguntas.size
        }
        fun moveToForward() {
            currentIndex = if (currentIndex == 0) {
                BancoPreguntas.size - 1
            }else{(currentIndex - 1) % BancoPreguntas.size
            }
        }

    }
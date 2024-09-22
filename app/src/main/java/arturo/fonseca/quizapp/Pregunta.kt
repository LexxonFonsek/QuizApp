package arturo.fonseca.quizapp

import androidx.annotation.StringRes

data class Pregunta (@StringRes val textoPreguntaResId: Int, val respuesta: Boolean)

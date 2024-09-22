package arturo.fonseca.quizapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import arturo.fonseca.quizapp.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()
    private lateinit var true_button: Button
    private lateinit var false_button: Button
    private lateinit var prevButton: Button
    private lateinit var nextButton: Button

    var currentIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a  QuizViewModel: $quizViewModel")
        true_button = findViewById(R.id.trueButton)
        false_button = findViewById(R.id.falseButton)
        prevButton = findViewById(R.id.prevButton)
        nextButton = findViewById(R.id.nextButton)

        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            Toast.makeText(this, R.string.aviso, Toast.LENGTH_SHORT).show()
            true_button.isEnabled = false
            false_button.isEnabled = false

            }
        binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            Toast.makeText(this, R.string.aviso, Toast.LENGTH_SHORT).show()
            true_button.isEnabled = false
            false_button.isEnabled = false


        }
        binding.pregunta.setOnClickListener { view: View ->

            updateQuestion()
        }
        if (currentIndex == 0) {
            prevButton.isEnabled = false
        }

        binding.prevButton.setOnClickListener { view: View ->
            true_button.isEnabled = true
            false_button.isEnabled = true
            quizViewModel.moveToForward()
            updateQuestion()
        }
        binding.nextButton.setOnClickListener { view: View ->
            true_button.isEnabled = true
            false_button.isEnabled = true
            quizViewModel.moveToNext()
            updateQuestion()
        }
        updateQuestion()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val preguntasTextResId = quizViewModel.currentQuestionText
        binding.pregunta.setText(preguntasTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean) {
        val respuestaCorrecta = quizViewModel.currentQuestionAnswer
        val mensajeResId = if (userAnswer == respuestaCorrecta) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, mensajeResId, Toast.LENGTH_SHORT).show()
        calcularPorcentaje()
    }

   private fun calcularPorcentaje() {
        var cont = 0

        for (pregunta in quizViewModel.BancoPreguntas) {
            val respuestaCorrecta = quizViewModel.currentQuestionAnswer
            if (pregunta.respuesta == respuestaCorrecta) {
                cont++
            }
        }
        val porcentaje = (cont.toDouble() / quizViewModel.BancoPreguntas.size) * 100

         val roundedPorcentaje = String.format("%.2f", porcentaje) // Round to 2 decimal places
       Toast.makeText(this, "Tu porcentaje es: $roundedPorcentaje%", Toast.LENGTH_SHORT).show()
   }



}
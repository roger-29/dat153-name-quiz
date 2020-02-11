package io.roger.quiz.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.roger.quiz.data.Person
import io.roger.quiz.databinding.FragmentQuizBinding
import io.roger.quiz.utilities.ImageUtil
import io.roger.quiz.viewmodels.QuizViewModel
import kotlinx.android.synthetic.main.fragment_quiz.*

val SCORE_TEXT_PREFIX = "Current score: "

class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private lateinit var viewModel: QuizViewModel

    private var score = 0

    private lateinit var currentPerson: Person

    private fun checkIfCorrect(person: Person, answer: String): Boolean {
        if (person.name.toLowerCase() == answer.toLowerCase()) {
            return true
        }

        return false
    }

    private fun updateImageWithPerson(person: Person, imageView: ImageView) {
        val bitmap: Bitmap = ImageUtil.decodeRoomImageToBitmap(person.photo)

        imageView.setImageBitmap(bitmap)
    }

    private fun updateScore(inc: Int = 1) {
        score += inc
        scoreText.text = SCORE_TEXT_PREFIX + score
    }

    private fun nextPerson(persons: List<Person>) {
        currentPerson = persons.random()
    }

    private fun goButtonOnClick(persons: List<Person>) {
        val currentAnswer = binding.editTextTextPersonName.text.toString()
        val isCorrectAnswer = checkIfCorrect(currentPerson, currentAnswer)

        if (isCorrectAnswer) {
            Toast.makeText(context, "Correct 🚀", Toast.LENGTH_SHORT).show()

            updateScore()
            nextPerson(persons)
            updateImageWithPerson(currentPerson, binding.randomImageView)
        } else {
            Toast.makeText(context, "Oof, wrong 😳", Toast.LENGTH_SHORT).show()

            nextPerson(persons)
            updateImageWithPerson(currentPerson, binding.randomImageView)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(inflater, container, false)

        // Get a new or existing ViewModel from the ViewModelProvider.
        viewModel = ViewModelProvider(this).get(QuizViewModel::class.java)

        viewModel.allPersons.observe(viewLifecycleOwner, Observer { persons ->

            persons?.let {
                if(persons.isEmpty()) return@let

                // Set initial person
                nextPerson(persons)

                // Set initial view data
                scoreText.text = SCORE_TEXT_PREFIX + score.toString()
                updateImageWithPerson(currentPerson, binding.randomImageView)

                binding.button.setOnClickListener{
                    goButtonOnClick(persons)
                }
            }
        })

        return binding.root
    }

}


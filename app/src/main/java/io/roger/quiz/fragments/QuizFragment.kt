package io.roger.quiz.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.roger.quiz.databinding.FragmentQuizBinding
import io.roger.quiz.utilities.ImageUtil
import io.roger.quiz.viewmodels.QuizViewModel
import kotlinx.android.synthetic.main.fragment_quiz.*

class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private lateinit var viewModel: QuizViewModel

    private var score = 0

    // private var currentPerson: Person = Person()

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

                val randomPerson = persons.random()

                scoreText.text = score.toString()

                binding.button.setOnClickListener{
                    if (binding.editTextTextPersonName.text.toString().toLowerCase() == randomPerson.name.toLowerCase()) {
                        score++
                        Log.e("Yay", "Yay $score")
                    }

                    scoreText.text = score.toString()
                }

                val bitmap: Bitmap = ImageUtil.decodeRoomImageToBitmap(randomPerson.photo)

                binding.randomImageView.setImageBitmap(bitmap)
            }
        })

        return binding.root
    }

}


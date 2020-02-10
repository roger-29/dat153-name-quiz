package io.roger.quiz.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.roger.quiz.R
import io.roger.quiz.viewmodels.PersonListViewModel
import kotlinx.android.synthetic.main.fragment_quiz.*
import io.roger.quiz.data.Person

class QuizFragment : Fragment() {

    private lateinit var viewModel: PersonListViewModel

    private var score = 0

    // private var currentPerson: Person = Person()

    fun nextPerson() {}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(PersonListViewModel::class.java)

        // Get a new or existing ViewModel from the ViewModelProvider.
        viewModel = ViewModelProvider(this).get(PersonListViewModel::class.java)

        viewModel.allPersons.observe(viewLifecycleOwner, Observer { persons ->

            persons?.let { persons ->
                val randomPerson = persons.random()

                val imageView = view.findViewById<ImageView>(R.id.randomImageView)
                val textBox = view.findViewById<EditText>(R.id.editTextTextPersonName)
                val goButton = view.findViewById<Button>(R.id.button)
                val scoreText = view.findViewById<TextView>(R.id.scoreText)

                scoreText.text = score.toString()

                goButton.setOnClickListener{
                    if (textBox.text.toString().toLowerCase() == randomPerson.name.toLowerCase()) {
                        score++
                        Log.e("Yay", "Yay $score")
                    }

                    scoreText.text = score.toString()
                }

                // Get Android resource ID
                val context: Context = imageView.context
                val imageId: Int = context.resources
                    .getIdentifier(randomPerson.photo, "drawable", context.packageName)


                imageView.setImageResource(imageId)
            }
        })

        return view
    }

    private fun upadteUi(index: Int) {
        with(viewModel.allPersons.value?.get(index)) {
            context?.let {
            randomImageView.setImageResource(
                resources.getIdentifier(this?.photo, "drawable", it.packageName)
            )}
        }
    }
}


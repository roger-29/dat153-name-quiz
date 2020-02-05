package io.roger.quiz.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import io.roger.quiz.R
import io.roger.quiz.viewmodels.PersonListViewModel
import kotlinx.android.synthetic.main.fragment_quiz.*

class QuizFragment : Fragment() {
    private var score = 0

    fun nextPerson() {}

    private lateinit var viewModel: PersonListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(PersonListViewModel::class.java)
        return inflater.inflate(R.layout.fragment_quiz, container, false)
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


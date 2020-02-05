package io.roger.quiz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.roger.quiz.R
import io.roger.quiz.databinding.FragmentAddPersonBinding
import io.roger.quiz.viewmodels.AddViewModel

class AddPersonFragment : Fragment() {

    private lateinit var binding: FragmentAddPersonBinding

    private lateinit var viewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAddPersonBinding.inflate(inflater, container, false)

        return binding.root
    }
}
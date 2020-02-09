package io.roger.quiz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import io.roger.quiz.R
import io.roger.quiz.data.PersonDatabase
import io.roger.quiz.databinding.FragmentAddPersonBinding
import io.roger.quiz.viewmodelfactories.AddViewModelFactory
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

        //Create or retrieve viewmodel
        val application = requireNotNull(this.activity).application

        val datasource = PersonDatabase.getInstance(application).personDao

        val viewModelFactory = AddViewModelFactory(datasource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(AddViewModel::class.java)

        //Set onclicklistener for intent to retrieve an image
        binding.addButton.setOnClickListener {
            activity?.let { it1 -> viewModel.getPictureIntent(it1) }
        }

        return binding.root
    }
}
package io.roger.quiz.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.roger.quiz.R
import io.roger.quiz.databinding.FragmentPreferencesBinding
import io.roger.quiz.utilities.NAME_KEY
import io.roger.quiz.viewmodels.PreferencesViewModel
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 * Use the [PreferencesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PreferencesFragment : Fragment() {

    private lateinit var binding: FragmentPreferencesBinding
    private lateinit var preferencesViewModel: PreferencesViewModel

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPreferencesBinding.inflate(inflater, container, false)
        preferencesViewModel = ViewModelProvider(this).get(PreferencesViewModel::class.java)
        binding.lifecycleOwner = this

        preferencesViewModel.name.observe(this, Observer {
            binding.personName.text = it
        })

        binding.saveNameButton.setOnClickListener {
            preferencesViewModel.setName(binding.editTextPersonName.text.toString())
            savePreferences(binding.editTextPersonName.text.toString())
        }

        showPreferences()

        return binding.root
    }

    private fun showPreferences(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: throw Exception("No pref file")
        sharedPref.getString(getString(R.string.preference_file_name_key), "").let {
            it?.let { it1 -> preferencesViewModel.setName(it1) }
        }
    }

    private fun savePreferences(string: String){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: throw Exception("No pref file")
        with(sharedPref.edit()){
            putString(getString(R.string.preference_file_name_key), string)
            apply()
        }
    }

}
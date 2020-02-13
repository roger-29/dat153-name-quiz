package io.roger.quiz.viewmodels

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.roger.quiz.R
import io.roger.quiz.utilities.NAME_KEY
import java.lang.Exception

class PreferencesViewModel(application: Application): AndroidViewModel(application) {

    private var _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private lateinit var sharedPref: SharedPreferences
    private val sharedPrefFile: String = "io.roger.quiz"

    fun setName(name: String){
        _name.value = "Your name is: ${name}"
    }

}
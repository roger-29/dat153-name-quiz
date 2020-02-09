package io.roger.quiz.viewmodelfactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.roger.quiz.data.PersonDao
import io.roger.quiz.data.PersonDatabase
import io.roger.quiz.viewmodels.AddViewModel

class AddViewModelFactory(
    private val database: PersonDao,
    private val application: Application): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            return AddViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
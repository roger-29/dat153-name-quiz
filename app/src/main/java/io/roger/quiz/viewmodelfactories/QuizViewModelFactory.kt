package io.roger.quiz.viewmodelfactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.roger.quiz.data.PersonDao
import io.roger.quiz.data.PersonRepository
import io.roger.quiz.viewmodels.AddViewModel
import io.roger.quiz.viewmodels.QuizViewModel

class QuizViewModelFactory(
    private val repository: PersonRepository,
    private val application: Application
): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            return QuizViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
package io.roger.quiz.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.roger.quiz.data.Person
import io.roger.quiz.data.PersonDatabase
import io.roger.quiz.data.PersonRepository

class QuizViewModel(
    var repository: PersonRepository,
    application: Application): AndroidViewModel(application) {

    val allPersons: LiveData<List<Person>>

    init {
        val personsDao = PersonDatabase.getInstance(application).personDao
        repository = PersonRepository(personsDao)
        allPersons = repository.allPersons
    }



}
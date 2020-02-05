package io.roger.quiz.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import io.roger.quiz.data.Person
import io.roger.quiz.data.PersonRepository
import io.roger.quiz.data.PersonDatabase

class PersonListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PersonRepository

    val allPersons: LiveData<List<Person>>

    init {
        val personsDao = PersonDatabase.getInstance(application).personDao()
        repository = PersonRepository(personsDao)
        allPersons = repository.allPersons
    }

    fun insert(person: Person) = viewModelScope.launch {
        repository.insert(person)
    }
}
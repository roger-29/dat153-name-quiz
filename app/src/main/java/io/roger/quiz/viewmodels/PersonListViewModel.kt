package io.roger.quiz.viewmodels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import kotlinx.coroutines.launch
import io.roger.quiz.data.Person
import io.roger.quiz.data.PersonRepository
import io.roger.quiz.data.PersonDatabase
import io.roger.quiz.fragments.PersonListFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class PersonListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PersonRepository

    val allPersons: LiveData<List<Person>>

    init {
        val personsDao = PersonDatabase.getInstance(application).personDao
        repository = PersonRepository(personsDao)
        allPersons = repository.allPersons
    }

    fun insert(person: Person) = viewModelScope.launch {
        repository.insert(person)
    }

    fun deletePerson(person: Person){
        viewModelScope.launch {
            repository.delete(person)
        }
    }

    fun moveToAddFragClicker(view: View) {
        view.findNavController()
            .navigate(
                PersonListFragmentDirections
                    .actionPeopleToAddPerson()
            )
    }
}
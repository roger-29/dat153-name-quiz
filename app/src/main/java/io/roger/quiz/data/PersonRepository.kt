package io.roger.quiz.data

import androidx.lifecycle.LiveData

class PersonRepository(private val personDao: PersonDao) {
    val allPersons: LiveData<List<Person>> = personDao.getAll()

    suspend fun insert(person: Person) {
        personDao.insert(person)
    }
}
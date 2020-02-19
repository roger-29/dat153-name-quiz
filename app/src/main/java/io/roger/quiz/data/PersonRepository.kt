package io.roger.quiz.data

import androidx.lifecycle.LiveData

open class PersonRepository(private val personDao: PersonDao) {
    open val allPersons: LiveData<List<Person>> = personDao.getAll()

    open suspend fun insert(person: Person) {
        personDao.insert(person)
    }

    open suspend fun delete(person: Person) {
        personDao.delete(person.personId.toString())
    }
}
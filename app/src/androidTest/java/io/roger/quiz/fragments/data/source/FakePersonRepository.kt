package io.roger.quiz.fragments.data.source

import androidx.lifecycle.LiveData
import io.roger.quiz.data.Person
import io.roger.quiz.data.PersonDao
import io.roger.quiz.data.PersonRepository

class FakePersonRepository(private var fakePersonDao: FakePersonDao) : PersonRepository(fakePersonDao) {

    override val allPersons: LiveData<List<Person>> = fakePersonDao.getAll()

    override suspend fun insert(person: Person) {
        fakePersonDao.insert(person)
    }

    override suspend fun delete(person: Person) {
        fakePersonDao.delete(person.personId.toString())
    }

}
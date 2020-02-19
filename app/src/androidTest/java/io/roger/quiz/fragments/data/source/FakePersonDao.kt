package io.roger.quiz.fragments.data.source

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.roger.quiz.data.Person
import io.roger.quiz.data.PersonDao

interface FakePersonDao: PersonDao {

    @Query("SELECT * FROM persons ORDER BY name")
    override fun getAll(): LiveData<List<Person>>

    @Query("SELECT * FROM persons WHERE id = :personId")
    override fun getPerson(personId: String): LiveData<Person>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(person: Person)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertAll(persons: List<Person>)

    @Query("DELETE FROM persons WHERE id = :personId")
    override suspend fun delete(personId: String)

}
package io.roger.quiz.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PersonDao {
    @Query("SELECT * FROM persons ORDER BY name")
    fun getPersons(): LiveData<List<Person>>

    @Query("SELECT * FROM persons WHERE id = :personId")
    fun getPerson(personId: String): LiveData<Person>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(persons: List<Person>)
}
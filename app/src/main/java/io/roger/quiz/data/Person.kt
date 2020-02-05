package io.roger.quiz.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons")
data class Person(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id", index = true) val personId: Int,
    val name: String = "",
    val photo: String = ""
) {

    fun isCorrectName(answer: String): Boolean = answer == name

    override fun toString() = name
}
package io.roger.quiz.data

class PersonRepository private constructor(private val personDao: PersonDao) {
    fun getPersons() = personDao.getPersons()

    fun getPerson(personId: String) = personDao.getPerson(personId)

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: PersonRepository? = null

        fun getInstance(personDao: PersonDao) =
            instance ?: synchronized(this) {
                instance ?: PersonRepository(personDao).also { instance = it }
            }
    }
}
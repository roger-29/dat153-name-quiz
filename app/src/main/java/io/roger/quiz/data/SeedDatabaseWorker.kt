package io.roger.quiz.data

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import io.roger.quiz.utilities.PERSON_DATA_FILENAME
import kotlinx.coroutines.coroutineScope

/*
* Worker that creates a database and seeds it with the default data
*/
class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(PERSON_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val personType = object : TypeToken<List<Person>>() {}.type
                    val personList: List<Person> = Gson().fromJson(jsonReader, personType)

                    val database = PersonDatabase.getInstance(applicationContext)
                    database.personDao().insertAll(personList)
                    print("Hello ")
                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private val TAG = SeedDatabaseWorker::class.java.simpleName
    }
}

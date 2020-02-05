package io.roger.quiz.viewmodels

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import io.roger.quiz.data.PersonDatabase
import kotlinx.coroutines.withContext

class AddViewModel( val database: PersonDatabase, application: Application): AndroidViewModel(application){

    fun getPictureIntent(){
        var pictureIntent: Intent = Intent(Intent.ACTION_GET_CONTENT)
        pictureIntent.setType("image/*")
    }

    override fun onCleared() {
        super.onCleared()
    }
}
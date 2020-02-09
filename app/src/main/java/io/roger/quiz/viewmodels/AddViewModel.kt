package io.roger.quiz.viewmodels

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.roger.quiz.data.PersonDao
import io.roger.quiz.data.PersonDatabase
import kotlinx.coroutines.withContext

class AddViewModel(val database: PersonDao, application: Application): AndroidViewModel(application){

    private val _image = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap>
        get() = _image

    fun getPictureIntent(activity: FragmentActivity){
        val pictureIntent: Intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }

        val chooser: Intent = Intent.createChooser(pictureIntent, "Chooser")

        if(pictureIntent.resolveActivity(activity.packageManager) != null){
            activity.startActivity(chooser)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
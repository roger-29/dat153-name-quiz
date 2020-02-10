package io.roger.quiz.viewmodels

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Environment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.roger.quiz.data.PersonDao
import io.roger.quiz.data.PersonDatabase
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddViewModel(val database: PersonDao, application: Application): AndroidViewModel(application){

    private val _image = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap>
        get() = _image

    fun getPictureIntent(activity: FragmentActivity, fragment: Fragment){
        val pictureIntent: Intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }

        //val chooser: Intent = Intent.createChooser(pictureIntent, "Chooser")

        if(pictureIntent.resolveActivity(activity.packageManager) != null){
            //activity.startActivityForResult(pictureIntent, 1)
            activity.startActivityFromFragment(fragment,pictureIntent,1)
            //activity.startActivityForResult(chooser, 1)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(context: Context): File{
        //Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", //Prefix
            ".jpg", //Suffix
                    storageDir //Directory
        )
    }

    override fun onCleared() {
        super.onCleared()
    }
}
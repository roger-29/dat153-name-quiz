package io.roger.quiz.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import io.roger.quiz.data.Person
import io.roger.quiz.data.PersonDao
import io.roger.quiz.utilities.ImageUtil
import kotlinx.coroutines.*

import java.lang.Exception


class AddViewModel(val database: PersonDao, application: Application): AndroidViewModel(application){

    private var viewModelJob = Job()


    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _image = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap>
        get() = _image

    val addButtonClickable = Transformations.map(image) {it != null}

    fun getPictureIntent(activity: FragmentActivity, fragment: Fragment){
        val pictureIntent: Intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }

        if(pictureIntent.resolveActivity(activity.packageManager) != null){
            activity.startActivityFromFragment(fragment,pictureIntent,1)
        }
    }

    fun addPersonWithImage(context: Context, personName: String){
        val bitmapToEncode: Bitmap = _image.value ?: throw Exception("bitmapToEncode is null")
        val photoEncoded = ImageUtil.encodeRoomImageToB64(bitmapToEncode)
        val newPerson: Person = Person(name = personName, photo = photoEncoded)
        _image.value = null
        Toast.makeText(context,"Person added! 🍻",Toast.LENGTH_SHORT).show()
        uiScope.launch {
            database.insert(newPerson)
        }
    }

    fun selectImage(bitmap: Bitmap){
        Log.i("AddVM","Bitmap added")
        _image.value = bitmap
    }

    override fun onCleared() {
        super.onCleared()
    }
}
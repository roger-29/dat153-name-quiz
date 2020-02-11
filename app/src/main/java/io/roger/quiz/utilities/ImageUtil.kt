package io.roger.quiz.utilities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream

class ImageUtil() {
    companion object {

        fun decodeRoomImageToBitmap(photo: String): Bitmap {
            val imageByteArray = Base64.decode(photo, Base64.DEFAULT)
            Log.i("ImageUtil", "Loaded imageByteArray: ${photo}")
            return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
                ?: throw Exception("Bitmap is null")
        }

        fun encodeRoomImageToB64(photo: Bitmap): String{
            val byteArrayOutputStream: ByteArrayOutputStream = ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
        }

    }
}
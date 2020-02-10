package io.roger.quiz.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import io.roger.quiz.R
import io.roger.quiz.data.PersonDatabase
import io.roger.quiz.databinding.FragmentAddPersonBinding
import io.roger.quiz.viewmodelfactories.AddViewModelFactory
import io.roger.quiz.viewmodels.AddViewModel
import java.lang.Exception

class AddPersonFragment : Fragment() {

    private lateinit var binding: FragmentAddPersonBinding

    private lateinit var viewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAddPersonBinding.inflate(inflater, container, false)

        //Create or retrieve viewmodel
        val application = requireNotNull(this.activity).application

        val datasource = PersonDatabase.getInstance(application).personDao

        val viewModelFactory = AddViewModelFactory(datasource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(AddViewModel::class.java)

        //Set onclicklistener for intent to retrieve an image
        binding.addButton.setOnClickListener {
            activity?.let { it1 -> viewModel.getPictureIntent(it1, this) }
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("AddPersonFragment", "onActivityResult called")
        if (requestCode == 1 && resultCode == FragmentActivity.RESULT_OK) {
            Log.i("AddPersonFragment", "Image recieved")
            val imageUri: Uri? = data?.data
            context?.contentResolver?.let {
                if (imageUri != null) {
                    binding.selectedImage.setImageBitmap(ImageDecoder.decodeBitmap(ImageDecoder.createSource(it, imageUri)))
                }
            }
            // val imageBitmap = data?.extras.get("data") as Bitmap ?: throw Exception("No data")
            //val fullPhotoUri: Uri = data?.data ?: throw Exception("No Uri received")
            //binding.selectedImage.setImageURI(fullPhotoUri)
        }
    }
}

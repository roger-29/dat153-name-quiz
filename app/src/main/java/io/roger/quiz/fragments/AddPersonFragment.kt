package io.roger.quiz.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.roger.quiz.R
import io.roger.quiz.data.PersonDatabase
import io.roger.quiz.databinding.FragmentAddPersonBinding
import io.roger.quiz.viewmodelfactories.AddViewModelFactory
import io.roger.quiz.viewmodels.AddViewModel
import kotlinx.android.synthetic.main.list_item.*

class AddPersonFragment : Fragment() {

    private lateinit var binding: FragmentAddPersonBinding

    private lateinit var viewModel: AddViewModel

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_person, container, false)

        binding.lifecycleOwner = this

        //Create or retrieve viewmodel
        val application = requireNotNull(this.activity).application

        val datasource = PersonDatabase.getInstance(application).personDao

        val viewModelFactory = AddViewModelFactory(datasource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(AddViewModel::class.java)

        binding.viewModel = viewModel

        if(viewModel.image.value != null){
            binding.selectedImage.setImageBitmap(viewModel.image.value)
        }

        viewModel.image.observe(this , Observer {  image ->
            if (image != null){
                binding.selectedImage.setImageBitmap(image)
            }else{
                binding.selectedImage.setImageResource(R.drawable.ic_launcher_background)
            }
        })

        //Set onclicklistener for intent to retrieve an image
        binding.selectImage.setOnClickListener {
            activity?.let { it1 -> viewModel.getPictureIntent(it1, this) }
        }

        binding.addButton.setOnClickListener {
            if(binding.nameText.text.toString() == ""){
                Toast.makeText(context,"Please enter a name", Toast.LENGTH_SHORT).show()
            }else {
                val name = binding.nameText.text.toString()
                context?.let { viewModel.addPersonWithImage(it, name) }
            }
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
                    val bitmap: Bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(it, imageUri))
                    viewModel.selectImage(bitmap)
                }
            }
        }
    }
}

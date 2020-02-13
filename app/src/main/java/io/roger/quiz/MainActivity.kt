package io.roger.quiz

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.ads.MobileAds
import io.roger.quiz.utilities.NAME_KEY
import io.roger.quiz.viewmodels.PreferencesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()

        window.statusBarColor = R.color.colorOnBackground

        getPrefs()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        navHostFragment?.let {
            NavigationUI.setupWithNavController(
                bottom_nav,
                it.findNavController()
            )
        }

        MobileAds.initialize(this, "ca-app-pub-2912171741512332~6637535049")
    }

    private fun writeToSharedPref(string: String){

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putString(getString(R.string.preference_file_name_key), string)
            apply()
        }

    }

    private fun getPrefs(){
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)

        val prefName = sharedPref.getString(resources.getString(R.string.preference_file_name_key),"")

        if (prefName == ""){

            AlertDialog.Builder(this).also {
                it.setTitle("You have no name, please enter below")

                //Set up the input
                val input: EditText = EditText(this)
                input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                it.setView(input)

                //Set up the buttons
                it.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    writeToSharedPref(input.text.toString())
                })

                it.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                    this.finish()
                })

                it.show()
        }
    }

    }

}

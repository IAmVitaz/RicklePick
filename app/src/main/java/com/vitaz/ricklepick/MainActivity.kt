package com.vitaz.ricklepick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.vitaz.ricklepick.viewmodel.CharactersViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set up action bar with navigation controller
        setupActionBarWithNavController(findNavController(R.id.navHostFragment))

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
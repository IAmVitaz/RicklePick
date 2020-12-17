package com.vitaz.ricklepick.fragments.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.vitaz.ricklepick.R
import com.vitaz.ricklepick.viewmodel.CharacterDetailsViewModel
import com.vitaz.ricklepick.viewmodel.CharactersViewModel

class CharacterDetailsFragment : Fragment() {

    lateinit var viewModel: CharacterDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this).get(CharacterDetailsViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

}
package com.vitaz.ricklepick.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.vitaz.ricklepick.R
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_main, container, false)

        view.toCharactersListButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_charactersList)
        }

        view.toEpisodesListButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_episodesListFragment)
        }

        view.toLocationsListButton.setOnClickListener {
            Toast.makeText(activity, "Due to restricted budget, this button have been disabled\nBut who cares?", Toast.LENGTH_LONG).show()
        }

        return view
    }

}
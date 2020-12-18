package com.vitaz.ricklepick.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vitaz.ricklepick.R
import kotlinx.android.synthetic.main.fragment_main.view.*
import java.io.IOException


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_main, container, false)

        view.toCharactersListButton.setOnClickListener {
            if (isConnected()) {
                findNavController().navigate(R.id.action_mainFragment_to_charactersList)
            } else Toast.makeText(activity, "Internet is not available in this Dimension.\nChoose a better one to live!", Toast.LENGTH_LONG).show()
        }

        view.toEpisodesListButton.setOnClickListener {
            if (isConnected()) {
                findNavController().navigate(R.id.action_mainFragment_to_episodesListFragment)
            } else Toast.makeText(activity, "I told you, there is an infinite number of dimensions.\nJust pick the one which have stable internet invented already", Toast.LENGTH_LONG).show()
        }

        view.toLocationsListButton.setOnClickListener {
            Toast.makeText(activity, "Due to restricted budget, this button have been disabled\nBut who cares?", Toast.LENGTH_LONG).show()
        }

        return view
    }

    @Throws(InterruptedException::class, IOException::class)
    fun isConnected(): Boolean {
        val command = "ping -c 1 google.com"
        return Runtime.getRuntime().exec(command).waitFor() == 0
    }
}
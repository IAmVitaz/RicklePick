package com.vitaz.ricklepick.fragments.characters

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.vitaz.ricklepick.R
import com.vitaz.ricklepick.adapters.CharacterDetailsEpisodeListAdapter
import com.vitaz.ricklepick.utils.loadImage
import kotlinx.android.synthetic.main.fragment_character_details.*
import kotlinx.android.synthetic.main.fragment_character_details.view.*

class CharacterDetailsFragment : Fragment() {

    private val args by navArgs<CharacterDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_character_details, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //expand recyclerview and disable scrolling. works with NestedScrollView
        characterDetailsEpisodeRecyclerView.isNestedScrollingEnabled = false
        //populate episode recyclerview

        characterDetailsEpisodeRecyclerView.apply {
            adapter = CharacterDetailsEpisodeListAdapter(args.currentCharacter.episode)
            layoutManager = StaggeredGridLayoutManager(getNumberOfRows(), StaggeredGridLayoutManager.VERTICAL)
        }

        view.characterDetailsImageView.loadImage(args.currentCharacter.image)
        view.characterDetailsNameValue.text = checkStringForNull(args.currentCharacter.name)
        view.characterDetailsStatusValue.text = checkStringForNull(args.currentCharacter.status)
        view.characterDetailsSpeciesValue.text = checkStringForNull(args.currentCharacter.species)
        view.characterDetailsTypeValue.text = checkStringForNull(args.currentCharacter.type)
        view.characterDetailsOriginValue.text = checkStringForNull(args.currentCharacter.origin.name)
        view.characterDetailsLocationValue.text = checkStringForNull(args.currentCharacter.location.name)
        view.characterDetailsGender.setImageResource(getGenderResourceId(args.currentCharacter.gender))
    }

    private fun checkStringForNull(item: String?): String {
        return if (!item.isNullOrBlank() ||  item != "") {
            item!!
        } else "Unknown"
    }

    private fun getGenderResourceId(gender: String?): Int {
        var imageName: String = when (gender) {
            "Male" -> {
                "png_mars"
            }
            "Female" -> {
                "png_venus"
            }
            else -> "png_questionmark"
        }
        return  requireContext().resources.getIdentifier(imageName, "drawable", requireContext().packageName)
    }

    private fun getNumberOfRows(): Int {
        val width = Resources.getSystem().getDisplayMetrics().widthPixels
        val rows = when {
            width < 500 -> 1
            width in 500..999 -> 2
            width in 1000..1999 -> 3
            else -> 4
        }
        return rows
    }

}
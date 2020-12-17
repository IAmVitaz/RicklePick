package com.vitaz.ricklepick.fragments.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.vitaz.ricklepick.R
import com.vitaz.ricklepick.utils.loadImage
import com.vitaz.ricklepick.viewmodel.CharacterDetailsViewModel
import kotlinx.android.synthetic.main.fragment_character_details.view.*

class CharacterDetailsFragment : Fragment() {

    lateinit var viewModel: CharacterDetailsViewModel

    private val args by navArgs<CharacterDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_character_details, container, false)

        view.characterDetailsImageView.loadImage(args.currentCharacter.image)
        view.characterDetailsNameValue.text = checkStringForNull(args.currentCharacter.name)
        view.characterDetailsStatusValue.text = checkStringForNull(args.currentCharacter.status)
        view.characterDetailsSpeciesValue.text = checkStringForNull(args.currentCharacter.species)
        view.characterDetailsTypeValue.text = checkStringForNull(args.currentCharacter.type)
        view.characterDetailsOriginValue.text = checkStringForNull(args.currentCharacter.origin.name)
        view.characterDetailsLocationValue.text = checkStringForNull(args.currentCharacter.location.name)
        view.characterDetailsGender.setImageResource(getGenderResourceId(args.currentCharacter.gender))

        viewModel = ViewModelProviders.of(this).get(CharacterDetailsViewModel::class.java)


        return view
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

}
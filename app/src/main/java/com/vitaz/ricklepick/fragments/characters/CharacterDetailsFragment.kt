package com.vitaz.ricklepick.fragments.characters

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.vitaz.ricklepick.R
import com.vitaz.ricklepick.adapters.CharacterDetailsEpisodeListAdapter
import com.vitaz.ricklepick.utils.loadImage
import com.vitaz.ricklepick.viewmodel.CharacterDetailsViewModel
import kotlinx.android.synthetic.main.fragment_character_details.*

class CharacterDetailsFragment : Fragment() {

    private val args by navArgs<CharacterDetailsFragmentArgs>()
    lateinit var viewModel: CharacterDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_character_details, container, false)

        viewModel = ViewModelProviders.of(this).get(CharacterDetailsViewModel::class.java)
        viewModel.characterId = args.characterId
        viewModel.showCharacterDetails()

        observeViewModel()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun observeViewModel() {
        viewModel.character.observe(viewLifecycleOwner, Observer {character ->
            character?.let {
                characterDetailsWindow.visibility = View.INVISIBLE
                characterDetailsProgressBar.visibility = View.VISIBLE

                //expand recyclerview and disable scrolling. works with NestedScrollView
                characterDetailsEpisodeRecyclerView.isNestedScrollingEnabled = false

                //populate episode recyclerview
                characterDetailsEpisodeRecyclerView.apply {
                    adapter = CharacterDetailsEpisodeListAdapter(viewModel.character.value!!.episode)
                    layoutManager = StaggeredGridLayoutManager(getNumberOfRows(), StaggeredGridLayoutManager.VERTICAL)
                }

                //show character details data received
                characterDetailsImageView.loadImage(viewModel.character.value!!.image)
                characterDetailsNameValue.text = checkStringForNull(viewModel.character.value!!.name)
                characterDetailsStatusValue.text = checkStringForNull(viewModel.character.value!!.status)
                characterDetailsSpeciesValue.text = checkStringForNull(viewModel.character.value!!.species)
                characterDetailsTypeValue.text = checkStringForNull(viewModel.character.value!!.type)
                characterDetailsOriginValue.text = checkStringForNull(viewModel.character.value!!.origin.name)
                characterDetailsLocationValue.text = checkStringForNull(viewModel.character.value!!.location.name)
                setGenderImage(viewModel.character.value!!.gender, characterDetailsGender)
            }
        })

//        viewModel.characterLoadError.observe(viewLifecycleOwner, Observer { isError ->
//            list_error.visibility = if(isError == "") View.GONE else View.VISIBLE
//        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                characterDetailsWindow.visibility = if(it) View.INVISIBLE else View.VISIBLE
                characterDetailsProgressBar.visibility = if(it) View.VISIBLE else View.GONE

            }
        })
    }

    private fun checkStringForNull(item: String?): String {
        return if (!item.isNullOrBlank() ||  item != "") {
            item!!
        } else "Unknown"
    }

    private fun setGenderImage(gender: String?, image: ImageView) {
        var imageName: String = when (gender) {
            "Male" -> {
                "png_mars"
            }
            "Female" -> {
                "png_venus"
            }
            else -> "png_questionmark"
        }
        val imageID = requireContext().resources.getIdentifier(imageName, "drawable", requireContext().packageName)
        image.setImageResource(imageID)

        val color: Int = when (gender) {
            "Male" -> {
                R.color.blue
            }
            "Female" -> {
                R.color.pink
            }
            else -> R.color.black
        }
        image.setColorFilter(ContextCompat.getColor(requireContext(), color), android.graphics.PorterDuff.Mode.SRC_IN);
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
package com.vitaz.ricklepick.fragments.episodes

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.vitaz.ricklepick.R
import com.vitaz.ricklepick.adapters.EpisodeDetailsCharacterListAdapter
import com.vitaz.ricklepick.viewmodel.EpisodeDetailsViewModel
import kotlinx.android.synthetic.main.fragment_episode_details.*
import kotlin.math.log

class EpisodeDetailsFragment : Fragment() {

    private val args by navArgs<EpisodeDetailsFragmentArgs>()
    lateinit var viewModel: EpisodeDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_episode_details, container, false)

        viewModel = ViewModelProviders.of(this).get(EpisodeDetailsViewModel::class.java)
        viewModel.episodeId = args.episodeId
        viewModel.showEpisodeDetails()

        observeViewModel()

        return view
    }

    fun observeViewModel() {
        viewModel.episode.observe(viewLifecycleOwner, Observer {episode ->
            episode?.let {
                episodeDetailsWindow.visibility = View.INVISIBLE
                episodeDetailsProgressBar.visibility = View.VISIBLE

                //expand recyclerview and disable scrolling. works with NestedScrollView
                episodeDetailsCharacterRecyclerView.isNestedScrollingEnabled = false

                //populate episode recyclerview
                episodeDetailsCharacterRecyclerView.apply {
                    adapter = EpisodeDetailsCharacterListAdapter(viewModel.episode.value!!.characters)
                    layoutManager = StaggeredGridLayoutManager(getNumberOfRows(), StaggeredGridLayoutManager.VERTICAL)
                }

                //show character details data received
                episodeDetailsEpisodeValue.text = viewModel.episode.value!!.episode
                episodeDetailsNameValue.text = viewModel.episode.value!!.name
                episodeDetailsAirValue.text = viewModel.episode.value!!.air_date
                setSeasonImage(viewModel.episode.value!!.episode!!, episodeDetailsSeasonCover)
            }
        })

//        viewModel.characterLoadError.observe(viewLifecycleOwner, Observer { isError ->
//            list_error.visibility = if(isError == "") View.GONE else View.VISIBLE
//        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                episodeDetailsWindow.visibility = if(it) View.INVISIBLE else View.VISIBLE
                episodeDetailsProgressBar.visibility = if(it) View.VISIBLE else View.GONE
            }
        })
    }

    private fun setSeasonImage(episode: String, image: ImageView) {
        val seasonNumber = episode.take(3).takeLast(1)
        val seasonImageName = "season_$seasonNumber"
        try {
            val imageID = requireContext().resources.getIdentifier(seasonImageName, "drawable", requireContext().packageName)
            image.setImageResource(imageID)
        } catch (e: Exception) {
            Log.i("Error", "Exception while trying to set season image: $e")
        }
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
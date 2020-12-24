package com.vitaz.ricklepick.fragments.episodes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.vitaz.ricklepick.R
import com.vitaz.ricklepick.viewmodel.EpisodeDetailsViewModel
import kotlinx.android.synthetic.main.fragment_episode_details.*

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
//                characterDetailsEpisodeRecyclerView.isNestedScrollingEnabled = false

                //populate episode recyclerview
//                characterDetailsEpisodeRecyclerView.apply {
//                    adapter = CharacterDetailsEpisodeListAdapter(viewModel.character.value!!.episode)
//                    layoutManager = StaggeredGridLayoutManager(getNumberOfRows(), StaggeredGridLayoutManager.VERTICAL)
//                }

                //show character details data received
                episodeDetailsEpisodeValue.text = viewModel.episode.value!!.episode
                episodeDetailsNameValue.text = viewModel.episode.value!!.name
                episodeDetailsAirValue.text = viewModel.episode.value!!.air_date
//                setGenderImage(viewModel.character.value!!.gender, characterDetailsGender)
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

}
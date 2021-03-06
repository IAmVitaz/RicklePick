package com.vitaz.ricklepick.fragments.episodes

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
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

        //Set Menu
        setHasOptionsMenu(true)

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

                //show character details data received
                episodeDetailsEpisodeOverallValue.text = viewModel.episode.value!!.id.toString()
                episodeDetailsEpisodeValue.text = viewModel.episode.value!!.episode
                episodeDetailsNameValue.text = viewModel.episode.value!!.name
                episodeDetailsAirValue.text = viewModel.episode.value!!.air_date
                setSeasonImage(viewModel.episode.value!!.episode!!, episodeDetailsSeasonCover)

                viewModel.showCharactersList()
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

        viewModel.isCharacterLoadingFinished.observe(viewLifecycleOwner, Observer { isFinished ->
            isFinished?.let {
                episodeDetailsCharacterListProgressBar.visibility = if(it) View.INVISIBLE else View.VISIBLE

                //expand recyclerview and disable scrolling. works with NestedScrollView
                episodeDetailsCharacterRecyclerView.isNestedScrollingEnabled = false

                //populate episode recyclerview
                episodeDetailsCharacterRecyclerView.apply {
                    adapter = EpisodeDetailsCharacterListAdapter(viewModel.charactersList)
                    layoutManager = StaggeredGridLayoutManager(getNumberOfRows(), StaggeredGridLayoutManager.VERTICAL)
                }
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
            width < 700 -> 1
            width in 700..1200 -> 2
            width in 1200..1999 -> 3
            else -> 4
        }
        return rows
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.back_home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.menu_home) {
            confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialog)
        builder.setPositiveButton("Yes") { _, _ ->
            startActivity(Intent.makeRestartActivityTask(requireActivity().intent.component));
        }
        builder.setNegativeButton("No") { _, _ ->}
        builder.setTitle("Back to title screen?")
        builder.setMessage("Are you sure you want to go back to title screen?")
        builder.create().show()
    }

}
package com.vitaz.ricklepick.fragments.episodes

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.vitaz.ricklepick.R
import com.vitaz.ricklepick.adapters.EpisodesListAdapter
import com.vitaz.ricklepick.viewmodel.EpisodesViewModel
import kotlinx.android.synthetic.main.fragment_episodes_list.*

class EpisodesListFragment : Fragment() {

    lateinit var viewModel: EpisodesViewModel
    private val episodesListAdapter = EpisodesListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this).get(EpisodesViewModel::class.java)
        viewModel.downloadNextPageOfEpisodes()

        observeViewModel()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_episodes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        episodesListRecyclerView.apply {
            adapter = episodesListAdapter
            layoutManager = StaggeredGridLayoutManager(getNumberOfRows(), StaggeredGridLayoutManager.VERTICAL)


            // SlideInUpAnimator turns on nice animation for recyclerView, but crash the app when come back to the fragment works fine with  itemAnimator = null.
            // ToDo investigate the animation issue
            itemAnimator = null
//            itemAnimator = SlideInUpAnimator().apply{
//                addDuration = 300
//            }
        }
        episodesListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (viewModel.currentPage <= viewModel.totalPages) {
                        Toast.makeText(activity, "Loading page ${viewModel.currentPage} of total ${viewModel.totalPages}", Toast.LENGTH_SHORT).show()
                        viewModel.downloadNextPageOfEpisodes()
                    } else {
                        Toast.makeText(activity, "Here is the end of the list.\nYeah, I also wish to have more episodes.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }

    fun observeViewModel() {
        viewModel.episodes.observe(viewLifecycleOwner, Observer {episodes ->
            episodes?.let {
                episodesListRecyclerView.visibility = View.VISIBLE
                episodesListProgressBar.visibility = View.INVISIBLE
                episodesListAdapter.updateCharacterList(it) }
        })

//        viewModel.characterLoadError.observe(viewLifecycleOwner, Observer { isError ->
//            list_error.visibility = if(isError == "") View.GONE else View.VISIBLE
//        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                episodesListProgressBar.visibility = if(it) View.VISIBLE else View.GONE
            }
        })
    }

    private fun getNumberOfRows(): Int {
        val width = Resources.getSystem().displayMetrics.widthPixels
        val rows = when {
            width < 1441 -> 1
            width in 1441..3121 -> 2
            else -> 3
        }
        return rows
    }

}
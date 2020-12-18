package com.vitaz.ricklepick.fragments.characters

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.vitaz.ricklepick.R
import com.vitaz.ricklepick.adapters.CharacterListAdapter
import com.vitaz.ricklepick.viewmodel.CharactersViewModel
import kotlinx.android.synthetic.main.fragment_characters_list.*


class CharactersListFragment : Fragment() {

    lateinit var viewModel: CharactersViewModel
    private val charactersListAdapter = CharacterListAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this).get(CharactersViewModel::class.java)
        viewModel.refresh()

        observeViewModel()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_characters_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        characterListRecyclerView.apply {
            adapter = charactersListAdapter
            layoutManager = StaggeredGridLayoutManager(getNumberOfRows(), StaggeredGridLayoutManager.VERTICAL)

            // SlideInUpAnimator turns on nice animation for recyclerView, but crash the app when come back to the fragment works fine with  itemAnimator = null.
            // ToDo investigate the animation issue
            itemAnimator = null
//            itemAnimator = SlideInUpAnimator().apply{
//                addDuration = 300
//            }
        }
        characterListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    Toast.makeText(activity, "Unfortunately, UFO captured the rest of this API response :(", Toast.LENGTH_LONG).show()
                    //ToDo add coroutine with api call to extend characterList
                }
            }
        })


        super.onViewCreated(view, savedInstanceState)
    }

    fun observeViewModel() {
        viewModel.characters.observe(viewLifecycleOwner, Observer {characters ->
            characters?.let {
                characterListRecyclerView.visibility = View.VISIBLE
                characterListProgressBar.visibility = View.INVISIBLE
                charactersListAdapter.updateCharacterList(it) }
        })

//        viewModel.characterLoadError.observe(viewLifecycleOwner, Observer { isError ->
//            list_error.visibility = if(isError == "") View.GONE else View.VISIBLE
//        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                characterListProgressBar.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
//                    list_error.visibility = View.GONE
                    characterListRecyclerView.visibility = View.GONE
                }
            }
        })
    }

    private fun getNumberOfRows(): Int {
        val width = Resources.getSystem().getDisplayMetrics().widthPixels
        val rows = when {
            width < 1081 -> 1
            width in 1081..2000 -> 2
            else -> 3
        }
        return rows
    }

}
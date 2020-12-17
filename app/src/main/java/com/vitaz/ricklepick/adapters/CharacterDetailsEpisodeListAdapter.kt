package com.vitaz.ricklepick.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.ricklepick.R
import com.vitaz.ricklepick.model.Character
import com.vitaz.ricklepick.utils.loadImage
import kotlinx.android.synthetic.main.item_character_details_episode.view.*
import kotlinx.android.synthetic.main.item_character_list.view.*

class CharacterDetailsEpisodeListAdapter(var episodes: List<String>):
    RecyclerView.Adapter<CharacterDetailsEpisodeListAdapter.ViewHolder>() {

    val episodeList = getEpisodeList(episodes)


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val episodeName = view.episodeNameValue
        fun bind(episode: String) {
            episodeName.text = episode
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_character_details_episode, parent, false)
    )

    override fun getItemCount() = episodeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(episodeList[position])
    }

    private fun getEpisodeList(urlList: List<String>): List<String> {
        val episodeList = mutableListOf<String>()

        if (urlList.isNotEmpty()) {
            urlList.forEach {
                val url: String = it.replace("https://rickandmortyapi.com/api/", "")
                    .replace("/", " ")
                    .capitalize()
                episodeList.add(url)
            }
        }
        return episodeList
    }
}
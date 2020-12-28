package com.vitaz.ricklepick.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.ricklepick.R
import com.vitaz.ricklepick.fragments.characters.CharacterDetailsFragmentDirections
import com.vitaz.ricklepick.fragments.episodes.EpisodeDetailsFragmentDirections
import kotlinx.android.synthetic.main.item_character_details_episode.view.*

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

        holder.itemView.episodeListRowBackground.setOnClickListener {
            val episodeId = getEpisodeId(episodeList[position])
            val action = CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToEpisodeDetailsFragment(episodeId)
            holder.itemView.findNavController().navigate(action)
        }
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

    private fun getEpisodeId(episode: String): Int {
        return episode.drop(8).toInt()
    }
}
package com.vitaz.ricklepick.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.ricklepick.R
import com.vitaz.ricklepick.model.Character
import com.vitaz.ricklepick.model.Episode
import kotlinx.android.synthetic.main.item_episode_list.view.*

class EpisodesListAdapter (var episodes: ArrayList<Episode>):
    RecyclerView.Adapter<EpisodesListAdapter.ViewHolder>() {

    fun updateCharacterList(newEpisodes: List<Episode>) {
        episodes.clear()
        episodes.addAll(newEpisodes)
//        notifyItemRangeInserted(0, episodes.size-1)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val episodeId = view.episodeListItemIdValue
        private val episodeName = view.episodeListINameValue
        private val episodeEpisode = view.episodeListEpisodeValue
        private val episodeAirDate = view.episodeListAirDateValue

        fun bind(episode: Episode) {
            episodeId.text = episode.id.toString()
            episodeName.text = episode.name
            episodeEpisode.text = episode.episode
            episodeAirDate.text = episode.air_date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_episode_list, parent, false)
    )

    override fun getItemCount() = episodes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(episodes[position])

        holder.itemView.episodeListRowBackground.setOnClickListener {

        }
    }
}
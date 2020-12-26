package com.vitaz.ricklepick.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.ricklepick.R
import kotlinx.android.synthetic.main.item_episode_details_character.view.*

class EpisodeDetailsCharacterListAdapter(var characters: List<String>):
    RecyclerView.Adapter<EpisodeDetailsCharacterListAdapter.ViewHolder>() {

    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        private val characterName = view.characterNameValue
        fun bind(episode: String) {
            characterName.text = episode
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_episode_details_character, parent, false)
    )

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characters[position])
    }
}
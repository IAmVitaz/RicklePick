package com.vitaz.ricklepick.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.ricklepick.R
import com.vitaz.ricklepick.fragments.episodes.EpisodeDetailsFragmentDirections
import com.vitaz.ricklepick.model.Character
import com.vitaz.ricklepick.utils.loadImage
import kotlinx.android.synthetic.main.item_episode_details_character.view.*

class EpisodeDetailsCharacterListAdapter(var characters: List<Character>):
    RecyclerView.Adapter<EpisodeDetailsCharacterListAdapter.ViewHolder>() {

    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        private val characterName = view.characterNameValue
        private val characterImage = view.characterImage
        fun bind(character: Character) {
            characterName.text = character.name
            characterImage.loadImage(character.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_episode_details_character, parent, false)
    )

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characters[position])

        holder.itemView.characterListRowBackground.setOnClickListener {
            val action = EpisodeDetailsFragmentDirections.actionEpisodeDetailsFragmentToCharacterDetailsFragment(characters[position].id!!)
            holder.itemView.findNavController().navigate(action)
        }
    }
}
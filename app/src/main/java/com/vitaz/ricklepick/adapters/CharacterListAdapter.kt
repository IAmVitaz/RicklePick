package com.vitaz.ricklepick.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.ricklepick.R
import com.vitaz.ricklepick.model.Character
import kotlinx.android.synthetic.main.item_character_list.view.*

class CharacterListAdapter (var characters: ArrayList<Character>):
    RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {

    fun updateCharacterList(newCharacters: List<Character>) {
        characters.clear()
        characters.addAll(newCharacters)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
//        private val imageView = view.imageView
        private val characterName = view.characterNameValue
        private val characterSpecies = view.characterSpeciesValue
        private val genderValue = view.characterGenderValue


        fun bind(character: Character) {
            characterName.text = character.name
            characterSpecies.text = character.species
            genderValue.text = character.gender

//            genderValue.loadImage(character.flag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_character_list, parent, false)
    )

    override fun getItemCount() = characters.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characters[position])
    }
}
package com.cecysamitu.disney.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cecysamitu.disney.remote.model.Character
import com.cecysamitu.disney.databinding.ElementBinding

class CharacterAdapter(
    private val characters: MutableList<Character>,
    private val onCharacterClick: (Character) -> Unit
): RecyclerView.Adapter<CharacterViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun getItemCount(): Int = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)
        holder.itemView.setOnClickListener{
            onCharacterClick(character)
        }
    }
}
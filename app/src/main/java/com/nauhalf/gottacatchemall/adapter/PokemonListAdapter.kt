package com.nauhalf.gottacatchemall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.color.MaterialColors
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.utils.colorOfFirstType
import com.nauhalf.gottacatchemall.core.utils.extractPokemonName
import com.nauhalf.gottacatchemall.databinding.ItemPokemonBinding


class PokemonListAdapter(diffUtil: DiffUtil.ItemCallback<Pokemon>, private val onItemSelected: (pokemon: Pokemon) -> Unit) :
    ListAdapter<Pokemon, PokemonListAdapter.PokemonViewHolder>(diffUtil) {
    inner class PokemonViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(data: Pokemon) {
            binding.apply {
                pokemonId = "#${data.id.toString().padStart(3, '0')}"
                pokemonName = data.name
                val color = MaterialColors.getColor(root, data.types.colorOfFirstType())
                tvPokemonName.setBackgroundColor(color)
                cardPokemon.strokeColor = color
                Glide.with(root)
                    .load(data.imageUrl)
                    .fitCenter()
                    .into(ivPokemon)

                cardPokemon.setOnClickListener {
                    onItemSelected.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.binding(getItem(position))
    }
}
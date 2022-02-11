package com.nauhalf.gottacatchemall.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.color.MaterialColors
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.utils.colorOfType
import com.nauhalf.gottacatchemall.databinding.ItemPokemonBinding


class PokemonListAdapter(diffUtil: DiffUtil.ItemCallback<Pokemon>) :
    ListAdapter<Pokemon, PokemonListAdapter.PokemonViewHolder>(diffUtil) {
    class PokemonViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(data: Pokemon) {
            binding.apply {
                tvPokemonId.text = "#${data.id.toString().padStart(3, '0')}"
                val color = MaterialColors.getColor(root, data.types.colorOfType())
                tvPokemonName.text = data.name.uppercase()
                tvPokemonName.setBackgroundColor(color)
                cardPokemon.strokeColor = color
                Glide.with(root)
                    .load(data.imageUrl)
                    .fitCenter()
                    .into(ivPokemon)
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
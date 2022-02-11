package com.nauhalf.gottacatchemall.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.color.MaterialColors
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.utils.colorOfType
import com.nauhalf.gottacatchemall.core.utils.extractPokemonName
import com.nauhalf.gottacatchemall.databinding.ItemPokemonBinding
import com.nauhalf.gottacatchemall.databinding.ItemPokemonLoadingBinding


class PokemonPagingAdapter(diffUtil: DiffUtil.ItemCallback<Pokemon>) :
    PagingDataAdapter<Pokemon, PokemonPagingAdapter.PokemonViewHolder>(diffUtil) {
    class PokemonViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(data: Pokemon) {
            binding.apply {
                pokemonId = "#${data.id.toString().padStart(3, '0')}"
                pokemonName = data.name.extractPokemonName()
                val color = MaterialColors.getColor(root, data.types.colorOfType())
                tvPokemonName.setBackgroundColor(color)
                cardPokemon.strokeColor = color
                Glide.with(root)
                    .load(data.imageUrl)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
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
        getItem(position)?.let { holder.binding(it) }
    }
}
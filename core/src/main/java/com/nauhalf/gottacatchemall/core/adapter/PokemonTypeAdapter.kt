package com.nauhalf.gottacatchemall.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.nauhalf.gottacatchemall.core.databinding.ItemPokemonStatTitleBinding
import com.nauhalf.gottacatchemall.core.databinding.ItemPokemonTypeBinding
import com.nauhalf.gottacatchemall.core.domain.model.Stat
import com.nauhalf.gottacatchemall.core.domain.model.Type
import com.nauhalf.gottacatchemall.core.utils.colorOfType

class PokemonTypeAdapter :
    ListAdapter<Type, PokemonTypeAdapter.PokemonTypeTitleViewHolder>(object :
        DiffUtil.ItemCallback<Type>() {
        override fun areItemsTheSame(oldItem: Type, newItem: Type): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Type, newItem: Type): Boolean =
            oldItem == newItem && oldItem.slot == newItem.slot && oldItem.type == newItem.type
    }) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonTypeTitleViewHolder {
        return PokemonTypeTitleViewHolder(
            ItemPokemonTypeBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonTypeTitleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class PokemonTypeTitleViewHolder(private val binding: ItemPokemonTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Type) {
            binding.apply {
                data = item.type.lowercase().replaceFirstChar { it.uppercase() }
                cardPokemonType.setCardBackgroundColor(
                    MaterialColors.getColor(
                        this.root,
                        item.colorOfType()
                    )
                )
                executePendingBindings()
            }
        }
    }
}
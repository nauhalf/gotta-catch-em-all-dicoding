package com.nauhalf.gottacatchemall.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.nauhalf.gottacatchemall.core.databinding.ItemPokemonStatBinding
import com.nauhalf.gottacatchemall.core.databinding.ItemPokemonStatTitleBinding
import com.nauhalf.gottacatchemall.core.domain.model.Stat
import com.nauhalf.gottacatchemall.core.utils.colorOfType

class PokemonStatAdapter(@AttrRes private val color: Int) :
    ListAdapter<Stat, PokemonStatAdapter.PokemonStatViewHolder>(object :
        DiffUtil.ItemCallback<Stat>() {
        override fun areItemsTheSame(oldItem: Stat, newItem: Stat): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Stat, newItem: Stat): Boolean =
            oldItem == newItem && oldItem.stat == newItem.stat
    }) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonStatViewHolder {
        return PokemonStatViewHolder(
            ItemPokemonStatBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonStatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class PokemonStatViewHolder(private val binding: ItemPokemonStatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Stat) {

            binding.apply {
                baseStatString = item.baseStat.toString().padStart(3, '0')
                baseStatNumber = item.baseStat


                val indicator = MaterialColors.getColor(this.root, color)
                val alpha = ColorUtils.setAlphaComponent(indicator, 51)
                progressStat.setIndicatorColor(indicator)
                progressStat.trackColor = alpha
                executePendingBindings()
            }

        }
    }
}
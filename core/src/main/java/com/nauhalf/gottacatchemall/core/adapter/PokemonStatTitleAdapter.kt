package com.nauhalf.gottacatchemall.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.nauhalf.gottacatchemall.core.databinding.ItemPokemonStatBinding
import com.nauhalf.gottacatchemall.core.databinding.ItemPokemonStatTitleBinding
import com.nauhalf.gottacatchemall.core.domain.model.Stat
import com.nauhalf.gottacatchemall.core.utils.mapStat

class PokemonStatTitleAdapter(@AttrRes private val color: Int) :
    ListAdapter<Stat, PokemonStatTitleAdapter.PokemonStatTitleViewHolder>(object :
        DiffUtil.ItemCallback<Stat>() {
        override fun areItemsTheSame(oldItem: Stat, newItem: Stat): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Stat, newItem: Stat): Boolean =
            oldItem == newItem && oldItem.baseStat == newItem.baseStat && oldItem.stat == newItem.stat
    }) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonStatTitleViewHolder {
        return PokemonStatTitleViewHolder(
            ItemPokemonStatTitleBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonStatTitleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class PokemonStatTitleViewHolder(private val binding: ItemPokemonStatTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Stat) {
            binding.apply {
                statTitle = item.stat.mapStat()
                tvStatTitle.setTextColor(MaterialColors.getColor(this.root, color))
                executePendingBindings()
            }
        }
    }
}
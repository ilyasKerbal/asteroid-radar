package com.udacity.asteroidradar.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.ListElementBinding

class MainAdapter : ListAdapter<Asteroid, MainAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean = oldItem == newItem
    }

    class ViewHolder private constructor(val binding: ListElementBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(context: Context) : ViewHolder = ViewHolder(ListElementBinding.inflate(LayoutInflater.from(context)))
        }

        fun bind(asteroid: Asteroid) {
            binding.asteriod = asteroid
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
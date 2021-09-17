package com.example.firebasesampleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.firebasesampleapp.databinding.PhraseLayoutBinding

class PhraseAdapter : ListAdapter<String, PhraseViewHolder>(itemComparator) {

    private companion object {
        val itemComparator = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhraseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PhraseLayoutBinding.inflate(layoutInflater, parent, false)

        return PhraseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhraseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}
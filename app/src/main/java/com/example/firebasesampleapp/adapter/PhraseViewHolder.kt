package com.example.firebasesampleapp.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.firebasesampleapp.databinding.PhraseLayoutBinding

class PhraseViewHolder(private val binding: PhraseLayoutBinding)
    : RecyclerView.ViewHolder(binding.root) {

        fun bind(phrase: String) {

            binding.phraseView.text = phrase
        }

}
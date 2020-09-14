package com.example.worldofhunting.ui.screens.news_list

import androidx.recyclerview.widget.RecyclerView
import com.example.worldofhunting.data.models.TopHeadlines.Article
import com.example.worldofhunting.databinding.ItemNewsListBinding

class NewsViewHolder(private val binding: ItemNewsListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Article, callback: ((Article) -> Unit)?) {
        item.bind(binding)
        binding.container.setOnClickListener { callback?.invoke(item) }
    }
}
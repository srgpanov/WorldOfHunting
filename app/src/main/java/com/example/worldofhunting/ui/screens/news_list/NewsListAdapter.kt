package com.example.worldofhunting.ui.screens.news_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worldofhunting.data.models.TopHeadlines.Article

import com.example.worldofhunting.databinding.ItemNewsListBinding

class NewsListAdapter :
    RecyclerView.Adapter<NewsViewHolder>() {
    private val items: MutableList<Article> = mutableListOf()
    var clickListener: ((Article) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(
            ItemNewsListBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(news: List<Article>) {
        items.clear()
        items.addAll(news)
        notifyDataSetChanged()
    }


}

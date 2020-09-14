package com.example.worldofhunting.ui.screens.news_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.worldofhunting.data.models.TopHeadlines.Article
import com.example.worldofhunting.databinding.ItemNewsListBinding


class PagedNewsAdapter(diff: DiffUtil.ItemCallback<Article>) :
    PagingDataAdapter<Article, NewsViewHolder>(diff) {
    var clickListener: ((Article) -> Unit)? = null
    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int
    ) {
        val item =getItem(position)
        if (item != null) {
            holder.bind(item, clickListener)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(
            ItemNewsListBinding.inflate(inflater, parent, false)
        )
    }


    object Comparator: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title==newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return (oldItem.title==newItem.title) and (oldItem.publishedAt==newItem.publishedAt)
        }

    }

}

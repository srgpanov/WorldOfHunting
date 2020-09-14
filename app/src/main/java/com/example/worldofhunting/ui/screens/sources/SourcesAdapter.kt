package com.example.worldofhunting.ui.screens.sources

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worldofhunting.data.models.Sources.Source

import com.example.worldofhunting.databinding.ItemSourceBinding
import java.util.ArrayList

class SourcesAdapter() :
    RecyclerView.Adapter<SourcesAdapter.SourcesAdapterViewHolder>() {
    val items: MutableList<Source> = mutableListOf()
    var checkedListener:((source:Source)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourcesAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SourcesAdapterViewHolder(
            ItemSourceBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SourcesAdapterViewHolder, position: Int) {
        holder.bind(items[position],position,checkedListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(sources: List<Source>) {
        items.clear()
        items.addAll(sources)
        notifyDataSetChanged()
    }

    class SourcesAdapterViewHolder(private val binding: ItemSourceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Source,position: Int, checkedListener: ((source: Source) -> Unit)?) {
            binding.checkbox.setOnCheckedChangeListener(null)
            item.bind(binding)
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                item.isSelected=isChecked
                checkedListener?.invoke(item)
            }
        }
    }
}

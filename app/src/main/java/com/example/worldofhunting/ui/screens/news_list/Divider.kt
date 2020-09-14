package com.example.worldofhunting.ui.screens.news_list

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import com.example.worldofhunting.R
import com.example.worldofhunting.other.getDrawableCompat

class Divider(
    context: Context
) : RecyclerView.ItemDecoration() {
    var drawable: Drawable? = context.getDrawableCompat(R.drawable.custom_divider)
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        drawable?.let {
            val childCount = parent.adapter?.itemCount ?: 0
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                if (child != null) {
                    val params = child.layoutParams as RecyclerView.LayoutParams
                    val top = child.bottom + params.bottomMargin
                    val bottom = top + it.intrinsicHeight
                    it.setBounds(left, top, right, bottom)
                    it.draw(c)
                }
            }
        }
    }
}
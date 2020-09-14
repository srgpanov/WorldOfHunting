package com.example.worldofhunting.domain_logic.view_entities

import androidx.viewbinding.ViewBinding

interface ViewItem<VB:ViewBinding> {
    fun bind(binding: VB)
}
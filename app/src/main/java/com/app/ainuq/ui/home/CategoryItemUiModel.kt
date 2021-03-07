package com.app.ainuq.ui.home

import java.util.*

data class CategoryItemUiModel(
    val id: Long = UUID.randomUUID().mostSignificantBits,
    val name: String,
)
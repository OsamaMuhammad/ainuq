package com.app.ainuq.ui.profile

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class UserItemUiModel(
    val id: Long = UUID.randomUUID().mostSignificantBits,
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String
)

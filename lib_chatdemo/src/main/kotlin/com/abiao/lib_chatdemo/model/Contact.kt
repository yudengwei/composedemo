package com.abiao.lib_chatdemo.model

import androidx.annotation.DrawableRes

data class Contact(
    val id: Long,
    val name: String,
    val isOnline: Boolean,
    val lastTime: Long,
    @DrawableRes val drawableId: Int
)

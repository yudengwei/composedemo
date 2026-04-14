package com.abiao.lib_chatdemo.model

data class TopEntry(
    val title: String? = null,
    val icons: List<Int>? = null,
    val showBack: Boolean = false,
    val onClickBack: (() -> Unit)? = null,
    val onClickIcon: ((Int) -> Unit)? = null
)

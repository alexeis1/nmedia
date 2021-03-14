package ru.netology.nmedia.dto

import android.graphics.drawable.Icon

data class Post(
    val id           : Long = 1,
    val author       : String = "",
    val content      : String = "",
    val published    : String = "",
    val avatar       : Icon? = null,

    val likes        : Long = 10L,
    val shares       : Long = 5L,
    val views        : Long = 5L,

    val isLikedByMe   : Boolean = false,
    val mySharedCount : Long = 0L
)


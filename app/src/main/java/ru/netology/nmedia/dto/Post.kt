package ru.netology.nmedia.dto

import android.graphics.drawable.Icon
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import java.net.URL

@Parcelize
data class Post(
    val id: Long = 1,
    val author: String = "",
    val content: String = "",
    val published: String = "",
    val avatar: Icon? = null,
    val video: URL? = null,

    val likes: Long = 10L,
    val shares: Long = 5L,
    val views: Long = 5L,

    val isLikedByMe: Boolean = false,
    val mySharedCount: Long = 0L
) : Parcelable


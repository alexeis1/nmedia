package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val likes: Long = 0,
    val shares : Long = 0,
    val views : Long = 0,
) {
    fun toDto() = Post(
        id = id,
        author = author,
        content = content,
        published = published, isLikedByMe = likedByMe, likes = likes,
        shares = shares, views = views
    )

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(
                id = dto.id,
                author = dto.author,
                content = dto.content,
                published = dto.published,
                likedByMe = dto.isLikedByMe,
                likes = dto.likes,
                shares = dto.shares,
                views = dto.views
            )

    }
}
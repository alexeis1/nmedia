package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity
import ru.netology.nmedia.repository.PostRepository as PostRepository

class PostRepositoryImpl(
    private val dao: PostDao
) : PostRepository {
    override fun getAll(): LiveData<List<Post>> = Transformations.map(dao.getAll()) {list->
        list.map { Post(
            id = it.id,
            author = it.author,
            content = it.content,
            published = it.published,
            likes = it.likes,
            shares = it.shares,
            views = it.views,
            isLikedByMe = it.likedByMe)
        }
    }

    override fun like(id: Long, isLiked: Boolean) {
        dao.likeById(id)
    }

    override fun share(id: Long) {
        dao.shareById(id)
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }
}
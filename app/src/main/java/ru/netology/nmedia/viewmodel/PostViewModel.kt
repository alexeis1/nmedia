package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl


class PostViewModel() : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data                    = repository.getAll()
    fun like(id: Long, isLiked : Boolean) = repository.like(id, isLiked)
    fun share(id: Long)                   = repository.share(id)
}
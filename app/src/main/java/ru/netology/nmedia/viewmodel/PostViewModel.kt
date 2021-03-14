package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl


class PostViewModel() : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data                    = repository.get()
    fun like(isLiked : Boolean) = repository.like(isLiked)
    fun share()                 = repository.share()
}
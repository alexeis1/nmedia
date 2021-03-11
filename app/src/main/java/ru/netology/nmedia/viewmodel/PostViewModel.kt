package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

data class PostContext(
    var repository : PostRepository? = null
)

class PostViewModel(context : PostContext) : ViewModel() {
    private val repository: PostRepository
    /**
     * Если активити создается в первый раз, то создаем модель.
     * иначе используем данные существующего контекста
     */
    init {
        if (context.repository == null) {
            context.repository = PostRepositoryInMemoryImpl()
        }
        repository = context.repository!!
    }

    val data                    = repository.get()
    fun like(isLiked : Boolean) = repository.like(isLiked)
    fun share()                 = repository.share()


}
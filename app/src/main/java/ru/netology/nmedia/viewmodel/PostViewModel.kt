package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl


class PostViewModel() : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data                              = repository.getAll()
    fun like(id: Long, isLiked : Boolean) = repository.like(id, isLiked)
    fun share(id: Long)                   = repository.share(id)
    fun removeById(id: Long)              = repository.removeById(id)

    private val emptyPost = Post(id =  0)
    val edited    = MutableLiveData(emptyPost)
    fun save(){
        edited.value?.let {
            repository.save(it)
            edited.value = emptyPost
        }
    }
    fun changeContent(content : String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content == text){
                return
            }
            edited.value = it.copy(content = text)
        }
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun cancelEdit() {
        edited.value = emptyPost
    }
}
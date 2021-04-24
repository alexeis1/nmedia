package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl


class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryImpl(
        AppDb.getInstance(context = application).postDao())

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
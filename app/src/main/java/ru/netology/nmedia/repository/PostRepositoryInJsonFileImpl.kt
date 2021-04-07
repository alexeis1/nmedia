package ru.netology.nmedia.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositoryInJsonFileImpl(private val context : Context) : PostRepository {
    /**
     * класс рассылающий изменения подписчикам
     */
    private val gson  = Gson()
    private val type   = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private var nextId = 1L
    private var posts  = emptyList<Post>()
    private val data   = MutableLiveData(posts)
    private val fileName = "posts.json"

    init {
        val file = context.filesDir.resolve(fileName)

        try {
            if (!file.exists()) throw RuntimeException("File $fileName not exists")
            context.openFileInput(fileName).bufferedReader().use {
                posts = gson.fromJson(it, type)
                data.value = posts
            }
        }
        catch (e : RuntimeException)
        {
            Log.e("PostRepository", e.toString())
            sync()
        }
    }

    override fun getAll(): LiveData<List<Post>> = data
    /**
     * отправляет изменения в модель
     * isLiked - true пост лайкнули
     * isLiked - false пост дизлайкнули
     */
    override fun like(id: Long, isLiked : Boolean) {
        posts = posts.map {
            if (it.id != id) it else it.copy(isLikedByMe = isLiked)
        }
        data.value = posts
        sync()
    }

    override fun save(post : Post) {
        if (post.id == 0L){
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    isLikedByMe = false,
                    published = "now")
            ) + posts
            data.value = posts
            sync()
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
        sync()
    }

    /**
     * указывает на то, что постом поделились
     */
    override fun share(id: Long){
        posts = posts.map {
            if (it.id != id) it else it.copy(mySharedCount = it.mySharedCount+1)
        }
        data.value = posts
        sync()
    }

    override fun removeById(id: Long) {
        Log.d("removeById", posts.size.toString())
        posts = posts.filter { it.id != id }
        Log.d("removeById", posts.size.toString())
        data.value = posts
        sync()
    }

    private fun sync(){
        context.openFileOutput(fileName, Context.MODE_PRIVATE).bufferedWriter().use{
            it.write(gson.toJson(posts))
        }
    }
}
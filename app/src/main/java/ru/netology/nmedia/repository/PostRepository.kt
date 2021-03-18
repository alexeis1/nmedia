package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {

    /**
     * выдает набор данных для инициализации View
     */
    fun getAll(): LiveData<List<Post>>

    /**
     * отправляет изменения в модель
     * isLiked - true пост лайкнули
     * isLiked - false пост дизлайкнули
     */
    fun like(id: Long, isLiked : Boolean)

    /**
     * указывает на то, что постом поделились
     */
    fun share(id: Long)
    /**
        Удаляем пост по id
     */
    fun removeById(id: Long)

    /**
     * Создаем новый пост
     */
    fun save(post: Post)
}
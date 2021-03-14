package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    /**
     * выдает набор данных для инициализации View
     */
    fun get(): LiveData<Post>

    /**
     * отправляет изменения в модель
     * isLiked - true пост лайкнули
     * isLiked - false пост дизлайкнули
     */
    fun like(isLiked : Boolean)

    /**
     * указывает на то, что постом поделились
     */
    fun share()
}
package ru.netology.nmedia.repository

import android.graphics.drawable.Icon
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.R
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    init {

    }
    /**
     * стартовые данные для инициализации модели
     */
    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "21 мая в 18:36",
        avatar = Icon.createWithResource("ru.netology", R.drawable.post_avatar_drawable),
        likes = 10,
        shares = 5,
        views = 5,
        isLikedByMe = false,
        mySharedCount = 0
    )
    /**
     * класс рассылающий изменения подписчикам
     */
    private val data = MutableLiveData(post)

    /**
     * выдает набор данных для инициализации View
     */
    override fun get(): LiveData<Post> = data

    /**
     * отправляет изменения в модель
     * isLiked - true пост лайкнули
     * isLiked - false пост дизлайкнули
     */
    override fun like(isLiked : Boolean) {
        post = post.copy(isLikedByMe = isLiked)
        data.value  = post
    }

    /**
     * указывает на то, что постом поделились
     */
    override fun share(){
        post = post.copy(mySharedCount = post.mySharedCount+1)
        data.value = post
    }
}

/**
 * Альтернативный набор данных для теста View
private var post = Post(
id = 1L,
author = "Нетология. Университет интернет-профессий",
content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
published = "21 мая в 18:36",
avatar = Icon.createWithResource("drawable", R.drawable.post_avatar_drawable),
likes  = 12_000L,
shares = 5_100L,
views  = 50_000_000L,
isLikedByMe = false,
mySharedCount = 0
)
    */
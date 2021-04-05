package ru.netology.nmedia.repository

import android.graphics.drawable.Icon
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.R
import ru.netology.nmedia.dto.Post
import java.net.URL

class PostRepositoryInMemoryImpl : PostRepository {
    /**
     * стартовые данные для инициализации модели
     */
    private var posts = listOf(
        Post(
            id = 9,
            author = "Нетология. Университет интернет-профессий",
            content = "Иллюстрация в медиа. Приглашение на дизайн-лекторий",
            published = "21 мая в 18:36",
            avatar = Icon.createWithResource("ru.netology", R.drawable.post_avatar_drawable),
            video = URL("https://www.youtube.com/watch?v=WhWc3b3KhnY"),
            likes = 10,
            shares = 5,
            views = 5,
            isLikedByMe = false,
            mySharedCount = 0
        ),
        Post(
            id = 8,
            author = "Нетология. Университет интернет-профессий",
            content = "Делиться впечатлениями о любимых фильмах легко, а что если рассказать так, чтобы все заскучали \\uD83D\\uDE34\n",
            published = "21 мая в 18:36",
            avatar = Icon.createWithResource("ru.netology", R.drawable.post_avatar_drawable),
            likes = 10,
            shares = 5,
            views = 5,
            isLikedByMe = false,
            mySharedCount = 0
        ),
        Post(
            id = 7,
            author = "Нетология. Университет интернет-профессий",
            content = "Таймбоксинг — отличный способ навести порядок в своём календаре и разобраться с делами, которые долго откладывали на потом. Его главный принцип — на каждое дело заранее выделяется определённый отрезок времени. В это время вы работаете только над одной задачей, не переключаясь на другие. Собрали советы, которые помогут внедрить таймбоксинг \\uD83D\\uDC47\\uD83C\\uDFFB",
            published = "21 мая в 18:36",
            avatar = Icon.createWithResource("ru.netology", R.drawable.post_avatar_drawable),
            likes = 10,
            shares = 5,
            views = 5,
            isLikedByMe = false,
            mySharedCount = 0
        ),
        Post(
            id = 6,
            author = "Нетология. Университет интернет-профессий",
            content = "\\uD83D\\uDE80 24 сентября стартует новый поток бесплатного курса «Диджитал-старт: первый шаг к востребованной профессии» — за две недели вы попробуете себя в разных профессиях и определите, что подходит именно вам → http://netolo.gy/fQ",
            published = "21 мая в 18:36",
            avatar = Icon.createWithResource("ru.netology", R.drawable.post_avatar_drawable),
            likes = 10,
            shares = 5,
            views = 5,
            isLikedByMe = false,
            mySharedCount = 0
        ),
        Post(
            id = 5,
            author = "Нетология. Университет интернет-профессий",
            content = "Диджитал давно стал частью нашей жизни: мы общаемся в социальных сетях и мессенджерах, заказываем еду, такси и оплачиваем счета через приложения.",
            published = "21 мая в 18:36",
            avatar = Icon.createWithResource("ru.netology", R.drawable.post_avatar_drawable),
            likes = 10,
            shares = 5,
            views = 5,
            isLikedByMe = false,
            mySharedCount = 0
        ),
        Post(
            id = 4,
            author = "Нетология. Университет интернет-профессий",
            content = "Большая афиша мероприятий осени: конференции, выставки и хакатоны для жителей Москвы, Ульяновска и Новосибирска \\uD83D\\uDE09",
            published = "21 мая в 18:36",
            avatar = Icon.createWithResource("ru.netology", R.drawable.post_avatar_drawable),
            likes = 10,
            shares = 5,
            views = 5,
            isLikedByMe = false,
            mySharedCount = 0
        ),
        Post(
            id = 3,
            author = "Нетология. Университет интернет-профессий",
            content = "Языков программирования много, и выбрать какой-то один бывает нелегко. Собрали подборку статей, которая поможет вам начать, если вы остановили свой выбор на JavaScript.",
            published = "21 мая в 18:36",
            avatar = Icon.createWithResource("ru.netology", R.drawable.post_avatar_drawable),
            likes = 10,
            shares = 5,
            views = 5,
            isLikedByMe = false,
            mySharedCount = 0
        ),
        Post(
            id = 2,
            author = "Нетология. Университет интернет-профессий",
            content = "Знаний хватит на всех: на следующей неделе разбираемся с разработкой мобильных приложений, учимся рассказывать истории и составлять PR-стратегию прямо на бесплатных занятиях \\uD83D\\uDC47",
            published = "21 мая в 18:36",
            avatar = Icon.createWithResource("ru.netology", R.drawable.post_avatar_drawable),
            likes = 10,
            shares = 5,
            views = 5,
            isLikedByMe = false,
            mySharedCount = 0
        ),
        Post(
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
    )
    /**
     * класс рассылающий изменения подписчикам
     */
    private val data   = MutableLiveData(posts)

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
    }

    private var nextId : Long = posts.first().id + 1
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
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
    }

    /**
     * указывает на то, что постом поделились
     */
    override fun share(id: Long){
        posts = posts.map {
            if (it.id != id) it else it.copy(mySharedCount = it.mySharedCount+1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        Log.d("removeById", posts.size.toString())
        posts = posts.filter { it.id != id }
        Log.d("removeById", posts.size.toString())
        data.value = posts
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
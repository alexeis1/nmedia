package ru.netology

import android.graphics.Typeface
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post as Post

class MainActivity : AppCompatActivity() {
    private var post     : Post?    = null
    private val likesMV  : LikesMV  = LikesMV(this)
    private val sharesMV : SharesMV = SharesMV(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val newPost : Post = initTestPost()
        post = newPost

        likesMV.binding  = binding
        sharesMV.binding = binding

        updatePostView(binding, newPost)
        likesMV.initLikeBindings(binding, newPost)
        sharesMV.initSharesBindings(binding, newPost)
    }

    private fun updatePostView(binding: ActivityMainBinding, post: Post) {
        with(binding){
            author.   text = post.author
            published.text = post.published
            postText. text = post.content
            textViews.text = NumberDecoration(post.views).toString()

            likesMV.updateLikeState(binding, post)
            sharesMV.updateSharesState(binding, post)
            avatar.setImageIcon(post.avatar)
        }
    }
/*
    private fun initTestPost() = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "21 мая в 18:36",
        avatar = Icon.createWithResource(this, R.drawable.post_avatar_drawable),
        likes = 10,
        shares = 5,
        views = 5,
        isLikedByMe = false,
        mySharedCount = 0
    )*/

    private fun initTestPost() = Post(
        id = 1L,
        author = "Нетология. Университет интернет-профессий",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "21 мая в 18:36",
        avatar = Icon.createWithResource(this, R.drawable.post_avatar_drawable),
        likes  = 12_000L,
        shares = 5_100L,
        views  = 50_000_000L,
        isLikedByMe = false,
        mySharedCount = 0
    )

}
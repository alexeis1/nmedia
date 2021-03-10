package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import ru.netology.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostContext
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.dto.Post as Post

class MainActivity : AppCompatActivity() {
    /**
     * контект в котором будет сохраняться модель данных
     */
    companion object{
        var context = PostContext()
    }
    private val likesHandlers  : LikesHandlers  = LikesHandlers(this)
    private val sharesHandlers : SharesHandlers = SharesHandlers(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        likesHandlers.binding  = binding
        sharesHandlers.binding = binding

        /**
         * время жизни viewModel будет ограничиваться временем жизни MainActivity
         * т.е. пока будут сохранятся ссылки на viewModel в лямбда функциях обработчиках
         */
        val viewModel = PostViewModel(context)
        viewModel.data.observe(this, { post->
            updatePostView(binding, post)
        })

        likesHandlers.initLikeBindings   (binding, viewModel)
        sharesHandlers.initSharesBindings(binding, viewModel)
    }

    private fun updatePostView(binding: ActivityMainBinding, post: Post) {
        with(binding){
            author.   text = post.author
            published.text = post.published
            postText. text = post.content
            textViews.text = NumberDecoration(post.views).toString()

            likesHandlers.updateLikeState(binding, post)
            sharesHandlers.updateSharesState(binding, post)
            avatar.setImageIcon(post.avatar)
        }
    }


}
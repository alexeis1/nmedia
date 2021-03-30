package ru.netology.nmedia.adapter

import android.content.Context
import android.content.res.Resources
import android.view.View
import ru.netology.R
import ru.netology.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class LikesHandlers(
    private val context: Context,
){
    private val resources: Resources
        get() = context.resources

    fun updateLikeState(binding: CardPostBinding, post: Post)
    {
        with(binding){
            btnLikes.isChecked = post.isLikedByMe
            btnLikes.text = NumberDecoration(post.likes + if (post.isLikedByMe) 1 else 0).toString()
        }
    }

    fun initLikeBindings(binding: CardPostBinding, viewModel : PostViewModel, post : Post){
        val onClick = fun(_: View) {
                viewModel.like(post.id, !post.isLikedByMe)
        }

        binding.btnLikes.setOnClickListener(onClick)
    }
}
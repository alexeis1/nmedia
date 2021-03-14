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
            if (post.isLikedByMe)
            {
                btnLikes.setImageResource(R.drawable.ic_liked_24)
                textLikes.setTextColor(resources.getColor(R.color.likesRed, null))
                textLikes.text = NumberDecoration(post.likes + 1).toString()
            }
            else
            {
                btnLikes.setImageResource(R.drawable.ic_like_24)
                textLikes.setTextColor(resources.getColor(R.color.likesGray, null))
                textLikes.text = NumberDecoration(post.likes).toString()
            }
        }
    }

    fun initLikeBindings(binding: CardPostBinding, viewModel : PostViewModel, post : Post){
        val onClick = fun(_: View) {
                viewModel.like(post.id, !post.isLikedByMe)
        }

        binding.btnLikes.setOnClickListener(onClick)
        binding.textLikes.setOnClickListener(onClick)
    }
}
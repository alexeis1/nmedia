package ru.netology

import android.content.res.Resources
import android.view.View
import ru.netology.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class LikesMV(
    private val activity: MainActivity,
){
    var binding  : ActivityMainBinding? = null
    private val resources: Resources
        get() = activity.resources

    fun updateLikeState(binding: ActivityMainBinding, post: Post)
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

    fun initLikeBindings(binding: ActivityMainBinding, post: Post){
        val onClick = fun(_: View) {
            post.isLikedByMe = !post.isLikedByMe
            updateLikeState(binding, post)
        }

        binding.btnLikes.setOnClickListener(onClick)
        binding.textLikes.setOnClickListener(onClick)
    }
}
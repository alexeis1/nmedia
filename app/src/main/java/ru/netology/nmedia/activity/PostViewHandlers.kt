package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import ru.netology.R
import ru.netology.databinding.CardPostBinding
import ru.netology.nmedia.activity.FeedFragment.Companion.postArg
import ru.netology.nmedia.adapter.CrudHelper
import ru.netology.nmedia.adapter.LikesHandlers
import ru.netology.nmedia.adapter.NumberDecoration
import ru.netology.nmedia.adapter.SharesHandlers
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class PostViewHandlers(
        private val binding: CardPostBinding,
        private val context : Context,
        private val viewModel: PostViewModel)
{
    private val likesHandlers  : LikesHandlers = LikesHandlers(context)
    private val sharesHandlers : SharesHandlers = SharesHandlers(context)
    private val crudHelper     : CrudHelper = CrudHelper(context)

    fun bind(post : Post){
        likesHandlers. initLikeBindings  (binding, viewModel, post)
        sharesHandlers.initSharesBindings(binding, viewModel, post)
        crudHelper.    initCrudBindings  (binding, viewModel, post)
        with(binding)
        {
            author.   text = post.author
            published.text = post.published
            postText. text = post.content
            textViews.text = NumberDecoration(post.views).toString()

            if (post.video != null) {
                videoElements.visibility = View.VISIBLE
                setPlayVideoHandler(post)
            } else {
                videoElements.visibility = View.GONE
            }

            likesHandlers.updateLikeState(   this, post)
            sharesHandlers.updateSharesState(this, post)
            avatar.setImageIcon(post.avatar)

            binding.cardPost.setOnClickListener {
                val navController = findNavController(this.cardPost)
                navController.
                    navigate(R.id.action_feedFragment_to_cardFragment, Bundle().apply {
                    postArg = post
                })
            }
        }
    }

    private fun setPlayVideoHandler(post : Post){
        with(binding)
        {
            val onClick = fun(_: View) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video?.toString()))
                sharesHandlers.toString()
                context.startActivity(intent)
            }
            playButton.setOnClickListener(onClick)
            videoPreview.setOnClickListener(onClick)
        }
    }
}

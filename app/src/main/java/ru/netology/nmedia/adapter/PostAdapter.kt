package ru.netology.nmedia.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostDiffCallback
import ru.netology.nmedia.viewmodel.PostViewModel

class PostAdapter(
    private val viewModel: PostViewModel
    ) : ListAdapter<Post,PostViewHolder>(PostDiffCallback())
{/*
    var list = emptyList<Post>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return PostViewHolder(binding, parent.context, viewModel)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val context : Context,
    private val viewModel: PostViewModel) : RecyclerView.ViewHolder(binding.root)
{
    private val likesHandlers  : LikesHandlers  = LikesHandlers(context)
    private val sharesHandlers : SharesHandlers = SharesHandlers(context)
    private val crudHelper     : CrudHelper     = CrudHelper(context)

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


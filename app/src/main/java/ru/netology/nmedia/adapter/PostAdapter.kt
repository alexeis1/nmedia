package ru.netology.nmedia.adapter

import android.content.Context
import android.view.LayoutInflater
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
    context : Context,
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

            likesHandlers.updateLikeState(   this, post)
            sharesHandlers.updateSharesState(this, post)
            avatar.setImageIcon(post.avatar)
        }
    }
}


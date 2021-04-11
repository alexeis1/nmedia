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
import ru.netology.nmedia.activity.PostViewHandlers
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostDiffCallback
import ru.netology.nmedia.viewmodel.PostViewModel

class PostAdapter(
    private val viewModel: PostViewModel
    ) : ListAdapter<Post,PostViewHolder>(PostDiffCallback())
{
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
    binding: CardPostBinding,
    context : Context,
    viewModel: PostViewModel) : RecyclerView.ViewHolder(binding.root)
{
    private val handles = PostViewHandlers(binding, context, viewModel)
    fun bind(post : Post){
        handles.bind(post)
    }
}


package ru.netology.nmedia.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.ImageFullscreenFragment.Companion.urlArg
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Attachment
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.enumeration.AttachmentType
import ru.netology.nmedia.view.load
import ru.netology.nmedia.view.loadCircleCrop
import ru.netology.nmedia.viewmodel.AuthViewModel

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onShare(post: Post) {}
}

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener,
    private val authViewModel: AuthViewModel
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener, authViewModel)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,
    private val authViewModel: AuthViewModel
) : RecyclerView.ViewHolder(binding.root) {

    private fun bindAttachment(attachment: Attachment?){
        binding.apply {
            if (attachment != null) {
                if (attachment.type == AttachmentType.IMAGE) {
                    imageView.visibility = View.VISIBLE
                    imageView.load("${BuildConfig.BASE_URL}/media/${attachment.url}")
                    imageView.setOnClickListener {
                        imageView.findNavController()
                            .navigate(
                                R.id.action_feedFragment_to_imageFullscreenFragment,
                                Bundle().apply {
                                    urlArg = attachment.url
                                }
                            )
                    }
                } else {
                    imageView.visibility = View.GONE
                }
            } else {
                imageView.visibility = View.GONE
            }
        }
    }

    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published.toString()
            content.text = post.content
            avatar.loadCircleCrop("${BuildConfig.BASE_URL}/avatars/${post.authorAvatar}")
            like.isCheckable = authViewModel.authenticated
            like.isChecked = post.likedByMe
            like.text = "${post.likes}"
            bindAttachment(post.attachment)

            menu.visibility = if (post.ownedByMe) View.VISIBLE else View.INVISIBLE

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    menu.setGroupVisible(R.id.owned, post.ownedByMe)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }

            like.setOnClickListener {
                onInteractionListener.onLike(post)
            }

            share.setOnClickListener {
                onInteractionListener.onShare(post)
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}

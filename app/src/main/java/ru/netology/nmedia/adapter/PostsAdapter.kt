package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Attachment
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.enumeration.AttachmentType

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onShare(post: Post) {}
}

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener,
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {

    private fun loadAvatar(authorAvatar : String){
        binding.apply {
            //удаляем старую картину с предыдущего поста
            //если карточка была переиспользована
            Glide.with(avatar).clear(avatar)
            //загружаем аватар в новую карточку
            val url = "http://10.0.2.2:9999/avatars/${authorAvatar}"
            Glide.with(avatar)
                .load(url)
                .transform(CircleCrop())
                .placeholder(R.drawable.ic_loading_100dp)
                .error(R.drawable.ic_error_100dp)
                .timeout(10_000)
                .into(avatar)
        }
    }

    private fun loadAttachment(attachment: Attachment) {
        if (attachment.type == AttachmentType.IMAGE)
        {
            binding.attachment.contentDescription = attachment.description
            binding.attachment.visibility = View.VISIBLE
            //удаляем старую картину с предыдущего поста
            //если карточка была переиспользована
            Glide.with(binding.attachment).clear(binding.attachment)
            //загружаем аватар в новую карточку
            val url = "http://10.0.2.2:9999/images/${attachment.url}"
            Glide.with(binding.attachment)
                .load(url)
                .placeholder(R.drawable.ic_loading_100dp)
                .error(R.drawable.ic_error_100dp)
                .timeout(10_000)
                .into(binding.attachment)
        }
    }

    fun bind(post: Post) {
        binding.apply {
            author.text    = post.author
            published.text = post.published.toString()
            content.text   = post.content
            // в адаптере
            like.isChecked = post.likedByMe
            like.text      = "${post.likes}"

            loadAvatar(post.authorAvatar)
            attachment.visibility = View.GONE
            post.attachment?.apply { loadAttachment(this) }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
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

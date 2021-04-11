package ru.netology.nmedia.adapter

import android.content.Context
import android.content.res.Resources
import android.widget.PopupMenu
import ru.netology.R
import ru.netology.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class CrudHelper (private val context: Context,
){
    private val resources: Resources
    get() = context.resources

    fun initCrudBindings(binding: CardPostBinding, viewModel : PostViewModel, post : Post){
        binding.menuButton.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { item->
                    when(item.itemId){
                        R.id.remove -> onRemoveListener(post, viewModel)
                        R.id.edit   -> onEditListener(post, viewModel)
                        else        -> false
                    }
                }
            }.show()
        }
    }

    private fun onEditListener(post: Post, viewModel : PostViewModel): Boolean {
        viewModel.edit(post)
        return true
    }

    private fun onRemoveListener(post: Post, viewModel : PostViewModel): Boolean {
        viewModel.removeById(post.id)
        return true
    }
}
package ru.netology.nmedia.adapter

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.view.View
import ru.netology.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class SharesHandlers(private val context: Context
) {
    private val resources: Resources
        get() = context.resources

    fun updateSharesState(binding: CardPostBinding, post: Post)
    {
        with(binding){
            textShares.text = NumberDecoration(post.shares + post.mySharedCount).toString()
            textShares.setTypeface(null, if (post.mySharedCount > 0) Typeface.BOLD else Typeface.NORMAL)
        }
    }

    fun initSharesBindings(binding: CardPostBinding, viewModel : PostViewModel, post : Post){
        val onClick = fun(_: View) {
                viewModel.share(post.id)
        }

        binding.btnShares.setOnClickListener(onClick)
        binding.textShares.setOnClickListener(onClick)
    }
}
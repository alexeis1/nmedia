package ru.netology.nmedia.adapter

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Typeface
import android.view.View
import ru.netology.R
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
            btnShares.text = NumberDecoration(post.shares + post.mySharedCount).toString()
            btnShares.setTypeface(null, if (post.mySharedCount > 0) Typeface.BOLD else Typeface.NORMAL)
        }
    }

    fun initSharesBindings(binding: CardPostBinding, viewModel : PostViewModel, post : Post)
    {
        val onClick = fun(_: View)
        {
            with(binding) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT,post.content)
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(intent, context.getString(R.string.chooser_share_post))
                context.startActivity(shareIntent)
            }

            viewModel.share(post.id)
        }

        binding.btnShares.setOnClickListener(onClick)
    }
}
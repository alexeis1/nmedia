package ru.netology.nmedia.activity

import android.content.res.Resources
import android.graphics.Typeface
import android.view.View
import ru.netology.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class SharesHandlers(private val activity: MainActivity
) {
    var binding  : ActivityMainBinding? = null
    private val resources: Resources
        get() = activity.resources

    fun updateSharesState(binding: ActivityMainBinding, post: Post)
    {
        with(binding){
            textShares.text = NumberDecoration(post.shares + post.mySharedCount).toString()
            textShares.setTypeface(null, if (post.mySharedCount > 0) Typeface.BOLD else Typeface.NORMAL)
        }
    }

    fun initSharesBindings(binding: ActivityMainBinding, viewModel : PostViewModel){
        val onClick = fun(_: View) {
                viewModel.share()
        }

        binding.btnShares.setOnClickListener(onClick)
        binding.textShares.setOnClickListener(onClick)
    }
}
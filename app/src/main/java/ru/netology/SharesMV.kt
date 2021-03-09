package ru.netology

import android.content.res.Resources
import android.graphics.Typeface
import android.view.View
import ru.netology.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class SharesMV(    private val activity: MainActivity
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

    fun initSharesBindings(binding: ActivityMainBinding, post: Post){
        val onClick = fun(_: View) {
            ++post.mySharedCount
            updateSharesState(binding, post)
        }

        binding.btnShares.setOnClickListener(onClick)
        binding.textShares.setOnClickListener(onClick)
    }
}
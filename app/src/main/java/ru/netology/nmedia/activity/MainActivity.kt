package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.databinding.ActivityMainBinding
import ru.netology.databinding.CardPostBinding
import ru.netology.nmedia.adapter.LikesHandlers
import ru.netology.nmedia.adapter.NumberDecoration
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.SharesHandlers
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.dto.Post as Post

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(viewModel)
        binding.list.adapter = adapter

        viewModel.data.observe(this, { posts->
            adapter.submitList(posts)
        })
    }

}
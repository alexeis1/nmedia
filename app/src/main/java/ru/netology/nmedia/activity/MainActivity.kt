package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.R
import ru.netology.databinding.ActivityMainBinding
import ru.netology.databinding.CardPostBinding
import ru.netology.nmedia.adapter.LikesHandlers
import ru.netology.nmedia.adapter.NumberDecoration
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.SharesHandlers
import ru.netology.nmedia.utils.AndroidUtils
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

        binding.save.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }

        viewModel.edited.observe(this, { post->
            if (post.id == 0L){
                return@observe
            }
            with(binding.content){
                requestFocus()
                setText(post.content)
            }
        })

        //Функция отмены становится видна только пока редактор текста
        //имеет фокус
        binding.content.setOnFocusChangeListener { _, hasFocus ->
            binding.editMsgGroup.visibility = if (hasFocus) View.VISIBLE else View.GONE
            //Нет фокуса, клавиатура и не нужна
            if (!hasFocus) AndroidUtils.hideKeyboard(binding.content)
        }

        //кнопка отмены редактирования
        binding.cancelButton.setOnClickListener {
            //убираем набранный текст или текст поста
            binding.content.text = null
            //очидаем ViewModel от данный (в случае если были)
            viewModel.cancelEdit()
            //Забираем фокус. Это действие скроет доп. панель
            binding.content.clearFocus()
        }
    }

}
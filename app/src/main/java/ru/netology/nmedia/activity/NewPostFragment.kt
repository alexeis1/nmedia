package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.databinding.FragmentNewPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.utils.PostArg
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {
    companion object{
        var Bundle.postArg : Post? by PostArg
        var Bundle.textArg : String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentNewPostBinding = FragmentNewPostBinding.inflate(layoutInflater)
        val post = arguments?.postArg
        val text = arguments?.textArg

        binding.edit.setText(when {
            post != null -> post.content
            text != null -> text
            else         -> ""
        })

        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            viewModel.changeContent(binding.edit.text.toString())
            viewModel.save()
            AndroidUtils.hideKeyboard(requireView())

            findNavController().navigateUp()
        }
        return binding.root
    }
}
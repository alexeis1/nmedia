package ru.netology.nmedia.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.text.trimmedLength
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
    private val newPostDraft   = "newPostCache"
    private val draftKey = "lostContent"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentNewPostBinding = FragmentNewPostBinding.inflate(layoutInflater)
        val post = arguments?.postArg
        val text = arguments?.textArg

        val draftSettings = requireContext().getSharedPreferences(newPostDraft, Context.MODE_PRIVATE)
        val draftContent = draftSettings.getString(draftKey, "")

        binding.edit.setText(
            when {
                post != null -> post.content
                text != null -> text
                else         -> draftContent
            }
        )

        //очищаем черновик, чтоб 2 раза не показывать его текст
        if (draftContent?.isNotEmpty() == true) {
            draftSettings.edit().apply {
                putString(draftKey, "")
                apply()
            }
        }
        //Черновик сохраняется только для нового поста
        if (post == null && text == null)
        {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                if (binding.edit.text.trimmedLength() > 0) {
                    draftSettings.edit().apply{
                        putString(draftKey, binding.edit.text.toString())
                        apply()
                    }
                }
                AndroidUtils.hideKeyboard(requireView())
                findNavController().navigateUp()
            }
        }

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
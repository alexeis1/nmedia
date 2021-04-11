package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.databinding.CardPostBinding
import ru.netology.databinding.FragmentCardBinding
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.PostArg
import ru.netology.nmedia.viewmodel.PostViewModel

class CardFragment : Fragment() {
    companion object{
        var Bundle.postArg : Post? by PostArg
    }

    private val viewModel: PostViewModel by viewModels(
            ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentCardBinding.inflate(inflater,
                container,
                false)

        context?.let {
            val handlers = PostViewHandlers(binding.cardPost, it, viewModel)
            val post = arguments?.postArg
            post?.let {
                handlers.bind(post)
                binding.cardPost.cardPost.setOnClickListener(null)
            }

            viewModel.data.observe(viewLifecycleOwner, { posts->
                post?.let{
                    val newPost = posts.find { newPost -> newPost.id == post.id }
                    if (newPost != null){
                        handlers.bind(newPost)
                        binding.cardPost.cardPost.setOnClickListener(null)
                    } else {
                        findNavController().popBackStack()
                    }
                }
            })
        }

        return binding.root
    }
}

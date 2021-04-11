package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.R
import ru.netology.databinding.FragmentFeedBinding
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.PostArg
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {
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
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = PostAdapter(viewModel)
        binding.list.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner, { posts->
            adapter.submitList(posts)
        })

        viewModel.edited.observe(viewLifecycleOwner, { post->
            if (post.id == 0L){
                return@observe
            }

            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment, Bundle().apply {
                postArg = post
            })
        })

        binding.fab.setOnClickListener(){
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        return binding.root
    }

}

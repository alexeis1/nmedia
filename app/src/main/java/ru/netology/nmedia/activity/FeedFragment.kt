package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.AuthViewModel
import ru.netology.nmedia.viewmodel.PostViewModel

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    private val authViewModel: AuthViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        var likeFun : (post: Post)->Unit = { post: Post-> viewModel.likeById(post.id)}

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                likeFun(post)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }
        }, authViewModel)
        binding.list.adapter = adapter
        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            binding.progress.isVisible = state.loading
            binding.swiperefresh.isRefreshing = state.refreshing
            if (state.error) {
                Snackbar.make(binding.root, R.string.error_loading, Snackbar.LENGTH_LONG)
                    .setAction(R.string.retry_loading) { viewModel.loadPosts() }
                    .show()
            }
        }
        var scrollToTop = false
        viewModel.data.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.posts) {
                if (scrollToTop) {
                    binding.list.scrollToPosition(0)
                }
            }
            binding.emptyText.isVisible = state.empty
        }

        viewModel.newerCount.observe(viewLifecycleOwner) { state ->
            if (state != 0){
                binding.newPosts.text = getString(R.string.new_posts, state)
                binding.newPosts.visibility = View.VISIBLE
            } else {
                binding.newPosts.visibility = View.GONE
            }

            println(state)
        }

        viewModel.getUnreadCount().observe(viewLifecycleOwner){
            if (it == 0L){
                binding.newPosts.visibility = View.GONE
            }
        }

        binding.newPosts.setOnClickListener {
            viewModel.markReadAllUnReadPosts()
            scrollToTop = true
        }

        binding.swiperefresh.setOnRefreshListener {
            viewModel.refreshPosts()
        }

        authViewModel.data.observe(viewLifecycleOwner){
            if (it.id != 0L) {
                binding.fab.setOnClickListener {
                    findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
                }
                likeFun = { post: Post-> viewModel.likeById(post.id)}
            } else {
                 val queryRegistration = {_:View?->
                    Snackbar.make(
                        binding.fab,
                        getString(R.string.authentication_required),
                        Snackbar.LENGTH_SHORT
                    ).setAction(getString(R.string.login),) {
                        findNavController().navigate(R.id.action_feedFragment_to_signInFragment)
                    }.show()
                }
                binding.fab.setOnClickListener(queryRegistration)
                likeFun = {_->queryRegistration(null)}
           }
        }

        return binding.root
    }
}

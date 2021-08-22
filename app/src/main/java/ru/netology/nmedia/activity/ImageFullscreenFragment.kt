package ru.netology.nmedia.activity

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.databinding.FragmentImageFullscreenBinding
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.view.load
import ru.netology.nmedia.viewmodel.PostViewModel

@AndroidEntryPoint
class ImageFullscreenFragment : Fragment() {
    companion object {
        var Bundle.urlArg: String? by StringArg
    }

    @ExperimentalCoroutinesApi
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment,
    )

    private var fragmentBinding: FragmentImageFullscreenBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentImageFullscreenBinding.inflate(
            inflater,
            container,
            false
        )
        fragmentBinding = binding
        (activity as AppCompatActivity).supportActionBar?.hide()
        activity?.window?.let{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
               // it.setDecorFitsSystemWindows(false)
                it.insetsController?.let {controller->
                    with(controller) {
                        hide(WindowInsets.Type.systemBars())
                        systemBarsBehavior =
                            WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    }

                }
            }
        }

        arguments?.urlArg?.let { url->
                with(binding){
                    imageView.load("${BuildConfig.BASE_URL}/media/${url}")
                }
        }

        return binding.root
    }

    override fun onDestroyView() {
        activity?.window?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
              //  it.setDecorFitsSystemWindows(true)
                it.insetsController?.let {controller->
                    with(controller) {
                        show(WindowInsets.Type.systemBars())
                        systemBarsBehavior =
                            WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    }
                }
            }
        }
        (activity as AppCompatActivity).supportActionBar?.show()
        fragmentBinding = null
        super.onDestroyView()
    }
}
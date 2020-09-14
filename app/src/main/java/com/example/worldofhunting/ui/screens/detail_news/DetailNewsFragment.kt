package com.example.worldofhunting.ui.screens.detail_news

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.worldofhunting.R
import com.example.worldofhunting.data.models.TopHeadlines.Article
import com.example.worldofhunting.databinding.FragmentDetailNewsBinding
import com.example.worldofhunting.other.InsetSide
import com.example.worldofhunting.other.addSystemWindowInsetToMargin
import com.example.worldofhunting.other.setHeightOrWidthAsSystemWindowInset


class DetailNewsFragment : Fragment() {
    private var _binding: FragmentDetailNewsBinding? = null
    private val binding: FragmentDetailNewsBinding
        get() = _binding!!


    private lateinit var viewModel: DetailNewsViewModel


    companion object {
        private const val ARG_NEWS = "ARG_NEWS"
        fun newInstance(news: Article): DetailNewsFragment {
            return DetailNewsFragment().apply {
                arguments = bundleOf(ARG_NEWS to news)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val news = arguments?.getParcelable<Article>(ARG_NEWS)!!
        val vmFactory: DetailNewsViewModel.Factory = DetailNewsViewModel.Factory(news)
        viewModel = ViewModelProvider(this, vmFactory)[DetailNewsViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupInsets()
        setupListeners()
    }


    private fun setupListeners() {
        binding.ivNewsImage.setOnClickListener { openUrl(viewModel.news.value.url) }
        binding.tvContent.setOnClickListener { openUrl(viewModel.news.value.url) }
        binding.fab.setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL")
            i.putExtra(Intent.EXTRA_TEXT, viewModel.news.value.url)
            startActivity(Intent.createChooser(i, "Share URL"))
        }
    }

    private fun setupInsets() {
        binding.viewStatus.setHeightOrWidthAsSystemWindowInset(InsetSide.TOP)
        binding.fab.addSystemWindowInsetToMargin(bottom = true)
    }

    private fun observeViewModel() {
        binding.ivNewsImage.transitionName = viewModel.news.value.title
        viewModel.news.observe(viewLifecycleOwner) { news ->
            Glide.with(this).load(news.urlToImage?: R.drawable.ic_place_holder_image).addListener(glideCallback)
                .into(binding.ivNewsImage)
            binding.tvContent.text = news.description
            binding.tvTitle.text = news.title
        }
    }

    private fun openUrl(url: String) {
        val address: Uri = Uri.parse(url)
        val openLinkIntent = Intent(Intent.ACTION_VIEW, address)
        if (openLinkIntent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(openLinkIntent)
        } else {
            Log.d("Intent", "Не получается обработать намерение!")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val glideCallback = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            startPostponedEnterTransition()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            startPostponedEnterTransition()
            return false
        }

    }

}

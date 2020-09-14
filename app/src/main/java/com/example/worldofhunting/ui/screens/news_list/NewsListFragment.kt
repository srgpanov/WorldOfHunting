package com.example.worldofhunting.ui.screens.news_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.worldofhunting.App
import com.example.worldofhunting.R
import com.example.worldofhunting.data.models.TopHeadlines.Article
import com.example.worldofhunting.databinding.FragmentNewsListBinding
import com.example.worldofhunting.other.*
import com.example.worldofhunting.ui.screens.detail_news.DetailNewsFragment
import com.example.worldofhunting.ui.screens.sources.SourcesFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class NewsListFragment : Fragment() {
    private var _binding: FragmentNewsListBinding? = null
    private val binding: FragmentNewsListBinding
        get() = _binding!!

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    lateinit var viewModel: NewsListViewModel

    private val simpleAdapter: NewsListAdapter by lazy { NewsListAdapter() }
    private val pagingAdapter: PagedNewsAdapter by lazy { PagedNewsAdapter(PagedNewsAdapter.Comparator) }
    private val clickListener = { news: Article->
        val fragment = DetailNewsFragment.newInstance(news)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_main, fragment)
            .addToBackStack(null)
            .commit()
        Unit
    }

    var newsJob: Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.injectNewsListFragment(this)
        viewModel = ViewModelProvider(this, vmFactory)[NewsListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInsets()
        setupViews()
        observeViewModel()
    }



    private fun setupInsets() {
        binding.rv.addSystemWindowInsetToPadding(bottom = true, top = true)
        binding.fab.addSystemWindowInsetToMargin(bottom = true)
        binding.viewStatus.setHeightOrWidthAsSystemWindowInset(InsetSide.TOP)
    }

    private fun setupViews() {
        setupRecyclerView()
        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.fab.setOnClickListener {
            val fragment = SourcesFragment()
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_main, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setupRecyclerView() {
        val adapter = if (requireContext().isInternetConnected) pagingAdapter else simpleAdapter
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(Divider(requireContext()))
        pagingAdapter.clickListener = clickListener
        simpleAdapter.clickListener = clickListener
        viewLifecycleOwner.lifecycleScope.launch {
            pagingAdapter.loadStateFlow.collectLatest { loadStates ->
                loadStates.refresh is LoadState.Error

                Log.d("NewsListFragment", "setupRecyclerView: ${loadStates.refresh}")
            }
        }
    }

    private fun showError(s: String) {
        requireContext().showToast(s)
    }

    private fun setupNewsFlow() {
        newsJob?.cancel()
        newsJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getNewsFlow().collectLatest { pagingData ->
                Log.d("NewsListFragment", "setupRecyclerView: $pagingData")
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.selectedPlacesChanged.observe(viewLifecycleOwner) {
            setupNewsFlow()
        }
        viewModel.newsList.observe(viewLifecycleOwner) { news ->
            simpleAdapter.setItems(news)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

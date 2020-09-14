package com.example.worldofhunting.ui.screens.sources

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.worldofhunting.App

import com.example.worldofhunting.databinding.FragmentSourcesBinding
import com.example.worldofhunting.other.InsetSide
import com.example.worldofhunting.other.OnBackPressedListener
import com.example.worldofhunting.other.addSystemWindowInsetToPadding
import com.example.worldofhunting.other.setHeightOrWidthAsSystemWindowInset
import com.example.worldofhunting.ui.screens.news_list.Divider
import com.example.worldofhunting.ui.screens.news_list.NewsListViewModel
import java.lang.StringBuilder
import javax.inject.Inject

class SourcesFragment : Fragment() {
    private var _binding: FragmentSourcesBinding? = null
    private val binding: FragmentSourcesBinding
        get() = _binding!!

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SourcesViewModel
    private val adapter by lazy { SourcesAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.injectSourcesFragment(this)
        viewModel = ViewModelProvider(this, vmFactory)[SourcesViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSourcesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInsets()
        setupViews()
        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.sources.observe(viewLifecycleOwner) { sources ->
            adapter.setItems(sources)
        }
    }

    private fun setupViews() {
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(Divider(requireContext()))
        adapter.checkedListener = { source ->
            viewModel.updateSource(source)
        }
    }

    private fun setupInsets() {
        binding.rv.addSystemWindowInsetToPadding(bottom = true,top = true)
        binding.viewStatus.setHeightOrWidthAsSystemWindowInset(InsetSide.TOP)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}

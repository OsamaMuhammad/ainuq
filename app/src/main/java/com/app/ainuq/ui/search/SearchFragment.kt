package com.app.ainuq.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.ainuq.databinding.FragmentSearchBinding
import com.app.ainuq.ui.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding

    private lateinit var productAdapter: SearchProductAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProducts("")
        setupViews()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.filteredProducts.observe(viewLifecycleOwner) {
            it?.let {
                productAdapter.submitList(it)
            } ?: run {

            }
        }
    }

    private fun setupViews() {

        productAdapter = SearchProductAdapter(
            context = requireContext(),
            onTryClick = {
                findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToAiNuqImageFragment(it))
            },
            onClick = {
                findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToProductDetailFragment(it))
            },
            onFavouriteClick = {

            }
        )

        binding.rvProducts.apply {
            adapter = productAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        binding.layoutSearch.etSearch.doOnTextChanged { text, start, count, after ->
            viewModel.getProducts(text?.toString() ?: "")
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment()
    }
}
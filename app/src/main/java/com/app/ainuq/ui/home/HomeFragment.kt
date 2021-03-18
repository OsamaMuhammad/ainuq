package com.app.ainuq.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.ainuq.databinding.FragmentHomeBinding
import com.app.ainuq.ui.main.MainFragmentDirections
import com.app.ainuq.utils.Result
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: CategoriesAdapter
    private lateinit var popularAdapter: HomeProductAdapter
    private lateinit var youMayLikeAdapter: HomeProductAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.homeData.observe(viewLifecycleOwner) {
            it?.let {
                youMayLikeAdapter.submitList(it.listYouMayLike)
                popularAdapter.submitList(it.listYouMayLike)
                categoryAdapter.submitList(it.listCategory)
            } ?: run {
                youMayLikeAdapter.submitList(listOf())
                popularAdapter.submitList(listOf())
                categoryAdapter.submitList(listOf())
            }
        }

        viewModel.eventState.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {

                }
                is Result.Success -> {

                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupViews() {

        binding.layoutCategories.tvSuggestionName.text = "Top Categories"
        binding.layoutCategories.tvSeeAll.visibility = View.GONE
        binding.layoutSuggestionPopularFrames.tvSuggestionName.text = "Popular Frames"
        binding.layoutSuggestionYouMayLike.tvSuggestionName.text = "Frames You May Like"
        popularAdapter = HomeProductAdapter(
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

        binding.rvPopular.apply {
            adapter = popularAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }


        youMayLikeAdapter = HomeProductAdapter(
            context = requireContext(),
            onTryClick = {

            },
            onClick = {
                findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToProductDetailFragment(it))
            },
            onFavouriteClick = {

            }
        )

        binding.rvYouMayLike.apply {
            adapter = youMayLikeAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }


        categoryAdapter = CategoriesAdapter(
            context = requireContext(),
            onClick = {

            }
        )

        binding.rvCategories.apply {
            adapter = categoryAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        binding.layoutSearch.etSearch.isFocusable = false
        binding.layoutSearch.etSearch.isFocusableInTouchMode = false
        binding.layoutSearch.etSearch.isLongClickable = false

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }
}
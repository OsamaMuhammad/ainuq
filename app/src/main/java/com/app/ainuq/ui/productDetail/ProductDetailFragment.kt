package com.app.ainuq.ui.productDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import androidx.viewpager2.widget.ViewPager2
import com.app.ainuq.databinding.FragmentProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private val viewModel: ProductDetailViewModel by viewModels()
    private lateinit var binding: FragmentProductDetailBinding
    private val args: ProductDetailFragmentArgs by navArgs()

    private lateinit var colorAdapter: ColorsAdapter
    private lateinit var imageSliderAdapter: ImageSliderAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        viewModel.setProduct(args.productDetail)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.productDetail.observe(viewLifecycleOwner) {
            it?.let {
                it.apply {
                    binding.tvFrameName.text = name
                    binding.tvGender.text = gender
                    binding.tvWeight.text = weight
                    binding.tvThickness.text = thickness
                    binding.tvTotalPrice.text = price
                    binding.tvRating.text = rating
                    binding.tvMaterial.text = material

                    colorAdapter.submitList(colors)

                    binding.pageIndicator.count = images.size
                    imageSliderAdapter.list = images
                    imageSliderAdapter.notifyDataSetChanged()

                }
            } ?: run {

            }
        }
    }

    private fun setupViews() {

        colorAdapter = ColorsAdapter(
            context = requireContext(),
            onClick = {

            }
        )

        binding.rvColors.apply {
            adapter = colorAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }


        imageSliderAdapter = ImageSliderAdapter(
            context = requireContext(),
            list = listOf()
        )

        binding.viewPagerImages.apply {
            adapter = imageSliderAdapter
        }

        binding.viewPagerImages.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.pageIndicator.setSelected(position)
            }
        })

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProductDetailFragment()
    }
}
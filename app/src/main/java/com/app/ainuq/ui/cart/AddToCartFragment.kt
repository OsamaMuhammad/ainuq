package com.app.ainuq.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.app.ainuq.databinding.FragmentAddToCartBinding
import com.app.ainuq.ui.productDetail.ColorsAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddToCartFragment : Fragment() {

    private val viewModel: AddToCartViewModel by viewModels()

    private lateinit var binding: FragmentAddToCartBinding
    private val args: AddToCartFragmentArgs by navArgs()

    private lateinit var colorAdapter: ColorsAdapter
    private lateinit var glassTypeAdapter: GlassTypeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setProduct(args.productDetail)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddToCartBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()

    }



    private fun setupObservers() {
        viewModel.productDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.layoutTop.imgProduct.load(it.images.firstOrNull() ?: ""){
                    crossfade(true)
                }
                binding.layoutTop.tvFrameName.text = it.name
                binding.layoutTop.tvTotalPrice.text = it.price

                colorAdapter.submitList(it.colors)
                glassTypeAdapter.submitList(it.glasses)
            }
        })

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

        glassTypeAdapter = GlassTypeAdapter(
            context = requireContext(),
            onClick = {

            }
        )

        binding.rvGlass.apply {
            adapter = glassTypeAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AddToCartFragment()
    }
}
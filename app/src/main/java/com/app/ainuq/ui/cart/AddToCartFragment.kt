package com.app.ainuq.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.app.ainuq.databinding.FragmentAddToCartBinding
import com.app.ainuq.ui.prescription.PrescriptionAdapter
import com.app.ainuq.ui.productDetail.ColorsAdapter
import com.app.ainuq.utils.Result
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddToCartFragment : Fragment() {

    private val viewModel: AddToCartViewModel by viewModels()

    private lateinit var binding: FragmentAddToCartBinding
    private val args: AddToCartFragmentArgs by navArgs()

    private lateinit var colorAdapter: ColorsAdapter
    private lateinit var glassTypeAdapter: GlassTypeAdapter
    private lateinit var prescriptAdapter: PrescriptionAdapter

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
                binding.layoutTop.imgProduct.load(it.images.firstOrNull() ?: "") {
                    crossfade(true)
                }
                binding.layoutTop.tvFrameName.text = it.name
                binding.layoutTop.tvFFramePrice.text = "Rs. ${it.price}"

                colorAdapter.submitList(it.colors)
                glassTypeAdapter.submitList(it.glasses)

                var totalPrice = 0.0
                totalPrice += it.price
                totalPrice += it.glasses.firstOrNull { it.isSelected }?.price ?: 0.0
                binding.tvTotalPrice.text = "Rs. ${totalPrice}"
            }
        })

        viewModel.prescriptions.observe(viewLifecycleOwner) {
            prescriptAdapter.submitList(it)
        }

        viewModel.addToCartEvent.observe(viewLifecycleOwner, {
            when (it) {
                is Result.Loading -> {

                }
                is Result.Success -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is Result.Error -> {

                }
            }
        })

        viewModel.messageEvent.observe(viewLifecycleOwner, Observer {
            it?.consume()?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setupViews() {
        colorAdapter = ColorsAdapter(
            context = requireContext(),
            onClick = {
                viewModel.selectColor(it)
            }
        )

        binding.rvColors.apply {
            adapter = colorAdapter
            itemAnimator = null
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        glassTypeAdapter = GlassTypeAdapter(
            context = requireContext(),
            onClick = {
                viewModel.selectGlassType(it)
            }
        )

        binding.rvGlass.apply {
            adapter = glassTypeAdapter
            itemAnimator = null
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }


        prescriptAdapter = PrescriptionAdapter(
            context = requireContext(),
            onClick = {
                viewModel.selectPrescription(it)
            }
        )

        binding.rvPrescription.apply {
            adapter = prescriptAdapter
            itemAnimator = null
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnAddToCart.setOnClickListener {
            viewModel.addToCart()
        }

        binding.btnAddPrescription.setOnClickListener {
            findNavController().navigate(AddToCartFragmentDirections.actionAddToCartFragmentToAddPrescriptionFragment())
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AddToCartFragment()
    }
}
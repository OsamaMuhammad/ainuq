package com.app.ainuq.ui.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.ainuq.R
import com.app.ainuq.databinding.FragmentCartBinding
import com.app.ainuq.databinding.FragmentOrderDetailBinding
import com.app.ainuq.ui.cart.CartAdapter
import com.app.ainuq.ui.cart.CartFragmentDirections
import com.app.ainuq.ui.cart.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailFragment : Fragment() {

    private val viewModel: OrderDetailViewModel by viewModels()
    private lateinit var binding: FragmentOrderDetailBinding

    val args: OrderDetailFragmentArgs by navArgs()
    private lateinit var cartAdapter: CartAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setProducts(args.orderDetail.items)
        setupViews()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.products.observe(viewLifecycleOwner) {
            it?.let {
                cartAdapter.submitList(it)

                var totalPrice = 0.0
                it.forEach {
                    totalPrice += it.price
                    totalPrice += it.glasses.firstOrNull { it.isSelected }?.price ?: 0.0
                }
                binding.tvTotalPrice.text = "Rs. ${totalPrice}"
            }

        }
    }

    private fun setupViews() {

        binding.tvDeliveryAddress.text = args.orderDetail.address


        cartAdapter = CartAdapter(
            context = requireContext(),
            onEditClick = {
            },
            onRemoveClick = {
            },
            isFromOrderDetail = true
        )

        binding.rvList.apply {
            adapter = cartAdapter
            itemAnimator = null
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

}
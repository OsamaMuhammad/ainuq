package com.app.ainuq.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.ainuq.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private val viewModel: CartViewModel by viewModels()

    private lateinit var binding: FragmentCartBinding
//    private val args: Cartksdjdsf by navArgs()

    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.cartItems.observe(viewLifecycleOwner) {
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
        cartAdapter = CartAdapter(
            context = requireContext(),
            onEditClick = {

            },
            onRemoveClick = {

            }
        )

        binding.rvCart.apply {
            adapter = cartAdapter
            itemAnimator = null
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnCheckout.setOnClickListener {

        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CartFragment()
    }
}
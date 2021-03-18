package com.app.ainuq.ui.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.ainuq.databinding.FragmentCheckoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutFragment : Fragment() {

    private val viewModel: CheckoutViewModel by viewModels()
    private lateinit var binding: FragmentCheckoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutBinding.inflate(layoutInflater)
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

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CheckoutFragment()
    }
}

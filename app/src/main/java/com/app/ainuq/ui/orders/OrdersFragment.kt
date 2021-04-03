package com.app.ainuq.ui.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.ainuq.R
import com.app.ainuq.databinding.FragmentOrdersBinding
import com.app.ainuq.databinding.FragmentProductDetailBinding
import com.app.ainuq.ui.main.MainFragmentDirections
import com.app.ainuq.ui.productDetail.ProductDetailViewModel
import com.app.ainuq.ui.search.SearchProductAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private val viewModel: OrderViewModel by viewModels()
    private lateinit var binding: FragmentOrdersBinding

    private lateinit var orderAdapter: OrdersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrdersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        orderAdapter = OrdersAdapter(
            context = requireContext(),
            onClick= {

            },
        )

        binding.rvOrders.apply {
            adapter = orderAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupObservers() {
        viewModel.orderItems.observe(viewLifecycleOwner, Observer {
            it?.let {
                orderAdapter.submitList(it)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            OrdersFragment()
    }
}
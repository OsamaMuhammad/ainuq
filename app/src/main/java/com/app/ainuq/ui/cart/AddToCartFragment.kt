package com.app.ainuq.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
        viewModel.isEdit = args.isEdit
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

        if (viewModel.isEdit) {
            viewModel.productDetail.value?.prescription?.let {
                viewModel.selectPrescription(it)
            }
        }
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
            it?.consume()?.let {
                showAddToCartDialog()
            }
        })

        viewModel.updateItemEvent.observe(viewLifecycleOwner, {
            it?.consume()?.let {
                findNavController().popBackStack()
            }
        })

        viewModel.messageEvent.observe(viewLifecycleOwner, Observer {
            it?.consume()?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun showAddToCartDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("Item added to Cart successfully")
            .setPositiveButton("View Cart") { dialog, _ ->
                dialog.dismiss()
                findNavController().navigate(AddToCartFragmentDirections.actionAddToCartFragmentToCartFragmentPop())
            }
            .setNegativeButton("Dismiss") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    private fun setupViews() {

        if (viewModel.isEdit) {
            binding.tvAppbarTitle.text = "Edit Item"
            binding.btnAddToCart.text = "Update"
        }


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
            if (viewModel.isEdit) {
                viewModel.updateItem()
            } else {
                viewModel.addToCart()
            }
        }

        binding.imgCart.setOnClickListener {
            findNavController().navigate(AddToCartFragmentDirections.actionAddToCartFragmentToCartFragment())
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
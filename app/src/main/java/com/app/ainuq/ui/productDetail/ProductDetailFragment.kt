package com.app.ainuq.ui.productDetail

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
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
import androidx.viewpager2.widget.ViewPager2
import com.app.ainuq.databinding.FragmentProductDetailBinding
import com.app.ainuq.utils.FileHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private val viewModel: ProductDetailViewModel by viewModels()
    private lateinit var binding: FragmentProductDetailBinding
    private val args: ProductDetailFragmentArgs by navArgs()

    private lateinit var colorAdapter: ColorsAdapter
    private lateinit var imageSliderAdapter: ImageSliderAdapter

    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_IMAGE_CHOOSE = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        viewModel.productDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.apply {
                    binding.tvFrameName.text = name
                    binding.tvGender.text = gender
                    binding.tvWeight.text = weight
                    binding.tvThickness.text = thickness
                    binding.tvTotalPrice.text = "Rs. ${price}"
                    binding.tvRating.text = rating
                    binding.tvMaterial.text = material

                    colorAdapter.submitList(colors)

                    binding.pageIndicator.count = images.size
                    imageSliderAdapter.list = images
                    imageSliderAdapter.notifyDataSetChanged()

                }
            } ?: run {

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

        binding.btnTryNow.setOnClickListener {
//            selectImage(requireContext())
            navigateToAiNuqImageFragment()
        }

        binding.btnAddToCart.setOnClickListener {
            viewModel.productDetail.value?.let {
                findNavController()
                    .navigate(
                        ProductDetailFragmentDirections.actionProductDetailFragmentToAddToCartFragment(
                            it
                        )
                    )
            }
        }

        binding.imgCart.setOnClickListener {
            findNavController().navigate(ProductDetailFragmentDirections.actionProductDetailFragmentToCartFragment())
        }
    }

    private fun selectImage(context: Context) {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Take Photo" -> {
                    val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE)
                }
                options[item] == "Choose from Gallery" -> {
                    val pickPhoto =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(pickPhoto, REQUEST_IMAGE_CHOOSE)
                }
                options[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_CANCELED) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> if (resultCode == RESULT_OK && data != null) {
                    val selectedImage = data.extras?.get("data") as Bitmap?
//                    navigateToAiNuqImageFragment(selectedImage)
                }
                REQUEST_IMAGE_CHOOSE -> if (resultCode == RESULT_OK && data != null) {

                    val uri = data.data
                    val bitmap: Bitmap? = FileHelper.getBitmapFromUri(uri, requireContext())
//                    navigateToAiNuqImageFragment(bitmap)

                }
            }
        }
    }

    private fun navigateToAiNuqImageFragment() {
        viewModel.productDetail.value?.let { productDetail ->
            findNavController()
                .navigate(
                    ProductDetailFragmentDirections
                        .actionProductDetailFragmentToAiNuqImageFragment(productDetail)
                )

        } ?: run {
            Toast.makeText(requireContext(), "Product Detail not found", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProductDetailFragment()
    }
}
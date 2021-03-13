package com.app.ainuq.ui.ainuqImage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.app.ainuq.databinding.FragmentAiNuqImageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AiNuqImageFragment : Fragment() {

    private val viewModel: AiNuqImageViewModel by viewModels()

    private lateinit var binding: FragmentAiNuqImageBinding
    private val args: AiNuqImageFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setProduct(args.productDetail)
        viewModel.setUnprocessedBitmap(args.image)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAiNuqImageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.productDetail.observe(viewLifecycleOwner){

        }

        viewModel.unprocessedBitmap.observe(viewLifecycleOwner){
            it?.let {
                binding.imageView.setImageBitmap(it)
            }
        }
    }

    private fun setupViews() {

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AiNuqImageFragment()
    }
}
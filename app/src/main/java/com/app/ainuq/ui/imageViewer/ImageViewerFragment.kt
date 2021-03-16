package com.app.ainuq.ui.imageViewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.app.ainuq.databinding.FragmentImageViewerBinding


class ImageViewerFragment : Fragment() {

    private lateinit var binding: FragmentImageViewerBinding

    private val args: ImageViewerFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageViewerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView.load(args.bitmap){
            crossfade(true)
        }
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ImageViewerFragment()
    }
}
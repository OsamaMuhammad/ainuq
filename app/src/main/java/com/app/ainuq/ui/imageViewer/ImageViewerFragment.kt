package com.app.ainuq.ui.imageViewer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.app.ainuq.databinding.FragmentImageViewerBinding
import com.app.ainuq.utils.ImageStorageManager


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

        val uri = ImageStorageManager.saveImage(
            args.bitmap,
            requireActivity(),
            requireContext(),
            "AiNuq"
        )

        binding.imageView.load(args.bitmap){
            crossfade(true)
        }
        
        binding.btnShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/jpeg"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(intent, "Share Image"))
        }
        
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.clear()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ImageViewerFragment()
    }
}
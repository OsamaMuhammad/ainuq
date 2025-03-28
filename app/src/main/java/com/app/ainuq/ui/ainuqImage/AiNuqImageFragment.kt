package com.app.ainuq.ui.ainuqImage

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.PixelCopy
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.ainuq.R
import com.app.ainuq.databinding.FragmentAiNuqImageBinding
import com.app.ainuq.utils.ImageStorageManager
import com.google.ar.core.AugmentedFace
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Texture
import com.google.ar.sceneform.ux.AugmentedFaceNode
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.HashMap

@AndroidEntryPoint
class AiNuqImageFragment : Fragment() {

    companion object {
        const val MIN_OPENGL_VERSION = 3.0
    }

    lateinit var arFragment: FaceArFragment
    private var faceMeshTexture: Texture? = null
    private var glasses: ArrayList<ModelRenderable> = ArrayList()
    private var faceRegionsRenderable: ModelRenderable? = null

    var faceNodeMap = HashMap<AugmentedFace, AugmentedFaceNode>()
    private var index: Int = 0
    private var changeModel: Boolean = false

    private val viewModel: AiNuqImageViewModel by viewModels()

    private lateinit var binding: FragmentAiNuqImageBinding
    private val args: AiNuqImageFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setProduct(args.productDetail)
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



        arFragment = FaceArFragment(args.productDetail.modelUrl)

        arFragment.showHideLoading = { show ->
            if (show) {
                binding.layoutLoading.visibility = View.VISIBLE
            } else {
                binding.layoutLoading.visibility = View.GONE
            }
        }

        childFragmentManager.commit {
            replace(R.id.fragment_container, arFragment)
        }
        Texture.builder()
            .setSource(requireContext(), R.drawable.bell)
            .build()
            .thenAccept { texture -> faceMeshTexture = texture }

        ModelRenderable.builder()
            .setSource(requireContext(), Uri.parse("yellow_sunglasses.sfb"))
            .build()
            .thenAccept { modelRenderable ->
                glasses.add(modelRenderable)
                faceRegionsRenderable = modelRenderable
                modelRenderable.isShadowCaster = false
                modelRenderable.isShadowReceiver = false
            }

        ModelRenderable.builder()
            .setSource(requireContext(), Uri.parse("sunglasses.sfb"))
            .build()
            .thenAccept { modelRenderable ->
                glasses.add(modelRenderable)
                modelRenderable.isShadowCaster = false
                modelRenderable.isShadowReceiver = false
            }

//        val sceneView = arFragment.arSceneView
//        sceneView.cameraStreamRenderPriority = Renderable.RENDER_PRIORITY_FIRST
//        val scene = sceneView.scene
//
//        scene.addOnUpdateListener {
//            if (faceRegionsRenderable != null) {
//                sceneView.session
//                    ?.getAllTrackables(AugmentedFace::class.java)?.let {
//                        for (f in it) {
//                            if (!faceNodeMap.containsKey(f)) {
//                                val faceNode = AugmentedFaceNode(f)
//                                faceNode.setParent(scene)
//                                faceNode.faceRegionsRenderable = faceRegionsRenderable
//                                faceNodeMap.put(f, faceNode)
//                            } else if (changeModel) {
//                                faceNodeMap.getValue(f).faceRegionsRenderable = faceRegionsRenderable
//                            }
//                        }
//                        changeModel = false
//                        // Remove any AugmentedFaceNodes associated with an AugmentedFace that stopped tracking.
//                        val iter = faceNodeMap.entries.iterator()
//                        while (iter.hasNext()) {
//                            val entry = iter.next()
//                            val face = entry.key
//                            if (face.trackingState == TrackingState.STOPPED) {
//                                val faceNode = entry.value
//                                faceNode.setParent(null)
//                                iter.remove()
//                            }
//                        }
//                    }
//            }
//        }


        setupViews()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.productDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.apply {
                    binding.tvTotalPrice.text = "Rs. ${price}"
                }
            } ?: run {

            }
        })
    }

    private fun setupViews() {
        binding.btnAddToCart.setOnClickListener {

        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.viewCapture.setOnClickListener {
            val bitmap = Bitmap.createBitmap(
                arFragment.arSceneView.width,
                arFragment.arSceneView.height,
                Bitmap.Config.ARGB_8888
            )
            PixelCopy.request(arFragment.arSceneView, bitmap, { copyResult ->
                if (copyResult == PixelCopy.SUCCESS) {
                    ImageStorageManager.getResizedBitmap(bitmap,1024)?.let { rezisedBitmap ->
                        findNavController().navigate(
                            AiNuqImageFragmentDirections.actionAiNuqImageFragmentToImageViewerFragment(rezisedBitmap)
                        )
                    }
                } else {

                }
            }, Handler())
        }

        binding.btnAddToCart.setOnClickListener {
            viewModel.productDetail.value?.let {
                findNavController()
                    .navigate(
                        AiNuqImageFragmentDirections.actionAiNuqImageFragmentToAddToCartFragment(
                            it
                        )
                    )
            }
        }
    }

}
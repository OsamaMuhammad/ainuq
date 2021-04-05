package com.app.ainuq.ui.ainuqImage

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.app.ainuq.R
import com.google.ar.core.AugmentedFace
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.Texture
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.AugmentedFaceNode
import timber.log.Timber
import java.util.*

class FaceArFragment(private val arModel:String) : ArFragment() {

    private var faceRegionsRenderable: ModelRenderable? = null

    var showHideLoading: ((Boolean) -> Unit)? = null

    var faceNodeMap = HashMap<AugmentedFace, AugmentedFaceNode>()
    private var changeModel: Boolean = false

    override fun getSessionConfiguration(session: Session?): Config {
        val config = Config(session)
        config.augmentedFaceMode = Config.AugmentedFaceMode.MESH3D
        return config
    }

    override fun getSessionFeatures(): MutableSet<Session.Feature> {
        return EnumSet.of(Session.Feature.FRONT_CAMERA, Session.Feature.SHARED_CAMERA)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val frameLayout =
            super.onCreateView(inflater, container, savedInstanceState) as? FrameLayout
        planeDiscoveryController.hide()
        planeDiscoveryController.setInstructionView(null)
        return frameLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadModel()

        arSceneView.cameraStreamRenderPriority = Renderable.RENDER_PRIORITY_FIRST
        val scene = arSceneView.scene

        scene.addOnUpdateListener {
            if (faceRegionsRenderable != null) {
                arSceneView.session
                    ?.getAllTrackables(AugmentedFace::class.java)?.let {
                        for (f in it) {
                            if (!faceNodeMap.containsKey(f)) {
                                val faceNode = AugmentedFaceNode(f)
                                faceNode.setParent(scene)
                                faceNode.faceRegionsRenderable = faceRegionsRenderable
                                faceNodeMap.put(f, faceNode)
                            } else if (changeModel) {
                                faceNodeMap.getValue(f).faceRegionsRenderable =
                                    faceRegionsRenderable
                            }
                        }
                        changeModel = false
                        // Remove any AugmentedFaceNodes associated with an AugmentedFace that stopped tracking.
                        val iter = faceNodeMap.entries.iterator()
                        while (iter.hasNext()) {
                            val entry = iter.next()
                            val face = entry.key
                            if (face.trackingState == TrackingState.STOPPED) {
                                val faceNode = entry.value
                                faceNode.setParent(null)
                                iter.remove()
                            }
                        }
                    }
            }
        }
    }

    private fun loadModel() {
        try {
            showHideLoading?.invoke(true)
            ModelRenderable.builder()
                .setSource(
                    requireContext(),
                    RenderableSource.builder().setSource(
                        requireActivity(),
                        Uri.parse(arModel),
                        RenderableSource.SourceType.GLTF2
                    )
                        .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                        .build()
                )
                .setRegistryId(arModel)
                .build()
                .thenAccept { renderable: ModelRenderable ->
                    faceRegionsRenderable = renderable
                    renderable.isShadowCaster = false
                    renderable.isShadowReceiver = false
                    Toast.makeText(requireContext(), "Model loaded", Toast.LENGTH_SHORT).show()
                    showHideLoading?.invoke(false)
                }
                .exceptionally { throwable: Throwable? ->
                    Toast.makeText(requireContext(), throwable?.message ?: "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                    Timber.i("cant load")
                    showHideLoading?.invoke(false)
                    null
                }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message ?: "Something went wrong", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
            Timber.i(e)
            showHideLoading?.invoke(false)
        }
    }

}
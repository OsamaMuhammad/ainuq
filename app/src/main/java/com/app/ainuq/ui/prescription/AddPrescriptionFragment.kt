package com.app.ainuq.ui.prescription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.ainuq.databinding.FragmentAddPrescriptionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPrescriptionFragment : Fragment() {

    private val viewModel: AddPrescriptionViewModel by viewModels()

    private lateinit var binding: FragmentAddPrescriptionBinding
//    private val args: AiNuqImageFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPrescriptionBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()
    }

    private fun setupObservers() {

    }

    private fun setupViews() {

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddPrescriptionFragment()
    }
}
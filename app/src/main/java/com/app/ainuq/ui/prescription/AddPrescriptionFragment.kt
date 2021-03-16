package com.app.ainuq.ui.prescription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.ainuq.databinding.FragmentAddPrescriptionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPrescriptionFragment : Fragment() {

    private val viewModel: AddPrescriptionViewModel by viewModels()

    private lateinit var binding: FragmentAddPrescriptionBinding

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

        viewModel.messageEvent.observe(viewLifecycleOwner, Observer {
            it?.consume()?.let {
                Toast.makeText(requireContext(),it, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        })

    }

    private fun setupViews() {

        binding.btnAdd.setOnClickListener {
            if(isValid())
            viewModel.addPrescription(
                PrescriptionItemUiModel(
                    userId = "Osamaid",
                    axisLeft = binding.layputPrescription.axisLeft.text?.toString() ?: "",
                    axisRight = binding.layputPrescription.axisRight.text?.toString() ?: "",
                    baseLeft = binding.layputPrescription.baseLeft.text?.toString() ?: "",
                    baseRight = binding.layputPrescription.baseRight.text?.toString() ?: "",
                    prescriptionId = System.currentTimeMillis().toString(),
                    cylLeft = binding.layputPrescription.cylLeft.text?.toString() ?: "",
                    cylRight = binding.layputPrescription.cylRight.text?.toString() ?: "",
                    isSelected = false,
                    prismRight = binding.layputPrescription.prismRight.text?.toString() ?: "",
                    prismLeft = binding.layputPrescription.prismLeft.text?.toString() ?: "",
                    spLeft = binding.layputPrescription.spLeft.text?.toString() ?: "",
                    spRight = binding.layputPrescription.spRight.text?.toString() ?: "",
                    userName = binding.etName.text?.toString() ?: "",
                    date = binding.etDate.text?.toString() ?: "",
                )
            )
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    private fun isValid(): Boolean {
        if(binding.etName.text?.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(),"Please enter name", Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.etDate.text?.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(),"Please enter date", Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.layputPrescription.spRight.text?.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(),"Please enter SP for right eye", Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.layputPrescription.cylRight.text?.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(),"Please enter CYL for right eye", Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.layputPrescription.axisRight.text?.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(),"Please enter AXIS for right eye", Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.layputPrescription.prismRight.text?.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(),"Please enter PRISM for right eye", Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.layputPrescription.baseRight.text?.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(),"Please enter BASE for right eye", Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.layputPrescription.spLeft.text?.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(),"Please enter SP for left eye", Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.layputPrescription.cylLeft.text?.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(),"Please enter CYL for left eye", Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.layputPrescription.axisLeft.text?.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(),"Please enter AXIS for left eye", Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.layputPrescription.prismLeft.text?.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(),"Please enter PRISM for left eye", Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.layputPrescription.baseLeft.text?.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(),"Please enter BASE for left eye", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddPrescriptionFragment()
    }
}
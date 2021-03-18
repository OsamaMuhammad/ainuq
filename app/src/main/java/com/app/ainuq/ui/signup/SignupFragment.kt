package com.app.ainuq.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.ainuq.databinding.FragmentSignupBinding
import com.app.ainuq.utils.Result
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignupFragment : Fragment() {

    private val viewModel: SignupViewModel by viewModels()
    lateinit var binding: FragmentSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.eventState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Loading -> {
                    toggleEnable(false)
                }
                is Result.Success -> {
                    toggleEnable(true)
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToLoginFragment())
                }
                is Result.Error -> {
                    toggleEnable(true)
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    private fun toggleEnable(enable: Boolean) {
        with(binding) {
            btnSignUp.isEnabled = false
            etFirstName.isEnabled = false
            etLastName.isEnabled = false
            etEmail.isEnabled = false
            etPhone.isEnabled = false
            etPassword.isEnabled = false
            tvGoToLogin.isEnabled = false
            if (enable){
                progressBar.visibility = View.GONE
                btnSignUp.text = "Sign Up"
            }
            else {
                progressBar.visibility = View.VISIBLE
                btnSignUp.text = ""
            }
        }
    }

    private fun setupViews() {
        binding.tvGoToLogin.setOnClickListener {
            findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToLoginFragment())
        }

        binding.btnSignUp.setOnClickListener {
            if (isValid()) {
                with(binding){
                    viewModel.signUp(
                        firstName = etFirstName.text?.toString() ?: "",
                        lastName = etLastName.text?.toString() ?: "",
                        email = etEmail.text?.toString() ?: "",
                        phoneNumber = etPhone.text?.toString() ?: "",
                        password = etPassword.text?.toString() ?: "",
                    )
                }
            }
        }
    }


    private fun isValid(): Boolean {
        if (binding.etFirstName.text?.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Please enter first name", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etLastName.text?.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Please enter last name", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etEmail.text?.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Please enter email", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etPhone.text?.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Please enter phone number", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etPassword.text?.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Please enter password", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignupFragment()
    }
}
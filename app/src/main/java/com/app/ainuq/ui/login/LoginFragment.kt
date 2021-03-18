package com.app.ainuq.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.ainuq.databinding.FragmentLoginBinding
import com.app.ainuq.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {


    private val viewModel: LoginViewModel by viewModels()
    lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.eventState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Loading -> {
                    toggleEnable(false)
                }
                is Result.Success -> {
                    toggleEnable(true)
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(LoginFragmentDirections.loginFragmentToMainFragment())
                }
                is Result.Error -> {
                    toggleEnable(true)
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setUpViews() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(
                email = binding.etEmailPhone.text?.toString() ?: "",
                password = binding.etPassword.text?.toString() ?: ""
            )
            findNavController().navigate(LoginFragmentDirections.loginFragmentToMainFragment())
        }

        binding.tvCreateAccount.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }
    }

    private fun toggleEnable(enable: Boolean) {
        with(binding) {
            btnLogin.isEnabled = false
            etEmailPhone.isEnabled = false
            etPassword.isEnabled = false
            tvCreateAccount.isEnabled = false
            if (enable) {
                progressBar.visibility = View.GONE
                btnLogin.text = "Login"
            } else {
                progressBar.visibility = View.VISIBLE
                btnLogin.text = ""
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LoginFragment()
    }
}
package com.app.ainuq.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.ainuq.databinding.FragmentMainBinding
import com.app.ainuq.ui.login.LoginViewModel

class MainFragment : Fragment() {


    lateinit var binding: FragmentMainBinding
    lateinit var viewPagerAdapter: MainViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
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
        viewPagerAdapter = MainViewPagerAdapter(fragmentActivity = requireActivity())

        binding.viewPager.apply {
            adapter = viewPagerAdapter
            isUserInputEnabled = false
        }

        binding.imgCart.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToCartFragment())
        }

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            MainFragment()
    }
}
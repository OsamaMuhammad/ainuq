package com.app.ainuq.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.app.ainuq.R
import com.app.ainuq.databinding.FragmentMainBinding

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

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    changeImages(position)
                }
            })
        }

        binding.imgCart.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToCartFragment())
        }

        binding.imgHome.setOnClickListener {
            binding.viewPager.currentItem = 0
        }
        binding.imgOrders.setOnClickListener {
            binding.viewPager.currentItem = 1
        }
        binding.imgSearch.setOnClickListener {
            binding.viewPager.currentItem = 2
        }
        binding.imgNotification.setOnClickListener {
//            binding.viewPager.currentItem = 3
        }
        binding.imgProfile.setOnClickListener {
//            binding.viewPager.currentItem = 4
        }
    }


    fun changeImages(index: Int) {
        with(binding) {
            imgHome.setImageResource(R.drawable.home_outlined)
            imgOrders.setImageResource(R.drawable.shopping_bag_outlined)
            imgSearch.setImageResource(R.drawable.search)
            imgNotification.setImageResource(R.drawable.bell_outlined)
            imgProfile.setImageResource(R.drawable.user_outlined)
            when (index) {
                0 -> imgHome.setImageResource(R.drawable.home_blue)
                1 -> imgOrders.setImageResource(R.drawable.shopping_bag_blue)
                2 -> imgSearch.setImageResource(R.drawable.search_blue)
                3 -> imgNotification.setImageResource(R.drawable.bell_blue)
                4 -> imgProfile.setImageResource(R.drawable.user_blue)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            MainFragment()
    }
}
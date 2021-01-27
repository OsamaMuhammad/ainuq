package com.app.ainuq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.ainuq.databinding.ActivityMainBinding
import com.squareup.moshi.Moshi

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
package com.abhinavdev.animeapp.ui.main

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.abhinavdev.animeapp.core.BaseActivity
import com.abhinavdev.animeapp.databinding.ActivityMainBinding
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.ui.home.viewmodel.HomeViewModel
import com.abhinavdev.animeapp.util.ViewModelFactory
import com.abhinavdev.animeapp.util.extension.toast

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        initComponents()
        setAdapters()
        setListeners()
        setObservers()
    }

    private fun initComponents() {
        val factory = ViewModelFactory { HomeViewModel(application) }
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

    }

    private fun setAdapters() {

    }

    private fun setListeners() {

    }

    private fun setObservers() {
        viewModel.animeFullResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.data?.let {

                        }
                        isLoaderVisible(true)
                    }

                    is Resource.Error -> {
                        isLoaderVisible(true)
                        response.message?.let { message -> toast(message) }
                    }

                    is Resource.Loading -> {
                        isLoaderVisible(true)
                    }
                }
            }
        }

    }

    private fun isLoaderVisible(b: Boolean) {

    }
}
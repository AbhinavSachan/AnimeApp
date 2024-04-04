package com.abhinavdev.animeapp.ui.auth

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.lifecycle.ViewModelProvider
import com.abhinavdev.animeapp.core.BaseActivity
import com.abhinavdev.animeapp.databinding.ActivityLoginBinding
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.ui.auth.viewmodel.AuthViewModel
import com.abhinavdev.animeapp.util.ViewModelFactory
import com.abhinavdev.animeapp.util.extension.toast

class LoginActivity : BaseActivity(), OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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
        val factory = ViewModelFactory { AuthViewModel(application) }
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

    }

    private fun setAdapters() {

    }

    private fun setListeners() {

    }

    override fun onClick(v: View?) {

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
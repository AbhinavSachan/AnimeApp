package com.abhinavdev.animeapp.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseActivity
import com.abhinavdev.animeapp.databinding.ActivityMainBinding
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.ui.main.adapters.MainFragmentAdapter
import com.abhinavdev.animeapp.ui.main.viewmodels.MainViewModel
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.appsettings.SettingsPrefs
import com.abhinavdev.animeapp.util.extension.showOrHide
import com.abhinavdev.animeapp.util.extension.toast
import com.abhinavdev.animeapp.util.statusbar.setTransparentForWindow
import com.google.android.material.navigation.NavigationBarView

class MainActivity : BaseActivity(), NavigationBarView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private val rootFragmentTypes: List<MainFragmentAdapter.PageType> = arrayListOf(
        MainFragmentAdapter.PageType.ANIME,
        MainFragmentAdapter.PageType.MANGA,
        MainFragmentAdapter.PageType.MORE,
    )

    private var fragmentAdapter: MainFragmentAdapter? = null
    private var currentPageType: MainFragmentAdapter.PageType = MainFragmentAdapter.PageType.ANIME

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTransparentForWindow()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        checkLoginIntent(intent)
        init()
    }

    private fun parseIntentData(uri: Uri) {
        val code = uri.getQueryParameter("code")
        val receivedState = uri.getQueryParameter("state")
        if (code != null && receivedState == Const.Mal.STATE) {
            viewModel.getAccessToken(code)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        checkLoginIntent(intent)
    }

    private fun checkLoginIntent(intent: Intent?) {
        if (intent?.data?.toString()?.startsWith(Const.Links.APP_DEEP_LINK) == true) {
            intent.data?.let { parseIntentData(it) }
        }
    }

    private fun init() {
        initComponents()
        setAdapters()
        setListeners()
        setObservers()
    }

    private fun initComponents() {

    }

    private fun setAdapters() {
        fragmentAdapter = MainFragmentAdapter(this, rootFragmentTypes)
        binding.viewPager.adapter = fragmentAdapter
        binding.viewPager.isUserInputEnabled = false

    }

    private fun setListeners() {
        binding.bottomNavBar.setOnItemSelectedListener(this)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPageType = rootFragmentTypes[position]
                setUpStatusBarColor(position)
                updateSelectedItemId(position)
            }
        })
    }

    private fun setObservers() {
        viewModel.getAccessTokenResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let {
                            SettingsPrefs.accessToken = it
                        }
                        isLoaderVisible(false)
                    }

                    is Resource.Error -> {
                        isLoaderVisible(false)
                        response.message?.let { message -> toast(message) }
                    }

                    is Resource.Loading -> {
                        isLoaderVisible(true)
                    }
                }
            }
        }
    }

    private fun setUpStatusBarColor(position: Int) {
//        statusBarStartColor = if (position == MainFragmentAdapter.HOME) {
//            val endColor = applyColor(R.color.white)
//            setStatusBarIconsDark(DesignColors.Util.isDarkColor(endColor))
//            animateStatusBarColor(statusBarStartColor, endColor)
//            endColor
//        } else {
//            val endColor = applyColor(R.color.secondary)
//            setStatusBarIconsDark(DesignColors.Util.isDarkColor(endColor))
//            animateStatusBarColor(statusBarStartColor, endColor)
//            endColor
//        }
    }

    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (currentPageType == MainFragmentAdapter.PageType.ANIME) {
            finish()
        } else {
            //if its not home page then back pressing will bring us on home page
            navigateToFragment(MainFragmentAdapter.PageType.ANIME)
        }
    }

    fun isLoaderVisible(b: Boolean) {
        binding.loader.progressOverlay.showOrHide(b)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_anime -> navigateToFragment(MainFragmentAdapter.PageType.ANIME)
            R.id.navigation_manga -> navigateToFragment(MainFragmentAdapter.PageType.MANGA)
            R.id.navigation_more -> navigateToFragment(MainFragmentAdapter.PageType.MORE)
            else -> false
        }
    }

    private fun navigateToFragment(type: MainFragmentAdapter.PageType): Boolean {
        if (currentPageType != type) {
            val position = rootFragmentTypes.indexOf(type)
            binding.viewPager.setCurrentItem(position, false)
        }
        return true
    }

    private fun updateSelectedItemId(position: Int) {

        val selectedItemId = when (rootFragmentTypes[position]) {
            MainFragmentAdapter.PageType.ANIME -> R.id.navigation_anime
            MainFragmentAdapter.PageType.MANGA -> R.id.navigation_manga
            MainFragmentAdapter.PageType.MORE -> R.id.navigation_more
        }

        // Update the bottom navigation bar
        binding.bottomNavBar.selectedItemId = selectedItemId
    }
}
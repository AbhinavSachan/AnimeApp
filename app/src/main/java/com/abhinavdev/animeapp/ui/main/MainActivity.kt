package com.abhinavdev.animeapp.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseActivity
import com.abhinavdev.animeapp.databinding.ActivityMainBinding
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.ui.main.adapters.MainFragmentAdapter
import com.abhinavdev.animeapp.ui.main.viewmodels.MainViewModel
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.PrefUtils
import com.abhinavdev.animeapp.util.adapter.FadeTransformation
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.ViewUtil
import com.abhinavdev.animeapp.util.extension.createViewModel
import com.abhinavdev.animeapp.util.extension.showOrHide
import com.abhinavdev.animeapp.util.extension.testLog
import com.abhinavdev.animeapp.util.extension.toast
import com.google.android.material.navigation.NavigationBarView

class MainActivity : BaseActivity(), NavigationBarView.OnItemSelectedListener {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

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
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        ViewUtil.setOnApplyUiInsetsListener(binding.bottomNavBar) { insets ->
            ViewUtil.setBottomPadding(binding.bottomNavBar, insets.bottom, false)
        }
        setContentView(binding.root)

        viewModel = createViewModel(MainViewModel::class.java)
        setOnBackPressedListener {
            testLog { "Back Pressed" }
            val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            if (fragment != null) {
                //first if there is any fragment open close it
                supportFragmentManager.popBackStackImmediate()
            } else if (currentPageType == MainFragmentAdapter.PageType.ANIME) {
                //if home page just finish activity
                finish()
            } else {
                //if its not home page then back pressing will bring us on home page
                navigateToPosition(MainFragmentAdapter.PageType.ANIME)
            }
        }
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
        binding.viewPager.setPageTransformer(FadeTransformation())
    }

    private fun setListeners() {
        binding.bottomNavBar.setOnItemSelectedListener(this)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPageType = rootFragmentTypes[position]
                setUpStatusBarColor()
                updateSelectedItemId()
            }
        })
    }

    private fun setObservers() {
        viewModel.getAccessTokenResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let {
                            PrefUtils.setObject(Const.PrefKeys.ACCESS_TOKEN_KEY, it)
                            getProfile()
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
        viewModel.getMalProfileResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let {
                            PrefUtils.setObject(Const.PrefKeys.MAL_PROFILE_KEY, it)
                            PrefUtils.setBoolean(Const.PrefKeys.IS_AUTHENTICATED_KEY, true)
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

    private fun getProfile() {
        viewModel.getProfile()
    }

    private fun setUpStatusBarColor() {
//        setStatusBarIconsDark(false)
    }

//    @SuppressLint("MissingSuperCall")
//    override fun onBackPressed() {
//        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
//        if (fragment != null) {
//            //first if there is any fragment open close it
//            supportFragmentManager.popBackStackImmediate()
//        } else if (currentPageType == MainFragmentAdapter.PageType.ANIME) {
//            //if home page just finish activity
//            finish()
//        } else {
//            //if its not home page then back pressing will bring us on home page
//            navigateToPosition(MainFragmentAdapter.PageType.ANIME)
//        }
//    }

    fun isLoaderVisible(b: Boolean) {
        binding.loader.progressOverlay.showOrHide(b)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_anime -> navigateToPosition(MainFragmentAdapter.PageType.ANIME)
            R.id.navigation_manga -> navigateToPosition(MainFragmentAdapter.PageType.MANGA)
            R.id.navigation_more -> navigateToPosition(MainFragmentAdapter.PageType.MORE)
            else -> false
        }
    }

    private fun navigateToPosition(type: MainFragmentAdapter.PageType): Boolean {
        if (currentPageType != type) {
            val position = rootFragmentTypes.indexOf(type)
            binding.viewPager.setCurrentItem(position, false)
        }
        return true
    }

    fun navigateToFragment(fragment: Fragment) {
        addFragment(fragment, R.id.nav_host_fragment, true)
    }

    fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun updateSelectedItemId() {

        val selectedItemId = when (currentPageType) {
            MainFragmentAdapter.PageType.ANIME -> R.id.navigation_anime
            MainFragmentAdapter.PageType.MANGA -> R.id.navigation_manga
            MainFragmentAdapter.PageType.MORE -> R.id.navigation_more
        }

        // Update the bottom navigation bar
        binding.bottomNavBar.selectedItemId = selectedItemId
    }

    fun logout() {
        SettingsHelper.logout()
    }
}
package com.abhinavdev.animeapp.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseActivity
import com.abhinavdev.animeapp.databinding.ActivityMainBinding
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.models.enums.MediaType
import com.abhinavdev.animeapp.ui.anime.AnimeDetailsFragment
import com.abhinavdev.animeapp.ui.main.adapters.MainFragmentAdapter
import com.abhinavdev.animeapp.ui.main.viewmodels.MainViewModel
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.PrefUtils
import com.abhinavdev.animeapp.util.adapter.FadeTransformation
import com.abhinavdev.animeapp.util.appsettings.AppMediaType
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.ViewUtil
import com.abhinavdev.animeapp.util.extension.createViewModel
import com.abhinavdev.animeapp.util.extension.showOrHide
import com.abhinavdev.animeapp.util.extension.toast
import com.abhinavdev.animeapp.util.ui.curved_bottom_navigation.CbnMenuItem

class MainActivity : BaseActivity() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val rootFragmentTypes: List<MainFragmentAdapter.PageType> = arrayListOf(
        MainFragmentAdapter.PageType.HOME,
        MainFragmentAdapter.PageType.GENRE,
        MainFragmentAdapter.PageType.SEARCH,
        MainFragmentAdapter.PageType.MY_LIST,
        MainFragmentAdapter.PageType.MORE,
    )

    private var fragmentAdapter: MainFragmentAdapter? = null
    private var currentPageType: MainFragmentAdapter.PageType = MainFragmentAdapter.PageType.HOME

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
        setOnBackPressedListener(::handleOnBackPressed)
        init()
        checkLoginIntent(intent)
        ifFromLinkNavigateToDetailsPage(intent)
    }

    private fun handleOnBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (fragment != null) {
            //first if there is any fragment open close it
            supportFragmentManager.popBackStackImmediate()
        } else if (currentPageType == MainFragmentAdapter.PageType.HOME) {
            //if home page just finish activity
            finish()
        } else {
            //if its not home page then back pressing will bring us on home page
            navigateToPosition(MainFragmentAdapter.PageType.HOME)
        }
    }

    private fun parseIntentData(uri: Uri) {
        val code = uri.getQueryParameter("code")
        val receivedState = uri.getQueryParameter("state")
        if (code != null && receivedState == Const.Mal.STATE) {
            viewModel.getAccessToken(code)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        checkLoginIntent(intent)
        ifFromLinkNavigateToDetailsPage(intent)
    }

    private fun checkLoginIntent(intent: Intent) {
        if (intent.dataString?.startsWith(Const.Links.APP_DEEP_LINK) == true) {
            intent.data?.let { parseIntentData(it) }
        }
    }

    private fun ifFromLinkNavigateToDetailsPage(intent: Intent) {
        val data = findMediaIdAndTypeFromIntent(intent.dataString)

        if (data != null) {
            val mediaId = data.first
            val mediaType = MediaType.valueOfOrDefault(data.second)
            val fragment = when (mediaType) {
                MediaType.ANIME -> AnimeDetailsFragment.newInstance(mediaId)
                MediaType.MANGA -> AnimeDetailsFragment.newInstance(mediaId)
                else -> null
            }
            fragment?.let { navigateToFragment(it) }
        }
    }

    private fun findMediaIdAndTypeFromIntent(data: String?): Pair<Int, String>? {
        data?.let { dataString ->
            // Updated regex to match both types of URLs
            val regex = Regex("myanimelist\\.net/(\\w+)/(\\d+)(?:/[^/]*)?")
            val matchResult = regex.find(dataString)
            matchResult?.let {
                val mediaType = it.groupValues[1]
                val mediaId = it.groupValues[2].toIntOrNull()
                if (mediaId != null) return mediaId to mediaType
            }
        }
        return null
    }

    private fun init() {
        initComponents()
        setAdapters()
        setListeners()
        setObservers()
    }

    private fun initComponents() {
        val mediaType = SettingsHelper.getAppMediaType()

        val homeInactiveIcon: Int
        val homeActiveIcon: Int

        when (mediaType) {
            AppMediaType.ANIME -> {
                homeInactiveIcon = R.drawable.ic_anime_inactive
                homeActiveIcon = R.drawable.ic_anime_active
            }

            AppMediaType.MANGA -> {
                homeInactiveIcon = R.drawable.ic_manga_inactive
                homeActiveIcon = R.drawable.ic_manga_active
            }
        }

        val menuItems = arrayOf(
            CbnMenuItem(
                homeInactiveIcon, //normal icon
                homeActiveIcon, //selected icon
            ), CbnMenuItem(
                R.drawable.ic_genre_inactive,
                R.drawable.ic_genre_active,
            ), CbnMenuItem(
                R.drawable.ic_search_inactive,
                R.drawable.ic_search_active,
            ), CbnMenuItem(
                R.drawable.ic_my_list_inactive,
                R.drawable.ic_my_list_active,
            ), CbnMenuItem(
                R.drawable.ic_more_inactive,
                R.drawable.ic_more_active,
            )
        )
        binding.bottomNavBar.setMenuItems(menuItems, 0)
        binding.bottomNavBar.setOnFabClickListener {

        }
    }

    private fun setAdapters() {
        fragmentAdapter =
            MainFragmentAdapter(this, rootFragmentTypes, SettingsHelper.getAppMediaType())
        binding.viewPager.adapter = fragmentAdapter
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.setPageTransformer(FadeTransformation())
    }

    private fun setListeners() {
        binding.bottomNavBar.setOnMenuItemClickListener { _, index ->
            when (index) {
                0 -> navigateToPosition(MainFragmentAdapter.PageType.HOME)
                1 -> navigateToPosition(MainFragmentAdapter.PageType.GENRE)
                2 -> navigateToPosition(MainFragmentAdapter.PageType.SEARCH)
                3 -> navigateToPosition(MainFragmentAdapter.PageType.MY_LIST)
                4 -> navigateToPosition(MainFragmentAdapter.PageType.MORE)
            }
        }
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPageType = rootFragmentTypes[position]
                setUpStatusBarColor()
                updateSelectedItemId()
            }
        })
    }

    private fun navigateToPosition(type: MainFragmentAdapter.PageType): Boolean {
        if (currentPageType != type) {
            binding.viewPager.setCurrentItem(type.position, false)
        }
        return true
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

    fun isLoaderVisible(b: Boolean) {
        binding.loader.progressOverlay.showOrHide(b)
    }

    fun navigateToFragment(fragment: Fragment) {
        addFragment(
            fragment = fragment,
            containerId = R.id.nav_host_fragment,
            addToBackStack = true,
            enterAnim = R.anim.enter_bottom_to_top,
            exitAnim = R.anim.exit_top_to_bottom
        )
    }

    fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun updateSelectedItemId() {
        val selectedItemId = currentPageType.position/*when (currentPageType) {
            MainFragmentAdapter.PageType.ANIME -> R.id.navigation_anime
            MainFragmentAdapter.PageType.MANGA -> R.id.navigation_manga
            MainFragmentAdapter.PageType.MORE -> R.id.navigation_more
        }*/

        // Update the bottom navigation bar
        binding.bottomNavBar.onMenuItemClick(selectedItemId)
    }

    fun logout() {
        SettingsHelper.logout()
    }
}
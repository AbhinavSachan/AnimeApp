package com.abhinavdev.animeapp.ui.anime

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseFragment
import com.abhinavdev.animeapp.databinding.FragmentAnimeBinding
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.models.anime.AnimeData
import com.abhinavdev.animeapp.remote.models.anime.AnimeSearchResponse
import com.abhinavdev.animeapp.remote.models.malmodels.MalAnimeData
import com.abhinavdev.animeapp.remote.models.malmodels.MalMyAnimeListResponse
import com.abhinavdev.animeapp.ui.anime.adapters.AnimeBannerAdapter
import com.abhinavdev.animeapp.ui.anime.adapters.AnimeVerticalCardAdapter
import com.abhinavdev.animeapp.ui.anime.adapters.MalAnimeVerticalCardAdapter
import com.abhinavdev.animeapp.ui.anime.misc.MultiApiCallType
import com.abhinavdev.animeapp.ui.anime.viewmodel.AnimeViewModel
import com.abhinavdev.animeapp.ui.common.listeners.CustomClickCallback
import com.abhinavdev.animeapp.ui.common.ui.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.abhinavdev.animeapp.ui.main.MainActivity
import com.abhinavdev.animeapp.util.appsettings.SettingsPrefs
import com.abhinavdev.animeapp.util.extension.createViewModel
import com.abhinavdev.animeapp.util.extension.getDisplaySize
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.isHidden
import com.abhinavdev.animeapp.util.extension.isVisible
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.showOrInvisible
import com.abhinavdev.animeapp.util.extension.toast
import com.facebook.shimmer.ShimmerFrameLayout

class AnimeFragment : BaseFragment(), View.OnClickListener, CustomClickCallback {
    private var _binding: FragmentAnimeBinding? = null
    private val binding get() = _binding!!
    private var parentActivity: MainActivity? = null

    private lateinit var viewModel: AnimeViewModel

    private val airingList: ArrayList<AnimeData> = arrayListOf()
    private var airingAdapter: AnimeBannerAdapter? = null

    private val popularList: ArrayList<AnimeData> = arrayListOf()
    private var popularAdapter: AnimeVerticalCardAdapter? = null

    private val favouriteList: ArrayList<AnimeData> = arrayListOf()
    private var favouriteAdapter: AnimeVerticalCardAdapter? = null

    private val upcomingList: ArrayList<AnimeData> = arrayListOf()
    private var upcomingAdapter: AnimeVerticalCardAdapter? = null

    private val recommendedList: ArrayList<MalAnimeData> = arrayListOf()
    private var recommendedAdapter: MalAnimeVerticalCardAdapter? = null

    private val rankedList: ArrayList<MalAnimeData> = arrayListOf()
    private var rankedAdapter: MalAnimeVerticalCardAdapter? = null

    private var isLoading = false
    private var isRefreshed = false
    private var isAuthenticated = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = context as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        parentActivity = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModel(AnimeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initComponents()
        setAdapters()
        setListeners()
        setObservers()
        allApiCalls()
    }

    private fun initComponents() {
        isAuthenticated = SettingsPrefs.getIsAuthenticated()
        setAllTitles()
        setTopViewPagerHeight()
    }

    private fun setAllTitles() {
        binding.groupPopularAnime.tvHeading.text = getString(R.string.msg_most_popular)
        binding.groupFavouriteAnime.tvHeading.text = getString(R.string.msg_top_favourite)
        binding.groupUpcomingAnime.tvHeading.text = getString(R.string.msg_upcoming)
        binding.groupRecommendedAnime.tvHeading.text = getString(R.string.msg_top_recommended)
        binding.groupTopRankedAnime.tvHeading.text = getString(R.string.msg_top_ranked)
    }

    private fun setTopViewPagerHeight() {
        val screenSize = getDisplaySize(requireActivity())
        if (screenSize.width > screenSize.height) {
            binding.svTopAiring.layoutParams.height = (screenSize.height * 8) / 9
        } else {
            binding.svTopAiring.layoutParams.height = (screenSize.width * 8) / 9
        }
    }

    private fun setAdapters() {
        //top airing in top auto image slider
        airingAdapter = AnimeBannerAdapter(airingList, this)
        binding.svTopAiring.setIndicatorAnimation(IndicatorAnimationType.DROP)
        binding.svTopAiring.setSliderAdapter(airingAdapter!!)

        //second rv
        popularAdapter = AnimeVerticalCardAdapter(popularList, MultiApiCallType.TopPopular, this)
        popularAdapter?.setHasStableIds(true)
        binding.groupPopularAnime.rvItems.setHasFixedSize(true)
        binding.groupPopularAnime.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupPopularAnime.rvItems.adapter = popularAdapter

        //third rv
        favouriteAdapter =
            AnimeVerticalCardAdapter(favouriteList, MultiApiCallType.TopFavourite, this)
        favouriteAdapter?.setHasStableIds(true)
        binding.groupFavouriteAnime.rvItems.setHasFixedSize(true)
        binding.groupFavouriteAnime.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupFavouriteAnime.rvItems.adapter = favouriteAdapter

        //fourth rv
        upcomingAdapter = AnimeVerticalCardAdapter(upcomingList, MultiApiCallType.TopUpcoming, this)
        upcomingAdapter?.setHasStableIds(true)
        binding.groupUpcomingAnime.rvItems.setHasFixedSize(true)
        binding.groupUpcomingAnime.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupUpcomingAnime.rvItems.adapter = upcomingAdapter

        //fifth rv
        recommendedAdapter =
            MalAnimeVerticalCardAdapter(recommendedList, MultiApiCallType.TopRecommended, this)
        recommendedAdapter?.setHasStableIds(true)
        binding.groupRecommendedAnime.rvItems.setHasFixedSize(true)
        binding.groupRecommendedAnime.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupRecommendedAnime.rvItems.adapter = recommendedAdapter

        //sixth rv
        rankedAdapter = MalAnimeVerticalCardAdapter(rankedList, MultiApiCallType.TopRanked, this)
        rankedAdapter?.setHasStableIds(true)
        binding.groupTopRankedAnime.rvItems.setHasFixedSize(true)
        binding.groupTopRankedAnime.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupTopRankedAnime.rvItems.adapter = rankedAdapter
    }

    private fun setListeners() {
        binding.ivRefresh.setOnClickListener(this)
        binding.ivSearch.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.ivRefresh -> onRefreshClick()
            binding.ivSearch -> {}
        }
    }

    private fun onRefreshClick() = if (!isLoading) {
        isRefreshed = true
        allApiCalls()
    } else {
        toast(getString(R.string.msg_please_wait))
    }

    private fun setAuthenticationView() {
        if (!isAuthenticated && binding.groupRecommendedAnime.group.isVisible()) {
            binding.groupRecommendedAnime.root.hide()
            binding.groupTopRankedAnime.root.hide()
        } else if (isAuthenticated && binding.groupRecommendedAnime.root.isHidden()) {
            binding.groupRecommendedAnime.root.show()
            binding.groupTopRankedAnime.root.show()
        }
    }

    private fun setObservers() {
        SettingsPrefs.onAuthenticationChange {
            if (it != isAuthenticated) {
                isAuthenticated = it
                setAuthenticationView()
                allApiCalls()
            }
        }
        viewModel.animeAllApiResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { apiMap ->
                apiMap.forEach { (key, response) ->
                    when (key) {
                        MultiApiCallType.TopAiring -> handleAnimeSearchResponse(
                            key, response, ::airingLoading
                        )

                        MultiApiCallType.TopPopular -> handleAnimeSearchResponse(
                            key, response, ::popularLoading
                        )

                        MultiApiCallType.TopFavourite -> handleAnimeSearchResponse(
                            key, response, ::favouriteLoading
                        )

                        MultiApiCallType.TopUpcoming -> handleAnimeSearchResponse(
                            key, response, ::upcomingLoading
                        )

                        else -> {}
                    }
                }
            }
        }
        viewModel.animeAuthenticatedApiResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { apiMap ->
                apiMap.forEach { (key, response) ->
                    when (key) {
                        MultiApiCallType.TopRanked -> handleAnimeAuthenticatedResponse(
                            key, response, ::rankedLoading
                        )

                        MultiApiCallType.TopRecommended -> handleAnimeAuthenticatedResponse(
                            key, response, ::recommendedLoading
                        )

                        else -> {}
                    }
                }
            }
        }
    }

    // Function to handle each type of response
    private inline fun handleAnimeSearchResponse(
        key: MultiApiCallType,
        response: Resource<AnimeSearchResponse>,
        loadingFunction: (Boolean) -> Unit
    ) {
        when (response) {
            is Resource.Success -> {
                response.data?.data?.let {
                    when (key) {
                        MultiApiCallType.TopAiring -> setAiringData(it)
                        MultiApiCallType.TopPopular -> setPopularData(it)
                        MultiApiCallType.TopFavourite -> setFavouriteData(it)
                        MultiApiCallType.TopUpcoming -> setUpcomingData(it)
                        else -> {}
                    }
                }
                isLoading = false
                loadingFunction(false)
            }

            is Resource.Error -> {
                isLoading = false
                loadingFunction(false)
                response.message?.let { message -> toast(message) }
            }

            is Resource.Loading -> {
                isLoading = true
                loadingFunction(true)
            }
        }
    }

    // Function to handle each type of response
    private inline fun handleAnimeAuthenticatedResponse(
        key: MultiApiCallType,
        response: Resource<MalMyAnimeListResponse>,
        loadingFunction: (Boolean) -> Unit
    ) {
        when (response) {
            is Resource.Success -> {
                response.data?.data?.let {
                    when (key) {
                        MultiApiCallType.TopRecommended -> setRecommendedData(it)
                        MultiApiCallType.TopRanked -> setRankedData(it)
                        else -> {}
                    }
                }
                isLoading = false
                loadingFunction(false)
            }

            is Resource.Error -> {
                isLoading = false
                loadingFunction(false)
                response.message?.let { message -> toast(message) }
            }

            is Resource.Loading -> {
                isLoading = true
                loadingFunction(true)
            }
        }
    }

    private fun airingLoading(isLoading: Boolean) {
        with(binding) {
            showLoaderOrShimmer(isLoading, svTopAiring, shimmerViewPager)
        }
    }

    private fun popularLoading(isLoading: Boolean) {
        with(binding.groupPopularAnime) {
            showLoaderOrShimmer(isLoading, group, shimmerLoader.root)
        }
    }

    private fun favouriteLoading(isLoading: Boolean) {
        with(binding.groupFavouriteAnime) {
            showLoaderOrShimmer(isLoading, group, shimmerLoader.root)
        }
    }

    private fun upcomingLoading(isLoading: Boolean) {
        with(binding.groupUpcomingAnime) {
            showLoaderOrShimmer(isLoading, group, shimmerLoader.root)
        }
    }

    private fun recommendedLoading(isLoading: Boolean) {
        with(binding.groupRecommendedAnime) {
            showLoaderOrShimmer(isLoading, group, shimmerLoader.root)
        }
    }

    private fun rankedLoading(isLoading: Boolean) {
        with(binding.groupTopRankedAnime) {
            showLoaderOrShimmer(isLoading, group, shimmerLoader.root)
        }
    }

    private fun showLoaderOrShimmer(
        isLoading: Boolean, groupView: View, shimmerView: ShimmerFrameLayout
    ) {
        if (isRefreshed) {
            parentActivity?.isLoaderVisible(isLoading)
        } else {
            groupView.showOrInvisible(!isLoading)
            shimmerView.showOrInvisible(isLoading)
        }
        this.isLoading = isLoading
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setAiringData(animeData: ArrayList<AnimeData>) {
        airingList.clear()
        airingList.addAll(animeData)
        airingAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setPopularData(animeData: ArrayList<AnimeData>) {
        popularList.clear()
        popularList.addAll(animeData)
        popularAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setFavouriteData(animeData: ArrayList<AnimeData>) {
        favouriteList.clear()
        favouriteList.addAll(animeData)
        favouriteAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpcomingData(animeData: ArrayList<AnimeData>) {
        upcomingList.clear()
        upcomingList.addAll(animeData)
        upcomingAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecommendedData(animeData: ArrayList<MalAnimeData>) {
        recommendedList.clear()
        recommendedList.addAll(animeData)
        recommendedAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRankedData(animeData: ArrayList<MalAnimeData>) {
        rankedList.clear()
        rankedList.addAll(animeData)
        rankedAdapter?.notifyDataSetChanged()
    }

    private fun allApiCalls() {
        viewModel.getAllAnimeData()
        if (isAuthenticated) {
            viewModel.getAuthenticatedAnimeData()
        }
    }

    override fun <T> onItemClick(position: Int, type: T) {
        val apiType = type as MultiApiCallType
        when (apiType) {
            MultiApiCallType.TopAiring -> toast("Airing")
            MultiApiCallType.TopPopular -> toast("Popular")
            MultiApiCallType.TopFavourite -> toast("Favourite")
            MultiApiCallType.TopUpcoming -> toast("Upcoming")
            MultiApiCallType.TopRecommended -> toast("Recommended")
            MultiApiCallType.TopRanked -> toast("Ranked")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AnimeFragment()/*.apply {
            arguments = Bundle().apply {

            }
        }*/
    }

}
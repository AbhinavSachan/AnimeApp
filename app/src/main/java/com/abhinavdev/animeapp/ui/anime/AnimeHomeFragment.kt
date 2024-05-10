package com.abhinavdev.animeapp.ui.anime

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseFragment
import com.abhinavdev.animeapp.databinding.FragmentAnimeHomeBinding
import com.abhinavdev.animeapp.remote.kit.Event
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.models.anime.AnimeData
import com.abhinavdev.animeapp.remote.models.anime.AnimeSearchResponse
import com.abhinavdev.animeapp.remote.models.enums.AnimeFilter
import com.abhinavdev.animeapp.remote.models.malmodels.MalAnimeData
import com.abhinavdev.animeapp.remote.models.malmodels.MalMyAnimeListResponse
import com.abhinavdev.animeapp.ui.anime.adapters.AnimeBannerAdapter
import com.abhinavdev.animeapp.ui.anime.adapters.AnimeHorizontalAdapter
import com.abhinavdev.animeapp.ui.anime.adapters.MalAnimeHorizontalAdapter
import com.abhinavdev.animeapp.ui.anime.misc.MultiContentAdapterType
import com.abhinavdev.animeapp.ui.anime.viewmodel.AnimeViewModel
import com.abhinavdev.animeapp.ui.common.listeners.OnClickMultiTypeCallback
import com.abhinavdev.animeapp.ui.common.ui.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.abhinavdev.animeapp.ui.main.MainActivity
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.PrefUtils
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.createViewModel
import com.abhinavdev.animeapp.util.extension.getDisplaySize
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.isVisible
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.showOrHide
import com.abhinavdev.animeapp.util.extension.showOrInvisible
import com.abhinavdev.animeapp.util.extension.toast
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.Job


class AnimeHomeFragment : BaseFragment(), View.OnClickListener, OnClickMultiTypeCallback {
    private var _binding: FragmentAnimeHomeBinding? = null
    private val binding get() = _binding!!
    private var parentActivity: MainActivity? = null

    private lateinit var viewModel: AnimeViewModel

    private val airingList: ArrayList<AnimeData> = arrayListOf()
    private var airingAdapter: AnimeBannerAdapter? = null

    private val popularList: ArrayList<AnimeData> = arrayListOf()
    private var popularAdapter: AnimeHorizontalAdapter? = null

    private val favouriteList: ArrayList<AnimeData> = arrayListOf()
    private var favouriteAdapter: AnimeHorizontalAdapter? = null

    private val upcomingList: ArrayList<AnimeData> = arrayListOf()
    private var upcomingAdapter: AnimeHorizontalAdapter? = null

    private val recommendedList: ArrayList<MalAnimeData> = arrayListOf()
    private var recommendedAdapter: MalAnimeHorizontalAdapter? = null

    private val rankedList: ArrayList<MalAnimeData> = arrayListOf()
    private var rankedAdapter: MalAnimeHorizontalAdapter? = null

    private var isLoading = false
    private var isRefreshed = false
    private var isAuthenticated = false

    private var authCheckJob: Job? = null

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
        _binding = FragmentAnimeHomeBinding.inflate(layoutInflater, container, false)
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
        isAuthenticated = SettingsHelper.getIsAuthenticated()
        with(binding.toolbar){
            ivBack.hide()
            ivExtra.show()
            ivExtraTwo.show()
            tvTitle.text = getString(R.string.msg_anime)
            ivExtra.setImageResource(R.drawable.ic_refresh)
            ivExtraTwo.setImageResource(R.drawable.ic_search)
        }
        setAllTitles()
        setTopViewPagerHeight()
        initializeShimmerLoading()
    }

    private fun initializeShimmerLoading() {
        airingLoading(true)
        popularLoading(true)
        favouriteLoading(true)
        upcomingLoading(true)
        if (isAuthenticated){
            rankedLoading(true)
            recommendedLoading(true)
        }
    }

    private fun setAllTitles() {
        binding.groupPopular.tvHeading.text = getString(R.string.msg_most_popular)
        binding.groupFavourite.tvHeading.text = getString(R.string.msg_top_favourite)
        binding.groupUpcoming.tvHeading.text = getString(R.string.msg_upcoming)
        binding.groupRecommended.tvHeading.text = getString(R.string.msg_top_recommended)
        binding.groupTopRanked.tvHeading.text = getString(R.string.msg_top_ranked)
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
        popularAdapter =
            AnimeHorizontalAdapter(popularList, MultiContentAdapterType.TopPopular, this)
        popularAdapter?.setHasStableIds(true)
        binding.groupPopular.rvItems.setHasFixedSize(Const.Other.HAS_FIXED_SIZE)
        binding.groupPopular.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupPopular.rvItems.adapter = popularAdapter

        //third rv
        favouriteAdapter =
            AnimeHorizontalAdapter(favouriteList, MultiContentAdapterType.TopFavourite, this)
        favouriteAdapter?.setHasStableIds(true)
        binding.groupFavourite.rvItems.setHasFixedSize(Const.Other.HAS_FIXED_SIZE)
        binding.groupFavourite.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupFavourite.rvItems.adapter = favouriteAdapter

        //fourth rv
        upcomingAdapter =
            AnimeHorizontalAdapter(upcomingList, MultiContentAdapterType.TopUpcoming, this)
        upcomingAdapter?.setHasStableIds(true)
        binding.groupUpcoming.rvItems.setHasFixedSize(Const.Other.HAS_FIXED_SIZE)
        binding.groupUpcoming.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupUpcoming.rvItems.adapter = upcomingAdapter

        //fifth rv
        recommendedAdapter =
            MalAnimeHorizontalAdapter(recommendedList, MultiContentAdapterType.TopRecommended, this)
        recommendedAdapter?.setHasStableIds(true)
        binding.groupRecommended.rvItems.setHasFixedSize(Const.Other.HAS_FIXED_SIZE)
        binding.groupRecommended.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupRecommended.rvItems.adapter = recommendedAdapter

        //sixth rv
        rankedAdapter =
            MalAnimeHorizontalAdapter(rankedList, MultiContentAdapterType.TopRanked, this)
        rankedAdapter?.setHasStableIds(true)
        binding.groupTopRanked.rvItems.setHasFixedSize(Const.Other.HAS_FIXED_SIZE)
        binding.groupTopRanked.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupTopRanked.rvItems.adapter = rankedAdapter
    }

    private fun setListeners() {
        binding.toolbar.ivExtra.setOnClickListener(this)
        binding.toolbar.ivExtraTwo.setOnClickListener(this)
        binding.groupTopRanked.tvViewAll.setOnClickListener(this)
        binding.groupRecommended.tvViewAll.setOnClickListener(this)
        binding.groupPopular.tvViewAll.setOnClickListener(this)
        binding.groupFavourite.tvViewAll.setOnClickListener(this)
        binding.groupUpcoming.tvViewAll.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.toolbar.ivExtra -> onRefreshClick()
            binding.toolbar.ivExtraTwo -> onSearchClick()
            binding.groupTopRanked.tvViewAll -> onTopRankedClick()
            binding.groupRecommended.tvViewAll -> onTopRecommendedClick()
            binding.groupPopular.tvViewAll -> openJikanFragment(AnimeFilter.BY_POPULARITY)
            binding.groupFavourite.tvViewAll -> openJikanFragment(AnimeFilter.FAVORITE)
            binding.groupUpcoming.tvViewAll -> openJikanFragment(AnimeFilter.UPCOMING)
        }
    }

    private fun onSearchClick() {

    }

    private fun openJikanFragment(filter: AnimeFilter) {
        parentActivity?.navigateToFragment(JikanTopAnimeFragment.newInstance(filter))
    }

    private fun onTopRankedClick() {
        parentActivity?.navigateToFragment(AnimeRankingFragment.newInstance())
    }

    private fun onTopRecommendedClick() {
        parentActivity?.navigateToFragment(AnimeRecommendedFragment.newInstance())
    }

    private fun onRefreshClick() = if (!isLoading) {
        isRefreshed = true
        allApiCalls()
    } else {
        toast(getString(R.string.msg_please_wait))
    }

    private fun setAuthenticationView() {
        val condition1 = isAuthenticated != binding.groupTopRanked.root.isVisible()
        val condition2 = isAuthenticated != binding.groupRecommended.root.isVisible()
        binding.groupTopRanked.root.showOrHide(condition1)
        binding.groupRecommended.root.showOrHide(condition2)
    }

    override fun onResume() {
        super.onResume()
        //set layout first time we arrive on fragment
        setAuthLayout(PrefUtils.getBoolean(Const.PrefKeys.IS_AUTHENTICATED_KEY))
        //set layout when while being in fragment value changes
        PrefUtils.setBooleanObserver(Const.PrefKeys.IS_AUTHENTICATED_KEY) {
            setAuthLayout(it)
        }
    }

    private fun setAuthLayout(authenticated: Boolean) {
        if (authenticated != isAuthenticated) {
            isAuthenticated = authenticated
            setAuthenticationView()
            allApiCalls()
        }
    }

    override fun onPause() {
        super.onPause()
        PrefUtils.removeObserver()
    }

    private fun setObservers() {
        viewModel.airingResponse.handleJikanResponse(MultiContentAdapterType.TopAiring)
        viewModel.popularResponse.handleJikanResponse(MultiContentAdapterType.TopPopular)
        viewModel.favouriteResponse.handleJikanResponse(MultiContentAdapterType.TopFavourite)
        viewModel.upcomingResponse.handleJikanResponse(MultiContentAdapterType.TopUpcoming)
        viewModel.recommendedResponse.handleMalResponse(MultiContentAdapterType.TopRecommended)
        viewModel.rankingResponse.handleMalResponse(MultiContentAdapterType.TopRanked)

        viewModel.allResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { loading ->
                isLoading = loading
                if (isRefreshed) {
                    parentActivity?.isLoaderVisible(isLoading)
                }
            }
        }
    }

    // Function to handle each type of response
    private fun LiveData<Event<Resource<AnimeSearchResponse>>>.handleJikanResponse(key: MultiContentAdapterType) {
        observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.data?.let {
                            when (key) {
                                MultiContentAdapterType.TopAiring -> {
                                    setAiringData(it)
                                }

                                MultiContentAdapterType.TopPopular -> {
                                    setPopularData(it)
                                }

                                MultiContentAdapterType.TopFavourite -> {
                                    setFavouriteData(it)
                                }

                                MultiContentAdapterType.TopUpcoming -> {
                                    setUpcomingData(it)
                                }

                                else -> {}
                            }
                            key.setShimmerLoading(false)
                        }
                    }

                    is Resource.Error -> {
                        key.setShimmerLoading(false)
                        response.message?.let { message -> toast(message) }
                    }

                    is Resource.Loading -> {

                    }
                }
            }
        }
    }

    // Function to handle each type of response
    private fun LiveData<Event<Resource<MalMyAnimeListResponse>>>.handleMalResponse(key: MultiContentAdapterType) {
        observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.data?.let {
                            when (key) {

                                MultiContentAdapterType.TopRanked -> {
                                    setRankedData(it)
                                }

                                MultiContentAdapterType.TopRecommended -> {
                                    setRecommendedData(it)
                                }

                                else -> {}
                            }
                            key.setShimmerLoading(false)
                        }
                    }

                    is Resource.Error -> {
                        key.setShimmerLoading(false)
                        response.message?.let { message -> toast(message) }
                    }

                    is Resource.Loading -> {

                    }
                }
            }
        }
    }

    private fun MultiContentAdapterType.setShimmerLoading(isLoading: Boolean) {
        when (this) {
            MultiContentAdapterType.TopAiring -> airingLoading(isLoading)
            MultiContentAdapterType.TopPopular -> popularLoading(isLoading)
            MultiContentAdapterType.TopFavourite -> favouriteLoading(isLoading)
            MultiContentAdapterType.TopUpcoming -> upcomingLoading(isLoading)
            MultiContentAdapterType.TopRanked -> rankedLoading(isLoading)
            MultiContentAdapterType.TopRecommended -> recommendedLoading(isLoading)
            else -> {}
        }
    }

    private fun airingLoading(isLoading: Boolean) {
        with(binding) {
            showOrHideShimmer(isLoading, svTopAiring, shimmerViewPager)
        }
    }

    private fun popularLoading(isLoading: Boolean) {
        with(binding.groupPopular) {
            showOrHideShimmer(isLoading, group, shimmerLoader.root)
        }
    }

    private fun favouriteLoading(isLoading: Boolean) {
        with(binding.groupFavourite) {
            showOrHideShimmer(isLoading, group, shimmerLoader.root)
        }
    }

    private fun upcomingLoading(isLoading: Boolean) {
        with(binding.groupUpcoming) {
            showOrHideShimmer(isLoading, group, shimmerLoader.root)
        }
    }

    private fun recommendedLoading(isLoading: Boolean) {
        with(binding.groupRecommended) {
            showOrHideShimmer(isLoading, group, shimmerLoader.root)
        }
    }

    private fun rankedLoading(isLoading: Boolean) {
        with(binding.groupTopRanked) {
            showOrHideShimmer(isLoading, group, shimmerLoader.root)
        }
    }

    private fun showOrHideShimmer(
        isLoading: Boolean, groupView: View, shimmerView: ShimmerFrameLayout
    ) {
        groupView.showOrInvisible(!isLoading)
        shimmerView.showOrInvisible(isLoading)
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
        viewModel.getAllData()
    }

    override fun <T> onItemClick(position: Int, type: T) {
        val apiType = type as MultiContentAdapterType
        when (apiType) {
            MultiContentAdapterType.TopAiring -> toast("Airing")
            MultiContentAdapterType.TopPopular -> toast("Popular")
            MultiContentAdapterType.TopFavourite -> toast("Favourite")
            MultiContentAdapterType.TopUpcoming -> toast("Upcoming")
            MultiContentAdapterType.TopRecommended -> toast("Recommended")
            MultiContentAdapterType.TopRanked -> toast("Ranked")
            else -> {}
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AnimeHomeFragment()/*.apply {
            arguments = Bundle().apply {

            }
        }*/
    }

}
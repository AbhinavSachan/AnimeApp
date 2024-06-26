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
import com.abhinavdev.animeapp.util.extension.ViewUtil
import com.abhinavdev.animeapp.util.extension.applyDimen
import com.abhinavdev.animeapp.util.extension.createViewModel
import com.abhinavdev.animeapp.util.extension.getDisplaySize
import com.abhinavdev.animeapp.util.extension.setHeightAsPercentageOfGivenHeight
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.showOrHide
import com.abhinavdev.animeapp.util.extension.showOrInvisible
import com.abhinavdev.animeapp.util.extension.toast
import com.facebook.shimmer.ShimmerFrameLayout


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
        with(binding.toolbar) {
            ivExtra.show()
            ivExtraTwo.show()
            ivExtraThree.show()
            ivExtra.setImageResource(R.drawable.ic_refresh)
            ivExtraTwo.setImageResource(R.drawable.ic_random)
            ivExtraThree.setImageResource(R.drawable.ic_search)

            ViewUtil.setOnApplyUiInsetsListener(root) { insets ->
                ViewUtil.setTopPadding(root, insets.top)
            }
        }
        val bottomBarHeight = applyDimen(R.dimen.cbn_height)
        val salt = applyDimen(R.dimen.bottom_bar_height_salt)
        ViewUtil.setOnApplyUiInsetsListener(binding.nestedScrollView) { insets ->
            ViewUtil.setBottomPadding(binding.nestedScrollView, insets.bottom + bottomBarHeight + salt)
        }
        setAllTitles()
        setTopViewPagerHeight()
    }

    private fun initializeShimmerLoading() {
        airingLoading(true)
        popularLoading(true)
        favouriteLoading(true)
        upcomingLoading(true)
        if (isAuthenticated) {
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
        activity?.let { getDisplaySize(it) }?.let {
            binding.svTopAiring.autoImageSlider.setHeightAsPercentageOfGivenHeight(it.height,55)
        }
    }

    private fun setAdapters() {
        //top airing in top auto image slider
        airingAdapter = AnimeBannerAdapter(airingList, this)
        binding.svTopAiring.autoImageSlider.setIndicatorAnimation(IndicatorAnimationType.DROP)
        binding.svTopAiring.autoImageSlider.setSliderAdapter(airingAdapter!!)
        binding.svTopAiring.autoImageSlider.startAutoCycle()

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
        binding.toolbar.ivExtraThree.setOnClickListener(this)
        binding.groupTopRanked.tvViewAll.setOnClickListener(this)
        binding.groupRecommended.tvViewAll.setOnClickListener(this)
        binding.groupPopular.tvViewAll.setOnClickListener(this)
        binding.groupFavourite.tvViewAll.setOnClickListener(this)
        binding.groupUpcoming.tvViewAll.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.toolbar.ivExtra -> onRefreshClick()
            binding.toolbar.ivExtraTwo -> onRandomClick()
            binding.toolbar.ivExtraThree -> onSearchClick()
            binding.groupTopRanked.tvViewAll -> onTopRankedClick()
            binding.groupRecommended.tvViewAll -> onTopRecommendedClick()
            binding.groupPopular.tvViewAll -> openJikanFragment(AnimeFilter.BY_POPULARITY)
            binding.groupFavourite.tvViewAll -> openJikanFragment(AnimeFilter.FAVORITE)
            binding.groupUpcoming.tvViewAll -> openJikanFragment(AnimeFilter.UPCOMING)
        }
    }

    private fun onRefreshClick() = if (!isLoading) {
        isRefreshed = true
        allApiCalls()
    } else {
        toast(getString(R.string.msg_please_wait))
    }

    private fun onRandomClick() {

    }

    private fun onSearchClick() {

    }

    private fun openJikanFragment(filter: AnimeFilter) {
        parentActivity?.navigateToFragment(JikanTopAnimeFragment.newInstance(filter = filter))
    }

    private fun onTopRankedClick() {
        parentActivity?.navigateToFragment(AnimeRankingFragment.newInstance())
    }

    private fun onTopRecommendedClick() {
        parentActivity?.navigateToFragment(AnimeRecommendedFragment.newInstance())
    }

    private fun setAuthenticationView() {
        binding.groupTopRanked.root.showOrHide(isAuthenticated)
        binding.groupRecommended.root.showOrHide(isAuthenticated)
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

    override fun onPause() {
        super.onPause()
        PrefUtils.removeObserver()
    }

    private fun setAuthLayout(authenticated: Boolean) {
        if (authenticated != isAuthenticated) {
            isAuthenticated = authenticated
            setAuthenticationView()
            allApiCalls()
        }
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
                    if (!isLoading) isRefreshed = false
                } else if (isLoading) {
                    initializeShimmerLoading()
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
                        }
                        key.stopShimmerLoading()
                    }

                    is Resource.Error -> {
                        key.stopShimmerLoading()
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
                            key.stopShimmerLoading()
                        }
                    }

                    is Resource.Error -> {
                        key.stopShimmerLoading()
                        response.message?.let { message -> toast(message) }
                    }

                    is Resource.Loading -> {

                    }
                }
            }
        }
    }

    private fun MultiContentAdapterType.stopShimmerLoading() {
        when (this) {
            MultiContentAdapterType.TopAiring -> airingLoading(false)
            MultiContentAdapterType.TopPopular -> popularLoading(false)
            MultiContentAdapterType.TopFavourite -> favouriteLoading(false)
            MultiContentAdapterType.TopUpcoming -> upcomingLoading(false)
            MultiContentAdapterType.TopRanked -> rankedLoading(false)
            MultiContentAdapterType.TopRecommended -> recommendedLoading(false)
            else -> {}
        }
    }

    private fun airingLoading(isLoading: Boolean) {
        with(binding.svTopAiring) {
            showOrHideShimmer(isLoading, autoImageSlider, shimmerLoader)
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
            MultiContentAdapterType.TopAiring -> onAnimeClick(airingList[position].malId)
            MultiContentAdapterType.TopPopular -> onAnimeClick(popularList[position].malId)
            MultiContentAdapterType.TopFavourite -> onAnimeClick(favouriteList[position].malId)
            MultiContentAdapterType.TopUpcoming -> onAnimeClick(upcomingList[position].malId)
            MultiContentAdapterType.TopRecommended -> onAnimeClick(recommendedList[position].node?.id)
            MultiContentAdapterType.TopRanked -> onAnimeClick(rankedList[position].node?.id)
            else -> {}
        }
    }

    private fun onAnimeClick(animeId:Int?){
        if (animeId != null){
            parentActivity?.navigateToFragment(AnimeDetailsFragment.newInstance(animeId))
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
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
import com.abhinavdev.animeapp.databinding.FragmentAnimeHomeBinding
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.models.anime.AnimeData
import com.abhinavdev.animeapp.remote.models.anime.AnimeSearchResponse
import com.abhinavdev.animeapp.remote.models.enums.AnimeFilter
import com.abhinavdev.animeapp.remote.models.malmodels.MalAnimeData
import com.abhinavdev.animeapp.remote.models.malmodels.MalMyAnimeListResponse
import com.abhinavdev.animeapp.ui.anime.adapters.AnimeBannerAdapter
import com.abhinavdev.animeapp.ui.anime.adapters.AnimeHorizontalAdapter
import com.abhinavdev.animeapp.ui.anime.adapters.MalAnimeHorizontalAdapter
import com.abhinavdev.animeapp.ui.anime.misc.MultiApiCallType
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
import com.abhinavdev.animeapp.util.extension.isHidden
import com.abhinavdev.animeapp.util.extension.isVisible
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.showOrInvisible
import com.abhinavdev.animeapp.util.extension.toast
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.Job
import java.io.Serializable


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
        setAllTitles()
        setTopViewPagerHeight()
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
        popularAdapter = AnimeHorizontalAdapter(popularList, MultiApiCallType.TopPopular, this)
        popularAdapter?.setHasStableIds(true)
        binding.groupPopular.rvItems.setHasFixedSize(true)
        binding.groupPopular.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupPopular.rvItems.adapter = popularAdapter

        //third rv
        favouriteAdapter =
            AnimeHorizontalAdapter(favouriteList, MultiApiCallType.TopFavourite, this)
        favouriteAdapter?.setHasStableIds(true)
        binding.groupFavourite.rvItems.setHasFixedSize(true)
        binding.groupFavourite.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupFavourite.rvItems.adapter = favouriteAdapter

        //fourth rv
        upcomingAdapter = AnimeHorizontalAdapter(upcomingList, MultiApiCallType.TopUpcoming, this)
        upcomingAdapter?.setHasStableIds(true)
        binding.groupUpcoming.rvItems.setHasFixedSize(true)
        binding.groupUpcoming.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupUpcoming.rvItems.adapter = upcomingAdapter

        //fifth rv
        recommendedAdapter =
            MalAnimeHorizontalAdapter(recommendedList, MultiApiCallType.TopRecommended, this)
        recommendedAdapter?.setHasStableIds(true)
        binding.groupRecommended.rvItems.setHasFixedSize(true)
        binding.groupRecommended.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupRecommended.rvItems.adapter = recommendedAdapter

        //sixth rv
        rankedAdapter = MalAnimeHorizontalAdapter(rankedList, MultiApiCallType.TopRanked, this)
        rankedAdapter?.setHasStableIds(true)
        binding.groupTopRanked.rvItems.setHasFixedSize(true)
        binding.groupTopRanked.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupTopRanked.rvItems.adapter = rankedAdapter
    }

    private fun setListeners() {
        binding.ivRefresh.setOnClickListener(this)
        binding.ivSearch.setOnClickListener(this)
        binding.groupPopular.tvViewAll.setOnClickListener(this)
        binding.groupFavourite.tvViewAll.setOnClickListener(this)
        binding.groupUpcoming.tvViewAll.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.ivRefresh -> onRefreshClick()
            binding.ivSearch -> {}
            binding.groupPopular.tvViewAll -> onViewClick(AnimeFilter.BY_POPULARITY)
            binding.groupFavourite.tvViewAll -> onViewClick(AnimeFilter.FAVORITE)
            binding.groupUpcoming.tvViewAll -> onViewClick(AnimeFilter.UPCOMING)
        }
    }

    private fun onViewClick(filter: AnimeFilter) {
        parentActivity?.navigateToFragment(JikanTopAnimeFragment.newInstance(filter))
    }

    private fun onRefreshClick() = if (!isLoading) {
        isRefreshed = true
        allApiCalls()
    } else {
        toast(getString(R.string.msg_please_wait))
    }

    private fun setAuthenticationView() {
        if (!isAuthenticated && binding.groupRecommended.group.isVisible()) {
            binding.groupRecommended.root.hide()
            binding.groupTopRanked.root.hide()
        } else if (isAuthenticated && binding.groupRecommended.root.isHidden()) {
            binding.groupRecommended.root.show()
            binding.groupTopRanked.root.show()
        }
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
        PrefUtils.getBoolean(Const.PrefKeys.IS_AUTHENTICATED_KEY).let {
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

                        MultiApiCallType.TopRanked -> handleAnimeSearchResponse(
                            key, response, ::rankedLoading
                        )

                        MultiApiCallType.TopRecommended -> handleAnimeSearchResponse(
                            key, response, ::recommendedLoading
                        )
                    }
                }
            }
        }
    }

    // Function to handle each type of response
    private inline fun handleAnimeSearchResponse(
        key: MultiApiCallType, response: Resource<Serializable>, loadingFunction: (Boolean) -> Unit
    ) {
        when (response) {
            is Resource.Success -> {
                when (key) {
                    MultiApiCallType.TopAiring -> setAiringData(response.data as AnimeSearchResponse)
                    MultiApiCallType.TopPopular -> setPopularData(response.data as AnimeSearchResponse)
                    MultiApiCallType.TopFavourite -> setFavouriteData(response.data as AnimeSearchResponse)
                    MultiApiCallType.TopUpcoming -> setUpcomingData(response.data as AnimeSearchResponse)
                    MultiApiCallType.TopRecommended -> setRecommendedData(response.data as MalMyAnimeListResponse)
                    MultiApiCallType.TopRanked -> setRankedData(response.data as MalMyAnimeListResponse)
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
        with(binding.groupPopular) {
            showLoaderOrShimmer(isLoading, group, shimmerLoader.root)
        }
    }

    private fun favouriteLoading(isLoading: Boolean) {
        with(binding.groupFavourite) {
            showLoaderOrShimmer(isLoading, group, shimmerLoader.root)
        }
    }

    private fun upcomingLoading(isLoading: Boolean) {
        with(binding.groupUpcoming) {
            showLoaderOrShimmer(isLoading, group, shimmerLoader.root)
        }
    }

    private fun recommendedLoading(isLoading: Boolean) {
        with(binding.groupRecommended) {
            showLoaderOrShimmer(isLoading, group, shimmerLoader.root)
        }
    }

    private fun rankedLoading(isLoading: Boolean) {
        with(binding.groupTopRanked) {
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
    private fun setAiringData(animeData: AnimeSearchResponse) {
        airingList.clear()
        animeData.data?.let { airingList.addAll(it) }
        airingAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setPopularData(animeData: AnimeSearchResponse) {
        popularList.clear()
        animeData.data?.let { popularList.addAll(it) }
        popularAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setFavouriteData(animeData: AnimeSearchResponse) {
        favouriteList.clear()
        animeData.data?.let { favouriteList.addAll(it) }
        favouriteAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpcomingData(animeData: AnimeSearchResponse) {
        upcomingList.clear()
        animeData.data?.let { upcomingList.addAll(it) }
        upcomingAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecommendedData(animeData: MalMyAnimeListResponse) {
        recommendedList.clear()
        animeData.data?.let { recommendedList.addAll(it) }
        recommendedAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRankedData(animeData: MalMyAnimeListResponse) {
        rankedList.clear()
        animeData.data?.let { rankedList.addAll(it) }
        rankedAdapter?.notifyDataSetChanged()
    }

    private fun allApiCalls() {
        viewModel.getAllAnimeData()
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
        fun newInstance() = AnimeHomeFragment()/*.apply {
            arguments = Bundle().apply {

            }
        }*/
    }

}
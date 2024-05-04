package com.abhinavdev.animeapp.ui.manga

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseFragment
import com.abhinavdev.animeapp.databinding.FragmentMangaBinding
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.models.malmodels.MalMangaData
import com.abhinavdev.animeapp.remote.models.malmodels.MalMyMangaListResponse
import com.abhinavdev.animeapp.remote.models.manga.MangaData
import com.abhinavdev.animeapp.remote.models.manga.MangaSearchResponse
import com.abhinavdev.animeapp.ui.anime.misc.MultiApiCallType
import com.abhinavdev.animeapp.ui.common.listeners.CustomClickMultiTypeCallback
import com.abhinavdev.animeapp.ui.common.ui.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.abhinavdev.animeapp.ui.main.MainActivity
import com.abhinavdev.animeapp.ui.manga.adapters.MalMangaHorizontalAdapter
import com.abhinavdev.animeapp.ui.manga.adapters.MangaBannerAdapter
import com.abhinavdev.animeapp.ui.manga.adapters.MangaHorizontalAdapter
import com.abhinavdev.animeapp.ui.manga.viewmodel.MangaViewModel
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

class MangaFragment : BaseFragment(), View.OnClickListener, CustomClickMultiTypeCallback {
    private var _binding: FragmentMangaBinding? = null
    private val binding get() = _binding!!
    private var parentActivity: MainActivity? = null

    private lateinit var viewModel: MangaViewModel

    private val airingList: ArrayList<MangaData> = arrayListOf()
    private var airingAdapter: MangaBannerAdapter? = null

    private val popularList: ArrayList<MangaData> = arrayListOf()
    private var popularAdapter: MangaHorizontalAdapter? = null

    private val favouriteList: ArrayList<MangaData> = arrayListOf()
    private var favouriteAdapter: MangaHorizontalAdapter? = null

    private val upcomingList: ArrayList<MangaData> = arrayListOf()
    private var upcomingAdapter: MangaHorizontalAdapter? = null

    private val rankedList: ArrayList<MalMangaData> = arrayListOf()
    private var rankedAdapter: MalMangaHorizontalAdapter? = null

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
        viewModel = createViewModel(MangaViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMangaBinding.inflate(layoutInflater, container, false)
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
        airingAdapter = MangaBannerAdapter(airingList, this)
        binding.svTopAiring.setIndicatorAnimation(IndicatorAnimationType.DROP)
        binding.svTopAiring.setSliderAdapter(airingAdapter!!)

        //second rv
        popularAdapter = MangaHorizontalAdapter(popularList, MultiApiCallType.TopPopular, this)
        popularAdapter?.setHasStableIds(true)
        binding.groupPopular.rvItems.setHasFixedSize(true)
        binding.groupPopular.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupPopular.rvItems.adapter = popularAdapter

        //third rv
        favouriteAdapter =
            MangaHorizontalAdapter(favouriteList, MultiApiCallType.TopFavourite, this)
        favouriteAdapter?.setHasStableIds(true)
        binding.groupFavourite.rvItems.setHasFixedSize(true)
        binding.groupFavourite.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupFavourite.rvItems.adapter = favouriteAdapter

        //fourth rv
        upcomingAdapter = MangaHorizontalAdapter(upcomingList, MultiApiCallType.TopUpcoming, this)
        upcomingAdapter?.setHasStableIds(true)
        binding.groupUpcoming.rvItems.setHasFixedSize(true)
        binding.groupUpcoming.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupUpcoming.rvItems.adapter = upcomingAdapter

        //fifth rv
        rankedAdapter = MalMangaHorizontalAdapter(rankedList, MultiApiCallType.TopRanked, this)
        rankedAdapter?.setHasStableIds(true)
        binding.groupTopRanked.rvItems.setHasFixedSize(true)
        binding.groupTopRanked.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupTopRanked.rvItems.adapter = rankedAdapter
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
        if (!isAuthenticated && binding.groupTopRanked.group.isVisible()) {
            binding.groupTopRanked.root.hide()
        } else if (isAuthenticated && binding.groupTopRanked.root.isHidden()) {
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
        viewModel.mangaAllApiResponse.observe(viewLifecycleOwner) { event ->
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

                        else -> {}
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
                    MultiApiCallType.TopAiring -> setAiringData(response.data as MangaSearchResponse)
                    MultiApiCallType.TopPopular -> setPopularData(response.data as MangaSearchResponse)
                    MultiApiCallType.TopFavourite -> setFavouriteData(response.data as MangaSearchResponse)
                    MultiApiCallType.TopUpcoming -> setUpcomingData(response.data as MangaSearchResponse)
                    MultiApiCallType.TopRanked -> setRankedData(response.data as MalMyMangaListResponse)
                    else -> {}
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
    private fun setAiringData(animeData: MangaSearchResponse) {
        airingList.clear()
        animeData.data?.let { airingList.addAll(it) }
        airingAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setPopularData(animeData: MangaSearchResponse) {
        popularList.clear()
        animeData.data?.let { popularList.addAll(it) }
        popularAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setFavouriteData(animeData: MangaSearchResponse) {
        favouriteList.clear()
        animeData.data?.let { favouriteList.addAll(it) }
        favouriteAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpcomingData(animeData: MangaSearchResponse) {
        upcomingList.clear()
        animeData.data?.let { upcomingList.addAll(it) }
        upcomingAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRankedData(animeData: MalMyMangaListResponse) {
        rankedList.clear()
        animeData.data?.let { rankedList.addAll(it) }
        rankedAdapter?.notifyDataSetChanged()
    }

    private fun allApiCalls() {
        viewModel.getAllMangaData()
    }

    override fun <T> onItemClick(position: Int, type: T) {
        val apiType = type as MultiApiCallType
        when (apiType) {
            MultiApiCallType.TopAiring -> toast("Publishing")
            MultiApiCallType.TopPopular -> toast("Popular")
            MultiApiCallType.TopFavourite -> toast("Favourite")
            MultiApiCallType.TopUpcoming -> toast("Upcoming")
            MultiApiCallType.TopRanked -> toast("Ranked")
            else -> {}
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MangaFragment()
    }
}
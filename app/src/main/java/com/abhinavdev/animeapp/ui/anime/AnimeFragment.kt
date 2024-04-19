package com.abhinavdev.animeapp.ui.anime

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseFragment
import com.abhinavdev.animeapp.databinding.FragmentAnimeBinding
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.models.anime.AnimeData
import com.abhinavdev.animeapp.remote.models.anime.AnimeSearchResponse
import com.abhinavdev.animeapp.ui.anime.adapters.AnimeBannerAdapter
import com.abhinavdev.animeapp.ui.anime.adapters.AnimeVerticalCardAdapter
import com.abhinavdev.animeapp.ui.anime.misc.MultiApiCallType
import com.abhinavdev.animeapp.ui.anime.viewmodel.AnimeViewModel
import com.abhinavdev.animeapp.ui.common.listeners.CustomClickCallback
import com.abhinavdev.animeapp.ui.common.ui.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.abhinavdev.animeapp.ui.main.MainActivity
import com.abhinavdev.animeapp.util.appsettings.SettingsPrefs
import com.abhinavdev.animeapp.util.extension.getDisplaySize
import com.abhinavdev.animeapp.util.extension.showOrInvisible
import com.abhinavdev.animeapp.util.extension.toast

class AnimeFragment : BaseFragment(), View.OnClickListener, CustomClickCallback {
    private var _binding: FragmentAnimeBinding? = null
    private val binding get() = _binding!!
    private var parentActivity: MainActivity? = null

    private lateinit var viewModel: AnimeViewModel

    private val bannerList: ArrayList<AnimeData> = arrayListOf()
    private var bannerAdapter: AnimeBannerAdapter? = null

    private val popularList: ArrayList<AnimeData> = arrayListOf()
    private var popularAdapter: AnimeVerticalCardAdapter? = null

    private val favouriteList: ArrayList<AnimeData> = arrayListOf()
    private var favouriteAdapter: AnimeVerticalCardAdapter? = null

    private val upcomingList: ArrayList<AnimeData> = arrayListOf()
    private var upcomingAdapter: AnimeVerticalCardAdapter? = null

    private var isLoading = false

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
        viewModel = ViewModelProvider(this)[AnimeViewModel::class.java]
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
        setAllTitles()
        setTopViewPagerHeight()
    }

    private fun setAllTitles() {
        binding.groupPopularAnime.tvHeading.text = getString(R.string.msg_most_popular)
        binding.groupFavouriteAnime.tvHeading.text = getString(R.string.msg_top_favourite)
        binding.groupUpcomingAnime.tvHeading.text = getString(R.string.msg_upcoming)
    }

    private fun setTopViewPagerHeight() {
        val screenSize = getDisplaySize(requireActivity())
        if (screenSize.width > screenSize.height) {
            binding.svTopAiring.layoutParams.height = (screenSize.height * 8) / 9
        } else {
            binding.svTopAiring.layoutParams.height = (screenSize.width * 8) / 9
        }
        binding.svTopAiring.requestLayout()
    }

    private fun setAdapters() {
        //top airing in top auto image slider
        bannerAdapter = AnimeBannerAdapter(bannerList, this)
        binding.svTopAiring.setIndicatorAnimation(IndicatorAnimationType.DROP)
        binding.svTopAiring.setSliderAdapter(bannerAdapter!!)

        //second rv
        popularAdapter = AnimeVerticalCardAdapter(popularList, MultiApiCallType.TopPopular, this)
        binding.groupPopularAnime.rvItems.setHasFixedSize(true)
        binding.groupPopularAnime.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupPopularAnime.rvItems.adapter = popularAdapter

        //third rv
        favouriteAdapter =
            AnimeVerticalCardAdapter(favouriteList, MultiApiCallType.TopFavourite, this)
        binding.groupFavouriteAnime.rvItems.setHasFixedSize(true)
        binding.groupFavouriteAnime.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupFavouriteAnime.rvItems.adapter = favouriteAdapter

        //fourth rv
        upcomingAdapter = AnimeVerticalCardAdapter(upcomingList, MultiApiCallType.TopUpcoming, this)
        binding.groupUpcomingAnime.rvItems.setHasFixedSize(true)
        binding.groupUpcomingAnime.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupUpcomingAnime.rvItems.adapter = upcomingAdapter
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
        allApiCalls()
    } else {
        toast(getString(R.string.msg_please_wait))
    }

    private fun setObservers() {
        viewModel.animeAllApiResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { apiMap ->
                apiMap.forEach { (key, response) ->
                    when (key) {
                        MultiApiCallType.TopAiring -> handleResponse(
                            key, response, ::airingLoading
                        )

                        MultiApiCallType.TopPopular -> handleResponse(
                            key, response, ::popularLoading
                        )

                        MultiApiCallType.TopFavourite -> handleResponse(
                            key, response, ::favouriteLoading
                        )

                        MultiApiCallType.TopUpcoming -> handleResponse(
                            key, response, ::upcomingLoading
                        )
                    }
                }
            }
        }
    }

    // Function to handle each type of response
    private inline fun handleResponse(
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
        binding.svTopAiring.showOrInvisible(!isLoading)
        binding.shimmerViewPager.showOrInvisible(isLoading)
    }

    private fun popularLoading(isLoading: Boolean) {
        binding.groupPopularAnime.group.showOrInvisible(!isLoading)
        binding.groupPopularAnime.shimmerLoader.root.showOrInvisible(isLoading)
    }

    private fun favouriteLoading(isLoading: Boolean) {
        binding.groupFavouriteAnime.group.showOrInvisible(!isLoading)
        binding.groupFavouriteAnime.shimmerLoader.root.showOrInvisible(isLoading)
    }

    private fun upcomingLoading(isLoading: Boolean) {
        binding.groupUpcomingAnime.group.showOrInvisible(!isLoading)
        binding.groupUpcomingAnime.shimmerLoader.root.showOrInvisible(isLoading)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setAiringData(animeData: ArrayList<AnimeData>) {
        bannerList.clear()
        bannerList.addAll(animeData)
        bannerAdapter?.notifyDataSetChanged()
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

    private fun allApiCalls() {
        val sfw = SettingsPrefs.isSfwEnabled

        viewModel.getAllAnimeData(sfw)
    }

    override fun <T> onItemClick(position: Int, type: T) {
        val apiType = type as MultiApiCallType
        when (apiType) {
            MultiApiCallType.TopAiring -> toast("Airing")
            MultiApiCallType.TopPopular -> toast("Popular")
            MultiApiCallType.TopFavourite -> toast("Favourite")
            MultiApiCallType.TopUpcoming -> toast("Upcoming")
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
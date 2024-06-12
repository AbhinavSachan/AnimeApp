package com.abhinavdev.animeapp.ui.anime

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseFragment
import com.abhinavdev.animeapp.databinding.FragmentSearchBinding
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.models.anime.AnimeSearchResponse
import com.abhinavdev.animeapp.ui.anime.misc.AdapterType
import com.abhinavdev.animeapp.ui.anime.viewmodel.AnimeViewModel
import com.abhinavdev.animeapp.ui.main.MainActivity
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.ViewUtil
import com.abhinavdev.animeapp.util.extension.applyDimen
import com.abhinavdev.animeapp.util.extension.createViewModel
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.toast
import com.abhinavdev.animeapp.util.ui.PaginationViewHelper

class SearchAnimeFragment : BaseFragment(), View.OnClickListener {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var parentActivity: MainActivity? = null
    private lateinit var viewModel: AnimeViewModel

    private var gridOrList: AdapterType = AdapterType.GRID

    private var isFromSwipe = false

    private var page = 1
    private var limit = SettingsHelper.getJikanListLimit()
    private val isFirstPage get() = page == 1
    private var paginationHelper: PaginationViewHelper? = null
    private var shouldScrollToTop: Boolean = false

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
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
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
        getSearchResult(false)
    }

    private fun initComponents() {
        with(binding.toolbar) {
            ivBack.hide()
            tvTitle.text = getString(R.string.msg_search_anime)
            val viewIcon = when (gridOrList) {
                AdapterType.GRID -> R.drawable.ic_list_view
                AdapterType.LIST -> R.drawable.ic_grid_view
            }
            ivExtra.show()
            ivExtra.setImageResource(viewIcon)

            ViewUtil.setOnApplyUiInsetsListener(root) { insets ->
                ViewUtil.setTopPadding(root, insets.top)
            }
        }
        val rvBPadding = binding.rvList.paddingBottom
        val bottomBarHeight = applyDimen(R.dimen.cbn_height)
        val salt = applyDimen(R.dimen.bottom_bar_height_salt)
        ViewUtil.setOnApplyUiInsetsListener(binding.rvList) { insets ->
            ViewUtil.setBottomPadding(
                binding.rvList, insets.bottom + rvBPadding + bottomBarHeight + salt
            )
        }
        ViewUtil.setOnApplyUiInsetsListener(binding.groupPagination.clPagination) { insets ->
            ViewUtil.setBottomPadding(
                binding.groupPagination.clPagination, insets.bottom + bottomBarHeight + salt
            )
        }
    }

    private fun setAdapters() {

    }

    private fun setListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            getSearchResult(true)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
        }
    }

    private fun setObservers() {
        viewModel.searchAnimeResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let {
                            setData(it)
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

    private fun setData(data: AnimeSearchResponse) {

    }

    private fun isLoaderVisible(b: Boolean) {
        if (isFromSwipe && !b) {
            binding.swipeRefresh.isRefreshing = false
        } else if (!isFromSwipe) {
            parentActivity?.isLoaderVisible(b)
        }
    }

    private fun getSearchResult(fromSwipe: Boolean){
        isFromSwipe = fromSwipe
        viewModel.getAnimeBySearch(page = page, limit = limit)
    }

    private fun commonFetchListAfterOptionChange() {
        shouldScrollToTop = true
        getSearchResult(false)
    }

    private fun increaseOffset() {
        page += 1
    }

    private fun decreaseOffset() {
        if (page != 1) page -= 1
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchAnimeFragment()
    }
}
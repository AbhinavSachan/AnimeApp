package com.abhinavdev.animeapp.ui.anime

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseFragment
import com.abhinavdev.animeapp.databinding.FragmentSearchBinding
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.models.anime.AnimeData
import com.abhinavdev.animeapp.remote.models.enums.AgeRating
import com.abhinavdev.animeapp.remote.models.enums.AnimeOrderBy
import com.abhinavdev.animeapp.remote.models.enums.AnimeStatus
import com.abhinavdev.animeapp.remote.models.enums.AnimeType
import com.abhinavdev.animeapp.remote.models.enums.SortOrder
import com.abhinavdev.animeapp.ui.anime.adapters.AnimeVerticalAdapter
import com.abhinavdev.animeapp.ui.anime.misc.AdapterType
import com.abhinavdev.animeapp.ui.anime.viewmodel.AnimeViewModel
import com.abhinavdev.animeapp.ui.common.listeners.OnAdapterItemClickListener
import com.abhinavdev.animeapp.ui.common.ui.FilterDialogActivity
import com.abhinavdev.animeapp.ui.main.MainActivity
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.PrefUtils
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.ViewUtil
import com.abhinavdev.animeapp.util.extension.applyDimen
import com.abhinavdev.animeapp.util.extension.createViewModel
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.showOrHide
import com.abhinavdev.animeapp.util.extension.toast
import com.abhinavdev.animeapp.util.ui.PaginationViewHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchAnimeFragment : BaseFragment(), View.OnClickListener, OnAdapterItemClickListener {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var parentActivity: MainActivity? = null
    private lateinit var viewModel: AnimeViewModel

    private var gridOrList: AdapterType = AdapterType.GRID

    private val animeList: ArrayList<AnimeData> = arrayListOf()
    private var adapter: AnimeVerticalAdapter? = null

    private var isFromSwipe = false

    private var page = 1
    private var limit = SettingsHelper.getJikanListLimit()
    private val isFirstPage get() = page == 1
    private var lastPage = 1
    private var paginationHelper: PaginationViewHelper? = null
    private var pickPageDialog: BottomSheetDialog? = null
    private var shouldScrollToTop: Boolean = false

    private var isUnapprovedChecked = false
    private var animeType = AnimeType.ALL
    private var animeStatus = AnimeStatus.ALL
    private var ageRating = AgeRating.NONE
    private var animeOrderBy = AnimeOrderBy.POPULARITY
    private var sortType = SortOrder.ASCENDING
    private var startDate = ""
    private var endDate = ""
    private var query = ""
    private var letter = ""

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
        paginationHelper = context?.let { PaginationViewHelper(binding.groupPagination, it) }
        gridOrList = AdapterType.valueOfOrDefault(PrefUtils.getInt(Const.PrefKeys.GRID_OR_LIST_KEY))
        with(binding.toolbar) {
            ivBack.hide()
            tvTitle.text = getString(R.string.msg_search_anime)
            val viewIcon = when (gridOrList) {
                AdapterType.GRID -> R.drawable.ic_list_view
                AdapterType.LIST -> R.drawable.ic_grid_view
            }
            ivExtra.show()
            ivExtra.setImageResource(viewIcon)

            ivExtraTwo.show()
            ivExtraTwo.setImageResource(R.drawable.ic_filter)

            ViewUtil.setOnApplyUiInsetsListener(root) { insets ->
                ViewUtil.setTopPadding(root, insets.top)
            }
        }
        val rvBPadding = applyDimen(R.dimen.recycler_view_bottom_padding_for_programmatically)
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

    private fun updatePageNo() {
        //in mal api's we have to send offset but in jikan page no that's why we are adding one to show correct page no
        paginationHelper?.setPageText(page)
    }

    private fun setAdapters() {
        adapter = AnimeVerticalAdapter(animeList, this)
        adapter?.setHasStableIds(true)
        toggleAdapterType(gridOrList)
        binding.rvList.setHasFixedSize(Const.Other.HAS_FIXED_SIZE)
        binding.rvList.adapter = adapter
    }

    private fun toggleAdapterType(gridOrList: AdapterType) {
        when (gridOrList) {
            AdapterType.GRID -> {
                binding.rvList.layoutManager = GridLayoutManager(context, 2)
            }

            AdapterType.LIST -> {
                binding.rvList.layoutManager = LinearLayoutManager(context)
            }
        }
        adapter?.setAdapterType(gridOrList)
        binding.rvList.adapter = adapter

        isLoaderVisible(false)
    }

    private fun setListeners() {
        binding.toolbar.ivExtra.setOnClickListener(this)
        binding.toolbar.ivExtraTwo.setOnClickListener(this)
        binding.swipeRefresh.setOnRefreshListener {
            getSearchResult(true)
        }
        paginationHelper?.onPreviousPageClick { onPreviousClick() }
        paginationHelper?.onNextPageClick { onNextClick() }
        paginationHelper?.onEditPageClick { onEditPageNoClick() }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.toolbar.ivExtra -> toggleViewType()
            binding.toolbar.ivExtraTwo -> onFilterClick()
        }
    }

    private fun onFilterClick() {
        val intent = FilterDialogActivity.createSelectItemBottomSheet(context = context,
            isAnime = true,
            isUnapproved = isUnapprovedChecked,
            query = query.takeIf { it.isNotEmpty() } ?: letter,
            type = animeType.search,
            status = animeStatus.search,
            ageRating = ageRating.search,
            orderBy = animeOrderBy.search,
            sortBy = sortType.search,
            startDate = startDate,
            endDate = endDate)
        bottomSheetCloseCallback.launch(intent)
    }

    private fun onSubmitFilter(data: Intent) {
        val withResult = data.getBooleanExtra(Const.BundleExtras.EXTRA_WITH_RESULT, false)
        if (withResult) {
            isUnapprovedChecked = data.getBooleanExtra(Const.BundleExtras.EXTRA_IS_APPROVED, false)
            animeType =
                AnimeType.valueOfOrDefault(data.getStringExtra(Const.BundleExtras.EXTRA_TYPE))
            animeStatus =
                AnimeStatus.valueOfOrDefault(data.getStringExtra(Const.BundleExtras.EXTRA_STATUS))
            ageRating =
                AgeRating.valueOfOrDefault(data.getStringExtra(Const.BundleExtras.EXTRA_AGE_RATING))
            animeOrderBy =
                AnimeOrderBy.valueOfOrDefault(data.getStringExtra(Const.BundleExtras.EXTRA_ORDER_BY))
            sortType =
                SortOrder.valueOfOrDefault(data.getStringExtra(Const.BundleExtras.EXTRA_SORT))
            startDate = data.getStringExtra(Const.BundleExtras.EXTRA_START_DATE) ?: ""
            endDate = data.getStringExtra(Const.BundleExtras.EXTRA_END_DATE) ?: ""
            val q = data.getStringExtra(Const.BundleExtras.EXTRA_QUERY) ?: ""
            if (q.length == 1) {
                letter = q
                query = ""
            } else {
                query = q
                letter = ""
            }
        }
        val clearAll = data.getBooleanExtra(Const.BundleExtras.EXTRA_CLEAR_ALL, false)
        if (clearAll) {
            resetFilters()
        }
        if (withResult) {
            runPostOptionClick()
        }
    }

    private fun resetFilters() {
        isUnapprovedChecked = false
        animeType = AnimeType.ALL
        animeStatus = AnimeStatus.ALL
        ageRating = AgeRating.NONE
        animeOrderBy = AnimeOrderBy.POPULARITY
        sortType = SortOrder.ASCENDING
        startDate = ""
        endDate = ""
        query = ""
        letter = ""
    }

    private var bottomSheetCloseCallback =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { onSubmitFilter(it) }
            }
        }

    private fun toggleViewType() {
        binding.rvList.hide()
        isLoaderVisible(true)
        CoroutineScope(Dispatchers.IO).launch {
            val viewIcon = when (gridOrList) {
                AdapterType.GRID -> {
                    gridOrList = AdapterType.LIST
                    R.drawable.ic_grid_view
                }

                AdapterType.LIST -> {
                    gridOrList = AdapterType.GRID
                    R.drawable.ic_list_view
                }
            }
            PrefUtils.setInt(Const.PrefKeys.GRID_OR_LIST_KEY, gridOrList.value)
            CoroutineScope(Dispatchers.Main).launch {
                binding.toolbar.ivExtra.setImageResource(viewIcon)
                toggleAdapterType(gridOrList)
                binding.rvList.show()
            }
        }
    }

    private fun setObservers() {
        viewModel.searchAnimeResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.data?.let {
                            setData(it)
                        }
                        isLoaderVisible(false)
                        val hasNext = response.data?.pagination?.hasNextPage ?: false
                        lastPage = response.data?.pagination?.lastVisiblePage ?: 1
                        updatePageNo()
                        paginationHelper?.setEditButtonVisible(true)
                        //if this is not the first page then enable previous button
                        paginationHelper?.setPreviousButtonEnabled(!isFirstPage)
                        //if api has next page then enable next button
                        paginationHelper?.setNextButtonEnabled(hasNext)
                        //if first page then check if list is empty
                        if (isFirstPage) {
                            showEmptyLayout(false)
                        }
                    }

                    is Resource.Error -> {
                        isLoaderVisible(false)
                        showEmptyLayout(true)
                        response.message?.let { message -> toast(message) }
                    }

                    is Resource.Loading -> {
                        isLoaderVisible(true)
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setData(data: ArrayList<AnimeData>) {
        animeList.clear()
        animeList.addAll(data)
        adapter?.notifyDataSetChanged()
        if (shouldScrollToTop) {
            scrollToTopOrPosition()
            shouldScrollToTop = false
        }
    }

    private fun scrollToTopOrPosition(position: Int = 0) {
        binding.rvList.scrollToPosition(position)
    }

    private fun isLoaderVisible(b: Boolean) {
        if (isFromSwipe && !b) {
            binding.swipeRefresh.isRefreshing = false
        } else if (!isFromSwipe) {
            parentActivity?.isLoaderVisible(b)
        }
    }

    private fun showEmptyLayout(isError: Boolean) {
        val isListEmpty = animeList.isEmpty()
        with(binding.emptyLayout) {
            if (isListEmpty) {
                val imageRes = if (isError) {
                    tvEmptyTitle.text = getString(R.string.error_something_went_wrong)
                    tvEmptyDesc.text = getString(R.string.msg_empty_error_des)
                    R.drawable.bg_error
                } else {
                    tvEmptyTitle.text = getString(R.string.msg_list_empty)
                    tvEmptyDesc.text = getString(R.string.msg_empty_seach_list_des)
                    R.drawable.bg_empty_search_list
                }
                ivEmptyIcon.setImageResource(imageRes)
            }
        }
        binding.rvList.showOrHide(!isListEmpty)
        binding.emptyLayout.root.showOrHide(isListEmpty)
    }

    private fun getSearchResult(fromSwipe: Boolean) {
        isFromSwipe = fromSwipe
        viewModel.getAnimeBySearch(
            page = page,
            limit = limit,
            unapproved = isUnapprovedChecked,
            query = query,
            type = animeType,
            score = null,
            minScore = null,
            maxScore = null,
            status = animeStatus,
            rating = ageRating,
            genres = "",
            genresExclude = "",
            orderBy = animeOrderBy,
            sort = sortType,
            letter = letter,
            producers = "",
            startDate = startDate,
            endDate = endDate
        )
    }

    override fun onItemClick(position: Int, type: String?) {
        val animeId = animeList[position].malId
        parentActivity?.navigateToFragment(AnimeDetailsFragment.newInstance(animeId))
    }

    private fun commonFetchListAfterOptionChange() {
        shouldScrollToTop = true
        getSearchResult(false)
    }

    private fun runPostOptionClick() {
        page = 1
        commonFetchListAfterOptionChange()
    }

    private fun onNextClick() {
        increaseOffset()
        commonFetchListAfterOptionChange()
    }

    private fun onPreviousClick() {
        decreaseOffset()
        commonFetchListAfterOptionChange()
    }

    private fun onEditPageNoClick() {
        pickPageDialog = paginationHelper?.createEditPageDialog(lastPage) {
            page = it
            commonFetchListAfterOptionChange()
        }
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
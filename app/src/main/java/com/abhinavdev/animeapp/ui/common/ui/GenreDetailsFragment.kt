package com.abhinavdev.animeapp.ui.common.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseFragment
import com.abhinavdev.animeapp.databinding.DialogOptionsBinding
import com.abhinavdev.animeapp.databinding.FragmentGenreDetailsBinding
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.models.anime.AnimeData
import com.abhinavdev.animeapp.remote.models.enums.AgeRating
import com.abhinavdev.animeapp.remote.models.enums.AnimeOrderBy
import com.abhinavdev.animeapp.remote.models.enums.AnimeStatus
import com.abhinavdev.animeapp.remote.models.enums.AnimeType
import com.abhinavdev.animeapp.remote.models.enums.Genre
import com.abhinavdev.animeapp.remote.models.enums.MangaOrderBy
import com.abhinavdev.animeapp.remote.models.enums.MangaStatus
import com.abhinavdev.animeapp.remote.models.enums.MangaType
import com.abhinavdev.animeapp.remote.models.enums.SortOrder
import com.abhinavdev.animeapp.remote.models.manga.MangaData
import com.abhinavdev.animeapp.ui.anime.AnimeDetailsFragment
import com.abhinavdev.animeapp.ui.anime.adapters.AnimeVerticalAdapter
import com.abhinavdev.animeapp.ui.anime.misc.AdapterType
import com.abhinavdev.animeapp.ui.anime.viewmodel.AnimeViewModel
import com.abhinavdev.animeapp.ui.common.listeners.OnAdapterItemClickListener
import com.abhinavdev.animeapp.ui.common.listeners.OnClickMultiTypeCallback
import com.abhinavdev.animeapp.ui.main.MainActivity
import com.abhinavdev.animeapp.ui.manga.adapters.MangaVerticalAdapter
import com.abhinavdev.animeapp.ui.manga.viewmodel.MangaViewModel
import com.abhinavdev.animeapp.ui.models.ItemSelectionModelBase
import com.abhinavdev.animeapp.ui.more.adapters.ItemSelectionAdapter
import com.abhinavdev.animeapp.ui.more.adapters.setOptionSelected
import com.abhinavdev.animeapp.ui.more.misc.ListOptionsType
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.PrefUtils
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.ViewUtil
import com.abhinavdev.animeapp.util.extension.applyDimen
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.showOrHide
import com.abhinavdev.animeapp.util.extension.toast
import com.abhinavdev.animeapp.util.ui.PaginationViewHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenreDetailsFragment : BaseFragment(), View.OnClickListener, OnAdapterItemClickListener,
    OnClickMultiTypeCallback {
    private var _binding: FragmentGenreDetailsBinding? = null
    private val binding get() = _binding!!
    private var parentActivity: MainActivity? = null

    private val viewModel by viewModels<AnimeViewModel>()
    private val mangaViewModel by viewModels<MangaViewModel>()

    private var genreList = listOf<ItemSelectionModelBase>()

    private var gridOrList: AdapterType = AdapterType.GRID

    private val animeList: ArrayList<AnimeData> = arrayListOf()
    private var adapter: AnimeVerticalAdapter? = null

    private val mangaList: ArrayList<MangaData> = arrayListOf()
    private var mangaAdapter: MangaVerticalAdapter? = null

    private var isFromSwipe = false

    private var page = 1
    private var limit = SettingsHelper.getJikanListLimit()
    private val isFirstPage get() = page == 1
    private var lastPage = 1
    private var paginationHelper: PaginationViewHelper? = null
    private var pickPageDialog: BottomSheetDialog? = null
    private var shouldScrollToTop: Boolean = false

    private var selectedGenre = Genre.ALL
    private var isAnime = false

    private var optionAdapter: ItemSelectionAdapter<ListOptionsType>? = null
    private var optionBottomSheetDialog: BottomSheetDialog? = null

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
        gridOrList = AdapterType.valueOfOrDefault(PrefUtils.getInt(Const.PrefKeys.GRID_OR_LIST_KEY))
        isAnime = arguments?.getBoolean(Const.BundleExtras.EXTRA_IS_ANIME, false) ?: false
        val id = arguments?.getInt(Const.BundleExtras.EXTRA_ID)
        selectedGenre =
            if (isAnime) Genre.valueOfOrDefaultAnime(id) else Genre.valueOfOrDefaultManga(id)

        val sfw = SettingsHelper.getSfwEnabled()
        genreList = if (isAnime) {
            Genre.listAnime(sfw).map {
                ItemSelectionModelBase(it.animeId.toString(), it.showName).apply {
                    isSelected = selectedGenre == it
                }
            }
        } else {
            Genre.listManga(sfw).map {
                ItemSelectionModelBase(it.mangaId.toString(), it.showName).apply {
                    isSelected = selectedGenre == it
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenreDetailsBinding.inflate(layoutInflater, container, false)
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
        getList(false)
    }

    private fun initComponents() {
        paginationHelper = context?.let { PaginationViewHelper(binding.groupPagination, it) }
        with(binding.toolbar) {
            ivBack.hide()
            tvTitle.text = getString(R.string.msg_genres_details)
            val viewIcon = when (gridOrList) {
                AdapterType.GRID -> R.drawable.ic_list_view
                AdapterType.LIST -> R.drawable.ic_grid_view
            }
            ivExtra.show()
            ivExtra.setImageResource(viewIcon)
        }
        val bottomBarHeight = applyDimen(R.dimen.cbn_height)
        val salt = applyDimen(R.dimen.bottom_bar_height_salt)
        ViewUtil.setOnApplyUiInsetsListener(binding.root) { insets ->
            ViewUtil.setTopPadding(binding.toolbar.root, insets.top)
            ViewUtil.setBottomPadding(binding.rvList, insets.bottom + bottomBarHeight + salt)
        }
        with(binding) {
            groupGenre.tvItemLabel.text = getString(R.string.msg_filter_by_genre)

            groupGenre.tvItem.text = selectedGenre.showName
        }
    }

    private fun updatePageNo() {
        //in mal api's we have to send offset but in jikan page no that's why we are adding one to show correct page no
        paginationHelper?.setPageText(page)
    }

    private fun setAdapters() {
        if (isAnime) {
            adapter = AnimeVerticalAdapter(animeList, this)
            adapter?.setHasStableIds(true)
            toggleAdapterType(gridOrList)
            binding.rvList.setHasFixedSize(Const.Other.HAS_FIXED_SIZE)
            binding.rvList.adapter = adapter
        } else {
            mangaAdapter = MangaVerticalAdapter(mangaList, this)
            adapter?.setHasStableIds(true)
            toggleAdapterType(gridOrList)
            binding.rvList.setHasFixedSize(Const.Other.HAS_FIXED_SIZE)
            binding.rvList.adapter = mangaAdapter
        }
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
        binding.toolbar.ivBack.setOnClickListener(this)
        binding.toolbar.ivExtra.setOnClickListener(this)
        binding.groupGenre.llItem.setOnClickListener(this)
        binding.swipeRefresh.setOnRefreshListener {
            getList(true)
        }
        paginationHelper?.onPreviousPageClick { onPreviousClick() }
        paginationHelper?.onNextPageClick { onNextClick() }
        paginationHelper?.onEditPageClick { onEditPageNoClick() }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.toolbar.ivBack -> parentActivity?.onBackPressedDispatcher?.onBackPressed()
            binding.toolbar.ivExtra -> toggleViewType()
            binding.groupGenre.llItem -> openOptionDialog(genreList, ListOptionsType.TYPE)
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

    private fun openOptionDialog(list: List<ItemSelectionModelBase>, type: ListOptionsType) {
        optionBottomSheetDialog =
            BottomSheetDialog(requireContext(), R.style.NoBackGroundBottomSheetDialog)
        val view = DialogOptionsBinding.inflate(layoutInflater)

        with(view) {
            val title = when (type) {
                ListOptionsType.TYPE -> R.string.msg_choose_type
                ListOptionsType.STATUS -> R.string.msg_choose_status
                ListOptionsType.AGE -> R.string.msg_choose_age_rating
                else -> 0
            }
            tvTitle.text = getString(title)
            optionAdapter = ItemSelectionAdapter(list, this@GenreDetailsFragment, type)
            rvItems.setHasFixedSize(true)
            rvItems.layoutManager = LinearLayoutManager(context)
            rvItems.adapter = optionAdapter
        }

        optionBottomSheetDialog?.setContentView(view.root)
        optionBottomSheetDialog?.show()
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
        mangaViewModel.searchMangaResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.data?.let {
                            setMangaData(it)
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

    @SuppressLint("NotifyDataSetChanged")
    private fun setMangaData(data: ArrayList<MangaData>) {
        mangaList.clear()
        mangaList.addAll(data)
        mangaAdapter?.notifyDataSetChanged()
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
        val isListEmpty = if (isAnime) animeList.isEmpty() else mangaList.isEmpty()
        with(binding.emptyLayout) {
            if (isListEmpty) {
                val imageRes = if (isError) {
                    tvEmptyTitle.text = getString(R.string.error_something_went_wrong)
                    tvEmptyDesc.text = getString(R.string.msg_empty_error_des)
                    R.drawable.bg_error
                } else {
                    tvEmptyTitle.text = getString(R.string.msg_list_empty)
                    tvEmptyDesc.text = getString(R.string.msg_empty_list_des)
                    R.drawable.bg_empty_list
                }
                ivEmptyIcon.setImageResource(imageRes)
            }
        }
        binding.rvList.showOrHide(!isListEmpty)
        binding.emptyLayout.root.showOrHide(isListEmpty)
    }

    private fun getList(fromSwipe: Boolean) {
        isFromSwipe = fromSwipe
        val genreIds =
            if (isAnime) selectedGenre.animeId.toString() else selectedGenre.mangaId.toString()

        if (isAnime) {
            viewModel.getAnimeBySearch(
                page = page,
                limit = limit,
                unapproved = false,
                query = "",
                type = AnimeType.ALL,
                score = null,
                minScore = null,
                maxScore = null,
                status = AnimeStatus.ALL,
                rating = AgeRating.NONE,
                genres = genreIds,
                genresExclude = "",
                orderBy = AnimeOrderBy.POPULARITY,
                sort = SortOrder.ASCENDING,
                letter = "",
                producers = "",
                startDate = "",
                endDate = ""
            )
        } else {
            mangaViewModel.getMangaBySearch(
                page = page,
                limit = limit,
                unapproved = false,
                query = "",
                type = MangaType.ALL,
                score = null,
                minScore = null,
                maxScore = null,
                status = MangaStatus.ALL,
                genres = genreIds,
                genresExclude = "",
                orderBy = MangaOrderBy.POPULARITY,
                sort = SortOrder.ASCENDING,
                letter = "",
                magazines = "",
                startDate = "",
                endDate = ""
            )
        }
    }

    override fun onItemClick(position: Int, type: String?) {
        val animeId = animeList[position].malId
        parentActivity?.navigateToFragment(AnimeDetailsFragment.newInstance(animeId))
    }

    override fun <T> onItemClick(position: Int, type: T) {
        genreList.setOptionSelected(position) {
            binding.groupGenre.tvItem.text = it.name
            val id = it.id.toIntOrNull()
            selectedGenre =
                if (isAnime) Genre.valueOfOrDefaultAnime(id) else Genre.valueOfOrDefaultManga(id)
            runPostOptionClick()
        }
    }

    private fun runPostOptionClick() {
        page = 1
        optionBottomSheetDialog?.cancel()
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

    private fun commonFetchListAfterOptionChange() {
        shouldScrollToTop = true
        getList(false)
    }

    private fun increaseOffset() {
        page += 1
    }

    private fun decreaseOffset() {
        if (page != 1) page -= 1
    }

    companion object {
        @JvmStatic
        fun newInstance(isAnime: Boolean, genre: Genre = Genre.ALL) = GenreDetailsFragment().apply {
            arguments = Bundle().apply {
                putBoolean(Const.BundleExtras.EXTRA_IS_ANIME, isAnime)
                putInt(Const.BundleExtras.EXTRA_ID, if (isAnime) genre.animeId else genre.mangaId)
            }
        }
    }
}
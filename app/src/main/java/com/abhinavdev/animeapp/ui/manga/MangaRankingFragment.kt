package com.abhinavdev.animeapp.ui.manga

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseFragment
import com.abhinavdev.animeapp.databinding.DialogOptionsBinding
import com.abhinavdev.animeapp.databinding.FragmentMangaRankingBinding
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.models.enums.MalMangaType
import com.abhinavdev.animeapp.remote.models.malmodels.MalMangaData
import com.abhinavdev.animeapp.ui.anime.misc.AdapterType
import com.abhinavdev.animeapp.ui.anime.misc.MultiContentAdapterType
import com.abhinavdev.animeapp.ui.common.listeners.CustomClickListener
import com.abhinavdev.animeapp.ui.common.listeners.OnClickMultiTypeCallback
import com.abhinavdev.animeapp.ui.main.MainActivity
import com.abhinavdev.animeapp.ui.manga.adapters.MalMangaVerticalAdapter
import com.abhinavdev.animeapp.ui.manga.viewmodel.MangaViewModel
import com.abhinavdev.animeapp.ui.models.ItemSelectionModelBase
import com.abhinavdev.animeapp.ui.more.adapters.ItemSelectionAdapter
import com.abhinavdev.animeapp.ui.more.adapters.setOptionSelected
import com.abhinavdev.animeapp.ui.more.misc.ListOptionsType
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.PrefUtils
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.ViewUtil
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

class MangaRankingFragment : BaseFragment(), View.OnClickListener, CustomClickListener,
    OnClickMultiTypeCallback {
    private var _binding: FragmentMangaRankingBinding? = null
    private val binding get() = _binding!!
    private var parentActivity: MainActivity? = null
    private lateinit var viewModel: MangaViewModel

    private var isFromSwipe = false
    private var shouldScrollToTop = false

    private var gridOrList: AdapterType = AdapterType.GRID

    private var mangaType = MalMangaType.ALL
    private var page = 1
    private var offset = 0
    private var limit = SettingsHelper.getRankingListLimit()
    private val isFirstPage get() = page == 1

    private val mangaList: ArrayList<MalMangaData> = arrayListOf()
    private var adapter: MalMangaVerticalAdapter? = null

    private var typeList: List<ItemSelectionModelBase> = arrayListOf()

    private var optionAdapter: ItemSelectionAdapter<ListOptionsType>? = null
    private var optionBottomSheetDialog: BottomSheetDialog? = null

    private var paginationHelper: PaginationViewHelper? = null

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
        _binding = FragmentMangaRankingBinding.inflate(layoutInflater, container, false)
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
        gridOrList = AdapterType.valueOfOrDefault(PrefUtils.getInt(Const.PrefKeys.GRID_OR_LIST_KEY))
        typeList = MalMangaType.list(SettingsHelper.getSfwEnabled()).map {
            ItemSelectionModelBase(it.search, it.showName).apply {
                isSelected = mangaType == it
            }
        }
        val padding = binding.rvList.paddingBottom
        ViewUtil.setOnApplyUiInsetsListener(binding.rvList) { insets ->
            ViewUtil.setBottomPadding(binding.rvList, padding + insets.bottom)
        }

        with(binding.toolbar) {
            tvTitle.text = getString(R.string.msg_top_ranked)
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

        with(binding) {
            groupType.tvItemLabel.text = getString(R.string.msg_filter_by)
            groupType.tvItem.text = mangaType.showName
        }
    }

    private fun updatePageNo() {
        //in mal api's we have to send offset but in jikan page no that's why we are adding one to show correct page no
        paginationHelper?.setPageText(page)
    }

    private fun setAdapters() {
        adapter = MalMangaVerticalAdapter(mangaList, this,MultiContentAdapterType.TopRanked)
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
        binding.toolbar.ivBack.setOnClickListener(this)
        binding.toolbar.ivExtra.setOnClickListener(this)
        binding.groupType.llItem.setOnClickListener(this)
        binding.swipeRefresh.setOnRefreshListener {
            getList(true)
        }
        paginationHelper?.onPreviousPageClick { onPreviousClick() }
        paginationHelper?.onNextPageClick { onNextClick() }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.toolbar.ivBack -> parentActivity?.onBackPressedDispatcher?.onBackPressed()
            binding.toolbar.ivExtra -> toggleViewType()
            binding.groupType.llItem -> openOptionDialog(typeList, ListOptionsType.TYPE)
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
                else -> 0
            }
            tvTitle.text = getString(title)
            optionAdapter = ItemSelectionAdapter(list, this@MangaRankingFragment, type)
            rvItems.setHasFixedSize(true)
            rvItems.layoutManager = LinearLayoutManager(context)
            rvItems.adapter = optionAdapter
        }

        optionBottomSheetDialog?.setContentView(view.root)
        optionBottomSheetDialog?.show()
    }

    private fun setObservers() {
        viewModel.mangaRankingResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.data?.let {
                            setData(it)
                        }
                        isLoaderVisible(false)
                        val hasNext = response.data?.paging?.next != null
                        updatePageNo()
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
    private fun setData(data: ArrayList<MalMangaData>) {
        mangaList.clear()
        mangaList.addAll(data)
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
        val isListEmpty = mangaList.isEmpty()
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
        viewModel.getMangaRanking(mangaType, offset, limit)
    }

    override fun onItemClick(position: Int) {

    }

    override fun <T> onItemClick(position: Int, type: T) {
        when (type as ListOptionsType) {
            ListOptionsType.TYPE -> {
                typeList.setOptionSelected(position) {
                    binding.groupType.tvItem.text = it.name
                    mangaType = MalMangaType.valueOfOrDefault(it.id)
                    runPostOptionClick()
                }
            }

            else -> {}
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun runPostOptionClick() {
        page =1
        offset = 0
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

    private fun commonFetchListAfterOptionChange() {
        shouldScrollToTop = true
        getList(false)
    }

    private fun increaseOffset() {
        page += 1
        offset += limit
    }

    private fun decreaseOffset() {
        if (offset != 0) {
            page -= 1
            offset -= limit
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MangaRankingFragment()
    }
}
package com.abhinavdev.animeapp.ui.more

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
import com.abhinavdev.animeapp.databinding.FragmentMyMangaListBinding
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.models.enums.MalMangaStatus
import com.abhinavdev.animeapp.remote.models.enums.MalSortType
import com.abhinavdev.animeapp.remote.models.malmodels.MalMangaData
import com.abhinavdev.animeapp.remote.models.malmodels.MalMyMangaListResponse
import com.abhinavdev.animeapp.ui.anime.misc.AdapterType
import com.abhinavdev.animeapp.ui.common.listeners.CustomClickListener
import com.abhinavdev.animeapp.ui.main.MainActivity
import com.abhinavdev.animeapp.ui.manga.adapters.MalMangaVerticalAdapter
import com.abhinavdev.animeapp.ui.more.viewmodels.MoreViewModel
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.adapter.GridSpacing
import com.abhinavdev.animeapp.util.PrefUtils
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.createViewModel
import com.abhinavdev.animeapp.util.extension.removeItemDecorations
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.showOrHide
import com.abhinavdev.animeapp.util.extension.toast

class MyMangaListFragment : BaseFragment(), View.OnClickListener, CustomClickListener {
    private var _binding: FragmentMyMangaListBinding? = null
    private val binding get() = _binding!!
    private var parentActivity: MainActivity? = null
    private lateinit var viewModel: MoreViewModel

    private var isFromSwipe = false

    private var gridOrList: AdapterType = AdapterType.GRID

    private var limit = 0
    private var offset = 0

    private val mangaList: ArrayList<MalMangaData> = arrayListOf()
    private var adapter: MalMangaVerticalAdapter? = null

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
        viewModel = createViewModel(MoreViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyMangaListBinding.inflate(layoutInflater, container, false)
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
        getAnimeList()
    }

    private fun initComponents() {
        gridOrList = AdapterType.valueOfOrDefault(PrefUtils.getInt(Const.PrefKeys.GRID_OR_LIST_KEY))
        limit = SettingsHelper.getMyListLimit()
        with(binding.toolbar) {
            tvTitle.text = getString(R.string.msg_my_manga_list)
            val viewIcon = when (gridOrList) {
                com.abhinavdev.animeapp.ui.anime.misc.AdapterType.GRID -> R.drawable.ic_list_view
                com.abhinavdev.animeapp.ui.anime.misc.AdapterType.LIST -> R.drawable.ic_grid_view
            }
            ivExtra.show()
            ivExtra.setImageResource(viewIcon)
        }
    }

    private fun setAdapters() {
        adapter = MalMangaVerticalAdapter(mangaList, this)
        adapter?.setHasStableIds(true)
        toggleAdapterType(gridOrList)
        binding.rvList.setHasFixedSize(true)
        binding.rvList.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun toggleAdapterType(gridOrList: AdapterType) {
        adapter?.setAdapterType(gridOrList)
        when (gridOrList) {
            AdapterType.GRID -> {
                binding.rvList.addItemDecoration(GridSpacing(2, 16, false))
                binding.rvList.layoutManager = GridLayoutManager(context, 2)
            }

            AdapterType.LIST -> {
                binding.rvList.removeItemDecorations()
                binding.rvList.layoutManager = LinearLayoutManager(context)
            }
        }
        adapter?.notifyDataSetChanged()
    }

    private fun setListeners() {
        binding.toolbar.ivBack.setOnClickListener(this)
        binding.toolbar.ivExtra.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.toolbar.ivBack -> parentActivity?.onBackPressed()
            binding.toolbar.ivExtra -> toggleViewType()
        }
    }

    private fun toggleViewType() {
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
        binding.toolbar.ivExtra.setImageResource(viewIcon)
        PrefUtils.setInt(Const.PrefKeys.GRID_OR_LIST_KEY, gridOrList.value)
        toggleAdapterType(gridOrList)
    }

    private fun setObservers() {
        viewModel.myMangaListResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let {
                            setData(it)
                        }
                        isLoaderVisible(false)
                        showEmptyLayout(false)
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
    private fun setData(data: MalMyMangaListResponse) {
        mangaList.clear()
        data.data?.let { mangaList.addAll(it) }
        adapter?.notifyDataSetChanged()
    }

    private fun isLoaderVisible(b: Boolean) {
        parentActivity?.isLoaderVisible(b)
    }

    private fun showEmptyLayout(isError: Boolean) {
        val isListEmpty = mangaList.isEmpty()
        with(binding.emptyLayout) {
            if (isListEmpty){
                val imageRes = if (isError){
                    tvEmptyTitle.text = getString(R.string.error_something_went_wrong)
                    tvEmptyDesc.text = getString(R.string.msg_empty_error_des)
                    R.drawable.bg_error
                }else{
                    tvEmptyTitle.text = getString(R.string.msg_your_list_empty)
                    tvEmptyDesc.text = getString(R.string.msg_empty_manga_list_des)
                    R.drawable.bg_empty_list
                }
                binding.emptyLayout.ivEmptyIcon.setImageResource(imageRes)
            }
        }
        binding.rvList.showOrHide(!isListEmpty)
        binding.emptyLayout.root.showOrHide(isListEmpty)
    }

    private fun getAnimeList() {
        viewModel.getMyMangaList(MalMangaStatus.ALL, MalSortType.UPDATED, limit, offset)
    }

    override fun onItemClick(position: Int) {

    }

    companion object {
        @JvmStatic
        fun newInstance() = MyMangaListFragment()
    }
}
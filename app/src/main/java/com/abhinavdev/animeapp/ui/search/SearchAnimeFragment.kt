package com.abhinavdev.animeapp.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseFragment
import com.abhinavdev.animeapp.databinding.FragmentSearchBinding
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.models.users.UserFullProfileResponse
import com.abhinavdev.animeapp.ui.anime.misc.AdapterType
import com.abhinavdev.animeapp.ui.main.MainActivity
import com.abhinavdev.animeapp.ui.more.viewmodels.MoreViewModel
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.ViewUtil
import com.abhinavdev.animeapp.util.extension.applyDimen
import com.abhinavdev.animeapp.util.extension.createViewModel
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.toast

class SearchAnimeFragment : BaseFragment(), View.OnClickListener {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var parentActivity: MainActivity? = null
    private lateinit var viewModel: MoreViewModel

    private var gridOrList: AdapterType = AdapterType.GRID

    private var isFromSwipe = false

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
        getProfile(false)
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
        val paginationBPadding = binding.groupPagination.clPagination.paddingBottom
        ViewUtil.setOnApplyUiInsetsListener(binding.groupPagination.clPagination) { insets ->
            ViewUtil.setBottomPadding(
                binding.groupPagination.clPagination, insets.bottom + paginationBPadding + bottomBarHeight + salt
            )
        }
    }

    private fun setAdapters() {

    }

    private fun setListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            getProfile(true)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
        }
    }

    private fun setObservers() {
        viewModel.userFullProfileResponse.observe(viewLifecycleOwner) { event ->
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

    private fun setData(data: UserFullProfileResponse) {

    }

    private fun isLoaderVisible(b: Boolean) {
        if (isFromSwipe && !b) {
            binding.swipeRefresh.isRefreshing = false
        } else if (!isFromSwipe) {
            parentActivity?.isLoaderVisible(b)
        }
    }

    private fun getProfile(fromSwipe: Boolean){
        isFromSwipe = fromSwipe
        val userName = SettingsHelper.getMalProfile()?.name
        userName?.let { viewModel.getUserFullProfile(it) }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchAnimeFragment()
    }
}
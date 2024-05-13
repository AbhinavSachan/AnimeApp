package com.abhinavdev.animeapp.ui.anime

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseFragment
import com.abhinavdev.animeapp.databinding.FragmentProfileBinding
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.models.anime.AnimeFullResponse
import com.abhinavdev.animeapp.ui.anime.viewmodel.AnimeViewModel
import com.abhinavdev.animeapp.ui.main.MainActivity
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.extension.createViewModel
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.toast

class AnimeDetailsFragment : BaseFragment(), View.OnClickListener {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var parentActivity: MainActivity? = null
    private lateinit var viewModel: AnimeViewModel

    private var isFromSwipe = false

    private var animeId: Int = -1

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
        arguments?.let { animeId = it.getInt(Const.BundleExtras.EXTRA_ID) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
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
        getData(false)
    }

    private fun initComponents() {
        with(binding.toolbar) {
            tvTitle.text = getString(R.string.action_details)
        }
        with(binding.emptyLayout) {
            tvEmptyTitle.text = getString(R.string.error_something_went_wrong)
            tvEmptyDesc.text = getString(R.string.msg_empty_error_des)
            binding.emptyLayout.ivEmptyIcon.setImageResource(R.drawable.bg_error)
        }
    }

    private fun setAdapters() {

    }

    private fun setListeners() {
        binding.toolbar.ivBack.setOnClickListener(this)
        binding.swipeRefresh.setOnRefreshListener {
            getData(true)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.toolbar.ivBack->parentActivity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    private fun setObservers() {
        viewModel.animeFullResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let {
                            setData(it)
                        }
                        isLoaderVisible(false)
                        hideErrorLayout()
                    }

                    is Resource.Error -> {
                        isLoaderVisible(false)
                        showErrorLayout()
                        response.message?.let { message -> toast(message) }
                    }

                    is Resource.Loading -> {
                        isLoaderVisible(true)
                    }
                }
            }
        }
    }

    private fun setData(data: AnimeFullResponse) {

    }

    private fun isLoaderVisible(b: Boolean) {
        if (isFromSwipe && !b) {
            binding.swipeRefresh.isRefreshing = false
        } else if (!isFromSwipe) {
            parentActivity?.isLoaderVisible(b)
        }
    }

    private fun showErrorLayout(){
        binding.clContent.hide()
        binding.emptyLayout.root.show()
    }

    private fun hideErrorLayout(){
        binding.clContent.show()
        binding.emptyLayout.root.hide()
    }

    private fun getData(fromSwipe: Boolean) {
        isFromSwipe = fromSwipe
        viewModel.getFullAnimeById(animeId)
    }

    companion object {
        @JvmStatic
        fun newInstance(animeId: Int) = AnimeDetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(Const.BundleExtras.EXTRA_ID, animeId)
            }
        }
    }
}
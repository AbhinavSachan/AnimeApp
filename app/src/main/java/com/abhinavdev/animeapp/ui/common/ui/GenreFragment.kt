package com.abhinavdev.animeapp.ui.common.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseFragment
import com.abhinavdev.animeapp.databinding.FragmentGenreBinding
import com.abhinavdev.animeapp.remote.models.enums.Genre
import com.abhinavdev.animeapp.remote.models.enums.MediaType
import com.abhinavdev.animeapp.ui.common.adapters.GenreCategoryAdapter
import com.abhinavdev.animeapp.ui.common.listeners.CustomClickListener
import com.abhinavdev.animeapp.ui.main.MainActivity
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.adapter.GridSpacing
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.ViewUtil
import com.abhinavdev.animeapp.util.extension.applyDimen
import com.abhinavdev.animeapp.util.extension.hide

class GenreFragment : BaseFragment(), View.OnClickListener, CustomClickListener {
    private var _binding: FragmentGenreBinding? = null
    private val binding get() = _binding!!
    private var parentActivity: MainActivity? = null

    private var adapter: GenreCategoryAdapter? = null
    private var genreList = listOf<Genre>()
    private var mediaType = MediaType.ANIME

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenreBinding.inflate(layoutInflater, container, false)
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
    }

    private fun initComponents() {
        val sfw = SettingsHelper.getSfwEnabled()
        mediaType = MediaType.valueOfOrDefault(arguments?.getString(Const.BundleExtras.EXTRA_TYPE))
        genreList = when (mediaType) {
            MediaType.ANIME -> Genre.listAnime(sfw)
            MediaType.MANGA -> Genre.listManga(sfw)
            else -> Genre.listAnime(sfw)
        }
        with(binding.toolbar) {
            ivBack.hide()
            tvTitle.text = getString(R.string.msg_genres)

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
    }

    private fun setAdapters() {
        adapter = GenreCategoryAdapter(genreList, this)
        adapter?.setHasStableIds(true)
        binding.rvList.setHasFixedSize(Const.Other.HAS_FIXED_SIZE)
        binding.rvList.addItemDecoration(GridSpacing(2, 16, false))
        binding.rvList.layoutManager = GridLayoutManager(context, 2)
        binding.rvList.adapter = adapter
    }

    private fun setListeners() {

    }

    override fun onClick(v: View?) {
        when (v) {

        }
    }

    override fun onItemClick(position: Int) {

    }

    private fun setObservers() {

    }

    companion object {
        @JvmStatic
        fun newInstance(type: MediaType) = GenreFragment().apply {
            arguments = Bundle().apply {
                putString(Const.BundleExtras.EXTRA_TYPE, type.search)
            }
        }
    }
}
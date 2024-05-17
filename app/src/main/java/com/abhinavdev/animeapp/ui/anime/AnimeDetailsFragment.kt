package com.abhinavdev.animeapp.ui.anime

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginTop
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseFragment
import com.abhinavdev.animeapp.databinding.FragmentAnimeDetailsBinding
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.models.anime.AnimeData
import com.abhinavdev.animeapp.remote.models.anime.AnimeFullResponse
import com.abhinavdev.animeapp.ui.anime.viewmodel.AnimeViewModel
import com.abhinavdev.animeapp.ui.main.MainActivity
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.appsettings.AppTitleType
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.NumExtensions.toStringOrUnknown
import com.abhinavdev.animeapp.util.extension.ViewUtil
import com.abhinavdev.animeapp.util.extension.createViewModel
import com.abhinavdev.animeapp.util.extension.getDisplaySize
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.loadImage
import com.abhinavdev.animeapp.util.extension.openShareSheet
import com.abhinavdev.animeapp.util.extension.setHeightAsPercentageOfGivenHeight
import com.abhinavdev.animeapp.util.extension.setWidthInRatioToHeight
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.toast
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class AnimeDetailsFragment : BaseFragment(), View.OnClickListener {
    private var _binding: FragmentAnimeDetailsBinding? = null
    private val binding get() = _binding!!
    private var parentActivity: MainActivity? = null
    private lateinit var viewModel: AnimeViewModel

    private var isFromSwipe = false

    private var animeId: Int = -1

    private var malUrl: String? = null

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
        _binding = FragmentAnimeDetailsBinding.inflate(layoutInflater, container, false)
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
            ivBack.show()
            ivExtra.show()
            ivExtra.setImageResource(R.drawable.ic_share)

            ViewUtil.setOnApplyUiInsetsListener(root) { insets ->
                ViewUtil.setTopPadding(root, insets.top)
            }
        }
        val posterMargin = binding.ivPoster.marginTop
        ViewUtil.setOnApplyUiInsetsListener(binding.ivPoster) { insets ->
            ViewUtil.setTopMargin(binding.ivPoster, posterMargin + insets.top)
        }
        setTopViewPagerHeight()
        with(binding.emptyLayout) {
            tvEmptyTitle.text = getString(R.string.error_something_went_wrong)
            tvEmptyDesc.text = getString(R.string.msg_empty_error_des)
            binding.emptyLayout.ivEmptyIcon.setImageResource(R.drawable.bg_error)
        }
    }

    private fun setTopViewPagerHeight() {
        activity?.let { getDisplaySize(it) }?.let {
            binding.ivPosterBackground.setHeightAsPercentageOfGivenHeight(it.height, 70)
            binding.ivPoster.setHeightAsPercentageOfGivenHeight(it.height, 40)
            binding.ivPoster.setWidthInRatioToHeight(2, 3)
        }
    }

    private fun setAdapters() {

    }

    private fun setListeners() {
        binding.toolbar.ivBack.setOnClickListener(this)
        binding.toolbar.ivExtra.setOnClickListener(this)
        binding.swipeRefresh.setOnRefreshListener {
            getData(true)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.toolbar.ivBack -> parentActivity?.onBackPressedDispatcher?.onBackPressed()
            binding.toolbar.ivExtra -> onShareClick()
        }
    }

    private fun onShareClick() {
        malUrl?.let { context?.openShareSheet(it) }
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
        data.data?.let { anime ->
            val metaData = generateMetadataString(anime)
            val shareUrl = anime.url
            val image = anime.images?.jpg?.largeImageUrl
            val titles = anime.titles
            val userPreferredType = SettingsHelper.getPreferredTitleType()
            val animeName = AppTitleType.getTitleFromData(titles, userPreferredType)

            with(binding) {
                malUrl = shareUrl
                val target = object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        ivPosterBackground.setImageDrawable(resource)
                        ivPoster.setImageDrawable(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        ivPosterBackground.setImageDrawable(placeholder)
                        ivPoster.setImageDrawable(placeholder)
                    }
                }
                context?.let { target.loadImage(it,image) }
                tvAnimeName.text = animeName
                //if score was added then show start icon
                if (metaData.first) ivRating.show()
                tvMetadata.text = metaData.second
            }
        }
    }

    private fun generateMetadataString(anime: AnimeData): Pair<Boolean, String> {
        val result = StringBuilder()
        val duration = anime.episodeDuration
        val episodes = anime.episodes
        val year = anime.year ?: anime.airedOn?.prop?.from?.year
        val season = anime.season?.showName
        val score = anime.score
        var isScoreAdded = false
        val bigDot = " ${Const.Other.BIG_DOT_CHAR} "

        score?.let {
            isScoreAdded = true
            result.append(it.toString())
        }
        episodes.takeIf { it != null && it > 0 }?.let {
            if (result.isNotEmpty()) result.append(bigDot)
            val episodeRes = if (it == 1) {
                R.string.msg_episode
            } else {
                R.string.msg_episodes
            }
            result.append(" (${it.toStringOrUnknown()} ${getString(episodeRes)})")
        }
        duration?.let {
            if (result.isNotEmpty()) result.append(bigDot)
            result.append(it.duration)
            if (it.isPerEpisode) result.append(getString(R.string.msg_per_ep))
        }
        if (season != null || year != null){
            if (result.isNotEmpty()) result.append(bigDot)
            season?.let {
                result.append(it)
                result.append(" ")
            }
            year?.let {
                result.append(it)
            }
        }

        return isScoreAdded to result.toString()
    }

    private fun isLoaderVisible(b: Boolean) {
        if (isFromSwipe && !b) {
            binding.swipeRefresh.isRefreshing = false
        } else if (!isFromSwipe) {
            parentActivity?.isLoaderVisible(b)
        }
    }

    private fun showErrorLayout() {
        binding.clContent.hide()
        binding.emptyLayout.root.show()
    }

    private fun hideErrorLayout() {
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
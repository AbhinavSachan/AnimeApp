package com.abhinavdev.animeapp.ui.anime

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.TypefaceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.core.view.marginTop
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseFragment
import com.abhinavdev.animeapp.databinding.FragmentAnimeDetailsBinding
import com.abhinavdev.animeapp.remote.kit.Resource
import com.abhinavdev.animeapp.remote.models.anime.AnimeData
import com.abhinavdev.animeapp.remote.models.anime.AnimeFullResponse
import com.abhinavdev.animeapp.ui.anime.viewmodel.AnimeViewModel
import com.abhinavdev.animeapp.ui.common.adapters.GenreAdapter
import com.abhinavdev.animeapp.ui.common.models.LocalGenreModel
import com.abhinavdev.animeapp.ui.common.ui.FullScreenImageActivity
import com.abhinavdev.animeapp.ui.main.MainActivity
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.appsettings.AppTitleType
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.NumExtensions.formatOrNull
import com.abhinavdev.animeapp.util.extension.NumExtensions.toStringOrUnknown
import com.abhinavdev.animeapp.util.extension.ViewUtil
import com.abhinavdev.animeapp.util.extension.applyFont
import com.abhinavdev.animeapp.util.extension.createViewModel
import com.abhinavdev.animeapp.util.extension.getDisplaySize
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.isHidden
import com.abhinavdev.animeapp.util.extension.loadImage
import com.abhinavdev.animeapp.util.extension.openShareSheet
import com.abhinavdev.animeapp.util.extension.placeholder
import com.abhinavdev.animeapp.util.extension.setHeightAsPercentageOfGivenHeight
import com.abhinavdev.animeapp.util.extension.setWidthInRatioToHeight
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.showOrHide
import com.abhinavdev.animeapp.util.extension.toast
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class AnimeDetailsFragment : BaseFragment(), View.OnClickListener, GenreAdapter.Callback {
    private var _binding: FragmentAnimeDetailsBinding? = null
    private val binding get() = _binding!!
    private var parentActivity: MainActivity? = null
    private lateinit var viewModel: AnimeViewModel

    private var isFromSwipe = false

    private var animeId: Int = -1
    private var posterImageUrl: String? = null

    private var malUrl: String? = null

    private val genreList: ArrayList<LocalGenreModel> = arrayListOf()
    private var genreAdapter: GenreAdapter? = null

    private val coroutine = CoroutineScope(Dispatchers.IO)

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
        ViewUtil.setOnApplyUiInsetsListener(binding.scrollView) { insets ->
            ViewUtil.setBottomPadding(binding.scrollView, insets.bottom)
        }

        setTopViewPagerHeight()
        with(binding.emptyLayout) {
            tvEmptyTitle.text = getString(R.string.error_something_went_wrong)
            tvEmptyDesc.text = getString(R.string.msg_empty_error_des)
            binding.emptyLayout.ivEmptyIcon.setImageResource(R.drawable.bg_error)
        }
        binding.tvDescription.setInterpolator(OvershootInterpolator())
    }

    private fun setTopViewPagerHeight() {
        activity?.let { getDisplaySize(it) }?.let {
            binding.ivPosterBackground.setHeightAsPercentageOfGivenHeight(it.height, 70)
            binding.ivPoster.setHeightAsPercentageOfGivenHeight(it.height, 40)
            binding.ivPoster.setWidthInRatioToHeight(2, 3)
        }
    }

    private fun setAdapters() {
        binding.rvGenre.setHasFixedSize(true)
        genreAdapter = GenreAdapter(genreList, this)
        genreAdapter?.setHasStableIds(true)
        binding.rvGenre.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvGenre.adapter = genreAdapter
    }

    private fun setListeners() {
        binding.toolbar.ivBack.setOnClickListener(this)
        binding.toolbar.ivExtra.setOnClickListener(this)
        binding.ivPoster.setOnClickListener(this)
        binding.tvToggleDescription.setOnClickListener(this)
        binding.swipeRefresh.setOnRefreshListener {
            getData(true)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.toolbar.ivBack -> parentActivity?.onBackPressedDispatcher?.onBackPressed()
            binding.toolbar.ivExtra -> onShareClick()
            binding.ivPoster -> onImageClick()
            binding.tvToggleDescription -> onToggleDescriptionClick()
        }
    }

    private fun onToggleDescriptionClick() {
        binding.tvDescription.toggle()
        val toggleText =
            if (binding.tvDescription.isExpanded) getString(R.string.action_read_more) else getString(
                R.string.action_read_less
            )
        binding.tvToggleDescription.text = toggleText
    }

    private fun onImageClick() {
        FullScreenImageActivity.startNewActivity(
            parentActivity, posterImageUrl, binding.ivPoster
        )
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

    @SuppressLint("NotifyDataSetChanged")
    private fun setData(data: AnimeFullResponse) {
        data.data?.let { anime ->
            posterImageUrl = anime.images?.jpg?.largeImageUrl
            malUrl = anime.url
            val metaData = generateMetadataString(anime)
            val titles = anime.titles
            val userPreferredType = SettingsHelper.getPreferredTitleType()
            val animeName = AppTitleType.getTitleFromData(titles, userPreferredType)
            val description = generateDescription(anime.synopsis, anime.background)
            val rank = "#${anime.rank?.formatOrNull().placeholder()}"
            val popularity = "#${anime.popularity?.formatOrNull().placeholder()}"
            val scoredBy = anime.scoredBy?.formatOrNull().placeholder()
            val members = anime.members?.formatOrNull().placeholder()
            val favourites = anime.favorites?.formatOrNull().placeholder()

            genreList.clear()
            val fetchedGenreList = getGenreLocalList(anime)
            genreList.addAll(fetchedGenreList)
            genreAdapter?.notifyDataSetChanged()

            with(binding) {
                val target = object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable, transition: Transition<in Drawable>?
                    ) {
                        ivPosterBackground.setImageDrawable(resource)
                        ivPoster.setImageDrawable(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        ivPosterBackground.setImageDrawable(placeholder)
                        ivPoster.setImageDrawable(placeholder)
                    }
                }
                context?.let { target.loadImage(it, posterImageUrl) }
                tvAnimeName.text = animeName
                //if score was added then show start icon
                if (metaData.first) ivRating.show()
                tvMetadata.showOrHide(metaData.second.isNotBlank())
                tvMetadata.text = metaData.second
                tvDescription.text = description

                //setting all the stats
                groupRank.tvHeading.text = getString(R.string.msg_rank)
                groupRank.tvText.text = rank
                groupPopularity.tvHeading.text = getString(R.string.msg_popularity)
                groupPopularity.tvText.text = popularity
                groupScoredBy.tvHeading.text = getString(R.string.msg_scored_by)
                groupScoredBy.tvText.text = scoredBy
                groupMembers.tvHeading.text = getString(R.string.msg_members)
                groupMembers.tvText.text = members
                groupFavourite.tvHeading.text = getString(R.string.msg_favourites)
                groupFavourite.tvText.text = favourites

                if (clContent.isHidden()) clContent.show()
                Handler(Looper.getMainLooper()).postDelayed({
                    if (tvDescription.lineCount >= tvDescription.maxLines) tvToggleDescription.show()
                },10)
            }
        }
    }

    private fun getGenreLocalList(anime: AnimeData): List<LocalGenreModel> {
        val list: ArrayList<LocalGenreModel> = arrayListOf()
        anime.genres?.map { LocalGenreModel(it.malId, it.name) }?.let { list.addAll(it) }
        if (!SettingsHelper.getSfwEnabled()) {
            anime.explicitGenres?.map { LocalGenreModel(it.malId, it.name) }
                ?.let { list.addAll(it) }

        }
        anime.demographics?.map { LocalGenreModel(it.malId, it.name) }?.let { list.addAll(it) }
        anime.themes?.map { LocalGenreModel(it.malId, it.name) }?.let { list.addAll(it) }
        return list
    }

    private fun generateMetadataString(anime: AnimeData): Pair<Boolean, String> {
        val result = StringBuilder()
        val duration = anime.duration
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
            result.append(" ${it.toStringOrUnknown()} ${getString(episodeRes)}")
        }
        duration?.let {
            if (result.isNotEmpty()) result.append(bigDot)
            result.append(it.duration)
            if (it.isPerEpisode) result.append(getString(R.string.msg_per_ep))
        }
        if (season != null || year != null) {
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

    private fun generateDescription(
        synopsis: String?, backgroundContent: String?
    ): SpannableStringBuilder {
        // Load custom typeface
        val customTypeface = applyFont(R.font.custom_bold)

        val bgHeading = getString(R.string.msg_background)
        val spanBgHeading = SpannableString(bgHeading)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && customTypeface != null) {
            spanBgHeading.setSpan(
                TypefaceSpan(customTypeface),
                0,
                bgHeading.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        val desHeading = getString(R.string.msg_synopsis)
        val spanDesHeading = SpannableString(desHeading)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && customTypeface != null) {
            spanDesHeading.setSpan(
                TypefaceSpan(customTypeface),
                0,
                desHeading.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        val result = SpannableStringBuilder()
        synopsis?.takeIf { it.isNotBlank() }?.let {
            result.append(spanDesHeading)
            result.append(" ")
            result.append(SpannableString(it))
        }
        backgroundContent?.takeIf { it.isNotBlank() }?.let {
            if (result.isNotEmpty()) result.append("\n\n")
            result.append(spanBgHeading)
            result.append(" ")
            result.append(SpannableString(it))
        }

        return result
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

    override fun onGenreClick(position: Int) {

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
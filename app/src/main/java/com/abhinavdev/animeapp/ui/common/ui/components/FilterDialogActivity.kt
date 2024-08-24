package com.abhinavdev.animeapp.ui.common.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.icu.util.Calendar
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseActivity
import com.abhinavdev.animeapp.databinding.ActivityFilterDialogBinding
import com.abhinavdev.animeapp.remote.models.enums.AgeRating
import com.abhinavdev.animeapp.remote.models.enums.AnimeOrderBy
import com.abhinavdev.animeapp.remote.models.enums.AnimeStatus
import com.abhinavdev.animeapp.remote.models.enums.AnimeType
import com.abhinavdev.animeapp.remote.models.enums.MangaOrderBy
import com.abhinavdev.animeapp.remote.models.enums.MangaStatus
import com.abhinavdev.animeapp.remote.models.enums.MangaType
import com.abhinavdev.animeapp.remote.models.enums.SortOrder
import com.abhinavdev.animeapp.ui.common.adapters.SelectableItemAdapter
import com.abhinavdev.animeapp.ui.common.adapters.SelectableItemData
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.dismiss
import com.abhinavdev.animeapp.util.extension.formatTo
import com.abhinavdev.animeapp.util.extension.getDisplaySize
import com.abhinavdev.animeapp.util.extension.getFormattedDateOrNull
import com.abhinavdev.animeapp.util.extension.isHidden
import com.abhinavdev.animeapp.util.extension.showOrHide
import com.abhinavdev.animeapp.util.ui.date.MonthYearPickerDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback

class FilterDialogActivity : BaseActivity(), View.OnClickListener {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityFilterDialogBinding.inflate(layoutInflater)
    }
    private var sheetBehavior: BottomSheetBehavior<View>? = null

    private var withResult = false
    private var clearAll = false

    private var isAnime = false

    private var isUnapprovedChecked = false

    private var animeType = AnimeType.ALL
    private var animeStatus = AnimeStatus.ALL
    private var animeOrderBy = AnimeOrderBy.POPULARITY
    private var ageRating = AgeRating.NONE

    private var mangaType = MangaType.ALL
    private var mangaStatus = MangaStatus.ALL
    private var mangaOrderBy = MangaOrderBy.POPULARITY

    private var sortType = SortOrder.ASCENDING
    private var startDate = ""
    private var query = ""
    private var endDate = ""

    private var typeList = listOf<SelectableItemData>()
    private var statusList = listOf<SelectableItemData>()
    private var orderByList = listOf<SelectableItemData>()
    private var ageRatingList = listOf<SelectableItemData>()

    private var sortTypeList = listOf<SelectableItemData>()

    private var typeAdapter: SelectableItemAdapter? = null
    private var statusAdapter: SelectableItemAdapter? = null
    private var orderByAdapter: SelectableItemAdapter? = null
    private var ageRatingAdapter: SelectableItemAdapter? = null
    private var sortByAdapter: SelectableItemAdapter? = null

    private val sheetCallback = object : BottomSheetCallback() {
        @SuppressLint("SwitchIntDef")
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_HIDDEN -> {
                    onSheetClose()
                }
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        setBottomSheet()
        setData()
        setListeners()
    }

    private fun setData() {
        isAnime = intent.getBooleanExtra(Const.BundleExtras.EXTRA_IS_ANIME, false)

        isUnapprovedChecked = intent.getBooleanExtra(Const.BundleExtras.EXTRA_IS_APPROVED, false)
        sortType = SortOrder.valueOfOrDefault(intent.getStringExtra(Const.BundleExtras.EXTRA_SORT))
        startDate = intent.getStringExtra(Const.BundleExtras.EXTRA_START_DATE) ?: ""
        endDate = intent.getStringExtra(Const.BundleExtras.EXTRA_END_DATE) ?: ""
        query = intent.getStringExtra(Const.BundleExtras.EXTRA_QUERY) ?: ""

        sortTypeList = SortOrder.list.map {
            SelectableItemData(it.search, it.showName).apply { isSelected = sortType == it }
        }
        if (isAnime) {
            animeType =
                AnimeType.valueOfOrDefault(intent.getStringExtra(Const.BundleExtras.EXTRA_TYPE))
            animeStatus =
                AnimeStatus.valueOfOrDefault(intent.getStringExtra(Const.BundleExtras.EXTRA_STATUS))
            animeOrderBy =
                AnimeOrderBy.valueOfOrDefault(intent.getStringExtra(Const.BundleExtras.EXTRA_ORDER_BY))
            ageRating =
                AgeRating.valueOfOrDefault(intent.getStringExtra(Const.BundleExtras.EXTRA_AGE_RATING))

            typeList = AnimeType.list.map {
                SelectableItemData(it.search, it.showName).apply { isSelected = animeType == it }
            }
            statusList = AnimeStatus.list.map {
                SelectableItemData(it.search, it.showName).apply { isSelected = animeStatus == it }
            }
            orderByList = AnimeOrderBy.list.map {
                SelectableItemData(it.search, it.showName).apply { isSelected = animeOrderBy == it }
            }
            ageRatingList = AgeRating.list(SettingsHelper.getSfwEnabled()).map {
                SelectableItemData(it.search, it.showName).apply { isSelected = ageRating == it }
            }

        } else {
            mangaType =
                MangaType.valueOfOrDefault(intent.getStringExtra(Const.BundleExtras.EXTRA_TYPE))
            mangaStatus =
                MangaStatus.valueOfOrDefault(intent.getStringExtra(Const.BundleExtras.EXTRA_STATUS))
            mangaOrderBy =
                MangaOrderBy.valueOfOrDefault(intent.getStringExtra(Const.BundleExtras.EXTRA_ORDER_BY))

            typeList = MangaType.list.map {
                SelectableItemData(it.search, it.showName).apply { isSelected = mangaType == it }
            }
            statusList = MangaStatus.list.map {
                SelectableItemData(it.search, it.showName).apply { isSelected = mangaStatus == it }
            }
            orderByList = MangaOrderBy.list.map {
                SelectableItemData(it.search, it.showName).apply { isSelected = mangaOrderBy == it }
            }
        }
        binding.groupAgeRating.root.showOrHide(isAnime)
        //type
        with(binding.groupMediaType) {
            tvFilterHeading.text = getString(R.string.msg_choose_type)
            typeAdapter = SelectableItemAdapter(typeList) { item, position ->
                if (isAnime) {
                    animeType = AnimeType.valueOfOrDefault(item.id)
                } else {
                    mangaType = MangaType.valueOfOrDefault(item.id)
                }
            }
            rvFilterOptions.layoutManager = LinearLayoutManager(
                this@FilterDialogActivity, LinearLayoutManager.HORIZONTAL, false
            )
            rvFilterOptions.setHasFixedSize(true)
            rvFilterOptions.adapter = typeAdapter
        }
        //status
        with(binding.groupMediaStatus) {
            tvFilterHeading.text = getString(R.string.msg_choose_status)
            statusAdapter = SelectableItemAdapter(statusList) { item, position ->
                if (isAnime) {
                    animeStatus = AnimeStatus.valueOfOrDefault(item.id)
                } else {
                    mangaStatus = MangaStatus.valueOfOrDefault(item.id)
                }
            }
            rvFilterOptions.layoutManager = LinearLayoutManager(
                this@FilterDialogActivity, LinearLayoutManager.HORIZONTAL, false
            )
            rvFilterOptions.setHasFixedSize(true)
            rvFilterOptions.adapter = statusAdapter
        }
        //order by
        with(binding.groupOrderBy) {
            tvFilterHeading.text = getString(R.string.msg_order_by)
            orderByAdapter = SelectableItemAdapter(orderByList) { item, position ->
                if (isAnime) {
                    animeOrderBy = AnimeOrderBy.valueOfOrDefault(item.id)
                } else {
                    mangaOrderBy = MangaOrderBy.valueOfOrDefault(item.id)
                }
            }
            rvFilterOptions.layoutManager = LinearLayoutManager(
                this@FilterDialogActivity, LinearLayoutManager.HORIZONTAL, false
            )
            rvFilterOptions.setHasFixedSize(true)
            rvFilterOptions.adapter = orderByAdapter
        }
        //sort by
        with(binding.groupSortBy) {
            tvFilterHeading.text = getString(R.string.msg_sort_by)
            sortByAdapter = SelectableItemAdapter(sortTypeList) { item, position ->
                sortType = SortOrder.valueOfOrDefault(item.id)
            }
            rvFilterOptions.layoutManager = LinearLayoutManager(
                this@FilterDialogActivity, LinearLayoutManager.HORIZONTAL, false
            )
            rvFilterOptions.setHasFixedSize(true)
            rvFilterOptions.adapter = sortByAdapter
        }
        //age rating
        with(binding.groupAgeRating) {
            tvFilterHeading.text = getString(R.string.msg_choose_age_rating)
            ageRatingAdapter = SelectableItemAdapter(ageRatingList) { item, position ->
                if (isAnime) {
                    ageRating = AgeRating.valueOfOrDefault(item.id)
                }
            }
            rvFilterOptions.layoutManager = LinearLayoutManager(
                this@FilterDialogActivity, LinearLayoutManager.HORIZONTAL, false
            )
            rvFilterOptions.setHasFixedSize(true)
            rvFilterOptions.adapter = ageRatingAdapter
        }
        //start date
        with(binding.groupStartDate) {
            val year = getFormattedDateOrNull(startDate, outFormat = "yyyy")
                ?: getString(R.string.msg_select_year)
            tvItemLabel.text = getString(R.string.msg_start_year)
            tvItem.text = year
        }
        //end date
        with(binding.groupEndDate) {
            val year = getFormattedDateOrNull(endDate, outFormat = "yyyy")
                ?: getString(R.string.msg_select_year)
            tvItemLabel.text = getString(R.string.msg_end_year)
            tvItem.text = year
        }
        binding.etSearch.setText(query)
        binding.etSearch.addTextChangedListener {
            query = it.toString()
        }
    }

    private fun setListeners() {
        binding.ivClose.setOnClickListener(this)
        binding.btnApply.setOnClickListener(this)
        binding.btnClearAll.setOnClickListener(this)
        binding.groupStartDate.llItem.setOnClickListener(this)
        binding.groupEndDate.llItem.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.ivClose -> onBackPressed()
            binding.btnApply -> {
                withResult = true
                onBackPressed()
            }

            binding.btnClearAll -> {
                withResult = true
                clearAll = true
                onBackPressed()
            }

            binding.groupStartDate.llItem -> {
                openYearPicker {
                    startDate = it
                    binding.groupStartDate.tvItem.text =
                        getFormattedDateOrNull(startDate, outFormat = "yyyy")
                }
            }

            binding.groupEndDate.llItem -> {
                openYearPicker {
                    endDate = it
                    binding.groupEndDate.tvItem.text =
                        getFormattedDateOrNull(endDate, outFormat = "yyyy")
                }
            }
        }
    }

    private fun openYearPicker(onPicked: (String) -> Unit) {
        val tag = "YearPicker"
        val fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            val dialog = MonthYearPickerDialog()
            dialog.setListener { _, year, month, dayOfMonth ->
                val cal = Calendar.getInstance()
                cal.set(year, 0, 1)
                val pickedYear = cal.time.formatTo("yyyy-MM-dd") ?: ""
                onPicked(pickedYear)
            }
            dialog.show(supportFragmentManager, tag)
        }
    }

    private fun closeExerciseSheet(event: MotionEvent) {
        closeSheetWhenClickOutSide(sheetBehavior, binding.bottomSheet, event)
    }

    private fun setBottomSheetProperties(
        sheet: BottomSheetBehavior<View>?,
        peekHeight: Int,
        skipCollapse: Boolean,
        state: Int,
    ) {
        sheet?.isHideable = true
        //when sheet is in collapse state
        sheet?.peekHeight = peekHeight
        //when sheet is in expand state
        sheet?.skipCollapsed = skipCollapse
        sheet?.state = state
    }

    /**
     * returns true if closed sheet, false otherwise
     */
    private fun onBackPressedCloseSheet(): Boolean {
        if (sheetBehavior?.isHidden() == false) {
            sheetBehavior?.dismiss()
            return true
        }
        return false
    }

    private fun setBottomSheet() {
        val height = getDisplaySize(this).height

        val peekHeight = (height * 80 / 100)
        sheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        sheetBehavior?.addBottomSheetCallback(sheetCallback)
        setBottomSheetProperties(
            sheetBehavior, peekHeight, true, BottomSheetBehavior.STATE_EXPANDED
        )
    }

    private fun closeSheetWhenClickOutSide(
        sheetBehavior: BottomSheetBehavior<View>?,
        sheet: View,
        event: MotionEvent,
    ) {
        if (sheetBehavior!!.state == BottomSheetBehavior.STATE_COLLAPSED) {
            val outRect = Rect()
            sheet.getGlobalVisibleRect(outRect)
            if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                sheetBehavior.dismiss()
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        onBackPressedCloseSheet()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            closeExerciseSheet(ev)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun onSheetClose() {
        val result = Intent().apply {
            if (isAnime) {
                putExtra(Const.BundleExtras.EXTRA_TYPE, animeType.search)
                putExtra(Const.BundleExtras.EXTRA_STATUS, animeStatus.search)
                putExtra(Const.BundleExtras.EXTRA_AGE_RATING, ageRating.search)
                putExtra(Const.BundleExtras.EXTRA_ORDER_BY, animeOrderBy.search)
            } else {
                putExtra(Const.BundleExtras.EXTRA_TYPE, mangaType.search)
                putExtra(Const.BundleExtras.EXTRA_STATUS, mangaStatus.search)
                putExtra(Const.BundleExtras.EXTRA_ORDER_BY, mangaOrderBy.search)
            }
            putExtra(Const.BundleExtras.EXTRA_IS_APPROVED, isUnapprovedChecked)
            putExtra(Const.BundleExtras.EXTRA_SORT, sortType.search)
            putExtra(Const.BundleExtras.EXTRA_QUERY, query)
            putExtra(Const.BundleExtras.EXTRA_START_DATE, startDate)
            putExtra(Const.BundleExtras.EXTRA_END_DATE, endDate)
            putExtra(Const.BundleExtras.EXTRA_WITH_RESULT, withResult)
            putExtra(Const.BundleExtras.EXTRA_CLEAR_ALL, clearAll)
        }
        setResult(RESULT_OK, result)
        finish()
    }

    companion object {
        fun createSelectItemBottomSheet(
            context: Context?,
            isAnime: Boolean,
            isUnapproved: Boolean = false,
            query: String? = null,
            type: String? = null,
            status: String? = null,
            ageRating: String? = null,
            orderBy: String? = null,
            sortBy: String? = null,
            startDate: String? = null,
            endDate: String? = null,
        ): Intent {
            val intent = Intent(context, FilterDialogActivity::class.java).apply {
                putExtra(Const.BundleExtras.EXTRA_IS_ANIME, isAnime)
                putExtra(Const.BundleExtras.EXTRA_IS_APPROVED, isUnapproved)
                putExtra(Const.BundleExtras.EXTRA_QUERY, query)
                putExtra(Const.BundleExtras.EXTRA_TYPE, type)
                putExtra(Const.BundleExtras.EXTRA_STATUS, status)
                putExtra(Const.BundleExtras.EXTRA_AGE_RATING, ageRating)
                putExtra(Const.BundleExtras.EXTRA_ORDER_BY, orderBy)
                putExtra(Const.BundleExtras.EXTRA_SORT, sortBy)
                putExtra(Const.BundleExtras.EXTRA_START_DATE, startDate)
                putExtra(Const.BundleExtras.EXTRA_END_DATE, endDate)
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            return intent
        }
    }

}
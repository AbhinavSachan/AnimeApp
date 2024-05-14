package com.abhinavdev.animeapp.util.ui

import android.content.Context
import android.content.res.ColorStateList
import android.os.Handler
import android.os.Looper
import android.text.InputFilter
import android.view.LayoutInflater
import androidx.core.widget.addTextChangedListener
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.CustomPaginationLayoutBinding
import com.abhinavdev.animeapp.databinding.DialogPickPageBinding
import com.abhinavdev.animeapp.ui.main.MainActivity
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.extension.ViewUtil
import com.abhinavdev.animeapp.util.extension.applyColor
import com.abhinavdev.animeapp.util.extension.disableError
import com.abhinavdev.animeapp.util.extension.hideKeyboard
import com.abhinavdev.animeapp.util.extension.removeSpace
import com.abhinavdev.animeapp.util.extension.showError
import com.abhinavdev.animeapp.util.extension.showOrHide
import com.google.android.material.bottomsheet.BottomSheetDialog

private typealias OnClickListener = () -> Unit

class PaginationViewHelper(
    private val binding: CustomPaginationLayoutBinding, private val context: Context
) {

    private var isNextBtnEnabled = false
    private var isPreviousBtnEnabled = false

    init {
        setPageText()
        setEditButtonVisible(false)
        setNextButtonEnabled(false)
        setPreviousButtonEnabled(false)
        val padding = binding.clPagination.paddingBottom
        ViewUtil.setOnApplyUiInsetsListener(binding.clPagination) { insets ->
            ViewUtil.setBottomPadding(binding.clPagination, padding + insets.bottom)
        }
    }

    fun setPageText(pageNo: Int = 0) {
        val page =
            if (pageNo == 0) Const.Other.UNKNOWN_CHAR else "${context.getString(R.string.msg_page)} $pageNo"
        binding.tvPageNo.text = page
    }

    fun onNextPageClick(onClick: OnClickListener) {
        binding.btnNextPage.setOnClickListener {
            if (isNextBtnEnabled) {
                onClick.invoke()
            }
        }
    }

    fun onPreviousPageClick(onClick: OnClickListener) {
        binding.btnPreviousPage.setOnClickListener {
            if (isPreviousBtnEnabled) {
                onClick.invoke()
            }
        }
    }

    fun onEditPageClick(onClick: OnClickListener) {
        binding.ivEditPageNo.setOnClickListener { onClick.invoke() }
    }

    fun createEditPageDialog(lastPage: Int, onClick:(Int)->Unit): BottomSheetDialog {
        val pickPageDialog = BottomSheetDialog(context, R.style.NoBackGroundBottomSheetDialog)
        val view = DialogPickPageBinding.inflate(LayoutInflater.from(context))

        with(view) {
            val hint = if (lastPage == 1) {
                etPageNo.isEnabled = false
                context.getString(R.string.msg_only_single_page)
            } else {
                etPageNo.isEnabled = true
                "${context.getString(R.string.msg_between)} (1 to $lastPage)"
            }
            tilPageNo.hint = hint
            etPageNo.requestFocus()
            val removeFilter = InputFilter { s, _, _, _, _, _ -> s.toString().removeSpace() }
            etPageNo.apply { filters = filters.plus(removeFilter) }
        }

        view.etPageNo.addTextChangedListener {
            if (!it?.toString().isNullOrBlank()){
                view.tilPageNo.disableError()
            }
        }

        view.btnNegative.setOnClickListener {
            pickPageDialog.dismiss()
        }

        view.btnPositive.setOnClickListener {
            val pageNoString = view.etPageNo.text?.toString()
            pageNoString?.toIntOrNull()?.takeIf { it in 1 ..lastPage }?.run {
                (context as? MainActivity?)?.hideKeyboard(view.etPageNo)
                pickPageDialog.dismiss()
                onClick.invoke(this)
            } ?: view.tilPageNo.showError(Handler(Looper.getMainLooper()), context.getString(R.string.msg_invalid_range))
        }

        pickPageDialog.setContentView(view.root)
        pickPageDialog.show()
        return pickPageDialog
    }

    fun setEditButtonVisible(visible: Boolean) {
        binding.ivEditPageNo.showOrHide(visible)
    }

    fun setNextButtonEnabled(enabled: Boolean) {
        isNextBtnEnabled = enabled
        if (enabled) {
            binding.ivNextPage.imageTintList =
                ColorStateList.valueOf(context.applyColor(R.color.onAccent))
            binding.btnNextPage.setBackgroundResource(R.drawable.bg_accent_button)
            binding.tvNextPage.setTextColor(context.applyColor(R.color.onAccent))
        } else {
            binding.ivNextPage.imageTintList =
                ColorStateList.valueOf(context.applyColor(R.color.muted))
            binding.btnNextPage.setBackgroundResource(R.drawable.bg_primary_bor_accent_rounded_disabled)
            binding.tvNextPage.setTextColor(context.applyColor(R.color.muted))
        }
    }

    fun setPreviousButtonEnabled(enabled: Boolean) {
        isPreviousBtnEnabled = enabled
        if (enabled) {
            binding.ivPreviousPage.imageTintList =
                ColorStateList.valueOf(context.applyColor(R.color.onAccent))
            binding.btnPreviousPage.setBackgroundResource(R.drawable.bg_accent_button)
            binding.tvPreviousPage.setTextColor(context.applyColor(R.color.onAccent))
        } else {
            binding.ivPreviousPage.imageTintList =
                ColorStateList.valueOf(context.applyColor(R.color.muted))
            binding.btnPreviousPage.setBackgroundResource(R.drawable.bg_primary_bor_accent_rounded_disabled)
            binding.tvPreviousPage.setTextColor(context.applyColor(R.color.muted))
        }
    }
}
package com.abhinavdev.animeapp.ui.more.misc

import android.content.Context
import android.content.res.ColorStateList
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.CustomPaginationLayoutBinding
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.extension.applyColor
import com.abhinavdev.animeapp.util.extension.showOrHide

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
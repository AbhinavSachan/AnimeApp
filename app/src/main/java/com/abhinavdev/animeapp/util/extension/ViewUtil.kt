package com.abhinavdev.animeapp.util.extension

import android.content.res.Resources
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.view.WindowInsets
import android.widget.LinearLayout
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private typealias InsetsUpdateListener<I> = (I) -> Unit

object ViewUtil {
    private val UI_INSETS =
        WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
    private const val TAG = "ViewUtil"

    fun setLeftMargin(view: View, margin: Int): Boolean {
        val margins = getMargins(view) ?: return false
        margins.leftMargin = margin
        view.layoutParams = margins
        return true
    }

    fun createLinearParams(width: Int, height: Int): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(width, height)
    }

    fun createMarginParams(width: Int, height: Int): ViewGroup.MarginLayoutParams {
        return ViewGroup.MarginLayoutParams(width, height)
    }

    fun setWeight(view: View, weight: Float): Boolean {
        val params = view.layoutParams
        if (params is LinearLayout.LayoutParams) {
            params.weight = weight
            view.layoutParams = params
            return true
        }
        return false
    }

    fun setScale(view: View, scale: Float) {
        view.scaleX = scale
        view.scaleY = scale
    }

    fun getScale(view: View): Float {
        return (view.scaleX + view.scaleY) / 2
    }

    fun setMargin(view: View, margin: Int): Boolean {
        val margins = getMargins(view) ?: return false
        margins.setMargins(margin, margin, margin, margin)
        view.layoutParams = margins
        return true
    }

    fun setPadding(view: View, padding: Int) {
        view.setPadding(padding, padding, padding, padding)
    }

    fun setPadding(view: View, horizontal: Int, vertical: Int) {
        view.setPadding(horizontal, vertical, horizontal, vertical)
    }

    fun setTopMargin(params: ViewGroup.LayoutParams, margin: Int): Boolean {
        if (params is ViewGroup.MarginLayoutParams) {
            params.topMargin = margin
            return true
        }
        return false
    }

    fun removeParent(view: View): Boolean {
        if (view.parent is ViewManager) {
            (view.parent as ViewManager).removeView(view)
            return true
        }
        return false
    }

    fun setVerticalMargin(params: ViewGroup.LayoutParams, margin: Int) {
        if (params is ViewGroup.MarginLayoutParams) {
            params.topMargin = margin
            params.bottomMargin = margin
        }
    }

    fun setHorizontalMargin(params: ViewGroup.LayoutParams, margin: Int): Boolean {
        if (params is ViewGroup.MarginLayoutParams) {
            params.rightMargin = margin
            params.leftMargin = margin
            return true
        }
        return false
    }

    interface UseLayoutParamsCallback<T : ViewGroup.LayoutParams> {
        fun onUse(params: T)
    }

    fun <T : ViewGroup.LayoutParams> useLayoutParams(
        view: View, callback: UseLayoutParamsCallback<T>
    ): Boolean {
        return try {
            val params = view.layoutParams ?: return false
            @Suppress("UNCHECKED_CAST") callback.onUse(params as T)
            view.layoutParams = params
            true
        } catch (e: ClassCastException) {
            Log.e(TAG, "Failed to cast layout params!", e)
            false
        }
    }

    fun setVerticalMargin(view: View, margin: Int): Boolean {
        val margins = getMargins(view) ?: return false
        margins.topMargin = margin
        margins.bottomMargin = margin
        view.layoutParams = margins
        return true
    }

    fun setRightMargin(view: View, margin: Int): Boolean {
        val margins = getMargins(view) ?: return false
        margins.rightMargin = margin
        view.layoutParams = margins
        return true
    }

    fun setStartMargin(view: View, margin: Int) {
        val margins = getMargins(view) ?: return
        margins.marginStart = margin
        view.layoutParams = margins
    }

    fun setHorizontalMargin(view: View, margin: Int): Boolean {
        val margins = getMargins(view) ?: return false
        margins.leftMargin = margin
        margins.rightMargin = margin
        view.layoutParams = margins
        return true
    }

    fun setHorizontalMargin(view: View, left: Int, right: Int) {
        val margins = getMargins(view) ?: return
        margins.leftMargin = left
        margins.rightMargin = right
        view.layoutParams = margins
    }

    fun setLeftPadding(view: View, padding: Int) {
        view.setPadding(padding, view.paddingTop, view.paddingRight, view.paddingBottom)
    }

    fun setRightPadding(view: View, padding: Int) {
        view.setPadding(view.paddingLeft, view.paddingTop, padding, view.paddingBottom)
    }

    fun setTopPadding(view: View, padding: Int) {
        view.setPadding(view.paddingLeft, padding, view.paddingRight, view.paddingBottom)
    }

    fun setVerticalPadding(view: View, padding: Int) {
        view.setPadding(view.paddingLeft, padding, view.paddingRight, padding)
    }

    fun setBottomPadding(view: View, padding: Int) {
        setBottomPadding(view, padding, true)
    }

    fun setBottomPadding(view: View, padding: Int, getOther: Boolean) {
        view.setPadding(
            if (getOther) view.paddingLeft else 0,
            if (getOther) view.paddingTop else 0,
            if (getOther) view.paddingRight else 0,
            padding
        )
    }

    fun setHorizontalPadding(view: View, padding: Int) {
        view.setPadding(padding, view.paddingTop, padding, view.paddingBottom)
    }

    fun setHorizontalPadding(view: View, left: Int, right: Int) {
        view.setPadding(left, view.paddingTop, right, view.paddingBottom)
    }

    fun setEndMargin(view: View, margin: Int) {
        val margins = getMargins(view) ?: return
        margins.marginEnd = margin
        view.layoutParams = margins
    }

    fun setTopMargin(view: View, margin: Int) {
        val params = view.layoutParams ?: return
        setTopMargin(params, margin)
        view.layoutParams = params
    }

    fun setBottomMargin(view: View, margin: Int) {
        val margins = getMargins(view) ?: return
        margins.bottomMargin = margin
        view.layoutParams = margins
    }

    /**
     * Automatically calls a listener.
     * Provided insets will be used on the first call
     */
    fun setOnApplyInsetsListener(view: View, listener: InsetsUpdateListener<WindowInsetsCompat>) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            listener.invoke(insets)
            WindowInsetsCompat.CONSUMED
        }
    }

    fun setOnApplyUiInsetsListener(
        view: View, listener: InsetsUpdateListener<Insets>, parentView: View?
    ) {
        if (parentView == null) {
            setOnApplyUiInsetsListener(view, listener)
            return
        }
        setOnApplyUiInsetsListener(view, listener, parentView.rootWindowInsets)
    }

    fun setOnApplyUiInsetsListener(
        view: View, listener: InsetsUpdateListener<Insets>, rootInsets: WindowInsets?
    ) {
        var rootInsets1 = rootInsets
        if (rootInsets1 == null && view.parent is View) {
            rootInsets1 = (view.parent as View).rootWindowInsets
        }
        rootInsets1?.let {
            val uiInsets = WindowInsetsCompat.toWindowInsetsCompat(it).getInsets(UI_INSETS)
            listener.invoke(uiInsets)
        }
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val uiInsets = insets.getInsets(UI_INSETS)
            listener.invoke(uiInsets)
            WindowInsetsCompat.CONSUMED
        }
    }

    fun setOnApplyUiInsetsListener(view: View, listener: InsetsUpdateListener<Insets>) {
        setOnApplyUiInsetsListener(view, listener, view.rootWindowInsets)
    }

    fun getMargins(view: View): ViewGroup.MarginLayoutParams? {
        val params = view.layoutParams
        return if (params is ViewGroup.MarginLayoutParams) params else null
    }

    fun dpPx(dp: Int): Int {
        val metrics = Resources.getSystem().displayMetrics
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), metrics
            )
        )
    }

    fun spPx(sp: Int): Int {
        val metrics = Resources.getSystem().displayMetrics
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), metrics
            )
        )
    }
}

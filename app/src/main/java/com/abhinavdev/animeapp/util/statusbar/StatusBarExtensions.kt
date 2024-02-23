package com.abhinavdev.animeapp.util.statusbar

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.abhinavdev.animeapp.util.statusbar.StatusBarColors.getDefaultStatusBarHeight

/**
 * Extension function on an [Activity] to get the current color of the status bar.
 *
 * If the return value is 0, then the status bar is transparent.
 * @return The current color of the status bar for this [Activity].
 * @see StatusBarColors.getStatusBarColor
 */
fun Activity.getStatusBarColor(): Int {
    return this.window.statusBarColor
}

/**
 * Extension function on an [Activity] to set the color of the Status bar.
 * @param color The color to set as the Status bar background color.
 * @param alpha The alpha transparency to apply on the [color].
 * @param statusBarColorChangeListener Listener for changes to the status bar color.
 * @see StatusBarColors.setStatusBarColor
 */
fun Activity.setStatusBarColor(
    @ColorInt color: Int,
    @IntRange(from = 0, to = 255) alpha: Int = StatusBarColors.DEFAULT_ALPHA,
    statusBarColorChangeListener: StatusBarColors.OnStatusBarColorChangeListener? = null
) {
    window.apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            statusBarColor = StatusBarColors.parseColor(color, alpha)
        } else {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            StatusBarColors.setTranslucentView(window.decorView as ViewGroup, color, alpha)
            StatusBarColors.setRootView(this@setStatusBarColor, true)
        }
    }
    StatusBarColors.let {
        StatusBarColors.statusBarColorChangeListener?.apply {
            onColorChange(color)
            onGradientColorChange(null)
        }
        StatusBarColors.transparencyChangeListener?.onTransparencyChange(false)
    }
    statusBarColorChangeListener?.apply {
        onColorChange(color)
        onGradientColorChange(null)
    }
}

/**
 * Extension function on an [Activity] to set the color of the Status bar to a gradient color.
 *
 * The [view] parameter **MUST** have a background which is a [GradientDrawable] unless an [IllegalStateException] is thrown.
 *
 *               <androidx.appcompat.widget.Toolbar
 *                android:id="@+id/toolbar"
 *                android:layout_width="match_parent"
 *                android:layout_height="?attr/actionBarSize"
 *                android:background="@color/blue"/>`
 *
 *                val mToolbar = findViewById<Toolbar>(R.id.toolbar)
 *                val colors = intArrayOf(0xff000000, 0xff000000)
 *                val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
 *                mToolbar.background = gradientDrawable
 *                StatusBarColors.setGradientColor(activity, mToolbar)
 * @param view The view anchored directly at the bottom of the status bar
 * with a gradient colored background to sync with the status bar preferably a [Toolbar].
 * @see StatusBarColors.setGradientColor
 */
fun Activity.setGradientColor(
    view: View,
    statusBarColorChangeListener: StatusBarColors.OnStatusBarColorChangeListener? = null
) {
    require(view.background is GradientDrawable) { "The background of the view is not a Gradient Drawable." }
    val decorView = window.decorView as ViewGroup
    val fakeStatusBarView = decorView.findViewById<View>(android.R.id.custom)
    fakeStatusBarView?.let {
        decorView.removeView(it)
    }
    val background = (view.background as GradientDrawable)
    StatusBarColors.let {
        StatusBarColors.setRootView(this, false)
        StatusBarColors.setTransparentForWindow(this)
        StatusBarColors.setPaddingTop(this, view)
        StatusBarColors.statusBarColorChangeListener?.onGradientColorChange(background)
    }
    statusBarColorChangeListener?.onGradientColorChange(background)
}

/**
 * Extension function on an [Activity] to set the background color of the status bar to transparent.
 *
 * This function removes the padding set by the status bar and fits the content anchored below
 * the status bar to the top of the screen.
 * @see WindowCompat.setDecorFitsSystemWindows
 * @see StatusBarColors.setTransparentForWindow
 */
fun Activity.setTransparentForWindow(transparencyChangeListener: StatusBarColors.OnTransparencyChangeListener? = null) {
    this.window.apply {
        statusBarColor = Color.TRANSPARENT
        WindowCompat.setDecorFitsSystemWindows(this, false)
    }
    StatusBarColors.transparencyChangeListener?.onTransparencyChange(true)
    transparencyChangeListener?.onTransparencyChange(true)
}

/**
 * Extension function on an [Activity] to insets a top padding using the [getDefaultStatusBarHeight] on the [view].
 *
 * The implementation of this method is in relation to the [view] parameter which is assumed
 * to be directly anchored below the status bar (usually a [Toolbar]). It gives the [view] a top margin space which is the same dimension occupied
 * by a status bar.
 * Use cases can be in scenarios where the status bar has been made transparent or removed and a top padding is still needed at the topmost [View]
 * to give the illusion that the status bar is still there.
 * @param view The [View] which a top padding should be added.
 * @see StatusBarColors.setPaddingTop
 */
fun Activity.setPaddingTop(view: View) {
    view.apply {
        layoutParams?.let {
            if (it.height > 0 && paddingTop == 0) {
                it.height += getDefaultStatusBarHeight(this@setPaddingTop)
                setPadding(
                    paddingLeft, paddingTop + getDefaultStatusBarHeight(this@setPaddingTop),
                    paddingRight, paddingBottom
                )
            }
        }
    }
}

fun Activity.setStatusBarIconsDark(value: Boolean) {
    if (value) StatusBarColors.setDarkStatusBarIcons(this)
    else StatusBarColors.setLightStatusBarIcons(this)
}

fun Fragment.setStatusBarColorAndIconDark(color: Int) {
    activity?.apply {
        setStatusBarColor(color)
        setStatusBarIconsDark(DesignColors.Util.isDarkColor(color))
    }
}

fun Activity.setStatusBarColorAndIconDark(color: Int) {
    setStatusBarColor(color)
    setStatusBarIconsDark(DesignColors.Util.isDarkColor(color))
}

// Function to change status bar color with animation
fun Activity.animateStatusBarColor(startColor: Int, endColor: Int, duration: Long = 400L) {
    val colorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor)
    colorAnimator.addUpdateListener { animator ->
        val color = animator.animatedValue as Int
        setStatusBarColorPrivate(color)
    }
    colorAnimator.duration = duration
    colorAnimator.start()
}

// Function to set status bar color
private fun Activity.setStatusBarColorPrivate(color: Int) {
    val window: Window = window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = color
}


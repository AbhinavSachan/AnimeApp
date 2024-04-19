package com.abhinavdev.animeapp.util.extension

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.Selection
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Size
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.io.ByteArrayOutputStream
import kotlin.math.ceil


fun Context.applyColor(@ColorRes resId: Int): Int {
    return ResourcesCompat.getColor(resources, resId, null)
}

fun Fragment.applyColor(@ColorRes resId: Int): Int {
    return ResourcesCompat.getColor(resources, resId, null)
}

fun Context.applyDrawable(@DrawableRes resId: Int): Drawable? {
    return ResourcesCompat.getDrawable(resources, resId, null)
}

fun Fragment.applyDrawable(@DrawableRes resId: Int): Drawable? {
    return ResourcesCompat.getDrawable(resources, resId, null)
}

fun Bitmap.getCircularBitmap(): Bitmap {
    val output = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    val paint = Paint()
    val rect = Rect(0, 0, this.width, this.height)
    val centerX = this.width / 2f
    val centerY = this.height / 2f
    val radius = Math.min(centerX, centerY)
    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = Color.WHITE
    canvas.drawCircle(centerX, centerY, radius, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, rect, rect, paint)
    return output
}

fun Bitmap.compressBitmap(quality: Int): Bitmap {
    val outputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
    val byteArray = outputStream.toByteArray()
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.isHidden(): Boolean {
    return visibility == View.GONE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.isInvisible(): Boolean {
    return visibility == View.INVISIBLE
}

fun View.showOrHide(shouldShow: Boolean) {
    if (shouldShow) this.show() else this.hide()
}

fun View.showOrInvisible(shouldShow: Boolean) {
    if (shouldShow) this.show() else this.invisible()
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.zeroAlpha() {
    alpha = 0f
}

fun View.oneAlpha() {
    alpha = 1f
}

fun Activity.hideKeyboard(view: View? = currentFocus) {
    if (currentFocus != null) {
        val manager: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}

fun Activity.hideKeyBoard() {
    val mInputMethodManager: InputMethodManager =
        getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view: View? = currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    mInputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.showKeyboard(view: View? = activity?.currentFocus) {
    if (activity?.currentFocus != null) {
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}

fun Fragment.hideKeyboard(view: View? = activity?.currentFocus) {
    if (activity?.currentFocus != null) {
        val manager: InputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}

fun ScrollView.scrollToBottom() {
    val lastChild = getChildAt(childCount - 1)
    val bottom = lastChild.bottom + paddingBottom
    val delta = bottom - (scrollY + height)
    smoothScrollBy(0, delta)
}

fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {

            override fun updateDrawState(textPaint: TextPaint) {
                // use this to change the link color
                textPaint.color = textPaint.linkColor
                // toggle below value to enable/disable
                // the underline shown below the clickable text
                textPaint.isUnderlineText = true
            }

            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        val startIndexOfLink = this.text.toString().indexOf(link.first)
        spannableString.setSpan(
            clickableSpan,
            startIndexOfLink,
            startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod =
        LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}

fun View.getBackgroundColor() = (background as? ColorDrawable?)?.color ?: Color.TRANSPARENT

fun EditText.showErrorIfEmpty(error: String = "Required"): Boolean {
    if (this.text.isNullOrEmpty()) {
        this.error = error
        this.requestFocus()
        return true
    }
    return false
}

fun TextView.isEllipsized(): Boolean {
    val textPixelLength = paint.measureText(text.toString())
    val numberOfLines = ceil((textPixelLength / measuredWidth).toDouble())
    return lineHeight * numberOfLines > measuredHeight
}

fun increaseViewSize(view: View, width: Int, height: Int) {
    val vaWidth = ValueAnimator.ofInt(view.measuredWidth, width)
    vaWidth.duration = 2000
    vaWidth.addUpdateListener {
        val animatedValue = vaWidth.animatedValue as Int
        val layoutParams = view.layoutParams
        layoutParams.width = animatedValue
        view.layoutParams = layoutParams
    }

    val vaHeight = ValueAnimator.ofInt(view.measuredHeight, height)
    vaHeight.duration = 2000
    vaHeight.addUpdateListener {
        val animatedValue = vaHeight.animatedValue as Int
        val layoutParams = view.layoutParams
        layoutParams.height = animatedValue
        view.layoutParams = layoutParams
    }

    vaWidth.start()
    vaHeight.start()
}

fun AppCompatTextView.addRedAsterisk() {
    val name = text

    // Create a SpannableString to apply color only to the asterisk
    val spannableString = SpannableString("$name *")

    // Set the color of the asterisk to red
    spannableString.setSpan(
        ForegroundColorSpan(Color.RED),
        spannableString.length - 1,
        spannableString.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    // Set the modified text to the TextView
    text = spannableString
}

fun TextView.addRedAsterisk() {
    val name = text

    // Create a SpannableString to apply color only to the asterisk
    val spannableString = SpannableString("$name *")

    // Set the color of the asterisk to red
    spannableString.setSpan(
        ForegroundColorSpan(Color.RED),
        spannableString.length - 1,
        spannableString.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    // Set the modified text to the TextView
    text = spannableString
}

fun View?.getSizeOfView(onSizeReady: (Size) -> Unit) {
    this?.let { view ->
        val vto: ViewTreeObserver = view.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (view.width != 0 && view.height != 0) {
                    val size = Size(view.width, view.height)
                    onSizeReady(size) // Notify the caller with the size
                }
                // Remove the listener to avoid multiple callbacks
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }
}

fun Context.inflateLayoutAsync(
    @LayoutRes layoutRes: Int, container: ViewGroup?, onLayoutInflated: (View) -> Unit
) {
    val asyncLayoutInflater = AsyncLayoutInflater(this)

    asyncLayoutInflater.inflate(layoutRes, container) { view, _, _ ->
        onLayoutInflated(view)
    }
}

fun Drawable.toUnscaledBitmap(): Bitmap? {
    if (this is BitmapDrawable) {
        return this.bitmap
    }

    val bitmap = Bitmap.createBitmap(
        intrinsicWidth, intrinsicHeight, Bitmap.Config.RGB_565
    )

    val canvas = Canvas(bitmap)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)

    return bitmap
}

fun pxToDp(resources: Resources, px: Int): Float {
    val density = resources.displayMetrics.density
    return px / density
}

fun View.fadOutAnimation(
    duration: Long = 300, visibility: Int = View.INVISIBLE, completion: (() -> Unit)? = null
) {
    animate().alpha(0f).setDuration(duration).withEndAction {
        this.visibility = visibility
        completion?.let {
            it()
        }
    }
}

fun View.fadInAnimation(duration: Long = 300, completion: (() -> Unit)? = null) {
    alpha = 0f
    visibility = View.VISIBLE
    animate().alpha(1f).setDuration(duration).withEndAction {
        completion?.let {
            it()
        }
    }
}

fun ViewPager2.reduceDragSensitivity(f: Int = 4) {
    val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
    recyclerViewField.isAccessible = true
    val recyclerView = recyclerViewField.get(this) as RecyclerView

    val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
    touchSlopField.isAccessible = true
    val touchSlop = touchSlopField.get(recyclerView) as Int
    touchSlopField.set(recyclerView, touchSlop * f)       // "8" was obtained experimentally
}

fun BottomSheetBehavior<*>.dismiss() {
    this.state = BottomSheetBehavior.STATE_HIDDEN
}

fun BottomSheetBehavior<*>.collapse() {
    this.state = BottomSheetBehavior.STATE_COLLAPSED
}

fun BottomSheetBehavior<*>.expand() {
    this.state = BottomSheetBehavior.STATE_EXPANDED
}

fun BottomSheetBehavior<*>.isHidden(): Boolean {
    return this.state == BottomSheetBehavior.STATE_HIDDEN
}

fun BottomSheetBehavior<*>.isCollapsed(): Boolean {
    return this.state == BottomSheetBehavior.STATE_COLLAPSED
}

fun BottomSheetBehavior<*>.isExpanded(): Boolean {
    return this.state == BottomSheetBehavior.STATE_EXPANDED
}
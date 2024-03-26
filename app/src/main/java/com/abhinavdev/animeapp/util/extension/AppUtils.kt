package com.abhinavdev.animeapp.util.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.Html
import android.text.Spanned
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.util.AppTheme
import com.abhinavdev.animeapp.util.Const
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.Serializable
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale
import java.util.Timer
import java.util.TimerTask
import java.util.regex.Pattern
import kotlin.math.min


fun Fragment.getVersionName(): String? {
    return requireContext().packageManager?.getPackageInfo(
        requireContext().packageName, 0
    )?.versionName
}

fun AppCompatActivity.getVersionName(): String? {
    return packageManager?.getPackageInfo(packageName, 0)?.versionName
}

fun String.formatPriceWithoutCurrency(): String {
    val price = this.replace(",", "")
    val num = price.toFloat()

    return if (num % 1 == 0f)
        "%.0f".format(Locale.ENGLISH, num)
    else num.toString()
}

fun String.isEmailValid(): Boolean {
    val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,8}$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

@Suppress("DEPRECATION")
fun getDeviceWidth(activity: Activity): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = activity.windowManager.currentWindowMetrics
        val displayMetrics = activity.resources.displayMetrics

//		val pxHeight = windowMetrics.bounds.height()
        val pxWidth = windowMetrics.bounds.width()

        val density = displayMetrics.density

        ((pxWidth / density).toInt())
    } else {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.widthPixels
    }
}

@Suppress("DEPRECATION")
fun getDeviceHeight(activity: Activity): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = activity.windowManager.currentWindowMetrics
        val displayMetrics = activity.resources.displayMetrics

        val pxHeight = windowMetrics.bounds.height()
//		val pxWidth = windowMetrics.bounds.width()

        val density = displayMetrics.density

        ((pxHeight / density).toInt())
    } else {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.heightPixels
    }
}

fun Int.toPx(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()

fun Int.toDp(context: Context): Int = (this / context.resources.displayMetrics.density).toInt()

val Float.toPx get() = this * Resources.getSystem().displayMetrics.density

val Float.toDp get() = this / Resources.getSystem().displayMetrics.density

@Suppress("DEPRECATION")
fun setHtmlTexView(strData: String?): Spanned? {
    //android does not support \r\n so handle it manually
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(strData.toString().replace("\r\n", "<br/>"), Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(strData.toString().replace("\r\n", "<br/>"))
    }
}

fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
    if (view.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = view.layoutParams as ViewGroup.MarginLayoutParams
        p.setMargins(left, top, right, bottom)
        view.requestLayout()
    }
}

fun dpToPx(dp: Float, context: Context): Int {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

fun getFilePath(context: Context, contentUri: Uri): File? {
    try {
        val filePathColumn = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.TITLE,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
        )

        val returnCursor = contentUri.let {
            context.contentResolver.query(
                it,
                filePathColumn,
                null,
                null,
                null
            )
        }

        if (returnCursor != null) {
            returnCursor.moveToFirst()
            val nameIndex = returnCursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
            val name = returnCursor.getString(nameIndex)
            val file = File(context.cacheDir, name)
            val inputStream = context.contentResolver.openInputStream(contentUri)
            val outputStream = FileOutputStream(file)
            var read: Int
            val maxBufferSize = 1 * 1024 * 1024
            val bytesAvailable = inputStream!!.available()

            val bufferSize = min(bytesAvailable, maxBufferSize)
            val buffers = ByteArray(bufferSize)

            while (inputStream.read(buffers).also { read = it } != -1) {
                outputStream.write(buffers, 0, read)
            }

            inputStream.close()
            outputStream.close()
            return file
        } else {
            Log.d("", "returnCursor is null")
            return null
        }
    } catch (e: Exception) {
        Log.d("", "exception caught at getFilePath(): $e")
        return null
    }
}

fun convertToBase64(attachment: File): String {
    return Base64.encodeToString(attachment.readBytes(), Base64.NO_WRAP)
}

fun ViewPager2.enableAutoScroll(totalPages: Int, activity: Activity): Timer {
    val autoTimerTask = Timer()
    var currentPageIndex = currentItem
    autoTimerTask.schedule(object : TimerTask() {
        override fun run() {
            activity.runOnUiThread {
                currentItem = currentPageIndex++
            }
            if (currentPageIndex == totalPages) currentPageIndex = 0
        }
    }, 0, Const.TimeOut.BANNER_SWIPE_DELAY)

    // Stop auto paging when user touch the view
    /*getRecyclerView().setOnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_DOWN) autoTimerTask.cancel()
        false
    }*/

    return autoTimerTask // Return the reference for cancel
}

fun ViewPager2.getRecyclerView(): RecyclerView {
    val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
    recyclerViewField.isAccessible = true
    return recyclerViewField.get(this) as RecyclerView
}

/**
 * Rotate an image if required.
 *
 * @param img           The image bitmap
 * @param selectedImage Image URI
 * @return The resulted Bitmap after manipulation
 */
fun rotateImageIfRequired(context: Context, img: Bitmap, selectedImage: Uri): Bitmap? {
    try {
        val input: InputStream? = context.contentResolver.openInputStream(selectedImage)
        input?.let {
            val ei: ExifInterface? =
                if (Build.VERSION.SDK_INT > 23) ExifInterface(it)
                else selectedImage.path?.let { it1 -> ExifInterface(it1) }
            val orientation: Int? =
                ei?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            return when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90)
                ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180)
                ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270)
                else -> img
            }
        }
    } catch (e: Exception) {
    }
    return null
}

fun <T> addAllListTogetherAndJoinStringWithComma(
    vararg lists: List<List<T>>,
    post: (T) -> String
): String {
    val mixList = arrayListOf<T>()
    lists.forEach {
        it.forEach { item ->
            mixList.addAll(item)
        }
    }
    return mixList.joinToString(separator = ",") { post(it) }
}

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Flow<List<T>>.flattenToList() =
    flatMapConcat { it.asFlow() }.toList()

val LocalDate.yearMonth: YearMonth
    get() = YearMonth.of(year, month)

val YearMonth.nextMonth: YearMonth
    get() = this.plusMonths(1)

val YearMonth.previousMonth: YearMonth
    get() = this.minusMonths(1)

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(
        key,
        T::class.java
    )

    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}

fun getNonNullNumValue(value: String?): String {
    return if (value.isNullOrBlank()) {
        "0"
    } else {
        value
    }
}

fun Window.setTheme(theme: AppTheme) {
    this.setWindowAnimations(R.style.FadeAnimationDark)
    when (theme) {
        AppTheme.DARK -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            setSystemBarColor(this, theme)
        }

        AppTheme.LIGHT -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            setSystemBarColor(this, theme)
        }

        AppTheme.DEFAULT -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}

private fun setSystemBarColor(window: Window, theme: AppTheme) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val controller = try {
            window.insetsController
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        controller?.apply {
            when (theme) {
                AppTheme.DARK -> {
                    setSystemBarsAppearance(
                        0,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                    setSystemBarsAppearance(
                        0,
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                    )
                }

                AppTheme.LIGHT -> {
                    setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                    setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                    )
                }

                AppTheme.DEFAULT -> {}
            }
        }
    } else {
        when (theme) {
            AppTheme.DARK -> window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

            AppTheme.LIGHT -> window.clearFlags(window.decorView.systemUiVisibility)
            AppTheme.DEFAULT -> {}
        }
    }

}

fun String.removeSpace() = trim().replace("\\s+".toRegex(), replacement = "")
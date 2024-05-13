package com.abhinavdev.animeapp.util.extension

import android.app.Activity
import android.app.Application
import android.app.UiModeManager
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Insets
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
import android.util.Size
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.abhinavdev.animeapp.BuildConfig
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.appsettings.AppTheme
import com.abhinavdev.animeapp.util.statusbar.setStatusBarColorAndIconDark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.Serializable
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale
import java.util.Timer
import java.util.TimerTask
import java.util.regex.Pattern
import kotlin.coroutines.coroutineContext
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

fun String.formatToOneDigitAfterDecimalOrNull(): String? {
    val num = this.toFloat()

    return  try {
        "%.1f".format(Locale.ENGLISH, num)
    }catch (_:Exception){
        null
    }
}

fun Float.formatToOneDigitAfterDecimalOrNull(): String? {
    return try {
        "%.1f".format(Locale.ENGLISH, this)
    }catch (_:Exception){
        null
    }
}

fun String.isEmailValid(): Boolean {
    val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,8}$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    return matcher.matches()
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
    autoTimerTask.schedule(object : TimerTask() {
        override fun run() {
            var currentPageIndex = currentItem

            activity.runOnUiThread {
                currentItem = ++currentPageIndex
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
//fun rotateImageIfRequired(context: Context, img: Bitmap, selectedImage: Uri): Bitmap? {
//    try {
//        val input: InputStream? = context.contentResolver.openInputStream(selectedImage)
//        input?.let {
//            val ei: ExifInterface? =
//                if (Build.VERSION.SDK_INT > 23) ExifInterface(it)
//                else selectedImage.path?.let { it1 -> ExifInterface(it1) }
//            val orientation: Int? =
//                ei?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
//            return when (orientation) {
//                ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90)
//                ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180)
//                ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270)
//                else -> img
//            }
//        }
//    } catch (e: Exception) {
//    }
//    return null
//}

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
fun restartApp(context: Context) {
    val pm = context.packageManager

    val intent = pm.getLaunchIntentForPackage(context.packageName)
    val component = intent?.component

    val mainIntent = Intent.makeRestartActivityTask(component)
    mainIntent.setPackage(context.packageName)
    context.startActivity(mainIntent)

    Runtime.getRuntime().exit(0)
}
fun Activity.setThemeChangeAnimation(){
    this.window.setWindowAnimations(R.style.FadeAnimationDark)
}
fun setTheme(theme: AppTheme) {
    when (theme) {
        AppTheme.DARK -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        AppTheme.LIGHT -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        AppTheme.DEFAULT -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}
@RequiresApi(Build.VERSION_CODES.S)
fun Application.setApplicationTheme(theme: AppTheme) {
    val service  = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
    when (theme) {
        AppTheme.DARK -> {
            service.setApplicationNightMode(UiModeManager.MODE_NIGHT_YES)
        }

        AppTheme.LIGHT -> {
            service.setApplicationNightMode(UiModeManager.MODE_NIGHT_NO)
        }

        AppTheme.DEFAULT -> {
            service.setApplicationNightMode(UiModeManager.MODE_NIGHT_CUSTOM)
        }
    }
}

fun Activity.setStatusBarColorAsTheme(theme: AppTheme) {
    this.window.setWindowAnimations(R.style.FadeAnimationDark)
    when (theme) {
        AppTheme.DARK -> {
            setSystemBarColor(this.window, theme)
        }

        AppTheme.LIGHT -> {
            setSystemBarColor(this.window, theme)
        }

        AppTheme.DEFAULT -> {
            setStatusBarColorAndIconDark(applyColor(R.color.primary))
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

fun getDisplaySize(activity: Activity): Size {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val metrics1 = activity.windowManager.currentWindowMetrics
        // Gets all excluding insets
        val windowInsets = metrics1.windowInsets
        val insets: Insets =
            windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.statusBars())

        val insetsWidth: Int = insets.right + insets.left
        val insetsHeight: Int = insets.top + insets.bottom


        // Legacy size that Display#getSize reports
        val bounds = metrics1.bounds
        return Size(
            bounds.width() - insetsWidth, bounds.height() - insetsHeight
        )
    } else {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        return Size(
            metrics.widthPixels, metrics.heightPixels
        )
    }
}

/**
 * default placeholder is "-"
 */
fun String?.placeholder(placeholder: String = Const.Other.UNKNOWN_CHAR): String {
    return if (this.isNullOrBlank()) {
        placeholder
    } else {
        this
    }
}

fun String.getFirstCharactersOfWords(): String {
    return this.split(" ").joinToString("") { it.firstOrNull()?.toString() ?: "" }
}

fun createFileFromUri(context: Context, uri: Uri): File? {
    val fileName = getFileName(context.contentResolver, uri)
    fileName?.let { name ->
        context.contentResolver.openInputStream(uri)?.use { input ->
            val outputFile = File(context.cacheDir, name)
            FileOutputStream(outputFile).use { output ->
                val buffer = ByteArray(4 * 1024) // buffer size
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            return outputFile
        }
    }
    return null
}

fun getFileName(contentResolver: ContentResolver, uri: Uri): String? {
    val cursor = contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val displayName = it.getString(it.getColumnIndexOrThrow("_display_name"))
            if (displayName != null) {
                return displayName
            }
        }
    }
    return null
}

/** Open link in Chrome Custom Tabs */
fun Context.openCustomTab(url: String) {
    val colors = CustomTabColorSchemeParams.Builder()
        .setToolbarColor(ContextCompat.getColor(this, R.color.primary))
        .build()
    CustomTabsIntent.Builder()
        .setDefaultColorSchemeParams(colors)
        .build().apply {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            launchUrl(this@openCustomTab, Uri.parse(url))
        }
}

/** Open external link by default browser or intent chooser */
fun Context.openLink(url: String) {
    val uri = Uri.parse(url)
    Intent(Intent.ACTION_VIEW, uri).apply {
        val defaultBrowser =
            findBrowserIntentActivities(PackageManager.MATCH_DEFAULT_ONLY).firstOrNull()
        if (defaultBrowser != null) {
            try {
                setPackage(defaultBrowser.activityInfo.packageName)
                startActivity(this)
            } catch (e: ActivityNotFoundException) {
                startActivity(Intent.createChooser(this, null))
            }
        } else {
            val browsers = findBrowserIntentActivities(PackageManager.MATCH_ALL)
            val intents = browsers.map {
                Intent(Intent.ACTION_VIEW, uri).apply {
                    setPackage(it.activityInfo.packageName)
                }
            }
            startActivity(
                Intent.createChooser(this, null).apply {
                    putExtra(Intent.EXTRA_INITIAL_INTENTS, intents.toTypedArray())
                }
            )
        }
    }
}

/** Finds all the browsers installed on the device */
private fun Context.findBrowserIntentActivities(
    flags: Int = 0
): List<ResolveInfo> {
    val emptyBrowserIntent = Intent(Intent.ACTION_VIEW, Uri.fromParts("http", "", null))

    return packageManager
        .queryIntentActivitiesCompat(emptyBrowserIntent, flags)
        .filter { it.activityInfo.packageName != BuildConfig.APPLICATION_ID }
        .sortedBy { it.priority }
}

/** Custom compat method until Google decides to make one */
private fun PackageManager.queryIntentActivitiesCompat(intent: Intent, flags: Int = 0) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(flags.toLong()))
    } else {
        queryIntentActivities(intent, flags)
    }

fun Context.openShareSheet(url: String) {
    Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
        startActivity(Intent.createChooser(this, null))
    }
}

fun Context.copyToClipBoard(text: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    clipboard?.setPrimaryClip(ClipData.newPlainText("title", text))
    toast(getString(R.string.copied))
}

fun Window.hideStatusBar(hide:Boolean){
    if (hide) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            insetsController?.hide(WindowInsetsCompat.Type.statusBars())
        }else{
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }else{
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            insetsController?.show(WindowInsetsCompat.Type.statusBars())
        }
    }
}

fun Window.hideNavigationBar(hide:Boolean){
    if (hide){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            insetsController?.hide(WindowInsetsCompat.Type.navigationBars())
        }else{
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }else{
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            insetsController?.show(WindowInsetsCompat.Type.navigationBars())
        }
    }
}
/**
 * Only proceed with the given action if the coroutine has not been cancelled.
 * Necessary because Flow.collect receives items even after coroutine was cancelled
 * https://github.com/Kotlin/kotlinx.coroutines/issues/1265
 */
suspend inline fun <T> Flow<T>.safeCollect(crossinline action: (T) -> Unit) {
    this.cancellable().onEach { coroutineContext.ensureActive() }.collect {
        CoroutineScope(Dispatchers.Main).launch {
            action(it)
        }
    }
}

fun getOrientation(): Int {
    return Resources.getSystem().configuration.orientation
}

fun Context.getConfiguration(): Configuration {
    return resources.configuration
}
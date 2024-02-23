package com.codelab.dietapp.util.extension


//import com.codelab.dietapp.ui.address.models.AddressData
//import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
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
import androidx.appcompat.app.AppCompatActivity
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage
import com.codelab.dietapp.R
import com.codelab.dietapp.ui.address.models.AddressData
import com.codelab.dietapp.util.Const
import com.codelab.dietapp.util.preferences.PrefUtils
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.Serializable
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.WeekFields
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

/*fun String.formatPrice(isEnglishLang: Boolean): String {
	val storeData: StoresData? = PrefUtils.getObject(Const.SharedPrefs.STORE_DATA, StoresData::class.java)
	var currencyCode = Const.Other.CURRENCY_CODE
	if (storeData != null) {
		currencyCode = if (isEnglishLang)
			storeData.currencyEn.toString()
		else storeData.currencyAr.toString()
	}

	val price = this.replace(",", "")
	val num = price.toFloat()
	return currencyCode + " %.2f".format(Locale.ENGLISH, num)
}*/

fun String.formatPrice(): String {
    var currencyCode = Const.Other.CURRENCY_CODE
    val selectedLangCode = PrefUtils.getString(Const.SharedPrefs.SELECTED_LANGUAGE_CODE)
    if (selectedLangCode == Const.Other.ARABIC_LANG_CODE)
        currencyCode = Const.Other.ARABIC_CURRENCY_CODE

    if (this.isBlank()) return "$currencyCode 0"

    val price = this.replace(",", "")
    val num = price.toFloat()
//	return format(Locale.ENGLISH, num) + " " + currencyCode
    return if (num % 1 == 0f)
        currencyCode + " " + "%.0f".format(Locale.ENGLISH, num)
    else
        currencyCode + " " + "%.2f".format(Locale.ENGLISH, num)
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

fun getCheckoutAddress(model: AddressData, context: Context): String {
    var strAddress = ""

    if (!model.blockName.isNullOrEmpty()) {
        strAddress += "${context.getString(R.string.msg_block)} ${model.blockName}, "
    }
    if (!model.street.isNullOrEmpty()) {
        strAddress += "${context.getString(R.string.msg_street)} ${model.street}, "
    }
    if (!model.avenue.isNullOrEmpty()) {
        strAddress += "${context.getString(R.string.msg_avenue)} ${model.avenue}, "
    }
    if (!model.building.isNullOrEmpty()) {
        strAddress += "${context.getString(R.string.msg_building)} ${model.building}, "
    }
    if (!model.floor.isNullOrEmpty()) {
        strAddress += "${context.getString(R.string.msg_floor)} ${model.floor}, "
    }
    if (!model.flat.isNullOrEmpty()) {
        strAddress += "${context.getString(R.string.msg_flat)} ${model.flat}"
    }

    if (!model.areaName.isNullOrEmpty()) {
        strAddress += "\n${model.areaName}"
    }
    if (!model.governorateName.isNullOrEmpty()) {
        strAddress += ", ${model.governorateName}"
    }
//	if (!model.country.isNullOrEmpty()) {
//		strAddress += ", ${model.country}"
//	}
//	if (!model.country.isNullOrEmpty()) {
//		strAddress += ", ${model.country}"
//	}

    return strAddress
}

fun getWebViewData(strData: String): String {
//    "<head><style>@font-face {font-family: 'PlusJakartaSans-Medium';src: url('file:///android_asset/plus_jakarta_sans_medium.ttf');}body {font-family: 'PlusJakartaSans-Medium';text-align:left;font-size:16px}</style></head>"
    val lang = PrefUtils.getString(Const.SharedPrefs.SELECTED_LANGUAGE_CODE)
    val direction = getLanguageDirection(lang)
    val fontName = getLanguageFontName(lang)
    val fontAssetName = getLanguageFontAssetName(lang)
    val strHead =
        "<head><style>@font-face {font-family: '$fontName';src: url('file:///android_asset/$fontAssetName');}body {font-family: '$fontName'}</style></head>"
    return "<html>$strHead<body dir=\"$direction\" style=\"font-family: $fontName\">${
        strData.replace(
            "font-family",
            ""
        )
    }</body></html>"
}

private fun getLanguageDirection(language: String?): String {
    return if(language == Const.Other.ARABIC_LANG_CODE){
        "rtl"
    }else{
        "ltr"
    }
}
private fun getLanguageFontName(language: String?): String {
    return if(language == Const.Other.ARABIC_LANG_CODE){
        "Cairo-Medium"
    }else{
        "Sen-Medium"
    }
}
private fun getLanguageFontAssetName(language: String?): String {
    return if(language == Const.Other.ARABIC_LANG_CODE){
        "cairo_medium.ttf"
    }else{
        "sen_medium.ttf"
    }
}

/*fun discountedPrice(context: Context, totalPrice: Double, disPrice: Double): String {
	val dis = (floor((disPrice * 100) / totalPrice)).toInt()
	return "${context.getString(R.string.action_save)} ${(100 - dis)}%"
}

fun cartDiscountedPrice(context: Context, totalPrice: Double, disPrice: Double): String {
	val dis = (floor((disPrice * 100) / totalPrice)).toInt()
	return "${(100 - dis)}% ${context.getString(R.string.msg_off)}"
}*/

//fun getNetPayableAmount(orderData: PreviousOrders, context: Context): String {
//	var netPayableAmount = 0.0f
//	if (orderData.orderTotalAmount?.isNotEmpty() == true) {
//		netPayableAmount = orderData.orderTotalAmount!!.toFloat()
//	}
//
//	if (orderData.walletAmountUsed?.isNotEmpty() == true) {
//		netPayableAmount -= orderData.walletAmountUsed!!.toFloat()
//	}
//	if (orderData.loyaltyPointsValue?.isNotEmpty() == true) {
//		netPayableAmount -= orderData.loyaltyPointsValue!!.toFloat()
//	}
//	return netPayableAmount.toString().formatPrice(context)
//}

//fun Context.formatOrderText(string: String?): String {
//    return "${resources.getString(R.string.txt_order)} $string"
//}

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


/*fun getCheckoutAddress(model: AddressData, context: Context): String {
	var strAddress = ""

	if (!model.blockName.isNullOrEmpty()) {
		strAddress += "${context.getString(R.string.hint_block)} ${model.blockName}, "
	}
	if (!model.street.isNullOrEmpty()) {
		strAddress += "${context.getString(R.string.hint_street)} ${model.street}, "
	}
	if (!model.avenue.isNullOrEmpty()) {
		strAddress += "${context.getString(R.string.hint_avenue)} ${model.avenue}, "
	}
	if (!model.building.isNullOrEmpty()) {
		strAddress += "${context.getString(R.string.hint_house_building_no)} ${model.building}, "
	}
	if (!model.floor.isNullOrEmpty()) {
		strAddress += "${context.getString(R.string.msg_floor)} ${model.floor}, "
	}
	if (!model.flat.isNullOrEmpty()) {
		strAddress += "${context.getString(R.string.msg_flat)} ${model.flat}"
	}

	if (!model.areaName.isNullOrEmpty()) {
		strAddress += "\n${model.areaName}"
	}
	if (!model.governorateName.isNullOrEmpty()) {
		strAddress += ", ${model.governorateName}"
	}
//	if (!model.country.isNullOrEmpty()) {
//		strAddress += ", ${model.country}"
//	}

	return strAddress
}*/

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

suspend fun <T> Flow<List<T>>.flattenToList() =
    flatMapConcat { it.asFlow() }.toList()

fun DayOfWeek.displayText(uppercase: Boolean = false): String {
    return getDisplayName(TextStyle.SHORT, Locale.ENGLISH).let { value ->
        if (uppercase) value.uppercase(Locale.ENGLISH) else value
    }
}

@JvmOverloads
fun daysOfWeek(firstDayOfWeek: DayOfWeek = firstDayOfWeekFromLocale()): List<DayOfWeek> {
    var selectedLangCode = PrefUtils.getString(Const.SharedPrefs.SELECTED_LANGUAGE_CODE)
    if (selectedLangCode.isNullOrEmpty()) selectedLangCode = Const.Other.ENGLISH_LANG_CODE
    val firstDay = WeekFields.of(Locale(selectedLangCode)).firstDayOfWeek

    val pivot = 7 - firstDay.ordinal
    val daysOfWeek = DayOfWeek.values()
    // Order `daysOfWeek` array so that firstDayOfWeek is at the start position.
    return (daysOfWeek.takeLast(pivot) + daysOfWeek.dropLast(pivot))
}

fun YearMonth.displayText(short: Boolean = false): String {
    return "${this.month.displayText(short = short)} ${this.year}"
}

fun Month.displayText(short: Boolean = true): String {
    var selectedLangCode = PrefUtils.getString(Const.SharedPrefs.SELECTED_LANGUAGE_CODE)
    if (selectedLangCode.isNullOrEmpty()) selectedLangCode = Const.Other.ENGLISH_LANG_CODE

    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return getDisplayName(style, Locale(selectedLangCode))
}

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

fun getDay(name: String, context: Context): String {
    return when (name) {
        DayOfWeek.SUNDAY.name -> context.getString(R.string.msg_sun)
        DayOfWeek.MONDAY.name -> context.getString(R.string.msg_mon)
        DayOfWeek.TUESDAY.name -> context.getString(R.string.msg_tue)
        DayOfWeek.WEDNESDAY.name -> context.getString(R.string.msg_wed)
        DayOfWeek.THURSDAY.name -> context.getString(R.string.msg_thu)
        DayOfWeek.FRIDAY.name -> context.getString(R.string.msg_fri)
        DayOfWeek.SATURDAY.name -> context.getString(R.string.msg_sat)
        else -> context.getString(R.string.msg_sun)
    }
}
fun getDayForApi(name: String, context: Context): String {
    return when (name) {
        DayOfWeek.SUNDAY.name -> Const.Type.SUN
        DayOfWeek.MONDAY.name -> Const.Type.MON
        DayOfWeek.TUESDAY.name -> Const.Type.TUE
        DayOfWeek.WEDNESDAY.name -> Const.Type.WED
        DayOfWeek.THURSDAY.name -> Const.Type.THU
        DayOfWeek.FRIDAY.name -> Const.Type.FRI
        DayOfWeek.SATURDAY.name -> Const.Type.SAT
        else -> Const.Type.SUN
    }
}
fun getNonNullNumValue(value:String?):String{
    return if (value.isNullOrBlank()){
        "0"
    }else{
        value
    }
}

fun String.removeSpace() = trim().replace("\\s+".toRegex(), replacement = "")

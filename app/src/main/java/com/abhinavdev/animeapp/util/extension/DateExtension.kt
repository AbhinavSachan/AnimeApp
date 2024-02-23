package com.abhinavdev.animeapp.util.extension

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * Pattern: yyyy-MM-dd HH:mm:ss
 */
fun Date.formatToServerDateTimeDefaults(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

fun Date.formatToTruncatedDateTime(): String {
    val sdf = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: yyyy-MM-dd
 */
fun Date.formatToServerDateDefaults(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: EE d'st' MMM yyyy
 */
fun String.customFormatToServerDateDefaults(): String {
    var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date: Date = sdf.parse(this)

    sdf = SimpleDateFormat("d", Locale.getDefault())
    val newDate: String = sdf.format(date)

    sdf = if (newDate.endsWith("1") && !newDate.endsWith("11")) SimpleDateFormat(
        "d'st' MMMM yyyy",
        Locale.getDefault()
    )
    else if (newDate.endsWith("2") && !newDate.endsWith("12")) SimpleDateFormat(
        "d'nd' MMMM yyyy",
        Locale.getDefault()
    )
    else if (newDate.endsWith("3") && !newDate.endsWith("13")) SimpleDateFormat(
        "d'rd' MMMM yyyy",
        Locale.getDefault()
    )
    else SimpleDateFormat("d'th' MMMM yyyy", Locale.getDefault())

    return sdf.format(date)
}

/**
 * Pattern: HH:mm:ss
 */
fun Date.formatToServerTimeDefaults(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: dd/MM/yyyy HH:mm:ss
 */
fun Date.formatToViewDateTimeDefaults(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: dd/MM/yyyy
 */
fun Date.formatToViewDateDefaults(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: HH:mm:ss
 */
fun Date.formatToViewTimeDefaults(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Add field date to current date
 */
fun Date.add(field: Int, amount: Int): Date {
    Calendar.getInstance().apply {
        time = this@add
        add(field, amount)
        return time
    }
}

fun Date.addYears(years: Int): Date {
    return add(Calendar.YEAR, years)
}

fun Date.addMonths(months: Int): Date {
    return add(Calendar.MONTH, months)
}

fun Date.addDays(days: Int): Date {
    return add(Calendar.DAY_OF_MONTH, days)
}

fun Date.addHours(hours: Int): Date {
    return add(Calendar.HOUR_OF_DAY, hours)
}

fun Date.addMinutes(minutes: Int): Date {
    return add(Calendar.MINUTE, minutes)
}

fun Date.addSeconds(seconds: Int): Date {
    return add(Calendar.SECOND, seconds)
}
/*
fun getFormattedDate(date: String): String {
    var sdf = SimpleDateFormat(Const.Other.API_RESPONSE_FORMAT, Locale.ENGLISH)
    val newDate: Date = sdf.parse(date) as Date
    sdf = SimpleDateFormat(Const.Other.START_DATE_FORMAT, Locale.ENGLISH)
    return "${sdf.format(newDate)}"
}

fun getServerFormatDate(date: String): String {
    var sdf = SimpleDateFormat(Const.Other.START_DATE_FORMAT, Locale.ENGLISH)
    val newDate: Date = sdf.parse(date) as Date
    sdf = SimpleDateFormat(Const.Other.API_RESPONSE_FORMAT, Locale.ENGLISH)
    return "${sdf.format(newDate)}"
}

fun getYearMonthFormatOrNull(date: String): String? {
    var sdf = SimpleDateFormat(Const.Other.API_RESPONSE_FORMAT, Locale.ENGLISH)
    val newDate: Date = try {
        sdf.parse(date) as Date
    } catch (e: Exception) {
        return null
    }
    sdf = SimpleDateFormat(Const.Other.YEAR_MONTH_FORMAT, Locale.ENGLISH)
    return try {
        sdf.format(newDate)
    } catch (e: Exception) {
        null
    }
}

fun getMealFormattedDate(date: Date): String {
    val sdf = SimpleDateFormat(Const.Other.API_RESPONSE_FORMAT, Locale.ENGLISH)
    return "${sdf.format(date)}"
}

fun getFormattedOrderDate(date: String): String {
    var sdf = SimpleDateFormat(Const.Other.DATE_FORMAT, Locale.ENGLISH)
    val newDate: Date = sdf.parse(date)
    sdf = SimpleDateFormat(Const.Other.ORDER_DATE_FORMAT, Locale.ENGLISH)
    return sdf.format(newDate)
}

fun getFormattedLiveDate(date: String): String {
    if (date.isNullOrEmpty()) return ""
    var sdf = SimpleDateFormat(Const.Other.API_RESPONSE_FORMAT, Locale.ENGLISH)
    val newDate: Date = sdf.parse(date)

    val format = SimpleDateFormat("d", Locale.ENGLISH)
    val fetchDate = format.format(newDate)
    sdf = when {
        fetchDate.endsWith("1") && !fetchDate.endsWith("11") -> SimpleDateFormat(
            "dd'st' MMMM yyyy",
            Locale.ENGLISH
        )

        fetchDate.endsWith("2") && !fetchDate.endsWith("12") -> SimpleDateFormat(
            "dd'nd' MMMM yyyy",
            Locale.ENGLISH
        )

        fetchDate.endsWith("3") && !fetchDate.endsWith("13") -> SimpleDateFormat(
            "dd'rd' MMMM yyyy",
            Locale.ENGLISH
        )

        else -> SimpleDateFormat("dd'th' MMMM yyyy", Locale.ENGLISH)
    }

    return sdf.format(newDate)
}

fun getFormattedTime(time: String): String {
    if (time.isNullOrEmpty()) return ""
    var sdf = SimpleDateFormat(Const.Other.TIME_FORMAT, Locale.ENGLISH)
    val startTime: Date = sdf.parse(time)
    sdf = SimpleDateFormat(Const.Other.CART_TIME_FORMAT, Locale.ENGLISH)
    return sdf.format(startTime)
}

fun getFormattedCartTime(time: String): String {
    if (time.isNullOrEmpty()) return ""
    val splitTime = time.split(" - ")
    var sdf = SimpleDateFormat(Const.Other.TIME_FORMAT, Locale.ENGLISH)
    val startTime: Date = sdf.parse(splitTime[0])
    val endTime: Date = sdf.parse(splitTime[1])
    sdf = SimpleDateFormat(Const.Other.CART_TIME_FORMAT, Locale.ENGLISH)
    return sdf.format(startTime) + " - " + sdf.format(endTime)
}

fun compareWithCurrentDate(date: String): Boolean {
    val sdf = SimpleDateFormat(Const.Other.DATE_FORMAT, Locale.ENGLISH)
    val strDate: Date = sdf.parse(date)
    return System.currentTimeMillis() > strDate.time
}
*/

/*fun getDuration(past: Date, now: Date, context: Context): String {
	var remainingTime = ""
	try {
//			val format = SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss")
//			val past = format.parse("2016.02.05 AD at 23:59:30")
//			val now = Date()
		val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
		val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
		val hours: Long = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
		val days: Long = TimeUnit.MILLISECONDS.toDays(now.time - past.time)
		when {
//				seconds < 60 -> {
//					println("$seconds seconds ago")
//				}
			minutes < 60 -> {
				remainingTime =
					"${minutes}${context.getString(R.string.msg_minute)} ${seconds % 60}${
						context.getString(R.string.msg_second)
					}"
//					println("$minutes minutes ago")
			}
			hours < 24 -> {
				remainingTime =
					"${hours}${context.getString(R.string.msg_hour)} ${minutes % 60}${
						context.getString(R.string.msg_minute)
					}"
//					println("$hours hours ago")
			}
			days < 7 -> {
				remainingTime =
					if (days > 1) "$days ${context.getString(R.string.msg_days)}"
					else "$days ${context.getString(R.string.msg_day)}"
			}
			else -> {
				remainingTime = when {
					days > 360 -> "${days / 360} ${context.getString(R.string.msg_years)}"
					days > 30 -> "${days / 30} ${context.getString(R.string.msg_months)}"
					else -> "${days / 7} ${context.getString(R.string.msg_week)}"
				}
//					println("$days days ago")
			}
		}
	} catch (j: Exception) {
		j.printStackTrace()
	}
	return remainingTime
}*/

fun getDurationListing(past: Date, now: Date, context: Context): String {
    var remainingTime = ""
    try {
//		val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
        val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
        val hours: Long = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
        val days: Long = TimeUnit.MILLISECONDS.toDays(now.time - past.time)

//		remainingTime = if (hours > 24) {
//			"${days}${context.getString(R.string.msg_day_d)} " +
//					"${hours % 24}${context.getString(R.string.msg_hour)} " +
//					"${minutes % 60}${context.getString(R.string.msg_minute)}"
//		} else if (hours > 0) {
//			"${hours % 24}${context.getString(R.string.msg_hour)} " +
//					"${minutes % 60}${context.getString(R.string.msg_minute)}"
//		} else {
//			"${minutes % 60}${context.getString(R.string.msg_minute)}"
//		}
    } catch (j: Exception) {
        j.printStackTrace()
    }
    return remainingTime
}


fun getDateDescription(timestamp: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = dateFormat.parse(timestamp)
    val calendar = Calendar.getInstance()
    calendar.time = date ?: Date()
    val today = Calendar.getInstance()

    return when {
        isSameDay(calendar, today) -> "Today"
        isSameDay(calendar, today.apply { add(Calendar.DAY_OF_YEAR, -1) }) -> "Yesterday"
        else -> "${getDaysDifference(calendar, today)} days ago"
    }
}

fun convertTimeFormat(timestamp: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    val date: Date = inputFormat.parse(timestamp) ?: Date()
    return outputFormat.format(date)
}

fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(
        Calendar.DAY_OF_YEAR
    )
}

fun getDaysDifference(cal1: Calendar, cal2: Calendar): Long {
    val startTime = cal1.timeInMillis
    val endTime = cal2.timeInMillis
    val diffTime = endTime - startTime
    return diffTime / (1000 * 60 * 60 * 24)
}
/*
@RequiresApi(Build.VERSION_CODES.O)
fun getYears(date: String): Int {
    val sdf = SimpleDateFormat(Const.Other.API_DATE_RESPONSE, Locale.ENGLISH)
    val newDate: Date = sdf.parse(date)
    val calendar = Calendar.getInstance()
    calendar.time = newDate
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH] + 1
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
    return Period.between(LocalDate.of(year, month, dayOfMonth), LocalDate.now()).years
}

fun getDays(date: String): String {
    val sdf = SimpleDateFormat(Const.Other.API_RESPONSE_FORMAT, Locale.ENGLISH)
    val formattedDate1 = sdf.parse(date)
    val date2 = Calendar.getInstance().time
    val millionSeconds = formattedDate1!!.time - date2.time
    return (TimeUnit.MILLISECONDS.toDays(millionSeconds) + 1).toString()
}*/

package com.abhinavdev.animeapp.util

import android.content.Context
import com.abhinavdev.animeapp.ApplicationClass
import com.google.gson.Gson

object PrefUtils {
    //preference file
    private const val DEFAULT_PREFS = "default_shared_prefs"

    //any numeric getter method will return -1 as default value
    private const val DEFAULT_NUMERIC_VALUE = -1

    //any string getter method will return empty string as default value
    private const val DEFAULT_STRING_VALUE = ""

    fun setString(key: String?, value: String?) {
        val prefs =
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String?, default: String = DEFAULT_STRING_VALUE): String {
        val prefs =
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        return prefs.getString(key, default) ?: default
    }

    fun setStringWithContext(context: Context, key: String?, value: String?) {
        val prefs = context.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringWithContext(
        context: Context, key: String?, default: String = DEFAULT_STRING_VALUE
    ): String {
        val prefs = context.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        return prefs.getString(key, default) ?: default
    }

    fun setBoolean(key: String?, value: Boolean) {
        val prefs =
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String?, default: Boolean = false): Boolean {
        val prefs =
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        return prefs.getBoolean(key, default)
    }

    fun setLong(key: String?, value: Long) {
        val prefs =
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getLong(key: String?, default: Int = DEFAULT_NUMERIC_VALUE): Long {
        val prefs =
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        return prefs.getLong(key, default.toLong())
    }

    fun setInteger(key: String?, value: Int) {
        val prefs =
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInteger(key: String?, default: Int = DEFAULT_NUMERIC_VALUE): Int {
        val prefs =
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        return prefs.getInt(key, default)
    }

    fun setFloat(key: String?, value: Float) {
        val prefs =
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun getFloat(key: String?, default: Int = DEFAULT_NUMERIC_VALUE): Float {
        val prefs =
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        return prefs.getFloat(key, default.toFloat())
    }

    /**
     * to set pojo object in preferences. will store json string of it.
     */
    fun setObject(key: String?, value: Any) {
        val prefs =
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(key, Gson().toJson(value))
        editor.apply()
    }

    /**
     * to get pojo object from json stored in preferences.
     * returns null if key doesn't exist in preferences
     */
    fun <T> getObject(key: String?, pojoClass: Class<T>?): T? {
        val prefs =
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        val jsonString = prefs.getString(key, null) ?: return null
        return Gson().fromJson(jsonString, pojoClass)
    }

    /**
     * fetches all key-value pairs from preferences in the form of map
     */
    val all: Map<String, *>
        get() {
            val prefs = ApplicationClass.getInstance().getSharedPreferences(
                DEFAULT_PREFS, Context.MODE_PRIVATE
            )
            return prefs.all
        }

    /**
     * removes particular key (and its associated value) from preferences
     */
    fun removeKey(key: String?) {
        val prefs =
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.remove(key)
        editor.apply()
    }

    /**
     * clears all key-value pairs in shared preferences
     */
    fun clearAll() {
        val prefs =
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    /**
     * check particular key is available or not before accessing
     */
    fun checkKeyAvailable(key: String?): Boolean {
        val sharedPrefs =
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        return sharedPrefs.contains(key)
    }
}
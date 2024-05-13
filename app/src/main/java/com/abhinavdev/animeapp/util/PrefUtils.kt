package com.abhinavdev.animeapp.util

import android.content.Context
import android.content.SharedPreferences
import com.abhinavdev.animeapp.ApplicationClass
import com.google.gson.Gson

object PrefUtils {
    //preference file
    private const val DEFAULT_PREFS = "com.abhinavdev.animeapp.SharedPreferences"

    //any numeric getter method will return -1 as default value
    private const val DEFAULT_NUMERIC_VALUE = -1

    //any string getter method will return empty string as default value
    private const val DEFAULT_STRING_VALUE = ""

    private var listener: SharedPreferences.OnSharedPreferenceChangeListener? = null

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

    fun setStringObserver(key: String, onChange: (String) -> Unit) {
        listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, changedKey ->
            if (changedKey == key) {
                onChange.invoke(prefs.getString(key, DEFAULT_STRING_VALUE) ?: DEFAULT_STRING_VALUE)
            }
        }
        ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
            .registerOnSharedPreferenceChangeListener(listener)
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

    fun setBooleanObserver(key: String, onChange: (Boolean) -> Unit) {
        listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, changedKey ->
            if (changedKey == key) {
                onChange.invoke(prefs.getBoolean(key, false))
            }
        }
        ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
            .registerOnSharedPreferenceChangeListener(listener)
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

    fun setLongObserver(key: String, onChange: (Long) -> Unit) {
        listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, changedKey ->
            if (changedKey == key) {
                onChange.invoke(prefs.getLong(key, DEFAULT_NUMERIC_VALUE.toLong()))
            }
        }
        ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
            .registerOnSharedPreferenceChangeListener(listener)
    }

    fun setInt(key: String?, value: Int) {
        val prefs =
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String?, default: Int = DEFAULT_NUMERIC_VALUE): Int {
        val prefs =
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
        return prefs.getInt(key, default)
    }

    fun setIntObserver(key: String, onChange: (Int) -> Unit) {
        listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, changedKey ->
            if (changedKey == key) {
                onChange.invoke(prefs.getInt(key, DEFAULT_NUMERIC_VALUE))
            }
        }
        ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
            .registerOnSharedPreferenceChangeListener(listener)
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

    fun setFloatObserver(key: String, onChange: (Float) -> Unit) {
        listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, changedKey ->
            if (changedKey == key) {
                onChange.invoke(prefs.getFloat(key, DEFAULT_NUMERIC_VALUE.toFloat()))
            }
        }
        ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
            .registerOnSharedPreferenceChangeListener(listener)
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
        return try {
            Gson().fromJson(jsonString, pojoClass)
        } catch (e: Exception) {
            null
        }
    }

    fun <T> setObjectObserver(key: String, pojoClass: Class<T>?, onChange: (T?) -> Unit) {
        listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, changedKey ->
            if (changedKey == key) {
                onChange.invoke(
                    try {
                        Gson().fromJson(prefs.getString(key, null), pojoClass)
                    } catch (e: Exception) {
                        null
                    }
                )
            }
        }
        ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
            .registerOnSharedPreferenceChangeListener(listener)
    }

    fun removeObserver() {
        if (listener != null) {
            ApplicationClass.getInstance().getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
                .unregisterOnSharedPreferenceChangeListener(listener)
        }
        listener = null
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
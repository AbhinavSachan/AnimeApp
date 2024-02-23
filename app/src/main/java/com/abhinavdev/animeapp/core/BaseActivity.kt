package com.abhinavdev.animeapp.core

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.LocaleHelper
import com.abhinavdev.animeapp.util.PrefUtils


abstract class BaseActivity : AppCompatActivity() {

    internal var lang: String = ""

    override fun attachBaseContext(base: Context) {
        PrefUtils.getStringWithContext(base, Const.SharedPrefs.SELECTED_LANGUAGE_CODE)?.let {
            lang = it
        }
        if (lang.isEmpty()) lang = Const.Other.ENGLISH_LANG_CODE
        super.attachBaseContext(LocaleHelper.onAttach(base, lang))
    }

    /**
     * to add fragment in container
     * tag will be same as class name of fragment
     *
     * @param containerId    id of fragment container
     * @param addToBackStack should be added to back stack?
     */
    open fun addFragment(fragment: Fragment, containerId: Int, addToBackStack: Boolean) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(containerId, fragment)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        }
        fragmentTransaction.commit()
    }

    /**
     * to replace fragment in container
     * tag will be same as class name of fragment
     *
     * @param containerId        id of fragment container
     * @param isAddedToBackStack should be added to back stack?
     */
    open fun replaceFragment(fragment: Fragment, containerId: Int, isAddedToBackStack: Boolean) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(containerId, fragment, fragment.javaClass.name)
        if (isAddedToBackStack) {
            fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        }
        fragmentTransaction.commit()
    }

}

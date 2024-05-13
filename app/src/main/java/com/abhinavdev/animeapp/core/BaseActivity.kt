package com.abhinavdev.animeapp.core

import android.content.Context
import android.os.Build
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.abhinavdev.animeapp.util.appsettings.LocaleHelper
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper


abstract class BaseActivity : AppCompatActivity() {

    internal var lang: String = ""

    override fun attachBaseContext(base: Context) {
        SettingsHelper.getAppLanguage().search.let { lang = it }
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

    open fun setOnBackPressedListener(callback: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.onBackInvokedDispatcher.registerOnBackInvokedCallback(0) { callback.invoke() }
        } else {
            this.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    callback.invoke()
                }
            })
        }
    }
}

package com.abhinavdev.animeapp.core

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

abstract class BaseFragment : Fragment() {
    lateinit var baseContext: BaseActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.baseContext = (context as? BaseActivity?)!!
    }

    /**
     * Pops the last fragment transaction from the back stack
     * Returns `true` if the pop was successful, `false` otherwise.
     */
    open fun popFragmentBackStack(tag: String? = null): Boolean {
        val fragmentManager = activity?.supportFragmentManager
        return if (fragmentManager != null && fragmentManager.backStackEntryCount > 0) {
            if (tag != null) {
                fragmentManager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            } else {
                fragmentManager.popBackStackImmediate()
            }
            true
        } else {
            false
        }
    }

    /**
     * this method calls [BaseActivity.addFragment].
     * So, it will add fragment in Activity's container
     */
    open fun addFragment(fragment: Fragment?, containerId: Int, addToBackStack: Boolean) {
        if (fragment != null) {
            (activity as BaseActivity).addFragment(fragment, containerId, addToBackStack)
        }
    }

    /**
     * this method calls [BaseActivity.replaceFragment].
     * So, it will replace fragment in Activity's container
     */
    open fun replaceFragment(fragment: Fragment?, containerId: Int, addToBackStack: Boolean) {
        if (fragment != null) {
            (activity as BaseActivity).replaceFragment(fragment, containerId, addToBackStack)
        }
    }

    /**
     * this method uses [.getChildFragmentManager] and adds nested fragment inside Fragment's container.
     * using it with activity's container will throw [IllegalStateException] or may cause other errors.
     */
    protected open fun addChildFragment(
        fragment: Fragment, containerId: Int, addToBackStack: Boolean
    ) {
        val fragmentManager = this.childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(containerId, fragment)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        }
        fragmentTransaction.commit()
    }

    /**
     * this method uses [.getChildFragmentManager] and replaces nested fragment inside Fragment's container
     * using it with activity's container will throw [IllegalStateException] or may cause other errors.
     */
    protected open fun replaceChildFragment(
        fragment: Fragment, containerId: Int, addToBackStack: Boolean
    ) {
        val fragmentManager = this.childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(containerId, fragment)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        }
        fragmentTransaction.commit()
    }
}
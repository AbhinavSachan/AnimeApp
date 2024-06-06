package com.abhinavdev.animeapp.util.extension

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.lang.ref.WeakReference

fun Fragment.snackbar(message: String, actionString: String? = null,bottomMargin:Int = -1, action: (() -> Unit)? = null) {
    val act = WeakReference(activity).get() ?: return
    act.runOnUiThread {
        val rootView = act.window.decorView.findViewById<View>(android.R.id.content)
        val snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)

        ViewUtil.useLayoutParams(snackbar.view,
            object : ViewUtil.UseLayoutParamsCallback<FrameLayout.LayoutParams> {
                override fun onUse(params: FrameLayout.LayoutParams) {
                    params.gravity = Gravity.BOTTOM
                    params.width = MATCH_PARENT
                    if (bottomMargin != -1) ViewUtil.setBottomMargin(snackbar.view,bottomMargin)
                }
            })
        snackbar.setAction(actionString) {
            action?.invoke()
        }
        snackbar.show()
    }
}

fun Activity.snackbar(input: String, actionString: String? = null, action: (() -> Unit)? = null) {
    runOnUiThread {
        val rootView = window.decorView.findViewById<View>(android.R.id.content)
        val snackbar = Snackbar.make(rootView, input, Snackbar.LENGTH_LONG)

        ViewUtil.useLayoutParams(snackbar.view,
            object : ViewUtil.UseLayoutParamsCallback<FrameLayout.LayoutParams> {
                override fun onUse(params: FrameLayout.LayoutParams) {
                    params.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
                    params.width = WRAP_CONTENT
                }
            })
        snackbar.setAction(actionString) {
            action?.invoke()
        }
        snackbar.show()
    }
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
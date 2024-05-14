package com.abhinavdev.animeapp.util

import android.content.Context
import android.view.LayoutInflater
import com.abhinavdev.animeapp.BuildConfig
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.DialogLoginBinding
import com.abhinavdev.animeapp.util.extension.openCustomTab
import com.abhinavdev.animeapp.util.extension.openLink
import com.abhinavdev.animeapp.util.extension.toast
import com.google.android.material.bottomsheet.BottomSheetDialog

object LoginUtil {

    private fun getLoginUrl(): String {
        val baseUrl = Const.BaseUrls.O_AUTH
        val clientId = BuildConfig.CLIENT_ID
        val codeChallenge = getCodeChallenge()
        val state = Const.Mal.STATE
        return "${baseUrl}authorize?response_type=code&client_id=${clientId}&code_challenge=${codeChallenge}&state=${state}"
    }

    fun Context.showLoginDialog(onCancel: (() -> Unit)? = null): BottomSheetDialog {
        val dialog = BottomSheetDialog(this, R.style.NoBackGroundBottomSheetDialog)
        val view = DialogLoginBinding.inflate(LayoutInflater.from(this))
        dialog.setCancelable(false)

        view.btnNegative.setOnClickListener {
            dialog.cancel()
            onCancel?.invoke()
        }
        view.btnPositive.setOnClickListener {
            dialog.cancel()
            val loginUrl = getLoginUrl()
            val useExternalBrowser = view.checkbox.isChecked
            if (useExternalBrowser) {
                try {
                    openLink(loginUrl)
                } catch (e: Exception) {
                    toast("Failed to open external browser")
                }
            } else {
                openCustomTab(loginUrl)
            }
        }
        dialog.setContentView(view.root)
        dialog.show()
        return dialog
    }

    private var verifier = ""

    fun getCodeVerifier(): String {
        return verifier
    }

    private fun getCodeChallenge(): String {
        verifier = generateVerifier()
        return verifier
    }

    private fun generateVerifier(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + '-' + '.' + '_' + '~'
        return (1..128).map { allowedChars.random() }.joinToString("")
    }

}
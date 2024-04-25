package com.abhinavdev.animeapp.util

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.abhinavdev.animeapp.BuildConfig
import com.abhinavdev.animeapp.databinding.DialogLoginBinding
import com.abhinavdev.animeapp.util.extension.openCustomTab
import com.abhinavdev.animeapp.util.extension.openLink
import com.abhinavdev.animeapp.util.extension.toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object LoginUtil {

    private var dialog: AlertDialog? = null

    private fun getLoginUrl(): String {
        val baseUrl = Const.BaseUrls.O_AUTH
        val clientId = BuildConfig.CLIENT_ID
        val codeChallenge = getCodeChallenge()
        val state = Const.Mal.STATE
        return "${baseUrl}authorize?response_type=code&client_id=${clientId}&code_challenge=${codeChallenge}&state=${state}"
    }

    fun Context.showLoginDialog(onCancel: (() -> Unit)? = null): AlertDialog {
        val builder = MaterialAlertDialogBuilder(this)
        val view = DialogLoginBinding.inflate(LayoutInflater.from(this))
        builder.setCancelable(false)
        builder.setView(view.root)

        view.btnNegative.setOnClickListener {
            dialog?.cancel()
            onCancel?.invoke()
        }
        view.btnPositive.setOnClickListener {
            dialog?.cancel()
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

        dialog = builder.create()
        dialog?.show()
        return dialog!!
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
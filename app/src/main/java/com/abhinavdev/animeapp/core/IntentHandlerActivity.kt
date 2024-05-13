package com.abhinavdev.animeapp.core

import android.os.Bundle

class IntentHandlerActivity:BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uri = intent.data

        if (uri == null) {
            finish()
            return
        }

    }
}
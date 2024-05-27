package com.abhinavdev.animeapp.ui.common.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.abhinavdev.animeapp.core.BaseActivity
import com.abhinavdev.animeapp.databinding.ActivityFullScreenImageBinding
import com.abhinavdev.animeapp.util.extension.ViewUtil
import com.abhinavdev.animeapp.util.extension.loadImage
import com.abhinavdev.animeapp.util.extension.show

class FullScreenImageActivity : BaseActivity() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityFullScreenImageBinding.inflate(layoutInflater)
    }
    private var imageUrl: String? = null

    companion object {
        private const val TRANSITION_NAME = "image_transition"
        private const val EXTRA_IMAGE_URL = "extra_image_url"

        /**
         * Don't have to do anything just call this fun it will add transitionName
         */
        @JvmStatic
        fun startNewActivity(
            activity: Activity?,
            imageUrl: String?,
            imageViewForTransition: AppCompatImageView? = null
        ) {
            val options = if (imageViewForTransition != null) {
                ViewCompat.setTransitionName(imageViewForTransition, TRANSITION_NAME).run {
                    activity?.let {
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            it, imageViewForTransition, TRANSITION_NAME
                        )
                    }
                }
            } else {
                null
            }
            val intent = Intent(activity, FullScreenImageActivity::class.java).apply {
                putExtra(EXTRA_IMAGE_URL, imageUrl)
            }
            activity?.startActivity(intent, options?.toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        init()
    }

    fun init() {
        binding.myZoomageView.transitionName = TRANSITION_NAME
        with(binding.toolbar) {
            ViewUtil.setOnApplyUiInsetsListener(root) {
                ViewUtil.setTopPadding(root, it.top)
            }
            ivBack.show()
            ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL)
        binding.myZoomageView.loadImage(imageUrl)

        setOnBackPressedListener { onBackPressedDispatcher.onBackPressed() }
    }
}
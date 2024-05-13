package com.abhinavdev.animeapp.util.adapter

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.viewpager2.widget.ViewPager2
import com.abhinavdev.animeapp.R

class FadeTransformation: ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        if (position != 0f) return
        ObjectAnimator.ofFloat(page, "alpha", 0f, 1f)
            .setDuration(150)
            .start()

        val anim = ScaleAnimation(
            1.15f, 1f, 1.15f, 1f,
            Animation.RELATIVE_TO_SELF,
            0.75f,
            Animation.RELATIVE_TO_SELF,
            0.75f
        )

        anim.duration = 200
        anim.setInterpolator(page.context, R.anim.over_shoot)
        page.startAnimation(anim)
    }
}
package com.abhinavdev.animeapp.ui.common.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.RowReviewsBinding
import com.abhinavdev.animeapp.remote.models.common.ReviewData
import com.abhinavdev.animeapp.ui.common.listeners.OnClickMultiTypeCallback
import com.abhinavdev.animeapp.util.extension.NumExtensions.toStringOrUnknown
import com.abhinavdev.animeapp.util.extension.getFormattedDateOrNull
import com.abhinavdev.animeapp.util.extension.loadImage
import com.abhinavdev.animeapp.util.extension.placeholder

class ReviewAdapter(val list: ArrayList<ReviewData>, val listener: OnClickMultiTypeCallback) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val score = "${item.score.toStringOrUnknown()}/10"
        val date = "${holder.binding.root.context.getString(R.string.msg_reviewed_on)} - ${getFormattedDateOrNull(item.date).placeholder()}"
        val image = item.user?.images?.jpg?.imageUrl
        val review = item.review
        val username = item.user?.username.placeholder()

        with(holder) {
            with(binding) {
                ivProfile.loadImage(image)
                tvReview.setInterpolator(OvershootInterpolator())
                tvReview.text = review
                tvReview.setOnClickListener {
                    tvReview.toggle()
                }
                tvUserName.text = username
                tvScore.text = score
                tvDate.text = date
            }
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: RowReviewsBinding) : RecyclerView.ViewHolder(binding.root)
}
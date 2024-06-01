package com.abhinavdev.animeapp.ui.anime.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinavdev.animeapp.databinding.RowGridListItemBinding
import com.abhinavdev.animeapp.remote.models.common.RecommendationsData
import com.abhinavdev.animeapp.ui.common.listeners.CustomClickListener
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.PrefUtils
import com.abhinavdev.animeapp.util.extension.getSizeOfView
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.isVisible
import com.abhinavdev.animeapp.util.extension.loadImage
import com.abhinavdev.animeapp.util.extension.showOrHide

class AnimeRecommendationAdapter(
    private val list: List<RecommendationsData>, private val listener: CustomClickListener
) : RecyclerView.Adapter<AnimeRecommendationAdapter.GridViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        context = parent.context
        val gridView = RowGridListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return GridViewHolder(gridView)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val item = list[position]
        with(holder) {
            with(binding) {
                ivPoster.loadImage(item.entry?.images?.jpg?.largeImageUrl)
                vtvAnimeName.text = item.entry?.title

                tvEpisodes.hide()
                tvRanking.hide()
                tvRating.hide()
                tvType.hide()

                //showing black background faded view accordingly which text is visible
                viewBottomLeftFade.showOrHide(tvRanking.isVisible())
                viewTopLeftFade.showOrHide(tvRating.isVisible())
                viewTopRightFade.showOrHide(tvType.isVisible())
                viewBottomRightFade.showOrHide(tvEpisodes.isVisible())
                root.setOnClickListener { listener.onItemClick(position) }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return list[position].entry?.malId?.toLong() ?: 0
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class GridViewHolder(val binding: RowGridListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            val savedItemHeight = PrefUtils.getInt(Const.PrefKeys.GRID_ITEM_HEIGHT_KEY)
            if (savedItemHeight <= 0) {
                binding.root.getSizeOfView {
                    val height = (it.width * 3) / 2
                    PrefUtils.setInt(Const.PrefKeys.GRID_ITEM_HEIGHT_KEY, height)
                    setItemHeight(height)
                }
            } else {
                setItemHeight(savedItemHeight)
            }
        }

        private fun setItemHeight(height: Int) {
            binding.root.layoutParams.height = height
        }
    }
}
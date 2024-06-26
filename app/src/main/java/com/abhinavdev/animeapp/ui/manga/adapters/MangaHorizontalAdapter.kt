package com.abhinavdev.animeapp.ui.manga.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.RowHorizontalListItemBinding
import com.abhinavdev.animeapp.remote.models.manga.MangaData
import com.abhinavdev.animeapp.ui.anime.misc.MultiContentAdapterType
import com.abhinavdev.animeapp.ui.common.listeners.OnClickMultiTypeCallback
import com.abhinavdev.animeapp.ui.manga.misc.PresentableMangaData
import com.abhinavdev.animeapp.util.extension.applyDrawable
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.isVisible
import com.abhinavdev.animeapp.util.extension.loadImage
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.showOrHide

class MangaHorizontalAdapter(
    private val list: List<MangaData>,
    private val type: MultiContentAdapterType,
    private val listener: OnClickMultiTypeCallback
) : RecyclerView.Adapter<MangaHorizontalAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            RowHorizontalListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = PresentableMangaData(position, list[position])
        val image = data.getImage()
        val chapters = data.getChapters()
        val volumes = data.getVolumes()
        val mangaName = data.getName()
        val mangaType = data.getType()
        val rating = data.getRating()

        with(holder) {
            with(binding) {
                ivPoster.loadImage(image)
                tvEpisodes.setCompoundDrawables(tvEpisodes.context.applyDrawable(R.drawable.ic_chapters),null,null,null)
                tvRating.text = rating
                tvType.text = mangaType
                vtvAnimeName.text = mangaName
                tvEpisodes.text = chapters
                tvVolumes.text = volumes

                tvEpisodes.showOrHide(!chapters.isNullOrBlank())
                tvVolumes.showOrHide(!volumes.isNullOrBlank())
                when (type) {
                    MultiContentAdapterType.TopAiring -> {}
                    MultiContentAdapterType.TopPopular -> {
                        tvRanking.hide()
                        tvRating.show()
                        tvType.show()
                    }

                    MultiContentAdapterType.TopFavourite -> {
                        tvRanking.hide()
                        tvRating.show()
                        tvType.show()
                    }

                    MultiContentAdapterType.TopUpcoming -> {
                        tvRanking.hide()
                        tvRating.hide()
                        tvType.show()
                    }

                    MultiContentAdapterType.TopRecommended -> {
                        tvRanking.hide()
                        tvRating.show()
                        tvType.show()
                    }

                    MultiContentAdapterType.TopRanked -> {
                        tvRanking.show()
                        tvRating.show()
                        tvType.show()
                    }

                    else -> {}
                }
                viewBottomLeftFade.showOrHide(tvRanking.isVisible())
                viewTopLeftFade.showOrHide(tvRating.isVisible())
                viewTopRightFade.showOrHide(tvType.isVisible())
                viewBottomRightFade.showOrHide(tvEpisodes.isVisible() || tvVolumes.isVisible())
                root.setOnClickListener { listener.onItemClick(position, type) }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return list[position].malId.toLong()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: RowHorizontalListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}
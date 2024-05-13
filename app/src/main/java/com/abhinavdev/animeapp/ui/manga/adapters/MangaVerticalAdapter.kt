package com.abhinavdev.animeapp.ui.manga.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.RowGridListItemBinding
import com.abhinavdev.animeapp.databinding.RowVerticalListItemBinding
import com.abhinavdev.animeapp.remote.models.manga.MangaData
import com.abhinavdev.animeapp.ui.anime.misc.AdapterType
import com.abhinavdev.animeapp.ui.common.listeners.CustomClickListener
import com.abhinavdev.animeapp.ui.manga.misc.PresentableMangaData
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.PrefUtils
import com.abhinavdev.animeapp.util.extension.applyDrawable
import com.abhinavdev.animeapp.util.extension.getSizeOfView
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.isVisible
import com.abhinavdev.animeapp.util.extension.loadImage
import com.abhinavdev.animeapp.util.extension.placeholder
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.showOrHide

class MangaVerticalAdapter(
    private val list: List<MangaData>,
    private val listener: CustomClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var adapterType: AdapterType = AdapterType.GRID
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val gridView =
            RowGridListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        val verticalView =
            RowVerticalListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return when (adapterType) {
            AdapterType.GRID -> GridViewHolder(gridView)
            AdapterType.LIST -> ListViewHolder(verticalView)
        }
    }

    fun setAdapterType(type: AdapterType) {
        adapterType = type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = PresentableMangaData(position,list[position])
        val image = data.getImage()
        val chapters = data.getChapters()
        val volumes = data.getVolumes()
        val mangaName = data.getName().placeholder()
        val mangaType = data.getType()
        val rating = data.getRating()
        val status = data.getStatus()
        val airedOn = data.getDate()
        val typeWithChapters = data.getTypeWithChapter(context)

        when (holder) {
            is GridViewHolder -> {
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
                        tvRanking.hide()
                        tvRating.showOrHide(!rating.isNullOrBlank())
                        tvType.show()
                        //showing black background faded view accordingly which text is visible
                        viewBottomLeftFade.showOrHide(tvRanking.isVisible())
                        viewTopLeftFade.showOrHide(tvRating.isVisible())
                        viewTopRightFade.showOrHide(tvType.isVisible())
                        viewBottomRightFade.showOrHide(tvEpisodes.isVisible() || tvVolumes.isVisible())
                        root.setOnClickListener { listener.onItemClick(position) }
                    }
                }
            }

            is ListViewHolder -> {
                with(holder) {
                    with(binding) {
                        ivPoster.loadImage(image)
                        tvRating.text = rating
                        tvType.text = typeWithChapters
                        tvName.text = mangaName
                        tvStatus.text = status.placeholder()
                        tvDate.text = airedOn

                        groupSeason.hide()
                        tvRanking.hide()
                        tvType.show()
                        //showing black background faded view accordingly which text is visible
                        viewTopLeftFade.showOrHide(tvRanking.isVisible())
                        root.setOnClickListener { listener.onItemClick(position) }
                    }
                }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return list[position].malId.toLong()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class GridViewHolder(val binding: RowGridListItemBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            val savedItemHeight = PrefUtils.getInt(Const.PrefKeys.GRID_ITEM_HEIGHT_KEY)
            if (savedItemHeight <= 0){
                binding.root.getSizeOfView {
                    val height = (it.width * 3)/2
                    PrefUtils.setInt(Const.PrefKeys.GRID_ITEM_HEIGHT_KEY,height)
                    setItemHeight(height)
                }
            }else{
                setItemHeight(savedItemHeight)
            }
        }
        private fun setItemHeight(height: Int){
            binding.root.layoutParams.height = height
        }
    }

    inner class ListViewHolder(val binding: RowVerticalListItemBinding) : RecyclerView.ViewHolder(binding.root)

}
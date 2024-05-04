package com.abhinavdev.animeapp.ui.anime.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.RowGridListItemBinding
import com.abhinavdev.animeapp.databinding.RowVerticalListItemBinding
import com.abhinavdev.animeapp.remote.models.malmodels.MalAnimeData
import com.abhinavdev.animeapp.ui.anime.misc.AdapterType
import com.abhinavdev.animeapp.ui.anime.misc.PresentableMalAnimeData
import com.abhinavdev.animeapp.ui.common.listeners.CustomClickListener
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.PrefUtils
import com.abhinavdev.animeapp.util.extension.getSizeOfView
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.isHidden
import com.abhinavdev.animeapp.util.extension.loadImageWithAnime
import com.abhinavdev.animeapp.util.extension.placeholder
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.showOrHide

class MalAnimeVerticalAdapter(
    private val list: List<MalAnimeData>,
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
        val data = PresentableMalAnimeData(holder.adapterPosition,list[position])
        val image = data.getImage()
        val animeName = data.getName()
        val animeType = data.getType()
        val rating = data.getRating()
        val rank = data.getRank()
        val status = data.getStatus()
        val airedOn = data.getDate()
        val season = data.getSeasonWithYear()
        val typeWithEpisodes = data.getTypeWithEpisode(context)

        when (holder) {
            is GridViewHolder -> {
                with(holder) {
                    with(binding) {
                        ivPoster.loadImageWithAnime(image, R.color.bgLightGrey, true)
                        tvRating.text = rating
                        tvRanking.text = rank
                        tvType.text = animeType
                        vtvAnimeName.text = animeName

                        tvRanking.hide()
                        tvRating.showOrHide(!rating.isNullOrBlank())
                        tvType.show()
                        //showing black background faded view accordingly which text is visible
                        viewBottomLeftFade.showOrHide(!tvRanking.isHidden())
                        viewTopLeftFade.showOrHide(!tvRating.isHidden())
                        viewTopRightFade.showOrHide(!tvType.isHidden())
                        root.setOnClickListener { listener.onItemClick(position) }
                    }
                }
            }

            is ListViewHolder -> {
                with(holder) {
                    with(binding) {
                        ivPoster.loadImageWithAnime(image, R.color.bgLightGrey, true)
                        tvRating.text = rating.placeholder()
                        tvRanking.text = rank
                        tvType.text = typeWithEpisodes
                        tvName.text = animeName
                        tvStatus.text = status.placeholder()
                        tvSeason.text = season
                        tvDate.text = airedOn

                        tvRanking.hide()
                        tvType.show()
                        //showing black background faded view accordingly which text is visible
                        viewTopLeftFade.showOrHide(!tvRating.isHidden())
                        root.setOnClickListener { listener.onItemClick(position) }
                    }
                }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return list[position].node?.id?.toLong() ?: 0
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
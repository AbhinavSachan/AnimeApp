package com.abhinavdev.animeapp.ui.manga.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.RowGridListItemBinding
import com.abhinavdev.animeapp.remote.models.manga.MangaData
import com.abhinavdev.animeapp.ui.anime.misc.AdapterType
import com.abhinavdev.animeapp.ui.common.listeners.CustomClickListener
import com.abhinavdev.animeapp.ui.manga.misc.PresentableMangaData
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.PrefUtils
import com.abhinavdev.animeapp.util.extension.getSizeOfView
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.isHidden
import com.abhinavdev.animeapp.util.extension.loadImageWithAnime
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.showOrHide

class MangaVerticalAdapter(
    private val list: List<MangaData>,
    private val listener: CustomClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var adapterType: AdapterType = AdapterType.GRID

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            RowGridListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return when (adapterType) {
            AdapterType.GRID -> GridViewHolder(view)
            AdapterType.LIST -> ListViewHolder(view)
        }
    }

    fun setAdapterType(type: AdapterType) {
        adapterType = type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = PresentableMangaData(holder.adapterPosition,list[position])
        val image = data.getImage()
        val mangaName = data.getName()
        val mangaType = data.getType()
        val rating = data.getRating()
        val rank = data.getRank()

        when (holder) {
            is GridViewHolder -> {
                with(holder) {
                    with(binding) {
                        ivPoster.loadImageWithAnime(image, R.color.bgLightGrey, true)
                        tvRating.text = rating
                        tvRanking.text = rank
                        tvType.text = mangaType
                        vtvAnimeName.text = mangaName

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
                        tvRating.text = rating
                        tvRanking.text = rank
                        tvType.text = mangaType
                        vtvAnimeName.text = mangaName

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

    inner class ListViewHolder(val binding: RowGridListItemBinding) : RecyclerView.ViewHolder(binding.root)

}
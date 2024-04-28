package com.abhinavdev.animeapp.ui.anime.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.RowGridListItemBinding
import com.abhinavdev.animeapp.remote.models.anime.AnimeData
import com.abhinavdev.animeapp.ui.anime.misc.AdapterType
import com.abhinavdev.animeapp.ui.anime.misc.PresentableAnimeData
import com.abhinavdev.animeapp.ui.common.listeners.CustomClickListener
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.isHidden
import com.abhinavdev.animeapp.util.extension.loadImageWithAnime
import com.abhinavdev.animeapp.util.extension.show
import com.abhinavdev.animeapp.util.extension.showOrHide

class AnimeVerticalAdapter(
    private val list: List<AnimeData>,
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

    @SuppressLint("NotifyDataSetChanged")
    fun setAdapterType(type: AdapterType) {
        adapterType = type
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = PresentableAnimeData(holder.adapterPosition,list[position])
        val image = data.getImage()
        val rating = data.getRating()
        val rank = data.getRank()
        val animeType = data.getType()
        val animeName = data.getName()

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
                        tvRating.show()
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
                        tvType.text = animeType
                        vtvAnimeName.text = animeName

                        tvRanking.hide()
                        tvRating.show()
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

    }

    inner class ListViewHolder(val binding: RowGridListItemBinding) : RecyclerView.ViewHolder(binding.root)

}
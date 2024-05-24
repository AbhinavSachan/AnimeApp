package com.abhinavdev.animeapp.ui.common.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.RowGenreCategoryBinding
import com.abhinavdev.animeapp.remote.models.enums.Genre
import com.abhinavdev.animeapp.ui.common.listeners.CustomClickListener
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.PrefUtils
import com.abhinavdev.animeapp.util.extension.getSizeOfView

class GenreCategoryAdapter(val list: List<Genre>, val listener: CustomClickListener) :
    RecyclerView.Adapter<GenreCategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            RowGenreCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder) {
            with(binding) {
                val image = item.imageRes.takeIf { it != -1 } ?: R.drawable.bg_error_placeholder
                ivGenre.setImageResource(image)
                tvGenre.text = item.showName
                root.setOnClickListener { listener.onItemClick(adapterPosition) }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return list[position].animeId.toLong()
    }

    class ViewHolder(val binding: RowGenreCategoryBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            val savedItemHeight = PrefUtils.getInt(Const.PrefKeys.GENRE_ITEM_HEIGHT_KEY)
            if (savedItemHeight <= 0){
                binding.root.getSizeOfView {
                    val height = (it.width * 2)/3
                    PrefUtils.setInt(Const.PrefKeys.GENRE_ITEM_HEIGHT_KEY,height)
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
}
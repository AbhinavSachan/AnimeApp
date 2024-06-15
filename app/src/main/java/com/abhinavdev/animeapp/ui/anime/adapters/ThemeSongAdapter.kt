package com.abhinavdev.animeapp.ui.anime.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinavdev.animeapp.databinding.RowThemeSongsBinding
import com.abhinavdev.animeapp.remote.models.anime.AnimeData
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.extension.StringExtensions.buildQueryFromThemeText
import com.abhinavdev.animeapp.util.extension.openAction

class ThemeSongAdapter(private val list: ArrayList<String>) :
    RecyclerView.Adapter<ThemeSongAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowThemeSongsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder.binding.root) {
            text = AnimeData.processThemeData(item)
            setOnClickListener {
                context.openAction(
                    Const.Links.YOUTUBE_QUERY_LINK + item.buildQueryFromThemeText()
                )
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return list[position].hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: RowThemeSongsBinding) : RecyclerView.ViewHolder(binding.root)
}
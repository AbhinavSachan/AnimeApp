package com.abhinavdev.animeapp.ui.common.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinavdev.animeapp.databinding.RowGenreBinding
import com.abhinavdev.animeapp.ui.common.models.LocalGenreModel

class GenreAdapter(val list: ArrayList<LocalGenreModel>, val listener: Callback) :
    RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder) {
            with(binding) {
                tvGenre.text = item.name
                root.setOnClickListener { listener.onGenreClick(adapterPosition) }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return list[position].id.toLong()
    }

    class ViewHolder(val binding: RowGenreBinding) : RecyclerView.ViewHolder(binding.root)
    interface Callback {
        fun onGenreClick(position: Int)
    }
}
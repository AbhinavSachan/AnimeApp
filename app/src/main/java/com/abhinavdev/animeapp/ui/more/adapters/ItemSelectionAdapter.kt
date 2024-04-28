package com.abhinavdev.animeapp.ui.more.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.RowItemSelectionBinding
import com.abhinavdev.animeapp.ui.models.ItemSelectionModelBase
import com.abhinavdev.animeapp.ui.more.misc.ItemSelectionType
import com.abhinavdev.animeapp.util.extension.applyFont

class ItemSelectionAdapter(
    val list: ArrayList<ItemSelectionModelBase>, val listener: Callback, val type: ItemSelectionType
) : RecyclerView.Adapter<ItemSelectionAdapter.ViewHolder>() {

    private var regularTypeFace: Typeface? = null
    private var mediumTypeFace: Typeface? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        regularTypeFace = context.applyFont(R.font.english_regular)
        mediumTypeFace = context.applyFont(R.font.english_medium)

        val view =
            RowItemSelectionBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder) {
            with(binding) {
                tvItem.text = item.name
                if (item.isSelected) {

                } else {

                }
                cvMain.setOnClickListener { listener.onItemClick(adapterPosition, type) }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: RowItemSelectionBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface Callback {
        fun onItemClick(position: Int, type: ItemSelectionType)
    }
}
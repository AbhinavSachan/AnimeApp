package com.abhinavdev.animeapp.ui.more.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.RowItemSelectionBinding
import com.abhinavdev.animeapp.ui.common.listeners.OnClickMultiTypeCallback
import com.abhinavdev.animeapp.ui.models.ItemSelectionModelBase
import com.abhinavdev.animeapp.util.extension.applyFont

class ItemSelectionAdapter<T>(
    val list: List<ItemSelectionModelBase>, val listener: OnClickMultiTypeCallback, val type: T
) : RecyclerView.Adapter<ItemSelectionAdapter<T>.ViewHolder>() {

    private var regularTypeFace: Typeface? = null
    private var mediumTypeFace: Typeface? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        regularTypeFace = context.applyFont(R.font.custom_regular)
        mediumTypeFace = context.applyFont(R.font.custom_medium)

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
                    root.setBackgroundResource(R.drawable.bg_item_selected)
                    tvItem.typeface = mediumTypeFace
                } else {
                    root.setBackgroundResource(R.color.transparent)
                    tvItem.typeface = regularTypeFace
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
}
fun List<ItemSelectionModelBase>.setOptionSelected(
    currentPosition: Int,
    onOptionSelect: (ItemSelectionModelBase) -> Unit
) {
    val currentItem = get(currentPosition)

    // Check if the current item is already selected
    if (!currentItem.isSelected) {
        // Deselect any previously selected item
        find { it.isSelected }?.isSelected = false

        // Select the new item
        currentItem.isSelected = true

        // Run the callback with the newly selected item
        onOptionSelect(currentItem)
    }
}
package com.abhinavdev.animeapp.ui.common.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.RowSelectableItemBinding
import com.abhinavdev.animeapp.util.extension.applyColor

class SelectableItemAdapter(
    private val list: List<SelectableItemData>, val onClick: (SelectableItemData, Int) -> Unit
) : RecyclerView.Adapter<SelectableItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            RowSelectableItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        with(holder.binding) {
            val name = item.name
            tvItem.text = name
            val context = root.context

            if (item.isSelected) {
                tvItem.setTextColor(context.applyColor(R.color.onAccent))
                root.setBackgroundResource(R.drawable.bg_accent_button)
            } else {
                tvItem.setTextColor(context.applyColor(R.color.fontTextPrimary))
                root.setBackgroundResource(R.drawable.bg_primary_button_bor_accent)
            }

            fun onItemClick() {
                if (!item.isSelected) {
                    val index = list.indexOfFirst { it.isSelected }
                    if (index != -1) {
                        list[index].isSelected = false
                        notifyItemChanged(index)
                    }
                    item.isSelected = true
                    notifyItemChanged(position)
                }
                onClick(item, position)
            }
            root.setOnClickListener {
                onItemClick()
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: RowSelectableItemBinding) : RecyclerView.ViewHolder(binding.root)
}
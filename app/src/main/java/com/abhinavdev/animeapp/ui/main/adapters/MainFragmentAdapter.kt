package com.abhinavdev.animeapp.ui.main.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abhinavdev.animeapp.ui.anime.AnimeFragment
import com.abhinavdev.animeapp.ui.manga.MangaFragment
import com.abhinavdev.animeapp.ui.more.MoreFragment

class MainFragmentAdapter(
    activity: FragmentActivity, private val list: List<PageType>
) : FragmentStateAdapter(activity) {

    private val pageIds = list.map { it.hashCode().toLong() }

    sealed class PageType {
        data object ANIME : PageType()
        data object MANGA : PageType()
        data object MORE : PageType()
    }

    override fun getItemCount() = list.size

    override fun createFragment(position: Int): Fragment {
        val type = list[position]
        val fragment = when (type) {
            PageType.ANIME -> AnimeFragment.newInstance()
            PageType.MANGA -> MangaFragment.newInstance()
            PageType.MORE -> MoreFragment.newInstance()
        }
        return fragment
    }

    override fun getItemId(position: Int): Long {
        return list[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return pageIds.contains(itemId)
    }
}
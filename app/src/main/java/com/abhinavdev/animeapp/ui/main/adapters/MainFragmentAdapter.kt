package com.abhinavdev.animeapp.ui.main.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abhinavdev.animeapp.core.BaseFragment
import com.abhinavdev.animeapp.ui.anime.AnimeHomeFragment
import com.abhinavdev.animeapp.ui.manga.MangaHomeFragment
import com.abhinavdev.animeapp.ui.more.MoreFragment

class MainFragmentAdapter(
    activity: FragmentActivity, private val list: List<PageType>
) : FragmentStateAdapter(activity) {

    private val pageIds = list.map { it.hashCode().toLong() }

    enum class PageType(val position: Int) {
         ANIME(0),
         MANGA(1),
         MORE(2),
    }

    private val fragmentList:ArrayList<BaseFragment> = arrayListOf()

    fun getFragment(type:PageType):BaseFragment?{
        return fragmentList.getOrNull(type.position)
    }

    override fun getItemCount() = list.size

    override fun createFragment(position: Int): Fragment {
        val type = list[position]

        val fragment = when (type) {
            PageType.ANIME -> AnimeHomeFragment.newInstance()
            PageType.MANGA -> MangaHomeFragment.newInstance()
            PageType.MORE -> MoreFragment.newInstance()
        }

        if (fragmentList.contains(fragment)) {
            fragmentList[position] = fragment
        } else {
            fragmentList.add(fragment)
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
package com.abhinavdev.animeapp.ui.main.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abhinavdev.animeapp.ui.anime.AnimeHomeFragment
import com.abhinavdev.animeapp.ui.manga.MangaHomeFragment
import com.abhinavdev.animeapp.ui.more.MoreFragment

class MainFragmentAdapter(
    activity: FragmentActivity, private val list: List<PageType>
) : FragmentStateAdapter(activity) {

    enum class PageType(val position: Int) {
         ANIME(0),
         MANGA(1),
         MORE(2),
    }

    private val fragmentList:ArrayList<Fragment> = arrayListOf()

    fun getFragment(type:PageType):Fragment?{
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
}
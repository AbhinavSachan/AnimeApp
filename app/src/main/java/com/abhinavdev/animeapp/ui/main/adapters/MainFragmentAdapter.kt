package com.abhinavdev.animeapp.ui.main.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abhinavdev.animeapp.remote.models.enums.MediaType
import com.abhinavdev.animeapp.ui.anime.AnimeHomeFragment
import com.abhinavdev.animeapp.ui.common.ui.GenreFragment
import com.abhinavdev.animeapp.ui.manga.MangaHomeFragment
import com.abhinavdev.animeapp.ui.more.MoreFragment
import com.abhinavdev.animeapp.ui.more.MyAnimeListFragment
import com.abhinavdev.animeapp.ui.more.MyMangaListFragment
import com.abhinavdev.animeapp.ui.search.SearchAnimeFragment
import com.abhinavdev.animeapp.ui.search.SearchMangaFragment
import com.abhinavdev.animeapp.util.appsettings.AppMediaType

class MainFragmentAdapter(
    activity: FragmentActivity, private val list: List<PageType>, private val mediaType: AppMediaType
) : FragmentStateAdapter(activity) {

    enum class PageType(val position: Int) {
         HOME(0),
         GENRE(1),
         SEARCH(2),
         MY_LIST(3),
         MORE(4),
    }

    private val fragmentList:ArrayList<Fragment> = arrayListOf()

    fun getFragment(type:PageType):Fragment?{
        return fragmentList.getOrNull(type.position)
    }

    override fun getItemCount() = list.size

    override fun createFragment(position: Int): Fragment {
        val type = list[position]

        val fragment = when (type) {
            PageType.HOME -> {
                if (mediaType == AppMediaType.ANIME){
                    AnimeHomeFragment.newInstance()
                }else{
                    MangaHomeFragment.newInstance()
                }
            }
            PageType.GENRE -> GenreFragment.newInstance(MediaType.valueOfOrDefault(mediaType.search))
            PageType.SEARCH -> {
                if (mediaType == AppMediaType.ANIME){
                    SearchAnimeFragment.newInstance()
                }else{
                    SearchMangaFragment.newInstance()
                }
            }
            PageType.MY_LIST -> {
                if (mediaType == AppMediaType.ANIME){
                    MyAnimeListFragment.newInstance()
                }else{
                    MyMangaListFragment.newInstance()
                }
            }
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
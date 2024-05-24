package com.abhinavdev.animeapp.remote.models.enums

import com.abhinavdev.animeapp.R
import com.google.gson.annotations.SerializedName

/**
 * The anime genre.
 */
enum class Genre(
    /** The MAL anime ID.  */
    val animeId: Int,
    /** The MAL manga ID.  */
    val mangaId: Int,
    /** The genre name, as shown by MAL.  */
    val showName: String,
    val sfw: Boolean,
    val imageRes: Int,
) {
    @SerializedName("All")
    ALL(-1, -1, "All", true, -1),

    @SerializedName("Action")
    ACTION(1, 1, "Action", true, R.drawable.genre_action),

    @SerializedName("Adventure")
    ADVENTURE(2, 2, "Adventure", true, R.drawable.genre_adventure),

    @SerializedName("Racing")
    RACING(3, 3, "Racing", true, R.drawable.genre_racing),

    @SerializedName("Comedy")
    COMEDY(4, 4, "Comedy", true, R.drawable.genre_comedy),

    @SerializedName("Avant Garde")
    AVANT_GARDE(5, 5, "Avant Garde", true, R.drawable.genre_avant_garde),

    @SerializedName("Mythology")
    MYTHOLOGY(6, 6, "Mythology", true, R.drawable.genre_mythology),

    @SerializedName("Mystery")
    MYSTERY(7, 7, "Mystery", true, R.drawable.genre_mystery),

    @SerializedName("Drama")
    DRAMA(8, 8, "Drama", true, R.drawable.genre_drama),

    @SerializedName("Ecchi")
    ECCHI(9, 9, "Ecchi", false, R.drawable.genre_ecchi),

    @SerializedName("Fantasy")
    FANTASY(10, 10, "Fantasy", true, R.drawable.genre_fantasy),

    @SerializedName("Strategy Game")
    STRATEGY_GAME(11, 11, "Strategy Game", true, R.drawable.genre_strategy_game),

    @SerializedName("Hentai")
    HENTAI(12, 12, "Hentai", false, R.drawable.genre_hentai),

    @SerializedName("Historical")
    HISTORICAL(13, 13, "Historical", true, R.drawable.genre_historical),

    @SerializedName("Horror")
    HORROR(14, 14, "Horror", true, R.drawable.genre_horror),

    @SerializedName("Kids")
    KIDS(15, 15, "Kids", true, R.drawable.genre_kids),

    @SerializedName("Martial Arts")
    MARTIAL_ARTS(17, 17, "Martial Arts", true, R.drawable.genre_martial_arts),

    @SerializedName("Mecha")
    MECHA(18, 18, "Mecha", true, R.drawable.genre_mecha),

    @SerializedName("Music")
    MUSIC(19, 19, "Music", true, R.drawable.genre_music),

    @SerializedName("Parody")
    PARODY(20, 20, "Parody", true, R.drawable.genre_parody),

    @SerializedName("Samurai")
    SAMURAI(21, 21, "Samurai", true, R.drawable.genre_samurai),

    @SerializedName("Romance")
    ROMANCE(22, 22, "Romance", true, R.drawable.genre_romance),

    @SerializedName("School")
    SCHOOL(23, 23, "School", true, R.drawable.genre_school),

    @SerializedName("Sci-Fi")
    SCI_FI(24, 24, "Sci-Fi", true, R.drawable.genre_sci_fi),

    @SerializedName("Shoujo")
    SHOUJO(25, 25, "Shoujo", true, R.drawable.genre_shoujo),

    @SerializedName("Girls Love")
    GIRLS_LOVE(26, 26, "Girls Love", true, R.drawable.genre_girls_love),

    @SerializedName("Shounen")
    SHOUNEN(27, 27, "Shounen", true, R.drawable.genre_shounen),

    @SerializedName("Boys Love")
    BOYS_LOVE(28, 28, "Boys Love", true, R.drawable.genre_boys_love),

    @SerializedName("Space")
    SPACE(29, 29, "Space", true, R.drawable.genre_space),

    @SerializedName("Sports")
    SPORTS(30, 30, "Sports", true, R.drawable.genre_sports),

    @SerializedName("Super Power")
    SUPERPOWER(31, 31, "Super Power", true, R.drawable.genre_super_power),

    @SerializedName("Vampire")
    VAMPIRE(32, 32, "Vampire", true, R.drawable.genre_vampire),

    @SerializedName("Harem")
    HAREM(35, 35, "Harem", true, R.drawable.genre_harem),

    @SerializedName("Slice of Life")
    SLICE_OF_LIFE(36, 36, "Slice of Life", true, R.drawable.genre_slice_of_life),

    @SerializedName("Supernatural")
    SUPERNATURAL(37, 37, "Supernatural", true, R.drawable.genre_super_natural),

    @SerializedName("Military")
    MILITARY(38, 38, "Military", true, R.drawable.genre_military),

    @SerializedName("Detective")
    DETECTIVE(39, 39, "Detective", true, R.drawable.genre_detective),

    @SerializedName("Psychological")
    PSYCHOLOGICAL(40, 40, "Psychological", true, R.drawable.genre_psychological),

    @SerializedName("Suspense")
    SUSPENSE(41, 45, "Suspense", true, R.drawable.genre_suspense),

    @SerializedName("Seinen")
    SEINEN(42, 41, "Seinen", true, R.drawable.genre_seinen),

    @SerializedName("Josei")
    JOSEI(43, 42, "Josei", true, R.drawable.genre_josei),

    @SerializedName("Award Winning")
    AWARD_WINNING(46, 46, "Award Winning", true, R.drawable.genre_award_winning),

    @SerializedName("Gourmet")
    GOURMET(47, 47, "Gourmet", true, R.drawable.genre_gourmet),

    @SerializedName("Workplace")
    WORKPLACE(48, 48, "Workplace", true, R.drawable.genre_workplace),

    @SerializedName("Erotica")
    EROTICA(49, 49, "Erotica", false, R.drawable.genre_erotica),

    @SerializedName("Adult Cast")
    ADULT_CAST(50, 50, "Adult Cast", true, R.drawable.genre_adult_cast),

    @SerializedName("Anthropomorphic")
    ANTHROPOMORPHIC(51, 51, "Anthropomorphic", true, R.drawable.genre_anthropomorphic),

    @SerializedName("CGDCT")
    CGDCT(52, 52, "CGDCT", true, R.drawable.genre_cgdct),

    @SerializedName("Childcare")
    CHILDCARE(53, 53, "Childcare", true, R.drawable.genre_childcare),

    @SerializedName("Combat Sports")
    COMBAT_SPORTS(54, 54, "Combat Sports", true, R.drawable.genre_combat_sports),

    @SerializedName("Delinquents")
    DELINQUENTS(55, 55, "Delinquents", true, R.drawable.genre_delinquents),

    @SerializedName("Educational")
    EDUCATIONAL(56, 56, "Educational", true, R.drawable.genre_educational),

    @SerializedName("Gag Humor")
    GAG_HUMOR(57, 57, "Gag Humor", true, R.drawable.genre_gag_humor),

    @SerializedName("Gore")
    GORE(58, 58, "Gore", true, R.drawable.genre_gore),

    @SerializedName("High Stakes Game")
    HIGH_STAKES_GAME(59, 59, "High Stakes Game", true, R.drawable.genre_high_stakes_game),

    @SerializedName("Idols (Female)")
    IDOLS_FEMALE(60, 60, "Idols (Female)", true, R.drawable.genre_idol_female),

    @SerializedName("Idols (Male)")
    IDOLS_MALE(61, 61, "Idols (Male)", true, R.drawable.genre_idol_male),

    @SerializedName("Isekai")
    ISEKAI(62, 62, "Isekai", true, R.drawable.genre_isekai),

    @SerializedName("Iyashikei")
    IYASHIKEI(63, 63, "Iyashikei", true, R.drawable.genre_iyashikei),

    @SerializedName("Love Polygon")
    LOVE_POLYGON(64, 64, "Love Polygon", true, R.drawable.genre_love_polygon),

    @SerializedName("Magical Sex Shift")
    MAGICAL_SEX_SHIFT(65, 65, "Magical Sex Shift", true, R.drawable.genre_magical_sex_shift),

    @SerializedName("Mahou Shoujo")
    MAHOU_SHOUJO(66, 66, "Mahou Shoujo", true, R.drawable.genre_mahou_shoujo),

    @SerializedName("Medical")
    MEDICAL(67, 67, "Medical", true, R.drawable.genre_medical),

    @SerializedName("Memoir")
    MEMOIR(-1, 68, "Memoir", true, R.drawable.genre_memoir),

    @SerializedName("Organized Crime")
    ORGANIZED_CRIME(68, 69, "Organized Crime", true, R.drawable.genre_organized_crime),

    @SerializedName("Otaku Culture")
    OTAKU_CULTURE(69, 70, "Otaku Culture", true, R.drawable.genre_otaku_culture),

    @SerializedName("Performing Arts")
    PERFORMING_ARTS(70, 71, "Performing Arts", true, R.drawable.genre_performing_arts),

    @SerializedName("Pets")
    PETS(71, 72, "Pets", true, R.drawable.genre_pets),

    @SerializedName("Reincarnation")
    REINCARNATION(72, 73, "Reincarnation", true, R.drawable.genre_reincarnation),

    @SerializedName("Reverse Harem")
    REVERSE_HAREM(73, 74, "Reverse Harem", true, R.drawable.genre_reverse_harem),

    @SerializedName("Romantic Subtext")
    ROMANTIC_SUBTEXT(74, 75, "Romantic Subtext", true, R.drawable.genre_romantic_subtext),

    @SerializedName("Showbiz")
    SHOWBIZ(75, 76, "Showbiz", true, R.drawable.genre_showbiz),

    @SerializedName("Survival")
    SURVIVAL(76, 77, "Survival", true, R.drawable.genre_survival),

    @SerializedName("Team Sports")
    TEAM_SPORTS(77, 78, "Team Sports", true, R.drawable.genre_team_sports),

    @SerializedName("Time Travel")
    TIME_TRAVEL(78, 79, "Time Travel", true, R.drawable.genre_time_travel),

    @SerializedName("Video Game")
    VIDEO_GAME(79, 80, "Video Game", true, R.drawable.genre_video_game),

    @SerializedName("Visual Arts")
    VISUAL_ARTS(80, 82, "Visual Arts", true, R.drawable.genre_visual_arts),

    @SerializedName("Crossdressing")
    CROSS_DRESSING(81, 44, "Cross Dressing", true, R.drawable.genre_crossdressing),

    @SerializedName("Villainess")
    VILLAINESS(-1, 81, "Villainess", true, R.drawable.genre_villianess);

    companion object {
        fun valueOfOrDefaultAnime(value: Int?) = entries.find { it.animeId == value }
        fun valueOfOrDefaultManga(value: Int?) = entries.find { it.mangaId == value }

        fun listAnime(sfw: Boolean) =
            entries.filter { it.animeId != -1 && (!sfw || it.sfw) }.sortedBy { it.showName }


        fun listManga(sfw: Boolean) =
            entries.filter { it.mangaId != -1 && (!sfw || it.sfw) }.sortedBy { it.showName }

    }
}

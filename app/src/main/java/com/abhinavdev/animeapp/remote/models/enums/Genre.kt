package com.abhinavdev.animeapp.remote.models.enums

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
    ACTION(1, 1, "Action", true, -1),

    @SerializedName("Adventure")
    ADVENTURE(2, 2, "Adventure", true, -1),

    @SerializedName("Racing")
    RACING(3, 3, "Racing", true, -1),

    @SerializedName("Comedy")
    COMEDY(4, 4, "Comedy", true, -1),

    @SerializedName("Avant Garde")
    AVANT_GARDE(5, 5, "Avant Garde", true, -1),

    @SerializedName("Mythology")
    MYTHOLOGY(6, 6, "Mythology", true, -1),

    @SerializedName("Mystery")
    MYSTERY(7, 7, "Mystery", true, -1),

    @SerializedName("Drama")
    DRAMA(8, 8, "Drama", true, -1),

    @SerializedName("Ecchi")
    ECCHI(9, 9, "Ecchi", false, -1),

    @SerializedName("Fantasy")
    FANTASY(10, 10, "Fantasy", true, -1),

    @SerializedName("Strategy Game")
    STRATEGY_GAME(11, 11, "Strategy Game", true, -1),

    @SerializedName("Hentai")
    HENTAI(12, 12, "Hentai", false, -1),

    @SerializedName("Historical")
    HISTORICAL(13, 13, "Historical", true, -1),

    @SerializedName("Horror")
    HORROR(14, 14, "Horror", true, -1),

    @SerializedName("Kids")
    KIDS(15, 15, "Kids", true, -1),

    @SerializedName("Martial Arts")
    MARTIAL_ARTS(17, 17, "Martial Arts", true, -1),

    @SerializedName("Mecha")
    MECHA(18, 18, "Mecha", true, -1),

    @SerializedName("Music")
    MUSIC(19, 19, "Music", true, -1),

    @SerializedName("Parody")
    PARODY(20, 20, "Parody", true, -1),

    @SerializedName("Samurai")
    SAMURAI(21, 21, "Samurai", true, -1),

    @SerializedName("Romance")
    ROMANCE(22, 22, "Romance", true, -1),

    @SerializedName("School")
    SCHOOL(23, 23, "School", true, -1),

    @SerializedName("Sci-Fi")
    SCI_FI(24, 24, "Sci-Fi", true, -1),

    @SerializedName("Shoujo")
    SHOUJO(25, 25, "Shoujo", true, -1),

    @SerializedName("Girls Love")
    GIRLS_LOVE(26, 26, "Girls Love", true, -1),

    @SerializedName("Shounen")
    SHOUNEN(27, 27, "Shounen", true, -1),

    @SerializedName("Boys Love")
    BOYS_LOVE(28, 28, "Boys Love", true, -1),

    @SerializedName("Space")
    SPACE(29, 29, "Space", true, -1),

    @SerializedName("Sports")
    SPORTS(30, 30, "Sports", true, -1),

    @SerializedName("Super Power")
    SUPERPOWER(31, 31, "Super Power", true, -1),

    @SerializedName("Vampire")
    VAMPIRE(32, 32, "Vampire", true, -1),

    @SerializedName("Harem")
    HAREM(35, 35, "Harem", true, -1),

    @SerializedName("Slice of Life")
    SLICE_OF_LIFE(36, 36, "Slice of Life", true, -1),

    @SerializedName("Supernatural")
    SUPERNATURAL(37, 37, "Supernatural", true, -1),

    @SerializedName("Military")
    MILITARY(38, 38, "Military", true, -1),

    @SerializedName("Detective")
    DETECTIVE(39, 39, "Detective", true, -1),

    @SerializedName("Psychological")
    PSYCHOLOGICAL(40, 40, "Psychological", true, -1),

    @SerializedName("Suspense")
    SUSPENSE(41, 45, "Suspense", true, -1),

    @SerializedName("Seinen")
    SEINEN(42, 41, "Seinen", true, -1),

    @SerializedName("Josei")
    JOSEI(43, 42, "Josei", true, -1),

    @SerializedName("Award Winning")
    AWARD_WINNING(46, 46, "Award Winning", true, -1),

    @SerializedName("Gourmet")
    GOURMET(47, 47, "Gourmet", true, -1),

    @SerializedName("Workplace")
    WORKPLACE(48, 48, "Workplace", true, -1),

    @SerializedName("Erotica")
    EROTICA(49, 49, "Erotica", false, -1),

    @SerializedName("Adult Cast")
    ADULT_CAST(50, 50, "Adult Cast", true, -1),

    @SerializedName("Anthropomorphic")
    ANTHROPOMORPHIC(51, 51, "Anthropomorphic", true, -1),

    @SerializedName("CGDCT")
    CGDCT(52, 52, "CGDCT", true, -1),

    @SerializedName("Childcare")
    CHILDCARE(53, 53, "Childcare", true, -1),

    @SerializedName("Combat Sports")
    COMBAT_SPORTS(54, 54, "Combat Sports", true, -1),

    @SerializedName("Delinquents")
    DELINQUENTS(55, 55, "Delinquents", true, -1),

    @SerializedName("Educational")
    EDUCATIONAL(56, 56, "Educational", true, -1),

    @SerializedName("Gag Humor")
    GAG_HUMOR(57, 57, "Gag Humor", true, -1),

    @SerializedName("Gore")
    GORE(58, 58, "Gore", true, -1),

    @SerializedName("High Stakes Game")
    HIGH_STAKES_GAME(59, 59, "High Stakes Game", true, -1),

    @SerializedName("Idols (Female)")
    IDOLS_FEMALE(60, 60, "Idols (Female)", true, -1),

    @SerializedName("Idols (Male)")
    IDOLS_MALE(61, 61, "Idols (Male)", true, -1),

    @SerializedName("Isekai")
    ISEKAI(62, 62, "Isekai", true, -1),

    @SerializedName("Iyashikei")
    IYASHIKEI(63, 63, "Iyashikei", true, -1),

    @SerializedName("Love Polygon")
    LOVE_POLYGON(64, 64, "Love Polygon", true, -1),

    @SerializedName("Magical Sex Shift")
    MAGICAL_SEX_SHIFT(65, 65, "Magical Sex Shift", true, -1),

    @SerializedName("Mahou Shoujo")
    MAHOU_SHOUJO(66, 66, "Mahou Shoujo", true, -1),

    @SerializedName("Medical")
    MEDICAL(67, 67, "Medical", true, -1),

    @SerializedName("Memoir")
    MEMOIR(-1, 68, "Memoir", true, -1),

    @SerializedName("Organized Crime")
    ORGANIZED_CRIME(68, 69, "Organized Crime", true, -1),

    @SerializedName("Otaku Culture")
    OTAKU_CULTURE(69, 70, "Otaku Culture", true, -1),

    @SerializedName("Performing Arts")
    PERFORMING_ARTS(70, 71, "Performing Arts", true, -1),

    @SerializedName("Pets")
    PETS(71, 72, "Pets", true, -1),

    @SerializedName("Reincarnation")
    REINCARNATION(72, 73, "Reincarnation", true, -1),

    @SerializedName("Reverse Harem")
    REVERSE_HAREM(73, 74, "Reverse Harem", true, -1),

    @SerializedName("Romantic Subtext")
    ROMANTIC_SUBTEXT(74, 75, "Romantic Subtext", true, -1),

    @SerializedName("Showbiz")
    SHOWBIZ(75, 76, "Showbiz", true, -1),

    @SerializedName("Survival")
    SURVIVAL(76, 77, "Survival", true, -1),

    @SerializedName("Team Sports")
    TEAM_SPORTS(77, 78, "Team Sports", true, -1),

    @SerializedName("Time Travel")
    TIME_TRAVEL(78, 79, "Time Travel", true, -1),

    @SerializedName("Video Game")
    VIDEO_GAME(79, 80, "Video Game", true, -1),

    @SerializedName("Visual Arts")
    VISUAL_ARTS(80, 82, "Visual Arts", true, -1),

    @SerializedName("Crossdressing")
    CROSS_DRESSING(81, 44, "Cross Dressing", true, -1),

    @SerializedName("Villainess")
    VILLAINESS(-1, 81, "Villainess", true, -1);

    companion object {
        fun valueOfOrDefaultAnime(value: Int?) = entries.find { it.animeId == value }
        fun valueOfOrDefaultManga(value: Int?) = entries.find { it.mangaId == value }

        fun listAnime(sfw: Boolean) =
            entries.filter { it.animeId != -1 && (!sfw || it.sfw) }.sortedBy { it.showName }


        fun listManga(sfw: Boolean) =
            entries.filter { it.mangaId != -1 && (!sfw || it.sfw) }.sortedBy { it.showName }

    }
}

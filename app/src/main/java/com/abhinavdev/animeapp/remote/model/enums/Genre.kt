package com.abhinavdev.animeapp.remote.model.enums

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
    val displayName: String
) {
    @SerializedName("All")
    ALL(-2, -2, "All"),

    @SerializedName("Action")
    ACTION(1, 1, "Action"),

    @SerializedName("Adventure")
    ADVENTURE(2, 2, "Adventure"),

    @SerializedName("Racing")
    RACING(3, 3, "Racing"),

    @SerializedName("Comedy")
    COMEDY(4, 4, "Comedy"),

    @SerializedName("Avant Garde")
    AVANT_GARDE(5, 5, "Avant Garde"),

    @SerializedName("Mythology")
    MYTHOLOGY(6, 6, "Mythology"),

    @SerializedName("Mystery")
    MYSTERY(7, 7, "Mystery"),

    @SerializedName("Drama")
    DRAMA(8, 8, "Drama"),

    @SerializedName("Ecchi")
    ECCHI(9, 9, "Ecchi"),

    @SerializedName("Fantasy")
    FANTASY(10, 10, "Fantasy"),

    @SerializedName("Strategy Game")
    STRATEGY_GAME(11, 11, "Strategy Game"),

    @SerializedName("Hentai")
    HENTAI(12, 12, "Hentai"),

    @SerializedName("Historical")
    HISTORICAL(13, 13, "Historical"),

    @SerializedName("Horror")
    HORROR(14, 14, "Horror"),

    @SerializedName("Kids")
    KIDS(15, 15, "Kids"),

    @SerializedName("Martial Arts")
    MARTIAL_ARTS(17, 17, "Martial Arts"),

    @SerializedName("Mecha")
    MECHA(18, 18, "Mecha"),

    @SerializedName("Music")
    MUSIC(19, 19, "Music"),

    @SerializedName("Parody")
    PARODY(20, 20, "Parody"),

    @SerializedName("Samurai")
    SAMURAI(21, 21, "Samurai"),

    @SerializedName("Romance")
    ROMANCE(22, 22, "Romance"),

    @SerializedName("School")
    SCHOOL(23, 23, "School"),

    @SerializedName("Sci-Fi")
    SCI_FI(24, 24, "Sci-Fi"),

    @SerializedName("Shoujo")
    SHOUJO(25, 25, "Shoujo"),

    @SerializedName("Girls Love")
    GIRLS_LOVE(26, 26, "Girls Love"),

    @SerializedName("Shounen")
    SHOUNEN(27, 27, "Shounen"),

    @SerializedName("Boys Love")
    BOYS_LOVE(28, 28, "Boys Love"),

    @SerializedName("Space")
    SPACE(29, 29, "Space"),

    @SerializedName("Sports")
    SPORTS(30, 30, "Sports"),

    @SerializedName("Super Power")
    SUPERPOWER(31, 31, "Super Power"),

    @SerializedName("Vampire")
    VAMPIRE(32, 32, "Vampire"),

    @SerializedName("Harem")
    HAREM(35, 35, "Harem"),

    @SerializedName("Slice of Life")
    SLICE_OF_LIFE(36, 36, "Slice of Life"),

    @SerializedName("Supernatural")
    SUPERNATURAL(37, 37, "Supernatural"),

    @SerializedName("Military")
    MILITARY(38, 38, "Military"),

    @SerializedName("Detective")
    DETECTIVE(39, 39, "Detective"),

    @SerializedName("Psychological")
    PSYCHOLOGICAL(40, 40, "Psychological"),

    @SerializedName("Suspense")
    SUSPENSE(41, 45, "Suspense"),

    @SerializedName("Seinen")
    SEINEN(42, 41, "Seinen"),

    @SerializedName("Josei")
    JOSEI(43, 42, "Josei"),

    @SerializedName("Award Winning")
    AWARD_WINNING(46, 46, "Award Winning"),

    @SerializedName("Gourmet")
    GOURMET(47, 47, "Gourmet"),

    @SerializedName("Workplace")
    WORKPLACE(48, 48, "Workplace"),

    @SerializedName("Erotica")
    EROTICA(49, 49, "Erotica"),

    @SerializedName("Adult Cast")
    ADULT_CAST(50, 50, "Adult Cast"),

    @SerializedName("Anthropomorphic")
    ANTHROPOMORPHIC(51, 51, "Anthropomorphic"),

    @SerializedName("CGDCT")
    CGDCT(52, 52, "CGDCT"),

    @SerializedName("Childcare")
    CHILDCARE(53, 53, "Childcare"),

    @SerializedName("Combat Sports")
    COMBAT_SPORTS(54, 54, "Combat Sports"),

    @SerializedName("Delinquents")
    DELINQUENTS(55, 55, "Delinquents"),

    @SerializedName("Educational")
    EDUCATIONAL(56, 56, "Educational"),

    @SerializedName("Gag Humor")
    GAG_HUMOR(57, 57, "Gag Humor"),

    @SerializedName("Gore")
    GORE(58, 58, "Gore"),

    @SerializedName("High Stakes Game")
    HIGH_STAKES_GAME(59, 59, "High Stakes Game"),

    @SerializedName("Idols (Female)")
    IDOLS_FEMALE(60, 60, "Idols (Female)"),

    @SerializedName("Idols (Male)")
    IDOLS_MALE(61, 61, "Idols (Male)"),

    @SerializedName("Isekai")
    ISEKAI(62, 62, "Isekai"),

    @SerializedName("Iyashikei")
    IYASHIKEI(63, 63, "Iyashikei"),

    @SerializedName("Love Polygon")
    LOVE_POLYGON(64, 64, "Love Polygon"),

    @SerializedName("Magical Sex Shift")
    MAGICAL_SEX_SHIFT(65, 65, "Magical Sex Shift"),

    @SerializedName("Mahou Shoujo")
    MAHOU_SHOUJO(66, 66, "Mahou Shoujo"),

    @SerializedName("Medical")
    MEDICAL(67, 67, "Medical"),

    @SerializedName("Memoir")
    MEMOIR(-3, 68, "Memoir"),

    @SerializedName("Organized Crime")
    ORGANIZED_CRIME(68, 69, "Organized Crime"),

    @SerializedName("Otaku Culture")
    OTAKU_CULTURE(69, 70, "Otaku Culture"),

    @SerializedName("Performing Arts")
    PERFORMING_ARTS(70, 71, "Performing Arts"),

    @SerializedName("Pets")
    PETS(71, 72, "Pets"),

    @SerializedName("Reincarnation")
    REINCARNATION(72, 73, "Reincarnation"),

    @SerializedName("Reverse Harem")
    REVERSE_HAREM(73, 74, "Reverse Harem"),

    @SerializedName("Romantic Subtext")
    ROMANTIC_SUBTEXT(74, 75, "Romantic Subtext"),

    @SerializedName("Showbiz")
    SHOWBIZ(75, 76, "Showbiz"),

    @SerializedName("Survival")
    SURVIVAL(76, 77, "Survival"),

    @SerializedName("Team Sports")
    TEAM_SPORTS(77, 78, "Team Sports"),

    @SerializedName("Time Travel")
    TIME_TRAVEL(78, 79, "Time Travel"),

    @SerializedName("Video Game")
    VIDEO_GAME(79, 80, "Video Game"),

    @SerializedName("Visual Arts")
    VISUAL_ARTS(80, 82, "Visual Arts"),

    @SerializedName("Crossdressing")
    CROSSDRESSING(81, 44, "Crossdressing"),

    @SerializedName("Villainess")
    VILLAINESS(-5, 81, "Villainess")
}

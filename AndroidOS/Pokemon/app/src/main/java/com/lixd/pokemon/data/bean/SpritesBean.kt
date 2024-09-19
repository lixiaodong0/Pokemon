package com.lixd.pokemon.data.bean

import com.google.gson.annotations.SerializedName

data class SpritesBean(
    @SerializedName("back_default")
    val backDefault: String,
    @SerializedName("back_shiny")
    val backShiny: String,
    @SerializedName("front_default")
    val frontDefault: String,
    @SerializedName("front_shiny")
    val frontShiny: String,
    @SerializedName("other")
    val other: OtherSpritesBean,
)

data class OtherSpritesBean(
    //官方图
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork,
)

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String,
    @SerializedName("front_shiny")
    val frontShiny: String,
)
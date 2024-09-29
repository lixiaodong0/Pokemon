package com.lixd.pokemon.data.bean

import com.google.gson.annotations.SerializedName

data class StatsBean(
    @SerializedName("base_stat")
    val baseStat: Int,
    @SerializedName("effort")
    val effort: String,
    @SerializedName("stat")
    val stat: StatBean,
)

data class StatBean(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
)
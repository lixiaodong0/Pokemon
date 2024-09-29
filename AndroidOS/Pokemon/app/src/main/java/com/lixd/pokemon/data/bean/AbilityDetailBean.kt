package com.lixd.pokemon.data.bean

import com.google.gson.annotations.SerializedName

data class AbilityDetailBean(
    @SerializedName("name")
    val name: String,
    @SerializedName("effect_entries")
    val effectEntries: List<Effect>?,
    @SerializedName("names")
    val names: List<NamesBean>?,
)

data class Effect(
    @SerializedName("effect")
    val effect: String,
    @SerializedName("short_effect")
    val shortEffect: String,
    @SerializedName("language")
    val language: ResourceBean?
)

data class NamesBean(
    @SerializedName("name")
    val name: String,
    @SerializedName("language")
    val language: ResourceBean?
)




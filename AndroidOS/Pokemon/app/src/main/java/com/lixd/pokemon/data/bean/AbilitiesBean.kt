package com.lixd.pokemon.data.bean

import com.google.gson.annotations.SerializedName

data class AbilitiesBean(
    @SerializedName("ability")
    val ability: AbilityBean,
    @SerializedName("is_hidden")
    val isHidden: Boolean,
    @SerializedName("slot")
    val slot: Int,
)

data class AbilityBean(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
)
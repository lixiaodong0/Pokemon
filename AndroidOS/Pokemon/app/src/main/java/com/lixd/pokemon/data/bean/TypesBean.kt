package com.lixd.pokemon.data.bean

import com.google.gson.annotations.SerializedName

data class TypesBean(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: ResourceBean
)


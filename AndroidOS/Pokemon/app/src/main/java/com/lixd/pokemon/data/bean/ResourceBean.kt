package com.lixd.pokemon.data.bean

import com.google.gson.annotations.SerializedName

data class ResourceBean(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
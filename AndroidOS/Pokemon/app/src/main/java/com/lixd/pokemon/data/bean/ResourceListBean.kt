package com.lixd.pokemon.data.bean

data class ResourceListBean(
    //资源总数量
    val count: Int,
    //下一个分页资源
    val next: String?,
    //上一个分页资源
    val previous: String?,
    //资源数据
    val results: List<ResourceBean>?,
)
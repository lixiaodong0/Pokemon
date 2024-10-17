package com.lixd.pokemon.ui.pokemon

import com.lixd.pokemon.data.bean.PokemonIndexBean

//UI状态
data class PokemonIndexState(
    val currentIndex: Int = 0,
    val currentPokemonIndexBean: PokemonIndexBean? = null,
    val totalCount: Int = 0,
    val firstSelect: Boolean = false,
)

//UI动作
sealed class PokemonIndexAction {
    class UpdateCount(val count: Int) : PokemonIndexAction()

    class UpdateSelectedIndex(val index: Int, val data: PokemonIndexBean) : PokemonIndexAction()
    data object FirstSelectFinish : PokemonIndexAction()
}

//一次性事件
sealed class PokemonIndexEvent {
    class Toast(val msg: String) : PokemonIndexEvent()
}
package com.lixd.pokemon.ui.desc

import com.lixd.pokemon.data.bean.AbilityDetailBean
import com.lixd.pokemon.data.bean.PokemonBean
import com.lixd.pokemon.data.bean.PokemonIndexBean

//UI状态
data class PokemonDescriptionState(
    val data: PokemonBean? = null,
    val ability: AbilityDetailBean? = null,
    val currentTabIndex: Int = 0,
    val isNext: Boolean = false,
    val isPrevious: Boolean = false,
)

//UI动作
sealed class PokemonDescriptionAction {
    class SetPokemonList(
        val data: List<PokemonIndexBean>,
    ) :
        PokemonDescriptionAction()

    data object Previous : PokemonDescriptionAction()
    data object Next : PokemonDescriptionAction()

    class GetPokemonData(val id: Int) : PokemonDescriptionAction()
    class UpdateTabIndex(val tabIndex: Int) : PokemonDescriptionAction()
}

//一次性事件
sealed class PokemonDescriptionEvent {
    class Toast(val msg: String) : PokemonDescriptionEvent()
}
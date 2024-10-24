import CommonConstant from '../constant/CommonConstant'
import AbilityDetailBean from '../data/AbilityDetailBean'
import PokemonBean from '../data/PokemonBean'
import HttpUtil from '../util/HttpUtil'

export default class PokemonDescriptionViewModel {
  async getPokemon(id: number): Promise<PokemonBean> {
    const url = CommonConstant.BASE_URL + "api/v2/pokemon/" + id
    const resultString = await HttpUtil.get(url)
    let result = new PokemonBean()
    result = JSON.parse(resultString)
    return result
  }

  async getAbility(url: string): Promise<AbilityDetailBean> {
    const resultString = await HttpUtil.get(url)
    let result = new PokemonBean()
    result = JSON.parse(resultString)
    return result
  }
}
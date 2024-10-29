import CommonConstant from '../constant/CommonConstant'
import AbilityDetailBean from '../data/AbilityDetailBean'
import PokemonBean from '../data/PokemonBean'
import KVDBManager from '../db/KVDBManager'
import HttpUtil from '../util/HttpUtil'
import { emitter } from '@kit.BasicServicesKit';
import UpdateAvatarEvent from '../events/EasyEventBus'
import { BusinessError, commonEventManager } from '@kit.BasicServicesKit';
import { common } from '@kit.AbilityKit'
import EasyEventBus from '../events/EasyEventBus'
import { OfficialArtwork } from '../data/SpritesBean'

export default class PokemonDescriptionViewModel {
  async getPokemon(id: number): Promise<PokemonBean> {
    const url = CommonConstant.BASE_URL + "api/v2/pokemon/" + id
    let resultString = await HttpUtil.get(url)
    resultString = resultString.replace("official-artwork", "officialArtwork")
    let result = new PokemonBean()
    result = JSON.parse(resultString)
    console.info("发送更新头像事件:" + result.sprites?.other?.officialArtwork?.front_default ?? "")
    //发送事件
    EasyEventBus.getInstance().send("updateAvatarEvent", result.sprites?.other?.officialArtwork?.front_default ?? "")

    //缓存到KV数据库
    let coverJson = JSON.stringify(result.sprites)
    let key = "cover_" + id
    KVDBManager.getInstance().put(key, coverJson)
    return result
  }

  async getAbility(url: string): Promise<AbilityDetailBean> {
    const resultString = await HttpUtil.get(url)
    let result = new PokemonBean()
    result = JSON.parse(resultString)
    return result
  }
}
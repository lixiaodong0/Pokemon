import CommonConstant from '../constant/CommonConstant'
import AbilityDetailBean from '../data/AbilityDetailBean'
import PokemonBean from '../data/PokemonBean'
import KVDBManager from '../db/KVDBManager'
import HttpUtil from '../util/HttpUtil'
import { emitter } from '@kit.BasicServicesKit';
import UpdateAvatarEvent from '../events/UpdateAvatarEvent'
import { BusinessError, commonEventManager } from '@kit.BasicServicesKit';

export default class PokemonDescriptionViewModel {
  async getPokemon(id: number): Promise<PokemonBean> {
    const url = CommonConstant.BASE_URL + "api/v2/pokemon/" + id
    const resultString = await HttpUtil.get(url)
    let result = new PokemonBean()
    result = JSON.parse(resultString)

    console.info("发送更新头像事件:" + result.sprites?.front_default ?? "")

    //发送事件
    // emitter.emit({
    //   eventId: 1,
    //   priority: emitter.EventPriority.HIGH
    // }, {
    //   data: {
    //     avatar: result.sprites?.front_default ?? ""
    //   }
    // })
    commonEventManager.publish("UpdateAvatarEvent", {
      code: 1,
      data: result.sprites?.front_default ?? ""
    }, (err: BusinessError) => {
      if (err) {
        console.error('PublishCallBack err = ' + JSON.stringify(err));
      } else {
        console.info('Publish success');
      }
    });


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
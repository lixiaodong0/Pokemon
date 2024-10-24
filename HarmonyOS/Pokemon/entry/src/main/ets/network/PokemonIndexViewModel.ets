import CommonConstant from '../constant/CommonConstant'
import ResourceListBean from '../data/ResourceListBean'
import HttpUtil from '../util/HttpUtil'
import { plainToClass } from "class-transformer";
import PokemonIndexBean from '../data/PokemonIndexBean';
import KVDBManager from '../db/KVDBManager';
import SpritesBean from '../data/SpritesBean';

export default class PokemonIndexViewModel {
  page: number = 0
  pageSize: number = 20
  next?: string
  totalCount: number = 0

  async getPokemonList(): Promise<PokemonIndexBean[]> {
    let url = ""
    if (this.next == null) {
      url = CommonConstant.BASE_URL + "api/v2/pokemon"
      this.page = 0
    } else {
      url = this.next
      this.page++
    }
    const resultJson = await HttpUtil.get(url)
    let resourceListBean = new ResourceListBean()
    resourceListBean = JSON.parse(resultJson)
    this.next = resourceListBean.next
    this.totalCount = resourceListBean.count ?? 0
    //转换数据结构
    let pokemonIndexBeanList: PokemonIndexBean[] = []
    if (resourceListBean.results != null && resourceListBean.results.length > 0) {
      console.info(`key:` + "开始遍历");
      for (let index = 0; index < resourceListBean.results.length; index++) {
        let item = resourceListBean.results[index]
        let num = (this.page * this.pageSize) + (index + 1)

        let key = "cover_" + num
        let coverJson: string = await KVDBManager.getInstance().get(key)
        let spritesBean: SpritesBean = new SpritesBean()
        if (coverJson.length > 0) {
          spritesBean = JSON.parse(coverJson)
        }
        let avatar = spritesBean.other?.officialArtwork?.frontDefault ?? ""
        pokemonIndexBeanList.push(new PokemonIndexBean(num, item.name, avatar))

        console.info(`key:` + key);
      }
    }
    return pokemonIndexBeanList
  }
}
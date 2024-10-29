import PokemonIndexBean from '../data/PokemonIndexBean'
import PokemonIndexViewModel from '../network/PokemonIndexViewModel'
import PokemonIndexItem from '../view/PokemonIndexItem'
import promptAction from '@ohos.promptAction';
import PokemonIndexLayoutBackground from '../view/PokemonIndexLayoutBackground';
import PokemonIndexTopBar from '../view/PokemonIndexTopBar';
import { router } from '@kit.ArkUI';
import EasyEventBus from '../events/EasyEventBus'
import KVDBManager from '../db/KVDBManager';

@Preview
@Entry
@Component
struct PokemonIndexScreen {
  @State pokemonIndexList: Array<PokemonIndexBean> = []
  @State currentIndex: number = 0
  @State currentAvatar: string = ""
  @State totalCount: number = 0
  preloadCount = 5
  loadingState: boolean = false
  private pokemonIndexViewModel: PokemonIndexViewModel = new PokemonIndexViewModel()
  private onUpdateAvatarEvent = (avatar: string) => {
    console.info('onUpdateAvatarEvent:' + "收到更新头像回调:" + avatar);
    this.currentAvatar = avatar
  }

  aboutToAppear() {
    console.info('PokemonIndexScreen:' + "[aboutToAppear]");
    this.getPokemonList()
    EasyEventBus.getInstance().on("updateAvatarEvent", this.onUpdateAvatarEvent)
  }

  getPokemonList() {
    this.pokemonIndexViewModel.getPokemonList().then((data: PokemonIndexBean[]) => {
      console.info(`key:` + "请求完成");
      this.pokemonIndexList = this.pokemonIndexList.concat(data)
      this.totalCount = this.pokemonIndexViewModel.totalCount
      this.loadingState = false
    }).catch(() => {
      this.loadingState = false
    })
  }

  build() {
    Stack({ alignContent: Alignment.Top }) {
      PokemonIndexLayoutBackground()
      PokemonIndexTopBar({ totalCount: this.totalCount })
      Row() {
        Stack({ alignContent: Alignment.Bottom }) {
          Image(this.currentAvatar).width("80%").height("80%").objectFit(ImageFit.Contain)
        }
        .width("100%")
        .height("100%")
        .layoutWeight(1)

        List({ space: 16 }) {
          ForEach(this.pokemonIndexList, (item: PokemonIndexBean, index: number) => {
            ListItem() {
              PokemonIndexItem({ data: item, selected: index == this.currentIndex }).onClick(() => {
                console.info('onClick:' + index + ",name:" + item.name + ",num:" + item.number);
                if (this.currentIndex != index) {
                  this.currentIndex = index
                } else {
                  router.pushUrl({
                    url: "pages/PokemonDescriptionScreen",
                    params: {
                      id: item.number
                    }
                  }, router.RouterMode.Standard)
                }
              })
            }
          })
        }
        .width("100%")
        .height("100%")
        .layoutWeight(1)
        .padding({ top: 74 })
        .onScrollIndex((start: number, end: number, center: number) => {
          console.info('[onScrollIndex]:start' + start + ",end:" + end + ",center:" + center);
          if (end >= this.pokemonIndexList.length - this.preloadCount) {
            if (!this.loadingState) {
              console.info('[onScrollIndex]:开始预加载');
              this.loadingState = true
              this.getPokemonList()
            }
          }
        })
      }.width("100%").height("100%")
    }.width("100%").height("100%")
  }
}
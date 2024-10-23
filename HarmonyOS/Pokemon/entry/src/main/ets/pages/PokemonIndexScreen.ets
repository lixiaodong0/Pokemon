import PokemonIndexBean from '../data/PokemonIndexBean'
import PokemonIndexViewModel from '../network/PokemonIndexViewModel'
import PokemonIndexItem from '../view/PokemonIndexItem'
import promptAction from '@ohos.promptAction';
import PokemonIndexLayoutBackground from '../view/PokemonIndexLayoutBackground';
import PokemonIndexTopBar from '../view/PokemonIndexTopBar';

@Preview
@Entry
@Component
struct PokemonIndexScreen {
  @State pokemonIndexList: Array<PokemonIndexBean> = []
  @State currentIndex: number = 0
  @State totalCount: number = 0
  preloadCount = 5
  loadingState: boolean = false
  private url: string = "http://gips2.baidu.com/it/u=195724436,3554684702&fm=3028&app=3028&f=JPEG&fmt=auto?w=1280&h=960"
  private pokemonIndexViewModel: PokemonIndexViewModel = new PokemonIndexViewModel()

  aboutToAppear() {
    console.info('PokemonIndexScreen:' + "[aboutToAppear]");
    this.getPokemonList()
  }

  getPokemonList() {
    this.pokemonIndexViewModel.getPokemonList().then((data: PokemonIndexBean[]) => {
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
        Column() {
          Image(this.url).width("80%").height("80%").objectFit(ImageFit.ScaleDown)
        }.width("100%").height("100%").layoutWeight(1).justifyContent(FlexAlign.Center)

        List({ space: 16 }) {
          ForEach(this.pokemonIndexList, (item: PokemonIndexBean, index: number) => {
            ListItem() {
              PokemonIndexItem({ data: item, selected: index == this.currentIndex }).onClick(() => {
                console.info('onClick:' + index + ",name:" + item.name + ",num:" + item.number);
                if (this.currentIndex != index) {
                  this.currentIndex = index
                } else {
                  promptAction.showToast({ message: "跳转详情" + item.name })
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
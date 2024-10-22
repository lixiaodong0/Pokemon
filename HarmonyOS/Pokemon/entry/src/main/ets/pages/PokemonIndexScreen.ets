import PokemonIndexBean from '../model/PokemonIndexBean'
import PokemonIndexItem from '../view/PokemonIndexItem'

@Preview
@Entry
@Component
struct PokemonIndexScreen {
  private pokemonIndexs: Array<PokemonIndexBean> = [
    new PokemonIndexBean(1, "Pokemon1"),
    new PokemonIndexBean(2, "Pokemon2"),
    new PokemonIndexBean(3, "Pokemon3"),
  ]
  private url: string = "http://gips2.baidu.com/it/u=195724436,3554684702&fm=3028&app=3028&f=JPEG&fmt=auto?w=1280&h=960"

  build() {
    Stack() {
      Row() {
        Column() {
          Image(this.url).width("80%").height("80%").objectFit(ImageFit.ScaleDown)
        }.width("100%").height("100%").layoutWeight(1).justifyContent(FlexAlign.Center)

        List({ space: 16 }) {
          ForEach(this.pokemonIndexs, (item: PokemonIndexBean, index: number) => {
            ListItem() {
              PokemonIndexItem({ data: item, selected: index == 1 })
            }
          })
        }.width("100%").height("100%").layoutWeight(1).backgroundColor(Color.Orange)
      }
    }.width("100%").height("100%")
  }
}
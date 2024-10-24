import PokemonBean from '../data/PokemonBean'
import PokemonDescriptionViewModel from '../network/PokemonDescriptionViewModel'
import PokemonDescriptionInfoCard from '../view/PokemonDescriptionInfoCard'
import PokemonDescriptionLayoutBackground from '../view/PokemonDescriptionLayoutBackground'
import { router } from '@kit.ArkUI'


class Params {
  id: number = 0
}

@Preview
@Entry
@Component
struct PokemonDescriptionScreen {
  @State pokemonData: PokemonBean = new PokemonBean()
  @State attributeValue: Array<string> = []
  private pokemonDescriptionViewModel = new PokemonDescriptionViewModel()

  aboutToAppear(): void {
    let params = router.getParams() as Params
    console.info("params:" + params.id)
    this.pokemonDescriptionViewModel.getPokemon(params.id).then((data) => {
      this.pokemonData = data
      //转换数据
      if (data.types != null && data.types.length > 0) {
        data.types.forEach((item) => {
          if (item.type?.name != null) {
            this.attributeValue.push(item.type?.name)
          }
        })
      }
    })
  }

  build() {
    Stack({ alignContent: Alignment.TopStart }) {
      PokemonDescriptionLayoutBackground()
      PokemonDescriptionInfoCard({
        nameValue: this.pokemonData.name,
        heightValue: (this.pokemonData.height ? this.pokemonData.height / 10 : 0) + "m",
        weightValue: (this.pokemonData.weight ? this.pokemonData.weight / 10 : 0) + "kg",
        killExperienceValue: this.pokemonData.base_experience + "",
        attributeValue: this.attributeValue
      })

      Stack({ alignContent: Alignment.Bottom }) {
        Button("返回").onClick(() => {
          router.back()
        })
      }.width("100%").height("100%")
    }
  }
}
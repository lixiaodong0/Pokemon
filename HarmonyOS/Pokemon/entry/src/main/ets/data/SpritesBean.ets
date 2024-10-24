export default class SpritesBean {
  back_default?: string
  back_shiny?: string
  front_default?: string
  front_shiny?: string
  other?: OtherSpritesBean
}

class OtherSpritesBean {
  officialArtwork?: OfficialArtwork
}

class OfficialArtwork {
  frontDefault?: string
  frontShiny?: string
}
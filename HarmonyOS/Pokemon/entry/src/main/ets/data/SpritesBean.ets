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

export class OfficialArtwork {
  front_default?: string
  front_shiny?: string
}
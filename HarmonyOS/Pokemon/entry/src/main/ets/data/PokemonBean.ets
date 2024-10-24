import AbilitiesBean from './AbilitiesBean'
import MovesBean from './MovesBean'
import SpritesBean from './SpritesBean'
import StatsBean from './StatsBean'
import TypesBean from './TypesBean'

export default class PokemonBean {
  id?: number
  name?: string
  base_experience?: number
  height?: number
  weight?: number
  stats?: StatsBean[]
  abilities?: AbilitiesBean[]
  sprites?: SpritesBean
  types?: TypesBean[]
  moves?: MovesBean[]
}
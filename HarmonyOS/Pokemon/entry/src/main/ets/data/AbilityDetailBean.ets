import ResourceBean from './ResourceBean'

export default class AbilityDetailBean {
  name?: string
  effect_entries?: Effect[]
  names?: NamesBean[]
}

class Effect {
  effect?: string
  short_effect?: string
  language?: ResourceBean
}

class NamesBean {
  name?: string
  language?: ResourceBean
}
export default class PokemonIndexBean {
  number: number = 0;
  name: string = "";
  avatar: string = "";

  constructor(number: number = 0, name: string = "", avatar: string = "") {
    this.number = number;
    this.name = name;
    this.avatar = avatar;
  }
}
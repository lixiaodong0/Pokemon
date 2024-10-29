@Preview
@Component
export struct NumberKeyWidget {
  @Prop _key: string = "A"
  @Prop desc: string = "See Details"

  build() {
    Row() {
      Stack() {
        Text(this._key).fontColor(Color.Black).fontSize(12)
      }
      .width(15)
      .height(15)
      .backgroundColor(Color.Gray)
      .clip(true)
      .borderRadius(15)

      Text(this.desc).fontColor(Color.White).fontSize(10).margin({ left: 2 })
    }
  }
}

@Preview
@Component
export struct ImageKeyWidget {
  @Prop _key: string = "app.media.ic_arrow_forward"
  @Prop desc: string = "See Details"

  build() {
    Row() {
      Stack() {
        Image($r(this._key))
      }
      .width(15)
      .height(15)
      .backgroundColor(Color.Gray)
      .clip(true)
      .borderRadius(15)

      Text(this.desc).fontColor(Color.White).fontSize(10).margin({ left: 2 })
    }
  }
}
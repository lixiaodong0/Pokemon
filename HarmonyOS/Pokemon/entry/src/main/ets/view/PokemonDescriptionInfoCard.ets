@Preview
@Component
export default struct PokemonDescriptionInfoCard {
  @Prop nameValue: string = ""
  @Prop heightValue: string = ""
  @Prop weightValue: string = ""
  @Prop attributeValue: Array<string> = ["飞行","草"]
  @Prop killExperienceValue: string = ""

  build() {
    Column() {
      KeyValueInfoItem({ _key: "名称", _value: this.nameValue })
      Line().width("100%").height(1).backgroundColor(0XFFefefef)
      AttributeInfoItem({
        _key: "属性",
        primaryAttrValue: this.attributeValue[0],
        secondaryAttrValue: this.attributeValue[1]
      })
      Line().width("100%").height(1).backgroundColor(0XFFefefef)
      KeyValueInfoItem({ _key: "身高", _value: this.heightValue })
      Line().width("100%").height(1).backgroundColor(0XFFefefef)
      KeyValueInfoItem({ _key: "体重", _value: this.weightValue })
      Line().width("100%").height(4).backgroundColor(Color.Transparent)
      KeyValueInfoItem({ _key: "获得经验值", _value: this.killExperienceValue })
    }.width("100%")
  }
}

@Component
struct AttributeInfoItem {
  @Prop _key: string = "属性"
  @Prop primaryAttrValue?: string = ""
  @Prop secondaryAttrValue?: string = ""

  build() {
    Row() {
      Row() {
        Text(this._key).fontColor(Color.Black).fontSize(16)
      }.layoutWeight(0.45).backgroundColor(0xffdbdcd7).height("100%").justifyContent(FlexAlign.Center)

      Row() {
        Row() {
          if (this.primaryAttrValue != null && this.primaryAttrValue.length > 0) {
            PokemonAttributeWidget({ _value: this.primaryAttrValue })
          }
        }.layoutWeight(1).margin({ right: 4 }).height("50%")

        Row() {
          if (this.secondaryAttrValue != null && this.secondaryAttrValue.length > 0) {
            PokemonAttributeWidget({ _value: this.secondaryAttrValue })
          }
        }.layoutWeight(1).margin({ left: 4 }).height("50%")
      }
      .layoutWeight(0.55)
      .backgroundColor(Color.White)
      .height("100%")
      .justifyContent(FlexAlign.Center)
      .padding({ left: 16, right: 16 })
    }.width("100%").height(40)
  }
}

@Component
struct KeyValueInfoItem {
  @Prop _key: string = ""
  @Prop _value: string = ""

  build() {
    Row() {
      Row() {
        Text(this._key).fontColor(Color.Black).fontSize(16)
      }.layoutWeight(0.45).backgroundColor(0xffdbdcd7).height("100%").justifyContent(FlexAlign.Center)

      Row() {
        Text(this._value).fontColor(Color.Black).fontSize(16)
      }.layoutWeight(0.55).backgroundColor(Color.White).height("100%").justifyContent(FlexAlign.Center)
    }.width("100%").height(40)
  }
}

@Component
struct PokemonAttributeWidget {
  private settings: RenderingContextSettings = new RenderingContextSettings(true);
  private context: CanvasRenderingContext2D = new CanvasRenderingContext2D(this.settings);
  @Prop _value: string = ""

  build() {
    Stack() {
      Canvas(this.context)
        .width("100%")
        .height("100%")
        .onReady(() => {
          let width = this.context.width
          let height = this.context.height
          let startX = width * 0.2
          //第二层背景
          this.context.beginPath()
          let path = new Path2D()
          path.moveTo(0, 0)
          path.lineTo(startX, 0)
          path.lineTo(startX / 2, height)
          path.lineTo(0, height)
          path.closePath()
          this.context.fillStyle = 0xff5893d2
          this.context.fill(path)
        })//第一层背景
        .backgroundColor(0xff424f58)

      Row() {
        Image("").width(20).height(20)
        Text(this._value).fontSize(16).fontColor(Color.White).layoutWeight(1).textAlign(TextAlign.Center)
      }.justifyContent(FlexAlign.Start)
    }.clip(true).borderRadius(2)

  }
}


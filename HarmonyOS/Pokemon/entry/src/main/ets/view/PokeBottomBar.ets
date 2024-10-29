import { ImageKeyWidget, NumberKeyWidget } from './NumberKeyWidget'

@Preview
@Component
export struct PokeBottomBar {
  @Prop showBack: boolean = true
  onBack: () => void = () => {
  }
  @Prop otherItems: PokeBottomBarItem[] = []

  build() {
    Row() {
      Row({ space: 16 }) {
        if (this.otherItems.length > 0) {
          ForEach(this.otherItems, (item: PokeBottomBarItem) => {
            if (item.isImage) {
              ImageKeyWidget({ _key: item.key, desc: item.desc }).onClick(item.onClick)
            } else {
              NumberKeyWidget({ _key: item.key, desc: item.desc }).onClick(item.onClick)
            }
          })
        }
      }

      if (this.showBack) {
        NumberKeyWidget({ _key: "B", desc: "Back" })
          .onClick(this.onBack).margin({ left: 16 })
      }
    }
    .width("100%")
    .height(20)
    .backgroundColor(Color.Black)
    .padding({ left: 16, right: 16 })
    .justifyContent(FlexAlign.End)
  }
}

export class PokeBottomBarItem {
  isImage: boolean = false
  key: string = ""
  desc: string = ""
  onClick: () => void = () => {
  }

  constructor(isImage: boolean = false, key: string, desc: string, onClick: () => void) {
    this.isImage = isImage
    this.key = key
    this.desc = desc
    this.onClick = onClick
  }
}
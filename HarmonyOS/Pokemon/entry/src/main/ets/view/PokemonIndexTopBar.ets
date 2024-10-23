@Preview
@Component
export default struct PokemonIndexTopBar {
  private settings: RenderingContextSettings = new RenderingContextSettings(true);
  private context: CanvasRenderingContext2D = new CanvasRenderingContext2D(this.settings);
  @Prop totalCount: number = 0

  build() {
    Stack({ alignContent: Alignment.Top }) {
      Canvas(this.context)
        .width("100%")
        .height("100%")
        .onReady(() => {
          let width = this.context.width
          let height = this.context.height
          let centerX = width / 2
          let offset = centerX / 3

          //第一层背景
          this.context.beginPath()
          let path = new Path2D()
          path.moveTo(0, 0)
          path.lineTo(width, 0)
          path.lineTo(width, height)
          path.lineTo(0, height)
          path.closePath()
          this.context.fillStyle = 0XFFefefef
          this.context.fill(path)

          //第二层背景
          this.context.beginPath()
          let path2 = new Path2D()
          path2.moveTo(centerX - offset, height)
          path2.lineTo(centerX, 0)
          path2.lineTo(width, 0)
          path2.lineTo(width, height)
          path2.closePath()
          this.context.fillStyle = 0xE6000000
          this.context.fill(path2)

          this.context.clearRect(0, 0, width, 10)
          this.context.clearRect(0, 50, width, height)
        })

      Row() {
        Row() {
          Text(this.totalCount + "").fontColor(Color.Black).fontSize(14)
        }.layoutWeight(1).justifyContent(FlexAlign.Center)

        Row() {
          Text("By Number").fontColor(Color.White).fontSize(16)
        }.layoutWeight(1).justifyContent(FlexAlign.Center)
      }.margin({ top: 10 }).height(40).width("100%")
    }
  }
}
@Preview
@Component
export default struct PokemonIndexLayoutBackground {
  private settings: RenderingContextSettings = new RenderingContextSettings(true);
  private context: CanvasRenderingContext2D = new CanvasRenderingContext2D(this.settings);

  build() {
    Canvas(this.context)
      .width("100%")
      .height("100%")
      .onReady(() => {
        let width = this.context.width
        let height = this.context.height
        let centerX = width / 2
        let offset = centerX / 3

        //第二层背景
        this.context.beginPath()
        let path = new Path2D()
        path.moveTo(centerX - offset, height)
        path.lineTo(centerX, 0)
        path.lineTo(width, 0)
        path.lineTo(width, height)
        path.closePath()
        this.context.fillStyle = 0xfff3533b
        this.context.fill(path)

        //第三层背景
        this.context.beginPath()
        let path2 = new Path2D()
        path2.moveTo(centerX, height)
        path2.lineTo(centerX + offset, 0)
        path2.lineTo(width, 0)
        path2.lineTo(width, height)
        path2.closePath()
        this.context.fillStyle = 0xfffa7346
        this.context.fill(path2)
      })//第一层背景
      .backgroundColor(0xfffdfcf8)
  }
}
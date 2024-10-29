import PokemonIndexBean from '../data/PokemonIndexBean';
import sendableColorSpaceManager from '@ohos.graphics.sendableColorSpaceManager';

@Component
@Preview
export default struct PokemonIndexItem {
  @Prop data: PokemonIndexBean = new PokemonIndexBean();
  @Prop selected: boolean = true;
  private settings: RenderingContextSettings = new RenderingContextSettings(true);
  private context: CanvasRenderingContext2D = new CanvasRenderingContext2D(this.settings);

  build() {
    Stack({ alignContent: Alignment.TopStart }) {
      Stack() {
        if (this.selected) {
          Canvas(this.context)
            .width("100%")
            .height("100%")
            .onReady(() => {
              let width = this.context.width
              let height = this.context.height
              let centerX = width * 0.4
              let path = new Path2D()
              path.moveTo(0, 0)
              path.lineTo(centerX, 0)
              path.lineTo(centerX - 20, height)
              path.lineTo(0, height)
              path.closePath()
              this.context.fillStyle = 0xffeb521d
              this.context.fill(path)
            })
            .backgroundColor(0xff000200)
            .clip(true)
            .borderRadius(40)
            .shadow({ radius: 2 })
        }
        Row() {
          Text("NO." + this.data.number)
            .fontSize(14)
            .fontColor(this.selected ? Color.White : Color.Black)
            .layoutWeight(0.4)
            .padding({ left: 10 })
          Text(this.data.name).fontSize(14).fontColor(this.selected ? Color.White : Color.Black).layoutWeight(0.6)
        }.width("100%").height("100%")
      }
      .height(40)
      .margin({ left: 50, right: 16 })


      Stack({ alignContent: Alignment.Start }) {
        Image(this.data.avatar)
          .width(40)
          .height(40)
          .objectFit(ImageFit.ScaleDown)
          .margin({ left: 10 })
        if (this.selected) {
          Image($r('app.media.ic_arrow_forward_ios')).width(20).height(20)
        }
      }.height("100%")
    }.width("100%").height(40).padding({ right: 16 })

  }
}
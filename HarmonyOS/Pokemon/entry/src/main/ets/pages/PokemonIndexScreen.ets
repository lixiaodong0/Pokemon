import PokemonIndexBean from '../data/PokemonIndexBean'
import PokemonIndexViewModel from '../network/PokemonIndexViewModel'
import PokemonIndexItem from '../view/PokemonIndexItem'
import promptAction from '@ohos.promptAction';
import PokemonIndexLayoutBackground from '../view/PokemonIndexLayoutBackground';
import PokemonIndexTopBar from '../view/PokemonIndexTopBar';
import { router } from '@kit.ArkUI';
import { emitter } from '@kit.BasicServicesKit';
import UpdateAvatarEvent from '../events/UpdateAvatarEvent';
import { BusinessError, commonEventManager } from '@kit.BasicServicesKit';

// 用于保存创建成功的订阅者对象，后续使用其完成订阅及退订的动作
let subscriber: commonEventManager.CommonEventSubscriber | null = null;
// 订阅者信息，其中的event字段需要替换为实际的事件名称。
let subscribeInfo: commonEventManager.CommonEventSubscribeInfo = {
  events: ['UpdateAvatarEvent'], // 订阅灭屏公共事件

};

@Preview
@Entry
@Component
struct PokemonIndexScreen {
  @State pokemonIndexList: Array<PokemonIndexBean> = []
  @State currentIndex: number = 0
  @State totalCount: number = 0
  preloadCount = 5
  loadingState: boolean = false
  private url: string = "http://gips2.baidu.com/it/u=195724436,3554684702&fm=3028&app=3028&f=JPEG&fmt=auto?w=1280&h=960"
  private pokemonIndexViewModel: PokemonIndexViewModel = new PokemonIndexViewModel()

  aboutToAppear() {
    console.info('PokemonIndexScreen:' + "[aboutToAppear]");
    this.getPokemonList()

    // 创建订阅者回调
    commonEventManager.createSubscriber(subscribeInfo,
      (err: BusinessError, data: commonEventManager.CommonEventSubscriber) => {
        if (err) {
          console.error(`Failed to create subscriber. Code is ${err.code}, message is ${err.message}`);
          return;
        }
        console.info('Succeeded in creating subscriber.');
        subscriber = data;

        // 订阅公共事件回调
        if (subscriber !== null) {
          commonEventManager.subscribe(subscriber, (err: BusinessError, data: commonEventManager.CommonEventData) => {
            if (err) {
              console.error(`Failed to subscribe common event. Code is ${err.code}, message is ${err.message}`);
              return;
            }
            // ...
            console.info("[commonEventManager]收到更新头像事件:" + data.data)
          })
        } else {
          console.error(`Need create subscriber`);
        }
      })

    // emitter.on(UpdateAvatarEvent.event, (eventData: emitter.EventData) => {
    //   console.info("收到更新头像事件:" + eventData.data)
    // })
  }

  getPokemonList() {
    this.pokemonIndexViewModel.getPokemonList().then((data: PokemonIndexBean[]) => {
      console.info(`key:` + "请求完成");
      this.pokemonIndexList = this.pokemonIndexList.concat(data)
      this.totalCount = this.pokemonIndexViewModel.totalCount
      this.loadingState = false
    }).catch(() => {
      this.loadingState = false
    })
  }

  build() {
    Stack({ alignContent: Alignment.Top }) {
      PokemonIndexLayoutBackground()
      PokemonIndexTopBar({ totalCount: this.totalCount })
      Row() {
        Column() {
          Image(this.url).width("80%").height("80%").objectFit(ImageFit.ScaleDown)
        }.width("100%").height("100%").layoutWeight(1).justifyContent(FlexAlign.Center)

        List({ space: 16 }) {
          ForEach(this.pokemonIndexList, (item: PokemonIndexBean, index: number) => {
            ListItem() {
              PokemonIndexItem({ data: item, selected: index == this.currentIndex }).onClick(() => {
                console.info('onClick:' + index + ",name:" + item.name + ",num:" + item.number);
                if (this.currentIndex != index) {
                  this.currentIndex = index
                } else {
                  router.pushUrl({
                    url: "pages/PokemonDescriptionScreen",
                    params: {
                      id: item.number
                    }
                  }, router.RouterMode.Standard)
                }
              })
            }
          })
        }
        .width("100%")
        .height("100%")
        .layoutWeight(1)
        .padding({ top: 74 })
        .onScrollIndex((start: number, end: number, center: number) => {
          console.info('[onScrollIndex]:start' + start + ",end:" + end + ",center:" + center);
          if (end >= this.pokemonIndexList.length - this.preloadCount) {
            if (!this.loadingState) {
              console.info('[onScrollIndex]:开始预加载');
              this.loadingState = true
              this.getPokemonList()
            }
          }
        })
      }.width("100%").height("100%")
    }.width("100%").height("100%")
  }
}
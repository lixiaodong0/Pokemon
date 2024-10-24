// 定义一个eventId为1的事件
import { emitter } from '@kit.BasicServicesKit';

export default class UpdateAvatarEvent {
  static event: emitter.InnerEvent = {
    eventId: 1
  };
}







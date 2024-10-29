
export default class EasyEventBus {
  private constructor() {
  }

  private static instance: EasyEventBus

  static getInstance(): EasyEventBus {
    if (EasyEventBus.instance == null) {
      EasyEventBus.instance = new EasyEventBus()
    }
    return EasyEventBus.instance
  }

  private observers: Map<string, Function[]> = new Map()

  on(event: string, fun: Function) {
    console.info("[EasyEventBus]on:" + event + ",fun" + fun)
    let values = this.observers.get(event)
    if (values == null) {
      values = []
    }
    values.push(fun)
    this.observers.set(event, values)
  }

  off(event: string, fun: Function) {
    console.info("[EasyEventBus]off:" + event + ",fun" + fun)
    let values = this.observers.get(event)
    if (values != null && values.length > 0) {
      let index = values.indexOf(fun)
      if (index != -1) {
        let newValues = values.slice(index, 1)
        this.observers.set(event, newValues)
      }
    }
  }

  send(event: string, data?: object | string) {
    console.info("[EasyEventBus]send:" + event + ",data" + data)
    let values = this.observers.get(event)
    if (values != null && values.length > 0) {
      values.forEach((callback) => {
        callback(data)
      })
    }
  }
}



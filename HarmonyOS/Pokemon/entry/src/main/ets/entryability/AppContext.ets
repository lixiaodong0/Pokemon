export default class AppContext {
  private static instance: AppContext
  private context: Context | undefined

  static getInstance() {
    if (!AppContext.instance) {
      AppContext.instance = new AppContext()
    }
    return AppContext.instance
  }

  setContext(context: Context) {
    this.context = context
  }

  getContext(): Context {
    return this.context ?? getContext(this)
  }
}




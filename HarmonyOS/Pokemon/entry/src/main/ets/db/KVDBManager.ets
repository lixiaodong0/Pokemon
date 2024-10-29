import { distributedKVStore } from '@kit.ArkData';
import { BusinessError } from '@kit.BasicServicesKit';

export default class KVDBManager {
  private constructor() {
  }

  private static instance: KVDBManager

  static getInstance() {
    if (!KVDBManager.instance) {
      KVDBManager.instance = new KVDBManager()
    }
    return KVDBManager.instance
  }

  private kvManager: distributedKVStore.KVManager | undefined = undefined;
  private kvStore: distributedKVStore.SingleKVStore | undefined = undefined;

  init(context: Context) {

    const kvManagerConfig: distributedKVStore.KVManagerConfig = {
      context: context,
      bundleName: 'com.lixd.pokemon.datamanager'
    };

    try {
      // 创建KVManager实例
      this.kvManager = distributedKVStore.createKVManager(kvManagerConfig);
      console.info('Succeeded in creating KVManager.');
      // 继续创建获取数据库
    } catch (e) {
      let error = e as BusinessError;
      console.error(`Failed to create KVManager. Code:${error.code},message:${error.message}`);
    }

    if (this.kvManager !== undefined) {
      this.kvManager = this.kvManager as distributedKVStore.KVManager;

      try {
        const options: distributedKVStore.Options = {
          createIfMissing: true,
          encrypt: false,
          backup: false,
          autoSync: false,
          // kvStoreType不填时，默认创建多设备协同数据库
          kvStoreType: distributedKVStore.KVStoreType.SINGLE_VERSION,
          // 多设备协同数据库：kvStoreType: distributedKVStore.KVStoreType.DEVICE_COLLABORATION,
          securityLevel: distributedKVStore.SecurityLevel.S1
        };
        this.kvManager.getKVStore<distributedKVStore.SingleKVStore>('storeId', options,
          (err, store: distributedKVStore.SingleKVStore) => {
            if (err) {
              console.error(`Failed to get KVStore: Code:${err.code},message:${err.message}`);
              return;
            }
            console.info('Succeeded in getting KVStore.');
            this.kvStore = store;
            // 请确保获取到键值数据库实例后，再进行相关数据操作
          });
      } catch (e) {
        let error = e as BusinessError;
        console.error(`An unexpected error occurred. Code:${error.code},message:${error.message}`);
      }
    }
  }

  isInit(): boolean {
    if (this.kvStore == undefined) {
      return false
    }
    return true
  }

  async put(key: string, value: string): Promise<boolean> {
    let success: boolean = await new Promise((resolve: Function, reject: Function) => {
      if (this.kvStore == undefined) {
        console.error(`请先初始化kvStore`);
        resolve(false)
      } else {
        this.kvStore.put(key, value, (err) => {
          if (err !== undefined) {
            console.error(`Failed to put data. Code:${err.code},message:${err.message}`);
            resolve(false)
          } else {
            console.info('Succeeded in putting data.');
            resolve(true)
          }
        });
      }
    })
    return success
  }

  async get(key: string): Promise<string> {
    let value: string = await new Promise((resolve: Function, reject: Function) => {
      if (this.kvStore == undefined) {
        console.error(`请先初始化kvStore`);
        resolve("")
      } else {
        this.kvStore.get(key, (err, data) => {
          if (err != undefined) {
            resolve("")
            console.error(`Failed to get data. Code:${err.code},message:${err.message}`);
          } else {
            resolve(data)
            console.info(`Succeeded in getting data. Data:${data}`);
          }
        });
      }
    })
    return value
  }
}
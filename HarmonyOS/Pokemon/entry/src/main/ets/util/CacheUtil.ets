import { common } from '@kit.AbilityKit'

import { fileIo as fs, ReadOptions } from '@kit.CoreFileKit';
import CryptoJS from '@ohos/crypto-js';
import AppContext from '../entryability/AppContext';
import { buffer } from '@kit.ArkTS';

export default class CacheUtil {
  static checkCache(key: string): boolean {
    let cryptoKey = CryptoJS.MD5(key).toString()
    let filesDir = AppContext.getInstance().getContext()?.filesDir
    let filePath = filesDir + "/" + cryptoKey
    return fs.accessSync(filePath, fs.AccessModeType.EXIST)
  }

  static writeCache(key: string, content: string) {
    let cryptoKey = CryptoJS.MD5(key).toString()
    let filesDir = AppContext.getInstance().getContext()?.filesDir
    let filePath = filesDir + "/" + CryptoJS.MD5(key).toString()
    console.info("[writeCache]key:" + key)
    console.info("[writeCache]cryptoKey:" + cryptoKey)
    // 新建并打开文件
    let file = fs.openSync(filePath, fs.OpenMode.READ_WRITE | fs.OpenMode.CREATE)
    // 写入一段内容至文件
    let writeLen = fs.writeSync(file.fd, content, {
      encoding: "utf-8"
    })
    fs.closeSync(file)
    console.info("[writeCache]writeLen:" + writeLen)
  }

  static readCache(key: string): string {
    let cryptoKey = CryptoJS.MD5(key).toString()
    let filesDir = AppContext.getInstance().getContext()?.filesDir
    let cacheContent: string = ""
    let filePath = filesDir + "/" + cryptoKey

    let file = fs.openSync(filePath, fs.OpenMode.READ_WRITE | fs.OpenMode.CREATE)
    // 读取源文件内容并写入至目的文件
    let bufSize = 4096;
    let readSize = 0;
    let buf = new ArrayBuffer(bufSize);
    let readOptions: ReadOptions = {
      offset: readSize,
      length: bufSize
    };
    let readLen = fs.readSync(file.fd, buf, readOptions);
    while (readLen > 0) {
      readSize += readLen;
      readOptions.offset = readSize
      cacheContent += buffer.from(buf, 0, readLen).toString()
      readLen = fs.readSync(file.fd, buf, readOptions);
    }
    console.info("[readCache]key:" + key)
    console.info("[readCache]cryptoKey:" + cryptoKey)
    console.info("[readCache]cacheContent:" + cacheContent)
    return cacheContent
  }
}
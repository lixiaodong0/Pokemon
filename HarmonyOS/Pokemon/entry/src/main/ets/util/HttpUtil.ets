import { http } from '@kit.NetworkKit'
import { BusinessError } from '@kit.BasicServicesKit'
import { TreeMap } from '@kit.ArkTS';
import CacheUtil from './CacheUtil';

export default class HttpUtil {
  static async get(url: string, isCache: boolean = true): Promise<string> {
    const result: string = await new Promise((resolve: Function, reject: Function) => {
      let cacheContent = ""
      if (CacheUtil.checkCache(url)) {
        console.info("[checkCache]key:" + url + "发现缓存")
        cacheContent = CacheUtil.readCache(url)
      }
      if (cacheContent != null && cacheContent.length > 0) {
        console.info('Result:' + cacheContent);
        resolve(cacheContent)
      } else {
        let httpRequest = http.createHttp()
        httpRequest.request(
          url,
          {
            method: http.RequestMethod.GET,
            connectTimeout: 60000,
            readTimeout: 60000,
          },
          (err: BusinessError, data: http.HttpResponse) => {
            if (!err) {

              let result = data.result

              // data.result为HTTP响应内容，可根据业务需要进行解析
              console.info('Result:' + result);
              console.info('code:' + data.responseCode);

              if (isCache && result != null && result.toString().length > 0) {
                console.info("[checkCache]key:" + url + "开始缓存内容")
                CacheUtil.writeCache(url, result.toString())
              }
              resolve(result)
              httpRequest.destroy()
            } else {
              console.error('error:' + JSON.stringify(err));
              reject(new Error(JSON.stringify(err)))
              httpRequest.destroy()
            }
          }
        )
      }
    });
    return result
  }
}
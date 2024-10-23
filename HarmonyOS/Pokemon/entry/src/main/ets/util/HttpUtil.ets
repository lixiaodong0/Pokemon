import { http } from '@kit.NetworkKit'
import { BusinessError } from '@kit.BasicServicesKit'

export default class HttpUtil {
  static async get(url: string): Promise<string> {
    let httpRequest = http.createHttp()
    const result: string = await new Promise((resolve: Function, reject: Function) => {
      httpRequest.request(
        url,
        {
          method: http.RequestMethod.GET,
          connectTimeout: 60000,
          readTimeout: 60000,
        },
        (err: BusinessError, data: http.HttpResponse) => {
          if (!err) {
            // data.result为HTTP响应内容，可根据业务需要进行解析
            console.info('Result:' + data.result);
            console.info('code:' + data.responseCode);
            resolve(data.result)
          } else {
            console.error('error:' + JSON.stringify(err));
            reject(new Error(JSON.stringify(err)))
          }
        }
      )
    });
    httpRequest.destroy()
    return result
  }
}
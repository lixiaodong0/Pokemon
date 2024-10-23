import ResourceBean from './ResourceBean';

export default class ResourceListBean {
  //资源总数量
  count?: number;
  //下一个分页资源
  next?: string;
  //上一个分页资源
  previous?: string;
  //资源数据
  results?: ResourceBean[];
}


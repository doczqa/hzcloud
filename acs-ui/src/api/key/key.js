import request from '@/utils/request'

/*
  密钥生成相关api
 */

// 获取密钥
export function getKeys(id) {
  return request({
    url: '/keyapi/getKey/User-' + id,
    method: 'get'
  })
}

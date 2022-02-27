import request from '@/utils/request'

// 查询基本设置列表
export function listSetting(query) {
  return request({
    url: '/appgroup/setting/list',
    method: 'get',
    params: query
  })
}

// 查询基本设置详细
export function getSetting(id) {
  return request({
    url: '/appgroup/setting/' + id,
    method: 'get'
  })
}

// 新增基本设置
export function addSetting(data) {
  return request({
    url: '/appgroup/setting',
    method: 'post',
    data: data
  })
}

// 修改基本设置
export function updateSetting(data) {
  return request({
    url: '/appgroup/setting',
    method: 'put',
    data: data
  })
}

// 删除基本设置
export function delSetting(id) {
  return request({
    url: '/appgroup/setting/' + id,
    method: 'delete'
  })
}

// 导出基本设置
export function exportSetting(query) {
  return request({
    url: '/appgroup/setting/export',
    method: 'get',
    params: query
  })
}
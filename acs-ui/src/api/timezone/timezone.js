import request from '@/utils/request'

// 查询时区设置列表
export function listTimezone(query) {
  return request({
    url: '/timezone/timezone/list',
    method: 'get',
    params: query
  })
}

// 查询时区设置详细
export function getTimezone(id) {
  return request({
    url: '/timezone/timezone/' + id,
    method: 'get'
  })
}

// 新增时区设置
export function addTimezone(data) {
  return request({
    url: '/timezone/timezone',
    method: 'post',
    data: data
  })
}

// 修改时区设置
export function updateTimezone(data) {
  return request({
    url: '/timezone/timezone',
    method: 'put',
    data: data
  })
}

// 删除时区设置
export function delTimezone(id) {
  return request({
    url: '/timezone/timezone/' + id,
    method: 'delete'
  })
}

// 导出时区设置
export function exportTimezone(query) {
  return request({
    url: '/timezone/timezone/export',
    method: 'get',
    params: query
  })
}
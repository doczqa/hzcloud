import request from '@/utils/request'

// 查询时段设置列表
export function listInterval(query) {
  return request({
    url: '/timezone/interval/list',
    method: 'get',
    params: query
  })
}

// 查询时段设置详细
export function getInterval(id) {
  return request({
    url: '/timezone/interval/' + id,
    method: 'get'
  })
}

// 新增时段设置
export function addInterval(data) {
  return request({
    url: '/timezone/interval',
    method: 'post',
    data: data
  })
}

// 修改时段设置
export function updateInterval(data) {
  return request({
    url: '/timezone/interval',
    method: 'put',
    data: data
  })
}

// 删除时段设置
export function delInterval(id) {
  return request({
    url: '/timezone/interval/' + id,
    method: 'delete'
  })
}

// 导出时段设置
export function exportInterval(query) {
  return request({
    url: '/timezone/interval/export',
    method: 'get',
    params: query
  })
}
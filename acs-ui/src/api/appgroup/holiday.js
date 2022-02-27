import request from '@/utils/request'

// 查询假日设置列表
export function listHoliday(query) {
  return request({
    url: '/appgroup/holiday/list',
    method: 'get',
    params: query
  })
}

// 查询假日设置详细
export function getHoliday(month) {
  return request({
    url: '/appgroup/holiday/' + month,
    method: 'get'
  })
}

// 新增假日设置
export function addHoliday(data) {
  return request({
    url: '/appgroup/holiday',
    method: 'post',
    data: data
  })
}

// 修改假日设置
export function updateHoliday(data) {
  return request({
    url: '/appgroup/holiday',
    method: 'put',
    data: data
  })
}

// 删除假日设置
export function delHoliday(month) {
  return request({
    url: '/appgroup/holiday/' + month,
    method: 'delete'
  })
}

// 导出假日设置
export function exportHoliday(query) {
  return request({
    url: '/appgroup/holiday/export',
    method: 'get',
    params: query
  })
}
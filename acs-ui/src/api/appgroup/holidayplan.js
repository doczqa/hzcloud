import request from '@/utils/request'

// 查询假日计划列表
export function listHoliday(query) {
  return request({
    url: '/appgroup/holidayplan/list',
    method: 'get',
    params: query
  })
}

// 查询假日计划详细
export function getHoliday(id) {
  return request({
    url: '/appgroup/holidayplan/' + id,
    method: 'get'
  })
}

// 新增假日计划
export function addHoliday(data) {
  return request({
    url: '/appgroup/holidayplan',
    method: 'post',
    data: data
  })
}

// 修改假日计划
export function updateHoliday(data) {
  return request({
    url: '/appgroup/holidayplan',
    method: 'put',
    data: data
  })
}

// 删除假日计划
export function delHoliday(id) {
  return request({
    url: '/appgroup/holidayplan/' + id,
    method: 'delete'
  })
}

// 导出假日计划
export function exportHoliday(query) {
  return request({
    url: '/appgroup/holidayplan/export',
    method: 'get',
    params: query
  })
}
import request from '@/utils/request'

// 查询周计划模板列表
export function listWeek(query) {
  return request({
    url: '/appgroup/week/list',
    method: 'get',
    params: query
  })
}

// 查询周计划模板详细
export function getWeek(id) {
  return request({
    url: '/appgroup/week/' + id,
    method: 'get'
  })
}

// 新增周计划模板
export function addWeek(data) {
  return request({
    url: '/appgroup/week',
    method: 'post',
    data: data
  })
}

// 修改周计划模板
export function updateWeek(data) {
  return request({
    url: '/appgroup/week',
    method: 'put',
    data: data
  })
}

// 删除周计划模板
export function delWeek(id) {
  return request({
    url: '/appgroup/week/' + id,
    method: 'delete'
  })
}

// 导出周计划模板
export function exportWeek(query) {
  return request({
    url: '/appgroup/week/export',
    method: 'get',
    params: query
  })
}
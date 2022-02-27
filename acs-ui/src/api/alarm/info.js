import request from '@/utils/request'

// 查询报警信息列表
export function listInfo(query) {
  return request({
    url: '/alarm/info/list',
    method: 'get',
    params: query
  })
}

// 查询报警信息详细
export function getInfo(alarmId) {
  return request({
    url: '/alarm/info/' + alarmId,
    method: 'get'
  })
}

// 新增报警信息
export function addInfo(data) {
  return request({
    url: '/alarm/info',
    method: 'post',
    data: data
  })
}

// 修改报警信息
export function updateInfo(data) {
  return request({
    url: '/alarm/info',
    method: 'put',
    data: data
  })
}

// 删除报警信息
export function delInfo(alarmId) {
  return request({
    url: '/alarm/info/' + alarmId,
    method: 'delete'
  })
}

// 导出报警信息
export function exportInfo(query) {
  return request({
    url: '/alarm/info/export',
    method: 'get',
    params: query
  })
}

export function listByConAndDoor(query){
  return request({
    url:'/alarm/info/listbyconanddoor',
    method: 'get',
    params: query
  })
}
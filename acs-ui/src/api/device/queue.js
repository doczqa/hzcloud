import request from '@/utils/request'

// 查询命令列表列表
export function listQueue(query) {
  return request({
    url: '/device/queue/list',
    method: 'get',
    params: query
  })
}

// 查询命令列表列表
export function listQueuedf(query) {
  return request({
    url: '/device/queue/listdf',
    method: 'get',
    params: query
  })
}

// 查询命令列表详细
export function getQueue(taskId) {
  return request({
    url: '/device/queue/' + taskId,
    method: 'get'
  })
}

// 新增命令列表
export function addQueue(data) {
  return request({
    url: '/device/queue',
    method: 'post',
    data: data
  })
}

// 修改命令列表
export function updateQueue(data) {
  return request({
    url: '/device/queue',
    method: 'put',
    data: data
  })
}

// 删除命令列表
export function delQueue(taskId) {
  return request({
    url: '/device/queue/' + taskId,
    method: 'delete'
  })
}

// 导出命令列表
export function exportQueue(query) {
  return request({
    url: '/device/queue/export',
    method: 'get',
    params: query
  })
}

// 重新执行失败命令
export function replay(){
  return request(    {
    url:'/device/queue/replay',
    mrthod: 'get'
  })
}
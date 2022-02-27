import request from '@/utils/request'

// 查询区域列表
export function listZone(query) {
  return request({
    url: '/device/zone/list',
    method: 'get',
    params: query
  })
}

// 查询区域列表不分页
export function listZoneNoPage(query) {
  return request({
    url: '/device/zone/listNoPage',
    method: 'get',
    params: query
  })
}

// 查询区域详细
export function getZone(zoneId) {
  return request({
    url: '/device/zone/' + zoneId,
    method: 'get'
  })
}

// 新增区域
export function addZone(data) {
  return request({
    url: '/device/zone',
    method: 'post',
    data: data
  })
}

// 修改区域
export function updateZone(data) {
  return request({
    url: '/device/zone',
    method: 'put',
    data: data
  })
}

// 删除区域
export function delZone(zoneId) {
  return request({
    url: '/device/zone/' + zoneId,
    method: 'delete'
  })
}

// 导出区域
export function exportZone(query) {
  return request({
    url: '/device/zone/export',
    method: 'get',
    params: query
  })
}

// 区域状态修改
export function changeZoneStatus(zoneId, status) {
  const data = {
    zoneId,
    status
  }
  return request({
    url: '/device/zone/changeStatus',
    method: 'put',
    data: data
  })
}

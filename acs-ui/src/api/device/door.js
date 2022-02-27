import request from '@/utils/request'

// 查询门禁门列表
export function listDoor(query) {
  return request({
    url: '/device/door/list',
    method: 'get',
    params: query
  })
}

// 查询门禁门列表
export function listDoorNoPage(query) {
  return request({
    url: '/device/door/listNoPage',
    method: 'get',
    params: query
  })
}

// 查询门禁门列表过滤
export function listDoordf(query) {
  return request({
    url: '/device/door/listdf',
    method: 'get',
    params: query
  })
}

// 查询门禁门列表过滤
export function listDoordfnp() {
  return request({
    url: '/device/door/listdfNoPage',
    method: 'get'
  })
}

// 查询门禁门详细
export function getDoor(doorId) {
  return request({
    url: '/device/door/' + doorId,
    method: 'get'
  })
}

// 新增门禁门
export function addDoor(data) {
  return request({
    url: '/device/door',
    method: 'post',
    data: data
  })
}

// 修改门禁门
export function updateDoor(data) {
  return request({
    url: '/device/door',
    method: 'put',
    data: data
  })
}

// 删除门禁门
export function delDoor(doorId) {
  return request({
    url: '/device/door/' + doorId,
    method: 'delete'
  })
}

// 导出门禁门
export function exportDoor(query) {
  return request({
    url: '/device/door/export',
    method: 'get',
    params: query
  })
}

// 获取门禁组中的门
export function getDoorsByGroupId(doorQueryParams) {
  return request({
    url: `/device/door/list/${doorQueryParams.doorGroupId}`,
    method: 'get',
    params: doorQueryParams,
  })
}

export function controlDoor(controlDoorParams) {
  return request({
    url: `/device/door/cmd`,
    method: 'get',
    params: controlDoorParams,
  })
}

// 查询不在门禁组中的门列表
export function listNotInDoorGroup(query) {
  return request({
    url: '/device/door/listNotInDoorGroup',
    method: 'get',
    params: query
  })
}

// 查询不在门禁组中的控制器与门的树
export function getControllerAndDoorTree(query) {
  return request({
    url: '/device/door/controllerAndDoorTree',
    method: 'get',
    params: query
  })
}

export function listDoorByControllerIds(controllerIds){
  return request({
    url: '/device/door/listbyconids',
    method: 'post',
    data: controllerIds
  })
}
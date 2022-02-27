import request from '@/utils/request'

// 查询门禁组列表
export function listDoor_group(query) {
  return request({
    url: '/group/door_group/list',
    method: 'get',
    params: query
  })
}

// 查询门禁组详细
export function getDoor_group(groupId) {
  return request({
    url: '/group/door_group/' + groupId,
    method: 'get'
  })
}

// 新增门禁组
export function addDoor_group(data) {
  return request({
    url: '/group/door_group',
    method: 'post',
    data: data
  })
}

// 修改门禁组
export function updateDoor_group(data) {
  return request({
    url: '/group/door_group',
    method: 'put',
    data: data
  })
}

// 删除门禁组
export function delDoor_group(groupId) {
  return request({
    url: '/group/door_group/' + groupId,
    method: 'delete'
  })
}

// 导出门禁组
export function exportDoor_group(query) {
  return request({
    url: '/group/door_group/export',
    method: 'get',
    params: query
  })
}

// 门禁组添加门
export function　add_door(data) {
  return request({
    url: '/group/door_group/add_door',
    method: 'post',
    data: data
  })
}

// 获取门禁组中的门id数组
export function getDoorIdsByGroupId(groupId) {
  return request({
    url: `/group/door_group/get_doorIds/${groupId}`,
    method: 'get',
  })
}

/**
 * 移除门
 * @param data
 * @returns {AxiosPromise}
 */
export function remove_door(data) {
  return request({
    url: '/group/door_group/remove_door',
    method: 'delete',
    data: data
  })
}

// 门禁组添加用户组
export function　add_userGroup(data) {
  return request({
    url: '/group/door_group/add_userGroup',
    method: 'post',
    data: data
  })
}

// 门禁组移除用户组
export function　remove_userGroup(data) {
  return request({
    url: '/group/door_group/remove_userGroup',
    method: 'post',
    data: data
  })
}

// 获取门禁组中的用户组ID数组
export function get_userGroupIdsByGroupId(groupId) {
  return request({
    url: `/group/door_group/get_userGroupIds/${groupId}`,
    method: 'get',
  })
}

export function distribution(groupId) {
  return request({
    url: `/group/door_group/distribution/${groupId}`,
    method: 'get',
  })
}

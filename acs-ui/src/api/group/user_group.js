import request from '@/utils/request'

// 查询用户组列表
export function listUser_group(query) {
  return request({
    url: '/group/user_group/list',
    method: 'get',
    params: query
  })
}

// 查询用户组详细
export function getUser_group(groupId) {
  return request({
    url: '/group/user_group/' + groupId,
    method: 'get'
  })
}

// 新增用户组
export function addUser_group(data) {
  return request({
    url: '/group/user_group',
    method: 'post',
    data: data
  })
}

// 修改用户组
export function updateUser_group(data) {
  return request({
    url: '/group/user_group',
    method: 'put',
    data: data
  })
}

// 删除用户组
export function delUser_group(groupId) {
  return request({
    url: '/group/user_group/' + groupId,
    method: 'delete'
  })
}

// 导出用户组
export function exportUser_group(query) {
  return request({
    url: '/group/user_group/export',
    method: 'get',
    params: query
  })
}

// 查询用户组中的用户ID数组
export function getUserIds(groupId) {
  return request({
    url: `/group/user_group/userIds/${groupId}`,
    method: 'get'
  })
}

// 修改用户组状态
export function updateUser_group_status(data) {
  return request({
    url: '/group/user_group/editStatus',
    method: 'put',
    data: data
  })
}

// 修改用户组名称
export function updateUser_group_groupName(data) {
  return request({
    url: '/group/user_group/editGroupName',
    method: 'put',
    data: data
  })
}

// 修改用户组中用户
export function updateUser_group_updateUser(data) {
  return request({
    url: '/group/user_group/updateUser',
    method: 'put',
    data: data
  })
}

// 查询不在门禁组中的用户组
export function listUser_group_listNotInDoorGroup(query) {
  return request({
    url: '/group/user_group/listNotInDoorGroup',
    method: 'get',
    params: query
  })
}

// 查询不在门禁组中的用户组
export function listUser_group_listInDoorGroup(query) {
  return request({
    url: '/group/user_group/listInDoorGroup',
    method: 'get',
    params: query
  })
}

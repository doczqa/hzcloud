import request from '@/utils/request'

// 查询假日组列表
export function listGroup(query) {
  return request({
    url: '/holiday/group/list',
    method: 'get',
    params: query
  })
}

// 查询假日组详细
export function getGroup(groupId) {
  return request({
    url: '/holiday/group/' + groupId,
    method: 'get'
  })
}

// 新增假日组
export function addGroup(data) {
  return request({
    url: '/holiday/group',
    method: 'post',
    data: data
  })
}

// 修改假日组
export function updateGroup(data) {
  return request({
    url: '/holiday/group',
    method: 'put',
    data: data
  })
}

// 删除假日组
export function delGroup(groupId) {
  return request({
    url: '/holiday/group/' + groupId,
    method: 'delete'
  })
}

// 导出假日组
export function exportGroup(query) {
  return request({
    url: '/holiday/group/export',
    method: 'get',
    params: query
  })
}
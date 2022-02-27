import request from '@/utils/request'

// 查询卡片管理列表
export function listCard(query) {
  return request({
    url: '/card/list',
    method: 'get',
    params: query
  })
}

// 查询卡片管理详细
export function getCard(cardId) {
  return request({
    url: '/card/' + cardId,
    method: 'get'
  })
}

// 新增卡片管理
export function addCard(data) {
  return request({
    url: '/card',
    method: 'post',
    data: data
  })
}

// 导出卡片管理
export function exportCard(query) {
  return request({
    url: '/card/export',
    method: 'get',
    params: query
  })
}

// 注销卡片管理
export function cancelCard(id) {
  return request({
    url: '/card/cancel/' + id,
    method: 'get'
  })
}

// 冻结卡片管理
export function frozenCard(id) {
  return request({
    url: '/card/frozen/' + id,
    method: 'get'
  })
}

// 解冻卡片管理
export function unfreezeCard(id) {
  return request({
    url: '/card/unfreeze/' + id,
    method: 'get'
  })
}

// 下载卡片导入模板
export function importTemplate() {
  return request({
    url: '/card/importTemplate',
    method: 'get'
  })
}

export function importDateByUser(data) {
  return request({
    url: '/card/importByUser',
    method: 'put',
    data: data
  })
}

export function updateCardexpirationEndTime(cardId, expirationEndTime) {
  const data = {
    cardId,
    expirationEndTime
  }
  return request({
    url: '/card/updatetime',
    method: 'post',
    data: data
  })
}
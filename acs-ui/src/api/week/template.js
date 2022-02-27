import request from '@/utils/request'

// 查询周计划模板列表
export function listTemplate(query) {
  return request({
    url: '/week/template/list',
    method: 'get',
    params: query
  })
}

// 查询周计划模板详细
export function getTemplate(templateId) {
  return request({
    url: '/week/template/' + templateId,
    method: 'get'
  })
}

// 新增周计划模板
export function addTemplate(data) {
  return request({
    url: '/week/template',
    method: 'post',
    data: data
  })
}

// 修改周计划模板
export function updateTemplate(data) {
  return request({
    url: '/week/template',
    method: 'put',
    data: data
  })
}

// 删除周计划模板
export function delTemplate(templateId) {
  return request({
    url: '/week/template/' + templateId,
    method: 'delete'
  })
}

// 导出周计划模板
export function exportTemplate(query) {
  return request({
    url: '/week/template/export',
    method: 'get',
    params: query
  })
}
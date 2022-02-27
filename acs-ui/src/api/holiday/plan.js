import request from '@/utils/request'

// 查询假日计划列表
export function listPlan(query) {
  return request({
    url: '/holiday/plan/list',
    method: 'get',
    params: query
  })
}

// 查询假日计划详细
export function getPlan(planId) {
  return request({
    url: '/holiday/plan/' + planId,
    method: 'get'
  })
}

// 新增假日计划
export function addPlan(data) {
  return request({
    url: '/holiday/plan',
    method: 'post',
    data: data
  })
}

// 修改假日计划
export function updatePlan(data) {
  return request({
    url: '/holiday/plan',
    method: 'put',
    data: data
  })
}

// 删除假日计划
export function delPlan(planId) {
  return request({
    url: '/holiday/plan/' + planId,
    method: 'delete'
  })
}

// 导出假日计划
export function exportPlan(query) {
  return request({
    url: '/holiday/plan/export',
    method: 'get',
    params: query
  })
}

//设置假日时段
export function updateSlice(data){
  return request({
    url: '/holiday/slice',
    method: 'post',
    data: data
  }
  )
}

//获取假日时段
export function getSlice(){
  return request({
    url: '/holiday/slice',
    method: 'get'
  })
}
import request from '@/utils/request'

// 查询门禁控制器列表
export function listCon(query) {
  return request({
    url: '/device/con/list',
    method: 'get',
    params: query
  })
}

// 查询门禁控制器列表不分页
export function listConNoPage(query) {
  return request({
    url: '/device/con/listNoPage',
    method: 'get',
    params: query
  })
}

// 根据区域ID查询门禁控制器列表
export function listConByZoneId(zoneId) {
  return request({
    url: '/device/con/listByZoneId/' + zoneId,
    method: 'get',
  })
}

// 查询门禁控制器列表
export function listCondf(query) {
  return request({
    url: '/device/con/listdf',
    method: 'get',
    params: query
  })
}

// 查询门禁控制器列表
export function listCondfnp() {
  return request({
    url: '/device/con/listdfNoPage',
    method: 'get'
  })
}

// 查询门禁控制器详细
export function getCon(controllerId) {
  return request({
    url: '/device/con/' + controllerId,
    method: 'get'
  })
}

// 新增门禁控制器
export function addCon(data) {
  return request({
    url: '/device/con',
    method: 'post',
    data: data
  })
}

// 修改门禁控制器
export function updateCon(data) {
  return request({
    url: '/device/con',
    method: 'put',
    data: data
  })
}

// 删除门禁控制器
export function delCon(controllerId) {
  return request({
    url: '/device/con/' + controllerId,
    method: 'delete'
  })
}

// 导出门禁控制器
export function exportCon(query) {
  return request({
    url: '/device/con/export',
    method: 'get',
    params: query
  })
}

//布撤防
export function changeAlarmStatus(controllerId, alarmStatus){
  const data = {
    controllerId,
    alarmStatus
  }
  return request({
    url: '/device/con/changealarmStatus',
    method: 'put',
    data: data
  })
}

//下发周计划模板
export function issueWeekTemplate(controllerId,ip){
  return request({
    url: `/device/con/weektemplate/${controllerId}?ip=${ip}`,
    method: 'get',
  })
}

//下发假日计划
export function issueHolidayPlan(controllerId,ip){
  return request({
    url: `/device/con/holidayplan/${controllerId}?ip=${ip}`,
    method: 'get'
  })
}

//下发应用群组
export function issueAppGroup(controllerId){
  return request({
    url: `/device/con/appgroup/${controllerId}`,
    method: 'get'
  })
}

//下发时间设置
export function issueDateAndTime(controllerId){
  return request({
    url: `/device/con/dateandtime/${controllerId}`,
    method: 'get'
  })
}

//下发所有用户
export function issueAllUser(controllerId){
  return request({
    url: `/device/con/issuedalluser/${controllerId}`,
    method: 'get'
  })
}

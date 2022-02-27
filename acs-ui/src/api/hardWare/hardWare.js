import request from '@/utils/request'

/*
  发卡功能硬件交互相关api
 */

// 打开写卡器硬件设备，返回handle
export function openHidDevice(data) {
  return request({
    url: '/hardWare/FM_API_OpenHidDevice',
    method: 'post',
    data: data
  })
}

// 关闭写卡器硬件设备
export function close(data) {
  return request({
    url: '/hardWare/FM_API_Close',
    method: 'post',
    data: data
  })
}

// 蜂鸣
export function beep(data) {
  return request({
    url: '/hardWare/FM_API_Beep',
    method: 'post',
    data: data
  })
}

// 设置卡片类型
export function setCardType(data) {
  return request({
    url: '/hardWare/FM_API_SetCardType',
    method: 'post',
    data: data
  })
}

// 非接触CPU卡
export function fieldOn(data) {
  return request({
    url: '/hardWare/FM_API_Fieldon',
    method: 'post',
    data: data
  })
}

// 发送指令
export function apdu(data) {
  return request({
    url: '/hardWare/FM_API_APDU',
    method: 'post',
    data: data
  })
}

// 显示
export function display(data) {
  return request({
    url: '/hardWare/FM_API_Display',
    method: 'post',
    data: data
  })
}

//
export function deselect(data) {
  return request({
    url: '/hardWare/FM_API_Deselect',
    method: 'post',
    data: data
  })
}

// 接触cpu卡
export function fm17InReset(data) {
  return request({
    url: '/hardWare/FM_API_FM17InReset',
    method: 'post',
    data: data
  })
}

// 接触cpu卡，复位
export function reset(data) {
  return request({
    url: '/hardWare/FM_API_Reset',
    method: 'post',
    data: data
  })
}

//
export function disActive(data) {
  return request({
    url: '/hardWare/FM_API_DisActive',
    method: 'post',
    data: data
  })
}

//3 DES 加密
export function tripleDes(data) {
  return request({
    url: '/hardWare/tripleDes',
    method: 'post',
    data: data
  })

}


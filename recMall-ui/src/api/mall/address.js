import request from '@/utils/request'

// 查询地址信息列表
export function listAddress(query) {
  return request({
    url: '/mall/address/list',
    method: 'get',
    params: query
  })
}

// 查询地址信息详细
export function getAddress(id) {
  return request({
    url: '/mall/address/' + id,
    method: 'get'
  })
}

// 新增地址信息
export function addAddress(data) {
  return request({
    url: '/mall/address',
    method: 'post',
    data: data
  })
}

// 修改地址信息
export function updateAddress(data) {
  return request({
    url: '/mall/address',
    method: 'put',
    data: data
  })
}

// 删除地址信息
export function delAddress(id) {
  return request({
    url: '/mall/address/' + id,
    method: 'delete'
  })
}

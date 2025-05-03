import request from '@/utils/request'

// 查询订单信息列表
export function listOrders(query) {
  return request({
    url: '/mall/orders/list',
    method: 'get',
    params: query
  })
}

// 查询订单信息详细
export function getOrders(id) {
  return request({
    url: '/mall/orders/' + id,
    method: 'get'
  })
}

// 新增订单信息
export function addOrders(data) {
  return request({
    url: '/mall/orders',
    method: 'post',
    data: data
  })
}

// 修改订单信息
export function updateOrders(data) {
  return request({
    url: '/mall/orders',
    method: 'put',
    data: data
  })
}

// 删除订单信息
export function delOrders(id) {
  return request({
    url: '/mall/orders/' + id,
    method: 'delete'
  })
}

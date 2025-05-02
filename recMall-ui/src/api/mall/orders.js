import request from '@/utils/request'

// 查询书籍订单列表
export function listOrders(query) {
  return request({
    url: '/mall/orders/list',
    method: 'get',
    params: query
  })
}

// 查询书籍订单详细
export function getOrders(id) {
  return request({
    url: '/mall/orders/' + id,
    method: 'get'
  })
}

// 新增书籍订单
export function addOrders(data) {
  return request({
    url: '/mall/orders',
    method: 'post',
    data: data
  })
}

// 修改书籍订单
export function updateOrders(data) {
  return request({
    url: '/mall/orders',
    method: 'put',
    data: data
  })
}

// 删除书籍订单
export function delOrders(id) {
  return request({
    url: '/mall/orders/' + id,
    method: 'delete'
  })
}

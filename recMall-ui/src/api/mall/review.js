import request from '@/utils/request'

// 查询用户书评数据列表
export function listReview(query) {
  return request({
    url: '/mall/review/list',
    method: 'get',
    params: query
  })
}

// 查询用户书评数据详细
export function getReview(reviewId) {
  return request({
    url: '/mall/review/' + reviewId,
    method: 'get'
  })
}

// 新增用户书评数据
export function addReview(data) {
  return request({
    url: '/mall/review',
    method: 'post',
    data: data
  })
}

// 修改用户书评数据
export function updateReview(data) {
  return request({
    url: '/mall/review',
    method: 'put',
    data: data
  })
}

// 删除用户书评数据
export function delReview(reviewId) {
  return request({
    url: '/mall/review/' + reviewId,
    method: 'delete'
  })
}

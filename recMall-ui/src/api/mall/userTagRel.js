import request from '@/utils/request'

// 查询用户与标签多对多关系列表
export function listUserTagRel(query) {
  return request({
    url: '/mall/userTagRel/list',
    method: 'get',
    params: query
  })
}

// 查询用户与标签多对多关系详细
export function getUserTagRel(userId) {
  return request({
    url: '/mall/userTagRel/' + userId,
    method: 'get'
  })
}

// 新增用户与标签多对多关系
export function addUserTagRel(data) {
  return request({
    url: '/mall/userTagRel',
    method: 'post',
    data: data
  })
}

// 修改用户与标签多对多关系
export function updateUserTagRel(data) {
  return request({
    url: '/mall/userTagRel',
    method: 'put',
    data: data
  })
}

// 删除用户与标签多对多关系
export function delUserTagRel(userId) {
  return request({
    url: '/mall/userTagRel/' + userId,
    method: 'delete'
  })
}

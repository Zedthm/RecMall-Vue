import request from '@/utils/request'

// 查询用户标签字典列表
export function listUserTag(query) {
  return request({
    url: '/mall/userTag/list',
    method: 'get',
    params: query
  })
}

// 查询用户标签字典详细
export function getUserTag(tagId) {
  return request({
    url: '/mall/userTag/' + tagId,
    method: 'get'
  })
}

// 新增用户标签字典
export function addUserTag(data) {
  return request({
    url: '/mall/userTag',
    method: 'post',
    data: data
  })
}

// 修改用户标签字典
export function updateUserTag(data) {
  return request({
    url: '/mall/userTag',
    method: 'put',
    data: data
  })
}

// 删除用户标签字典
export function delUserTag(tagId) {
  return request({
    url: '/mall/userTag/' + tagId,
    method: 'delete'
  })
}

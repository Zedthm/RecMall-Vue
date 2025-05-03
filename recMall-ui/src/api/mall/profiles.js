import request from '@/utils/request'

// 查询用户画像及评分数据列表
export function listProfiles(query) {
  return request({
    url: '/mall/profiles/list',
    method: 'get',
    params: query
  })
}

// 查询用户画像及评分数据详细
export function getProfiles(userId) {
  return request({
    url: '/mall/profiles/' + userId,
    method: 'get'
  })
}

// 新增用户画像及评分数据
export function addProfiles(data) {
  return request({
    url: '/mall/profiles',
    method: 'post',
    data: data
  })
}

// 修改用户画像及评分数据
export function updateProfiles(data) {
  return request({
    url: '/mall/profiles',
    method: 'put',
    data: data
  })
}

// 删除用户画像及评分数据
export function delProfiles(userId) {
  return request({
    url: '/mall/profiles/' + userId,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询存储系统标签信息列表
export function listTags(query) {
  return request({
    url: '/mall/tags/list',
    method: 'get',
    params: query
  })
}

// 查询存储系统标签信息详细
export function getTags(tagId) {
  return request({
    url: '/mall/tags/' + tagId,
    method: 'get'
  })
}

// 新增存储系统标签信息
export function addTags(data) {
  return request({
    url: '/mall/tags',
    method: 'post',
    data: data
  })
}

// 修改存储系统标签信息
export function updateTags(data) {
  return request({
    url: '/mall/tags',
    method: 'put',
    data: data
  })
}

// 删除存储系统标签信息
export function delTags(tagId) {
  return request({
    url: '/mall/tags/' + tagId,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询评论信息列表
export function listComment(query) {
  return request({
    url: '/mall/comment/list',
    method: 'get',
    params: query
  })
}

// 查询评论信息详细
export function getComment(id) {
  return request({
    url: '/mall/comment/' + id,
    method: 'get'
  })
}

// 新增评论信息
export function addComment(data) {
  return request({
    url: '/mall/comment',
    method: 'post',
    data: data
  })
}

// 修改评论信息
export function updateComment(data) {
  return request({
    url: '/mall/comment',
    method: 'put',
    data: data
  })
}

// 删除评论信息
export function delComment(id) {
  return request({
    url: '/mall/comment/' + id,
    method: 'delete'
  })
}

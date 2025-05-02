import request from '@/utils/request'

// 查询书籍标签字典列表
export function listBookTag(query) {
  return request({
    url: '/mall/bookTag/list',
    method: 'get',
    params: query
  })
}

// 查询书籍标签字典详细
export function getBookTag(tagId) {
  return request({
    url: '/mall/bookTag/' + tagId,
    method: 'get'
  })
}

// 新增书籍标签字典
export function addBookTag(data) {
  return request({
    url: '/mall/bookTag',
    method: 'post',
    data: data
  })
}

// 修改书籍标签字典
export function updateBookTag(data) {
  return request({
    url: '/mall/bookTag',
    method: 'put',
    data: data
  })
}

// 删除书籍标签字典
export function delBookTag(tagId) {
  return request({
    url: '/mall/bookTag/' + tagId,
    method: 'delete'
  })
}

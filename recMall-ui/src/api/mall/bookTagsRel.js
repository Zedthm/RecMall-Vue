import request from '@/utils/request'

// 查询书籍与标签多对多关系列表
export function listBookTagsRel(query) {
  return request({
    url: '/mall/bookTagsRel/list',
    method: 'get',
    params: query
  })
}

// 查询书籍与标签多对多关系详细
export function getBookTagsRel(bookId) {
  return request({
    url: '/mall/bookTagsRel/' + bookId,
    method: 'get'
  })
}

// 新增书籍与标签多对多关系
export function addBookTagsRel(data) {
  return request({
    url: '/mall/bookTagsRel',
    method: 'post',
    data: data
  })
}

// 修改书籍与标签多对多关系
export function updateBookTagsRel(data) {
  return request({
    url: '/mall/bookTagsRel',
    method: 'put',
    data: data
  })
}

// 删除书籍与标签多对多关系
export function delBookTagsRel(bookId) {
  return request({
    url: '/mall/bookTagsRel/' + bookId,
    method: 'delete'
  })
}

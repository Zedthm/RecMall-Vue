import request from '@/utils/request'

// 查询书籍作者信息列表
export function listAuthor(query) {
  return request({
    url: '/mall/author/list',
    method: 'get',
    params: query
  })
}

// 查询书籍作者信息详细
export function getAuthor(authorId) {
  return request({
    url: '/mall/author/' + authorId,
    method: 'get'
  })
}

// 新增书籍作者信息
export function addAuthor(data) {
  return request({
    url: '/mall/author',
    method: 'post',
    data: data
  })
}

// 修改书籍作者信息
export function updateAuthor(data) {
  return request({
    url: '/mall/author',
    method: 'put',
    data: data
  })
}

// 删除书籍作者信息
export function delAuthor(authorId) {
  return request({
    url: '/mall/author/' + authorId,
    method: 'delete'
  })
}

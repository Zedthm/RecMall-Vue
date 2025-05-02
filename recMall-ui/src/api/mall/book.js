import request from '@/utils/request'

// 查询图书核心信息列表
export function listBook(query) {
  return request({
    url: '/mall/book/list',
    method: 'get',
    params: query
  })
}

// 查询图书核心信息详细
export function getBook(bookId) {
  return request({
    url: '/mall/book/' + bookId,
    method: 'get'
  })
}

// 新增图书核心信息
export function addBook(data) {
  return request({
    url: '/mall/book',
    method: 'post',
    data: data
  })
}

// 修改图书核心信息
export function updateBook(data) {
  return request({
    url: '/mall/book',
    method: 'put',
    data: data
  })
}

// 删除图书核心信息
export function delBook(bookId) {
  return request({
    url: '/mall/book/' + bookId,
    method: 'delete'
  })
}

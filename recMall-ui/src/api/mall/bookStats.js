import request from '@/utils/request'

// 查询书籍评分统计列表
export function listBookStats(query) {
  return request({
    url: '/mall/bookStats/list',
    method: 'get',
    params: query
  })
}

// 查询书籍评分统计详细
export function getBookStats(bookId) {
  return request({
    url: '/mall/bookStats/' + bookId,
    method: 'get'
  })
}

// 新增书籍评分统计
export function addBookStats(data) {
  return request({
    url: '/mall/bookStats',
    method: 'post',
    data: data
  })
}

// 修改书籍评分统计
export function updateBookStats(data) {
  return request({
    url: '/mall/bookStats',
    method: 'put',
    data: data
  })
}

// 删除书籍评分统计
export function delBookStats(bookId) {
  return request({
    url: '/mall/bookStats/' + bookId,
    method: 'delete'
  })
}

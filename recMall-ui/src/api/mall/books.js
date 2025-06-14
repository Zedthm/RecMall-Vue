import request from '@/utils/request'
// 查询推荐商品
export function listRecBooksDeepFM(userId) {
  return request({
    url: '/mall/recBooks/deep-fm'+userId,
    method: 'get',
  })
}

export function listRecBooksNeuralCF(userId) {
  return request({
    url: '/mall/recBooks/neural-cf'+userId,
    method: 'get',
  })
}

export function listRecBooksNeuralCFSingleUser() {
  return request({
    url: '/mall/recBooks/single',
    method: 'get',
  })
}
// 查询商品信息-书籍核心数据列表
export function listBooks(query) {
  return request({
    url: '/mall/books/list',
    method: 'get',
    params: query
  })
}

// 查询商品信息-书籍核心数据详细
export function getBooks(bookId) {
  return request({
    url: '/mall/books/' + bookId,
    method: 'get'
  })
}

// 新增商品信息-书籍核心数据
export function addBooks(data) {
  return request({
    url: '/mall/books',
    method: 'post',
    data: data
  })
}

// 修改商品信息-书籍核心数据
export function updateBooks(data) {
  return request({
    url: '/mall/books',
    method: 'put',
    data: data
  })
}

// 删除商品信息-书籍核心数据
export function delBooks(bookId) {
  return request({
    url: '/mall/books/' + bookId,
    method: 'delete'
  })
}

// 获取图书标签列表
export function listBookTags(bookId) {
  return request({
    url: '/mall/books/tags/'+bookId,
    method: 'get',
  })
}

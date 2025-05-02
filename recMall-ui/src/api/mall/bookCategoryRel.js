import request from '@/utils/request'

// 查询书籍类别关系列表
export function listBookCategoryRel(query) {
  return request({
    url: '/mall/bookCategoryRel/list',
    method: 'get',
    params: query
  })
}

// 查询书籍类别关系详细
export function getBookCategoryRel(categoryId) {
  return request({
    url: '/mall/bookCategoryRel/' + categoryId,
    method: 'get'
  })
}

// 新增书籍类别关系
export function addBookCategoryRel(data) {
  return request({
    url: '/mall/bookCategoryRel',
    method: 'post',
    data: data
  })
}

// 修改书籍类别关系
export function updateBookCategoryRel(data) {
  return request({
    url: '/mall/bookCategoryRel',
    method: 'put',
    data: data
  })
}

// 删除书籍类别关系
export function delBookCategoryRel(categoryId) {
  return request({
    url: '/mall/bookCategoryRel/' + categoryId,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询书籍分类目录列表
export function listCategories(query) {
  return request({
    url: '/system/categories/list',
    method: 'get',
    params: query
  })
}

// 查询书籍分类目录详细
export function getCategories(categoryId) {
  return request({
    url: '/system/categories/' + categoryId,
    method: 'get'
  })
}

// 新增书籍分类目录
export function addCategories(data) {
  return request({
    url: '/system/categories',
    method: 'post',
    data: data
  })
}

// 修改书籍分类目录
export function updateCategories(data) {
  return request({
    url: '/system/categories',
    method: 'put',
    data: data
  })
}

// 删除书籍分类目录
export function delCategories(categoryId) {
  return request({
    url: '/system/categories/' + categoryId,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询书籍类别列表
export function listCategory(query) {
  return request({
    url: '/mall/category/list',
    method: 'get',
    params: query
  })
}

// 查询书籍类别详细
export function getCategory(categoryId) {
  return request({
    url: '/mall/category/' + categoryId,
    method: 'get'
  })
}

// 新增书籍类别
export function addCategory(data) {
  return request({
    url: '/mall/category',
    method: 'post',
    data: data
  })
}

// 修改书籍类别
export function updateCategory(data) {
  return request({
    url: '/mall/category',
    method: 'put',
    data: data
  })
}

// 删除书籍类别
export function delCategory(categoryId) {
  return request({
    url: '/mall/category/' + categoryId,
    method: 'delete'
  })
}

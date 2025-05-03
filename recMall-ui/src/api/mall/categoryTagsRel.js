import request from '@/utils/request'

// 查询分类与标签的关联关系列表
export function listCategoryTagsRel(query) {
  return request({
    url: '/mall/categoryTagsRel/list',
    method: 'get',
    params: query
  })
}

// 查询分类与标签的关联关系详细
export function getCategoryTagsRel(categoryId) {
  return request({
    url: '/mall/categoryTagsRel/' + categoryId,
    method: 'get'
  })
}

// 新增分类与标签的关联关系
export function addCategoryTagsRel(data) {
  return request({
    url: '/mall/categoryTagsRel',
    method: 'post',
    data: data
  })
}

// 修改分类与标签的关联关系
export function updateCategoryTagsRel(data) {
  return request({
    url: '/mall/categoryTagsRel',
    method: 'put',
    data: data
  })
}

// 删除分类与标签的关联关系
export function delCategoryTagsRel(categoryId) {
  return request({
    url: '/mall/categoryTagsRel/' + categoryId,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询书籍与作者多对多关系列表
export function listRel(query) {
  return request({
    url: '/mall/rel/list',
    method: 'get',
    params: query
  })
}

// 查询书籍与作者多对多关系详细
export function getRel(bookId) {
  return request({
    url: '/mall/rel/' + bookId,
    method: 'get'
  })
}

// 新增书籍与作者多对多关系
export function addRel(data) {
  return request({
    url: '/mall/rel',
    method: 'post',
    data: data
  })
}

// 修改书籍与作者多对多关系
export function updateRel(data) {
  return request({
    url: '/mall/rel',
    method: 'put',
    data: data
  })
}

// 删除书籍与作者多对多关系
export function delRel(bookId) {
  return request({
    url: '/mall/rel/' + bookId,
    method: 'delete'
  })
}

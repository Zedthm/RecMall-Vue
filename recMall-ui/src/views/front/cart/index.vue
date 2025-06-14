<template>
  <div class="main-content">
    <div style="width: 70%; background-color: white; margin: 30px auto; border-radius: 20px">
      <div style="padding-bottom: 10px">
        <div style="display: flex; font-size: 18px; color: #000000FF; line-height: 80px; border-bottom: #cccccc 1px solid;">
          <div style="margin-left: 20px; flex: 1">全部商品（{{ goodsData.length }}件）</div>
          <div style="flex: 2; text-align: right">
            <el-select v-model="addressId" placeholder="请选择收货地址" style="width: 70%">
              <el-option v-for="item in addressData" :label="item.username + ' - ' + item.useraddress + ' - ' + item.phone" :value="item.id"></el-option>
            </el-select>
          </div>
          <div style="flex: 1; font-size: 16px; text-align: right; padding-right: 20px">
            已选商品 ￥ {{totalPrice}} <el-button type="danger" round @click="pay">下单</el-button>
          </div>
        </div>
        <div style="margin: 20px 0; padding: 0 50px">
          <div class="table">
            <el-table :data="goodsData" strip @selection-change="handleSelectionChange">
              <el-table-column type="selection" width="55" align="center"></el-table-column>
              <el-table-column label="商品图片" width="120px">
                <template v-slot="scope">
                  <el-image style="width: 80px; height: 60px; border-radius: 3px" v-if="scope.row.goodsImg"
                            :src="scope.row.goodsImg" :preview-src-list="[scope.row.goodsImg]"></el-image>
                </template>
              </el-table-column>
              <el-table-column prop="goodsName" label="商品名称" width="240px">
                <template v-slot="scope">
                  <a :href="'/front/detail?id=' + scope.row.goodsId">{{scope.row.goodsName}}</a>
                </template>
              </el-table-column>
              <el-table-column prop="merchantName" label="店铺名称">
                <template v-slot="scope">
                  <a :href="'/front/merchant?id=' + scope.row.merchantId">{{scope.row.merchantName}}</a>
                </template>
              </el-table-column>
              <el-table-column prop="goodsPrice" label="商品价格"></el-table-column>
              <el-table-column prop="num" label="选择数量">
                <template v-slot="scope">
                  <el-input-number v-model="scope.row.num" style="width: 100px" @change="handleChange(scope.row)" :min="1"></el-input-number>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" width="180">
                <template v-slot="scope">
                  <el-button size="mini" type="danger" plain @click="del(scope.row.id)">移除购物车</el-button>
                </template>
              </el-table-column>
            </el-table>

            <div class="pagination" style="margin-top: 20px">
              <el-pagination
                  background
                  @current-change="handleCurrentChange"
                  :current-page="pageNum"
                  :page-sizes="[5, 10, 20]"
                  :page-size="pageSize"
                  layout="total, prev, pager, next"
                  :total="total">
              </el-pagination>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FrontCart',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      goodsData: [],
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      totalPrice: 0,
      total: 0,
      addressId: null,
      addressData: [],
      selectedData: [],
    };
  },
  mounted() {
    this.loadGoods();
    this.loadAddress();
  },
  methods: {
    goToProfile(url) {
      if (!this.user.id) {
        this.$message.warning('请先登录');
        this.navTo('/login');
      } else {
        this.navTo(url);
      }
    },
    navTo(url) {
      location.href = url;
    },
    async loadAddress() {
      try {
        const res = await this.$request.get('/address/selectAll');
        if (res.code === '200') {
          this.addressData = res.data;
        } else {
          this.$message.error(res.msg);
        }
      } catch (error) {
        this.$message.error('加载地址失败，请检查网络连接');
      }
    },
    async loadGoods(pageNum = 1) {
      this.pageNum = pageNum;
      try {
        const res = await this.$request.get('/cart/selectPage', {
          params: {pageNum: this.pageNum, pageSize: this.pageSize},
        });
        if (res.code === '200') {
          this.goodsData = res.data?.list;
          this.total = res.data?.total;
        } else {
          this.$message.error(res.msg);
        }
      } catch (error) {
        this.$message.error('加载商品失败，请检查网络连接');
      }
    },
    async del(id) {
      // 弹出确认框
      this.$confirm('确定要移除这个商品吗？', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
          .then(async () => {
            try {
              const res = await this.$request.delete('/cart/delete/' + id);
              if (res.code === '200') {
                this.$message.success('移除成功');
                this.loadGoods(1); // 重新加载商品
              } else {
                this.$message.error(res.msg); // 处理错误提示
              }
            } catch (error) {
              this.$message.error('删除失败，请检查网络连接'); // 处理异常情况
            }
          })
          .catch(() => {
            // 用户取消了删除操作，不做任何事情
            this.$message.info('已取消删除操作');
          });
    },
    handleCurrentChange(pageNum) {
      this.loadGoods(pageNum);
    },
    handleSelectionChange(rows) {
      this.selectedData = rows;
      this.totalPrice = this.selectedData.reduce((total, item) => total + item.goodsPrice * item.num, 0);
    },
    async pay() {
      if (!this.addressId) {
        this.$message.warning('请选择收货地址');
        return;
      }
      if (!this.selectedData.length) {
        this.$message.warning('请选择商品');
        return;
      }
      const data = {
        userId: this.user.id,
        addressId: this.addressId,
        status: '待发货',
        cartData: this.selectedData,
      };
      try {
        const res = await this.$request.post('/orders/add', data);
        if (res.code === '200') {
          this.$message.success('操作成功');
          this.loadGoods(1);
        } else {
          this.$message.error(res.msg);
        }
      } catch (error) {
        this.$message.error('支付失败，请检查网络连接');
      }
    }
  }
};
</script>
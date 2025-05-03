<template>
  <div class="main-content">
    <div style="width: 60%; background-color: white; min-height: 1000px; margin: 20px auto; border-radius: 20px">
      <div style="padding: 15px 20px">
        <el-row :gutter="20">
          <el-col :span="12">
            <img :src="goodsData.img" alt="" style="width: 100%; height: 400px; border-radius: 20px">
          </el-col>
          <el-col :span="12">
            <div style="font-size: 20px; font-weight: 900; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical;">{{goodsData.name}}</div>
            <div style="color: #666666FF; margin-top: 5px">销量：{{goodsData.count}}</div>
            <div style="color: red; margin-top: 15px">疯抢价：<span style="font-size: 20px">{{goodsData.price}} / {{goodsData.unit}}</span></div>
            <div style="margin-top: 20px">
              <img src="../../../assets/icons/front/right.png" alt="" style="width: 70%; height: 130px; border-radius: 15px">
            </div>
            <div style="color: #666666FF; margin-top: 20px">商家：<a href="#" @click="navTo('/front/merchant?id=' + goodsData.merchantId)">{{goodsData.merchantName}}</a></div>
            <div style="color: #666666FF; margin-top: 20px">分类：<a href="#" @click="navTo('/front/category?id=' + goodsData.categoryId)">{{goodsData.categoryName}}</a></div>
            <div style="color: #666666FF; margin-top: 20px" @mouseover="changeCursorStyle" @mouseleave="resetCursorStyle">
              <el-button @click="addCartList(goodsData)" type="warning">加入购物车</el-button>
              <el-button @click="addCollectList(goodsData)" type="warning">收藏</el-button>
            </div>
          </el-col>
        </el-row>
      </div>
      <div style="padding: 15px 20px">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="宝贝详情" name="first">
            <div style="padding: 10px 175px" v-html="goodsData.description"></div>
          </el-tab-pane>
          <el-tab-pane label="宝贝评价" name="second">
            <div style="margin-top: 10px">
              <div style="margin-top: 20px" v-for="item in commentData">
                <div style="display: flex">
                  <div style="width: 40px">
                    <img :src="item.userAvatar" alt="" style="height: 40px; width: 40px; border-radius: 50%">
                  </div>
                  <div style="width: 200px; margin-left: 10px">
                    <div style="font-weight: 700; font-size: 17px; color: #000000FF">{{item.userName}}</div>
                    <div style="color: #7A7A7AFF">{{item.time}}</div>
                  </div>
                </div>
                <div style="margin-top: 15px; font-size: 16px">{{item.content}}</div>
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "FrontDetail",
  data() {
    let goodsId = this.$route.query.id
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      goodsId: goodsId,
      goodsData: {},
      activeName: 'first',
      commentData: [],
    }
  },
  mounted() {
    this.loadData(); // 调用并行加载数据的方法
  },
  methods: {
    goToProfile(url) {
      if (!this.user.id) {
        this.$message.warning('请先登录'); // 提示用户登录
        this.navTo('/login');
      } else {
        this.navTo(url);
      }
    },
    navTo(url) {
      this.$router.push(url);
    },
    changeCursorStyle(event) {
      event.target.style.cursor = 'pointer';
    },
    resetCursorStyle(event) {
      event.target.style.cursor = 'default';
    },
    loadData() {
      // 使用 Promise.all 来并行请求商品和评论数据
      const goodsPromise = this.$request.get(`/goods/selectById?id=${this.goodsId}`);
      const commentsPromise = this.$request.get(`/comment/selectByGoodsId?id=${this.goodsId}`);

      Promise.all([goodsPromise, commentsPromise])
          .then(([goodsRes, commentsRes]) => {
            if (goodsRes.code === '200') {
              this.goodsData = goodsRes.data;
            } else {
              this.$message.error(goodsRes.msg);
            }

            if (commentsRes.code === '200') {
              this.commentData = commentsRes.data;
            } else {
              this.$message.error(commentsRes.msg);
            }
          })
          .catch(() => {
            this.$message.error('请求失败，请检查网络连接');
          });
    },
    addCartList(goodsData) {
      if (!this.user.id) {
        this.$message.warning('请先登录');
        this.navTo('/login');
        return; // 添加 return 以防止继续执行后续代码
      }

      const queryData = {
        userId: this.user.id,
        goodsId: goodsData.id,
        merchantId: goodsData.merchantId,
        num: goodsData.num ? goodsData.num : 0,
      };

      this.$request.post('/cart/add', queryData)
          .then(res => {
            if (res.code === '200') {
              this.$message.success('添加购物车成功');
            } else {
              this.$message.error(res.msg);
            }
          })
          .catch(() => {
            this.$message.error('请求失败，请检查网络连接');
          });
    },
    addCollectList(goodsData) {
      if (!this.user.id) {
        this.$message.warning('请先登录');
        this.navTo('/login');
        return; // 添加 return 以防止继续执行后续代码
      }

      const queryData = {
        userId: this.user.id,
        goodsId: goodsData.id,
        merchantId: goodsData.merchantId,
      };

      this.$request.post('/collect/add', queryData)
          .then(res => {
            if (res.code === '200') {
              this.$message.success('收藏成功');
            } else {
              this.$message.error(res.msg);
            }
          })
          .catch(() => {
            this.$message.error('请求失败，请检查网络连接');
          });
    },
    handleClick(tab) {
      this.activeName = tab.name;
    },
  }
}
</script>

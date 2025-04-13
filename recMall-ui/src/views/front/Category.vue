<template>
  <div class="main-content">
    <div style="display: flex; width: 70%; background-color: white; margin: 30px auto; border-radius: 20px">
      <div style="flex: 1; padding: 0 20px">
        <div style="font-size: 18px; color: #000000FF; line-height: 80px; border-bottom: #cccccc 1px solid">{{ categoryData.name }}</div>
        <div style="margin: 20px 0">
          <el-row :gutter="20">
            <el-col :span="6" style="margin-bottom: 20px" v-for="item in goodsData" :key="item.id">
              <img @mouseover="changeCursorStyle"
                   @mouseleave="resetCursorStyle"
                   @click="navTo('/front/detail?id=' + item.id)" :src="item.img" alt=""
                   style="width: 100%; height: 175px; border-radius: 10px; border: #cccccc 1px solid">
              <div style="margin-top: 10px; font-weight: 500; font-size: 16px; width: 180px; color: #000000FF; text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">{{ item.name }}</div>
              <div style="margin-top: 5px; font-size: 20px; color: #FF5000FF">￥ {{ item.price }} / {{ item.unit }}</div>
            </el-col>
          </el-row>
        </div>
      </div>
      <div style="width: 250px; padding: 0 20px; border-left: #cccccc 1px solid">
        <div style="font-size: 18px; color: #000000FF; line-height: 80px; border-bottom: #cccccc 1px solid">猜你喜欢</div>
        <div style="margin: 20px 0; padding: 0 10px">
          <div v-for="recommended in recommendData" :key="recommended.id" style="margin-bottom: 20px">
            <img @click="navTo('/front/detail?id=' + recommended.id)" :src="recommended.img" alt="" style="width: 100%; height: 175px; border-radius: 10px; border: #cccccc 1px solid">
            <div style="margin-top: 10px; font-weight: 500; font-size: 16px; width: 180px; color: #000000FF; text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">{{ recommended.name }}</div>
            <div style="margin-top: 5px; font-size: 20px; color: #FF5000FF">￥ {{ recommended.price }} / {{ recommended.unit }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "FrontCategory",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      categoryId: null,
      goodsData: [],
      categoryData: {},
      recommendData: [] // 推荐商品数据
    }
  },
  mounted() {
    this.categoryId = this.$route.query.id;
    if (this.categoryId) {
      this.loadData();
    } else {
      this.$message.error('无效的分类 ID');
    }
  },
  methods: {
    changeCursorStyle(event) {
      event.target.style.cursor = 'pointer';
    },
    resetCursorStyle(event) {
      event.target.style.cursor = 'default';
    },
    loadRecommend() {
      return this.$request.get('/goods/recommend', {
        params: { userId: this.user.id }
      }).then(res => {
        if (res.code === '200') {
          this.recommendData = res.data;
        } else {
          this.$message.error(res.msg);
        }
      }).catch(error => {
        console.error("加载推荐商品错误:", error);
        this.$message.error('加载推荐商品失败');
      });
    },
    loadCategory() {
      return this.$request.get('/category/selectById/' + this.categoryId)
          .then(res => {
            if (res.code === '200') {
              this.categoryData = res.data;
            } else {
              this.$message.error(res.msg);
            }
          }).catch(error => {
            console.error("加载类别错误:", error);
            this.$message.error('加载类别失败');
          });
    },
    loadGoods() {
      return this.$request.get('/goods/selectByCategoryId?id=' + this.categoryId)
          .then(res => {
            if (res.code === '200') {
              this.goodsData = res.data;
            } else {
              this.$message.error(res.msg);
            }
          }).catch(error => {
            console.error("加载商品错误:", error);
            this.$message.error('加载商品失败');
          });
    },
    loadData() {
      // 使用 Promise.all 并行发送请求
      Promise.all([
        this.loadCategory(),
        this.loadGoods(),
        this.loadRecommend()
      ]).then(() => {
        console.log("所有数据加载完成");
      }).catch(error => {
        console.error("加载数据时出错:", error);
      });
    },
    navTo(url) {
      location.href = url;
    }
  }
}
</script>

<template>
  <div class="main-content">
    <div style="display: flex; width: 70%; background-color: white; margin: 30px auto; border-radius: 20px">
      <div style="flex: 1; padding: 0 20px">
        <div style="font-size: 18px; color: #000000FF; line-height: 80px; border-bottom: #cccccc 1px solid">搜索"{{name}}"的结果</div>
        <div style="margin: 20px 0">
          <el-row :gutter="20">
            <el-col :span="6" style="margin-bottom: 20px" v-for="item in goodsData">
              <img @click="navTo('/front/detail?id=' + item.id)" :src="item.img" alt="" style="width: 100%; height: 175px; border-radius: 10px; border: #cccccc 1px solid">
              <div style="margin-top: 10px; font-weight: 500; font-size: 16px; width: 180px; color: #000000FF; text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">{{item.name}}</div>
              <div style="margin-top: 5px; font-size: 20px; color: #FF5000FF">￥ {{item.price}} / {{item.unit}}</div>
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
  data() {
    let name = this.$route.query.name;
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      name: name,
      goodsData: [],
      recommendData: [], // 推荐商品数据
      loading: false, // 加载状态
    };
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
    async loadData() {
      this.loading = true; // 设置加载状态
      try {
        const [goodsResponse, recommendResponse] = await Promise.all([
          this.$request.get('/goods/selectByName?name=' + this.name),
          this.$request.get('/goods/recommend', {
            params: { userId: this.user.id }
          })
        ]);

        // 处理商品数据
        if (goodsResponse.code === '200') {
          this.goodsData = goodsResponse.data; // 成功获取商品数据
        } else {
          this.$message.error(goodsResponse.msg); // 处理错误提示
        }

        // 处理推荐商品数据
        if (recommendResponse.code === '200') {
          this.recommendData = recommendResponse.data; // 成功获取推荐数据
        } else {
          this.$message.error(recommendResponse.msg); // 处理错误提示
        }
      } catch (error) {
        this.$message.error('加载数据失败，请检查网络连接'); // 处理异常情况
        console.error("加载数据错误:", error); // 输出错误信息
      } finally {
        this.loading = false; // 无论请求成功或失败，最后都要重置加载状态
      }
    },
    navTo(url) {
      this.$router.push(url); // 使用 Vue Router 进行导航
    }
  }
};
</script>
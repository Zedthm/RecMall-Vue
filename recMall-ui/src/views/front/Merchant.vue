<template>
  <div class="main-content" style="background-color: #f8f9fa; padding: 10px; min-height: 100vh;">
    <div style="width: 60%; margin: 30px auto; border-radius: 20px; background-color: #f8f9fa;">
      <div
          style="height: 100px; padding: 0 10px; display: flex; align-items: center; border-radius: 25px; background-color: white; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);">
        <img :src="merchantData.avatar" alt="" style="height: 60px; width: 60px; border-radius: 50%">
        <div style="width: 220px; margin: 0 30px 0 15px; font-size: 20px; font-weight: bold;">
          <div style="height: 30px; line-height: 30px">{{ merchantData.name }}</div>
          <img src="@/assets/icons/front/icon.png" alt="" style="height: 25px; margin-top: 5px">
        </div>
        <div style="width: 150px; height: 100px; padding: 20px">
          <div style="font-size: 16px; height: 30px; line-height: 30px; color: #7F7F7FFF">店铺电话</div>
          <div
              style="font-size: 16px; height: 30px; line-height: 30px;
              overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">
            {{ merchantData.phone }}
          </div>
        </div>
        <div style="width: 150px; height: 100px; padding: 20px">
          <div style="font-size: 16px; height: 30px; line-height: 30px; color: #7F7F7FFF">店铺邮箱</div>
          <div
              style="font-size: 16px; height: 30px; line-height: 30px;
              overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">
            {{ merchantData.email }}
          </div>
        </div>
        <div style="flex: 1; height: 100px; padding: 20px">
          <div
              style="height: 60px; line-height: 30px; font-size: 16px; color: #000000FF;
              overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 2;
              -webkit-box-orient: vertical;">
            店铺介绍：{{ merchantData.description }}
          </div>
        </div>
      </div>
      <div
          style="border-radius: 20px; padding: 0 20px; background-color: white; margin-top: 20px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);">
        <div style="font-size: 18px; color: #000000FF; line-height: 80px; border-bottom: #cccccc 1px solid">本店所有商品（{{ goodsData.length }}件）</div>
        <div style="margin-top: 20px">
          <el-row>
            <el-col :span="5" style="margin-bottom: 20px" v-for="item in goodsData" :key="item.id">
              <img
                  @mouseover="changeCursorStyle"
                  @mouseleave="resetCursorStyle"
                  @click="navTo('/front/detail?id=' + item.id)"
                  :src="item.img"
                  alt=""
                  style="width: 100%; height: 150px; border-radius: 10px; border: #cccccc 1px solid; cursor: pointer;">
              <div style="margin-top: 10px; font-weight: 500; font-size: 16px; width: 160px; color: #000000FF; text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">{{ item.name }}</div>
              <div style="margin-top: 5px; font-size: 20px; color: #FF5000FF">￥ {{ item.price }} / {{ item.unit }}</div>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FrontMerchant',
  data() {
    const merchantId = this.$route.query.id;
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      merchantId,
      goodsData: [],
      merchantData: {}
    }
  },
  mounted() {
    this.loadMerchantAndGoods();
  },
  methods: {
    goToProfile(url) {
      if (this.user.id == null) {
        this.$message.warning('请先登录'); // 提示用户登录
        this.navTo('/login');
      } else {
        this.navTo(url);
      }
    },
    changeCursorStyle(event) {
      event.target.style.cursor = 'pointer';
    },
    resetCursorStyle(event) {
      event.target.style.cursor = 'default';
    },
    loadMerchant() {
      return this.$request.get(`/merchant/selectById/${this.merchantId}`).then(res => {
        if (res.code === '200') {
          this.merchantData = res.data;
        } else {
          this.$message.error(res.msg);
        }
      }).catch(error => {
        console.error("加载商家错误:", error);
        this.$message.error('加载商家失败');
      });
    },
    loadGoods() {
      return this.$request.get(`/goods/selectByMerchantId?id=${this.merchantId}`).then(res => {
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
    loadMerchantAndGoods() {
      Promise.all([this.loadMerchant(), this.loadGoods()])
          .then(() => {
            // 可以在这里添加所有请求成功后的逻辑
            console.log("商家和商品加载成功");
          })
          .catch((error) => {
            console.error("请求失败:", error);
            this.$message.error('部分数据加载失败');
          });
    },
    navTo(url) {
      this.$router.push(url);
    }
  }
}
</script>

<style scoped>
.el-col-5 {
  width: 20%;
  max-width: 20%;
  padding: 10px 10px;
}
</style>

<template>
  <div class="main-content">
    <div style="height: 60px; background-color: #C566F6FF"></div>
    <div style="display: flex">
      <div class="left"></div>
      <div style="width: 66%; background-color: white; height: 1000px">
        <div style="color: #FE0137FF; margin: 15px 0 15px 18px; font-weight: bold; font-size: 16px">主题市场</div>
        <div style="display: flex; margin: 0 25px; height: 550px">
          <div style="flex: 2">
            <div style="display: flex; color: #666666FF; margin: 14px 0" v-for="item in typeData">
              <img :src="item.img" alt="" style="height: 20px; width: 20px">
              <div style="margin-left: 10px; font-size: 14px"><a href="#" @click="navTo('/front/category?id=' + item.id)">{{item.name}}</a></div>
            </div>
          </div>
          <div style="flex: 5; margin-top: 15px">
            <div>
              <el-carousel height="300px" style="border-radius: 10px">
                <el-carousel-item v-for="item in carousel_top">
                  <img :src="item" alt="" style="width: 100%; height: 300px; border-radius: 10px">
                </el-carousel-item>
              </el-carousel>
            </div>
            <div style="margin-top: 30px; display: flex">
              <div style="flex: 1">
                <el-carousel height="300px" style="border-radius: 10px">
                  <el-carousel-item v-for="item in carousel_left">
                    <img :src="item" alt="" style="width: 100%; height: 200px; border-radius: 10px">
                  </el-carousel-item>
                </el-carousel>
              </div>
              <div style="flex: 1; margin-left: 5px">
                <el-carousel height="300px" style="border-radius: 10px">
                  <el-carousel-item v-for="item in carousel_right">
                    <img :src="item" alt="" style="width: 100%; height: 200px; border-radius: 10px">
                  </el-carousel-item>
                </el-carousel>
              </div>
            </div>
          </div>
          <div style="flex: 3; background-color: #F3F3F3FF; margin-top: 15px; margin-left: 15px; border-radius: 10px;">
            <div style="text-align: center; margin-top: 30px; ">
              <img @click="goToProfile('/front/person')" :src="user.avatar" alt=""
                   style="cursor: pointer; width: 80px; height: 80px; border-radius: 50%" @mouseover="changeCursorStyle"
                   @mouseleave="resetCursorStyle">
              <div style="margin-top: 10px">Hi，{{ user.name }}</div>
            </div>

            <div style="margin-top: 20px; padding: 0 15px">
              <img src="@/assets/icons/front/right.png" alt="" style="height: 150px; width: 100%; border-radius: 20px">
            </div>

            <div style="margin: 20px 10px 10px 10px; width: 250px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis">
              <i class="el-icon-bell"></i>
              <span style="font-weight: bold">公告</span>
              <span style="color: #666666;">：{{ top }}</span>
            </div>

            <div style="display: flex; margin-top: 50px">
              <div @click="goToProfile('/front/collect')" style="flex: 1; text-align: center; cursor: pointer;"
                   @mouseover="changeCursorStyle" @mouseleave="resetCursorStyle">
                <img src="@/assets/icons/front/收藏.png" alt="" style="height: 25px; width: 25px">
                <div>我的收藏</div>
              </div>
              <div @click="goToProfile('/front/address')" style="flex: 1; text-align: center; cursor: pointer;" @mouseover="changeCursorStyle"
                   @mouseleave="resetCursorStyle">
                <img src="@/assets/icons/front/店铺.png" alt="" style="height: 25px; width: 25px">
                <div>我的地址</div>
              </div>
              <div @click="goToProfile('/front/cart')" style="flex: 1; text-align: center; cursor: pointer;" @mouseover="changeCursorStyle"
                   @mouseleave="resetCursorStyle">
                <img src="@/assets/icons/front/购物车.png" alt="" style="height: 25px; width: 25px">
                <div>我的购物车</div>
              </div>
              <div @click="goToProfile('/front/orders')" style="flex: 1; text-align: center; cursor: pointer;" @mouseover="changeCursorStyle"
                   @mouseleave="resetCursorStyle">
                <img src="@/assets/icons/front/订单.png" alt="" style="height: 25px; width: 25px">
                <div>我的订单</div>
              </div>
            </div>
          </div>
        </div>
        <div style="margin: 40px 0 0 15px; height: 40px; background-color: #04BF04FF; font-size: 20px; color: white; width: 130px; font-weight: bold; line-height: 40px; text-align: center; border-radius: 20px">
          热卖商品
        </div>
        <div style="margin: 10px 5px 0 5px">
          <el-row>
            <el-col :span="5" v-for="item in goodsData">
              <img @mouseover="changeCursorStyle"
                   @mouseleave="resetCursorStyle"
                   @click="navTo('/front/detail?id=' + item.id)" :src="item.img" alt=""
                   style="width: 100%; height: 175px; border-radius: 10px; border: #cccccc 1px solid">
              <div style="margin-top: 10px; font-weight: 500; font-size: 16px; width: 180px; color: #000000FF; text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">{{ item.name }}</div>
              <div style="margin-top: 5px; font-size: 20px; color: #FF5000FF">￥ {{ item.price }} / {{ item.unit }}</div>
            </el-col>
          </el-row>
        </div>
        <div v-if="user.id"
             style="margin: 40px 0 0 15px; height: 40px; background-color: #04BF04FF; font-size: 20px; color: white; width: 130px; font-weight: bold; line-height: 40px; text-align: center; border-radius: 20px">
          猜你喜欢
        </div>
        <div style="margin: 10px 5px 0 5px">
          <el-row>
            <el-col :span="5" v-for="item in recommendData">
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
      <div class="right"></div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'FrontHomePage',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      typeData: [],
      top: null,
      notice: [],
      carousel_top: [
        require('@/assets/icons/front/carousel-1.png'),
        require('@/assets/icons/front/carousel-2.png'),
        require('@/assets/icons/front/carousel-9.png'),
      ],
      carousel_left: [
        require('@/assets/icons/front/carousel-3.png'),
        require('@/assets/icons/front/carousel-4.png'),
        require('@/assets/icons/front/carousel-5.png'),
      ],
      carousel_right: [
        require('@/assets/icons/front/carousel-6.png'),
        require('@/assets/icons/front/carousel-7.png'),
        require('@/assets/icons/front/carousel-8.png'),
      ],
      goodsData: [],
      recommendData: [],
    }
  },
  mounted() {
    this.loadData(); // 使用 loadData 方法并行加载数据
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
    navTo(url) {
      location.href = url;
    },
    loadData() {
      // 使用 Promise.all 并行加载所有数据
      Promise.all([this.loadType(), this.loadNotice(), this.loadGoods(), this.loadRecommend()])
          .then(() => {
            console.log("所有数据加载完成");
          })
          .catch(error => {
            console.error("加载数据时出错:", error);
          });
    },
    loadRecommend() {
      if(this.user.id == null) return;
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
    loadGoods() {
      return this.$request.get('/goods/selectTop15')
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
    loadType() {
      return this.$request.get('/category/selectAll')
          .then(res => {
            if (res.code === '200') {
              this.typeData = res.data;
            } else {
              this.$message.error(res.msg);
            }
          }).catch(error => {
            console.error("加载类别错误:", error);
            this.$message.error('加载类别失败');
          });
    },
    loadNotice() {
      return this.$request.get('/notice/selectAll')
          .then(res => {
            this.notice = res.data;
            let i = 0;
            if (this.notice && this.notice.length) {
              this.top = this.notice[0].content;
              setInterval(() => {
                this.top = this.notice[i].content;
                i++;
                if (i === this.notice.length) {
                  i = 0;
                }
              }, 2500);
            }
          }).catch(error => {
            console.error("加载公告错误:", error);
            this.$message.error('加载公告失败');
          });
    },
  }
}
</script>

<style scoped>
.main-content {
  min-height: 100vh;
  /*overflow: hidden;*/
  background-size: 100%;
  background-image: url('@/assets/icons/front/img.png');
}
.left {
  width: 17%;
  background-repeat: no-repeat;
  background-image: url('@/assets/icons/front/left-img.png');
}
.right {
  width: 17%;
  background-repeat: no-repeat;
  background-image: url('@/assets/icons/front/right-img.png')
}
.el-col-5{
  width: 20%;
  max-width: 20%;
  padding: 10px 10px;
}
</style>

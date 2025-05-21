<template>
  <div class="main-content">
    <div style="height: 60px; background-color: #C566F6FF"></div>
    <div style="display: flex">
      <div class="left"></div>
      <div style="width: 66%; background-color: white; overflow-y: auto; max-height: 100vh;">
        <div style="margin: 40px 0 0 15px; height: 40px; background-color: #04BF04FF; font-size: 20px; color: white; width: 130px; font-weight: bold; line-height: 40px; text-align: center; border-radius: 20px">
          DeepFM模型推荐
        </div>
        <div style="margin: 10px 5px 0 5px">
          <el-row :gutter="20">
            <!-- 修改字段映射，添加数据校验 -->
            <el-col
              :span="5"
              v-for="(item, index) in booksList"
              :key="item.bookId"
              style="margin-bottom: 20px;"
            >
              <!-- 图片处理优化 -->
              <div class="book-card">
                <img
                  @mouseover="changeCursorStyle"
                  @mouseleave="resetCursorStyle"
                  @click="navTo('/front/detail?id=' + item.bookId)"
                  :src=getBookCover(item)
                  alt="图书封面"
                  class="book-cover"
                  @error="handleImageError"
                >

                <!-- 信息展示优化 -->
                <div class="book-info">
                  <div class="title" :title="item.title">
                    {{ item.title || '未命名图书' }}
                  </div>

                  <div class="price-section">
                    <span class="price">￥{{ formatPrice(item.price) }}</span>
                    <span class="unit">/ {{ item.units || '本' }}</span>

                    <!-- 库存状态 -->
                    <div
                      v-if="item.inventory <= 0"
                      class="stock-tag out-of-stock"
                    >
                      已售罄
                    </div>
                    <div
                      v-else
                      class="stock-tag"
                    >
                      库存 {{ item.inventory }} {{ item.units || '本' }}
                    </div>
                  </div>
                </div>
              </div>
            </el-col>
          </el-row>
        </div>
        <div class="loading-status">
          <span v-if="isLoading">正在加载更多商品...</span>
          <span v-if="!booksHasMore" class="no-more">已加载全部商品</span>
        </div>
        <div style="margin: 40px 0 0 15px; height: 40px; background-color: #04BF04FF; font-size: 20px; color: white; width: 130px; font-weight: bold; line-height: 40px; text-align: center; border-radius: 20px">
          NCF推荐
        </div>
        <div style="margin: 10px 5px 0 5px">
          <el-row :gutter="20">
            <!-- 修改字段映射，添加数据校验 -->
            <el-col
              :span="5"
              v-for="(item, index) in recommendData"
              :key="item.bookId"
              style="margin-bottom: 20px;"
            >
              <!-- 图片处理优化 -->
              <div class="book-card">
                <img
                  @mouseover="changeCursorStyle"
                  @mouseleave="resetCursorStyle"
                  @click="navTo('/front/detail?id=' + item.bookId)"
                  :src="item.img || require('@/assets/default-book.png')"
                  alt="图书封面"
                  class="book-cover"
                  @error="handleImageError"
                >

                <!-- 信息展示优化 -->
                <div class="book-info">
                  <div class="title" :title="item.title">
                    {{ item.title || '未命名图书' }}
                  </div>

                  <div class="price-section">
                    <span class="price">￥{{ formatPrice(item.price) }}</span>
                    <span class="unit">/ {{ item.units || '本' }}</span>

                    <!-- 库存状态 -->
                    <div
                      v-if="item.inventory <= 0"
                      class="stock-tag out-of-stock"
                    >
                      已售罄
                    </div>
                    <div
                      v-else
                      class="stock-tag"
                    >
                      库存 {{ item.inventory }} {{ item.units || '本' }}
                    </div>
                  </div>
                </div>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
      <div class="loading-status" v-if="user.id">
        <span v-if="isLoading">正在加载推荐内容...</span>
        <span v-if="!recommendHasMore" class="no-more">没有更多推荐了</span>
      </div>
      <div class="right"></div>
    </div>
  </div>
</template>

<script>
import { listCategories } from "@/api/mall/categories";
import { listNotice, getNotice, delNotice, addNotice, updateNotice } from "@/api/system/notice";
import {
  listBooks,
  getBook,
  delBook,
  addBook,
  updateBook,
  listRecBooksDeepFM,
  listRecBooksNeuralCF, listRecBooksNeuralCFSingleUser
} from "@/api/mall/books";
import {getUserProfile} from "@/api/system/user";

export default {
  name: 'FrontHomePage',
  data() {
    return {
      // 遮罩层
      loading: true,
      booksCurrentPage: 1,    // 热卖商品当前页
      recommendCurrentPage: 1, // 推荐商品当前页
      booksHasMore: true,      // 热卖商品是否有更多
      recommendHasMore: true,  // 推荐商品是否有更多
      isLoading: false,        // 加载锁定
      scrollThreshold: 200,     // 触发加载的滚动阈值
      hoverCategory: null, // 新增的响应式属性
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      user: {},
      roleGroup: {},
      postGroup: {},
      categoriesList: [],
      topNotice: null,
      noticeList: [],
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
      booksList: [],
      recommendData: [],
      defaultCovers: [], // 存储默认封面列表
      maxDefaultCovers: 30 // 根据实际图片数量修改
    }
  },
  created() {
    this.getUser();
  },
  mounted() {
    this.loadDynamicCovers();
    this.loadData(); // 使用 loadData 方法并行加载数据
    this.initScrollListener();
  },
  beforeDestroy() {
    window.removeEventListener('scroll', this.handleScroll);
    clearInterval(this.intervalId);
  },
  filters:{
    fixImgPath(value) {
      return value.startsWith('/profile')
        ? process.env.VUE_APP_BASE_API + value
        : value;
    }
  },
  methods: {
    // 动态加载封面
    loadDynamicCovers() {
      if (this.defaultCovers.length > 10){
        return;
      }
      try {
        // 获取assets/default-covers目录下所有图片
        const coverContext = require.context(
          '@/assets/default-covers', // 目录路径
          true,                      // 包含子目录
          /\.(png|jpe?g|webp|gif)$/i // 匹配图片格式（不区分大小写）
        );

        // 转换路径并过滤非图片文件
        this.defaultCovers = coverContext.keys()
          .filter(path => !/\.(svg|json)$/i.test(path)) // 排除非图片类型
          .map(path => {
            try {
              return require('@/assets/default-covers/' + path.split('/').pop());
            } catch (e) {
              console.warn('封面加载失败:', path);
              return null;
            }
          })
          .filter(Boolean);

        // 添加保底图片
        if (this.defaultCovers.length === 0) {
          this.defaultCovers.push(require('@/assets/default-book.png'));
        }
      } catch (error) {
        console.error('封面加载异常:', error);
        this.defaultCovers = [require('@/assets/default-book.png')];
      }
    },

    // 获取封面图片地址
    getBookCover(item) {
      return item.img || this.getRandomDefaultCover();
    },

    // 生成随机默认封面
    getRandomDefaultCover() {
      if (this.defaultCovers.length > 0) {
        const randomIndex = Math.floor(Math.random() * this.defaultCovers.length);
        return this.defaultCovers[randomIndex];
      }
      return require('@/assets/default-book.png'); // 保底默认图
    },
    getUser() {
      getUserProfile().then(response => {
        this.user = response.data;
        this.roleGroup = response.roleGroup;
        this.postGroup = response.postGroup;
      });
    },
    // 带防抖的滚动处理
    handleScroll: _.debounce(function() {
      const { scrollTop, clientHeight, scrollHeight } = document.documentElement
      if (scrollTop + clientHeight >= scrollHeight - this.scrollThreshold) {
        this.getBooks()
        if (this.user.id) this.loadRecommend()
      }
    }, 200),

    // 初始化滚动监听
    initScrollListener() {
      window.addEventListener('scroll', this.handleScroll)
    },
    // 价格格式化
    formatPrice(price) {
      return Number(price || 0).toFixed(2)
    },

    // 图片加载失败处理
    handleImageError(e) {
      const img = e.target;
      // 保持容器尺寸
      img.style.height = '190px';
      img.style.objectFit = 'contain';  // 避免拉伸
      img.src = require('@/assets/default-book.png');

      // 添加错误状态标识
      img.classList.add('cover-error');
    },

    goToProfile(url) {
      if (this.user.userId == null) {
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
      Promise.all([this.getBooksNCF(), this.getRecBooksDeepFM()])
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
    getBooksNCF() {
      this.loading = true;
      return  listRecBooksDeepFM(500).then(res => {
        if (res.code == '200') {
          this.booksList = res.rows;
        } else {
          this.$message.error(res.msg);
        }
      }).catch(error => {
        console.error("加载商品错误:", error);
        this.$message.error('加载商品失败');
      });

    },
    getRecBooksDeepFM(){
      this.loading = true;
      return listRecBooksNeuralCF(500).then(res => {
        if (res.code == '200') {
          this.recommendData = res.rows;
        }else{
          this.$message.error(res.msg);
        }
      }).catch(error => {
        console.error("加载推荐商品错误:", error);
        this.$message.error('加载推荐商品商品失败');
      });
    },
  }
}
</script>

<style scoped>
/* 统一版头样式 */
.section-header {
  display: flex;
  align-items: center;
  margin: 20px 0;
  padding: 0 18px;
}

.decorative-line {
  flex: 1;
  height: 2px;
  background: linear-gradient(90deg, transparent, #FE0137 50%, transparent);
}

.section-title {
  padding: 0 20px;
  color: #333;
  font-size: 20px;
  font-weight: 600;
  text-shadow: 1px 1px 2px rgba(0,0,0,0.1);
}

/* 分类列表优化 */
.category-wrapper {
  margin: 0 25px;
  border-radius: 8px;
  background: rgba(255,255,255,0.95);
  box-shadow: 0 2px 12px rgba(0,0,0,0.06);
}

.category-item {
  position: relative;
  display: flex;
  align-items: center;
  padding: 12px 18px;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  border-bottom: 1px solid #f0f0f0;
}

.category-item:last-child {
  border-bottom: none;
}

.category-icon {
  width: 28px;
  height: 28px;
  padding: 4px;
  background: #fff;
  border-radius: 6px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.08);
}
.category-icon img {
  width: 100%;
  height: 100%;
  object-fit: contain;
}
.category-name {
  margin-left: 15px;
  font-size: 15px;
}

.category-link {
  color: #666;
  transition: color 0.3s;
}

.category-link:hover {
  color: #FE0137;
  text-decoration: none;
}
/* 悬停效果 */
.hover-indicator {
  position: absolute;
  left: 0;
  top: 0;
  width: 3px;
  height: 100%;
  background: #FE0137;
  opacity: 0;
  transition: opacity 0.3s;
}

.hover-indicator.active {
  opacity: 1;
}

.category-item:hover {
  background: rgba(254, 1, 55, 0.03);
  transform: translateX(8px);
}

.main-content {
  min-height: 100vh;
  /*overflow: hidden;*/
  background-size: 100%;
  background-image: url('~@/assets/icons/front/img.png');
}
.left {
  width: 17%;
  background-repeat: no-repeat;
  background-image: url('~@/assets/icons/front/left-img.png');
}
.right {
  width: 17%;
  background-repeat: no-repeat;
  background-image: url('~@/assets/icons/front/right-img.png')
}


.el-col-5{
  width: 20%;
  max-width: 20%;
  padding: 10px 10px;
}

/* 右侧用户面板优化 */
.user-panel {
  background: linear-gradient(135deg, #f8f9fa 0%, #f1f3f5 100%);
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.06);
}
/* 优化卡片样式 */
.book-card {
  padding: 10px;
  border-radius: 8px;
  transition: box-shadow 0.3s;
}

.book-card:hover {
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
}

/* 保持原有比例优化 */
.book-cover {
  width: 100%;
  height: 190px;  /* 原175px基础上增加15px */
  object-fit: cover;
  border-radius: 12px;  /* 增加圆角 */
  box-shadow: 0 2px 8px rgba(0,0,0,0.1); /* 添加投影 */
  transition: transform 0.3s ease;
}

.book-cover:hover {
  transform: scale(1.03);
}

.book-info {
  padding: 10px 5px;
}

.title {
  font-weight: 500;
  font-size: 16px;
  color: #333;
  margin: 10px 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.price-section {
  display: flex;
  align-items: baseline;
  gap: 5px;
  font-size: 20px;
}

.price {
  color: #ff5000;
  font-weight: bold;
}

.unit {
  font-size: 14px;
  color: #666;
}

.stock-tag {
  font-size: 12px;
  padding: 2px 5px;
  background: #f5f5f5;
  border-radius: 3px;
  margin-left: auto;
}

.out-of-stock {
  background: #ffeceb;
  color: #f56c6c;
}

.loading-status {
  text-align: center;
  padding: 15px;
  color: #666;
}

.no-more {
  color: #999;
  font-size: 0.9em;
}

:root {
  --primary-color: #FE0137;
  --secondary-color: #04BF04;
  --text-dark: #333;
  --text-light: #666;
}

body {
  color: var(--text-dark);
  font-family: 'Helvetica Neue', Helvetica, Arial, 'PingFang SC', 'Microsoft YaHei', sans-serif;
}
</style>

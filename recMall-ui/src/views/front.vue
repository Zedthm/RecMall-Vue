<template>
  <div class="main-content">
    <div style="height: 60px; background-color: #C566F6FF"></div>
    <div style="display: flex">
      <div class="left"></div>
      <div style="width: 66%; background-color: white; overflow-y: auto; max-height: 100vh;">
        <!-- 修改主题市场标题部分 -->
        <div class="section-header">
          <div class="decorative-line"></div>
          <h2 class="section-title">主题市场</h2>
          <div class="decorative-line"></div>
        </div>
        <div style="display: flex; margin: 0 25px; height: 550px">
          <!-- 优化分类列表 -->
          <div class="category-wrapper">
            <div
              v-for="item in categoriesList"
              :key="item.categoryId"
              class="category-item"
              @mouseenter="hoverCategory = item.categoryId"
              @mouseleave="hoverCategory = null"
            >
              <div class="category-icon">
                <img :src="item.img | fixImgPath" alt="分类图标">
              </div>
              <div class="category-name">
                <router-link
                  :to="'/front/category?id=' + item.categoryId"
                  class="category-link"
                >
                  {{ item.categoryName }}
                </router-link>
              </div>
              <div
                class="hover-indicator"
                :class="{ active: hoverCategory === item.categoryId }"
              ></div>
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
              <img
                @click="goToProfile('/user/profile')"
                :src="user.avatar || require('@/assets/default-avatar.png')"
                alt="用户头像"
                style="cursor: pointer; width: 80px; height: 80px; border-radius: 50%"
                @mouseover="changeCursorStyle"
                @mouseleave="resetCursorStyle">
              <div style="margin-top: 10px">Hi，{{ user.nickName || '欢迎回来' }}</div>
            </div>

            <div style="margin-top: 20px; padding: 0 15px">
              <img src="@/assets/icons/front/right.png" alt="" style="height: 150px; width: 100%; border-radius: 20px">
            </div>

            <div
              style="margin: 20px 10px 10px 10px; width: 250px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis">
              <i class="el-icon-bell"></i>
              <span style="font-weight: bold">公告</span>
              <span style="color: #666666;">：{{ topNotice }}</span>
            </div>

            <div style="display: flex; margin-top: 50px">
              <div @click="goToProfile('/front/collect')" style="flex: 1; text-align: center; cursor: pointer;"
                   @mouseover="changeCursorStyle" @mouseleave="resetCursorStyle">
                <img src="@/assets/icons/front/收藏.png" alt="" style="height: 25px; width: 25px">
                <div>我的收藏</div>
              </div>
              <div @click="goToProfile('/front/address')" style="flex: 1; text-align: center; cursor: pointer;"
                   @mouseover="changeCursorStyle"
                   @mouseleave="resetCursorStyle">
                <img src="@/assets/icons/front/店铺.png" alt="" style="height: 25px; width: 25px">
                <div>我的地址</div>
              </div>
              <div @click="goToProfile('/front/cart')" style="flex: 1; text-align: center; cursor: pointer;"
                   @mouseover="changeCursorStyle"
                   @mouseleave="resetCursorStyle">
                <img src="@/assets/icons/front/购物车.png" alt="" style="height: 25px; width: 25px">
                <div>我的购物车</div>
              </div>
              <div @click="goToProfile('/front/orders')" style="flex: 1; text-align: center; cursor: pointer;"
                   @mouseover="changeCursorStyle"
                   @mouseleave="resetCursorStyle">
                <img src="@/assets/icons/front/订单.png" alt="" style="height: 25px; width: 25px">
                <div>我的订单</div>
              </div>
            </div>
          </div>
        </div>
        <div
          style="margin: 40px 0 0 15px; height: 40px; background-color: #04BF04FF; font-size: 20px; color: white; width: 130px; font-weight: bold; line-height: 40px; text-align: center; border-radius: 20px">
          热门商品
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
                  @click="navTo(`/front/book-detail?id=${item.bookId}&coverImg=${encodeURIComponent(getBookCover(item))}`)"
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
        <div style="display: flex; align-items: center; margin: 40px 0 0 15px;">
          <div
            style="height: 40px; background-color: #04BF04FF; font-size: 20px;
          color: white; width: 200px; font-weight: bold;
          line-height: 40px; text-align: center;
          border-radius: 20px 0 0 20px;">
            DeepFM商品推荐
          </div>
          <!-- 刷新按钮 -->
          <div
            style="height: 40px; width: 100px; background-color: #04BF04FF;
          margin-left: 2px; border-radius: 0 20px 20px 0;
          display: flex; align-items: center; justify-content: center;
          cursor: pointer; transition: all 0.3s;"
            @click="refreshDeepFMRecommendation"
            @mouseenter="highlightButton($event, true)"
            @mouseleave="highlightButton($event, false)">
            <i class="el-icon-refresh" style="color: white; font-size: 18px; margin-right: 5px"></i>
            <span style="color: white; font-size: 14px">换一批</span>
          </div>
        </div>
        <div style="margin: 10px 5px 0 5px">
          <el-row :gutter="20">
            <!-- 修改字段映射，添加数据校验 -->
            <el-col
              :span="5"
              v-for="(item, index) in getCurrentDeepFMSlice()"
              :key="item.bookId + '-' + currentDeepFMPage"
              style="margin-bottom: 20px;"
            >
              <!-- 图片处理优化 -->
              <div class="book-card">
                <img
                  @mouseover="changeCursorStyle"
                  @mouseleave="resetCursorStyle"
                  @click="navTo(`/front/book-detail?id=${item.bookId}&coverImg=${encodeURIComponent(getBookCover(item))}`)"
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
        <div style="display: flex; align-items: center; margin: 40px 0 0 15px;">
          <div
            style="height: 40px; background-color: #04BF04FF; font-size: 20px;
          color: white; width: 130px; font-weight: bold;
          line-height: 40px; text-align: center;
          border-radius: 20px 0 0 20px;">
            NCF商品推荐
          </div>
          <!-- 刷新按钮 -->
          <div
            style="height: 40px; width: 100px; background-color: #04BF04FF;
          margin-left: 2px; border-radius: 0 20px 20px 0;
          display: flex; align-items: center; justify-content: center;
          cursor: pointer; transition: all 0.3s;"
            @click="refreshNCFRecommendation"
            @mouseenter="highlightButton($event, true)"
            @mouseleave="highlightButton($event, false)">
            <i class="el-icon-refresh" style="color: white; font-size: 18px; margin-right: 5px"></i>
            <span style="color: white; font-size: 14px">换一批</span>
          </div>
        </div>
        <div style="margin: 10px 5px 0 5px">
          <el-row :gutter="20">
            <!-- 修改字段映射，添加数据校验 -->
            <el-col
              :span="5"
              v-for="(item, index) in getCurrentNCFSlice()"
              :key="item.bookId + '-' + currentDeepFMPage"
              style="margin-bottom: 20px;"
            >
              <!-- 图片处理优化 -->
              <div class="book-card">
                <img
                  @mouseover="changeCursorStyle"
                  @mouseleave="resetCursorStyle"
                  @click="navTo(`/front/book-detail?id=${item.bookId}&coverImg=${encodeURIComponent(getBookCover(item))}`)"
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
      </div>
      <div class="right"></div>
    </div>
  </div>
</template>

<script>
import {listCategories} from "@/api/mall/categories";
import {listNotice, getNotice, delNotice, addNotice, updateNotice} from "@/api/system/notice";
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
      DeepFMBookList: [],
      NCFBookList: [],
      defaultCovers: [], // 存储默认封面列表
      maxDefaultCovers: 30, // 根据实际图片数量修改
      coverCache: new Map(),
      isDataStabilized: false,
      currentDeepFMPage: 0, // DeepFM当前页
      currentNCFPage: 0,    // NCF当前页
      pageSize: 10,         // 每页数量
      deepFMRecommendations: [], // 完整推荐列表
      ncfRecommendations: []     // 完整推荐列
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
  filters: {
    fixImgPath(value) {
      return value.startsWith('/profile')
        ? process.env.VUE_APP_BASE_API + value
        : value;
    }
  },
  methods: {
    // 生成确定性封面索引
    getStableCoverIndex(bookId) {
      if (!this.coverCache.has(bookId)) {
        const hash = this.simpleHash(bookId);
        const index = hash % this.defaultCovers.length;
        this.coverCache.set(bookId, index);
      }
      return this.coverCache.get(bookId);
    },
    // 简单哈希函数
    simpleHash(str) {
      let hash = 0;
      for (let i = 0; i < str.length; i++) {
        hash = (hash << 5) - hash + str.charCodeAt(i);
        hash |= 0; // 转换为32位整数
      }
      return Math.abs(hash);
    },
    // 动态加载封面
    loadDynamicCovers() {
      if (this.defaultCovers.length > 10) {
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
      if (item.img) return item.img;
      const index = this.getStableCoverIndex(item.bookId);
      return this.defaultCovers[index] || require('@/assets/default-book.png');
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
      return getUserProfile().then(response => {
        this.user = response.data;
        return response.data; // 返回用户数据以便链式调用
      });
    },
    // 带防抖的滚动处理
    handleScroll: _.debounce(function () {
      const {scrollTop, clientHeight, scrollHeight} = document.documentElement
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
      const [path, query] = url.split('?')
      const params = Object.fromEntries(new URLSearchParams(query))
      this.$router.push({
        path,
        query: params
      })
    },
    async loadData() {
      if (this.isDataStabilized) return;

      try {
        // 先获取用户信息
        await this.getUser();

        // 并行加载其他数据
        await Promise.all([
          this.getCategories(),
          this.getNotices(),
          this.getBooks(),
          this.getRecBooksDeepFM(),
          this.getRecBooksNCF()
        ]);

        this.isDataStabilized = true;
      } catch (error) {
        console.error("数据加载失败:", error);
      }
    },
    getRecBooksDeepFM() {
      this.loading = true;
      return listRecBooksDeepFM(this.user.userId).then(res => {
        if (res.code == '200') {
          this.DeepFMBookList = res.rows;
          this.deepFMRecommendations = res.rows;
        } else {
          this.$message.error(res.msg);
        }
      }).catch(error => {
        console.error("加载DeepFM推荐商品错误:", error);
        this.$message.error('加载推荐商品商品失败');
      });
    },
    getRecBooksNCF() {
      this.loading = true;
      return listRecBooksNeuralCF(this.user.userId).then(res => {
        if (res.code == '200') {
          this.NCFBookList = res.rows;
          this.ncfRecommendations = res.rows;
        } else {
          this.$message.error(res.msg);
        }
      }).catch(error => {
        console.error("加载NCF推荐商品错误:", error);
        this.$message.error('加载推荐商品商品失败');
      });
    },
    getBooks() {
      this.loading = true;
      return listBooks(this.queryParams).then(res => {
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
    getCategories() {
      this.loading = true;
      return listCategories(this.queryParams).then(res => {
        console.log(res);
        if (res.code == '200') {
          this.categoriesList = res.rows;
        } else {
          this.$message.error(res.msg);
        }
      }).catch(error => {
        console.error("加载商品目录错误:", error);
        this.$message.error('加载商品失败');
      });
    },
    getNotices() {
      return listNotice(this.queryParams).then(res => {
        this.noticeList = res.rows; // 修正字段名rows
        let i = 0;
        if (this.noticeList && this.noticeList.length) {
          // 清除旧定时器防止重复
          if (this.intervalId) clearInterval(this.intervalId);

          // 使用noticeContent字段
          this.topNotice = this.noticeList[0].noticeContent;

          this.intervalId = setInterval(() => {
            this.topNotice = `${this.noticeList[i].noticeTitle}：${this.noticeList[i].noticeContent}`;
            i = (i + 1) % this.noticeList.length; // 更简洁的循环逻辑
          }, 2500);
        }
      }).catch(error => {
        console.error("加载公告错误:", error);
        this.$message.error('加载公告失败');
      });
    },
    // 按钮悬停效果
    highlightButton(event, isEnter) {
      const btn = event.currentTarget
      if (isEnter) {
        btn.style.opacity = 0.8
        btn.style.transform = 'scale(1.05)'
      } else {
        btn.style.opacity = 1
        btn.style.transform = 'scale(1)'
      }
    },
    getCurrentDeepFMSlice() {
      const start = this.currentDeepFMPage * this.pageSize;
      const end = start + this.pageSize;
      return this.deepFMRecommendations.slice(start, end);
    },
    getCurrentNCFSlice() {
      const start = this.currentNCFPage * this.pageSize;
      const end = start + this.pageSize;
      return this.ncfRecommendations.slice(start, end);
    },
    // 刷新DeepFM推荐
    async refreshDeepFMRecommendation() {
      try {
        if (!this.user.userId) {
          this.$message.warning('请先登录')
          return this.$router.push('/login')
        }

        const res = await listRecBooksDeepFM(this.user.userId, {
          fresh: true // 添加刷新参数
        })

        if (res.code === 200) {
          this.DeepFMBookList = res.rows.slice(0, 10)
          this.$message.success('推荐内容已更新')
        }
      } catch (error) {
        this.$message.error('推荐刷新失败')
        console.error('DeepFM推荐刷新失败:', error)
      }
      this.currentDeepFMPage++;
      if ((this.currentDeepFMPage + 1) * this.pageSize > this.deepFMRecommendations.length) {
        this.currentDeepFMPage = 0;
        this.shuffleRecommendations('deepFM');
      }
    },

    shuffleRecommendations(type) {
      const list = type === 'deepFM' ? this.deepFMRecommendations : this.ncfRecommendations;
      const keepCount = Math.floor(list.length * 0.2);

      // Fisher-Yates 洗牌算法优化
      for (let i = keepCount; i < list.length; i++) {
        const j = Math.floor(Math.random() * (list.length - keepCount)) + keepCount;
        [list[i], list[j]] = [list[j], list[i]];
      }

      // 更新响应式数据
      if (type === 'deepFM') {
        this.deepFMRecommendations = [...list];
      } else {
        this.ncfRecommendations = [...list];
      }
    },

    // 刷新NCF推荐
    async refreshNCFRecommendation() {
      try {
        if (!this.user.userId) {
          this.$message.warning('请先登录')
          return this.$router.push('/login')
        }

        const res = await listRecBooksNeuralCF(this.user.userId, {
          fresh: true // 添加刷新参数
        })

        if (res.code === 200) {
          this.NCFBookList = res.rows.slice(0, 10)
          this.$message.success('推荐内容已更新')
        }
      } catch (error) {
        this.$message.error('推荐刷新失败')
        console.error('NCF推荐刷新失败:', error)
      }
      this.currentNCFPage++;
      if ((this.currentNCFPage + 1) * this.pageSize > this.ncfRecommendations.length) {
        this.currentNCFPage = 0;
        this.shuffleRecommendations('ncf');
      }
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
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

/* 分类列表优化 */
.category-wrapper {
  margin: 0 25px;
  border-radius: 8px;
  background: rgba(255, 255, 255, 0.95);
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
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
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08);
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


.el-col-5 {
  width: 20%;
  max-width: 20%;
  padding: 10px 10px;
}

/* 右侧用户面板优化 */
.user-panel {
  background: linear-gradient(135deg, #f8f9fa 0%, #f1f3f5 100%);
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.06);
}

/* 优化卡片样式 */
.book-card {
  padding: 10px;
  border-radius: 8px;
  transition: box-shadow 0.3s;
}

.book-card:hover {
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

/* 保持原有比例优化 */
.book-cover {
  width: 100%;
  height: 190px; /* 原175px基础上增加15px */
  object-fit: cover;
  border-radius: 12px; /* 增加圆角 */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); /* 添加投影 */
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

/* 添加加载旋转动画 */
@keyframes refresh-spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

.refreshing i.el-icon-refresh {
  animation: refresh-spin 1s linear infinite;
}

/* 按钮点击动效 */
.refresh-btn:active {
  transform: scale(0.95);
  opacity: 0.9;
}

/* 添加换场动画 */
.recommend-item {
  transition: all 0.5s cubic-bezier(0.25, 0.46, 0.45, 0.94);
}

.recommend-enter-active, .recommend-leave-active {
  transition: opacity 0.5s, transform 0.5s;
}

.recommend-enter {
  opacity: 0;
  transform: translateY(20px);
}

.recommend-leave-to {
  opacity: 0;
  transform: translateY(-20px);
}
</style>

<template>
  <div class="main-content">
    <div class="book-detail-container">
      <!-- 商品主体信息 -->
      <div class="book-main-panel">
        <el-row :gutter="30">
          <!-- 封面及评分 -->
          <el-col :span="12">
            <div class="book-media">
              <div class="book-cover-wrapper">
                <img
                  :src="coverImg || require('@/assets/default-book.png')"
                  alt="图书封面"
                  class="book-cover"
                  @error="handleCoverError"
                >
                <div class="rating-overlay">
                  <div class="rating-score">{{ bookData.bookAvgRating }}</div>
                  <el-rate
                    v-model="bookData.bookAvgRating"
                    :max="5"
                    disabled
                    :colors="['#ffd21d', '#ffd21d', '#ffd21d']"
                    text-color="#ffd21d"
                  />
                  <div class="rating-count">{{ bookData.bookRatingCount }}人评分</div>
                </div>
              </div>
            </div>
          </el-col>

          <!-- 商品信息 -->
          <el-col :span="12">
            <div class="book-info">
              <h1 class="book-title">{{ bookData.title }}</h1>

              <!-- 元信息 -->
              <div class="meta-grid">
                <div class="meta-item">
                  <i class="el-icon-user"></i>
                  <span>{{ bookData.authors || '未知作者' }}</span>
                </div>
                <div class="meta-item">
                  <i class="el-icon-date"></i>
                  <span>出版 {{ bookData.pubYear }}</span>
                </div>
                <div class="meta-item">
                  <i class="el-icon-document"></i>
                  <span>{{ bookData.pageCount || 0 }}页</span>
                </div>
              </div>

              <!-- 价格库存 -->
              <div class="price-panel">
                <div class="current-price">
                  <span class="price-symbol">￥</span>
                  <span class="price-number">{{ bookData.price.toFixed(2) }}</span>
                  <span class="price-unit">/{{ bookData.units }}</span>
                </div>
                <div class="inventory-status">
                  <el-tag
                    type="success"
                    effect="dark"
                    v-if="bookData.inventory > 0"
                  >
                    库存 {{ bookData.inventory }}{{ bookData.units }}
                  </el-tag>
                  <el-tag
                    type="danger"
                    effect="dark"
                    v-else
                  >
                    暂时缺货
                  </el-tag>
                </div>
              </div>

              <!-- 标签云 -->
              <div class="tag-cloud">
                <el-tag
                  v-for="tag in bookData.mallBookTagsList"
                  :key="tag.tagId"
                  type="info"
                  effect="dark"
                  class="book-tag"
                >
                  {{ getTagName(tag.tagId) }}
                </el-tag>
              </div>

              <!-- 操作按钮 -->
              <div class="action-buttons">
                <el-button
                  type="warning"
                  icon="el-icon-shopping-cart-full"
                  class="action-btn"
                  @click="addCartList(bookData)"
                >
                  加入购物车
                </el-button>
                <el-button
                  type="danger"
                  icon="el-icon-star-on"
                  class="action-btn"
                  @click="addCollectList(bookData)"
                >
                  收藏商品
                </el-button>
              </div>
            </div>
          </el-col>
        </el-row>

        <!-- 详细信息区域 -->
        <el-tabs v-model="activeName" class="detail-tabs">
          <!-- 商品详情 -->
          <el-tab-pane label="商品详情" name="detail">
            <div
              class="description-content"
              v-if="bookData.description"
              v-html="bookData.description"
            ></div>
            <div v-else class="no-description">
              <i class="el-icon-picture-outline"></i>
              <p>暂无商品描述</p>
            </div>
          </el-tab-pane>

          <!-- 用户评价 -->
          <el-tab-pane label="用户评价" name="comment">
            <div class="comment-section">
              <div
                v-for="(comment, index) in commentData"
                :key="index"
                class="comment-card"
              >
                <div class="user-info">
                  <el-avatar
                    :src="comment.userAvatar || require('@/assets/default-avatar.png')"
                    class="user-avatar"
                  />
                  <div class="user-meta">
                    <div class="username">用户{{ comment.userId }}</div>
                    <div class="comment-time">{{ formatDate(comment.time) }}</div>
                  </div>
                </div>
                <div class="comment-content">
                  <p v-if="comment.content" class="comment-text">{{ comment.content }}</p>
                  <p v-else class="no-content">该用户没有填写评价内容</p>
                </div>
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<script>
import {getBooks, listBookTags} from "@/api/mall/books";
import { getSingleBookComment } from "@/api/mall/comment";
import {getUserProfile} from "@/api/system/user";
import {addCollect} from "@/api/mall/collect";
import {addCart} from "@/api/mall/cart";

export default {
  name: "FrontDetail",
  data() {
    return {
      bookId: this.$route.query.id,
      coverImg: decodeURIComponent(this.$route.query.coverImg || ''),
      user: {},
      bookData: {},
      activeName: 'detail',
      commentData: [],
      tagMap: {},
    }
  },
  created() {
    this.getUser();
    this.loadData();
  },
  methods: {
    getUser() {
      return getUserProfile().then(response => {
        this.user = response.data;
        return response.data; // 返回用户数据以便链式调用
      });
    },
    async loadData() {
      try {
        const [bookRes, commentRes, tagsRes] = await Promise.all([
          getBooks(this.bookId),
          getSingleBookComment(this.bookId),
          listBookTags(this.bookId)
        ]);

        if (bookRes.code === 200) {
          this.bookData = bookRes.data;
          // 封面图优先级处理
          if (!this.coverImg && this.bookData.img) {
            this.coverImg = this.bookData.img;
          }
        }

        if (commentRes.code === 200) {
          this.commentData = commentRes.rows;
        }

        if (tagsRes.code === 200) {
          this.tagMap  = tagsRes.data.reduce((acc, tag) => {
            acc[tag.tagId] = tag.tagName;
            return acc;
          }, {});
        }
      } catch (error) {
        this.$message.error('数据加载失败');
      }
    },
    getTagName(tagId) {
      return this.tagMap[tagId] || `标签#${tagId}`;
    },
    formatDate(dateStr) {
      return dateStr ? new Date(dateStr).toLocaleDateString('zh-CN') : '未知时间';
    },
    handleCoverError(e) {
      e.target.src = require('@/assets/default-book.png');
    },
    async addCollectList() {
      if (!this.user.userId) {
        this.$message.warning('请先登录');
        return this.$router.push('/login');
      }

      try {
        const res = await addCollect({
          userId: this.user.userId,
          bookId: this.bookId
        });

        if (res.code === 200) {
          this.$message.success('收藏成功');
        }
      } catch (error) {
        this.$message.error('收藏失败');
      }
    },
    async addCartList() {
      if (!this.user.userId) {
        this.$message.warning('请先登录');
        return this.$router.push('/login');
      }

      try {
        const res = await addCart({
          userId: this.user.userId,
          bookId: this.bookId,
          num: 1 // 默认数量或从弹窗获取
        });

        if (res.code === 200) {
          this.$message.success('已加入购物车');
        }
      } catch (error) {
        this.$message.error('操作失败');
      }
    },
  }
}
</script>

<style scoped>
.book-detail-container {
  max-width: 1200px;
  margin: 20px auto;
  background: #fff;
  border-radius: 15px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.08);
}

.book-media {
  position: relative;
  padding: 30px;
}

.book-cover {
  width: 100%;
  height: 500px;
  object-fit: contain;
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0,0,0,0.12);
}

.rating-overlay {
  position: absolute;
  bottom: 40px;
  left: 40px;
  background: rgba(255,255,255,0.95);
  padding: 20px 25px;
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
}

.rating-score {
  font-size: 36px;
  font-weight: 700;
  color: #f7b500;
  line-height: 1;
  margin-bottom: 10px;
}

.book-info {
  padding: 30px;
}

.book-title {
  font-size: 32px;
  color: #2d3436;
  margin: 0 0 25px;
  line-height: 1.3;
  letter-spacing: 0.5px;
}

.meta-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 15px;
  margin: 25px 0;
}

.meta-item {
  display: flex;
  align-items: center;
  padding: 12px;
  background: #f8f9fa;
  border-radius: 8px;
  font-size: 15px;
}

.meta-item i {
  margin-right: 8px;
  color: #6c757d;
}

.price-panel {
  margin: 30px 0;
  padding: 25px;
  background: linear-gradient(135deg, #fff9f0 0%, #fff3e0 100%);
  border-radius: 12px;
}

.current-price {
  display: flex;
  align-items: baseline;
  margin-bottom: 15px;
}

.price-symbol {
  font-size: 24px;
  color: #ff6b6b;
}

.price-number {
  font-size: 42px;
  color: #ff6b6b;
  font-weight: 800;
  margin: 0 5px;
}

.tag-cloud {
  margin: 25px 0;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.book-tag {
  border-radius: 15px;
  padding: 8px 18px;
  font-size: 14px;
}

.action-buttons {
  margin-top: 35px;
  display: flex;
  gap: 20px;
}

.action-btn {
  padding: 15px 30px;
  font-size: 16px;
  border-radius: 25px;
}

.detail-tabs {
  margin: 40px 30px 0;
}

.comment-card {
  padding: 20px;
  margin-bottom: 20px;
  background: #f8f9fa;
  border-radius: 12px;
  transition: all 0.3s;
}

.comment-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 15px rgba(0,0,0,0.08);
}

.user-info {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.user-avatar {
  width: 50px;
  height: 50px;
  margin-right: 15px;
}

.comment-text {
  color: #4a4a4a;
  line-height: 1.7;
  font-size: 15px;
}

.no-content {
  color: #95a5a6;
  font-style: italic;
}
</style>

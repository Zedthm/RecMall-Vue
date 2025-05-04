<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="书籍标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入书籍标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者列表" prop="authors">
        <el-input
          v-model="queryParams.authors"
          placeholder="请输入作者列表"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出版年份" prop="pubYear">
        <el-input
          v-model="queryParams.pubYear"
          placeholder="请输入出版年份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="平均评分" prop="bookAvgRating">
        <el-input
          v-model="queryParams.bookAvgRating"
          placeholder="请输入平均评分"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评分标准差" prop="bookRatingStd">
        <el-input
          v-model="queryParams.bookRatingStd"
          placeholder="请输入评分标准差"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评分人数" prop="bookRatingCount">
        <el-input
          v-model="queryParams.bookRatingCount"
          placeholder="请输入评分人数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="页数" prop="pageCount">
        <el-input
          v-model="queryParams.pageCount"
          placeholder="请输入页数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="定价" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="请输入定价"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="销售单位" prop="units">
        <el-input
          v-model="queryParams.units"
          placeholder="请输入销售单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="当前库存量" prop="inventory">
        <el-input
          v-model="queryParams.inventory"
          placeholder="请输入当前库存量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="归属商家名称" prop="merchant">
        <el-input
          v-model="queryParams.merchant"
          placeholder="请输入归属商家名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['mall:books:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['mall:books:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['mall:books:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mall:books:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="booksList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="书籍唯一ID" align="center" prop="bookId" />
      <el-table-column label="书籍标题" align="center" prop="title" />
      <el-table-column label="作者列表" align="center" prop="authors" />
      <el-table-column label="出版年份" align="center" prop="pubYear" />
      <el-table-column label="平均评分" align="center" prop="bookAvgRating" />
      <el-table-column label="评分标准差" align="center" prop="bookRatingStd" />
      <el-table-column label="评分人数" align="center" prop="bookRatingCount" />
      <el-table-column label="页数" align="center" prop="pageCount" />
      <el-table-column label="定价" align="center" prop="price" />
      <el-table-column label="封面图片URL" align="center" prop="img" />
      <el-table-column label="详细描述" align="center" prop="description" />
      <el-table-column label="销售单位" align="center" prop="units" />
      <el-table-column label="当前库存量" align="center" prop="inventory" />
      <el-table-column label="归属商家名称" align="center" prop="merchant" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mall:books:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mall:books:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改商品信息-书籍核心数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="书籍标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入书籍标题" />
        </el-form-item>
        <el-form-item label="作者列表" prop="authors">
          <el-input v-model="form.authors" placeholder="请输入作者列表" />
        </el-form-item>
        <el-form-item label="出版年份" prop="pubYear">
          <el-input v-model="form.pubYear" placeholder="请输入出版年份" />
        </el-form-item>
        <el-form-item label="平均评分" prop="bookAvgRating">
          <el-input v-model="form.bookAvgRating" placeholder="请输入平均评分" />
        </el-form-item>
        <el-form-item label="评分标准差" prop="bookRatingStd">
          <el-input v-model="form.bookRatingStd" placeholder="请输入评分标准差" />
        </el-form-item>
        <el-form-item label="评分人数" prop="bookRatingCount">
          <el-input v-model="form.bookRatingCount" placeholder="请输入评分人数" />
        </el-form-item>
        <el-form-item label="页数" prop="pageCount">
          <el-input v-model="form.pageCount" placeholder="请输入页数" />
        </el-form-item>
        <el-form-item label="定价" prop="price">
          <el-input v-model="form.price" placeholder="请输入定价" />
        </el-form-item>
        <el-form-item label="封面图片URL">
          <editor v-model="form.img" :min-height="192"/>
        </el-form-item>
        <el-form-item label="详细描述">
          <editor v-model="form.description" :min-height="192"/>
        </el-form-item>
        <el-form-item label="销售单位" prop="units">
          <el-input v-model="form.units" placeholder="请输入销售单位" />
        </el-form-item>
        <el-form-item label="当前库存量" prop="inventory">
          <el-input v-model="form.inventory" placeholder="请输入当前库存量" />
        </el-form-item>
        <el-form-item label="归属商家名称" prop="merchant">
          <el-input v-model="form.merchant" placeholder="请输入归属商家名称" />
        </el-form-item>
        <el-divider content-position="center">书籍与标签的关联关系信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddMallBookTags">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteMallBookTags">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="mallBookTagsList" :row-class-name="rowMallBookTagsIndex" @selection-change="handleMallBookTagsSelectionChange" ref="mallBookTags">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBooks, getBooks, delBooks, addBooks, updateBooks } from "@/api/mall/books";

export default {
  name: "Books",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedMallBookTags: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 商品信息-书籍核心数据表格数据
      booksList: [],
      // 书籍与标签的关联关系表格数据
      mallBookTagsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        authors: null,
        pubYear: null,
        bookAvgRating: null,
        bookRatingStd: null,
        bookRatingCount: null,
        pageCount: null,
        price: null,
        img: null,
        description: null,
        units: null,
        inventory: null,
        merchant: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          { required: true, message: "书籍标题不能为空", trigger: "blur" }
        ],
        price: [
          { required: true, message: "定价不能为空", trigger: "blur" }
        ],
        units: [
          { required: true, message: "销售单位不能为空", trigger: "blur" }
        ],
        inventory: [
          { required: true, message: "当前库存量不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询商品信息-书籍核心数据列表 */
    getList() {
      this.loading = true;
      listBooks(this.queryParams).then(response => {
        this.booksList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        bookId: null,
        title: null,
        authors: null,
        pubYear: null,
        bookAvgRating: null,
        bookRatingStd: null,
        bookRatingCount: null,
        pageCount: null,
        price: null,
        img: null,
        description: null,
        units: null,
        inventory: null,
        merchant: null
      };
      this.mallBookTagsList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.bookId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加商品信息-书籍核心数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const bookId = row.bookId || this.ids
      getBooks(bookId).then(response => {
        this.form = response.data;
        this.mallBookTagsList = response.data.mallBookTagsList;
        this.open = true;
        this.title = "修改商品信息-书籍核心数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.mallBookTagsList = this.mallBookTagsList;
          if (this.form.bookId != null) {
            updateBooks(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBooks(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const bookIds = row.bookId || this.ids;
      this.$modal.confirm('是否确认删除商品信息-书籍核心数据编号为"' + bookIds + '"的数据项？').then(function() {
        return delBooks(bookIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 书籍与标签的关联关系序号 */
    rowMallBookTagsIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 书籍与标签的关联关系添加按钮操作 */
    handleAddMallBookTags() {
      let obj = {};
      this.mallBookTagsList.push(obj);
    },
    /** 书籍与标签的关联关系删除按钮操作 */
    handleDeleteMallBookTags() {
      if (this.checkedMallBookTags.length == 0) {
        this.$modal.msgError("请先选择要删除的书籍与标签的关联关系数据");
      } else {
        const mallBookTagsList = this.mallBookTagsList;
        const checkedMallBookTags = this.checkedMallBookTags;
        this.mallBookTagsList = mallBookTagsList.filter(function(item) {
          return checkedMallBookTags.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleMallBookTagsSelectionChange(selection) {
      this.checkedMallBookTags = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mall/books/export', {
        ...this.queryParams
      }, `books_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

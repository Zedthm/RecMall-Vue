<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="平均评分" prop="userAvgRating">
        <el-input
          v-model="queryParams.userAvgRating"
          placeholder="请输入平均评分"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评分标准差" prop="userRatingStd">
        <el-input
          v-model="queryParams.userRatingStd"
          placeholder="请输入评分标准差"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="一级兴趣标签" prop="userTag1">
        <el-input
          v-model="queryParams.userTag1"
          placeholder="请输入一级兴趣标签"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="二级兴趣标签" prop="userTag2">
        <el-input
          v-model="queryParams.userTag2"
          placeholder="请输入二级兴趣标签"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="三级兴趣标签" prop="userTag3">
        <el-input
          v-model="queryParams.userTag3"
          placeholder="请输入三级兴趣标签"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="四级兴趣标签" prop="userTag4">
        <el-input
          v-model="queryParams.userTag4"
          placeholder="请输入四级兴趣标签"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="五级兴趣标签" prop="userTag5">
        <el-input
          v-model="queryParams.userTag5"
          placeholder="请输入五级兴趣标签"
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
          v-hasPermi="['mall:profiles:add']"
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
          v-hasPermi="['mall:profiles:edit']"
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
          v-hasPermi="['mall:profiles:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mall:profiles:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="profilesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="平均评分" align="center" prop="userAvgRating" />
      <el-table-column label="评分标准差" align="center" prop="userRatingStd" />
      <el-table-column label="有效评分次数" align="center" prop="userRatingCount" />
      <el-table-column label="一级兴趣标签" align="center" prop="userTag1" />
      <el-table-column label="二级兴趣标签" align="center" prop="userTag2" />
      <el-table-column label="三级兴趣标签" align="center" prop="userTag3" />
      <el-table-column label="四级兴趣标签" align="center" prop="userTag4" />
      <el-table-column label="五级兴趣标签" align="center" prop="userTag5" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mall:profiles:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mall:profiles:remove']"
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

    <!-- 添加或修改用户画像及评分数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="平均评分" prop="userAvgRating">
          <el-input v-model="form.userAvgRating" placeholder="请输入平均评分" />
        </el-form-item>
        <el-form-item label="评分标准差" prop="userRatingStd">
          <el-input v-model="form.userRatingStd" placeholder="请输入评分标准差" />
        </el-form-item>
        <el-form-item label="一级兴趣标签" prop="userTag1">
          <el-input v-model="form.userTag1" placeholder="请输入一级兴趣标签" />
        </el-form-item>
        <el-form-item label="二级兴趣标签" prop="userTag2">
          <el-input v-model="form.userTag2" placeholder="请输入二级兴趣标签" />
        </el-form-item>
        <el-form-item label="三级兴趣标签" prop="userTag3">
          <el-input v-model="form.userTag3" placeholder="请输入三级兴趣标签" />
        </el-form-item>
        <el-form-item label="四级兴趣标签" prop="userTag4">
          <el-input v-model="form.userTag4" placeholder="请输入四级兴趣标签" />
        </el-form-item>
        <el-form-item label="五级兴趣标签" prop="userTag5">
          <el-input v-model="form.userTag5" placeholder="请输入五级兴趣标签" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProfiles, getProfiles, delProfiles, addProfiles, updateProfiles } from "@/api/mall/profiles";

export default {
  name: "Profiles",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户画像及评分数据表格数据
      profilesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userAvgRating: null,
        userRatingStd: null,
        userRatingCount: null,
        userTag1: null,
        userTag2: null,
        userTag3: null,
        userTag4: null,
        userTag5: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userAvgRating: [
          { required: true, message: "平均评分不能为空", trigger: "blur" }
        ],
        userRatingCount: [
          { required: true, message: "有效评分次数不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询用户画像及评分数据列表 */
    getList() {
      this.loading = true;
      listProfiles(this.queryParams).then(response => {
        this.profilesList = response.rows;
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
        userId: null,
        userAvgRating: null,
        userRatingStd: null,
        userRatingCount: null,
        userTag1: null,
        userTag2: null,
        userTag3: null,
        userTag4: null,
        userTag5: null
      };
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
      this.ids = selection.map(item => item.userId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户画像及评分数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const userId = row.userId || this.ids
      getProfiles(userId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户画像及评分数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.userId != null) {
            updateProfiles(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProfiles(this.form).then(response => {
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
      const userIds = row.userId || this.ids;
      this.$modal.confirm('是否确认删除用户画像及评分数据编号为"' + userIds + '"的数据项？').then(function() {
        return delProfiles(userIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mall/profiles/export', {
        ...this.queryParams
      }, `profiles_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入分类名称查询" style="width: 200px" v-model="name"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="name" label="分类名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="description" label="分类描述" show-overflow-tooltip></el-table-column>
        <el-table-column prop="img" label="分类图标">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px; border-radius: 50%" v-if="scope.row.img"
                        :src="scope.row.img" :preview-src-list="[scope.row.img]"></el-image>
              <el-image v-else src="path/to/placeholder.png" style="width: 40px; height: 40px; border-radius: 50%"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="primary" @click="handleEdit(scope.row)" size="mini">编辑</el-button>
            <el-button plain type="danger" size="mini" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total">
        </el-pagination>
      </div>
    </div>

    <el-dialog title="分类信息" :visible.sync="fromVisible" @close="resetForm" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="name" label="分类名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="description" label="分类描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="分类图标">
          <el-upload
              class="img-uploader"
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: user.token }"
              list-type="picture"
              :on-success="handleAvatarSuccess"
          >
            <el-button type="primary">上传分类图标</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "ManagerCategory",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,     // 当前的页码
      pageSize: 10,   // 每页显示的个数
      total: 0,
      name: null,     // 分类名称
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [
          { required: true, message: '请输入分类名称', trigger: 'blur' },
        ],
        description: [
          { required: true, message: '请输入分类描述', trigger: 'blur' },
        ]
      },
      ids: [],
      loading: false,  // 加载状态
    }
  },
  created() {
    this.load(1);
  },
  methods: {
    handleAdd() {
      this.resetForm(); // 清空数据
      this.fromVisible = true; // 打开弹窗
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row)); // 深拷贝数据
      this.fromVisible = true; // 打开弹窗
    },
    async save() {
      try {
        const valid = await this.$refs.formRef.validate();
        if (valid) {
          const res = await this.$request({
            url: this.form.id ? '/category/update' : '/category/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          });
          if (res.code === '200') {
            this.$message.success('保存成功');
            this.load(1);
            this.fromVisible = false;
          } else {
            this.$message.error(res.msg);
          }
        }
      } catch (error) {
        this.$message.error('保存失败，请检查输入');
      }
    },
    async del(id) {
      // 弹出确认框
      this.$confirm('确定要移除这个商品吗？', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
          .then(async () => {
            try {
              const res = await this.$request.delete(`/cart/delete/${id}`);
              if (res.code === '200') {
                this.$message.success('移除成功');
                this.load(1); // 重新加载数据
              } else {
                this.$message.error(res.msg); // 处理错误提示
              }
            } catch (error) {
              this.$message.error('删除失败，请检查网络连接'); // 处理异常情况
            }
          })
          .catch(() => {
            // 用户取消了删除操作，不做任何事情
            this.$message.info('已取消删除操作');
          });
    },
    handleSelectionChange(rows) {
      this.ids = rows.map(v => v.id);
    },
    async delBatch() {
      if (!this.ids.length) {
        this.$message.warning('请选择数据');
        return;
      }
      try {
        await this.$confirm('您确定批量删除这些数据吗？', '确认删除', { type: "warning" });
        const res = await this.$request.delete('/category/delete/batch', { data: this.ids });
        if (res.code === '200') {
          this.$message.success('操作成功');
          this.load(1);
        } else {
          this.$message.error(res.msg);
        }
      } catch (error) {
        // 处理确认取消或删除错误
      }
    },
    async load(pageNum) {
      if (pageNum) this.pageNum = pageNum;
      this.loading = true; // 开始加载
      try {
        const res = await this.$request.get('/category/selectPage', {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name, // 使用 name 进行查询
          }
        });
        this.tableData = res.data?.list || [];
        this.total = res.data?.total || 0;
      } catch (error) {
        this.$message.error('加载数据失败，请稍后重试');
      } finally {
        this.loading = false; // 结束加载
      }
    },
    reset() {
      this.name = null; // 重置分类名称
      this.load(1); // 重新加载数据
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum);
    },
    handleAvatarSuccess(response, file, fileList) {
      this.form.img = response.data;
    },
    resetForm() {
      this.form = {}; // 重置表单数据
    }
  }
}
</script>

<style scoped>
/* 可以在此添加样式 */
</style>

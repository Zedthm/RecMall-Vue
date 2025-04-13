<template>
  <div class="main-content">
    <div style="width: 70%; background-color: white; margin: 30px auto; border-radius: 20px">
      <div style="padding-bottom: 10px">
        <div style="display: flex; font-size: 18px; color: #000000FF; line-height: 80px; border-bottom: #cccccc 1px solid;">
          <div style="flex: 3; margin-left: 20px">我的地址</div>
          <div style="flex: 1; text-align: right; padding-right: 20px">
            <el-button type="warning" round @click="addAddress">添加收货地址</el-button>
          </div>
        </div>
        <div style="margin: 20px 0; padding: 0 50px">
          <div class="table">
            <el-table :data="addressData" strip>
              <el-table-column prop="username" label="收货人" width="350px"></el-table-column>
              <el-table-column prop="userAddress" label="收货地址"></el-table-column>
              <el-table-column prop="phone" label="联系电话"></el-table-column>
              <el-table-column label="操作" align="center" width="180">
                <template v-slot="scope">
                  <el-button size="mini" type="primary" plain @click="editAddress(scope.row)">编辑</el-button>
                  <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>

            <div class="pagination" style="margin-top: 20px">
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
        </div>
      </div>
    </div>
    <el-dialog title="地址信息" :visible.sync="formVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username" label="收货人">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="userAddress" label="收货地址">
          <el-input v-model="form.userAddress" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="phone" label="联系电话">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="formVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      addressData: [],
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      formVisible: false,
      form: {
        username: '',
        userAddress: '',
        phone: '',
      },
      rules: {
        username: [
          {required: true, message: '请输入收货人', trigger: 'blur'},
        ],
        userAddress: [
          {required: true, message: '请输入收货地址', trigger: 'blur'},
        ],
        phone: [
          {required: true, message: '请输入联系电话', trigger: 'blur'},
        ],
      },
    };
  },
  mounted() {
    this.loadAddress(1);
  },
  methods: {
    goToProfile(url) {
      if (!this.user.id) {
        this.$message.warning('请先登录');
        this.navTo('/login');
      } else {
        this.navTo(url);
      }
    },
    navTo(url) {
      location.href = url;
    },
    resetForm() {
      this.form = {
        username: '',
        userAddress: '',
        phone: '',
      };
    },
    addAddress() {
      this.resetForm();
      this.formVisible = true;
    },
    editAddress(row) {
      this.form = {...row}; // 浅拷贝 row 对象
      this.formVisible = true;
    },
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.form.userId = this.user.id;
          this.$request({
            url: this.form.id ? '/address/update' : '/address/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form,
          }).then(res => {
            if (res.code === '200') {
              this.$message.success('保存成功');
              this.loadAddress(1);
              this.formVisible = false;
            } else {
              this.$message.error(res.msg);
            }
          }).catch(() => {
            this.$message.error('请求失败，请检查网络连接');
          });
        }
      });
    },
    loadAddress(pageNum) {
      if (pageNum) this.pageNum = pageNum;
      this.$request.get('/address/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        },
      }).then(res => {
        if (res.code === '200') {
          this.addressData = res.data?.list;
          this.total = res.data?.total;
        } else {
          this.$message.error(res.msg);
        }
      }).catch(() => {
        this.$message.error('请求失败，请检查网络连接');
      });
    },
    del(id) {
      this.$confirm('确定要删除这个地址吗？', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
          .then(async () => {
            try {
              const res = await this.$request.delete('/address/delete/' + id);
              if (res.code === '200') {
                this.$message.success('删除成功');
                this.loadAddress(1); // 重新加载地址
              } else {
                this.$message.error(res.msg); // 显示错误信息
              }
            } catch (error) {
              this.$message.error('请求失败，请检查网络连接'); // 捕获并处理请求错误
            }
          })
          .catch(() => {
            // 用户取消删除操作，不做任何事情
            this.$message.info('已取消删除操作');
          });
    },
    handleCurrentChange(pageNum) {
      this.loadAddress(pageNum);
    },
  },
};
</script>
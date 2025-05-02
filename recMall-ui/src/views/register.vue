<template>
  <div class="register">
    <el-form ref="registerForm" :model="registerForm" :rules="registerRules" class="register-form">
      <h3 class="title">{{title}}</h3>
      <el-form-item prop="username">
        <el-input v-model="registerForm.username" type="text" auto-complete="off" placeholder="账号">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="registerForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter.native="handleRegister"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="confirmPassword">
        <el-input
          v-model="registerForm.confirmPassword"
          type="password"
          auto-complete="off"
          placeholder="确认密码"
          @keyup.enter.native="handleRegister"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="code" v-if="captchaEnabled">
        <el-input
          v-model="registerForm.code"
          auto-complete="off"
          placeholder="验证码"
          style="width: 63%"
          @keyup.enter.native="handleRegister"
        >
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <div class="register-code">
          <img :src="codeUrl" @click="getCode" class="register-code-img"/>
        </div>
      </el-form-item>

      <!-- 注册方式切换 -->
      <el-radio-group v-model="registerForm.registerType">
        <el-radio-button label="phonenumber">手机注册</el-radio-button>
        <el-radio-button label="email">邮箱注册</el-radio-button>
      </el-radio-group>

      <!-- 手机号注册 -->
      <div v-if="registerForm.registerType === 'phonenumber'">
        <el-form-item prop="phonenumber">
          <el-input v-model="registerForm.phonenumber" placeholder="请输入手机号">
            <template slot="prepend">+86</template>
          </el-input>
        </el-form-item>
        <el-form-item prop="smsCode">
          <el-input v-model="registerForm.smsCode" placeholder="短信验证码" style="width: 80%">
            <el-button
              slot="append"
              :disabled="smsCountdown > 0"
              @click="sendSmsCode">
              {{ smsCountdown > 0 ? `${smsCountdown}s后重试` : '获取验证码' }}
            </el-button>
          </el-input>
        </el-form-item>
      </div>

      <!-- 邮箱注册 -->
      <div v-else>
        <el-form-item prop="email">
          <el-input v-model="registerForm.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item prop="emailCode">
          <el-input v-model="registerForm.emailCode" placeholder="邮箱验证码" style="width: 80%">
            <el-button
              slot="append"
              :disabled="emailCountdown > 0"
              @click="sendEmailCode">
              {{ emailCountdown > 0 ? `${emailCountdown}s后重试` : '获取验证码' }}
            </el-button>
          </el-input>
        </el-form-item>
      </div>

      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleRegister"
        >
          <span v-if="!loading">注 册</span>
          <span v-else>注 册 中...</span>
        </el-button>
        <div style="float: right;">
          <router-link class="link-type" :to="'/login'">使用已有账户登录</router-link>
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-register-footer">
      <span>Copyright © 2018-2025 ruoyi.vip All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
import { getCodeImg, register, getSmsOrEmailCode } from "@/api/login";

export default {
  name: "Register",
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.registerForm.password !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      title: process.env.VUE_APP_TITLE,
      codeUrl: "",
      registerForm: {
        registerType: '',
        phonenumber: '',
        email: '',
        smsCode: '',
        emailCode: '',
        username: "",
        password: "",
        confirmPassword: "",
        code: "",
        uuid: ""
      },
      smsCountdown: 0,
      emailCountdown: 0,
      registerRules: {
        phonenumber: [
          { required: true, message: '手机号不能为空' },
          { pattern: /^1[3-9]\d{9}$/, message: '手机号格式错误' }
        ],
        email: [
          { required: true, message: '邮箱不能为空' },
          { type: 'email', message: '邮箱格式不正确' }
        ],
        smsCode: [
          { required: true, message: '验证码不能为空' },
          { pattern: /^\d{6}$/, message: '6位数字验证码' }
        ],
        emailCode: [
          { required: true, message: '验证码不能为空' },
          { pattern: /^\d{6}$/, message: '6位数字验证码' }
        ],
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" },
          { min: 2, max: 20, message: '用户账号长度必须介于 2 和 20 之间', trigger: 'blur' }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" },
          { min: 5, max: 20, message: "用户密码长度必须介于 5 和 20 之间", trigger: "blur" },
          { pattern: /^[^<>"'|\\]+$/, message: "不能包含非法字符：< > \" ' \\\ |", trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, trigger: "blur", message: "请再次输入您的密码" },
          { required: true, validator: equalToPassword, trigger: "blur" }
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      captchaEnabled: true
    };
  },
  created() {
    this.getCode();
  },
  methods: {
    // 发送短信验证码 (Promise链式调用风格)
    sendSmsCode() {
      this.$refs.registerForm.validateField('phonenumber', errorMessage => {
        if (errorMessage) { // 有错误信息时返回
          this.$message.warning('请填写正确的手机号')
          return
        }

        const loading = this.$loading({
          lock: true,
          text: '发送中...',
          spinner: 'el-icon-loading'
        })

        getSmsOrEmailCode({
          type: 'phonenumber',
          account: this.registerForm.phonenumber
        }).then(response => {
          if (response.code === 200) {
            this.$message.success('验证码已发送')
            this.startSmsCountdown()
          } else {
            this.$message.error(response.msg)
          }
        }).catch(error => {
          this.handleSendError(error, 'sms')
        }).finally(() => {
          loading.close()
        })
      })
    },

    // 发送邮箱验证码 (统一风格)
    sendEmailCode() {
      this.$refs.registerForm.validateField('email', errorMessage => {
        if (errorMessage) { // 有错误信息时返回
          this.$message.warning('请填写正确的邮箱')
          return
        }

        const loading = this.$loading({
          lock: true,
          text: '发送中...',
          spinner: 'el-icon-loading'
        })

        getSmsOrEmailCode({
          type: 'email',
          account: this.registerForm.email
        }).then(response => {
          if (response.code === 200) {
            this.$message.success('验证码已发送至邮箱')
            this.startEmailCountdown()
          } else {
            this.$message.error(response.msg)
          }
        }).catch(error => {
          this.handleSendError(error, 'email')
        }).finally(() => {
          loading.close()
        })
      })
    },

    // 统一错误处理方法
    handleSendError(error, type) {
      const typeMap = {
        sms: { serviceName: '短信', networkError: '短信网关' },
        email: { serviceName: '邮件', networkError: '邮件服务器' }
      }

      if (error.response) {
        console.error(`${typeMap[type].serviceName}服务异常:`, error.response.data)
        this.$message.error(`发送失败: ${error.response.data.message || '服务异常'}`)
      } else if (error.request) {
        console.error('请求未响应:', error.request)
        this.$message.error(`无法连接${typeMap[type].networkError}`)
      } else {
        console.error('系统错误:', error.message)
        this.$message.error(`${typeMap[type].serviceName}发送系统错误`)
      }
    },
    // 倒计时处理
    startSmsCountdown() {
      this.smsCountdown = 60;
      const timer = setInterval(() => {
        this.smsCountdown--;
        if (this.smsCountdown <= 0) {
          clearInterval(timer);
        }
      }, 1000);
    },

    startEmailCountdown() {
      this.emailCountdown = 60;
      const timer = setInterval(() => {
        this.emailCountdown--;
        if (this.emailCountdown <= 0) {
          clearInterval(timer);
        }
      }, 1000);
    },

    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.registerForm.uuid = res.uuid;
        }
      });
    },
    handleRegister() {
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          this.loading = true;
          register(this.registerForm).then(res => {
            const username = this.registerForm.username;
            this.$alert("<font color='red'>恭喜你，您的账号 " + username + " 注册成功！</font>", '系统提示', {
              dangerouslyUseHTMLString: true,
              type: 'success'
            }).then(() => {
              this.$router.push("/login");
            }).catch(() => {});
          }).catch(() => {
            this.loading = false;
            if (this.captchaEnabled) {
              this.getCode();
            }
          })
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.register {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.register-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.register-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.register-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-register-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.register-code-img {
  height: 38px;
}
</style>

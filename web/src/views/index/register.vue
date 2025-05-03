<template>
  <div class="container">
    <div class="register-box">
      <div class="regist-title">
        <span>注册新账号</span>
        <span @click="router.push({ name: 'login' })" class="to-login">我要登录</span>
      </div>

      <div class="form-group">
        <div class="common-input">
          <img :src="MailIcon" class="left-icon" />
          <div class="input-view">
            <input
              placeholder="请输入邮箱"
              v-model="tData.loginForm.username"
              type="text"
              class="input"
            />
            <p class="err-view"></p>
          </div>
        </div>
      </div>

      <div class="form-group">
        <div class="common-input">
          <img :src="PwdIcon" class="left-icon" />
          <div class="input-view">
            <input
              placeholder="请输入密码"
              v-model="tData.loginForm.password"
              type="password"
              class="input"
            />
            <p class="err-view"></p>
          </div>
        </div>
      </div>

      <div class="form-group">
        <div class="common-input">
          <img :src="PwdIcon" class="left-icon" />
          <div class="input-view">
            <input
              placeholder="请再次输入密码"
              v-model="tData.loginForm.repassword"
              type="password"
              class="input"
            />
            <p class="err-view"></p>
          </div>
        </div>
      </div>

      <div class="submit-box">
        <button class="submit-btn" @click="handleRegister">注册</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { userRegisterApi } from '/@/api/user'
import { message } from 'ant-design-vue'
import MailIcon from '/@/assets/images/mail-icon.svg'
import PwdIcon from '/@/assets/images/pwd-icon.svg'

const router = useRouter()

const tData = reactive({
  loginForm: {
    username: '',
    password: '',
    repassword: ''
  }
})

const handleRegister = () => {
  if (
    tData.loginForm.username === '' ||
    tData.loginForm.password === '' ||
    tData.loginForm.repassword === ''
  ) {
    message.warn('不能为空！')
    return
  }

  userRegisterApi({
    username: tData.loginForm.username,
    password: tData.loginForm.password,
    rePassword: tData.loginForm.repassword
  })
    .then(() => {
      message.success('注册成功！')
      router.push({ name: 'login' })
    })
    .catch(err => {
      message.error(err.msg || '注册失败')
    })
}
</script>

<style scoped lang="less">
@import "/@/styles/reset.less";

.container {
  background-image: url('../images/admin-login-bg.jpg');
  background-size: cover;
  background-position: center;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.register-box {
  width: 400px;
  background: #fff;
  border: @border-width-base solid @border-color;
  border-radius: @border-radius-base;
  box-shadow: 0 2px 8px fade(@border-color, 30%);
  padding: 32px 28px;
  font-family: @font-family;

  .regist-title {
    font-size: 14px;
    font-weight: bold;
    color: @text-color;
    margin-bottom: 24px;
    display: flex;
    justify-content: space-between;

    .to-login {
      color: @primary-color;
      cursor: pointer;
      text-decoration: underline;
    }
  }

  .form-group {
    margin-bottom: 16px;
  }

  .common-input {
    display: flex;
    align-items: center;

    .left-icon {
      width: 20px;
      height: 20px;
      margin-right: 12px;
      filter: grayscale(100%);
    }

    .input-view {
      flex: 1;

      .input {
        width: 100%;
        border: none;
        border-bottom: @border-width-base solid @border-color;
        background: transparent;
        font-size: @font-size-base;
        color: @text-color;
        padding: 6px 0;

        &::placeholder {
          color: fade(@text-color, 50%);
        }
      }

      .err-view {
        margin-top: 4px;
        height: 16px;
        font-size: 12px;
        color: #f62a2a;
      }
    }
  }

  .submit-box {
    margin-top: 24px;

    .submit-btn {
      background: @primary-color;
      color: #fff;
      width: 100%;
      height: 42px;
      font-size: 14px;
      font-weight: bold;
      border: none;
      border-radius: @border-radius-base;
      cursor: pointer;

      &:hover {
        background: lighten(@primary-color, 10%);
      }
    }
  }
}
</style>
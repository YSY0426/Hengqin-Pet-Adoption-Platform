<template>
  <div class="container">
    <div class="login-page">
      <img :src="LogoIcon" alt="logo" class="logo-icon" />
      <div class="login-tab">
        <div class="tab-selected">
          <span>邮箱登录</span>
          <span class="tabline"></span>
        </div>
      </div>
      <div class="mail-login" type="login">
        <div class="common-input">
          <img :src="MailIcon" class="left-icon" />
          <div class="input-view">
            <input placeholder="请输入注册邮箱" v-model="pageData.loginForm.username" type="text" class="input" />
            <p class="err-view"></p>
          </div>
        </div>
        <div class="common-input">
          <img :src="PwdIcon" class="left-icon" />
          <div class="input-view">
            <input placeholder="请输入密码" v-model="pageData.loginForm.password" type="password" class="input" />
            <p class="err-view"></p>
          </div>
        </div>
        <div class="next-btn-view">
          <button class="next-btn" @click="handleLogin">登录</button>
        </div>
      </div>
      <div class="operation">
        <a @click="handleCreateUser" class="forget-pwd">注册新帐号</a>
        <a class="forget-pwd">忘记密码？</a>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useUserStore } from '/@/store'
import { message } from 'ant-design-vue'
import LogoIcon from '/@/assets/images/k-logo.png'
import MailIcon from '/@/assets/images/mail-icon.svg'
import PwdIcon from '/@/assets/images/pwd-icon.svg'

const router = useRouter()
const userStore = useUserStore()

const pageData = reactive({
  loginForm: {
    username: '',
    password: ''
  }
})

const handleLogin = () => {
  userStore
    .login({
      username: pageData.loginForm.username,
      password: pageData.loginForm.password
    })
    .then(() => {
      loginSuccess()
    })
    .catch(err => {
      message.warn(err.msg || '登录失败')
    })
}

const handleCreateUser = () => {
  router.push({ name: 'register' })
}

const loginSuccess = () => {
  router.push({ name: 'portal' })
  message.success('登录成功！')
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

.login-page {
  width: 400px;
  background: #fff;
  border: @border-width-base solid @border-color;
  border-radius: @border-radius-base;
  box-shadow: 0 2px 8px fade(@border-color, 30%);
  padding: 32px 28px;

  .logo-icon {
    display: block;
    width: 48px;
    height: 48px;
    margin: 0 auto 24px;
  }

  .login-tab {
    display: flex;
    justify-content: center;
    align-items: center;
    border-bottom: @border-width-base solid @border-color;
    height: 40px;
    margin-bottom: 32px;

    .tab-selected {
      font-weight: bold;
      font-size: 14px;
      color: @primary-color;
      position: relative;

      .tabline {
        height: 2px;
        background: @primary-color;
        position: absolute;
        bottom: -8px;
        left: 0;
        right: 0;
        width: 40%;
        margin: 0 auto;
        transition: width 0.3s ease;
      }
    }
  }

  .mail-login {
    display: flex;
    flex-direction: column;
    gap: 16px;

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
          font-family: @font-family;

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
  }

  .next-btn-view {
    margin-top: 16px;

    .next-btn {
      background: @primary-color;
      color: #ffffff;
      width: 100%;
      height: 42px;
      font-family: @font-family;
      font-size: 14px;
      font-weight: bold;
      border: none;
      border-radius: @border-radius-base;
      cursor: pointer;
      transition: background 0.3s ease;

      &:hover {
        background: lighten(@primary-color, 10%);
      }
    }
  }

  .operation {
    display: flex;
    justify-content: space-between;
    margin-top: 24px;

    .forget-pwd {
      font-size: 13px;
      color: @primary-color;
      text-decoration: underline;
      cursor: pointer;
    }
  }
}
</style>
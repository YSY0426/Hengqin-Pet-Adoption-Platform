<template>
  <div class="main-bar-view">
    <!-- 左侧 Logo -->
    <div class="logo">
      <img :src="logoImage" @click="$router.push({ name: 'portal' })" />
    </div>

    <!-- 中间搜索框 -->
    <div class="search-entry">
      <img :src="SearchIcon" class="search-icon" />
      <input
        placeholder="输入关键词"
        ref="keywordRef"
        @keyup.enter="search"
      />
    </div>

    <!-- 右侧用户区 -->
    <div class="right-view">
      <!-- 持续可见：后台入口 -->
      <a href="/admin" target="_blank" class="admin-link">后台入口</a>

      <!-- 已登录：头像下拉 + 消息 -->
      <template v-if="userStore.user_token">
        <a-dropdown>
          <a @click.prevent class="ant-dropdown-link">
            <img :src="AvatarIcon" class="self-img" />
          </a>
          <template #overlay>
            <a-menu>
              <a-menu-item @click="goUserCenter('usercenter')">个人中心</a-menu-item>
              <a-menu-item @click="quit">退出</a-menu-item>
            </a-menu>
          </template>
        </a-dropdown>

        <a-badge :count="msgData.length" class="msg-icon">
          <img :src="MessageIcon" @click="msgVisible = true" />
        </a-badge>

        <a-modal v-model:visible="msgVisible" title="消息" width="360">
          <template #footer>
            <a-button type="primary" @click="onClose">关闭</a-button>
          </template>
          <div v-if="msgData.length" class="msg-box">
            <div v-for="item in msgData" :key="item.id" class="msg-item">
              <div class="msg-title">
                <span class="name">{{ item.sender }}</span>
                <span class="time">{{ item.time }}</span>
              </div>
              <div class="msg-content">{{ item.content }}</div>
            </div>
          </div>
          <div v-else class="content">暂无消息</div>
        </a-modal>
      </template>

      <!-- 未登录：登录按钮 -->
      <template v-else>
        <button class="btn" @click="goLogin">登录</button>
      </template>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { listApi } from '/@/api/notice'
import { useUserStore } from '/@/store'

import logoImage from '/@/assets/images/k-logo.png'
import SearchIcon from '/@/assets/images/search-icon.svg'
import AvatarIcon from '/@/assets/images/avatar.jpg'
import MessageIcon from '/@/assets/images/message-icon.svg'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const keywordRef = ref<HTMLInputElement>()
const loading    = ref(false)
const msgVisible = ref(false)
const msgData    = ref<any[]>([])

onMounted(() => {
  getMessageList()
})

// 获取消息列表，传入空对象以满足参数签名
async function getMessageList() {
  loading.value = true
  try {
    const res = await listApi({})
    msgData.value = res.data || []
  } finally {
    loading.value = false
  }
}

function search() {
  const keyword = keywordRef.value?.value.trim()
  if (!keyword) return
  if (route.name === 'search') {
    router.push({ name: 'search', query: { keyword } })
  } else {
    window.open(`/search?keyword=${encodeURIComponent(keyword)}`, '_blank')
  }
}

function goLogin() {
  router.push({ name: 'login' })
}

function goUserCenter(menuName: string) {
  router.push({ name: menuName })
}

function quit() {
  userStore.logout()
  router.push({ name: 'login' })
}

function onClose() {
  msgVisible.value = false
}
</script>

<style scoped lang="less">
/* 导入全局样式 */
@import "../../../styles/reset.less";

.main-bar-view {
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  height: 56px;
  background: @primary-color;
  color: #ffffff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.logo {
  position: absolute;
  left: 24px;

  img {
    width: 36px;
    height: 36px;
    cursor: pointer;
  }
}

.search-entry {
  width: 50%;
  max-width: 600px;
  display: flex;
  align-items: center;
  background: fade(@primary-color, 20%);
  border: none;
  border-radius: @border-radius-base;
  padding: 6px 12px;

  .search-icon {
    width: 20px;
    height: 20px;
    margin-right: 10px;
    filter: invert(1);
  }

  input {
    flex: 1;
    border: none;
    background: transparent;
    font-family: @font-family;
    font-size: @font-size-base;
    color: #ffffff;
  }

  input::placeholder {
    color: fade(#ffffff, 60%);
  }
}

.right-view {
  position: absolute;
  right: 24px;
  display: flex;
  align-items: center;
  gap: 24px;

  .admin-link {
    padding: 4px 8px;
    background: @primary-color;
    color: #ffffff;
    border-radius: @border-radius-base;
    font-family: @font-family;
    font-size: @font-size-base;
    text-decoration: none;
  }

  .self-img {
    width: 32px;
    height: 32px;
    border-radius: 50%;
    cursor: pointer;
    border: 2px solid #ffffff;
  }

  .msg-icon img {
    width: 22px;
    height: 22px;
    cursor: pointer;
    filter: invert(1);
  }

  .btn {
    padding: 6px 14px;
    background: @primary-color;
    color: #ffffff;
    border: none;
    border-radius: @border-radius-base;
    font-family: @font-family;
    font-size: @font-size-base;
    cursor: pointer;
  }
}
</style>

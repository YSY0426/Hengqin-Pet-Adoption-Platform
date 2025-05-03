<template>
  <div class="user">
    <Header />
    <div class="user-content">
      <div class="user-content-left">
        <MineInfosView />
      </div>
      <div class="user-content-right">
        <router-view />
      </div>
    </div>
    <Footer />
  </div>
</template>

<script>
import Header from '/@/views/index/components/header.vue'
import Footer from '/@/views/index/components/footer.vue'
import MineInfosView from '/@/views/index/user/mine-infos-view.vue'

export default {
  components: {
    MineInfosView,
    Footer,
    Header
  },
  data () {
    return {
      collapsed: false
    }
  }
}
</script>

<style scoped lang="less">
.user {
  display: block;
  font-family: 'Courier New', Courier, monospace;
  color: #000;
  background-color: #fff;
}

.user-content {
  display: flex;
  flex-direction: row;
  max-width: 1200px;
  min-width: 800px;
  margin: 80px auto;

  .user-content-left {
    border-right: 1px solid #000;
    padding-right: 20px;
  }

  .user-content-right {
    flex: 1;
    padding-left: 20px;
  }
}

/* 复古印刷统一风格 */
:deep(*) {
  font-family: 'Courier New', Courier, monospace;
  color: #000;
  background-color: #fff;
  box-shadow: none;
  border-radius: 0;
}

:deep(input),
:deep(textarea),
:deep(.ant-input),
:deep(.ant-select-selector) {
  border: 1px solid #000;
  background-color: #f5f5f5;
  color: #000;
  border-radius: 0;
}

:deep(button),
:deep(.ant-btn) {
  font-family: 'Courier New', Courier, monospace;
  background-color: #000;
  color: #fff;
  border: 1px solid #000;
  border-radius: 0;
}

:deep(.ant-btn:hover),
:deep(button:hover) {
  background-color: #333;
  color: #fff;
}
</style>
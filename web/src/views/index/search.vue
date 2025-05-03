<template>
  <div class="search">
    <Header />
    <div class="search-content">
      <SearchContentView />
    </div>
    <Footer />
  </div>
</template>

<script setup>
import Header from '/@/views/index/components/header.vue'
import Footer from '/@/views/index/components/footer.vue'
import SearchContentView from '/@/views/index/components/search-content-view.vue'
</script>

<style scoped lang="less">
.search-content {
  width: 1100px;
  margin: 4px auto;
}

/* 统一复古印刷风格 */
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
  border-radius: 0;
  background-color: #f5f5f5;
  color: #000;
}

:deep(button),
:deep(.ant-btn) {
  font-family: 'Courier New', Courier, monospace;
  background-color: #000;
  color: #fff;
  border: 1px solid #000;
  border-radius: 0;
  box-shadow: none;
}

:deep(.ant-btn:hover),
:deep(button:hover) {
  background-color: #333;
  color: #fff;
}

:deep(a) {
  color: #000;
  text-decoration: underline;
}
</style>
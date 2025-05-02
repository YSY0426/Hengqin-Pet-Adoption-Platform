<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
// 这里保持你原来的逻辑不变
export default {
  name: 'App'
}
</script>

<style lang="less">
/* 先加载全局 Reset + 主题变量 */
@import "./styles/reset.less";
/* 原有 App.vue 样式，可留可删 */
#app {
  /* 如果你想让页面撑满全屏高度，保留下面这一行 */
  height: 100vh;
}
</style>

<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style lang="less">
/* 先加载全局 Reset + 主题变量 */
@import "./styles/reset.less";
/* 原有 App.vue 样式，可留可删 */
#app {
  height: 100vh;
}
</style>

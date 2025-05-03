<template>
  <router-view />
</template>

<script>
export default {}
</script>

<style scoped lang="less">
:deep(body) {
  font-family: 'Courier New', Courier, monospace;
  background-color: #ffffff;
  color: #000000;
  margin: 0;
  padding: 0;
}

:deep(button),
:deep(input),
:deep(textarea),
:deep(.ant-input),
:deep(.ant-btn) {
  font-family: 'Courier New', Courier, monospace;
  background-color: #f5f5f5;
  color: #000000;
  border: 1px solid #000000;
  border-radius: 0;
  box-shadow: none;
}

:deep(.ant-btn-primary) {
  background-color: #000000 !important;
  color: #ffffff !important;
  border: 1px solid #000000 !important;
  border-radius: 0 !important;
}

:deep(a) {
  color: #000000;
  text-decoration: underline;
}

:deep(::placeholder) {
  color: #666666;
}
</style>
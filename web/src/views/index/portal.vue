<template>
  <div class="portal">
    <Header />
    <Content />
    <Footer />
  </div>
</template>
<script>
import Header from '/@/views/index/components/header.vue'
import Footer from '/@/views/index/components/footer.vue'
import Content from '/@/views/index/components/content.vue'

export default {
  components: {
    Footer,
    Header,
    Content
  },
  data () {
    return {
    }
  }
}
</script>
<style scoped lang="less">
.portal {
  font-family: 'Courier New', Courier, monospace;
  background-color: #ffffff;
  color: #000000;
}

button, input, textarea, .ant-input, .ant-btn {
  font-family: 'Courier New', Courier, monospace;
  background-color: #f5f5f5;
  color: #000000;
  border: 1px solid #000000;
  border-radius: 0;
  box-shadow: none;
}

.ant-btn-primary {
  background-color: #000000 !important;
  color: #ffffff !important;
  border: 1px solid #000000 !important;
  border-radius: 0 !important;
}

a {
  color: #000000;
  text-decoration: underline;
}

::placeholder {
  color: #666666;
}
</style>

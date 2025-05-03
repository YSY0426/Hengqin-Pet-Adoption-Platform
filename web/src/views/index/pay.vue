<template>
  <div>
    <Header />
    <div class="pay-content">
      <div class="title">宠物领养成功</div>
      <div class="text time-margin">
        <span>请注意宠物领养进程</span>  <!--我们将会在 7 日内将宠物送至你家里，请注意查收-->
      </div>
    </div>
  </div>
</template>

<script setup>
import Header from '/@/views/index/components/header.vue'
const route = useRoute()
</script>

<style scoped lang="less">
@import "/@/styles/reset.less";

.pay-content {
  margin: 120px auto 0;
  width: 500px;
  background: #ffffff;
  padding: 32px 24px;
  border: @border-width-base solid @border-color;
  border-radius: @border-radius-base;
  box-shadow: 0 2px 8px fade(@border-color, 30%);

  .title {
    font-size: 22px;
    font-weight: bold;
    color: @primary-color;
    text-align: center;
    margin-bottom: 16px;
  }

  .time-margin {
    margin: 0 0 24px;
  }

  .text {
    font-size: @font-size-base;
    color: @text-color;
    text-align: center;
    line-height: 1.6;
  }

  .price {
    font-size: 16px;
    font-weight: bold;
    color: #ff8a00;
    text-align: center;
    margin-top: 16px;

    .num {
      font-size: 28px;
    }
  }

  .pay-choose-view {
    margin-top: 24px;

    .pay-choose-box {
      display: flex;
      justify-content: space-between;
      max-width: 300px;
      margin: 0 auto;
    }

    .choose-box {
      width: 140px;
      height: 126px;
      border: @border-width-base solid @border-color;
      border-radius: @border-radius-base;
      text-align: center;
      cursor: pointer;

      &.choose-box-active {
        border-color: #4684e2;
      }

      img {
        height: 40px;
        margin: 24px auto 16px;
        display: block;
      }
    }

    .tips {
      font-size: @font-size-base;
      color: #6f6f6f;
      text-align: center;
      margin: 16px 0 24px;
    }

    .pay-btn {
      background: #c3c9d5;
      color: #ffffff;
      font-size: 14px;
      width: 104px;
      height: 32px;
      line-height: 32px;
      text-align: center;
      border-radius: 32px;
      border: none;
      outline: none;
      cursor: pointer;
      display: block;
      margin: 0 auto;

      &.pay-btn-active {
        background: #4684e2;
      }
    }
  }
}
</style>
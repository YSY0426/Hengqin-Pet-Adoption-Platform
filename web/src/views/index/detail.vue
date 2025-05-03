<template>
    <div class="detail">
      <Header/>
      <div class="detail-content">
        <div class="detail-content-top">
          <div style="position: relative;">
            <div class="thing-infos-view">
              <div class="thing-infos">
                <div class="thing-img-box">
                  <img :src="detailData.cover"/>
                </div>
                <div class="thing-info-box">
                  <div class="thing-state">
                    <span class="state hidden-sm">宠物状态</span>
                    <span>未领养</span>
                  </div>
                  <h1 class="thing-name">{{ detailData.title }}</h1>
                  <div class="translators flex-view">
                    <span>性别：</span>
                    <span class="name">{{ detailData.sex }}</span>
                  </div>
                  <div class="translators flex-view">
                    <span>地址：</span>
                    <span class="name">{{ detailData.address }}</span>
                  </div>
                  <button class="buy-btn" @click="handleOrder(detailData)">
                    <img :src="AddIcon" />
                    <span>立即领养</span>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="detail-content-bottom">
          <div class="thing-content-view flex-view">
            <div class="main-content">
              <div class="order-view main-tab">
                <span class="tab"
                      :class="selectTabIndex===index? 'tab-select':''"
                      v-for="(item,index) in tabData"
                      :key="index"
                      @click="selectTab(index)">
                  {{ item }}
                </span>
                <span :style="{left: tabUnderLeft + 'px'}" class="tab-underline"></span>
              </div>
  
              <div class="thing-intro" :class="selectTabIndex <= 0 ? '' : 'hide'">
                <p class="text">{{ detailData.description }}</p>
              </div>
  
              <div class="thing-comment" :class="selectTabIndex > 0 ? '' : 'hide'">
                <div class="title">发表新的评论</div>
                <div class="publish flex-view">
                  <img :src="AvatarIcon" class="mine-img">
                  <input placeholder="说点什么..." class="content-input" ref="commentRef">
                  <button class="send-btn" @click="sendComment()">发送</button>
                </div>
                <div class="tab-view flex-view">
                  <div class="count-text">共有{{ commentData.length }}条评论</div>
                  <div class="tab-box flex-view" v-if="commentData.length > 0">
                    <span :class="sortIndex === 0 ? 'tab-select' : ''" @click="sortCommentList('recent')">最新</span>
                    <div class="line"></div>
                    <span :class="sortIndex === 1 ? 'tab-select' : ''" @click="sortCommentList('hot')">热门</span>
                  </div>
                </div>
                <div class="comments-list">
                  <div class="comment-item" v-for="item in commentData" :key="item.id">
                    <div class="flex-item flex-view">
                      <img :src="AvatarIcon" class="avator">
                      <div class="person">
                        <div class="name">{{ item.username }}</div>
                        <div class="time">{{ item.commentTime }}</div>
                      </div>
                      <div class="float-right">
                        <span @click="like(item.id)">推荐</span>
                        <span class="num">{{ item.likeCount }}</span>
                      </div>
                    </div>
                    <p class="comment-content">{{ item.content }}</p>
                  </div>
                </div>
              </div>
            </div>
  
            <div class="recommend">
              <div class="title">热门推荐</div>
              <div class="things">
                <div class="thing-item" v-for="item in recommendData" :key="item.id" @click="handleDetail(item)">
                  <div class="img-view">
                    <img :src="item.cover">
                  </div>
                  <div class="info-view">
                    <h3 class="thing-name">{{ item.title.substring(0, 12) }}</h3>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Footer/>
    </div>
  </template>
  
  <script setup>
  import {message} from "ant-design-vue"
  import Header from '/@/views/index/components/header.vue'
  import Footer from '/@/views/index/components/footer.vue'
  import AddIcon from '/@/assets/images/add.svg'
  import AvatarIcon from '/@/assets/images/avatar.jpg'
  import {
    detailApi, listApi as listThingList,
  } from '/@/api/thing'
  import {listThingCommentsApi, createApi as createCommentApi, likeApi} from '/@/api/comment'
  import {wishApi} from '/@/api/thingWish'
  import {collectApi} from '/@/api/thingCollect'
  import {BASE_URL} from "/@/store/constants"
  import {useRoute, useRouter} from "vue-router"
  import {useUserStore} from "/@/store"
  import {getFormatTime} from "/@/utils"
  
  const router = useRouter()
  const route = useRoute()
  const userStore = useUserStore()
  
  let thingId = ref('')
  let detailData = ref({})
  let tabUnderLeft = ref(6)
  let tabData = ref(['简介', '评论'])
  let selectTabIndex = ref(0)
  let commentData = ref([])
  let recommendData = ref([])
  let sortIndex = ref(0)
  let order = ref('recent')
  let commentRef = ref()
  
  onMounted(() => {
    thingId.value = route.query.id.trim()
    getThingDetail()
    getRecommendThing()
    getCommentList()
  })
  
  const selectTab = (index) => {
    selectTabIndex.value = index
    tabUnderLeft.value = 6 + 54 * index
  }
  
  const getThingDetail = () => {
    detailApi({id: thingId.value}).then(res => {
      detailData.value = res.data
      detailData.value.cover = BASE_URL + '/api/staticfiles/image/' + detailData.value.cover
    })
  }
  
  const getRecommendThing = () => {
    listThingList({sort: 'recommend'}).then(res => {
      res.data.forEach(item => {
        if (item.cover) {
          item.cover = BASE_URL + '/api/staticfiles/image/' + item.cover
        }
      })
      recommendData.value = res.data.slice(0, 6)
    })
  }
  
  const handleOrder = (detailData) => {
    const userId = userStore.user_id
    router.push({
      name: 'confirm',
      query: {
        id: detailData.id,
        title: detailData.title,
        cover: detailData.cover,
        price: detailData.price
      }
    })
  }
  
  const handleDetail = (item) => {
    let text = router.resolve({name: 'detail', query: {id: item.id}})
    window.open(text.href, '_blank')
  }
  
  const sendComment = () => {
    let text = commentRef.value.value.trim()
    if (text.length <= 0) return
    commentRef.value.value = ''
    let userId = userStore.user_id
    if (userId) {
      createCommentApi({content: text, thingId: thingId.value, userId: userId}).then(() => {
        getCommentList()
      })
    } else {
      message.warn('请先登录！')
      router.push({name: 'login'})
    }
  }
  
  const like = (commentId) => {
    likeApi({id: commentId}).then(() => {
      getCommentList()
    })
  }
  
  const getCommentList = () => {
    listThingCommentsApi({thingId: thingId.value, order: order.value}).then(res => {
      res.data.forEach(item => {
        item.commentTime = getFormatTime(item.commentTime, true)
      })
      commentData.value = res.data
    })
  }
  
  const sortCommentList = (sortType) => {
    sortIndex.value = sortType === 'recent' ? 0 : 1
    order.value = sortType
    getCommentList()
  }
  </script>
  
  <style scoped lang="less">
  @font-family: "Courier New", Courier, monospace;
  @black: #000;
  @white: #fff;
  @gray: #ccc;
  
  body {
    font-family: @font-family;
    color: @black;
    background-color: @white;
  }
  
  .detail-content {
    width: 1100px;
    margin: auto;
  }
  
  button, input {
    font-family: @font-family;
    background-color: @white;
    color: @black;
    border: 1px solid @black;
    border-radius: 0;
  }
  
  .buy-btn, .send-btn {
    background: @black;
    color: @white;
    border-radius: 0;
    border: none;
  }
  
  .thing-name,
  .count-title,
  .title,
  .comment-content,
  .text,
  .price,
  .name,
  .num {
    color: @black !important;
    font-family: @font-family;
  }
  
  .tab {
    color: @gray;
    font-family: @font-family;
  }
  
  .tab-select {
    color: @black;
  }
  
  .tab-underline {
    background-color: @black;
  }
  
  .img-view img,
  .thing-img-box img {
    filter: grayscale(100%);
  }
  </style>
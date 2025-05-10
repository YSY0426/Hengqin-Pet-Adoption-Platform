<template>
  <div class="detail">
    <Header />

    <div class="detail-content">
      <!-- 顶部：图片 + 基本信息 -->
      <div class="detail-content-top">
        <div class="thing-infos-view">
          <div class="thing-infos">
            <div class="thing-img-box">
              <img :src="detailData.cover" alt="宠物图片" />
            </div>
            <div class="thing-info-box">
              <div class="thing-state">
                <span class="state">宠物状态：</span>
                <span>未领养</span>
              </div>
              <h1 class="thing-name">{{ detailData.title }}</h1>
              <div class="field-row">
                <span class="field-label">性别：</span>
                <span class="field-value">{{ detailData.sex }}</span>
              </div>
              <div class="field-row">
                <span class="field-label">地址：</span>
                <span class="field-value">{{ detailData.address }}</span>
              </div>
              <button class="buy-btn" @click="handleOrder(detailData)">
                <img :src="AddIcon" alt="领养" />
                <span>立即领养</span>
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- 底部：简介/评论 + 侧边推荐 -->
      <div class="detail-content-bottom">
        <div class="thing-content-view">
          <!-- 左侧主内容 -->
          <div class="main-content">
            <div class="order-view">
              <span
                v-for="(item, index) in tabData"
                :key="index"
                class="tab"
                :class="{ 'tab-select': selectTabIndex === index }"
                @click="selectTab(index)"
              >
                {{ item }}
              </span>
            </div>

            <div class="thing-intro" v-show="selectTabIndex === 0">
              <p class="text">{{ detailData.description }}</p>
            </div>

            <div class="thing-comment" v-show="selectTabIndex === 1">
              <div class="title">发表新的评论</div>
              <div class="publish flex-view">
                <img :src="AvatarIcon" class="mine-img" />
                <input
                  placeholder="说点什么..."
                  class="content-input"
                  ref="commentRef"
                />
                <button class="send-btn" @click="sendComment()">发送</button>
              </div>
              <div class="tab-view flex-view">
                <div class="count-text">共有{{ commentData.length }}条评论</div>
                <div class="tab-box flex-view" v-if="commentData.length > 0">
                  <span
                    :class="sortIndex === 0 ? 'tab-select' : ''"
                    @click="sortCommentList('recent')"
                    >最新</span
                  >
                  <div class="line"></div>
                  <span
                    :class="sortIndex === 1 ? 'tab-select' : ''"
                    @click="sortCommentList('hot')"
                    >热门</span
                  >
                </div>
              </div>
              <div class="comments-list">
                <div
                  class="comment-item"
                  v-for="item in commentData"
                  :key="item.id"
                >
                  <div class="flex-item flex-view">
                    <img :src="AvatarIcon" class="avator" />
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

          <!-- 右侧推荐区 -->
          <div class="recommend">
            <div class="title">热门推荐</div>
            <div class="things">
              <div
                class="thing-item"
                v-for="item in recommendData"
                :key="item.id"
                @click="handleDetail(item)"
              >
                <div class="img-view">
                  <img :src="item.cover" alt="推荐图片" />
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

    <Footer />
  </div>
</template>

<script setup lang="ts">
import { message } from "ant-design-vue";
import Header from "./components/header.vue";
import Footer from "./components/footer.vue";
import AddIcon from "/@/assets/images/add.svg";
import AvatarIcon from "/@/assets/images/avatar.jpg";
import { detailApi, listApi as listThingList } from "/@/api/thing";
import {
  listThingCommentsApi,
  createApi as createCommentApi,
  likeApi,
} from "/@/api/comment";
import { BASE_URL } from "/@/store/constants";
import { useRoute, useRouter } from "vue-router";
import { useUserStore } from "/@/store";
import { getFormatTime } from "/@/utils";
import { ref, onMounted } from "vue";

const router = useRouter();
const route = useRoute();
const userStore = useUserStore();

let thingId = ref("");
let detailData = ref<any>({});
let tabData = ref(["简介", "评论"]);
let selectTabIndex = ref(0);
let sortIndex = ref(0);
let order = ref("recent");
let commentData = ref<any[]>([]);
let recommendData = ref<any[]>([]);
let commentRef = ref<HTMLInputElement>();

onMounted(() => {
  thingId.value = (route.query.id as string).trim();
  getThingDetail();
  getRecommendThing();
  getCommentList();
});

const selectTab = (index: number) => {
  selectTabIndex.value = index;
};

const getThingDetail = () => {
  detailApi({ id: thingId.value }).then((res) => {
    detailData.value = res.data;
    detailData.value.cover =
      BASE_URL + "/api/staticfiles/image/" + detailData.value.cover;
  });
};

const getRecommendThing = () => {
  listThingList({ sort: "recommend" }).then((res) => {
    res.data.forEach((item: any) => {
      if (item.cover) {
        item.cover =
          BASE_URL + "/api/staticfiles/image/" + item.cover;
      }
    });
    recommendData.value = res.data.slice(0, 6);
  });
};

const handleOrder = (detailData: any) => {
  router.push({
    name: "confirm",
    query: {
      id: detailData.id,
      title: detailData.title,
      cover: detailData.cover,
      price: detailData.price,
    },
  });
};

const handleDetail = (item: any) => {
  const url = router.resolve({
    name: "detail",
    query: { id: item.id },
  });
  window.open(url.href, "_blank");
};

const sendComment = () => {
  const text = commentRef.value?.value.trim();
  if (!text) return;
  if (userStore.user_id) {
    createCommentApi({
      content: text,
      thingId: thingId.value,
      userId: userStore.user_id,
    }).then(() => getCommentList());
    commentRef.value!.value = "";
  } else {
    message.warn("请先登录！");
    router.push({ name: "login" });
  }
};

const like = (commentId: string) => {
  likeApi({ id: commentId }).then(() => getCommentList());
};

const getCommentList = () => {
  listThingCommentsApi({
    thingId: thingId.value,
    order: order.value,
  }).then((res) => {
    res.data.forEach((item: any) => {
      item.commentTime = getFormatTime(item.commentTime, true);
    });
    commentData.value = res.data;
  });
};

const sortCommentList = (sortType: "recent" | "hot") => {
  sortIndex.value = sortType === "recent" ? 0 : 1;
  order.value = sortType;
  getCommentList();
};
</script>

<style scoped lang="less">
@import "../../styles/reset.less";

@primary-color: #1890ff;
@bg-color: #f5f5f5;
@white: #fff;
@black: #333;
@gray: #888;

.detail {
  background: @bg-color;
  padding-bottom: 40px;
}

.detail-content {
  max-width: 1100px;
  margin: 0 auto;
  padding: 20px;
}

.detail-content-top {
  .thing-infos-view {
    margin-top: 40px;
    .thing-infos {
      display: flex;
      align-items: flex-start;
      gap: 32px;
      background: @white;
      padding: 24px;
      border-radius: 8px;
      box-shadow: 0 2px 8px fade(#000, 5%);

      .thing-img-box {
        flex: none;
        width: 400px;
        img {
          width: 100%;
          height: auto;
          object-fit: cover;
          border-radius: 8px;
        }
      }

      .thing-info-box {
        flex: 1;
        display: flex;
        flex-direction: column;
        gap: 16px;

        .thing-state {
          font-size: 14px;
          color: @gray;
          .state {
            font-weight: bold;
            margin-right: 4px;
          }
        }

        .thing-name {
          font-size: 32px;
          margin: 0;
          color: @black;
        }

        .field-row {
          display: flex;
          align-items: center;
          font-size: 16px;
          .field-label {
            width: 60px;
            color: @gray;
          }
          .field-value {
            font-weight: 600;
            color: @black;
          }
        }

        .buy-btn {
          display: inline-flex;
          align-items: center;
          gap: 8px;
          margin-top: 8px;
          padding: 10px 20px;
          background: @primary-color;
          color: @white;
          font-size: 16px;
          border: none;
          border-radius: 4px;
          cursor: pointer;
          img {
            width: 16px;
            height: 16px;
          }
        }
      }
    }
  }
}

.detail-content-bottom {
  margin-top: 40px;
  .thing-content-view {
    display: grid;
    grid-template-columns: 2fr 1fr;
    gap: 32px;

    .main-content {
      background: @white;
      padding: 24px;
      border-radius: 8px;

      .order-view {
        display: flex;
        gap: 32px;
        margin-bottom: 16px;
        .tab {
          font-size: 18px;
          color: @gray;
          cursor: pointer;
          position: relative;
          padding-bottom: 4px;
          &:hover {
            color: @black;
          }
        }
        .tab-select {
          color: @black;
          &:after {
            content: '';
            position: absolute;
            bottom: -1px;
            left: 0;
            width: 100%;
            height: 3px;
            background: @primary-color;
          }
        }
      }

      .thing-intro,
      .thing-comment {
        .text {
          line-height: 1.8;
          color: @black;
        }
      }
    }

    .recommend {
      .title {
        font-size: 20px;
        font-weight: 600;
        margin-bottom: 16px;
      }
      .things {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(140px, 1fr));
        gap: 16px;
        .thing-item {
          background: @white;
          border-radius: 8px;
          overflow: hidden;
          cursor: pointer;
          transition: transform 0.2s;
          &:hover {
            transform: translateY(-4px);
          }
          .img-view {
            img {
              width: 100%;
              height: 100px;
              object-fit: cover;
            }
          }
          .info-view {
            padding: 8px;
            .thing-name {
              margin: 0;
              font-size: 14px;
              color: @black;
              white-space: nowrap;
              text-overflow: ellipsis;
              overflow: hidden;
            }
          }
        }
      }
    }
  }
}
</style>

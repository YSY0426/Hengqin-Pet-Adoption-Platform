<template>
  <div>
    <Header/>
    <section class="cart-page flex-view">
      <div class="left-flex">
        <div class="title flex-view">
          <h3>领养明细</h3>
        </div>
        <div class="cart-list-view">
          <div class="list-th flex-view">
            <span class="line-1">宠物名称</span>
            <span class="line-5">数量</span>
            <span class="line-6">操作</span>
          </div>
          <div class="list">
            <div class="items flex-view">
              <div class="book flex-view">
                <img :src="pageData.cover">
                <h2>{{ pageData.title }}</h2>
              </div>
              <a-input-number v-model:value="pageData.count" :min="1" :max="10" @change="onCountChange"/>
              <img :src="DeleteIcon" class="delete">
            </div>
          </div>
        </div>
        <div class="title flex-view">
          <h3>备注</h3>
        </div>
        <textarea v-model="pageData.remark" placeholder="输入备注信息，100字以内" class="remark">
    </textarea>
      </div>
      <div class="right-flex">
        <div class="title flex-view">
          <h3>领养人地址</h3>
        </div>
        <div class="address-view">
          <div class="info" style="">
            <span>收件人：</span>
            <span class="name">{{ pageData.receiverName }}
          </span>
            <span class="tel">{{ pageData.receiverPhone }}
          </span>
          </div>
          <div class="address" v-if="pageData.receiverAddress"> {{ pageData.receiverAddress }}</div>
          <div class="info" v-else>
            <span>目前暂无地址信息，请</span>
            <span class="info-blue" @click="handleAdd">新建地址</span>
          </div>
        </div>

        <div class="price-view">
          <div class="btns-view">
            <button class="btn buy" @click="handleBack()">返回</button>
            <button class="btn pay jiesuan" @click="handleJiesuan()">提交</button>
          </div>
        </div>
      </div>
    </section>

    <!--选择弹窗区域-->
    <div>
      <a-modal
          :visible="modal.visile"
          :forceRender="true"
          :title="modal.title"
          ok-text="确认"
          cancel-text="取消"
          @cancel="handleCancel"
          @ok="handleOk"
      >
        <a-form
            ref="myform"
            :label-col="{ style: { width: '80px' } }"
            :model="modal.form"
            :rules="modal.rules"
        >
          <a-row :gutter="24">
            <a-col span="24">
              <a-form-item label="姓名" name="name">
                <a-input placeholder="请输入" v-model:value="modal.form.name"></a-input>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col span="24">
              <a-form-item label="电话号码" name="mobile">
                <a-input placeholder="请输入" v-model:value="modal.form.mobile"></a-input>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col span="24">
              <a-form-item label="送货地址" name="desc">
                <a-input placeholder="请输入" v-model:value="modal.form.desc"></a-input>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col span="24">
              <a-form-item label="默认地址">
                <a-switch v-model:checked="modal.form.default"></a-switch>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </a-modal>
    </div>
  </div>
</template>

<script setup lang="ts">
import {message} from "ant-design-vue";
import Header from '/@/views/index/components/header.vue'
import Footer from '/@/views/index/components/footer.vue'
import DeleteIcon from '/@/assets/images/delete-icon.svg'
import {createApi} from '/@/api/order'
import {listApi as listAddressListApi, createApi as createAddressApi} from '/@/api/address'
import {useUserStore} from "/@/store";

const router = useRouter();
const route = useRoute();
const userStore = useUserStore();


const pageData = reactive({
  id: undefined,
  title: undefined,
  cover: undefined,
  price: undefined,
  remark: undefined,
  count: 1,
  amount: undefined,
  receiverName: undefined,
  receiverPhone: undefined,
  receiverAddress: undefined
})

// 弹窗数据
const modal = reactive({
  visile: false,
  editFlag: false,
  title: '',
  form: {
    name: undefined,
    mobile: undefined,
    desc: undefined,
    default: undefined
  },
  rules: {
    name: [{required: true, message: '请输入', trigger: 'change'}],
  },
})

const myform = ref()

onMounted(() => {

  pageData.id = route.query.id
  pageData.title = route.query.title
  pageData.cover = route.query.cover
  pageData.price = route.query.price
  pageData.amount = pageData.price

  listAddressData()
})

const handleAdd = () => {
  resetModal();
  modal.visile = true;
  modal.editFlag = false;
  modal.title = '新增';
  // 重置
  for (const key in modal.form) {
    modal.form[key] = undefined;
  }
};

const handleOk = () => {
  if(!userStore.user_id){
    message.warn('请先登录')
    return
  }
  myform.value?.validate()
      .then(() => {
        const formData = new FormData()
        formData.append('userId', userStore.user_id)
        formData.append('def', modal.form.default ? '1':'0')
        if (modal.form.name) {
          formData.append('name', modal.form.name)
        }
        if (modal.form.mobile) {
          formData.append('mobile', modal.form.mobile)
        }
        if (modal.form.desc) {
          formData.append('description', modal.form.desc)
        }
        createAddressApi(formData).then(res => {
          console.log(res)
          hideModal()
          pageData.receiverName = modal.form.name
          pageData.receiverAddress = modal.form.desc
          pageData.receiverPhone = modal.form.mobile
        }).catch(err => {
          message.error(err.msg || '新建失败')
        })
      })
      .catch((err) => {
        console.log(err);
        console.log('不能为空');
      });
};

const handleCancel = () => {
  hideModal();
};

// 恢复表单初始状态
const resetModal = () => {
  myform.value?.resetFields();
};

// 关闭弹窗
const hideModal = () => {
  modal.visile = false;
};

const onCountChange = (value) => {
  pageData.amount = pageData.price * value
}

const listAddressData = () => {
  let userId = userStore.user_id
  listAddressListApi({userId: userId}).then(res => {

    if (res.data.length > 0) {
      pageData.receiverName = res.data[0].name
      pageData.receiverPhone = res.data[0].mobile
      pageData.receiverAddress = res.data[0].description
      res.data.forEach(item => {
        if (item.default) {
          pageData.receiverName = item.name
          pageData.receiverPhone = item.mobile
          pageData.receiverAddress = item.description
        }
      })
    }
  }).catch(err => {
    console.log(err)
  })
}

const handleBack = () => {
  router.back()
  console.log('back...')
}
const handleJiesuan = () => {
  const formData = new FormData()
  let userId = userStore.user_id
  if (!userId) {
    message.warn('请先登录！')
    return
  }
  if (!pageData.receiverName) {
    message.warn('请选择地址！')
    return
  }
  formData.append('userId', userId)
  formData.append('thingId', pageData.id)
  formData.append('count', pageData.count)
  if (pageData.remark) {
    formData.append('remark', pageData.remark)
  }
  formData.append('receiverName', pageData.receiverName)
  formData.append('receiverPhone', pageData.receiverPhone)
  formData.append('receiverAddress', pageData.receiverAddress)
  console.log(formData)
  createApi(formData).then(res => {
    router.push({'name': 'pay', query: {'amount': pageData.amount}})
  }).catch(err => {
    message.error(err.msg || '失败')
  })

}


</script>

<style scoped lang="less">
@primary-color: #333;
@text-color: #444;
@background-color: #f2f1ef;
@border-color: #aaa;
@font-family: 'Courier New', Courier, monospace;
@font-size-base: 14px;
@border-radius: 0;
@border-width: 1px;

.flex-view {
  display: flex;
}

.cart-page {
  width: 1024px;
  min-height: 50vh;
  margin: 100px auto;
  font-family: @font-family;
  font-size: @font-size-base;
  color: @text-color;
  background: @background-color;
  border: @border-width solid @border-color;
  padding: 24px;
}

.left-flex {
  flex: 17;
  padding-right: 20px;
}

.title {
  justify-content: space-between;
  align-items: center;

  h3 {
    color: @text-color;
    font-weight: bold;
    font-size: 16px;
    margin: 0 0 8px;
    border-bottom: @border-width solid @border-color;
    padding-bottom: 4px;
  }
}

.cart-list-view {
  margin: 4px 0 40px;

  .list-th {
    border-bottom: @border-width solid @border-color;
    color: @text-color;

    span {
      font-weight: bold;
    }

    .line-1 {
      flex: 1;
      margin-right: 20px;
    }

    .line-5 {
      width: 80px;
      margin-right: 40px;
    }

    .line-6 {
      width: 28px;
    }
  }
}

.items {
  align-items: center;
  margin-top: 20px;

  .book {
    flex: 1;
    align-items: center;
    margin-right: 20px;

    img {
      width: 48px;
      margin-right: 16px;
      border: @border-width solid @border-color;
    }

    h2 {
      font-size: 14px;
      line-height: 22px;
      font-family: @font-family;
    }
  }

  .delete {
    margin-left: 36px;
    width: 24px;
    cursor: pointer;
  }
}

.remark {
  width: 100%;
  background: #fff;
  border: @border-width solid @border-color;
  padding: 6px 12px;
  margin-top: 16px;
  height: 56px;
  font-family: @font-family;
  resize: none;
}

.right-flex {
  flex: 8;
  padding-left: 24px;
  border-left: @border-width solid @border-color;
}

.address-view {
  margin: 12px 0 24px;

  .info {
    font-size: 14px;
    color: @text-color;

    .info-blue {
      cursor: pointer;
      color: @primary-color;
      text-decoration: underline;
    }
  }

  .name, .tel {
    font-weight: bold;
  }

  .address {
    margin-top: 4px;
  }
}

.price-view {
  margin-top: 16px;

  .btns-view {
    margin-top: 24px;
    text-align: right;

    .btn {
      cursor: pointer;
      padding: 6px 12px;
      font-family: @font-family;
      font-size: 14px;
      border: @border-width solid @border-color;
      background: @primary-color;
      color: #fff;
      border-radius: @border-radius;
      transition: background 0.3s ease;

      &:hover {
        background: lighten(@primary-color, 10%);
      }

      &.buy {
        background: #fff;
        color: @primary-color;
      }
    }
  }
}
</style>
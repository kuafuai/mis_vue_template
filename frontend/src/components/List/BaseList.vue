<template>
  <div v-loading="isLoading">
    <el-card shadow="hover" v-for="(item, index) in pageRes.records" :key="index" @click="click_ok(item)">
      <slot name="default" :item="item">
        old {{ item }}
      </slot>
    </el-card>
    <div v-if="isPage" class="flex-end-center m-t-10 m-r-10">
      <el-pagination
          v-if="pageRes && pageRes.total > 0"
          :pageSize="pageParams.pageSize"
          :current="pageParams.current"
          :total="pageRes.total == null ? 0 : pageRes.total"
          :showIcon="true"
          @change="handleSizeChange"
          />
    </div>
  </div>
</template>
<script setup>
const {proxy} = getCurrentInstance();

const props = defineProps({
  params: {
    type: Object, default: () => {
    }
  },
  api: {type: String, default: ''},
  //是否分页
  isPage: {type: Boolean, default: () => false},
  path: {type: String},
  is_route: {type: Boolean, default: () => false},
  query: {
    type: Object, default: () => {
    }
  }
});

// let route_query = proxy.$route.query
//
// var p = props.params
// // 如果查询参数没有，那么使用路由中的参数
// if (p==null || p==undefined) {
//   if (route_query!=null){
//     for (let key in route_query){
//       props.params.params[key] = route_query[key];
//     }
//   }
//
// }
// console.log(p,route_query)

let isLoading = ref(true);
// 分页响应数据
let pageRes = ref({current: 1, pages: 1, size: 10, total: 0, records: []});
let pageParams = ref({current: 1, pageSize: 10});

const emits = defineEmits(['click']);

// 暴露方法
defineExpose({
  refresh,
});

onMounted(() => {
  refresh();
});

// 刷新
function refresh() {
  isLoading.value = true;
  // 情况2：走api接口数据
  if (props.isPage) {
    pageRes.value = {
      current: 1,
      pages: 1,
      size: 10,
      total: 0,
      records: [],
    };
    pageParams.value.current = 1;
  }
  getApiData();

  isLoading.value = false;
}

async function getApiData(pageObj) {
  if (!props.api) {
    return;
  }

  isLoading.value = true;

  if (props.isPage) {
    // 处理分页参数
    if (pageObj) {
      console.log(pageObj)
      // 从分页组件中拿到数据
      pageParams.value.current = pageObj.page;
      pageParams.value.pageSize = pageObj.limit;
    }

    let response = await apiMethod(props.params, pageParams);
    pageRes.value = response.data;
  } else {
    let response = await apiMethod(props.params);
    pageRes.value.records = response.data;
  }

  isLoading.value = false;
}

function apiMethod(params, headers) {
  let data = {...params};
  if (headers) {
    data = Object.assign(data, headers.value);
  }
  console.log("params", props.params)
  console.log("data", data)
  return props.api.split('.').reduce((acc, item) => acc[item], proxy.$api)(data);
}

// 分页组件参数变更时触发
function handleCurrentChange(val) {
  getApiData({page: val, limit: pageParams.value.pageSize});
}

function handleSizeChange(val) {
  console.log("val",val)
  getApiData({page: pageParams.value.current, limit: val});
}

function click_ok(item) {
  emits("click", item)
  // if (props.is_route) {
  //   if (props.path) {
  //     let query = {};
  //     for (let key in props.query) {
  //       if (props.query.hasOwnProperty(key)) {
  //         let value = props.query[key];
  //         // todo 获取参数
  //         console.log(value)
  //         // let prop_value = value.split('.').reduce((acc, part) => acc[part], {item});
  //         query[key] = item[value];
  //       }
  //     }
  //     console.log("this", query)
  //     proxy.$router.push({path: props.path, query: query});
  //   }
  //   // else {
  //   //   emits('click', item);
  //   // }
  // }

}

</script>
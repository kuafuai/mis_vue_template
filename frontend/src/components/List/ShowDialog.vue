<template>
  <el-button type="primary" @click="showDialog">
    {{ text }}
  </el-button>
  <base-dialog
      @close="closeDialog"
      width="50%"
      style="max-width: 300px;"
      v-model="dialogVisible"
  >
    <slot name="dialog"/>
  </base-dialog>

</template>

<script setup>
const props = defineProps({
  text: {
    type: String,
    required: true
  },
});
import {defineProps, ref} from "vue";

const dialogVisible = ref(false)
const showDialog = () => {
  dialogVisible.value = true
}
let router = useRouter()
let route = useRoute()
const closeDialog = () => {
  // const { path, query, params } = route.value;
  location.reload()
  router.go(0)
  dialogVisible.value = false

  // router.push({path:"/sales_lead"})
  // 先导航到一个空路径，然后再导航回原来的路径
  //   router.push({ path, query, params });
}
</script>

<style scoped>

</style>
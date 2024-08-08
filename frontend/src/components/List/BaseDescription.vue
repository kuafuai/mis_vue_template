<template>
  <el-descriptions class="margin-top"  :column="4">
    <slot name="description-items" :description="description"></slot>
  </el-descriptions>
</template>

<script setup>
const {proxy} = getCurrentInstance();
import {defineProps, ref} from 'vue';

const props = defineProps({
  api: {
    type: String,
    default: '',
  },
  params: {
    type: Object, default: () => {
    }
  },
});
const description = ref({})

onMounted(async () => {
  var response = await props.api.split('.').reduce((acc, item) => acc[item], proxy.$api)(props.params);
  description.value = response.data;
  console.log("desctiption", description.value)
});
// export default {
//   name: "BaseDescription"
// }
</script>

<style scoped>

</style>
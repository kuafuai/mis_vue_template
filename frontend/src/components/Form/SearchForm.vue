<template>
  <el-form :inline="true" :model="props.searchData" class="search-form-inline">
    <slot />
    
    <el-form-item
      v-for="item in props.searchItems"
      :key="item.props"
      :label="item.label"
    >
      <el-input
        clearable
        :placeholder="`请输入${item.label}`"
        v-model="props.searchData[item.props]"
      />
    </el-form-item>

    <el-form-item>
      <el-button @click="onSearch" type="primary">搜索</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>

const props = defineProps({
  searchItems: { type: Array, default: () => [] },
  searchData: { type: Object, default: () => {} },
});

// 定义事件
const emit = defineEmits(["refreshTableData"]);

const onSearch = () => {
  emit("refreshTableData");
};

</script>
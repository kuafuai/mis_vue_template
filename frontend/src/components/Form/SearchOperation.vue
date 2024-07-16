<template>

    <base-layout display="flex" direction="c" :w_full="true">
        <base-layout display="flex" x="between" :w_full="true">
            <base-layout display="flex">
                <el-button round @click="eventAdd"><el-icon class="m-r-6"><CirclePlus /></el-icon> 添加</el-button>

                <el-button round><el-icon class="m-r-6"><Operation /></el-icon> 筛选</el-button>

                <el-button round><el-icon class="m-r-6"><Notebook /></el-icon> 导出Excel</el-button>
                
            </base-layout>
            <base-layout display="flex">
                <el-input v-model="props.searchData[firstSearchData]" @keyup.native.enter="onSearch"  placeholder="请输入要搜索的内容" >
                    <template #suffix>
                        <el-icon @click="onSearch"><Search /></el-icon>
                    </template>
                </el-input>

                <el-button round class="m-l-6" @click="toggleAdvanced">
                    高级搜索
                    <a style="margin-left: 8px" >
    
                        <el-icon v-if="advanced"><ArrowUp /></el-icon>
                        <el-icon v-else><ArrowDown /></el-icon>
                    </a>
                </el-button>

            </base-layout>
        </base-layout>
        <base-layout class="m-t-20">
            <el-form :inline="true" :model="props.searchData"  class="search-form-inline w-full">
                <el-collapse-transition>
                    <div v-show="advanced">
                        <slot name="collapse">
                        </slot>
                        <el-form-item>
                            <el-button round color="#5D5FEF" @click="onSearch" >
                                <el-icon size="20" class="m-r-6"><Search /></el-icon>    搜索
                            </el-button>
                        </el-form-item>
                    </div>
                </el-collapse-transition>
            </el-form>
        </base-layout>
    </base-layout>

</template>

<script setup>

import { Search } from '@element-plus/icons-vue'

const props = defineProps({
  searchItems: { type: Array, default: () => [] },
  searchData: { type: Object, default: () => {} },
  firstSearchData: { type: String}
});

const advanced = ref(false);

// 定义事件
const emit = defineEmits(["refreshTableData" , 'addEvent']);

const onSearch = () => {
  emit("refreshTableData");
};

function toggleAdvanced() {
  advanced.value = !advanced.value
}

function eventAdd(){
    emit("addEvent");
}

</script>
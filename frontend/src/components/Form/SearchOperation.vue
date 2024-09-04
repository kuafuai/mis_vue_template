<template>

    <base-layout v-if="type==='PC'" display="flex" direction="c" :w_full="true">
        <base-layout display="flex" x="between" :w_full="true">
            <base-layout display="flex" class="m-l-20">
                <el-button round @click="eventAdd"><el-icon class="m-r-6"><CirclePlus /></el-icon> 添加</el-button>

                <el-button round><el-icon class="m-r-6"><Operation /></el-icon> 筛选</el-button>

                <el-button round><el-icon class="m-r-6"><Notebook /></el-icon> 导出Excel</el-button>
                
            </base-layout>
            <base-layout display="flex" class="m-r-20">
              <el-input v-if="firstSearchData" v-model="props.searchData[firstSearchData]" @keyup.native.enter="onSearch"  :placeholder="'请输入'+firstSearchComment" >
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
        <base-layout class="m-t-20 m-l-20">
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

    <base-layout v-else display="flex" direction="c" :w_full="true">
        <base-layout display="flex" x="end" :w_full="true" class="p-x-20">
            <el-input v-if="firstSearchData" v-model="props.searchData[firstSearchData]" @keyup.native.enter="onSearch"  :placeholder="'请输入'+firstSearchComment" >
                <template #suffix>
                    <el-icon @click="onSearch"><Search /></el-icon>
                </template>
            </el-input>

            <el-button round class="m-l-6" @click="toggleAdvanced">
                <el-icon><More /></el-icon>
            </el-button>
        </base-layout>
        <base-layout class="m-t-20 m-l-20" display="flex">
            <el-button round @click="eventAdd"><el-icon class="m-r-6"><CirclePlus /></el-icon> 添加</el-button>

            <el-button round><el-icon class="m-r-6"><Operation /></el-icon> 筛选</el-button>

            <el-button round><el-icon class="m-r-6"><Notebook /></el-icon> 导出Excel</el-button>
                
        </base-layout>

        <base-drawer
            :show-close="false"
            direction="btt"
            title="高级搜索"
            v-model="advanced"
            size="80%"
            >
            <el-form label-position='top' :model="props.searchData"  >
                <slot name="collapse">
                </slot>
                <el-form-item>
                    <el-button style="width: 100%;" round color="#5D5FEF" @click="onSearch" >
                        <el-icon size="20" class="m-r-6"><Search /></el-icon> 搜索
                    </el-button>
                </el-form-item>
            </el-form>
        </base-drawer>
    </base-layout>

</template>

<script setup>

import { Search } from '@element-plus/icons-vue'
const type = import.meta.env.VITE_APP_TYPE;

const props = defineProps({
  searchItems: { type: Array, default: () => [] },
  searchData: { type: Object, default: () => {} },
  firstSearchData: { type: String},
  firstSearchComment: { type: String,default:"要搜索的内容"}
});

const advanced = ref(false);

// 定义事件
const emit = defineEmits(["refreshTableData" , 'addEvent']);

const onSearch = () => {
  emit("refreshTableData");
  advanced.value=false;
};

function toggleAdvanced() {
  advanced.value = !advanced.value
}

function eventAdd(){
    emit("addEvent");
}

</script>
<template>
    <div v-show="!$route.meta.isParentView" class="flex h-full w-full">
        <div class="menu" id="sidebar">
            <h3 class="flex-center-center p-y-10 text-color-primary">{{ left_title }}</h3>
            <el-menu class="el-menu-vertical-demo" @select="handleSelect">
                <div v-for="item in dynamicRoutes" :key="item.path">
                    <el-menu-item :index="item.path">
                        <template #title>{{ item.meta.title }}</template>
                    </el-menu-item>    
                </div>
            </el-menu>
        </div>
        <div class="flex-1 flex-column w-full m-l-10">
          <!-- 主页面 -->
          <el-scrollbar>
            <router-view />
          </el-scrollbar>
        </div>
    </div>
    <div v-if="$route.meta.isParentView" class="h-full">
        <router-view />
    </div>
    </template>
    
    <script setup>        
        import { dynamicRoutes } from '@/router/dynamic'
        const { proxy } = getCurrentInstance();
        let left_title = import.meta.env.VITE_APP_NAME
        
        function handleSelect(index, indexPath, item, routeResult){
            proxy.$router.push(index);
        }
    
    </script>
    
<style lang="scss" scoped>
.menu {
    box-shadow: 1px 0 5px rgba(0, 0, 0, 0.2);
    width: 250px;
}
</style>
<template>

<div v-show="!$route.meta.isParentView" class="flex bg-color-white w-full h-full">

    <div id="sidebar" class="flex-c-between-start menu p-r-2">
        <div class="w-full"> 
            <div class="flex-start-center m-30 ">
                <img src="@/assets/logo/logo.png" width="32px" height="34px" />
                <span class="m-l-10" style="font-weight: bold; font-size: 24px;">CodeFlying</span>
            </div>

            <div class="flex-start-center m-y-30 m-l-22">
                <el-divider direction="vertical" style="border-color: #5D5FEF; border-width: 4px; border-radius: 2px; height: 20px;"/>
                <h3 class="m-l-5">{{ left_title }}</h3>
            </div>

        </div>
        <div class="w-full h-full overflow-y-scroll">
            <el-menu
                :default-active="currentPath"
                class="el-menu-vertical-demo m-l-20 m-r-10"
                @select="handleSelect"
            >
            
                <div v-for="item in dynamicRoutes" :key="item.path">
                    <el-menu-item v-if="!item.meta.shownot" :index="item.path">
                        <template #title>{{ item.meta.title }}</template>
                    </el-menu-item>    
                </div>

                <div>
                    <el-menu-item index="/login_manger">
                        <template #title>登录管理</template>
                    </el-menu-item>
                </div>

            </el-menu>

        </div>
        
        <div class="w-full m-b-30">
            <el-divider />
            <div class="flex-end-center m-r-20">
                <el-avatar class="" :size="40" >
                    <img src="@/assets/logo/logo.png" />
                </el-avatar>
                <el-badge is-dot class="item m-l-20">
                    <el-icon size="20"><Bell /></el-icon>
                </el-badge>

                <el-button round class="m-l-20"> 退出登录</el-button>
            </div>
        </div>

    </div>

    
    <div class="flex-1 flex-column m-l-2" >
        <!-- <div id="top" class="flex-between-center w-full " style="height: 100px;">
            <div class="flex-c-start-start m-l-20">
                <span style="font-weight: bold; font-size: 22px; color: #5D5FEF;">{{ pageTitle }}</span>
                <span style="color: #999;"> {{ pageTitle }}</span>
            </div>
            <div class="flex-start-center m-r-20">
                <el-badge is-dot class="item">
                    <el-icon><Bell /></el-icon>
                </el-badge>
                <el-avatar class="m-l-20" :size="40" >
                    <img src="@/assets/logo/logo.png" />
                </el-avatar>
            </div>
        </div> -->
        <div class="overflow-y-scroll" :style="{height: appMainHeight+'px', width: appMainWidth+'px'}">
            <router-view />
        </div>

        
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
const pageTitle = ref('首页');
const currentPath = ref('');

const route = useRoute();

    function getBreadcrumb(){
        let matched = route;
        pageTitle.value = matched.meta.title;
    }

    watch(
        () => route.fullPath,
        (newPath, oldPath) => {
            console.log('Route changed from', oldPath, 'to', newPath);
            getBreadcrumb();
            currentPath.value = newPath;
        }
    );

    function handleSelect(index, indexPath, item, routeResult){
        proxy.$router.push(index);
    }

    let appMainWidth = ref(0);
    let appMainHeight = ref(0);

    onMounted(() => {
        window.onresize = function windowResize() {
            calWidthAndHeight();
        };
        calWidthAndHeight();
    });


    function calWidthAndHeight() {
        let sidebar = document.getElementById('sidebar');    
        let sidebarW = sidebar ? sidebar.offsetWidth : 0;
        appMainWidth.value = window.innerWidth - sidebarW;

        let top = document.getElementById('top');
        let topH = top ? top.offsetHeight : 0;
        appMainHeight.value = window.innerHeight - topH;
    }

</script>

<style lang="scss" scoped>

.menu {
    box-shadow: 1px 0 5px rgba(0, 0, 0, 0.2);
    width: 250px;
}

.el-menu {
    border-right: 0;
}

.el-menu-item.is-active {
  background-color: #5D5FEF !important; /* 设置你想要的颜色 */
  color: #ffffff !important; /* 选中项文字颜色 */
  border-radius: 4px;
}



</style>
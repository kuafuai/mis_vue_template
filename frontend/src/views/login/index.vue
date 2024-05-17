<template>
    <base-wrapper class="bg-color-primary flex-center-center">
        <login @loginSucceess="login_success" ></login>
    </base-wrapper>
</template>

<script setup>

const { proxy } = getCurrentInstance();

function login_success(){
    let fullPath = proxy.$route.fullPath;
    console.log(fullPath)
    if (fullPath.startsWith('/login?redirect=')) {
        let lastPath = fullPath.replace('/login?redirect=', '');
        // 跳转到上次退出的页面
        proxy.$router.push({ path: lastPath });
    } else {
        // 跳转到首页
        proxy.$router.push({ path: '/' });
    }
}

</script>
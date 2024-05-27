import { createRouter, createWebHistory } from 'vue-router'

export const constantRouters = [
    {
        path: '/login',
        meta: {
            isParentView: true,
            title: "登录",
        },
        component: () => import('@/views/login/index.vue'),
    }
    // ,
    // {
    //     path: "/:pathMatch(.*)*",
    //     component: () => import('@/views/error/404.vue'),
    //     hidden: true
    // }
]

const router = createRouter({
    history: createWebHistory(),
    routes: constantRouters,
});

export default router;
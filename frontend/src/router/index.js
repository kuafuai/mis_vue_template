import { createRouter, createWebHashHistory } from 'vue-router'

export const constantRouters = [
    {
        path: '/login',
        meta: {
            isParentView: true,
        },
        component: () => import('@/views/login/index.vue'),
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes: constantRouters,
});

export default router;
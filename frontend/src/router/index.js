import { createRouter, createWebHistory } from 'vue-router'

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
    history: createWebHistory(),
    routes: constantRouters,
});

export default router;
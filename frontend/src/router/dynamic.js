export const dynamicRoutes = [{
        path: '/purchase',
        meta: {
            title: '进货管理表'
        },
        component: ()=> import('@/views/purchase/index.vue'),
    }, {
        path: '/sales',
        meta: {
            title: '销售管理表'
        },
        component: ()=> import('@/views/sales/index.vue'),
    }, {
        path: '/inventory',
        meta: {
            title: '库存管理表'
        },
        component: ()=> import('@/views/inventory/index.vue'),
    }]
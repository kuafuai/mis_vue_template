import router from '@/router';

import { dynamicRoutes } from './dynamic'

// 进度条
import NProgress from 'nprogress'; // 导入 nprogress模块
import 'nprogress/nprogress.css'; // 导入样式
NProgress.configure({ showSpinner: true }); // 显示右上角螺旋加载提示

let hasRouter = false;

router.beforeEach(async (to, from, next) => {
    NProgress.start(); // 开启进度条
    if (hasRouter) {
        next(); // 放行
    } else {
        dynamicRoutes.forEach((e) => router.addRoute(e));
        hasRouter = true;
        next({ ...to, replace: true });
    }
});


// 全局后置钩子
router.afterEach(() => {
    NProgress.done(); // 完成进度条
});
  
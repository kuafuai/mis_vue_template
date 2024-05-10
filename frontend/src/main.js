import { createApp } from 'vue'
import App from './App.vue'
// 自定义样式
import '@/styles/index.scss';

const app = createApp(App);

// 路由
import router from './router'
app.use(router);

// element plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
app.use(ElementPlus,{
  locale: zhCn,
});

// 注册element icon
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

// pinia
import { createPinia } from 'pinia';
const pinia = createPinia();
app.use(pinia);

// 全局组件注册
import myComponent from '@/components/index';
Object.keys(myComponent).forEach((key) => {
  app.component(key, myComponent[key]);
});

// 配置全局api
import api from '@/api';
app.config.globalProperties.$api = api;

// plugins
import plugins from './plugins' 
app.use(plugins)

// 动态路由权限
import '@/router/permission.js';

app.mount('#app');

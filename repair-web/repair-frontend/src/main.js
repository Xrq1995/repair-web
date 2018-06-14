import Vue from 'vue';
import App from './App.vue';
import router from './common/router/router';
import store from './common/store/store';
import VueResource from 'vue-resource';
import ElementUI from 'element-ui';
import $ from 'jquery';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/extra/bootstrap/css/bootstrap.css';
import './assets/extra/bootstrap/js/bootstrap.js';
import './assets/extra/easyui/themes/default/easyui.css';
import './assets/extra/easyui/themes/icon.css';
import './assets/extra/easyui/jquery.easyui.min.js';

Vue.config.productionTip = false;

Vue.use(ElementUI);
Vue.use(VueResource);

/**
 * 配置路由守卫
 */
router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    if (store.state.isLogin) {
      next();
    } else {
      next({
        path: '/login'
      })
    }
  } else {
    next();
  }
});

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app');

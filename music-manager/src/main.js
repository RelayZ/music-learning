import Vue from 'vue'
import App from './App'
import router from './router/index'
import store from './store/index'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'babel-polyfill'
import VCharts from 'v-charts'
import './assets/css/main.css'

Vue.use(ElementUI)
Vue.use(VCharts)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  render: h=>h(App)
})

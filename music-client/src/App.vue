<template>
  <div id="app">    
    <!-- 自定义的组件名字，共有的 -->
    <the-header></the-header>     
    <router-view class="music-content"/>   
  </div>
</template>

<script>
import TheHeader from './components/TheHeader.vue'

export default {
  name: 'App',
  components: {
    TheHeader
    
  }
}
</script>

<style  lang="scss" scoped>
@import './assets/css/app.scss';
</style>

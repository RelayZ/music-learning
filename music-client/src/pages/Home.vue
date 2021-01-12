<template>
  <div class="home">
     <swiper />
  </div>
</template>

<script>
import Swiper from '../components/Swiper' //先引入
export default {
  name: 'home',
  components: {
    Swiper,       //注册了才能用
  },
  data () {
    return {
      
    }
  },
  created () {
   
  },
  methods: {

  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/home.scss';
</style>

<template>
<!-- swiper在下面的css文件定义过 -->
    <div class="swiper">       
        <el-carousel :interval="4000" type="card" height="280px">
            <!-- key指定了index是索引的属性 -->
          <el-carousel-item v-for="(item,index) in swiperList" :key="index"> 
              <img :src="item.picImg" />
          </el-carousel-item>
        </el-carousel>
    </div>
</template>

<script>
import {swiperList} from "../assets/data/swiper";
export default {
    name: "swiper",             //这个组件可以供外界使用了
    data(){
        return {
            swiperList: []              //这些数据在assets/data/swiper.js
        }
    },
    created(){
        this.swiperList = swiperList;       //获取到了数据   等号右边是后面引入的
    }
}
</script>

<style scoped>
@import '../assets/css/swiper.scss';
</style>
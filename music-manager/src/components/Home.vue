<template>
    <div>
        <the-header></the-header>

        <the-aside></the-aside>

        <!-- 这个div是因为header把router-view内容盖住了，来处理的 content-box在main.css定义了 -->
        <!-- content-collapse这个css在改左边侧边栏的宽度 -->
        <!-- 为什么能够main.css起作用？看main.js里面已经引入好了 -->
        <!-- :class="{'content-collapse':collapse}   content-collapse这个css样式在main.css里面，看collapse是true就作用 -->
        <div class="content-box" :class="{'content-collapse':collapse}">
            <router-view></router-view>
        </div>
        
    </div>
</template>

<script>
import TheHeader from './TheHeader'
import TheAside from './TheAside'
import bus from "../assets/js/bus"

export default {
    components:{
        TheHeader,
        TheAside
    },
    data(){
        return {
            collapse: false
        }
    },
    //创建完页面自动
    created(){      //监控collapse是为了改css样式，不然按下收缩按钮会留下空白
        //通过Bus进行组件间的通信，来折叠侧边栏
        //接受  收到的信息是msg  发送的人是在theheader.vue里面的$emit方法
        bus.$on('collapse',msg => { 
            //data里面的collapse
            this.collapse=msg;      //
        })  
    }
}
</script>
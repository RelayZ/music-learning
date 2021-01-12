<template>
    <!-- 公共头部   下面引入了 -->
    <div class="the-header">
        <div class="header-logo" @click="goHome">
            <svg class="icon">
                <!-- 小图标 -->
                <use xlink:href = "#icon-erji">
                </use>
            </svg>
            <span>music</span>
        </div>

        <ul class="navbar">
            <li :class="{active: item.name == activeName}"  v-for="item in navMsg" :key="item.path"  @click="goPage(item.path,item.name)">
                {{item.name}}
            </li>
        </ul>
    </div>
    
</template>

<script>
//！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
//！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
//！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
//！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
//这个中括号不加就不行，不报错，也不能用！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
import {navMsg} from "../assets/data/header";
import {mapGetters} from 'vuex';         //这个就可以在整个工程到处使用get方法了 
export default {
    name: 'the-header',         //本组件可以给外界使用了，就叫做name
    data(){
        return {
            navMsg : []         //在下面created方法保存assets/data/header.js里面的数据
        }
    },
    computed:{
        ...mapGetters([
            'activeName'            //这样就引入了activeName  可以当作自己的数据了
        ])
    },
    created(){
        this.navMsg = navMsg;  
    },
    methods:{
        goHome(){
            this.$router.push({path:"/"});      //跳转到根目录  这些跳转得在src/router/index.js里面进行配置
        },
        goPage(path,name){
            this.$store.commit('setActiveName',name);       //隔空调用store.configure.js提供的全局set方法，在缓存中得到了当前选中的菜单
            this.$router.push({path:path});     //跳转到path目录  这些跳转得在src/router/index.js里面进行配置
        }
    }
}
</script>

<style lang="scss" scoped>
@import "../assets/css/the-header.scss"
</style>
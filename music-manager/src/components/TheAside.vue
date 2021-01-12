<template>
    <div class="sidebar">
        <el-menu class="sidebar-el-menu"
            :default-active="OnRoutes"
            :collapse="collapse"
            background-color="#334256"
            text-color="#ffffff"
            active-text-color="#20a0ff"
            router
        >
         <!-- 上面的router跟下面的：index是一起用的，就是跳到：index去 -->
            <template v-for="item in items">
                <template>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        {{item.title}}
                    </el-menu-item>
                </template>
            </template>


        </el-menu>
    </div>
</template>


<style scoped>
.sidebar{
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    background-color: #334256;
    overflow-y: scroll;
}
/* :not(.el-menu--collapse)  当collapse是false的时候，结合前面的not，负负得正，才是起作用
    折叠状态不使用下面这个css
    点击左上角之后收缩
 */
.sidebar-el-menu:not(.el-menu--collapse){
    width: 150px;
}
/* 收缩之后把那条滚动条去掉 */
.sidebar::-webkit-scrollbar{       
    width: 0;
}
/* sidebar下面的ul标签 去掉旁边稍微高度不统一的情况 */
.sidebar >ul{
    height: 100%;
}

</style>

<script>
/**
 * 注释：1.scope不给别的vue用
 * 
 * 2.display block就是一块
 * 3.overflow-y: 满了可以滑动
 * 4.:default-active 哪个被点击状态
 * 5.router根上面的:default-active="OnRoutes"有点关系，点击了进入哪个页面
 * 6.只能有一个script标签，否则报错
 * */
import bus from "../assets/js/bus"
export default {
    data(){
        return{
            collapse: false,//是否折叠
            items:[ //菜单内容
                {
                    icon: 'el-icon-document', //图标 <i :class="item.icon"></i>
                    index: "Info",  //指向哪一页
                    title: '系统首页'
                },
                {
                    icon: 'el-icon-document',
                    index: "Consumer",  //指向哪一页
                    title: '用户管理'
                },
                {
                    icon: 'el-icon-document',
                    index: "Singer",  //指向哪一页
                    title: '歌手管理'
                },
                {
                    icon: 'el-icon-document',
                    index: "SongList",  //指向哪一页
                    title: '歌单管理'
                },
            ]
        }
    },
    computed:{      //监控变化
        OnRoutes(){
            return this.$route.path.replace('/','');   //看router文件夹下的index.js
        }
    },
    //创建完页面自动
    created(){
        //通过Bus进行组件间的通信，来折叠侧边栏
        //接受  收到的信息是msg  发送的人是在theheader.vue里面的$emit方法
        bus.$on('collapse',msg => { 
            //data里面的collapse
            this.collapse=msg;
        })  
    }
}
</script>
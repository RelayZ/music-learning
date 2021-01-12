<template>
    <div class="header">
        <!-- 折叠左侧的图片 -->
        <div class="collapse-btn" @click="collapseChange">
            <i class="el-icon-menu"></i>
        </div>

        <div class="logo">music后台管理</div>
            
        <div class="header-right">
            <!-- 全屏按钮 -->
            <div class="btn-fullscreen" @click="handleFullScreen">
                <!-- 提示用户全屏按钮的作用 fullscreen是个变量  placement是提示信息放在下面 -->
                <el-tooltip :content="fullscreen?'取消全屏':'全屏'" placement="bottom">
                    <i class="el-icon-rank"></i>
                </el-tooltip>
            </div>
            <!-- 头像 -->
            <div class="user-avator">
                <img src="../assets/img/3283141725800255.jpg" />
            </div>
            <!-- 下拉菜单 可以配置 click 激活或者 hover 激活。 -->
            <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                <span class="el-dropdown-link">
                    <!-- 向下的箭头 上面的css样式顺便把整个下拉箭头变白 -->
                    <i class="el-icon-caret-bottom"></i>
                    {{userName}}    
                </span>                
                <!-- slot向下拉 -->
                <el-dropdown-menu slot="dropdown">
                    <!-- 等会实现上面那个函数 handleCommand(command)  如果判断command等于logout就等于触发了这个菜单   -->
                    <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>

        </div>
    </div>
</template>

<script>
import bus from "../assets/js/bus"
export default {
    data(){
        return {
            collapse: false, //默认是false
            fullscreen: false
        }
    },
    //监视，状态变化
    computed:{
        userName(){
            //获取Login.vue放在本地缓存的localStorage里面的东西
            return localStorage.getItem('userName')
        }
    },
    methods:{
        //侧边栏折叠
        collapseChange(){
            this.collapse=!this.collapse;
            bus.$emit('collapse',this.collapse) //这个会被TheAside.Aue里面的bus.$on('collapse',抓到
        },
        //全屏事件
        handleFullScreen(){
            //当前是全屏
            if(this.fullscreen){
                //浏览器兼容性
                if(document.exitFullscreen){
                    document.exitFullscreen();
                }else if(document.webkitCancelFullScreen){  //Safari Chrome
                    document.webkitCancelFullScreen();
                }else if(document.mozCancelFullScreen){
                    document.mozCancelFullScreen();
                }else if(document.msExitFullScreen){
                    document.msExitFullScreen();
                }
            }
            //非全屏
            else{
                //全部页面元素
                let element = document.documentElement;
                if(element.requestFullscreen){
                    element.requestFullscreen();
                }else if(element.webkitMatchesSelector){
                    element.webkitMatchesSelector();
                }else if(element.mozCancelFullScreen){
                    element.mozCancelFullScreen();
                }else if(element.msRequestFullScreen){
                    element.msRequestFullScreen();
                }
            }
            this.fullscreen = !this.fullscreen;
        },
        //函数 handleCommand(command)  如果判断command等于logout就等于触发了某个下拉菜单
        handleCommand(command){
            if(command == "logout"){
                //去掉缓存
                localStorage.removeItem('userName');
                //是router不是route
                this.$router.push("/");  //回到首页
            }
        }
    }
}
</script>

<style scoped>
.header{
    /* 自动在上面 */
    position: relative;
    background-color: #253041;
    /* 立体感 */
    box-sizing: border-box;
    width: 100%;
    height: 70px;
    font-size: 22px;
    color:#ffffff;
}
.collapse-btn{
    /* 最左边 */
    float: left;
    /* 左 上 */
    padding: 0 21px;
    /* 鼠标移动过来变成手状态 */
    cursor: pointer;
    line-height: 70px;
}

.header .logo{
    /* 靠着微软那个图片对齐 */
    float: left;
    line-height: 70px;
}

.header-right{
    /* 处于右边 */
    float:right;
    padding-right: 50px;
    /* 有好几项  自由布局 平均分配 */
    display: flex;
    height: 70px;
    /* 内容居中 */
    align-items: center;
}

.btn-fullscreen
{
    /* 图片转45° 像全屏按钮 */
    transform: rotate(45deg);
    margin-left: 5px;
    font-size: 24px;
}

/* 图片css样式 */
.user-avator{
    margin-left: 20px;
}
.user-avator img{
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 50%;
}

.user-name{
    margin-left: 10px;
}

.el-dropdown-link{
    /* 白底 手状态 */
    color: #ffffff;
    cursor: pointer;
}
</style>
<template>
<!-- 在main.css中定义过了  800px -->
    <div class="table">
        <!-- crumbs是在main.css里面定义的 -->
        <div class="crumbs">
            <i class="el-icon-tickets">歌曲信息</i>
        </div>
        <!-- container在css中定义过了 -->
        <div class="container">
            <div class="handle-box">
                <!-- 批量删除歌手 -->
                <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
                <!-- 歌曲搜索框 双向绑定了elect_word这个变量，只要输入，我就能知道他的值 -->
                <el-input v-model="select_word" size="mini" placeholder="请输入歌曲名" class="handle-input"></el-input>
                <!-- 点击这个按钮，下面的弹出框会显示出来 为什么只搞一个=true就行，因为下面的弹出框设置了:visible.sync  -->
                <el-button type="primary" size="mini" @click="centerDialogVisible = true">添加歌曲</el-button>
            </div>
        </div>
        <!-- 所有歌曲信息 -->
        <!-- 叫做data是被分页那一块js代码的data函数赋值了 -->
        <el-table size="mini" border style="width:100%" height="680px"
         :data="data" @selection-change="handleSelectionChange">
         <!-- 歌曲多选框 准备删除歌曲 -->
         <el-table-column type="selection" width="40"></el-table-column>
          <el-table-column label="歌曲图片" width="110" align="center">
              <!-- 当前的单元格 score.name scope.introduction.. -->
              <template slot-scope="scope">
                    <div class="song-img">
                        <!-- 相对地址 调用工具类的方法获取绝对地址 -->
                        <img :src="getUrl(scope.row.pic)" style="width:100%"/>
                    </div>
                    <!-- 播放按钮的图片 -->
                    <!-- 状态的改变 -->
                    <div class="play" @click="setSongUrl(scope.row.url,scope.row.name)">
                        <div v-if="toggle == scope.row.name">
                            <svg class="icon">
                                <use xlink:href="#icon-zanting"></use>
                            </svg>
                        </div>
                        <div v-if="toggle != scope.row.name">
                            <svg class="icon">
                                <use xlink:href="#icon-bofanganniu"></use>
                            </svg>
                        </div>
                    </div>
              </template>
          </el-table-column>
          <el-table-column label="歌手-歌名" width="120" align="center" prop="name">
          </el-table-column>
          <el-table-column label="专辑" width="150" align="center" prop="introduction">
          </el-table-column>
          <el-table-column label="歌词" align="center">
              <!-- 跳转到歌曲管理页面，要动态 所以是template里面  参数有两个 -->
              <template slot-scope="scope">
                  <ul style="height:100px;overflow:scroll;">
                      <!-- 通过key指定index是下标（其实默认它也是下标 -->
                      <li v-for="(item,index) in parseLyric(scope.row.lyric)" :key="index">
                          {{item}}
                      </li>
                  </ul>
                  <!-- {{scope.row.lyric}} -->
              </template>
          </el-table-column>
          <!-- 更新图片和歌曲-->
            <el-table-column label="资源更新" align="center" width="100">
                <template slot-scope="scope">
                    <!-- 修改图片的按钮 上传之前 上传成功 -->
                    <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload"
                     :on-success="handleAvatorSuccess">
                        <el-button size="mini">更新图片</el-button>
                    </el-upload>
                    <br/>
                    <!-- 修改歌曲的按钮 上传之前 上传成功 -->
                    <el-upload :action="uploadSongUrl(scope.row.id)" :before-upload="beforeSongUpload"
                     :on-success="handleSongSuccess">
                        <el-button size="mini">更新歌曲</el-button>
                    </el-upload>
                </template>
            </el-table-column>
          <!-- 修改歌手 -->
          <el-table-column label="操作" width="150" align="center">
                    <!-- 用到函数就用插槽slot -->
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页按钮 tableData里面有真的所有的数据，这是从数据库查处理的 -->
        <!-- background就是每个按钮的背景  就是1 2 3 -->
        <!-- vue封装好了  输出内容total,prev,pager,next  总共 上一页 当前页 下一个 -->
        <!-- 点击翻页按钮触发handleCurrentChange函数 -->
        <div class="pagination">
            <el-pagination
            background
            layout = "total,prev,pager,next"
            :current-page="currentPage"
            :page-size="pageSize"
            :total="tableData.length"
            @current-change="handleCurrentChange"
            ></el-pagination>
        </div>

        <!-- 弹出框，添加时 -->
        <!-- .sync是什么意思呢，指的就是同步动态双向的来表示visible的值 -->
        <!-- 关闭窗口这个动作，我们没法用确定的动作去判断这个值，所以用到了vue中的双向绑定的原则，在vue中统一加上了.sync来表示同步的修改了visible的值。 -->
        <el-dialog title="添加歌曲" :visible.sync="centerDialogVisible" width="400px" center>
            <!-- 起个名字 registerForm  label-width歌手名、性别、……这样定义不换行-->
            <!-- registerForm一定要在js的data中定义，不然报错 -->
            <el-form :model="registerForm" ref="registerForm" label-width="80px" action="" id="tf"
            @input="change($event)">
                <div>
                    <label>歌名</label>
                    <!-- 要自己加上v-model  也不是！！！  不然外面的：model留着干嘛用  不加会报错 -->
                    <el-input v-model="registerForm.name" type="text" placeholder="歌名" name="name"></el-input>
                </div>
                <div>
                    <label>专辑</label>
                    <el-input v-model="registerForm.introduction" type="text" placeholder="专辑" name="introduction"></el-input>
                </div>
                <div>
                    <label>歌词</label>
                    <el-input v-model="registerForm.lyric" type="textarea" placeholder="歌词" name="lyric"></el-input>
                </div>
                <div>
                    <label>歌曲上传</label>
                    <!-- 后台就叫做file -->
                    <input type="file" name="file" >
                </div>
            </el-form>       
            <!-- 提交按钮 -->
            <!-- slot="footer 是底部中间 -->
            <span slot="footer">
                <!-- 弹出框关闭 -->
                <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
                <!-- 真正提交 -->
                <el-button size="mini" @click="addSong">确定</el-button>
            </span>                 
        </el-dialog>

        <!-- 弹出框，修改时 -->
        <!-- .sync是什么意思呢，指的就是同步动态双向的来表示visible的值 -->
        <!-- 关闭窗口这个动作，我们没法用确定的动作去判断这个值，所以用到了vue中的双向绑定的原则，在vue中统一加上了.sync来表示同步的修改了visible的值。 -->
        <el-dialog title="修改歌曲" :visible.sync="editVisible" width="400px" center>
            <!-- 起个名字 form  label-width歌手名、性别、……这样定义不换行-->
            <!-- form一定要在js的data中定义，不然报错 -->
            <el-form :model="form" ref="form" label-width="80px">
                <!-- 字段名是name -->
              <el-form-item prop="name" label="歌手-歌名" size="mini">
                  <!-- v-model双向绑定 -->
                  <el-input v-model="form.name" placeholder="歌手名"></el-input>
              </el-form-item> 
              <!-- 字段名是introduction -->
              <el-form-item prop="introduction" label="专辑" size="mini">
                  <!-- v-model双向绑定 type=textrea就可以拉长了 -->
                  <el-input v-model="form.introduction" placeholder="简介"
                  ></el-input>
              </el-form-item>
              <!-- 字段名是lyric -->
              <el-form-item prop="lyric" label="歌词" size="mini">
                  <!-- v-model双向绑定 type=textrea就可以拉长了 -->
                  <el-input v-model="form.lyric" placeholder="歌词" type="textarea"
                  ></el-input>
              </el-form-item>
            </el-form>       
            <!-- 提交按钮 -->
            <!-- slot="footer 是底部中间 -->
            <span slot="footer">
                <!-- 弹出框关闭 -->
                <el-button size="mini" @click="editVisible = false">取消</el-button>
                <!-- 真正提交 -->
                <el-button size="mini" @click="editSave">确定</el-button>
            </span>                 
        </el-dialog>

        <!-- 弹出框，删除时 -->
        <!-- .sync是什么意思呢，指的就是同步动态双向的来表示visible的值 -->
        <!-- 关闭窗口这个动作，我们没法用确定的动作去判断这个值，所以用到了vue中的双向绑定的原则，在vue中统一加上了.sync来表示同步的修改了visible的值。 -->
        <el-dialog title="删除歌曲" :visible.sync="delVisible" width="300px" center>  
            <div align="center">删除不可恢复，是否确定删除</div>
            <!-- 提交按钮 -->
            <!-- slot="footer 是底部中间 -->
            <span slot="footer">
                <!-- 弹出框关闭 -->
                <el-button size="mini" @click="delVisible = false">取消</el-button>
                <!-- 真正提交 -->
                <el-button size="mini" @click="deleteRow">确定</el-button>
            </span>                 
        </el-dialog>
    </div>
</template>

<script>
import {mixin} from '../mixins'
import {mapGetters} from 'vuex'
//小图标
import '../assets/js/iconfont.js';
//工具类放后面
import { delSong, songOfSingerId, updateSong } from '../api/index'


export default {
    mixins: [mixin],    //注册完记得在这里export以下
    data(){     //data是个函数，有人专门分析过 它是函数的原因  貌似是因为冲突
        return{
            //singerId和singerName是SingerPage.vue传过来的
            singerId: '',    //歌手id
            singerName: '', //歌手名
            centerDialogVisible: false, //添加弹框是否显示
            editVisible: false, //编辑弹框是否显示
            delVisible: false, //删除窗口是否显示
            registerForm:{      //添加框
                name: '',
                singerName: '',
                introduction: '',
                lyric: ''
            },
            form:{      //编辑框
                id: '',
                name: '',
                introduction: '',
                lyric: ''
            },
            tableData: [],
            //以下两个变量是为了查询歌手信息  提前准备的
            tempData: [],
            select_word: '',
            //以下是分页
            pageSize: 5, //分页每页大小
            currentPage: 1, //当前第1页
            //删除时
            idx: -1, //当前选择项，一定要有初始值
            //哪些项已经被选中了
            multipleSelection: [],
            toggle:false,//播放按钮的状态
        }
    },
    // 页面加载完了之后使用了下面的data方法，以后el-table的名字叫做data了。通过:data="data"
    computed:{
        //第一页 从第0条开始  第二页 从第5条（默认）开始 这个data也是变量也是函数
        data(){
            return this.tableData.slice((this.currentPage-1) * this.pageSize, this.currentPage * this.pageSize);
        },
        //get方法，给下面的函数调用
        ...mapGetters([
            'isPlay'
        ])
    },
    //监控select_word这个变量，发生了变化，实现模糊查询
    watch:{
        //监控select_word的值，发生变化就执行这个函数 搜索框里面的内容发生变化时，table列表的内容跟着他的内容变化
        select_word: function(){
            if(this.select_word==''){   //用户没查，数据得全部显示，恢复正常
                this.tableData = this.tempData;
            }else{      //用户在查东西,实时反应数据
                this.tableData =[];
                for(let item of this.tempData){
                    if(item.name.includes(this.select_word)){  //表中的数据中包含了输入框的东西
                        this.tableData.push(item);
                    }
                }
            }
        }
    },
    created(){  //页面创建完了之后自动调用 获取所有歌手信息
        //singerPage传来的，要接受以下  在router文件夹下面的index.js里面是叫做routes 这个
        //this.$router 相当于一个全局的路由对象，包含路由相关的属性、对象 (如 history 对象) 和方法，在任何页面都可以通过 this.$router 调用其方法如 push() 、go() 、resolve() 等。
// this.$route 表示当前的路由对象。每一个路由都有一个 route 对象，它是一个局部的对象，可以获取当前路由对应的 name , params, path , query 等属性。
        this.singerId = this.$route.query.id;
        this.singerName = this.$route.query.name;
        this.getData();
    },
    // 生命周期方法
    destroyed(){
        this.$store.commit('setIsPlay',false);
    },
    methods:{
        change(e){
            this.$forceUpdate()
        },
        //获取当前页 这个val就是点下去的页号  并实时改变currentPage
        handleCurrentChange(val){
            this.currentPage = val;
        },
        //查询所有歌曲 放在tableData里面
        //需提前保存现有数据在tempData
        getData(){
            this.tempData=[];
            this.tableData = [];
            //在api里面的方法类
            songOfSingerId(this.singerId).then(res=>{
                this.tempData = res;
                this.tableData = res;
                this.currentPage = 1;
            })
        },
        //添加歌曲
        addSong(){
            //axios前先保存一下
            let _this = this;
            //前面那个表单的id
            var form = new FormData(document.getElementById('tf'));
            form.append('singerId',this.singerId);      //提交表单时，歌手名字自动保存下来了
            form.set('name',this.singerName+'-'+form.get('name')); //歌手加歌名放一起
            if(!form.get('lyric')){
                form.set('lyric','[00:00:00]暂无歌词');
            }
            var req = new XMLHttpRequest();
            req.onreadystatechange = function(){
                //req.readyState == 4获取到返回的完整数据
                //req.status == 200 和后台正常交互完成
                if(req.readyState == 4 && req.status == 200){
                    let res = JSON.parse(req.response);//获取里面的内容
                    if(res.code){
                        _this.getData();
                        _this.registerForm = {};    //清空当前的新增页面
                        //后台返回的成功信息
                        _this.notify("保存成功",'success');
                    }else{
                        _this.notify('保存失败','error');
                    }
                }
            }
            //开始提交 false是同步 true是异步
            req.open('post',`${_this.$store.state.HOST}/song/add`,false);
            req.send(form);
            _this.centerDialogVisible = false;//记得加this，不然谁是谁都不知道
        },
        //弹出编辑页面
        handleEdit(row){
            this.editVisible = true;
            this.form = {
                id: row.id,
                name: row.name,
                introduction: row.introduction,
                lyric : row.lyric
            }
        },
        //保存编辑页面修改的数据
        editSave(){
            //封装参数 准备发给后台。 现在去api文件夹下的index.js开始写调用方法
            let params = new URLSearchParams();
            params.append('id',this.form.id);
            params.append('name',this.form.name);
            params.append('introduction',this.form.introduction);
            params.append('lyric',this.form.lyric);
            //记得引入 import {setSinger} from '../api/index'
            updateSong(params)
            .then(res => {
                if(res.code == 1){
                    this.getData(); //修改了要刷新一下页面
                    //记得引用import {mixin} from '../mixins'   success是蓝框
                    this.notify("修改成功","success");
                }else{
                    this.notify("修改失败","error");
                }
            }).catch(err => {
                console.log(err);
            });
            this.editVisible = false;//记得加this，不然谁是谁都不知道
        },
        //更新图片
        uploadUrl(id){
            //后台地址
             return `${this.$store.state.HOST}/song/updateSongPic?id=${id}`
        },
        //删除一名歌手 调用api的index.js
        deleteRow(){
            delSong(this.idx)
            .then(res => {
                if(res){
                    this.getData(); //修改了要刷新一下页面
                    //记得引用import {mixin} from '../mixins'   success是蓝框
                    this.notify("删除成功","success");
                }else{
                    this.notify("删除失败","error");
                }
            }).catch(err => {
                console.log(err);
            });
            this.delVisible = false;//记得加this，不然谁是谁都不知道
        },
        //解析歌词
        parseLyric(text){
            let lines = text.split("\n");
            let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g  //  斜杠开头,  反斜杠内容反斜杠, 后面是格式[00:00:00], 或者是[00:00:000]
            let result = [];
            for(let item of lines){
                //每行数据
                let value = item.replace(pattern,'')        //把包含正则表达式换位"
                result.push(value=="\n"?"":value);
            }
            return result;
        },
        //上传歌曲之前的校验
        beforeSongUpload(file){
            //获取后缀
            const testMsg = file.name.substring(file.name.lastIndexOf('.') + 1);
            const extension = ( testMsg == 'mp3');
            if(!extension){
                this.$message.error('上传歌曲只能是MP3格式');
                return false;
            }
            return true;
        },
        //上传歌曲成功之后的动作  后台会传数据来
        handleSongSuccess(res){
            let _this = this;
            if(res.code==1){
                _this.getData();
                _this.$notify({
                    title: '上传成功',
                    type: 'success'
                });
            }else{
                _this.$notify({
                    title: '上传失败',
                    type: 'success'
                })
            }
        },
        //更新歌曲url
        uploadSongUrl(id){
            return `${this.$store.state.HOST}/song/updateSongUrl?id=${id}`
        },
        //切换播放  url  name是状态
        setSongUrl(url,name){
            this.toggle = name;
            //给store.js传值 用set方法
            this.$store.commit('setUrl',this.$store.state.HOST + url);
            if(this.isPlay){
                this.$store.commit('setIsPlay',false);
            }else{
                this.$store.commit('setIsPlay',true);
            }
        }
    }
}
</script>

<style scoped>
    .handle-box{
        margin-bottom: 20px;
    }
    .song-img{
        width: 100%;
        height: 80px;
        border-radius: 5px;
        margin-bottom: 5px;
        /* 超出部分就隐藏 */
        overflow: hidden;
    }
    .handle-input{
        width: 300px;
        display: inline-block;
    }
    .pagination{
        /* //灵活布局 */
        display: flex; 
        /* 里面内容居中 */
        justify-content: center;
    }
    /* 播放按钮 */
    .play{
        position: absolute;
        /* 最上方 */
        z-index: 100;
        width: 80px;
        height: 80px;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        top:18px;
        left:15px;
    }
    /* svg图标位置 */
    .icon{
        width:2em;
        height: 2em;
        color: palegreen;
        fill: currentColor;
        overflow: hidden;
    }
</style>
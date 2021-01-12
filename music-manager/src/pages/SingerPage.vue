<template>
<!-- 在main.css中定义过了  800px -->
    <div class="table">
        <!-- container在css中定义过了 -->
        <div class="container">
            <div class="handle-box">
                <!-- 批量删除歌手 -->
                <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
                <!-- 歌手搜索框 双向绑定了elect_word这个变量，只要输入，我就能知道他的值 -->
                <el-input v-model="select_word" size="mini" placeholder="请输入歌手名" class="handle-input"></el-input>
                <!-- 点击这个按钮，下面的弹出框会显示出来 为什么只搞一个=true就行，因为下面的弹出框设置了:visible.sync  -->
                <el-button type="primary" size="mini" @click="centerDialogVisible = true">添加歌手</el-button>
            </div>
        </div>
        <!-- 所有歌手信息 -->
        <!-- 叫做data是被分页那一块js代码的data函数赋值了 -->
        <el-table size="mini" border style="width:100%" height="680px"
         :data="data" @selection-change="handleSelectionChange">
         <!-- 歌手多选框 准备删除歌手 -->
         <el-table-column type="selection" width="40"></el-table-column>
          <el-table-column label="歌手图片" width="110" align="center">
              <!-- 当前的单元格 score.name scope.introduction.. -->
              <template slot-scope="scope">
                    <div class="singer-img">
                        <!-- 相对地址 调用工具类的方法获取绝对地址 -->
                        <img :src="getUrl(scope.row.pic)" style="width:100%"/>
                    </div>
                    <!-- 修改头像的按钮 上传之前 上传成功 -->
                    <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload"
                     :on-success="handleAvatorSuccess">
                        <el-button size="mini">更新图片</el-button>
                    </el-upload>
              </template>
          </el-table-column>
          <el-table-column label="歌手" width="120" align="center" prop="name">
          </el-table-column>
          <el-table-column label="性别" align="center" width="50">
              <!-- 取到当前这一行的数据，下面用工具类来转换以下，这里就得使用template里面加给插槽slot  注意获取的时候要加row -->
            <template slot-scope="scope">
                {{changeSex(scope.row.sex)}}
            </template>
          </el-table-column>
          <el-table-column label="生日" width="120" align="center">
              <template slot-scope="scope">
                {{attachBirth(scope.row.birth)}}
              </template>
          </el-table-column>
          <el-table-column label="地区" width="100" align="center" prop="location">
          </el-table-column>
          <el-table-column label="简介">
              <template slot-scope="scope">
                <p style="height:100px;overflow:scroll">{{scope.row.introduction}}</p>
              </template>
          </el-table-column>
          <el-table-column label="歌曲管理" width="110" align="center">
              <!-- 跳转到歌曲管理页面，要动态 所以是template里面  参数有两个 -->
              <template slot-scope="scope">
                  <el-button size="mini" @click="songEdit(scope.row.id,scope.row.name)">歌曲管理</el-button>
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
        <el-dialog title="添加歌手" :visible.sync="centerDialogVisible" width="400px" center>
            <!-- 起个名字 registerForm  label-width歌手名、性别、……这样定义不换行-->
            <!-- registerForm一定要在js的data中定义，不然报错 -->
            <el-form :model="registerForm" ref="registerForm" label-width="80px">
                <!-- 字段名是name -->
              <el-form-item prop="name" label="歌手名" size="mini">
                  <!-- v-model双向绑定 -->
                  <el-input v-model="registerForm.name" placeholder="歌手名"></el-input>
              </el-form-item>
              <!-- 字段名是性别 -->
              <el-form-item label="性别" size="mini">
                  <!-- v-model双向绑定 -->
                  <el-radio-group v-model="registerForm.sex">
                    <el-radio :label="0">女</el-radio>
                    <el-radio :label="1">男</el-radio>
                    <el-radio :label="2">组合</el-radio>
                    <el-radio :label="3">不明</el-radio>
                  </el-radio-group>
              </el-form-item>
              <!-- 字段名是birth 生日转换很麻烦，下面在js  saveUser里面单独处理 -->
              <el-form-item label="生日" size="mini">
                  <!-- v-model双向绑定 日期选择器 -->
                  <el-date-picker v-model="registerForm.birth" placeholder="选择日期"
                                type="date" style="width:100%"
                    ></el-date-picker>
              </el-form-item>
              <!-- 字段名是location -->
              <el-form-item prop="location" label="地区" size="mini">
                  <!-- v-model双向绑定 -->
                  <el-input v-model="registerForm.location" placeholder="地区"></el-input>
              </el-form-item>
              <!-- 字段名是introduction -->
              <el-form-item prop="introduction" label="简介" size="mini">
                  <!-- v-model双向绑定 type=textrea就可以拉长了 -->
                  <el-input v-model="registerForm.introduction" placeholder="简介"
                   type="textarea"
                  ></el-input>
              </el-form-item>
            </el-form>       
            <!-- 提交按钮 -->
            <!-- slot="footer 是底部中间 -->
            <span slot="footer">
                <!-- 弹出框关闭 -->
                <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
                <!-- 真正提交 -->
                <el-button size="mini" @click="addSinger">确定</el-button>
            </span>                 
        </el-dialog>

        <!-- 弹出框，修改时 -->
        <!-- .sync是什么意思呢，指的就是同步动态双向的来表示visible的值 -->
        <!-- 关闭窗口这个动作，我们没法用确定的动作去判断这个值，所以用到了vue中的双向绑定的原则，在vue中统一加上了.sync来表示同步的修改了visible的值。 -->
        <el-dialog title="修改歌手" :visible.sync="editVisible" width="400px" center>
            <!-- 起个名字 form  label-width歌手名、性别、……这样定义不换行-->
            <!-- form一定要在js的data中定义，不然报错 -->
            <el-form :model="form" ref="form" label-width="80px">
                <!-- 字段名是name -->
              <el-form-item prop="name" label="歌手名" size="mini">
                  <!-- v-model双向绑定 -->
                  <el-input v-model="form.name" placeholder="歌手名"></el-input>
              </el-form-item>
              <!-- 字段名是性别 -->
              <el-form-item label="性别" size="mini">
                  <!-- v-model双向绑定 -->
                  <el-radio-group v-model="form.sex">
                    <el-radio :label="0">女</el-radio>
                    <el-radio :label="1">男</el-radio>
                    <el-radio :label="2">组合</el-radio>
                    <el-radio :label="3">不明</el-radio>
                  </el-radio-group>
              </el-form-item>
              <!-- 字段名是birth -->
              <el-form-item label="生日" size="mini">
                  <!-- v-model双向绑定 日期选择器 一定要加上value-format="yyyy-MM-dd"不然没法处理这格式-->
                  <el-date-picker v-model="form.birth" placeholder="选择日期"
                                type="date" style="width:100%"
                    ></el-date-picker>
              </el-form-item>
              <!-- 字段名是location -->
              <el-form-item prop="location" label="地区" size="mini">
                  <!-- v-model双向绑定 -->
                  <el-input v-model="form.location" placeholder="地区"></el-input>
              </el-form-item>
              <!-- 字段名是introduction -->
              <el-form-item prop="introduction" label="简介" size="mini">
                  <!-- v-model双向绑定 type=textrea就可以拉长了 -->
                  <el-input v-model="form.introduction" placeholder="简介"
                   type="textarea"
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
        <el-dialog title="删除歌手" :visible.sync="delVisible" width="300px" center>  
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
import {delSinger, getAllSinger, setSinger, updateSinger} from '../api/index'
import {mixin} from '../mixins'

export default {
    mixins: [mixin],    //注册完记得在这里export以下
    data(){     //data是个函数，有人专门分析过 它是函数的原因  貌似是因为冲突
        return{
            centerDialogVisible: false, //添加弹框是否显示
            editVisible: false, //编辑弹框是否显示
            delVisible: false, //删除窗口是否显示
            registerForm:{      //添加框
                name: '',
                sex: '',
                birth: '',
                // pic: '',不需要写，有专门功能
                location: '',
                introduction: ''
            },
            form:{      //编辑框
                id: '',
                name: '',
                sex: '',
                birth: '',
                // pic: '',不需要写，有专门功能
                location: '',
                introduction: ''
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
        }
    },
    // 页面加载完了之后使用了下面的data方法，以后el-table的名字叫做data了。通过:data="data"
    computed:{
        //第一页 从第0条开始  第二页 从第5条（默认）开始 这个data也是变量也是函数
        data(){
            return this.tableData.slice((this.currentPage-1) * this.pageSize, this.currentPage * this.pageSize);
        }
    },
    //监控select_word这个变量，发生了变化
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
        this.getData();
    },
    methods:{
        //获取当前页 这个val就是点下去的页号  并实时改变currentPage
        handleCurrentChange(val){
            this.currentPage = val;
        },
        //查询所有歌手 放在tableData里面
        //需提前保存现有数据在tempData
        getData(){
            this.tempData=[];
            this.tableData = [];
            getAllSinger().then(res => {
                this.tempData = res;
                this.tableData = res;
                //回到首页
                this.currentPage = 1;
            })
        },
        //添加歌手
        addSinger(){
            //搞不懂为什么这里可以直接得到birth，而在editSave里面得写日期转换
            let d = this.registerForm.birth
            //月份会小1
            let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
            //封装参数 准备发给后台。 现在去api文件夹下的index.js开始写调用方法
            let params = new URLSearchParams();
            params.append('name',this.registerForm.name);
            params.append('sex',this.registerForm.sex);
            params.append('pic','img/singerPic/hhh.jpg');
            params.append('birth',datetime);
            params.append('location',this.registerForm.location);
            params.append('introduction',this.registerForm.introduction);
            //记得引入 import {setSinger} from '../api/index'
            setSinger(params)
            .then(res => {
                if(res.code == 1){
                    //修改了要刷新一下页面
                    //记得引用import {mixin} from '../mixins'   success是蓝框
                    this.notify("添加成功","success");
                    this.getData();
                }else{
                    this.notify("添加失败","error");
                }
            }).catch(err => {
                console.log(err);
            });
            this.centerDialogVisible = false;//记得加this，不然谁是谁都不知道
        },
        //弹出编辑页面
        handleEdit(row){
            this.editVisible = true;
            this.form = {
                id: row.id,
                name: row.name,
                sex: row.sex,
                birth: row.birth,
                // pic: '',不需要写，有专门功能
                location: row.location,
                introduction: row.introduction
            }
        },
        //保存编辑页面修改的数据
        editSave(){
            //转换日期 为什么这里得加上new Date
            let d = new Date(this.form.birth);
            //月份会小1
            let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
            //封装参数 准备发给后台。 现在去api文件夹下的index.js开始写调用方法
            let params = new URLSearchParams();
            params.append('id',this.form.id);
            params.append('name',this.form.name);
            params.append('sex',this.form.sex);
            params.append('birth',datetime);
            params.append('location',this.form.location);
            params.append('introduction',this.form.introduction);
            //记得引入 import {setSinger} from '../api/index'
            updateSinger(params)
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
            return `${this.$store.state.HOST}/singer/updateSingerPic?id=${id}`
        },
        //删除一名歌手 调用api的index.js
        deleteRow(){
            delSinger(this.idx)
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
        //转向歌曲管理界面  这个router是router文件夹下面的全局变量  query是参数
        songEdit(id,name){
            this.$router.push({path: '/Song', query:{id,name}})
        }
    }
}
</script>

<style scoped>
    .handle-box{
        margin-bottom: 20px;
    }
    .singer-img{
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
</style>
<template>
    <div class="login-wrap">
        <div class="ms-title">music后台管理登录</div>
        <div class="ms-login">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm"> 
              <el-form-item prop="username">
                <el-input v-model="ruleForm.username" placeholder="用户名："></el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input type="password" v-model="ruleForm.password" placeholder="密码："></el-input>
              </el-form-item>
              <div class="login-btn">
                <el-button type="primary" @click="submitForm">登录</el-button>
              </div>
          </el-form>
        </div>
    </div>
</template>

<script>
import {mixin} from '../mixins/index' //引入公共方法
import {getLoginStatus} from "../api/index"   //引入getLoginStatus方法，在下面的methods調用

//目前el-form的model主要用表单验证的，也就是配合el-form的rules和el-form-item的prop来使用的。
export default {
  mixins:[mixin],   //要有这个，才能使用那个全局办法(弹出登录成功or失败)
  data: function(){
    return {
      ruleForm:{
        username: "admin",
        password: "123"
      },
      rules:{ //规则，这是上面的变量
        username:[//必须填
          {required:true,message:"请输入用户名",trigger:"blur"}   //required 而不是require
        ],
        password:[
          {required:true,message:"请输入密码",trigger:"blur"}
        ]
      },
    };
  },
  methods:{
    submitForm(){
      let params = new URLSearchParams();
      params.append("name",this.ruleForm.username);//從雙向綁定中拿值出來
      params.append("password",this.ruleForm.password);
      getLoginStatus(params)    //調用getLoginStatus方法后有返回值，用then來處理
        .then((res) => {
          if(res.code == 1)    //後端傳來的。那個code是後端提供的接口返回的json格式
          {
            localStorage.setItem('userName',this.ruleForm.username) //把登录成功的信息放到缓存中，方便别人获取
            this.$router.push("/Info"); //利用index.js里面写的路由跳转
            this.notify("登陆成功","success");    //notify是elementui提供的(报错了，在index.js里面重新封装成一个全局方法了)
          }else{
            this.notify("登陆失败","error");
          }
        })
    }
  }
}
</script>

<style scoped>
.login-wrap {
  position: relative;
  background: url('../assets/img/18808245906449965.jpg');
  background-attachment: fixed;
  background-position: center;
  background-size: cover;
  width: 100%;
  height: 100%;
}
.ms-title {
  position: absolute;
  top: 50%;
  width: 100%;
  margin-top: -230px;
  text-align: center;
  font-size: 30px;
  font-weight: 600;
  color: #fff;
}
.ms-login
{
  position: absolute;
  left: 50%;
  top: 50%;
  width:300px;
  height:160px;
  margin-left: -190px;
  margin-top: -150px;
  padding: 40px;
  border-radius: 5px;
  background:#fff;
}
.login-btn
{
  text-align: center;
}
.login-btn button
{
  width: 100%;
  height: 36px;
}
</style>
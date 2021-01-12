/**
 * 工具類
 */

import axios from 'axios';
axios.defaults.timeout = 5000;//后台访问超时时间
axios.defaults.withCredentials = true; //允许跨域
//Content-type响应头
axios.defaults.headers.post['Content-type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
//基础url  url默认开头
axios.defaults.baseURL="http://localhost:8888";


//响应拦截器
axios.interceptors.response.use(
    response => {
        //如果response里面是status是200，说明访问到接口了，否则错误
        if(response.status == 200){
            return Promise.resolve(response); //Promise是异步执行
        }else{
            return Promise.reject(response);    //不往下执行了
        }
    },error => {
        if(error.response.status){
            switch (error.response.status) {
                case 401:       //沒登陸
                    router.replace({
                        path:'/',
                        query:{
                            redirect: router.currentRoute.fullPath
                        }
                    });
                    break;
                case 404: //沒找到
                    break;
            }
            return Promise.reject(error.response);
        }
});


/**
 * 封裝get辦法
 */
export function get(url,params={}){  //相對地阯和參數（默認空)
    return new Promise((resolve,reject)=>{
        axios.get(url,{params:params})
        .then(response => {
            resolve(response.data);
        })
        .catch(err => {
            reject(err);
        })
    })
}

/**
 * 封裝post方法
 */
export function post(url,data={}){  //相對地阯和參數（默認空)
    return new Promise((resolve,reject)=>{
        axios.post(url,data)            //注意写成post方法，而不是看这个自定义的函数的名字是post就以为他是post！
        .then(response => {
            resolve(response.data);
        })
        .catch(err => {
            reject(err);
        })
    })
}
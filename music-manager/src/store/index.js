//等会是在main.js中引用
import Vue from 'vue'
import Vuex from 'vuex'//让vue
Vue.use(Vuex)

const store = new Vuex.Store({      //注意大写
    state:{
        HOST: 'http://127.0.0.1:8888',  //后台地址  并不是这样写就行了，需要在后台的config里面写静态资源配置映射
        isPlay: false, //是否播放
        url: '', //歌曲地址
        id: '', //歌曲id
    },
    //get方法，取上面的值
    getters: {
        isPlay: state => state.isPlay,
        url : state => state.url,
        id: state => state.id
    },
    //set方法
    mutations:{
        setIsPlay :(state,isPlay) => {state.isPlay = isPlay},
        setUrl: (state,url) => {state.url = url},
        setId: (state,id) => {state.id = id}
    }
})

//得导出去 在mixins里面就可以调用了
export default store
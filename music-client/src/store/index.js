import Vue from 'vue'
import Vuex from 'vuex'
import configure from '@/store/configure'
import user from '@/store/user'
import song from '@/store/song'

Vue.use(Vuex)

//记得Store是大写的！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
const store = new Vuex.Store({
    modules:{               //将上面引入的三个 在这里引入
        configure,
        user,
        song,
    },
})

export default store;
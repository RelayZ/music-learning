const configure = {
    state:{
        HOST: 'http://127.0.0.1:8888',      //后台地址根目录
        activeName: '',             //当前选中的菜单名(顶栏)
    },
    getters:{
        activeName: state => {
            let activeName = state.activeName;
            if(!activeName){
                activeName = JSON.parse(window.sessionStorage.getItem('activeName'))
            }
            return activeName;
        }
    },
    //set方法
    mutations:{
        setActiveName: (state,activeName) =>{
            state.activeName = activeName;  
            window.sessionStorage.setItem('activeName',JSON.stringify(activeName))  //存到缓存
        }
    }
}

export default configure
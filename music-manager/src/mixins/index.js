export const mixin ={
    methods:{
        //提示信息
        notify(title,type){
            this.$notify({
                title: title,
                type: type
            })
        },
        //根据相对地址获取绝对地址
        getUrl(url){
            //引用Vuex,Store({})之前，需要先Vue.use(Vuex)
            return `${this.$store.state.HOST}/${url}`
        },
        //获取性别 0-》女 1-》男
        changeSex(value){
            if(value==0){
                return '女';
            }
            if(value==1){
                return '男';
            }
            if(value==2){
                return '组合';
            }
            if(value==3){
                return '不明';
            }
        },
        //获取生日
        attachBirth(val){
            return String(val).substr(0,10);
        },
        //上传图片之前的校验
        beforeAvatorUpload(file){
            const isJpg = (file.type === 'image/jpeg') || (file.type === 'image/png');
            if(!isJpg){
                this.$message.error('上传图片只能是jpg或png格式');
                return false;
            }
            const isLt2M = (file.size/1024/1024)<10;//是否2M
            if(!isLt2M){
                this.$message.error('大小不得超过10MB');
                return false;
            }
            return true;
        },
        //上传图片成功之后的动作  后台会传数据来
        handleAvatorSuccess(res){
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
        //弹出删除窗口
        handleDelete(id){
            this.idx = id;
            this.delVisible = true;
        },
        //把已经选择的项赋值给multipleSelection
        handleSelectionChange(val){
            this.multipleSelection = val;
        },
        //批量删除已经选择的项
        delAll(){
            for(let item of this.multipleSelection){
                //选择到了每一项
                this.handleDelete(item.id);
                //一个一个删除  这个方法在每个业务里面
                this.deleteRow();
            }
            this.multipleSelection = [];
        }
    }
}
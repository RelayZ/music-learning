<template>
    <div>
        <!-- mgb20在公共的css里面定义过了    20是间距  6就是大概占1/4的间距  分成20份 -->
        <el-row :gutter="20" class="mgb20">
            <el-col :span="6">
                <el-card>
                    <div class="grid-content">
                        <div class="grid-cont-center">
                            <div class="grid-num">{{consumerCount}}</div>
                            <div>用户总数</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card>
                    <div class="grid-content">
                        <div class="grid-cont-center">
                            <div class="grid-num">{{songCount}}</div>
                            <div>歌曲总数</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card>
                    <div class="grid-content">
                        <div class="grid-cont-center">
                            <div class="grid-num">{{singerCount}}</div>
                            <div>歌手数量</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card>
                    <div class="grid-content">
                        <div class="grid-cont-center">
                            <div class="grid-num">{{songListCount}}</div>
                            <div>歌单总数</div>
                        </div>
                    </div>
                </el-card>
            </el-col>
        </el-row>

        <!-- 用户部分  饼状图  数据来源于:data-->
        <el-row :gutter="20" class="mgb20">
            <el-col :span="12">
                <h3 class="mgb20">用户性别比例</h3>
                <div style="background-color:white">
                    <ve-pie :data="consumerSex" :theme="options"></ve-pie>
                </div>
            </el-col>
            <el-col :span="12">
                <h3 class="mgb20">歌曲风格比例</h3>
                <div style="background-color:white">
                    <ve-histogram :data="songStyle" ></ve-histogram>
                </div>
            </el-col>
        </el-row>
        <!-- 歌手部分  饼状图  数据来源于:data-->
        <el-row :gutter="20" class="mgb20">
            <el-col :span="12">
                <h3 class="mgb20">歌手性别比例</h3>
                <div style="background-color:white">
                    <ve-pie :data="singerSex" ></ve-pie>
                </div>
            </el-col>
            <el-col :span="12">
                <h3 class="mgb20">歌手国籍比例</h3>
                <div style="background-color:white">
                    <ve-histogram :data="country" :theme="options1"></ve-histogram>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import {getAllConsumer,allSong, getAllSinger, getAllSongList} from '../api/index'
export default {
    data(){
        return {
            consumerCount: 0,       //用户总数
            songCount: 0,           //歌曲总数
            singerCount: 0,         //歌手数量
            songListCount:0,         //歌单数量
            consumer: [],            //从api/index.js里面的方法调用得到的所有用户
            //定义饼状图准备的数据   这些属性都是固定的 columns 和 rows
            consumerSex:{           //用户性别
                columns: ['性别','总数'],
                rows:[
                    {'性别':'男','总数':0},
                    {'性别':'女','总数':0}
                ]
            },
            //性别 饼状图 的颜色
            options:{
                color: ['#87cefa','#ffc0cb']
            },
            //国籍颜色
            options1:{
                color: 'red'
            },
            //定义柱状准备的数据   这些属性都是固定的 columns 和 rows
            songStyle:{         //歌单风格
                columns: ['风格','总数'],
                rows:[              //风格直接写四
                    {'风格':'华语','总数':0},
                    {'风格':'粤语','总数':0},
                    {'风格':'纯音乐','总数':0},
                    {'风格':'轻音乐','总数':0},
                    {'风格':'日韩','总数':0},
                    {'风格':'欧美','总数':0},
                    {'风格':'钢琴','总数':0},
                ]
            },
            //定义柱状准备的数据   这些属性都是固定的 columns 和 rows
            singerSex:{         //歌手性别
                columns: ['性别','总数'],
                rows:[        
                    {'性别':'女','总数':0},
                    {'性别':'男','总数':0},
                    {'性别':'组合','总数':0},
                    {'性别':'不明','总数':0},
                ]
            },
            country:{
                columns: ['国籍','总数'],
                rows:[              //风格直接写四
                    {'国籍':'中国','总数':0},
                    {'国籍':'韩国','总数':0},
                    {'国籍':'日本','总数':0},
                    {'国籍':'朝鲜','总数':0},
                    {'国籍':'新加坡','总数':0},
                    {'国籍':'意大利','总数':0},
                    {'国籍':'挪威','总数':0},
                    {'国籍':'肯尼亚','总数':0},
                ]
            }
        }
    },
    created(){

    },
    //所有都加载完了才调用  在页面渲染完了之后开始调用下面的方法，这些方法又去访问api/index.js里面的方法访问数据库，然后就有了一些数据
    mounted(){
        this.getConsumer();
        this.getSong();
        this.getSinger();
        this.getSongList();
    },
    methods: {
        getConsumer() {                 
            getAllConsumer().then(res => {          //用户总数      被饼状图的数据进行补充
                this.consumer = res;
                this.consumerCount = res.length;
                this.consumerSex.rows[0]['总数'] = this.setSex(1,this.consumer);
                this.consumerSex.rows[1]['总数'] = this.setSex(0,this.consumer);
            })
        }, 
        setSex(sex,val){           //根据性别获取用户数、歌手数
            let count = 0;
            for(let item of val){
                if(sex == item.sex){
                    count++;
                }
            }
            return count;
        },
        getSong(){                           //歌曲总数
            allSong().then(res => {
                this.songCount = res.length;
            })
        },
        getSinger(){                     //歌手总数
            getAllSinger().then(res => {
                this.singerCount = res.length;
                //顺便对图形进行装数据
                this.singerSex.rows[0]['总数'] = this.setSex(0,res);
                this.singerSex.rows[1]['总数'] = this.setSex(1,res);
                this.singerSex.rows[2]['总数'] = this.setSex(2,res);
                this.singerSex.rows[3]['总数'] = this.setSex(3,res);
                //国籍
                for(let item of res){
                    this.getByCountry(item.location);            //某一特定的国籍
                }
            })
        },
        getSongList(){                          //歌单总数
            getAllSongList().then(res => {
                this.songListCount = res.length;
                for(let item of res){
                    this.getByStyle(item.style);            //某一特定的风格
                }
            })
        },
        getByStyle(style){           //根据性别获取用户数
            for(let item of this.songStyle.rows){           //遍历每一行，遇到符合资格的直接累加，类型是写死的
                //if(style == item['风格']){
                if(style.includes(item['风格'])){
                    item['总数']++;
                }
            }
        },
        getByCountry(location){           //根据国籍获取歌手数
            for(let item of this.country.rows){           //遍历每一行，遇到符合资格的直接累加，类型是写死的
                if(location.includes(item['国籍'])){
                    item['总数']++;
                }
            }
        },
    }
}
</script>

<style scoped>
/* 对用户总数那一块的调整 */
.grid-content{
    display: flex;
    align-content: center;
    height: 50px;
}

.grid-cont-center{
    flex:1;
    text-align: center;
    font-size: 14px;
    color: darkgray;
}

.grid-num{
    font-size: 30px;
    font-weight: bold;
}
</style>
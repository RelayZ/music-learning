<template>
    <!-- 歌曲播放插件 -->
    <div class="song-audio">
        <audio id="player"
            :src="url" 
            controls = "controls" 
            preload = "true" 
            @canplay="startPlay" 
            @ended="ended">
        </audio>
    </div>
</template>

<script>

import {mapGetters} from 'vuex'; //mapGetter可以监控store里面的get方法

export default {
    name: 'song-audio',
    //监控缓存中的值
    computed:{
      ...mapGetters([
          'id',
          'url',
          'isPlay'
      ])  
    },
    //监听播放还是暂停，实时的
    watch:{
        isPlay: function(){
            this.togglePlay();
        }
    },
    methods:{
        //开始，暂停
        togglePlay(){
            let player = document.querySelector('#player');
            if(this.isPlay){
                player.play();
            }else{
                player.pause();
            }
        },
        //获取链接后准备播放
        startPlay(){
            let player = document.querySelector('#player');
            //开始播放
            player.play();
        },
        //播放完成之后触发
        ended(){
            this.isPlay = false;
        },
    }
}
</script>

<style>
    .song-audio{
        /* 设置成none就看不见了 */
        display: none;
    }
</style>
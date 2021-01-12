import {get,post} from "./http"

//判斷管理員是否登陸成功(接口，現在去在Login.Vue裏面調用一下它)
export const getLoginStatus = (params) => post(`/admin/login/status`,params)//const供其他vue訪問 tab上邊的點


// ============================歌手相关===============================
//查询歌手
export const getAllSinger =()=> get(`singer/allSinger`);
//添加歌手
export const setSinger = (params) => post(`singer/add`,params);
//编辑（修改）歌手
export const updateSinger = (params) => post(`singer/update`,params);
//编辑（修改）歌手  get方法是这样传参
export const delSinger = (id) => get(`singer/delete?id=${id}`);


// ============================歌曲相关===============================
//根据歌手id查找歌曲 
export const songOfSingerId = (id) =>get(`song/singer/detail?singerId=${id}`);
//编辑（修改）歌曲
export const updateSong = (params) => post(`song/update`,params);
//编辑（删除）歌手  get方法是这样传参
export const delSong = (id) => get(`song/delete?id=${id}`);
//根据歌单id查询歌曲对象
export const songOfSongId = (id) =>get(`song/detail?songId=${id}`);
//根据歌曲名获取歌曲对象
export const songOfSongName = (songName) =>get(`song/songOfSongName?songName=${songName}`);
//查询所有歌曲
export const allSong = () =>get(`song/allSong`);

// ============================歌单相关===============================
//查询歌单
export const getAllSongList =()=> get(`songList/allSongList`);
//添加歌单
export const setSongList = (params) => post(`songList/add`,params);
//编辑（修改）歌单
export const updateSongList = (params) => post(`songList/update`,params);
//编辑（修改）歌单  get方法是这样传参
export const delSongList = (id) => get(`songList/delete?id=${id}`);



// ============================歌单的歌曲相关===============================
//根据歌单id查询歌曲列表
export const listSongDetail = (songListId) => get(`listSong/detail?songListId=${songListId}`);
//给歌单增加歌曲
export const listSongAdd = (params) => post(`listSong/add`,params);
//输出歌单里面的歌曲
export const delListSong = (songId,songListId) => get(`listSong/delete?songId=${songId}&songListId=${songListId}`);


// ============================用户相关===============================
//查询用户
export const getAllConsumer =()=> get(`consumer/allConsumer`);
//添加用户
export const setConsumer = (params) => post(`consumer/add`,params);
//编辑（修改）用户
export const updateConsumer = (params) => post(`consumer/update`,params);
//编辑（修改）用户  get方法是这样传参
export const delConsumer = (id) => get(`consumer/delete?id=${id}`);
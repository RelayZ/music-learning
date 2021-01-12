package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.Singer;
import com.javaclimb.music.domain.Song;
import com.javaclimb.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import utils.Consts;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 歌曲管理controller
 */
@RequestMapping("song")
@RestController
public class SongController {
    @Autowired
    private SongService songService;

    /**
     * 添加歌曲
     * 包括mp3文件
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addSong(HttpServletRequest request, @RequestParam("file")MultipartFile mpFile){
        //下面可以简化为接受Song对象，就不用一个一个自己写了
        JSONObject jsonObject = new JSONObject();
        String singerId = request.getParameter("singerId").trim();
        String name = request.getParameter("name").trim();
        String introduction = request.getParameter("introduction").trim();
        //当前时间自动
        String pic = "/img/songPic/tubiao.jpg";//默认图片
        String lyric = request.getParameter("lyric").trim();
        //上传歌曲图片 mpFile
        if(mpFile.isEmpty()){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "歌曲上传失败");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis()+mpFile.getOriginalFilename();//避免重名
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")
                +"song";//文件路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            //新增路径
            file1.mkdir();
        }
        //实际文件路径
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库的相对文件路径
        String storeUrlPath = "/song/"+fileName;
        //真正上传
        try {
            mpFile.transferTo(dest);
            Song song = new Song();
            song.setSingerId(Integer.parseInt(singerId));
            song.setName(name);
            song.setIntroduction(introduction);
            song.setPic(pic);
            song.setLyric(lyric);
            song.setUrl(storeUrlPath);
            boolean flag = songService.insert(song);
            if(flag){   //保存成功
                jsonObject.put(Consts.CODE, 1);
                jsonObject.put(Consts.MSG, "保存成功");
                jsonObject.put("avator",storeUrlPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "保存失败");
        } catch (IOException e) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "保存失败"+e.getMessage());
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 根据歌手id查询歌曲
     */
    @RequestMapping(value = "/singer/detail",method = RequestMethod.GET)
    public Object songOfSingerId(HttpServletRequest request){
        String singerId = request.getParameter("singerId");
        return songService.songOfSingerId(Integer.parseInt(singerId));
    }

    /**
     * 修改歌曲
     */
    @RequestMapping(value ="/update",method = RequestMethod.POST)
    public Object updateSinger(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        String name = request.getParameter("name").trim();//获取前端传来的数据
        String introduction = request.getParameter("introduction").trim();
        String lyric = request.getParameter("lyric").trim();
        Song singer = new Song();
        singer.setId(Integer.parseInt(id));
        singer.setName(name);
        singer.setIntroduction(introduction);
        singer.setLyric(lyric);
        boolean flag = songService.update(singer);
        if(flag){
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "修改失败");
        return jsonObject;
    }

    /**
     * 删除歌曲
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteSinger(HttpServletRequest request){
        String id = request.getParameter("id").trim();

        //在这里还得删除磁盘上的歌曲，先查询到数据库对应的文件地址，删除它再进行下面的代码，
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")
                +"song";
        //数据库路径  如：/song1610200095580醒着醉.mp3
        String dbPath = songService.getUrlById(Integer.parseInt(id));
        //实际文件在磁盘路径   虽然file.separator是\  数据库是/  但是貌似有自动校正功能?
        String realPath = filePath+dbPath;
        File file = new File(realPath);
        if(file.delete()) {
            System.out.println("歌曲的mp3文件在磁盘上删除成功....");
        }else{
            System.out.println("歌曲的mp3文件在磁盘上删除失败....");
        }

        JSONObject jsonObject = new JSONObject();

        boolean flag = songService.delete(Integer.parseInt(id));
        return flag;
    }

    /**
     * 更新歌曲图片
     */
    @RequestMapping(value = "/updateSongPic",method = RequestMethod.POST)
    public Object updateSongPic(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "文件上传失败");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();//避免重名
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")
                +"img"+System.getProperty("file.separator")+"songPic";//文件路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            //新增路径
            file1.mkdir();
        }
        //实际文件路径
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库的相对文件路径
        String storeAvatorPath = "/img/songPic/"+fileName;
        //真正上传
        try {
            avatorFile.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setPic(storeAvatorPath);
            boolean flag = songService.update(song);
            if(flag){   //保存成功
                jsonObject.put(Consts.CODE, 1);
                jsonObject.put(Consts.MSG, "上传成功");
                jsonObject.put("pic",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "上传失败");
        } catch (IOException e) {
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "上传失败"+e.getMessage());
            e.printStackTrace();
        }finally {
            return jsonObject;
        }
    }

    /**
     * 更新歌曲
     */
    @RequestMapping(value = "/updateSongUrl",method = RequestMethod.POST)
    public Object updateSongUrl(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "文件上传失败");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();//避免重名
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"song";//文件路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            //新增路径
            file1.mkdir();
        }
        //实际文件路径
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库的相对文件路径
        String storeAvatorPath = "/song/"+fileName;
        //真正上传
        try {
            avatorFile.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setUrl(storeAvatorPath);
            boolean flag = songService.update(song);
            if(flag){   //保存成功
                jsonObject.put(Consts.CODE, 1);
                jsonObject.put(Consts.MSG, "上传成功");
                jsonObject.put("avator",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "上传失败");
        } catch (IOException e) {
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "上传失败"+e.getMessage());
            e.printStackTrace();
        }finally {
            return jsonObject;
        }
    }


    /**
     * 根据歌曲id查询歌曲对象
     */
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Object detail(HttpServletRequest request){
        String id = request.getParameter("songId").trim();          //主键
        return songService.selectByPrimaryKey(Integer.parseInt(id));
    }

    /**
     * 根据歌曲id查询歌曲对象
     */
    @RequestMapping(value = "/songOfSongName",method = RequestMethod.GET)
    public Object songOfSongName(HttpServletRequest request){
        String songName = request.getParameter("songName").trim();          //主键
        return songService.songOfName(songName);
    }

    /**
     * 查询所有歌曲
     */
    @RequestMapping(value = "/allSong",method = RequestMethod.GET)
    public Object allSong(HttpServletRequest request){
        return songService.allSong();
    }
}

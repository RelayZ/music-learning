package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Song;
import com.javaclimb.music.domain.Song;
import java.util.List;

public interface SongMapper {
    /**
     * 增加
     */
    public int insert(Song singer);

    /**
     * 修改
     */
    public int update(Song singer);

    /**
     * 根据id查找歌曲的url，方便磁盘删除
     */
    public String getUrlById(Integer id);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据主键查询
     */
    public Song selectByPrimaryKey(Integer id);

    /**
     * 查询所有歌曲
     */
    public List<Song> allSong();

    /**
     * 根据歌名 查询列表
     */
    public List<Song> songOfName(String name);

    /**
     * 根据歌手id查询
     */
    public List<Song> songOfSingerId(Integer id);
}
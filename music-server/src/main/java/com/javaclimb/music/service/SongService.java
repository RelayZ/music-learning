package com.javaclimb.music.service;

import com.javaclimb.music.domain.Song;

import java.util.List;

/**
 * 歌曲service接口
 */
public interface SongService {

    /**
     * 增加 这里改了dao的接口，从int改为boolean
     */
    public boolean insert(Song song);

    /**
     * 修改 这里改了dao的接口，从int改为boolean
     */
    public boolean update(Song song);

    /**
     * 根据id查找歌曲的url，方便磁盘删除
     */
    public String getUrlById(Integer id);

    /**
     * 删除 这里改了dao的接口，从int改为boolean
     */
    public boolean delete(Integer id);

    /**
     * 根据主键查询
     */
    public Song selectByPrimaryKey(Integer id);

    /**
     * 查询所有歌
     */
    public List<Song> allSong();

    /**
     * 根据歌名 模糊查询列表
     */
    public List<Song> songOfName(String name);

    /**
     * 根据歌手id查询
     */
    public List<Song> songOfSingerId(Integer id);




}

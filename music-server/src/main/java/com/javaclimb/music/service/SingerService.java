package com.javaclimb.music.service;

import com.javaclimb.music.domain.Singer;

import java.util.List;

/**
 * 歌手service接口
 */
public interface SingerService {
    /**
     * 增加 这里改了dao的接口，从int改为boolean
     */
    public boolean insert(Singer singer);

    /**
     * 修改 这里改了dao的接口，从int改为boolean
     */
    public boolean update(Singer singer);

    /**
     * 删除 这里改了dao的接口，从int改为boolean
     */
    public boolean delete(Integer id);

    /**
     * 根据主键查询
     */
    public Singer selectByPrimaryKey(Integer id);

    /**
     * 查询所有歌手
     */
    public List<Singer> allSinger();

    /**
     * 根据歌手名 模糊查询列表
     */
    public List<Singer> singerOfName(String name);

    /**
     * 根据性别查询
     */
    public List<Singer> singerOfSex(Integer sex);


}

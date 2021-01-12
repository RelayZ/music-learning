package com.javaclimb.music.service.impl;

import com.javaclimb.music.dao.SongMapper;
import com.javaclimb.music.domain.Song;
import com.javaclimb.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.MediaSize;
import java.util.List;

/**
 * 歌曲service实现类
 */
@Service
public class SongServiceImpl implements SongService {

    @Autowired
    public SongMapper songMapper;

    /**
     * 增加 这里改了dao的接口，从int改为boolean
     *
     * @param song
     */
    @Override
    public boolean insert(Song song) {
        return songMapper.insert(song)>0;
    }

    /**
     * 修改 这里改了dao的接口，从int改为boolean
     *
     * @param song
     */
    @Override
    public boolean update(Song song) {
        return songMapper.update(song)>0;
    }

    /**
     * 根据id查找歌曲的url，方便磁盘删除
     *
     * @param id
     */
    @Override
    public String getUrlById(Integer id) {
        return songMapper.getUrlById(id);
    }

    /**
     * 删除 这里改了dao的接口，从int改为boolean
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return songMapper.delete(id)>0;
    }

    /**
     * 根据主键查询
     *
     * @param id
     */
    @Override
    public Song selectByPrimaryKey(Integer id) {
        return songMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有歌
     */
    @Override
    public List<Song> allSong() {
        return songMapper.allSong();
    }

    /**
     * 根据歌名 模糊查询列表
     *
     * @param name
     */
    @Override
    public List<Song> songOfName(String name) {
        return songMapper.songOfName(name);
    }

    /**
     * 根据歌手id查询
     *
     * @param id
     */
    @Override
    public List<Song> songOfSingerId(Integer id) {
        return songMapper.songOfSingerId(id);
    }
}

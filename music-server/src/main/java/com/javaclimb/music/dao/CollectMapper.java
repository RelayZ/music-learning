package com.javaclimb.music.dao;

import java.util.Date;
import java.util.List;

import com.javaclimb.music.domain.Collect;
import org.apache.ibatis.annotations.Param;

public interface CollectMapper {
    int deleteByPrimaryKey(@Param("id") Integer id, @Param("createTime") Date createTime);

    int insert(Collect record);

    Collect selectByPrimaryKey(@Param("id") Integer id, @Param("createTime") Date createTime);

    List<Collect> selectAll();

    int updateByPrimaryKey(Collect record);
}
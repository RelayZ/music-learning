package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Rank;
import java.util.List;

public interface RankMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rank record);

    Rank selectByPrimaryKey(Integer id);

    List<Rank> selectAll();

    int updateByPrimaryKey(Rank record);
}
package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 管理员dao
 */
@Repository
public interface AdminMapper {
    /**
     * 验证密码是否正确
     */
    public int verifyPassword(@Param("username") String username,@Param("password") String password);

    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    Admin selectByPrimaryKey(Integer id);

    List<Admin> selectAll();

    int updateByPrimaryKey(Admin record);
}
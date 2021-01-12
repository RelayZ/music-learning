package com.javaclimb.music.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Song implements Serializable {
    /*主键 */
    private Integer id;
    /* 歌手id*/
    private Integer singerId;
    /*歌号*/
    private String name;
    /*简介*/
    private String introduction;
    /*创建时间*/
    private Date createTime;
    /*更新事件*/
    private Date updateTime;
    /*歌曲图片*/
    private String pic;
    /*歌曲地址*/
    private String url;
    /*歌词*/
    private String lyric;

    private static final long serialVersionUID = 1L;

}
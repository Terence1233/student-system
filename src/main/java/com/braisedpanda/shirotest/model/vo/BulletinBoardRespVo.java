package com.braisedpanda.shirotest.model.vo;

import lombok.Data;

/**
 * @version 1.0
 * @Description:
 * @Author: 赵宝强
 * @CreateTime: 2021/1/30 13:18
 */
@Data
public class BulletinBoardRespVo {

    private Integer id;

    private Integer createUserId;

    private String article_content;

    private String title;

    private Integer userVisitCount;

    private Integer isRecommend;

    private String createTime;
}

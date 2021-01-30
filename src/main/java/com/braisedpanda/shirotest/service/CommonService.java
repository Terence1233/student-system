package com.braisedpanda.shirotest.service;

import com.braisedpanda.shirotest.model.vo.BulletinBoardRespVo;

import java.util.List;

/**
 * @version 1.0
 * @Description:
 * @Author: 赵宝强
 * @CreateTime: 2021/1/30 14:46
 */
public interface CommonService {

    List<BulletinBoardRespVo> getBoard();
}

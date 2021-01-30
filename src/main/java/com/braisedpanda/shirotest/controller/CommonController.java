package com.braisedpanda.shirotest.controller;

import com.braisedpanda.shirotest.model.vo.BulletinBoardRespVo;
import com.braisedpanda.shirotest.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @Description:
 * @Author: 赵宝强
 * @CreateTime: 2021/1/30 13:16
 */
@RestController
@Slf4j
public class CommonController {

    @Resource
    private CommonService commonService;

    @GetMapping("/bulletin/board")
    public List<BulletinBoardRespVo> getBoard() {
        return commonService.getBoard();
    }
}

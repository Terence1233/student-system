package com.braisedpanda.shirotest.mapper;

import com.braisedpanda.shirotest.model.po.Nation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NationMapper extends tk.mybatis.mapper.common.Mapper<Nation>{

//    Nation getNationById(String nationId);
//
//    //查询所有的nation
//    List<Nation> listNations();
}

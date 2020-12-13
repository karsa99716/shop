package com.gjc.dao;

import com.gjc.utils.JDBCUtils;

import java.util.List;
import java.util.Map;

public class PCADao {
    public List<Map<String,Object>> getProvinces(){
        String sql = "select * from provinces";
        return JDBCUtils.select(sql);
    }
    public List<Map<String,Object>> getCities(String provinceid){
        String sql = "select * from cities where provinceid = ?";
        return JDBCUtils.select(sql,provinceid);
    }
    public List<Map<String,Object>> getAreas(String cityid){
        String sql = "select * from areas where cityid = ?";
        return JDBCUtils.select(sql,cityid);
    }
}

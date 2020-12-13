package com.gjc.dao;

import com.gjc.domain.Address;
import com.gjc.utils.JDBCUtils;

import java.util.List;
import java.util.Map;

public class AddressDao {
    //增加一条记录
    public void add(Address address){
        String sql = "insert into address values(null,?,?,?,?,?,?,?,?)";
        Object params[]={
                address.getAddressname(),address.getProvince(),address.getCity(),address.getArea(),
                address.getPostcode(),address.getReceiver(),address.getPhone(),address.getVipid(),
        };
        int id = ((Number) JDBCUtils.insert(sql,params)).intValue();
        address.setAddressid(id);
    }

    public List<Map<String, Object>> findAll(int vipid) {
        String sql = "select * from address where vipid = ?";
        return JDBCUtils.select(sql,vipid);
    }

    public void deleteById(int addressid) {
        String sql = "delete from address where addressid = ?";
        JDBCUtils.update(sql,addressid);
    }

    public Address findById(int addressid) {
        String sql = "select * from address where addressid = ?";
        Address address = JDBCUtils.selectToBean(Address.class,sql,addressid);
//        Map<String,Object> map = JDBCUtils.select(sql,addressid).get(0);
//        Address address = new Address();
//        address.setAddressid((Integer) map.get("addressid"));
//        address.setAddressname((String) map.get("addressname"));
//        address.setPhone((String) map.get("phone"));
//        address.setProvince((String) map.get("province"));
//        address.setCity((String) map.get("city"));
//        address.setArea((String) map.get("area"));
//        address.setPostcode((String) map.get("postcode"));
//        address.setReceiver((String) map.get("receiver"));
//        address.setVipid((Integer) map.get("vipid"));
        return address;
    }
}

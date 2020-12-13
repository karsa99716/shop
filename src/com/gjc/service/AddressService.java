package com.gjc.service;

import com.gjc.dao.AddressDao;
import com.gjc.domain.Address;

import java.util.List;
import java.util.Map;

public class AddressService {
    private AddressDao addressDao = new AddressDao();
    public void add(Address address){
        addressDao.add(address);
    }

    public List<Map<String,Object>> findAll(int vipid){
        return addressDao.findAll(vipid);
    }

    public void deleteById(int addressid){
        addressDao.deleteById(addressid);
    }

    public Address findById(int addressid){
        return addressDao.findById(addressid);
    }
}

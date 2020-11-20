package com.ljx.service;

import com.ljx.dao.ProductDao;
import com.ljx.domain.Page;
import com.ljx.domain.Product;

import java.util.List;
import java.util.Map;

public class ProductService {
    private ProductDao productDao = new ProductDao();

    public void add(Product product) {
        productDao.add(product);
    }

    //读取记录列表
    public Page findAll(int currentPage,String skey,String svalue){
        /*
        1.获取所有的记录个数，调用Dao层的方法
        2.当记录个数为0时，不需要读取记录
        3.个数大于0，创建Page对象
        4.调用Dao的findAll方法进行读取
         */
        int totalSize = productDao.findCount(skey,svalue);
        Page page = new Page(currentPage,totalSize);
        if (totalSize>0){
            List<Map<String,Object>> list = productDao.findAll(page.getStartIndex(),page.getPageSize(),skey,svalue);
            page.setList(list);
        }
        return page;
    }

    //用于排序和查找读取列表
    public Page findAll(int currentPage,String skey,String svalue,String sortkey,String sort){
        /*
        1.获取所有的记录个数，调用Dao层的方法
        2.当记录个数为0时，不需要读取记录
        3.个数大于0，创建Page对象
        4.调用Dao的findAll方法进行读取
         */
        int totalSize = productDao.findCount(skey,svalue);
        Page page = new Page(currentPage,totalSize);
        if (totalSize>0){
            List<Map<String,Object>> list = productDao.findAll(page.getStartIndex(),page.getPageSize(),skey,svalue,sortkey,sort);
            page.setList(list);
        }
        return page;
    }


    //读取记录列表
    public List<Map<String,Object>> findIndex(){
        /*
        1.获取1-12条记录
         */
        return productDao.findAll(0,12);
    }

    //通过id获取product的记录
    public Map<String,Object> findById(int productid){
        return productDao.findById(productid);
    }

    //修改
    public void update(Product product){
        productDao.update(product);
    }

    //删除
    public void delete(int productid){
        productDao.delete(productid);
    }

    //删除多条记录
    public void deleteMore(String []ids){
        productDao.deleteMore(ids);
    }
}
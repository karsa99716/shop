package com.ljx.dao;

import com.ljx.domain.Product;
import com.ljx.utils.JDBCUtils;

import java.util.List;
import java.util.Map;

public class ProductDao {
    //添加一条记录
    public void add(Product product) {
        String sql = "insert into product values(null,?,?,?,?,?,?,?,?,?)";
        Object params[] = {
                product.getName(), product.getPrice(), product.getMarkprice(), product.getQuality(), product.getHit(),
                product.getTime(), product.getPhoto(), product.getContent(), product.getCategoryid(),
        };
        Number number = (Number) JDBCUtils.insert(sql, params);
        product.setProductid(number.intValue());
    }

    //获取所有的记录个数
    public int findCount() {
//        String sql = "select count(*) from product";
//        return ((Number) JDBCUtils.selectScalar(sql)).intValue();
        return findCount(null,null);
    }

    //获取所有的记录个数,带查询条件的
    public int findCount(String skey,String svalue) {
        StringBuilder sql = new StringBuilder("select count(*) from product");
        if (skey!=null && skey.trim().length()>0 && svalue!=null && svalue.trim().length()>0){
            //有查询条件
            sql.append(" where "+ skey.toString() + " like \"%" + svalue + "%\"");
        }
        return ((Number) JDBCUtils.selectScalar(sql.toString())).intValue();
    }

    //获取记录列表
    public List<Map<String,Object>> findAll(int startIndex,int size){
//        String sql = "SELECT p.productid,p.content,p.hit,p.markprice,p.name,p.photo,p.price,p.productid,p.quality,p.time,c.name AS cname " +
//                "FROM product AS p,category AS c WHERE p.categoryid = c.categoryid limit ?,?";
//        return JDBCUtils.select(sql,startIndex,size);
        return findAll(startIndex,size,null,null,null,null);
    }

    //获取记录列表,带查询条件的
    public List<Map<String,Object>> findAll(int startIndex,int size,String skey,String svalue){
        String sql = "SELECT p.productid,p.content,p.hit,p.markprice,p.name,p.photo,p.price,p.productid,p.quality,p.time,c.name AS cname " +
                "FROM product AS p,category AS c WHERE p.categoryid = c.categoryid ";
        StringBuilder str = new StringBuilder("");
        if (skey!=null && (skey.trim().length()>0) && svalue!=null && (svalue.trim().length()>0)){
            str.append(" and p." + skey+ " like \"%"+svalue+"%\" ");
        }
        return JDBCUtils.select(sql+str.toString()+" limit ?,?",startIndex,size);
    }

    //通过id获取记录
    public Map<String, Object> findById(int productid) {
        String sql = "select p.productid,p.content,p.hit,p.markprice,p.name,p.photo,p.price,p.categoryid,p.quality,p.time,c.name as cname " +
                "from product as p,category as c where p.categoryid=c.categoryid and productid=?";
        return JDBCUtils.select(sql,productid).get(0);
    }

    //修改
    public void update(Product product){
        String sql = "update product set name=?,price=?,markprice=?,quality=?,hit=?,time=?,photo=?,content=?,categoryid=? where productid=?";
        Object params[]={
          product.getName(),product.getPrice(),product.getMarkprice(),product.getQuality(),product.getHit(),
          product.getTime(),product.getPhoto(),product.getContent(),product.getCategoryid(),product.getProductid(),
        };
        JDBCUtils.update(sql,params);
    }

    //删除
    public void delete(int productid){
        String sql = "delete from product where productid = ?";
        JDBCUtils.update(sql,productid);
    }

    //删除多条记录
    public void deleteMore(String ids[]){
        String sql = "delete from product where productid in(";
        StringBuilder str = new StringBuilder("");
        for (int i=0;i< ids.length;i++){
            if (i==ids.length-1){
                str.append("?)");
            }else {
                str.append("?,");
            }
        }
        JDBCUtils.update(sql+str.toString(),ids);
    }

    //获取记录列表,带查询条件和排序
    public List<Map<String,Object>> findAll(int startIndex,int size,String skey,String svalue,String sortkey,String sort){
        String sql = "SELECT p.productid,p.content,p.hit,p.markprice,p.name,p.photo,p.price,p.productid,p.quality,p.time,c.name AS cname " +
                "FROM product AS p,category AS c WHERE p.categoryid = c.categoryid ";
        StringBuilder str = new StringBuilder("");
        if (skey!=null && (skey.trim().length()>0) && svalue!=null && (svalue.trim().length()>0)){
            str.append(" and p." + skey+ " like \"%"+svalue+"%\" ");
        }
        if (sortkey!=null && sortkey.trim().length()>0 && sort!=null && sort.trim().length()>0){
            str.append(" order by "+sortkey+" "+sort+" ");
        }
        return JDBCUtils.select(sql+str.toString()+" limit ?,?",startIndex,size);
    }
}

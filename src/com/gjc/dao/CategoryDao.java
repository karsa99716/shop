package com.gjc.dao;

import com.gjc.domain.Category;
import com.gjc.utils.JDBCUtils;

import java.util.List;
import java.util.Map;

public class CategoryDao {
    //添加一条记录
    public void add(Category category){
        String sql = "insert into category values(null,?,?)";
        Object params[] = {
                category.getName(),
                category.getSort(),
        };
        Number n = (Number)JDBCUtils.insert(sql,params);
        category.setCategoryid(n.intValue());
    }
    //根据name值查找分类，返回一个category对象
    public Category findByName(String name){
        Category category = null;
        String sql = "select * from category where name=?";
        List<Map<String,Object>> list = JDBCUtils.select(sql,name);
        if (list.size()>0){
            category = new Category();
            category.setCategoryid(Integer.parseInt(list.get(0).get("categoryid").toString()));
            category.setName(name);
            category.setSort(Integer.parseInt(list.get(0).get("sort").toString()));
        }
        return category;
    }

    //获取所有记录
    public static List<Map<String,Object>> findAll(){
        String sql = "select * from category";
        return JDBCUtils.select(sql);
    }

    //获取表中总记录数
    public int findToatalCount(){
        String sql = "select count(*) from category";
        return ((Number)JDBCUtils.selectScalar(sql)).intValue();
    }

    //根据起始记录索引和读取记录个数，获取分类记录集
    public List<Map<String,Object>> findAll(int startIndex,int size){
        String sql = "select * from category limit ?,?";
        Object params[]={
                startIndex,size
        };
        return JDBCUtils.select(sql,params);
    }

    //删除id的记录
    public void deleteById(int id){
        String sql = "delete from category where categoryid = ?";
        JDBCUtils.update(sql,id);
    }

    //删除所有id数组中的记录
    public void deleteMore(String id[]){
        String sql = "delete from category where categoryid in (";
        StringBuilder str = new StringBuilder("");
        for (int i=0;i< id.length;i++){
            if (i== id.length-1)//表示为最后一个
                str.append("?)");
            else
                str.append("?,");
        }
        JDBCUtils.update(sql+str.toString(),id);
    }

    //通过id读取记录
    public Map<String,Object> findById(int id){
        String sql = "select * from category where categoryid=?";
        return JDBCUtils.select(sql,id).get(0);
    }

    //通过Category对象进行更新
    public void update(Category c){
        String sql = "update category set name=?,sort=? where categoryid=?";
        Object params[]={
                c.getName(),c.getSort(),c.getCategoryid()
        };
        JDBCUtils.update(sql,params);
    }
}

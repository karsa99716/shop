package com.gjc.utils;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class JDBCUtils {
    private static String driver;
    private static String url;
    private static String name;
    private static String password;

    private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();//事务专用

    //静态模块中的代码在类加载时执行，且仅被执行一次
    static {
        //从配置文件中将参数读取出来
        InputStream in = JDBCUtils.class.getClassLoader()
                .getResourceAsStream("dbConfig.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
            driver = prop.getProperty("driverClassName");
            url = prop.getProperty("url");
            name = prop.getProperty("name");
            password = prop.getProperty("password");
            //加载驱动程序
            Class.forName(driver);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    //获取数据库的连接
    //如果是事务，给它tl中conn，如果不是事务，创建一个新的事务给它
    public static Connection getConnection() throws SQLException {
        Connection conn = tl.get();
        if (conn!=null){
            //已经开启过事务
            return conn;
        }
        return DriverManager.getConnection(url, name, password);
    }

    //关闭连接
    //如果是事务，conn不能关；如果不是，关闭conn
    public static void release(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                Connection tconn = tl.get();
                if (tconn == conn){
                    //是事务
                    return;
                }
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void fillStatement(PreparedStatement pre, Object[] params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pre.setObject(i + 1, params[i]);
            }
        }
    }

    //开启事务
    public static void beginTranscation() throws SQLException {
        /*
        1.从tl中获取Connection对象
        2.如果tl有值的话，表示已经开启过事务，抛出异常
        3.如果tl没有值，创建Connection对象，开启事务
        4.把事务保存在tl中
         */
        //1.从tl中获取Connection对象
        Connection conn = tl.get();
        //2.如果tl有值的话，表示已经开启过事务，抛出异常
        if (conn!=null)
            throw new SQLException("已经开启过事务，请勿重复开启！");
        //3.如果tl没有值，创建Connection对象，开启事务
        conn = getConnection();
        conn.setAutoCommit(false);
        tl.set(conn);
    }

    //提交事务
    public static void commitTranscation() throws SQLException {
        /*
        1.获取Connection对象
        2.如果Connection对象为空，不存在事务，抛出异常
        3.不为空，提交事务，关闭连接，tl中的Conn移除
         */
        Connection conn = tl.get();
        if (conn == null)
            throw new SQLException("没有事务，可以提交！");
        conn.commit();//提交事务
        conn.close();
        conn = null;
        tl.remove();
    }

    //回滚事务
    public static void rollbackTranscation() throws SQLException {
        /*
        1.获取Connection对象
        2.如果Connection对象为空，不存在事务，抛出异常
        3.不为空，提交事务，关闭连接，tl中的Conn移除
         */
        Connection conn = tl.get();
        if (conn == null)
            throw new SQLException("没有事务，可以回滚！");
        conn.rollback();//回滚事务
        conn.close();
        conn = null;
        tl.remove();
    }

    //查找操作
    public static List<Map<String, Object>> select(String sql, Object...params) {
        /*
        1.获取数据库的连接
        2.获取Statement对象
        3.调用Statement对象executeQuery
        4.关闭连接
         */

        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        List<Map<String,Object>> list = null;
        //1.获取数据库的连接
        try {
            //1.获取数据库的连接
            conn = getConnection();
            //2.1获取Statement对象
            pre = conn.prepareStatement(sql);
            //2.2设置参数
            fillStatement(pre, params);
            //3.调用Statement对象executeQuery
            rs = pre.executeQuery();
            list = RsToList(rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            release(conn, pre, rs);
        }
        return list;
    }

    //将ResultSet对象转换为List
    private static List<Map<String, Object>> RsToList(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        //获取表结构
        ResultSetMetaData rsmd = rs.getMetaData();
        //通过rs逐条读取记录
        while (rs.next()) {
            //每条记录放入一个Map中
            Map<String, Object> map = new HashMap<String, Object>();
            //rsmd.getColumnCount()，字段的个数（列数）
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                //逐个字段读取出来放入Map中,以键值对的形式存入
                // key就是rsmd.getColumnName(i)字段名，值是rs.getObject(i)
                map.put(rsmd.getColumnLabel(i), rs.getObject(i));
            }
            list.add(map);
        }
        return list;
    }

    //增删改操作
    public static void update(String sql, Object... params) {
        /*
        1.获取数据库的连接
        2.获取Statement对象
        3.调用Statement对象executeUpdate
        4.关闭连接
         */
        //1.获取数据库的连接
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            //1.2获取数据库连接
            conn = getConnection();//需改写，如果是事务，给它tl中conn，如果不是事务，创建一个新的事务给它
            //2.1获取Statement对象
            pre = conn.prepareStatement(sql);
            //2.2设置参数
            fillStatement(pre, params);
            //3.调用Statement对象executeUpdate
            pre.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //4.关闭连接
            release(conn, pre, null);//需改写，如果是事务，conn不能关；如果不是，关闭conn
        }
    }

    //增加操作，需要返回自增的主键
    public static Object insert(String sql, Object... params) {
        /*
        1.获取数据库的连接
        2.获取Statement对象
        3.调用Statement对象executeUpdate
        4.关闭连接
         */
        //1.获取数据库的连接
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            //1.2获取数据库连接
            conn = getConnection();
            //2.1获取Statement对象
            pre = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //2.2设置参数
            fillStatement(pre, params);
            //3.调用Statement对象executeUpdate
            pre.executeUpdate();
            //4.1获取主键
            rs = pre.getGeneratedKeys();
            Object key = null;
            if (rs.next()) {
                key = rs.getObject(1);
            }
            return key;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //4.2关闭连接
            release(conn, pre, rs);
        }
    }

    //用于update,insert,delete这些批处理操作
    public static int[] updatebatch(String sql,Object [][]params){
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            //设置参数
            if (params!=null){
                for (int i=0;i<params.length;i++){
                    //设置每条记录的参数
                    fillStatement(pre,params[i]);
                    pre.addBatch();
                }
            }
            return pre.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            release(conn,pre,null);
        }
    }

    //用于update,insert,delete这些批处理操作
    public static <T> List<T> insertbatch(String sql,Object [][]params){
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            //设置参数
            if (params!=null){
                for (int i=0;i<params.length;i++){
                    //设置每条记录的参数
                    fillStatement(pre,params[i]);
                    pre.addBatch();
                }
            }
            pre.executeBatch();
            rs = pre.getGeneratedKeys();
            List<T> list = new ArrayList<>();
            while (rs.next()){
                list.add((T)rs.getObject(1));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            release(conn,pre,rs);
        }
    }

    //用于返回单值的操作
    public static <T> T selectScalar(String sql,Object ...params){
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        T result = null;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            fillStatement(pre,params);
            pre.executeQuery();
            rs = pre.executeQuery();
            if (rs.next()){
                result = (T)rs.getObject(1);
            }
            return result;
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }finally {
            release(conn,pre,rs);
        }
    }

    //查找操作
    public static <T> List<T> select(Class<T> clazz,String sql,Object...params){
        /*
        1.获取数据库的连接
        2.获取Statement对象
        3.使用Statement对象方法executeQuery
        4.关闭连接
         */

        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<T>();

        try {
            //1.获取数据库的连接
            conn = getConnection();
            //2.1获取Statement对象
            pre = conn.prepareStatement(sql);
            fillStatement(pre,params);
            //3.使用Statement对象方法executeQuery
            rs = pre.executeQuery();
            list = (List<T>) BeanUtils.BeanHandler(rs,clazz);
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            release(conn,pre,rs);
        }
        return list;
    }

    //查找操作
    public static <T> T selectToBean(Class<T> clazz,String sql,Object...params){
        /*
        1.获取数据库的连接
        2.获取Statement对象
        3.使用Statement对象方法executeQuery
        4.关闭连接
         */

        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        T object = null;

        try {
            //1.获取数据库的连接
            conn = getConnection();
            //2.1获取Statement对象
            pre = conn.prepareStatement(sql);
            fillStatement(pre,params);
            //3.使用Statement对象方法executeQuery
            rs = pre.executeQuery();
            object = BeanUtils.BeanHandler(rs,clazz);
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            release(conn,pre,rs);
        }
        return object;
    }
}

package com.gjc.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanUtils {
	
 public static <T> List<T> toBeanList(List<Map<String, Object>> list, Class<T> clazz)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, SQLException {
		List<T> beanlist = new ArrayList<T>();
		for (int k = 0; k < list.size(); k++) {
			Map<String, Object> map = list.get(k);
			Set<String> keys = map.keySet();// 获取Map中keys

			Method[] methods = clazz.getMethods();// 获取该类的所有方法
			T object = null;
			// 通过反射创建该对象
			object = clazz.newInstance();
			for (String str : keys) {
				String methodName = "set";// 要调用的方法名

				// 取首字符
				String newcolName = str.substring(0, 1).toUpperCase();// 将首字符变成大写
				newcolName = newcolName + str.substring(1);
				methodName += newcolName;
				for (int i = 0; i < methods.length; i++) {
					Method method = methods[i];
					if (method.getName().equals(methodName)) {// 获取和methodName相同的名字，调用它
						method.invoke(object, map.get(str));// 通过反射调用setXXX方法，并给它传参
					}
				}
			}
			beanlist.add(object);
		}
		return beanlist;
	}

	public static <T> T toBean(Map<String, Object> map, Class<T> clazz)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, SQLException {

		Set<String> keys = map.keySet();// 获取Map中keys

		Method[] methods = clazz.getMethods();// 获取该类的所有方法
		T object = null;
		// 通过反射创建该对象
		object = clazz.newInstance();
		for (String str : keys) {
			String methodName = "set";// 要调用的方法名

			// 取首字符
			String newcolName = str.substring(0, 1).toUpperCase();// 将首字符变成大写
			newcolName = newcolName + str.substring(1);
			methodName += newcolName;
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				if (method.getName().equals(methodName)) {// 获取和methodName相同的名字，调用它
					method.invoke(object, map.get(str));// 通过反射调用setXXX方法，并给它传参
				}
			}
		}

		return object;
	}
	
	public static <T> List<T> BeanListHandler(ResultSet rs, Class<T> clazz)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, SQLException {

		ResultSetMetaData rsmd = rs.getMetaData();// 获取表结构
		int count = rsmd.getColumnCount();
		String[] colNames = new String[count];// 对应查询表中所有字段
		for (int i = 1; i <= count; i++) {
			colNames[i - 1] = rsmd.getColumnLabel(i);// 获取表字段
		}

		Method[] methods = clazz.getMethods();// 获取该类的所有方法
		List<T> list = new ArrayList<T>();

		while (rs.next()) {
			// 通过反射创建该对象
			T object = clazz.newInstance();
			for (int j = 0; j < colNames.length; j++) {
				String methodName = "set";// 要调用的方法名

				String colName = colNames[j];// 第j列
				// 取首字符
				String newcolName = colName.substring(0, 1).toUpperCase();// 将首字符变成大写
				newcolName = newcolName + colName.substring(1);
				methodName += newcolName;
				for (int i = 0; i < methods.length; i++) {
					Method method = methods[i];
					if (method.getName().equals(methodName)) {// 获取和methodName相同的名字，调用它
						method.invoke(object, rs.getObject(colName));// 通过反射调用setXXX方法，并给它传参
					}
				}
			}
			list.add(object);
		}
		return list;
	}
	
	public static <T> T BeanHandler(ResultSet rs, Class<T> clazz)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, SQLException {

		ResultSetMetaData rsmd = rs.getMetaData();// 获取表结构
		int count = rsmd.getColumnCount();
		String[] colNames = new String[count];// 对应查询表中所有字段
		for (int i = 1; i <= count; i++) {
			colNames[i - 1] = rsmd.getColumnLabel(i);// 获取表字段
		}

		Method[] methods = clazz.getMethods();// 获取该类的所有方法
		T object = null;

		if (rs.next()) {
			// 通过反射创建该对象
			object = clazz.newInstance();
			for (int j = 0; j < colNames.length; j++) {
				String methodName = "set";// 要调用的方法名

				String colName = colNames[j];// 第j列
				// 取首字符
				String newcolName = colName.substring(0, 1).toUpperCase();// 将首字符变成大写
				newcolName = newcolName + colName.substring(1);
				methodName += newcolName;
				for (int i = 0; i < methods.length; i++) {
					Method method = methods[i];
					if (method.getName().equals(methodName)) {// 获取和methodName相同的名字，调用它
						method.invoke(object, rs.getObject(colName));// 通过反射调用setXXX方法，并给它传参
					}
				}
			}

		}
		return object;
	}

}

package com.proper.uip.common.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

public abstract class CollectionUtil {

	/**
	 * 将泛化不定长参数转化成数组
	 * <p><pre>
	 * CollectionUtil.toArray("a","b")={"a","b"};
	 * </pre>
	 * @param t
	 * @return
	 */
	public static <T> T[] toArray(T... t) {
		return t;
	}

	/**
	 * 将String数组用指定的连接符连接，返回连接后的字符串
	 * <p><pre>
	 * CollectionUtil.joins({"a","b"},",") = "a,b";
	 * CollectionUtil.joins({"a","b"},".") = "a.b";
	 * CollectionUtil.joins({"a","b"},null) = "a,b";
	 * CollectionUtil.joins(null,",") = "";
	 * CollectionUtil.joins({},",") = "";
	 * </pre>
	 * @param arrays 目标数组
	 * @param joinStr 数组连接符
	 * @return
	 */
	public static String joins(String[] arrays, String joinStr) {
		if (arrays == null) {
			return "";
		}
		joinStr = StringUtils.hasText(joinStr) ? joinStr : ",";
		StringBuilder rs = new StringBuilder();
		for (String e : arrays) {
			rs.append(joinStr).append(e);
		}
		return rs.length() > 0 ? rs.substring(1) : "";
	}

	/**
	 * 将泛型化集合转换成按持有对象的其中一个属性值为key，持有对象为value的Map映射
	 * <p><pre>
	 * public class Person{
	 * 		private String id;
	 * 		private String name;
	 * 		public Person(String id,String name){
	 * 			this.id = id;
	 * 			this.name = name;
	 * 		}
	 * }
	 * 
	 * Person p1 = new Person("1","张三");
	 * Person p2 = new Person("1","李四");
	 * 
	 * List<Person> rs = new ArrayList<Person>();
	 * rs.add(p1);
	 * rs.add(p2);
	 * 
	 * Map<String,Person> id2PersonMap =  CollectionUtil.getMap(rs,"id");
	 * 
	 * 结果：
	 * id2PersonMap.get("1") = p1;
	 * id2PersonMap.get("2") = p2;
	 * 
	 * </pre>
	 * @param <K> 集合持有对象idFieldName属性对应的类型,也就是返回Map的键类型
	 * @param <V> 集合持有对象类型泛型,也就是返回Map的值类型
	 * @param collection 需要被转换成Map的Collection
	 * @param idFieldNam collection持有对象属性名称,该属性的值为Map的Key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> getMap(Collection<V> collection, String idFieldName) {
		Map<K, V> map = new HashMap<K, V>();
		if (collection != null && !collection.isEmpty()) {
			for (V v : collection) {
				K key = (K) ReflectionUtils.getFieldValue(v, idFieldName);
				map.put(key, v);
			}
		}
		return map;
	}

	/**
	 * 将泛型化集合转换成按持有对象的其中一个属性值为key，另一个属性值为value的Map映射
	 * <p><pre>
	 * public class Person{
	 * 		private String id;
	 * 		private String name;
	 * 		public Person(String id,String name){
	 * 			this.id = id;
	 * 			this.name = name;
	 * 		}
	 * }
	 * 
	 * Person p1 = new Person("1","张三");
	 * Person p2 = new Person("1","李四");
	 * 
	 * List<Person> rs = new ArrayList<Person>();
	 * rs.add(p1);
	 * rs.add(p2);
	 * 
	 * Map<String,String> id2PersonMap =  CollectionUtil.getMap(rs,"id","name");
	 * 
	 * 结果：
	 * id2PersonMap.get("1") = "张三";
	 * id2PersonMap.get("2") = "李四";
	 * 
	 * </pre>
	 * @param <K> 返回的map的key实际类型
	 * @param <V> 返回的map的value实际类型
	 * @param <E> collection持有对象
	 * @param collection 目标集合
	 * @param idFieldName 目标集合持有对象属性名称,该属性的值为Map的Key
	 * @param valueFieldName 目标集合持有对象属性名称,该属性的值为Map的Value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V, E> Map<K, V> getMap(Collection<E> collection, String idFieldName, String valueFieldName) {
		Map<K, V> map = new HashMap<K, V>();
		if (collection != null && !collection.isEmpty()) {
			for (E e : collection) {
				K key = (K) ReflectionUtils.getFieldValue(e, idFieldName);
				V value = (V) ReflectionUtils.getFieldValue(e, valueFieldName);
				map.put(key, value);
			}
		}
		return map;
	}

}

package com.proper.uip.common.utils.qui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.proper.uip.common.utils.JsonUtil;
import com.proper.uip.common.utils.ReflectionUtils;

/**
 * QUI select 组件需要数据类型<br/>
 * {"list":[{"key":"m1 name","value":"m1 value"},{"key":"m2 name","value":"m2 value"}]}
 * 
 * @author zycgod
 * 
 * @param <T>
 * @param <K>
 * @param <V>
 */
public class SelectDatas<T, K, V> {

	public List<Data> list = new ArrayList<Data>();

	public static <T, K, V> SelectDatas<T, K, V> newInstance(Collection<T> datas, String keyName, String valueName) {
		return new SelectDatas<T, K, V>(datas, keyName, valueName);
	}

	public static <T, K, V> SelectDatas<T, K, V> newInstance(Collection<T> datas, String keyName, String valueName,
			SuggestGenerator<T> generator) {
		return new SelectDatas<T, K, V>(datas, keyName, valueName, generator);
	}

	@SuppressWarnings("unchecked")
	private SelectDatas(Collection<T> datas, String keyName, String valueName) {
		if (datas != null) {
			for (T t : datas) {
				if (t instanceof Map) {
					putData((K) ((Map<String, ?>) t).get(keyName), (V) ((Map<String, ?>) t).get(valueName));
				} else {
					putData((K) ReflectionUtils.getFieldValue(t, keyName),
							(V) ReflectionUtils.getFieldValue(t, valueName));
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private SelectDatas(Collection<T> datas, String keyName, String valueName, SuggestGenerator<T> generator) {
		if (datas != null) {
			for (T t : datas) {
				if (t instanceof Map) {
					putData((K) ((Map<String, ?>) t).get(keyName), (V) ((Map<String, ?>) t).get(valueName),
							generator.generate(t));
				} else {
					putData((K) ReflectionUtils.getFieldValue(t, keyName),
							(V) ReflectionUtils.getFieldValue(t, valueName), generator.generate(t));
				}
			}
		}
	}

	public void putData(K key, V value) {
		list.add(new Data(key, value));
	}

	public void putData(K key, V value, String suggest) {
		list.add(new Data(key, value, suggest));
	}

	public List<Data> getList() {
		return list;
	}

	@Override
	public String toString() {
		try {
			return JsonUtil.toJSON(this);
		} catch (IOException e) {
			e.printStackTrace();
			return "{}";
		}
	}

	public static interface SuggestGenerator<T> {
		String generate(T t);
	}

	public class Data {

		private K key;
		private V value;
		private String suggest;

		public Data(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}

		public Data(K key, V value, String suggest) {
			this(key, value);
			this.suggest = suggest;
		}

		public K getKey() {
			return key;
		}

		public void setKey(K key) {
			this.key = key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public String getSuggest() {
			return suggest;
		}

		public void setSuggest(String suggest) {
			this.suggest = suggest;
		}

	}

	public static void main(String[] args) {

		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("name", "m1 name");
		m1.put("value", "m1 value");
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("name", "m2 name");
		m2.put("value", "m2 value");

		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		datas.add(m1);
		datas.add(m2);

		System.out.println(SelectDatas.newInstance(datas, "name", "value"));
	}

}

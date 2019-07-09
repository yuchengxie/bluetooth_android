package com.nationz.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

public class JSONAnalyze {

	private static final String TAG = "JSON";

	/**
	 * 对象转换成jsonObject
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static String ObjectToJson(Object object) {
		Class classs = object.getClass();
		StringBuffer sb = new StringBuffer("{");
		try {

			Method methods[] = classs.getDeclaredMethods();
			List<Map<String, Method>> getMethods = new ArrayList<Map<String, Method>>();
			for (Method method : methods) {
				Map<String, Method> map = new HashMap<String, Method>();
				String name = method.getName();
				if (name.startsWith("get")) {
					String value = toLower(name.substring(3));
					map.put(value, method);
					getMethods.add(map);
				} else if (name.startsWith("is")) {
					String value = toLower(name.substring(2));
					map.put(value, method);
					getMethods.add(map);
				}
			}

			for (int i = 0; i < getMethods.size(); i++) {
				Map<String, Method> map = getMethods.get(i);
				for (Entry<String, Method> entry : map.entrySet()) {
					sb.append("\"" + entry.getKey() + "\":");
					Object value = entry.getValue().invoke(object, null);
					if (value == null) {
						value = "";
					}
					Class returnType = entry.getValue().getReturnType();
					if (String.class.getName().equals(returnType.getName())) {
						sb.append("\"" + value + "\"");
					} else {
						sb.append("" + value + "");
					}
					sb.append(",");
					break;
				}
			}

			sb.append("}");
			sb.replace(sb.lastIndexOf(","), sb.lastIndexOf(",") + 1, "");
			return sb.toString();
		} catch (IllegalAccessException e) {
			Log.e(TAG, "IllegalAccessException:" + e.getMessage());
		} catch (IllegalArgumentException e) {
			Log.e(TAG, "IllegalArgumentException:" + e.getMessage());
		} catch (InvocationTargetException e) {
			Log.e(TAG, "InvocationTargetException:" + e.getMessage());
		}
		return null;
	}

	/**
	 * list对象转换成json数据
	 * 
	 * @param objects
	 * @return
	 */
	public static String ListToJson(List<?> objects) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < objects.size(); i++) {
			String item = ObjectToJson(objects.get(i));
			if (TextUtils.isEmpty(item)) {
				continue;
			}
			sb.append(item);
			sb.append(",");
		}
		sb.append("]");
		sb.replace(sb.lastIndexOf(","), sb.lastIndexOf(",") + 1, "");

		return sb.toString();
	}

	/**
	 * json转换成对象
	 * @param json
	 * @param classes
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Object JsonObjectToObject(String json, Class classes) {
		try {
			Object object = classes.newInstance();
			Method methods[] = classes.getDeclaredMethods();

			List<Map<String, Method>> getMethods = new ArrayList<Map<String, Method>>();
			for (Method method : methods) {
				Map<String, Method> map = new HashMap<String, Method>();
				String name = method.getName();
				if (name.startsWith("set")) {
					String value = toLower(name.substring(3));
					map.put(value, method);
					getMethods.add(map);
				}
			}

			JSONObject jsonObject = new JSONObject(json);
			for (int i = 0; i < getMethods.size(); i++) {
				Map<String, Method> map = getMethods.get(i);
				for (Entry<String, Method> entry : map.entrySet()) {
					String key= entry.getKey();
					Method item = entry.getValue();
					if(jsonObject.isNull(key)){
						break;
					}
					String obj = jsonObject.get(key).toString();
					Class paramType = item.getParameterTypes()[0];
					if("int".equals(paramType.getName())){
						item.invoke(object, Integer.parseInt(obj));
					}else if("long".equals(paramType.getName())){
						item.invoke(object, Long.parseLong(obj));
					}else if("float".equals(paramType.getName())){
						item.invoke(object,Float.parseFloat(obj));
					}else if("double".equals(paramType.getName())){
						item.invoke(object,Double.parseDouble(obj));
					}else if(Float.class == paramType){
						item.invoke(object,(Float)Float.parseFloat(obj));
					}else if(Long.class == paramType){
						item.invoke(object, (Long)Long.parseLong(obj));
					}else if(Double.class == paramType){
						item.invoke(object, (Double)Double.parseDouble(obj));
					}else if(Integer.class == paramType){
						item.invoke(object,(Integer)Integer.parseInt(obj));
					}else if(boolean.class == paramType){
						item.invoke(object,Boolean.parseBoolean(obj));
					}else{
						item.invoke(object,obj);
					}
					break;
				}
			}

			return object;
		} catch (InstantiationException e) {
			Log.e(TAG, "InstantiationException:" + e.getMessage());
		} catch (IllegalAccessException e) {
			Log.e(TAG, "IllegalAccessException:" + e.getMessage());
		} catch (JSONException e) {
			Log.e(TAG, "JSONException:" + e.getMessage());
		} catch (IllegalArgumentException e) {
			Log.e(TAG, "IllegalArgumentException:" + e.getMessage());
		} catch (InvocationTargetException e) {
			Log.e(TAG, "InvocationTargetException:" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * json array to  list对象
	 * @param json
	 * @param classes
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<?> JsonArrayToList(String json,Class classes){
		try {
			JSONArray jsonArray = new JSONArray(json);
			List<Object> lists = new ArrayList<Object>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Object item = JsonObjectToObject(jsonObject.toString(), classes);
				lists.add(item);
			}
			return lists;
		} catch (JSONException e) {
			Log.e(TAG, "JSONException:"+e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * 字符串首字母小写
	 * @param fieldName
	 * @return
	 */
	private static String toLower(String fieldName) {
		if (TextUtils.isEmpty(fieldName)) {
			return null;
		}
		StringBuffer sb = new StringBuffer(fieldName);
		char first = sb.charAt(0);
		sb.setCharAt(0, Character.toLowerCase(first));
		return sb.toString();
	}

}

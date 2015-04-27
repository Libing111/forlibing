/* <p>文件名称: JsonUtil.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-6-21</p>
 * <p>完成日期：2013-6-21</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-6-21 下午5:12:57
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.common.utils;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {

	public static String obj2JSON(Object obj) throws IOException {
		return JSONObject.fromObject(obj).toString();
	}

	public static String array2JSON(Object obj) throws IOException {
		return JSONArray.fromObject(obj).toString();
	}

	public static String toJSON(Object obj) throws IOException {
		if (obj == null) {
			return "{}";
		}
		StringWriter sw = new StringWriter();
		JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(gen, obj);
			String json = sw.toString();
			return json;
		} finally {
			gen.close();
			sw.close();
		}
	}

}
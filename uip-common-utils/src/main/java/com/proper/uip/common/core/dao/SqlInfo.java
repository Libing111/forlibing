/* <p>文件名称: SqlInfo.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-5-9</p>
 * <p>完成日期：2013-5-9</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-5-9 下午2:58:46
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.common.core.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlInfo {
	public static final Pattern PATTERN=Pattern.compile("\\{[^\\}\\{]*\\}");
	public static final String DEFAULTVALUE="";
	private String sql;
	public SqlInfo(String sql){
		this.sql=sql;
	}
	public String getPureSql(){
		while(true){
			Matcher matcher=PATTERN.matcher(sql);
			if(!matcher.find()){
				break;
			}
			String sub=sql.substring(matcher.start()+1,matcher.end()-1);
			sql=sql.replaceFirst("\\{"+sub+"\\}", "?");
		}
		return sql;
	}
	public SqlEso getSqlEso(Map<String,Object> map){
		List<Object> list=new ArrayList<Object>();
		while(true){
			Matcher matcher=PATTERN.matcher(sql);
			if(!matcher.find()){
				break;
			}
			String sub=sql.substring(matcher.start()+1,matcher.end()-1);
			String[] s=sub.split(":");
			Object value=map.get(s[0]);
			if(value!=null){
				list.add(value);
				sql=sql.replaceFirst("\\{"+sub+"\\}", "?");
			}else{
				if(s.length>1){
					//list.add(s[1]);
					sql=sql.replaceFirst("\\{"+sub+"\\}", s[1]);
				}else{
					sql=sql.replaceFirst("\\{"+sub+"\\}", "?");
					list.add(DEFAULTVALUE);
				}
			}
			
		}
		return new SqlEso(sql,list.toArray());
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sql="select * from jc_cp where cp_bm={cp_bm:''} and dw_id_fk={dw_id:dw_id_fk}";
		SqlInfo sqlInfo=new SqlInfo(sql);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("cp_bm", "32");
		SqlEso se=sqlInfo.getSqlEso(map);
		System.out.println(se.getSql());
		//System.out.println(se.getParam()[0]);
		//System.out.println(se.getParam()[1]);
	}

}

/* <p>文件名称: RequestUtil.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2013-7-24</p>
 * <p>完成日期：2013-7-24</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2013-7-24 下午1:45:56
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.api.bpm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestUtil {
  
  private static final SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");
  //private static final SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
  private static final SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  
  public static String dateToString(Date date) {
    String dateString = null;
    if(date != null) {
      dateString = longDateFormat.format(date);
    }
    
    return dateString;
  }
  
  public static Integer parseToInteger(String integer) {
    Integer parsedInteger = null;
    try {
      parsedInteger = Integer.parseInt(integer);
    } catch(Exception e) {}
    return parsedInteger;
  }
  
  public static Date parseToDate(String date) {
    Date parsedDate = null;
    try {
      parsedDate = shortDateFormat.parse(date);
    } catch(Exception e) {}
    return parsedDate;
  }
}

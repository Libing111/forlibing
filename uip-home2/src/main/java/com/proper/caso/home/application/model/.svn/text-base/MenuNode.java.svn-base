/* <p>文件名称: MenuNode.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-10-30</p>
 * <p>完成日期：2014-10-30</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-10-30下午4:11:23
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.caso.home.application.model;

import java.util.List;

import com.proper.uip.api.desktop.entity.ApplicationAndGroup;

public class MenuNode {
    
	
	private String id;
    private String title;
    private String pid;
    private String url;
    private String icon;
    private List<MenuNode> children;
    private String ptype="default";

    public MenuNode(){  
    }
    
    public MenuNode(ApplicationAndGroup module, String url){  
    	this.id = module.getId();
        this.title = module.getName();
        this.pid = module.getParent();
        this.url = url;
        
        this.icon = "demo";
    }
    
    public MenuNode(String id,String title,String pid,String url,String icon){
        this.id = id;
        this.title = title;
        this.pid = pid;
        this.url = url;
        this.icon = icon;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPid() {
        return pid;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public List<MenuNode> getChildren() {
        return children;
    }
    public void setChildren(List<MenuNode> children) {
        this.children = children;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

}

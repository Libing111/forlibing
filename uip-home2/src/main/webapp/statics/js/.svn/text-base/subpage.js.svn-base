//首页入口函数
$(document).ready(function(){
	var menus=null;
	var filterConfig={
			"filterFunc":function(m, filterText1) {
				var ft="";
				if(filterText1){
					ft=filterText1.toLowerCase();
				}else{
					return true;//如果filterText1=="",则为全查
				}
				if (m.title) {
					return (m.title.indexOf(ft) >= 0||m.titlePinYinFull.indexOf(ft) >= 0||m.titlePinYinCamel.indexOf(ft) >= 0);
				}
				return false;
			}
	};
	var lurl="subpage/module/getList?menuId=" + $("#menuId").val();
	menus=properGlobalCache.get(lurl);
	if(menus){
		renderContent();
	}else{
		//异步加载html片段
		$.ajax({
			type: "POST",
			url: lurl,
			dataType:'json',
			cache: true,
			success: function(data){
				menus=data;
				properGlobalCache.put(lurl,data);
				renderContent();
			}
		});
	}
	
	
	function renderContent(){
		//对返回的数据进行初始化，加上拼音属性
		if(menus){
			//去掉拼音中遇到的中文符号（）。
			var replace_patten=/[（,）,。,，,！-]/g;
			for(var i=0;i<menus.length;i++){
				var m=menus[i];
				if(m.title){
					//全拼
					m.titlePinYinFull=pinyin.getFullChars(m.title).toLowerCase().replace(replace_patten,"");
					//只有声母
					m.titlePinYinCamel=pinyin.getCamelChars(m.title).toLowerCase().replace(replace_patten,"");
				}else{
					m.title="";
					m.titlePingYinFull="";
					m.titlePingYinCamel="";
				}
			}
		}
		$("#modelwrap").empty();
		//绑定数据
		$("#modelwrap").model({
			modelData:menus,
			modelCallBack:function(item){//点击一级菜单局部刷新中央区域
				//item.maxed=true;
				customAddTaskPanel(item);
				
			}
		});
		hideContentLoading();
	}
	//初始化查询控件
	$("#queryInput").queryInput({
		width:420,
		autoQuery:true,/*检测查询框的内容变化，自动进行查询*/
		watermark:"请输入拼音或文字",/*文本框为空时，默认显示的文字*/
		skin:'plat-jq-queryInput-skin1',
		enterCallBack:function(inputDom){//确定回调函数 inputDom为当前控件中input的Dom节点
			//$("#queryInput").queryInput('getValue')  可以通过getValue方法获取控件的value
			//$("#queryInput").queryInput('setValue','test value') 通过setValue方法给控件赋值
			if(menus){
				var  menus2=doTreeMenuFilter(filterConfig,menus,$("#queryInput").queryInput('getValue'));
				if(menus2){
					
					$("#modelwrap").model("setData",menus2);
				}
			}
		},
		cancelCallBack:function(inputDom){//取消回调函数
			if(menus){
				var menus2=menus;
				if(menus2){
					$("#modelwrap").model("setData",menus2);
				}
			}
		}
	});
	
	
	
	
});
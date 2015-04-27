/**
 * 描述：状态栏插件
 * 作者：温泉
 * */
(function($){

//初始化函数
function init(target){
	var opts = $.data(target,'TaskPanel').options;
	var isDailogModel = opts.isDailogModel;//判断是否为弹出窗口方式
	var el = opts.el;
	//判断是否为传统方式还是弹出窗口方式。
	if(isDailogModel===false){
		var elDom = $("#"+el).get(0);
		if(elDom==undefined){
			alert("传统多任务栏插件初始化失败，原因：当isDailogModel设置成false时，没有在文档中找到与el属性匹配的Dom节点。");
			return false;
		}
	}
	
	var template = '<div id="'+opts.id+'" class="sq-ui-taskpanel"></div>';
	//将taskpanel总面板容器加载到桌面上
	if($("#"+opts.id).length == 0){
		$.data(target,'TaskPanel').TaskPanel = $(template).appendTo(target);
	}
	
	//添加内部内容，分为左右导航按钮以及中间taskpanel的容器
	var taskPanel = $.data(target,'TaskPanel').TaskPanel;
	//将窗口容器设置成空数组{id:'',instance:dailogObject} 其中dailogObject为QUI的窗口对象
	$.data(target,'TaskPanel').dailogs = [];
	$.data(target,'TaskPanel').tabs = [];
	
	//添加两个按钮和外部容器
	$.data(target,'TaskPanel').TaskNextBox = $("<div id='taskNextBox' class='sq-ui-taskBox sq-ui-taskNextBox'></div>").appendTo(taskPanel);
	$.data(target,'TaskPanel').TaskContainer = $("<div id='taskContainer' class='sq-ui-taskContainer'></div>").appendTo(taskPanel);
	$.data(target,'TaskPanel').TaskPreBox = $("<div id='taskPreBox' class='sq-ui-taskBox sq-ui-taskPreBox'></div>").appendTo(taskPanel);
	
	var TaskContainer = $.data(target,'TaskPanel').TaskContainer;
	TaskContainer.width(taskPanel.width());
	
	//装载内部容器
	var TaskContainerInner = $.data(target,'TaskPanel').TaskContainerInner = $("<div id='taskContainerInner' class='sq-ui-taskContainerInner'></div>").appendTo(TaskContainer);
	
	
	//update by wenquan 添加传统方式的判断
	//将面板容器添加到缓存中
	$.data(target,'TaskPanel').panelContainer = $("#"+opts.el);
	
	
	//初始化TaskButton插件
	TaskContainerInner.TaskButton();
	
	$.data(target,'TaskPanel').TaskNextBox.bind("click",{target:target},taskNextBoxEvent);
	$.data(target,'TaskPanel').TaskPreBox.bind("click",{target:target},taskPreBoxEvent);
	
	//添加鼠标移动移除事件
	$.data(target,'TaskPanel').TaskNextBox.hover(
		function () {
			$(this).addClass("sq-ui-taskNextBox-hover");
			$(this).removeClass("sq-ui-taskNextBox");
	  	},
	  	function () {
			$(this).addClass("sq-ui-taskNextBox");
			$(this).removeClass("sq-ui-taskNextBox-hover");
	  	}
	);
	
	$.data(target,'TaskPanel').TaskPreBox.hover(
		function () {
			$(this).addClass("sq-ui-taskPreBox-hover");
			$(this).removeClass("sq-ui-taskPreBox");
	  	},
	  	function () {
			$(this).addClass("sq-ui-taskPreBox");
			$(this).removeClass("sq-ui-taskPreBox-hover");
	  	}
	);
};

//向前滚动按钮的点击事件
function taskNextBoxEvent(e){
	var target = e.data.target;
	var TaskContainerInner = $.data(target,'TaskPanel').TaskContainerInner;
	var TaskContainer = $.data(target,'TaskPanel').TaskContainer;
	var oldLeft = parseInt(TaskContainerInner.css("left").replace("px",""));
	var newLeft = oldLeft + 112;
	
	var offsetRangeLeft = TaskContainerInner.width() - TaskContainer.width();
	if(newLeft >= offsetRangeLeft){
		newLeft = offsetRangeLeft;
	}
	
	TaskContainerInner.animate({left:newLeft + 'px'},{queue:true,duration:100});
	
};
//向后滚动按钮的点击事件
function taskPreBoxEvent(e){
	var target = e.data.target;
	var TaskContainerInner = $.data(target,'TaskPanel').TaskContainerInner;
	var oldLeft = parseInt(TaskContainerInner.css("left").replace("px",""));
	var newLeft = oldLeft - 112;
	if(newLeft < 0){
		newLeft = 0;
	}
	TaskContainerInner.animate({left:newLeft + 'px'},{queue:true,duration:100});
	
};


/*
描述：添加taskbutton所用的函数，如果没有则添加一个否则不添加
参数：1 target,当前组件的所属dom
     2. arg1 为组装taskbutton的对象  格式如下
	 var obj = {
		id:'3',
		title:'呵呵，好3',
		url:'http://www.baidu.com',
		icon:'',
		reload:true,//设置为true的时候，tab不变，但是重新刷新里面的内容，为false的时候则不重新刷新
		closeable:true // 默认没有为true，设置成true的时候，tab按钮带有关闭功能，false则不带有关闭功能
	}
作者：温泉
时间：2012-3-27  TODO
*/
function addTaskPanel(target,btnObj,href){
	var opts = $.data(target,'TaskPanel').options;
	var isDailogModel = opts.isDailogModel;//判断是否为弹出窗口方式
	var TaskContainerInner = $.data(target,'TaskPanel').TaskContainerInner;
	var tmpBtnIndex = getBtnById(target,opts.btnIndex+btnObj.id);
	if(tmpBtnIndex==-1){
		//添加panel内容
		if(isDailogModel===false){
			addPanel(target,btnObj,href);
		}else{
			//添加窗口
			addDailog(target,btnObj,href);
		}
		//添加TaskButton按钮
		(opts.btns).push(TaskContainerInner.TaskButton('addTaskButton',btnObj,target));
		//重新计算容器的大小
		resizeTaskContainer(target);
	}else{
		var id = btnObj.id;
		var url = btnObj.url;
		var tabId = "taskPanel_tab_"+id;
		var reload = btnObj.reload;
		if(reload!=undefined && reload==true){
			var tabDom = $("#"+tabId);
			$("#"+tabId).find("iframe").attr("src",url);
		}
	}
	
	//选中当前tab和tab按钮
	selectTab(target,btnObj.id);
};

//选中当前tab和按钮
function selectTab(target,id){
	var opts = $.data(target,'TaskPanel').options;
	var isDailogModel = opts.isDailogModel;//判断是否为弹出窗口方式
	var tabPanelId = opts.panelIndex + id;
	var buttonId = opts.btnIndex + id;
	//选中按钮
	selectBtn(target,buttonId);
	//选择窗口或者tab
	if(isDailogModel===false){
		selectTabPanel(target,id);
	}else{
		selectDailog(target,id);
	}
};
//点击任务栏上的图标按钮，对话框的显示与隐藏切换效果
function toggleSelectDialog(target,id){
	var opts = $.data(target,'TaskPanel').options;
	var isDailogModel = opts.isDailogModel;//判断是否为弹出窗口方式
	var tabPanelId = opts.panelIndex + id;
	var buttonId = opts.btnIndex + id;
	//选中按钮
	selectBtn(target,buttonId);
	//选择窗口或者tab
	if(isDailogModel===false){
		if(console){
			console.log("toggleSelectDialog() must be isDailogModel==true");
		}
	}else{
		var dailog = getDailogById(target,id);
		if(dailog!=null){
			var dailogIns = dailog['instance'];
			if(!dailogIns.isVisible()){//如果不可见，则直接显示
				selectDailog(target,id);
			}else{
				var aryDialogs=$.data(target,'TaskPanel').dailogs;
				var maxZIndex=-1;
				var maxZIndex_i=-1;
				if(aryDialogs){
					for(var i=0;i<aryDialogs.length;i++){
						var di=aryDialogs[i].instance;
						var zIndex=di.zIndex();
						if(di.isVisible()&&maxZIndex<zIndex){
							maxZIndex=zIndex;
							maxZIndex_i=i;
						}
					}
					//如果有覆盖在当前对话框上面的对话框，则当将前对话框置顶
					if(maxZIndex>dailogIns.zIndex() &&maxZIndex_i>=0){
						var di_max=aryDialogs[maxZIndex_i].instance;
						di_max.zIndex(dailogIns.zIndex());
						dailogIns.zIndex(maxZIndex);
					}else{
						dailogIns.hide();
					}
				}
			}
		}
	}
};

//tabpanel 
function selectTabPanel(target,id){
	$("div[jqpoint='taskpanel_tab']").hide();//隐藏所有的窗口
	//显示当前窗口
	var tabId = "taskPanel_tab_"+id;
	$("#"+tabId).show();
};

//选择窗口
function selectDailog(target,id){
	var dailog = getDailogById(target,id);
	if(dailog!=null){
		var dailogIns = dailog['instance'];
		dailogIns.show();
		//判断是否有最大化的设置，如果为true，则将其最大化
		if(dailogIns.used==undefined && dailogIns.maxed===true){
			dailogIns.max();
		}
		//已经使用过的标志位，避免在操作之后再打开时还是最大化的内容
		dailogIns.used = true;
	}
};
/*
//判断窗口是否可见
function isDailogVisible(target,id){
	var dailog = getDailogById(target,id);
	if(dailog!=null){
		var dailogIns = dailog['instance'];
		return dailogIns.isVisible();
	}
	return false;
};*/


//添加panel的函数
function addPanel(target,item,href){
	var opts = $.data(target,'TaskPanel').options;
	var tabPanel = $.data(target,'TaskPanel').panelContainer;
	var id = item.id;
	var url = item.url;
	var tab = getTabById(target,id);
	var tabId = "taskPanel_tab_"+id;
	if(tab==null){
		var tabhtml = "" +
				"<div id='"+tabId+"' jqpoint='taskpanel_tab'  style='width:100%;height:100%;padding:0px;margin:0px;'>" +
					"<iframe allowtransparency='true' onload='myframeload(this);' frameborder='0' marginheight='0' marginwidth='0' style='border:0px; padding:0px; margin:0px; width:100%; height:100%;' src='"+url+"' scrolling='auto'></iframe>" +
				"</div>" +
				"";
		//console.log(tabhtml);
		tab = $(tabhtml).appendTo(tabPanel);
		if(window.$.fn.mCustomScrollbar){
			tab.mCustomScrollbar({
				theme:"minimal-dark"
			});
		}
		
		//将窗口对象添加到内存中
		($.data(target,'TaskPanel').tabs).push({id:id,instance:tab});
	}
};

//通过编号查询tab的dom对象
function getTabById(target,id){
	var tabs = $.data(target,'TaskPanel').tabs;
	var result = null;
	if(tabs!=null && tabs.length > 0){
		for(var i=0,len=tabs.length;i<len;i++){
			if(id==tabs[i]['id']){
				result = tabs[i];
				break;
			}
		}
	}
	return result;
};


//通过窗口的编号查询QUI窗口对象
function getDailogById(target,id){
	var dailogs = $.data(target,'TaskPanel').dailogs;
	var result = null;
	if(dailogs!=null && dailogs.length > 0){
		for(var i=0,len=dailogs.length;i<len;i++){
			if(id==dailogs[i]['id']){
				result = dailogs[i];
				break;
			}
		}
	}
	return result;
};


//添加窗口的函数
function addDailog(target,item,href){
	var opts = $.data(target,'TaskPanel').options;
	var id = item.id;
	var dailog = getDailogById(target,id);
	if(dailog==null){
		var dialog = new top.Dialog();
		var width = item.width;//菜单对象中设置的宽度
		var height = item.height;//菜单对象中设置的高度
		var maxed = item.maxed;//是否让窗口在创建时就是最大化
		if(width==undefined){
			width = opts.dailog.width;
		}
		if(height==undefined){
			height = opts.dailog.height;
		}
		dialog.ID = "qui_dailog_"+id;
		dialog.Title = item.title;
		dialog.ShowMaxButton = true;//有最大化
		dialog.Modal = false;//不适用模式窗口
		dialog.AllowChangeIndex = true;
		dialog.ShowMinButton = true;//有最小化
		dialog.MinEvent = function(){//最小化按钮执行事件
			dialog.hide();
		};
		dialog.CancelEvent = function(){//关闭按钮的执行事件
			closeDialog(target,id);//关闭窗口的方法
			return false;
		};
		dialog.URL = item.url;
		dialog.Height = height;
		dialog.Width = width;
		if(maxed===true){
			dialog.maxed = true;
		}
		//将窗口对象添加到内存中
		($.data(target,'TaskPanel').dailogs).push({id:id,instance:dialog});
	}
};

//选择按钮
function selectBtn(target,id){
	var TaskContainerInner = $.data(target,'TaskPanel').TaskContainerInner;
	TaskContainerInner.find("div[class*='taskGroup']").removeClass("taskSelected");
	TaskContainerInner.find('div[id="'+id+'"]').addClass("taskSelected");
};

//关闭窗口的方法
function closeDialog(target,id){
	var TaskContainerInner = $.data(target,'TaskPanel').TaskContainerInner;
	TaskContainerInner.TaskButton('delBtn',id,target);//调用删除按钮的方法
};

//移除tab内容
function removePanel(target,id){
	var opts = $.data(target,'TaskPanel').options;
	var buttonId = opts.btnIndex + id;
	var isDailogModel = opts.isDailogModel;//判断是否为弹出窗口方式
	
	//删除按钮
	delTaskButton(target,buttonId);
	
	//选择窗口或者tab
	var lastObjs = [];
	var delIndex = 0;
	if(isDailogModel===false){
		delIndex = delTab(target,id);//删除tab
		lastObjs = $.data(target,'TaskPanel').tabs;
	}else{
		delIndex = delDialog(target,id);//删除窗口
		lastObjs = $.data(target,'TaskPanel').dailogs;
	}
	
	//删除之后选择下一个按钮
	if(lastObjs!=null && lastObjs.length > 0){
		var nextIndex = delIndex - 1;
		if(nextIndex==-1){
			nextIndex = 0;
		}
		var lastObj = lastObjs[nextIndex];
		//多窗口模式,不用选中当前tab和tab按钮
		if(!opts.isDailogModel){
			selectTab(target,lastObj.id);
		}
	}
};

//删除tab
function delTab(target,id){
	//从缓存中查询到窗口，之后执行关闭的操作
	var tab = getTabById(target,id);
	if(tab!=null){
		$(tab['instance']).remove();
	}
	//根据编号去查询是否存在按钮，并返回该按钮的索引
	var index = getTabIndexById(target,id);
	if(index!=-1){
		var bb  = ($.data(target,'TaskPanel').tabs).del(index);
		$.data(target,'TaskPanel').tabs = bb;
	}
	return index;
};

//删除面板的函数
function delDialog(target,id){
	//从缓存中查询到窗口，之后执行关闭的操作
	var dailog = getDailogById(target,id);
	if(dailog!=null){
		dailog['instance'].close();
	}
	//根据编号去查询是否存在按钮，并返回该按钮的索引
	var index = getDailogIndexById(target,id);
	if(index!=-1){
		var bb  = ($.data(target,'TaskPanel').dailogs).del(index);
		$.data(target,'TaskPanel').dailogs = bb;
	}
	return index;
};

//删除taskbutton的操作
function delTaskButton(target,btnId){
	var opts = $.data(target,'TaskPanel').options;
	//根据编号去查询是否存在按钮，并返回该按钮的索引
	var index = getBtnById(target,btnId);
	if(index!=-1){
		var bb  = (opts.btns).del(index);
		opts.btns = bb;
		resizeTaskContainer(target);
	}
};

//通过编号去查询缓存中的tab的索引号
function getTabIndexById(target,id){
	var tabs = $.data(target,'TaskPanel').tabs;
	var index = -1;
	for(var i=0,len = tabs.length;i<len;i++){
		var tmpId = tabs[i]['id'];
		if(tmpId==id){
			index=i;
			break;
		}	
	}
	return index;
};

//通过编号去查询缓存中的窗口的索引号
function getDailogIndexById(target,id){
	var dailogs = $.data(target,'TaskPanel').dailogs;
	var index = -1;
	for(var i=0,len = dailogs.length;i<len;i++){
		var tmpId = dailogs[i]['id'];
		if(tmpId==id){
			index=i;
			break;
		}	
	}
	return index;
};

//通过编号去查询，并返回查找的索引号
function getBtnById(target,btnId){
	var opts = $.data(target,'TaskPanel').options;
	var index = -1;
	for(var i=0,len = (opts.btns).length;i<len;i++){
		var tmp = (opts.btns)[i];
		var tmpId = tmp.attr('id');
		if(tmpId==btnId){
			index=i;
			break;
		}	
	}
	return index;
};





//重新计算容器大小的函数
function resizeTaskContainer(target){
	var opts = $.data(target,'TaskPanel').options;
	var btns = opts.btns;
	var tabPanel = $.data(target,'TaskPanel').panelContainer;
	var taskPanel = $.data(target,'TaskPanel').TaskPanel;
	var taskPanel_h = $(taskPanel).height();
	var TaskContainer = $.data(target,'TaskPanel').TaskContainer;
	var TaskContainerInner = $.data(target,'TaskPanel').TaskContainerInner;
	var isDailogModel = opts.isDailogModel;
	
	//获取当页面的宽度和高度
	var pwidth = opts.pageWidth ;
	var pheight = opts.pageHeight ;
	//var tabPanelWidth = pwidth;
	
	
	//判断是否为传统方式，如果为传统方式则计算tabpanel的高度
	if(isDailogModel===false){
		var tabPanelWrap = tabPanel.parent();
		var tabPanelWrap_h = $(tabPanelWrap).outerHeight();
		var tabPanelWrap_w = $(tabPanelWrap).outerWidth();
		tabPanel.height(tabPanelWrap_h );
	}
	
	//设置taskpanel的位置
	taskPanel.css('top',(pheight - taskPanel.height())+'px');
	taskPanel.css('left',"0px");
	taskPanel.width(pwidth);

	var TaskContainerInner_width = 0;
	var TaskContainer_width = pwidth;
	if(btns.length > 0){
		var itemBtn = btns[0];
		TaskContainerInner_width = (itemBtn.outerWidth(true))*btns.length;
		//如果内部宽度超出总长度范围则显示左右滚动按钮
		if(TaskContainerInner_width > pwidth){
			$.data(target,'TaskPanel').TaskNextBox.css("display","block");
			$.data(target,'TaskPanel').TaskPreBox.css("display","block");
			TaskContainer_width = pwidth - 60;
		}else{
			$.data(target,'TaskPanel').TaskNextBox.css("display","none");
			$.data(target,'TaskPanel').TaskPreBox.css("display","none");
			TaskContainerInner.css("left","0px");
		}
	}
	if(TaskContainerInner_width==0){
		TaskContainerInner.removeAttr("style");	
	}else{
		TaskContainerInner.width(TaskContainerInner_width);	
	}
	TaskContainer.width(TaskContainer_width);
	
};

//设置当前页面的大小
function setPageSize(target,width,height){
	var opts = $.data(target,'TaskPanel').options;
	opts.pageWidth = width;
	opts.pageHeight = height;
};

//隐藏所有对话框
function dialogHideAll(target){
	var opts = $.data(target,'TaskPanel').options;
	var isDailogModel = opts.isDailogModel;
	
	//是对话框类型
	if(isDailogModel){
		var aryDialogs=$.data(target,'TaskPanel').dailogs;
		if(aryDialogs){
			for(var i=0;i<aryDialogs.length;i++){
				var di=aryDialogs[i].instance;
				di.hide();
			}
		}
	}
	

};
//插件类
$.fn.TaskPanel = function(options,arg1,arg2){
	if (typeof options == 'string'){
		switch(options){
			case 'options':
				return $.data(this[0], 'TaskPanel').options;
			case 'setPageSize'://设置当前页面的大小，包括两个参数，宽度和高度
				return this.each(function(){
					setPageSize(this,arg1,arg2);
				});
			case 'resizeTaskContainer'://重新计算大小
				return this.each(function(){
					resizeTaskContainer(this);
				});
			case 'addTaskPanel'://添加tab页面
				return this.each(function(){
					addTaskPanel(this,arg1,arg2);
				});
			case 'removePanel'://移除面板和按钮
				return this.each(function(){
					removePanel(this,arg1);
				});
			case 'selectTab'://选中tab页面和按钮
				return this.each(function(){
					selectTab(this,arg1);
				});	
			case 'toggleSelectDialog'://选中tab页面和按钮
				return this.each(function(){
					toggleSelectDialog(this,arg1);
				});	
			case 'dialogHideAll'://对话框全部隐藏
				return this.each(function(){
					dialogHideAll(this);
				});	
				
		}
	};
		
	options = options || {};

	return this.each(function(){
		var state = $.data(this, 'TaskPanel');
		var opts;
		if (state){
			opts = $.extend(state.options, options);
		}else{
			opts = $.extend({}, $.fn.TaskPanel.defaults, options);		
			state = $.data(this, 'TaskPanel', {
				options: opts
			});

		}	
		//初始化taskPanel内容
		init(this);
	});
};

//默认构造函数制定
$.fn.TaskPanel.defaults = {
	id:'TaskPanel',
	btnIndex:'taskGroup_',
	panelIndex:'tabGroup_',
	pageWidth:0,//当前页面的宽度
	pageHeight:0,//当前页面的高度  通过页面的高度和宽度计算taskpanel的位置
	btns:[],
	el:'',
	isDailogModel:true,//是否为弹出窗口方式
	dailog:{width:600,height:300},
	removeAble:true,//如果此项为false时，在关闭tab时，只将其隐藏，不删除该点的DOM
	minWidth:800
};



})(jQuery);

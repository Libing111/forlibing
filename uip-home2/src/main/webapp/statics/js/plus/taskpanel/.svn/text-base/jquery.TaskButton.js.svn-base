/**
 * 描述：状态栏按钮插件
 * 作者：温泉
 * */
(function($){
//添加按钮的方法
function addTaskButton(target,btnObj,ptarget){
	//将父dom对象的target传入至对象
	$.data(target,'TaskButton').ptarget = ptarget;
	var popts = $.data(ptarget,'TaskPanel').options;
	var opts = $.data(target,'TaskButton').options;
	var menuIdIdx = opts.menuIdIdx;//右键菜单的索引号
	var menuId = menuIdIdx + btnObj.id;//右键菜单实例dom的Id
	var menuShardonIdIdx = opts.menuShardonIdIdx;//右键菜单阴影的索引号
	var menuShardonId = menuShardonIdIdx + btnObj.id;//右键菜单实例dom的Id
	var taskButtonId = popts.btnIndex + btnObj.id;
	var icon = btnObj.icon;
	var btIconStr = "";
	if(icon!=null && icon.length > 0){
		btIconStr = "plat-module-"+icon;
	}
	var tmplt = ''+
		'<div id="'+taskButtonId+'" class="taskGroup taskBg">'+
			'<div class="taskItemIcon '+btIconStr+'"></div>'+
			'<div class="taskItemTxt" title="'+btnObj.title+'">'+btnObj.title+'</div>'+
		'</div>';
	
	//添加到taskpanel中
	var taskButton = $(tmplt).appendTo(target);
	//创建右键菜单的实例  （QUI 的右键菜单）
	var menu  = $.rightClickMenu({ width: 120, items:
	    [{ text: '关闭全部窗口',btnObj:btnObj,target:target,click:closeAllDailog},
	     { text: '关闭其他窗口',btnObj:btnObj,target:target,click:closeOtherDailog},
	     { text: '关闭当前窗口',btnObj:btnObj,target:target,click:closeCurrentDailog}]
	 }); 
	$.data(taskButton,'menu',menu);
	menu.ptarget=ptarget;
	//得到右键菜单的html的dom
	var menuDom = $(menu.menu);
	menuDom.attr('taskMenuId',menuId);
	var menuShardonDom = menuDom.next("div.l-menu-shadow");//得到右键菜单的阴影dom
	menuShardonDom.attr('taskMenuShardonId',menuShardonId);
	//绑定右键菜单的内容
	taskButton.bind("contextmenu", function (e){
		menuShow(menu,e);
        return false;
    });
	
	//关闭按钮函数
	var closeBtn = null;
	
	//判断是否添加关闭按钮
	if(btnObj.closeable==null || btnObj.closeable==undefined || btnObj.closeable==true){
		taskButton.attr('disable','false');
		//contextMenu.attr('disable','false');
		closeBtn = $('<div class="taskItemClose"></div>').appendTo(taskButton);
		//绑定关闭的点击事件
		closeBtn.bind('click',{target:target,id:btnObj.id},closeEvent);
	}else{
		taskButton.attr('disable','true');
		//contextMenu.attr('disable','true');
		
		//contextMenu.omMenu('disableItem','item1');
	}
	
	//添加变换事件
	taskButton.hover(
		function () {
			$(this).addClass("taskHover");
			$(this).removeClass("taskBg");
			if(closeBtn){
				closeBtn.css("display","block");
			}
	  	},
	  	function () {
			$(this).addClass("taskBg");
			$(this).removeClass("taskHover");
			if(closeBtn){
				closeBtn.css("display","none");
			}
	  	}
	);
	
	//绑定点击事件
	taskButton.bind('click',{target:target,id:btnObj.id},clickEvent);
	$.data(target,'TaskButton').taskButton = taskButton;
	
	return taskButton;
};

//显示右键菜单的方法
function menuShow(menu,e){
	var ptarget=menu.ptarget;
	var popts = $.data(ptarget,'TaskPanel').options;
	//在显示当前快捷菜单时，需要将已打开的快捷菜单隐藏
	for(var i=0;i<popts.btns.length;i++){
		var $btn=popts.btns[i];
		var $menu = $.data($btn,'menu');
		$menu.hide();
	}
	//右键菜单的dom节点
	var menuDom = $(menu.menu);
	var menuHeight = menuDom.height();
	var menuWidth = menuDom.width();
	var currentRightY = e.pageY;
	var currentRightX = e.pageX;
	var window_height = $(window).height();
	var window_width = $(window).width();
	var realY = currentRightY;
	var realX = currentRightX;
	if((currentRightY + menuHeight) > window_height){
		realY = window_height - menuHeight - 10;
	}
	if((currentRightX + menuWidth) > window_width){
		realX = window_width - menuWidth - 10;
	}
	menu.show({ top: realY, left: realX });
};

//关闭所有窗口
function closeAllDailog(item){
	var target = item.target;
	//获取父dom的target
	var ptarget = $.data(target,'TaskButton').ptarget;
	delAllBtn(target,ptarget);
};

//关闭其他窗口
function closeOtherDailog(item){
	var btnObj = item.btnObj;
	var target = item.target;
	//获取父dom的target
	var ptarget = $.data(target,'TaskButton').ptarget;
	delOtherBtn(target,btnObj,ptarget);	
};

//关闭当前窗口
function closeCurrentDailog(item){
	var btnObj = item.btnObj;
	var target = item.target;
	//获取父dom的target
	var ptarget = $.data(target,'TaskButton').ptarget;
	var id = btnObj.id;
	delBtn(target,id,ptarget);	
};


//关闭task的点击事件
function closeEvent(e){
	var target = e.data.target;
	var id = e.data.id;
	//获取父dom的target
	var ptarget = $.data(target,'TaskButton').ptarget;
	//调用删除函数
	delBtn(target,id,ptarget);	
};
//删除其他按钮的函数
function delOtherBtn(target,btnObj,ptarget){
	var id = btnObj.id;
	//删除所有
	var popts = $.data(ptarget,'TaskPanel').options;
	var btns = $(ptarget).find('div[id^="'+popts.btnIndex+'"][disable="false"]');
	//循环删除
	for(var i=0,len=btns.length;i<len;i++){
		var btnId = ($(btns[i]).attr("id")).replace(popts.btnIndex,"");
		if(id!=btnId){
			delBtn(target,btnId,ptarget);
		}
	}
};
//删除按钮的函数
function delBtn(target,id,ptarget){
	var popts = $.data(ptarget,'TaskPanel').options;
	var opts = $.data(target,'TaskButton').options;
	var menuIdIdx = opts.menuIdIdx;//右键菜单的索引号
	var menuId = menuIdIdx + id;//右键菜单实例dom的Id
	var menuShardonIdIdx = opts.menuShardonIdIdx;//右键菜单阴影的索引号
	var menuShardonId = menuShardonIdIdx + id;//右键菜单实例dom的Id
	//开始删除按钮
	var btn = $(ptarget).find('div[id="'+popts.btnIndex + id+'"][disable="false"]');
	if(btn.length > 0){
		//删除右键菜单
		$("div[taskmenuid='"+menuId+"']").remove();//删除右键菜单
		$("div[taskmenushardonid='"+menuShardonId+"']").remove();//删除右键菜单阴影
		//删除按钮
		btn.remove();
		//删除按钮的反调函数，调用taskpanel的删除函数
		$(ptarget).TaskPanel('removePanel',id);
	};
};



//删除有按钮
function delAllBtn(target,ptarget){
	var popts = $.data(ptarget,'TaskPanel').options;
	var btns = $(ptarget).find('div[id^="'+popts.btnIndex+'"][disable="false"]');
	//循环删除
	for(var i=0,len=btns.length;i<len;i++){
		var btnId = ($(btns[i]).attr("id")).replace(popts.btnIndex,"");
		delBtn(target,btnId,ptarget);
	}
};

//点击事件
function clickEvent(e){
	var target = e.data.target;
	var id = e.data.id;
	var ptarget = $.data(target,'TaskButton').ptarget;
	var popts = $.data(ptarget,'TaskPanel').options;
	if(popts.isDailogModel){//多窗口方式
		$(ptarget).TaskPanel('toggleSelectDialog',id);
	}else{
		//调用选中方法
		$(ptarget).TaskPanel('selectTab',id);
	}
	
};

//插件类
$.fn.TaskButton = function(options,arg1,arg2){
	if (typeof options == 'string'){
		switch(options){
			case 'options':
				return $.data(this[0], 'TaskButton').options;
			case 'addTaskButton'://添加portlet内容
				return addTaskButton(this[0],arg1,arg2);
			case 'delBtn'://删除按钮的方法
				return this.each(function(){
					delBtn(this,arg1,arg2);
				});
		}
	};
		
	options = options || {};

	return this.each(function(){
		var state = $.data(this, 'TaskButton');
		var opts;
		if (state){
			opts = $.extend(state.options, options);
		}else{
			opts = $.extend({}, $.fn.TaskButton.defaults, options);		
			state = $.data(this, 'TaskButton', {
				options: opts
			});

		}
	});
};

//默认构造函数制定
$.fn.TaskButton.defaults = {
	id:undefined,
	menuIdIdx:'task_rightMenu_',
	menuShardonIdIdx:'task_rightMenuShardon_',
	idIndex:'taskGroup_',
	title:'test',
	icon:'',
	closeable:true,
	href:''
};



})(jQuery);

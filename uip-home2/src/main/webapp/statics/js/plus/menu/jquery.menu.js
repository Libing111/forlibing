/**
 * 描述：一级菜单插件
 * 作者：温泉
 * */

/**
 * 一级菜单格式json
 * menuData:[{
 * 	id:'', //菜单编号，菜单构造是的唯一标识
 * 	title:'',//菜单标题
 * 	url:''//菜单执行的URL
 * }]
 * */
(function($){
	//初始化函数
	function init(target){
		var opts = $.data(target,'Menu').options;
		//定义单个菜单的宽度，包括左右空白
		$.data(target,'Menu').menuItemWidth = 120 + 5 + 5;
		
		var menuData = opts.menuData;
		//1.如果menuDataItem中有 data:[] 字段
		//则看看其长度是不是为0，如果为0，则隐藏。
		//2.看看是否是一个有效的URL，如果不是，则隐藏
		var newMenuData=[];
		for(var i=0;i<menuData.length;i++){
			var m=menuData[i];
			if(!m){
				continue;
			}
			if(m.data&&m.data.length==0){
				if(window.console){
					console.log("菜单:"+m.title+" is ignored for it's data's length =0.");
				}
				continue;
			}
			newMenuData.push(m);
		}
		opts.menuData0=menuData;
		menuData=opts.menuData=newMenuData;
		
		//渲染菜单
		setData(target,menuData);//渲染菜单
		
		//浏览器改变大小时执行的事件
		$(window).resize(function() {
			renderMenu(target);
		});
	};
	
	//渲染菜单的方法
	function setData(target,menuData){
		if(menuData!=null && menuData.length > 0){
			$.data(target,'Menu').menuData = menuData;
			//渲染菜单
			renderMenu(target);
		}else{
			$(target).html("菜单数据为空。");
		}
	};
	
	
	
	//渲染菜单的方法
	function renderMenu(target){
		//清空菜单的所有内容
		$(target).empty();
		//得到菜单的数据
		var menuData = $.data(target,'Menu').menuData;
		var menuNum = menuData.length;
		//判断菜单是否需要折行展示
		var menuItemWidth = $.data(target,'Menu').menuItemWidth;
		
		//将ul标签放入进去
		var ulDom = $.data(target,'Menu').ulDom = $('<ul class="plat-menu"></ul>').appendTo(target);
		ulDom.hide();
		
		//根据菜单数据的长度，计算出菜单真实的总长度
		var realWrapWidth = menuNum * menuItemWidth;
		//根据页面的长度获取菜单容器的长度
		var menuWrapWidth = getMenuWrapWidth(target);
		//如果真实长度大于容器长度，则需要折行处理
		if(realWrapWidth > menuWrapWidth){
			//需要计算出对出多少个菜单
			var gap = realWrapWidth - menuWrapWidth;
			var extNum = parseInt(gap/menuItemWidth);//得到扩展的数量
			if(gap % menuItemWidth!=0){
				extNum = extNum + 1;
			}
			var normalNum = menuNum - extNum;
			var tmpNormalMenuArray = [];
			var tmpExtMenuArray = [];
			//将数组进行拆分
			for(var i=0;i<menuNum;i++){
				if(i<=(normalNum-1)){
					tmpNormalMenuArray.push(menuData[i]);
				}else{
					tmpExtMenuArray.push(menuData[i]);
				}
			}
			//渲染扩展按钮
			if(tmpExtMenuArray!=null && tmpExtMenuArray.length > 0){
				$(target).width(menuWrapWidth);
				var menuExtButton = $.data(target,'Menu').menuExtButton = $('<div class="menu-ext-bt"><span id="curExtMenuTxt"></span></div>').appendTo(target);
				menuExtButton.bind("click",{target:target},menuExtBtEvent);
				menuExtButton.css("left",(normalNum * menuItemWidth)+"px");
				//渲染扩展菜单
				_renderExtMenu(target,tmpExtMenuArray);
			}
			//渲染正常菜单
			_renderNormalMenu(target,tmpNormalMenuArray);
		}else{
			//直接渲染正常菜单
			_renderNormalMenu(target,menuData);
		}
		
		//大于1的时候显示菜单，否则隐藏菜单
		if(menuNum > 1){
			ulDom.show()
		}
	};
	
	//扩展按钮的点击事件
	function menuExtBtEvent(e){
		var target = e.data.target;
		var menuExtWrapDom = $(target).find('div.plat-menu-ext-wrap').get(0);
		if(menuExtWrapDom.style.display=="none"){
			$(menuExtWrapDom).css('display',"block");
		}else{
			$(menuExtWrapDom).css('display',"none");
		}
		$('body').bind('mousedown.menuExtWrapClick',{target:target},menuExtBodyClick);
	};
	
	
	function menuExtBodyClick(e){
		var target = e.data.target;
		if (!(e.target.id == "plat-menu-ext-wrap" || $(e.target).parents("#plat-menu-ext-wrap").length > 0)) {
			hideMenuExtWrap(target);
		}
	}
	
	function hideMenuExtWrap(target){
		$(target).find('div.plat-menu-ext-wrap').css("display","none");
		$(document.body).unbind('mousedown.menuExtWrapClick');
	}
	
	//渲染扩展菜单
	function _renderExtMenu(target,menus){
		var opts = $.data(target,'Menu').options;
		var idIndex = opts.idIndex;
		//扩展菜单容器
		var menuExtWrap = $('<div id="plat-menu-ext-wrap" class="plat-menu-ext-wrap" style="display:none"></div>').appendTo(target);
		var ulDom = $('<ul class="plat-menu-ext-ul"></ul>').appendTo(menuExtWrap);
		for(var i=0,len=menus.length;i<len;i++){
			var menuId = idIndex + menus[i]['id'];
			var menuUrl = menus[i]['url'];
			var menuTitle = menus[i]['title'];
			var liDom = $('<li id="'+menuId+'" jqpoint="menuExtNode" title="'+menuTitle+'"><a href="javascript:void(0);" url="'+menuUrl+'">'+menuTitle+'</a></li>').appendTo(ulDom);
			//绑定点击事件
			if(i==(len - 1) ){
				$(liDom).addClass('last');
			}
			$(liDom).bind('click',{target:target,menuItem:menus[i]},menuExtClickEvent);
		}
	};
	
	//渲染正常菜单
	function _renderNormalMenu(target,menus){
		var opts = $.data(target,'Menu').options;
		var idIndex = opts.idIndex;
		var ulDom = $.data(target,'Menu').ulDom;
		for(var i=0,len=menus.length;i<len;i++){
			var menuId = idIndex + menus[i]['id'];
			var menuUrl = menus[i]['url'];
			var menuTitle = menus[i]['title'];
			var liDom = $('<li id="'+menuId+'" jqpoint="menuNode" title="'+menuTitle+'"><a href="javascript:void(0);" url="'+menuUrl+'">'+menuTitle+'</a></li>').appendTo(ulDom);
			//绑定点击事件
			$(liDom).bind('click',{target:target,menuItem:menus[i]},menuClickEvent);
		}
		var menuId = $.data(target,'Menu').selectedId;
		if(menuId!=undefined){
			$(target).find('li[jqpoint="menuNode"]').removeClass("selected");
			$(target).find('li[jqpoint="menuExtNode"]').removeClass("selected");
			$("#"+menuId).addClass('selected');
		}
	};
	
	//重新计算大小的函数
	function getMenuWrapWidth(target){
		var win_w = $(window).width();
		var logoPanel_w = $("#logoPanel").outerWidth(true);
		var toolsPanel_w = $("#toolsPanel").outerWidth(true);
		return win_w - logoPanel_w - toolsPanel_w - 80;//右侧留80px的空余空间
	}
	/**
	 * 设置当前选中的扩展菜单的名称
	 */
	function setCurExtMenuText(target,extText){
		$(target).find("#curExtMenuTxt").text(extText);
	}
	//菜单点击事件
	function menuExtClickEvent(e){
		var target = e.data.target;
		var menuItem = e.data.menuItem;
		setCurExtMenuText(target,menuItem.title);
		//关闭扩展
		menuItemSelect(target,menuItem);
	};
	
	//菜单的选择点击事件
	function menuItemSelect(target,menuItem){
		if(menuItem){
			var opts = $.data(target,'Menu').options;
			var menuActionCallBack = opts.menuActionCallBack;
			hideMenuExtWrap(target);
			$(target).find('li[jqpoint="menuNode"]').removeClass("selected");
			$(target).find('li[jqpoint="menuExtNode"]').removeClass("selected");
			
			var menuId = $.data(target,'Menu').selectedId = opts.idIndex + menuItem.id;
			$.data(target,'Menu').selectedItem =menuItem;//当前选中的menuItem
			$("#"+menuId).addClass('selected');
			//执行回调函数
			if(menuActionCallBack!=null && typeof(menuActionCallBack)=="function"){
				menuActionCallBack(menuItem);
			}
		}else{//返回当前选中菜单对应的item
			var item=$.data(target,'Menu').selectedItem;
			return item;
		}
	};
	
	
	//显示的菜单的点击事件
	function menuClickEvent(e){
		var target = e.data.target;
		var menuItem = e.data.menuItem;
		setCurExtMenuText(target,"");
		//选中菜单
		menuItemSelect(target,menuItem);
	};
	
	//插件类
	$.fn.Menu = function(options,arg1,arg2){
		if (typeof options == 'string'){
			switch(options){
				case 'options':
					return $.data(this[0], 'Menu').options;
				case 'setData'://添加portlet内容
					return this.each(function(){
						setData(this,arg1,arg2);
					});
				case 'select'://添加portlet内容
					if(arg1){
						return this.each(function(){
							menuItemSelect(this,arg1);
						});
					}else{//返回当前对应的item
						return menuItemSelect(this[0]);
					}
					
			}
		};
			
		options = options || {};

		return this.each(function(){
			var state = $.data(this, 'Menu');
			var opts;
			if (state){
				opts = $.extend(state.options, options);
			}else{
				opts = $.extend({}, $.fn.Menu.defaults, options);		
				state = $.data(this, 'Menu', {
					options: opts
				});
			}
			
			//初始化taskPanel内容
			init(this);
		});
	};
	

	//默认构造函数制定
	$.fn.Menu.defaults = {
		idIndex:'menu_',
		menuActionCallBack:function(menuItem){},
		menuData:[]
	};

})(jQuery);
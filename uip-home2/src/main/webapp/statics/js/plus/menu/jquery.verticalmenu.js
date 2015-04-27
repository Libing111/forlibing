/**
 * 描述：纵向一级菜单插件
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
		var opts = $.data(target,'VerticalMenu').options;
		var menuData = opts.menuData;
		var targetWrap = $(target).wrap();
		var targetWrap_w = $.data(target,'VerticalMenu').targetWrap_w = targetWrap.width();
		//渲染菜单
		setData(target,menuData);//渲染菜单
	};
	
	//渲染惨淡的方法
	function setData(target,menuData){
		if(menuData!=null && menuData.length > 0){
			//将children重新清空，要不会出现重复树形子菜单的作用：shendongsheng
			for(var i=0;i<menuData.length;i++){
				menuData[i].children=null;
			}
			$.data(target,'VerticalMenu').menuData = menuData;
			//渲染菜单
			renderMenu(target);
		}else{
			$(target).html("菜单数据为空。");
		}
	};
	
	//隐藏显示的按钮
	function excpEvent(e){
		var target = e.data.target;
		var liDom = e.data.liDom;
		var code = $(liDom).attr('code');
		var menuWrapId = "MenuWrapId_" + code;
		var menuWrapElement = $("#"+menuWrapId).get(0);
		if(menuWrapElement.style.display=="none"){
			showMenu(target,code);
		}else{
			hideMenu(target,code);
		}
		return false;
	};
	
	//显示菜单的方法
	function showMenu(target,menuId){
		var opts = $.data(target,'VerticalMenu').options;
		var idIndex = opts.idIndex;
		var menuid = idIndex + menuId;
		var menuWrapId = "MenuWrapId_" + menuId;
		var menuWrapElement = $("#"+menuWrapId).get(0);
		if(menuWrapElement!=null){
			$("#"+menuWrapId).show();
			$("#"+menuid).addClass("noBorder");
			var excpBt = $("#"+menuWrapId).prev('div[jqpoint="excpBt"]');
			var excpBtCss = ($(excpBt).attr('class')).replace("-expand","-cp");
			$(excpBt).attr('class',excpBtCss);
		}
	};
	
	
	//隐藏菜单的方法
	function hideMenu(target,menuId){
		var opts = $.data(target,'VerticalMenu').options;
		var idIndex = opts.idIndex;
		var menuid = idIndex + menuId;
		var menuWrapId = "MenuWrapId_" + menuId;
		var menuWrapElement = $("#"+menuWrapId).get(0);
		if(menuWrapElement!=null){
			$("#"+menuWrapId).hide();
			$("#"+menuid).removeClass("noBorder");
			var excpBt = $("#"+menuWrapId).prev('div[jqpoint="excpBt"]');
			var excpBtCss = ($(excpBt).attr('class')).replace("-cp","-expand");
			$(excpBt).attr('class',excpBtCss);
		}
	};
	
	//菜单的点击事件
	function itemClickEvent(e){
		var target = e.data.target;
		var menuItem = e.data.menuItem;
		var liDom = e.data.liDom;
		var childrenMenu = liDom.children('ul');
		//说明有子菜单，则展开子菜单
		if(childrenMenu.length > 0){
			var code = $(liDom).attr('code');
			var menuWrapId = "MenuWrapId_" + code;
			var menuWrapElement = $("#"+menuWrapId).get(0);
			if(menuWrapElement.style.display=="none"){
				showMenu(target,code);
			}else{
				hideMenu(target,code);
			}
		}else{
			_clickAction(target,menuItem);
		}
		
		return false;
	};
	
	//点击事件的执行动作
	function _clickAction(target,menuItem){
		var opts = $.data(target,'VerticalMenu').options;
		var idIndex = opts.idIndex;
		var menuid = idIndex + menuItem.id;
		var menuActionCallBack = opts.menuActionCallBack;
		//添加选中样式
		$(target).find('div[jqpoint="v-menu-item"]').removeClass("selected");
		$("#"+menuid).children('div[jqpoint="v-menu-item"]').addClass('selected');
		//执行回调函数
		if(menuActionCallBack!=null && typeof(menuActionCallBack)=="function"){
			menuActionCallBack(menuItem);
		}
	};
	
	//选择菜单的方法
	function select(target,menuItem){
		//按照逻辑展开其上级所有的菜单
		_showParent(target,menuItem);
		//执行点击动作
		_clickAction(target,menuItem);
	};
	//取消菜单的方法
	function unselect(target,menuItem){
		var code = menuItem.id;
		var liDom = $(target).find('li[code="'+code+'"]');
		var divDom = $(liDom).children('div[jqpoint="v-menu-item"]');
		$(divDom).removeClass('selected');
	};
	//根据菜单判断当前节点是否展开
	function isExpand(target,menuItem){
		var flag = false;
		var code = menuItem.id;
		var liDom = $(target).find('li[code="'+code+'"]');
		if(liDom.length > 0){
			var childrenMenu = liDom.children('ul');
			if(childrenMenu.length > 0){
				var menuWrapId = "MenuWrapId_" + code;
				var menuWrapElement = $("#"+menuWrapId).get(0);
				if(menuWrapElement.style.display=="none"){
					flag = false;
				}else{
					flag = true;
				}
			}
		}
		return flag;
	};
	
	//根据菜单依次递归展开父亲菜单
	function _showParent(target,menuItem){
		var opts = $.data(target,'VerticalMenu').options;
		var idIndex = opts.idIndex;
		var menuid = idIndex + menuItem.id;
		var currentLiDom = $("#"+menuid);
		var menuPid = currentLiDom.attr('parentcode');
		//展开父级别菜单
		showMenu(target,menuPid);
		if(menuPid!=null){
			_showParent(target,getMenuItemById(target,menuPid));
		}
	};
	
	//根据菜单的编号得到菜单对象
	function getMenuItemById(target,id){
		var menuItem = null;
		var menus = $.data(target,'VerticalMenu').menuData;
		if(menus!=null && menus.length > 0){
			for(var i=0,len=menus.length;i<len;i++){
				if(id==menus[i]['id']){
					menuItem = menus[i];
					break;
				}
			};
		}
		return menuItem;
	};
	
	//得到菜单的树形结构数据
	function getTreeData(target){
		return $.data(target,'VerticalMenu').treeData;
	};
	
	//渲染菜单的方法
	function renderMenu(target){
		var opts = $.data(target,'VerticalMenu').options;
		var idIndex = opts.idIndex;
		
		$(target).empty();
		$(target).addClass('plat-vertical-menu');
		//获取到数据
		var menuData = $.data(target,'VerticalMenu').menuData;
		//将数据变成tree的数据结构  
		menuData = $.data(target,'VerticalMenu').treeData = transformTozTreeFormat({
	 		idKey:'id',
	 		pIdKey:'pid',
	  		childrenKey:'children'
	  	},menuData);
		
		
		//渲染一级菜单
		for(var i=0,len=menuData.length;i<len;i++){
			var url = menuData[i]['url'];
			var id = menuData[i]['id'];
			var menuid = idIndex + id;
			var children = menuData[i]['children'];
			var title = menuData[i]['title'];
			var icon = menuData[i]['icon'];
			var liHtml = '<li id="'+menuid+'" code="'+id+'" url="'+url+'" jqpoint="v-menu-item-li">' + 
						'	<div class="plat-vertical-menu-1-item" jqpoint="v-menu-item">' + 
						'		<table cellpadding="0" cellspacing="0">' + 
						'			<tr>' + 
						'				<td><div jqpoint="v-menu-item-icon" class="plat-vertical-menu-1-item-icon plat-module-'+icon+'"></div></td>' + 
						'				<td><div jqpoint="v-menu-item-txt" class="plat-vertical-menu-1-item-txt" title="'+title+'">'+title+'</div></td>' + 
						'			</tr>' + 
						'		</table>' + 
						'	</div>' + 
						'</li>';
			var liDom = $(liHtml).appendTo(target);
			//设置item的宽度
			var targetWrap_w = $.data(target,'VerticalMenu').targetWrap_w;
			var iconWidth = $(liDom).children('div.plat-vertical-menu-1-item').find('div[jqpoint="v-menu-item-icon"]').width();
			var txtWidth = targetWrap_w - iconWidth - 40;
			$(liDom).children('div.plat-vertical-menu-1-item').find('div[jqpoint="v-menu-item-txt"]').width(txtWidth);
			//判断是否有二级菜单
			if(children!=null && children.length > 0){
				var excpBt = $('<div class="plat-vertical-menu-1-expand" jqpoint="excpBt"></div>').appendTo(liDom);
				excpBt.bind('click',{target:target,liDom:liDom},excpEvent);
				//渲染二级菜单
				renderSecMenu(target,children,liDom);
			}else{
				
			}
			
			//绑定事件
			liDom.bind('click',{target:target,liDom:liDom,menuItem:menuData[i]},itemClickEvent);
		};
		
	};
	
	//渲染二级菜单的内容 此处是一个递归函数
	function renderSecMenu(target,menu,liDom){
		var iconDom = $(liDom).find('div[jqpoint="v-menu-item-icon"]');
		var iconDomMarginLeft = $(iconDom).css('margin-left');
		//计算得到marginLeft的内容
		var marginLeft = parseInt(iconDomMarginLeft.replace('px','')) + 20;
		var opts = $.data(target,'VerticalMenu').options;
		var idIndex = opts.idIndex;
		var code = $(liDom).attr('code');
		var secMenuWrapId = "MenuWrapId_" + code;
		var secMenuWrap = $('<ul id="'+secMenuWrapId+'" class="plat-vertical-2-menu" jqpoint="secMenuWrap" style="display:none"></ul>').appendTo(liDom);
		
		for(var i=0,len=menu.length;i<len;i++){
			var url = menu[i]['url'];
			var id = menu[i]['id'];
			var menuid = idIndex + id;
			var children = menu[i]['children'];
			var title = menu[i]['title'];
			var icon = menu[i]['icon'];
			var liHtml = '<li id="'+menuid+'" code="'+id+'" url="'+url+'" jqpoint="v-menu-item-li" parentCode="'+code+'">' + 
						'	<div class="plat-vertical-menu-2-item" jqpoint="v-menu-item">' + 
						'		<table cellpadding="0" cellspacing="0">' + 
						'			<tr>' + 
						'				<td><div jqpoint="v-menu-item-icon" class="plat-vertical-menu-2-item-icon plat-module-'+icon+'-small" style="margin-left:'+marginLeft+'px"></div></td>' + 
						'				<td><div jqpoint="v-menu-item-txt" class="plat-vertical-menu-2-item-txt" title="'+title+'">'+title+'</div></td>' + 
						'			</tr>' + 
						'		</table>' + 
						'	</div>' + 
						'</li>';
			var subliDom = $(liHtml).appendTo(secMenuWrap);
			//设置item的宽度
			var targetWrap_w = $.data(target,'VerticalMenu').targetWrap_w;
			var iconWidth = $(subliDom).children('div.plat-vertical-menu-2-item').find('div[jqpoint="v-menu-item-icon"]').width();
			var txtWidth = targetWrap_w - iconWidth - marginLeft -40;
			$(subliDom).children('div.plat-vertical-menu-2-item').find('div[jqpoint="v-menu-item-txt"]').width(txtWidth);
			
			//判断是否有二级菜单
			if(children!=null && children.length > 0){
				var excpBt = $('<div class="plat-vertical-menu-2-expand" jqpoint="excpBt"></div>').appendTo(subliDom);
				excpBt.bind('click',{target:target,liDom:subliDom},excpEvent);
				//渲染二级菜单
				renderSecMenu(target,children,subliDom);
			}else{
				
			}
			//绑定事件
			subliDom.bind('click',{target:target,liDom:subliDom,menuItem:menu[i]},itemClickEvent);
		};
		
	};
	
	//展开所有菜单的方法
	function showAll(target){
		var menus = $.data(target,'VerticalMenu').menuData;
		if(menus!=null && menus.length > 0){
			for(var i=0,len=menus.length;i<len;i++){
				showMenu(target,menus[i]['id']);
			};
		}
	};
	//关闭所有菜单的方法
	function hideAll(target){
		var menus = $.data(target,'VerticalMenu').menuData;
		if(menus!=null && menus.length > 0){
			for(var i=0,len=menus.length;i<len;i++){
				hideMenu(target,menus[i]['id']);
			};
		}
	};
	
	
	
	//插件类
	$.fn.VerticalMenu = function(options,arg1,arg2){
		if (typeof options == 'string'){
			switch(options){
				case 'options':
					return $.data(this[0], 'VerticalMenu').options;
				case 'setData'://添加portlet内容
					return this.each(function(){
						setData(this,arg1,arg2);
					});
				case 'showAll'://展开所有菜单
					return this.each(function(){
						showAll(this);
					});
				case 'hideAll'://隐藏所有菜单
					return this.each(function(){
						hideAll(this);
					});
				case 'show'://根据item的编号展开菜单
					return this.each(function(){
						showMenu(this,arg1);
					});
				case 'hide'://根据item的编号隐藏菜单
					return this.each(function(){
						hideMenu(this,arg1);
					});
				case 'getMenuItemById'://根据item的编号得到菜单item对象
					return getMenuItemById(this[0],arg1);
				case 'getTreeData'://得到菜单的树形结构数据
					return getTreeData(this[0]);
				case 'select'://根据item将菜单选中并执行点击操作
					return this.each(function(){
						select(this,arg1);
					});
				case 'unselect'://根据item将菜单取消选中状态
					return this.each(function(){
						unselect(this,arg1);
					});
				case 'isExpand'://判断当前节点是否展开，展开返回true，未展开返回false
					return isExpand(this[0],arg1);
			}
		};
			
		options = options || {};

		return this.each(function(){
			var state = $.data(this, 'VerticalMenu');
			var opts;
			if (state){
				opts = $.extend(state.options, options);
			}else{
				opts = $.extend({}, $.fn.VerticalMenu.defaults, options);		
				state = $.data(this, 'VerticalMenu', {
					options: opts
				});
			}
			
			//初始化taskPanel内容
			init(this);
		});
	};
	

	//默认构造函数制定
	$.fn.VerticalMenu.defaults = {
		idIndex:'verticalMenu_',
		menuActionCallBack:function(menuItem){},
		menuData:[]
	};

})(jQuery);
/**
 * 描述：二、三级模块插件
 * 作者：温泉
 * */
/**
 * 一级菜单格式json
 * data:[{
 * 	id:'', //菜单编号，菜单构造是的唯一标识
 *  icon:'',
 * 	title:'',//菜单标题
 * 	url:'',//菜单执行的URL
 *  width:300,//弹出窗口的宽度
 *  height:200,//弹出窗口的高度
 *  maxed:true//是否最大化
 * }]
 * */
(function($){
	
	
	function init(target){
		var opts = $.data(target,'desktop').options;
		//设置模块数据
		var data = opts.data;
		//渲染菜单
		setData(target,data);//渲染菜单
	};
	
	function setData(target,data){
		$(target).empty();
		$.data(target,'desktop').data = [];
		$.data(target,'desktop').data = data;
		//渲染菜单
		renderData(target);
	};
	
	
	function renderData(target){
		var opts = $.data(target,'desktop').options;
		var layout = opts.layout;
		var data = $.data(target,'desktop').data;
		$.data(target,'desktop').container = null;
		$.data(target,'desktop').addBt = null;
		var onUpdate = opts.onUpdate;
		var addBt = null;
		//横向布局
		if("horizontal"==layout){
			var container = $.data(target,'desktop').container = $('<ul class="plat-desktop-wrap"></ul>').appendTo(target);
			
			var addBtHtml = ''+
			'<li class="desktop-add-bt desktop-item-li">'+
			'	<div align="center" class="desktop-item">	'+
			'		<div class="desktop-item-icon desktop-add-bt-icon"></div>	'+
			'		<div class="desktop-item-txt">添加常用功能</div>'+
			'	</div>'+
			'</li>';
			addBt = $.data(target,'desktop').addBt = $(addBtHtml).appendTo(container);
			
			if(data!=null && data.length > 0){
				$.each(data,function(i,dataitem){
					add(target,dataitem);
				});
			};
			
			//绑定拖拽事件
			$(container).sortable({
				items: "li:not(.desktop-add-bt)",
				placeholder: "plat-desktop-placeholder",
				update:function( event, ui ){//更新之后执行的事件
					if(onUpdate!=undefined && typeof(onUpdate)=="function"){
						onUpdate(target,event);
					}
				}
			});
			
		}else if("vertical"==layout){//纵向布局
			$(target).empty();
			var im_w = 100 + 5 * 2;
			var im_h = 90 + 5 * 2;
			var m_length = data.length + 1;
			var wrap_height = $(target).height();
			var cols = parseInt(wrap_height / im_h);//得到列的数量
			var rows = parseInt(m_length / cols);//得到行的数量
			if(m_length % cols != 0){
				rows = rows + 1;
			}
			var colIdx = 0;
			for(i=0;i<rows;i++){
				var ul = $('<ul class="plat-desktop-wrap"></ul>').appendTo(target);
				for(j=0;j<cols;j++){
					$('<li jqpoint="sortitem" colIdx="'+colIdx+'"></li>').appendTo(ul);
					colIdx++;
				}
			}
			
			//添加数据
			$.each(data,function(i,dataitem){
				var id = dataitem['id'];
				var domId = opts.idIndex + id;
				var icon = dataitem['icon'];
				var title = dataitem['title'];
				var closeable = dataitem['closeable'];//是否可以删除
				
				var liDom = $(target).find('li[colIdx="'+i+'"]');
				liDom.attr({id:domId,jqpoint:'sortitem',code:id,title:title});
				liDom.removeAttr("colIdx");
				
				var itemHtml = "" +
				"	<div class='desktop-item' align='center'>" +
				"		<div class='plat-module-"+icon+"'></div>" +
				"		<div class='desktop-item-txt'>"+title+"</div>" +
				"	</div>" +
				"";
				liDom.html(itemHtml);
				

				liDom.bind('mouseup',{target:target,item:dataitem},itemMouseupEvent);
				liDom.bind('mousedown',{target:target,item:dataitem},itemMousedownEvent);
				liDom.bind('click',{target:target,item:dataitem},itemClickEvent);
				
				
				//是否带有删除按钮
				if(closeable==null || closeable==undefined || closeable==true){
					var delBt = $('<div class="itemClose"></div>').appendTo(liDom);
					//添加事件
					delBt.bind('click',{target:target,item:dataitem},modelDeleteEvent);//给删除按钮添加点击事件
				}
			});
			
			//创建添加按钮
			var addBtHtml = ''+
			'<li class="desktop-add-bt">'+
			'	<div align="center" class="desktop-item">	'+
			'		<div class="desktop-item-icon desktop-add-bt-icon"></div>	'+
			'		<div class="desktop-item-txt">添加常用功能</div>'+
			'	</div>'+
			'</li>';
			addBt = $.data(target,'desktop').addBt = $(addBtHtml).appendTo($(target).find('ul.plat-desktop-wrap').last());
			
			//清楚含有colIdx属性的li节点
			$(target).find("li[colIdx]").remove();
			
			
			//绑定拖拽事件
			$(target).find('ul').sortable({
				connectWith: ".plat-desktop-wrap",
				items: "li:not(.desktop-add-bt)",
				placeholder: "plat-desktop-placeholder2",
				start:function(event, ui ){
					currentDragObj = ui.item;
					var id = currentDragObj.attr('id');
					var placeholderDom = ui.placeholder;
					g_ulDom = currentDragObj.closest('ul');
					g_nextLiDom = placeholderDom.next('li');
					if(g_nextLiDom.length==0){
						g_ul_append = true;
					}
				},
				update:function( event, ui ){//更新之后执行的事件
					var currentDragObj = ui.item;
					var sortObj = currentDragObj.next('li');
					if(g_ul_append===true){
						if(g_ulDom!=null){
							$(g_ulDom).append(sortObj);
							g_ul_append = false;
							g_ulDom = null;
						}
					}else{
						if(g_nextLiDom!=null){
							$(g_nextLiDom).before(sortObj);
							g_nextLiDom = null;
						}
					}
					
					if(onUpdate!=undefined && typeof(onUpdate)=="function"){
						onUpdate(target,event);
					}
				}
			});
		};
		//绑定添加按钮的事件
		addBt.bind('click',{target:target},addBtClickEvent);
	};
	
	var currentDragObj = null;
	var sortObj = null;
	var g_ulDom = null;
	var g_nextLiDom = null;
	var g_secevent = false;
	var g_ul_append = false;
	
	//添加模块的方法
	function add(target,dataitem){
		var opts = $.data(target,'desktop').options;
		($.data(target,'desktop').data).push(dataitem);
		var layout = opts.layout;
		var id = dataitem['id'];
		var domId = opts.idIndex + id;
		var icon = dataitem['icon'];
		var title = dataitem['title'];
		var closeable = dataitem['closeable'];//是否可以删除
		
		
		if("horizontal"==layout){
			var container = $.data(target,'desktop').container;
			var addBt = $.data(target,'desktop').addBt;
			var itemHtml = "" +
					"<li id='"+domId+"' class='desktop-item-li' jqpoint='sortitem' code='"+id+"' title='"+title+"'>" +
					"	<div class='desktop-item' align='center'>" +
					"		<div class='plat-module-"+icon+"'></div>" +
					"		<div class='desktop-item-txt'>"+title+"</div>" +
					"	</div>" +
					"</li>" +
					"";
			var item = $(addBt).before(itemHtml).prev();
			
			item.bind('mouseup',{target:target,item:dataitem},itemMouseupEvent);
			item.bind('mousedown',{target:target,item:dataitem},itemMousedownEvent);
			item.bind('click',{target:target,item:dataitem},itemClickEvent);
			
			
			//是否带有删除按钮
			if(closeable==null || closeable==undefined || closeable==true){
				var delBt = $('<div class="itemClose"></div>').appendTo(item);
				//添加事件
				delBt.bind('click',{target:target,item:dataitem},modelDeleteEvent);//给删除按钮添加点击事件
			}
			
		}else if("vertical"==layout){//纵向布局添加方法
			renderData(target);
		};
	};
	
	
	
	//添加按钮点击时间实现
	function addBtClickEvent(e){
		var target = e.data.target;
		var opts = $.data(target,'desktop').options;
		var addBtClickCallBack = opts.addBtClickCallBack;
		if(addBtClickCallBack!=undefined && typeof(addBtClickCallBack)=="function"){
			addBtClickCallBack(target);
		};
	};
	
	
	
	//得到排序之后的编号，以逗好分割
	function getSortIds(target){
		var opts = $.data(target,'desktop').options;
		var sortSplit = opts.sortSplit;
		var ids = [];
		$(target).find("[jqpoint='sortitem']").each(function(){
			ids.push($(this).attr('code'));
		});
		return ids.join(sortSplit);
	};
	
	
	//item点击事件
	function itemClickEvent(e){
		var target = e.data.target;
		var item = e.data.item;
		var opts = $.data(target,'desktop').options;
		var onClick = opts.onClick;
		if(onClick!=undefined && typeof(onClick)=="function"){
			if(isMouseDown==true){
				return false;
			}
			onClick(target,item);
		};
	};
	
	var isMouseDown = false;
	var oldX = -1;
	var oldY = -1;
	function itemMouseupEvent(e){
		if (!( oldX==e.pageX && oldY==e.pageY)) {
            isMouseDown=true;
		}
	};
	
	function itemMousedownEvent(e){
		oldX = e.pageX;
        oldY = e.pageY;
        isMouseDown = false;
	};
	
	
	
	
	//模块的删除按钮的点击事件
	function modelDeleteEvent(e){
		var target =  e.data.target;
		var item = e.data.item;
		var opts = $.data(target,'desktop').options;
		var deleteAction = opts.deleteAction;
		//执行回调函数
		if(deleteAction!=null && typeof(deleteAction)=="function"){
			deleteAction(target,item);
		}
		return false;
	};
	
	//删除模块的方法
	function deleteMode(target,model){
		var mId = model.id;
		deleteModeById(target,mId);
	};
	//根据model的编号去删除
	function deleteModeById(target,modelId){
		var mIdx = getModelIndexById(target,modelId);
		deleteModeByIndex(target,mIdx);
	};
	//根据模块的索引去删除
	function deleteModeByIndex(target,idx){
		var opts = $.data(target,'desktop').options;
		var layout = opts.layout;
		//得到所有的数组
		var models = $.data(target,'desktop').data;
		//得到dom的id
		var domId = opts.idIndex + models[idx]['id'];
		//删除缓存的中数组
		$.data(target,'desktop').data = models.del(idx);
		
		if("horizontal"==layout){
			//删除dom
			$("#"+domId).empty();
			$("#"+domId).remove();
		}else if("vertical"==layout){//纵向布局添加方法
			renderData(target);
		}
		
	};
	//根据模块编号查询模块的索引
	function getModelIndexById(target,modelId){
		var models = $.data(target,'desktop').data;
		var idx = -1;
		for(var i=0,len=models.length;i<len;i++){
			if(modelId==models[i]['id']){
				idx = i;
				break;
			};
		}
		return idx;
	};
	
	//插件类
	$.fn.desktop = function(options,arg1,arg2){
		if (typeof options == 'string'){
			switch(options){
				case 'options':
					return $.data(this[0], 'desktop').options;
				case 'setData'://设置数据的方法
					return this.each(function(){
						setData(this,arg1,arg2);
					});
				case 'add'://添加收藏夹模块的方法
					return this.each(function(){
						add(this,arg1);
					});
				case 'getSortIds'://得到排序之后的编号
					return getSortIds(this[0]);
				case 'delete'://通过模型对象删除
					return this.each(function(){
						deleteMode(this,arg1);
					});
				case 'deleteById'://通过模型对象的id删除
					return this.each(function(){
						deleteModeById(this,arg1);
					});
				case 'deleteByIndex'://通过模型对象的在数组中的索引进行删除
					return this.each(function(){
						deleteModeByIndex(this,arg1);
					});
			}
		};
			
		options = options || {};

		return this.each(function(){
			var state = $.data(this, 'desktop');
			var opts;
			if (state){
				opts = $.extend(state.options, options);
			}else{
				opts = $.extend({}, $.fn.desktop.defaults, options);		
				state = $.data(this, 'desktop', {
					options: opts
				});
			}
			
			//初始化taskPanel内容
			init(this);
		});
	};
	

	//默认构造函数制定
	$.fn.desktop.defaults = {
		idIndex:'desktop_',
		layout:'horizontal',//horizontal or vertical
		data:[],
		sortSplit:',',
		onClick:undefined,
		onUpdate:undefined,
		deleteAction:undefined,
		addBtClickCallBack:undefined
	};

})(jQuery);
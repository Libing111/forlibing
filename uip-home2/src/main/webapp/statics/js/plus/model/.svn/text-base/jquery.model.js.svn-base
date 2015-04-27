/**
 * 描述：二、三级模块插件
 * 作者：温泉
 * */
/**
 * 一级菜单格式json
 * modelData:[{
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
	//初始化函数
	function init(target){
		var opts = $.data(target,'model').options;
		//设置模块数据
		var modelData = opts.modelData;
		opts.firstInit=true; //第一次加载数据
		//渲染菜单
		setData(target,modelData);//渲染菜单
		
	};
	
	//给模块设置数据的方法
	function setData(target,models){
		var opts = $.data(target,'model').options;
		if(models!=null && models.length > 0){
			//清空上次的查询对应的结果
			for(var i=0;i<models.length;i++){
				models[i].children=null;
			}
			$.data(target,'model').modelData = models;
			//渲染菜单
			renderModel(target);
		}else{
			var emptyMsg="";
			if(opts.firstInit){
				emptyMsg=opts.emptyMsg;
			}else{
				emptyMsg=opts.queryEmptyMsg;
			}
			$(target).html('<div class="plat-waterfall-empty-container"><div class="ico_empty">'+emptyMsg+'</div></div>');
		}
		opts.firstInit=false;//更新标志变量，表明已经加载过了，以后再调用setData（）方法为则为更新方法。
	};
	
	//渲染模块数据
	function renderModel(target){
		var opts = $.data(target,'model').options;
		var idIndex = opts.idIndex;
		//清空菜单的所有内容
		$(target).empty();
		//得到菜单的数据
		var modelData = $.data(target,'model').modelData;
		if(opts.simpleData==true){
			modelData = transformTozTreeFormat({
		 		idKey:'id',
		 		pIdKey:'pid',
		  		childrenKey:'children'
		  	},modelData);
			
		}
		
		//将ul标签放入进去
		var ulDom = $.data(target,'model').ulDom = $('<ul id="modelContainer" class="plat-waterfall-layout">').appendTo(target);
		
		if(modelData!=null && modelData.length > 0){
			for(var i=0,len=modelData.length;i<len;i++){
				var mId = idIndex + modelData[i]['id'];
				var mUrl = modelData[i]['url'];
				var mIcon = modelData[i]['icon']; 
				var mTitle = modelData[i]['title'];
				var mchildren = modelData[i]['children'];
				
				var liDomStr = '<li id="'+mId+'" jqpoint="modelItem" url="'+mUrl+'" class="plat-model-waterfall-item"></li>';
				var liDom = $(liDomStr).appendTo(ulDom);
				var modelDomStr = ''+
				'	<div class="plat-model-panel" align="center">'+
				'		<table cellpadding="0" cellspacing="0">'+
				'			<tr>'+
				'				<td align="right"><div class="plat-module-'+mIcon+'"></div></td>'+
				'				<td align="left"><div class="plat-model-txt" title="'+mTitle+'">'+mTitle+'</div></td>'+
				'			</tr>'+
				'		</table>'+
				'	</div>';
				
				$(modelDomStr).appendTo(liDom);
				//看看是否有孩子节点
				if(mchildren!=null && mchildren.length >= 1 ){
					var subModelDomWrap = $('<div class="plat-model-subpanel"></div>').appendTo(liDom);
					for(var j=0,jlen=mchildren.length;j<jlen;j++){
						var msId = idIndex + mchildren[j]['id'];
						var msIcon = mchildren[j]['icon']; 
						var msUrl = mchildren[j]['url'];
						var msTitle = mchildren[j]['title'];
						var subModelDomStr = ''+
							'<div class="plat-model-sub-item" url="'+msUrl+'" id="'+msId+'" jqpoint="submodelItem">'+
							'	<table cellpadding="0" cellspacing="0">'+
							'		<tr>'+
							'			<td><div class="plat-module-'+msIcon+'-small"></div></td>'+
							'			<td><div class="plat-model-sub-txt" title="'+msTitle+'">'+msTitle+'</div></td>'+
							'		</tr>'+
							'	</table>'+
							'</div>';
						var subModelDom = $(subModelDomStr).appendTo(subModelDomWrap);
						$(subModelDom).bind('click',{target:target,modelItem:mchildren[j]},subModelItemClickEvent);
						
						//鼠标划过事件
						subModelDom.hover(
							function () {
								$(this).addClass("hover");
						  	},
						  	function () {
								$(this).removeClass("hover");
						  	}
						);
					}
				}else{
					$(liDom).bind('click',{target:target,modelItem:modelData[i]},modelItemClickEvent);
					//鼠标划过事件
					liDom.hover(
						function () {
							$(this).addClass("hover");
					  	},
					  	function () {
							$(this).removeClass("hover");
					  	}
					);
				}
			}
			
			//初始化瀑布插件
			$('#modelContainer li').wookmark({
		        autoResize: true, // This will auto-update the layout when the browser window is resized.
		        container: $('#modelwrap'), // Optional, used for some extra CSS styling
		        offset: 5, // Optional, the distance between grid items
		        outerOffset: 10, // Optional, the distance to the containers border
		        itemWidth: 230 // Optional, the width of a grid item
		    });
		}
	};
	
	function subModelItemClickEvent(e){
		var target = e.data.target;
		var opts = $.data(target,'model').options;
		var modelCallBack = opts.modelCallBack;
		var modelItem = e.data.modelItem;
		//添加选中样式
		$(target).find('li[jqpoint="modelItem"]').removeClass("selected");
		$(target).find('div[jqpoint="submodelItem"]').removeClass("selected");
		$(this).addClass('selected');
		//执行回调函数
		if(modelCallBack!=null && typeof(modelCallBack)=="function"){
			modelCallBack(modelItem);
		}
	};
	
	//模块点击事件
	function modelItemClickEvent(e){
		var target = e.data.target;
		var opts = $.data(target,'model').options;
		var modelCallBack = opts.modelCallBack;
		var modelItem = e.data.modelItem;
		//添加选中样式
		$(target).find('div[jqpoint="submodelItem"]').removeClass("selected");
		$(target).find('li[jqpoint="modelItem"]').removeClass("selected");
		$(this).addClass('selected');
		//执行回调函数
		if(modelCallBack!=null && typeof(modelCallBack)=="function"){
			modelCallBack(modelItem);
		}
	};
	
	
	
	
	//插件类
	$.fn.model = function(options,arg1,arg2){
		if (typeof options == 'string'){
			switch(options){
				case 'options':
					return $.data(this[0], 'model').options;
				case 'setData'://添加portlet内容
					return this.each(function(){
						setData(this,arg1,arg2);
					});
			}
		};
			
		options = options || {};

		return this.each(function(){
			var state = $.data(this, 'model');
			var opts;
			if (state){
				opts = $.extend(state.options, options);
			}else{
				opts = $.extend({}, $.fn.model.defaults, options);		
				state = $.data(this, 'model', {
					options: opts
				});
			}
			
			//初始化taskPanel内容
			init(this);
		});
	};
	

	//默认构造函数制定
	$.fn.model.defaults = {
		idIndex:'model_',
		simpleData:true,
		modelCallBack:function(modelItem){},
		modelData:[],
		emptyMsg:"载入模块数据为空",
		queryEmptyMsg:"查询结果为空",
	};

})(jQuery);
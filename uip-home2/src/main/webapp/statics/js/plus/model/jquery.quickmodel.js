/**
 * 描述：快捷模块插件
 * 作者：温泉
 * */
/**
 * 快捷格式json
 * modelData:[{
 * 	id:'', //菜单编号，菜单构造是的唯一标识
 *  icon:'',
 * 	title:'',//菜单标题
 *  closeable:true,//如果添加这个属性，并且给这个属性设置为true的话，按钮就带有删除的功能;如果没有这个属性，则没有删除功能。
 * 	url:''//菜单执行的URL
 * }]
 * */
(function($){
	
	function init(target){
		var opts = $.data(target,'QuickModel').options;
		//设置模块数据
		var modelData = opts.modelData;
		//设置
		setData(target,modelData);//渲染菜单
	};
	
	//上一个的点击事件
	function preBtEvent(e){
		var target = e.data.target;
		preAction(target);
	};
	//向前移动一个单元
	function preAction(target){
		var opts = $.data(target,'QuickModel').options;
		var itemWidth = opts.itemWidth;
		var container = $.data(target,'QuickModel').container;
		var oldLeft = parseInt(container.css("left").replace("px",""));
		var newLeft = oldLeft + itemWidth;
		if(oldLeft == 0){
			newLeft = 0;
		}
		container.animate({left:newLeft + 'px'},{queue:false,duration:100});
	};
	
	//下一个的点击事件
	function nextBtEvent(e){
		var target = e.data.target;
		nextAction(target);
	};
	
	//向前移动一个单元
	function nextAction(target){
		var opts = $.data(target,'QuickModel').options;
		var itemWidth = opts.itemWidth;
		var container = $.data(target,'QuickModel').container;
		var wrap = $.data(target,'QuickModel').wrap;
		var oldLeft = parseInt(container.css("left").replace("px",""));
		var newLeft = oldLeft - itemWidth;
		var offsetRangeLeft = container.width() - wrap.width();
		if(offsetRangeLeft>=0){
			if((-newLeft) >= offsetRangeLeft){
				newLeft = (-offsetRangeLeft);
			}
			container.animate({left:newLeft + 'px'},{queue:true,duration:100});
		}
	};
	
	
	//给模块设置数据的方法
	function setData(target,models){
		$.data(target,'QuickModel').modelData = models;
		$.data(target,'QuickModel').models = [];
		//渲染菜单
		render(target);
	};
	//渲染模块的内容
	function render(target){
		$(target).empty();
		var wrap = $.data(target,'QuickModel').wrap = $('<div class="plat-quick-wrap"></div>').appendTo(target);
		var preBt = $.data(target,'QuickModel').preBt = $('<div class="plat-quick-bt plat-quick-preBt" style="top: 0px;left: 0px;"></div>').appendTo(wrap);
		var nextBt = $.data(target,'QuickModel').nextBt = $('<div class="plat-quick-bt plat-quick-nextBt" style="top: 0px;right: 0px;"></div>').appendTo(wrap);
		var container = $.data(target,'QuickModel').container = $('<div class="plat-quick-container" style="left:0px"></div>').appendTo(wrap);
		$.data(target,'QuickModel').ul = $('<ul class="plat-quick-ul"></ul>').appendTo(container);
		
		
		//绑定按钮事件
		preBt.bind('click',{target:target},preBtEvent);
		nextBt.bind('click',{target:target},nextBtEvent);
		
		//添加鼠标移动移除事件
		nextBt.hover(
			function () {
				$(this).addClass("plat-quick-nextBt-hover");
				$(this).removeClass("plat-quick-nextBt");
		  	},
		  	function () {
				$(this).addClass("plat-quick-nextBt");
				$(this).removeClass("plat-quick-nextBt-hover");
		  	}
		);
		//添加鼠标移动移除事件
		preBt.hover(
			function () {
				$(this).addClass("plat-quick-preBt-hover");
				$(this).removeClass("plat-quick-preBt");
		  	},
		  	function () {
				$(this).addClass("plat-quick-preBt");
				$(this).removeClass("plat-quick-preBt-hover");
		  	}
		);
		
		var ul = $.data(target,'QuickModel').ul;
		var models = $.data(target,'QuickModel').modelData;
		//清空菜单的所有内容
		$(ul).empty();
		
		if(models!=null && models.length > 0){
			var modelsNum = models.length;
			//将数组进行拆分
			for(var i=0;i<modelsNum;i++){
				addModel(target,models[i],true);
			}
		}
		
		//resize
		resize(target);
		//渲染添加按钮
		addNewModel(target);
	};
	/*
	 <li>
			<div class="plat-quick-item" align="center">
				<div class="plat-module-demo"></div>
				<div class="plat-quick-item-txt">adsad</div>
			</div>
		</li> 
	  
	 * */
	//添加按钮的方法
	function addNewModel(target){
		var ulDom = $.data(target,'QuickModel').ul;
		var addBtStr = ''+
						' <li jqpoint="newModelItem" class="plat-quick-newModelItem">'+
						'	<div class="plat-quick-item" align="center">'+
						'		<div class="plat-module-add"></div>'+
						'		<div class="plat-quick-item-txt">添加常用功能</div>'+
						'	</div>'+
						'</li> ';
		var addBt = $.data(target,'QuickModel').addBt = $(addBtStr).appendTo(ulDom);
		addBt.bind('click',{target:target},addBtEvent);
	};
	
	//添加按钮的点击事件
	function addBtEvent(e){
		var target =  e.data.target;
		var opts = $.data(target,'QuickModel').options;
		var addCallBack =  opts.addCallBack;
		//执行回调函数
		if(addCallBack!=null && typeof(addCallBack)=="function"){
			addCallBack(target);
		}
	};
	
	//添加模块的方法
	function addModel(target,model,flag){
		var opts = $.data(target,'QuickModel').options;
		($.data(target,'QuickModel').models).push(model);
		var ulDom = $.data(target,'QuickModel').ul;
		var idIndex = opts.idIndex;
		var modelId = model['id'];
		var modelExtId = idIndex + modelId;
		var modelIcon = model['icon'];
		var modelTitle = model['title'];
		var modelUrl = model['url'];
		var closeable = model['closeable'];//是否可以删除
		var itemStr = ''+
						' <li id="'+modelExtId+'" jqpoint="modelItem" url="'+modelUrl+'">'+
						'	<div class="plat-quick-item" align="center">'+
						'		<div class="plat-module-'+modelIcon+'"></div>'+
						'		<div class="plat-quick-item-txt">'+modelTitle+'</div>'+
						'	</div>'+
						'</li> ';
		
		var itemDom = undefined;
		if(flag===true){
			itemDom = $(itemStr).appendTo(ulDom);
		}else{
			var newModelBt = $(($(target).find('li[jqpoint="newModelItem"]')[0]));
			itemDom =  $(itemStr).insertBefore(newModelBt);
		}
		itemDom.bind('click',{target:target,model:model},modelClickEvent);
		
		//是否带有删除按钮
		if(closeable && closeable===true){
			var delBt = $('<div class="itemClose"></div>').appendTo(itemDom);
			//添加事件
			delBt.bind('click',{target:target,model:model},modelDeleteEvent);//给删除按钮添加点击事件
		}
		
		//添加鼠标移动移除事件
		itemDom.hover(
			function () {
				$(this).addClass("hovered");
		  	},
		  	function () {
				$(this).removeClass("hovered");
		  	}
		);
		
		//重新计算页面内容
		resize(target,function(){
			if(flag===true){
				//什么都不做
			}else{
				nextAction(target);
			}
		});
	};
	//模块的删除按钮的点击事件
	function modelDeleteEvent(e){
		var target =  e.data.target;
		var model = e.data.model;
		var opts = $.data(target,'QuickModel').options;
		var deleteAction = opts.deleteAction;
		//执行回调函数
		if(deleteAction!=null && typeof(deleteAction)=="function"){
			deleteAction(model);
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
		var opts = $.data(target,'QuickModel').options;
		//得到所有的数组
		var models = $.data(target,'QuickModel').models;
		//得到dom的id
		var domId = opts.idIndex + models[idx]['id'];
		//删除缓存的中数组
		$.data(target,'QuickModel').models = models.del(idx);
		//删除dom
		$("#"+domId).empty();
		$("#"+domId).remove();
		//重新计算页面内容
		resize(target,function(){
			preAction(target);
		});
	};
	//根据模块编号查询模块的索引
	function getModelIndexById(target,modelId){
		var models = $.data(target,'QuickModel').models;
		var idx = -1;
		for(var i=0,len=models.length;i<len;i++){
			if(modelId==models[i]['id']){
				idx = i;
				break;
			};
		}
		return idx;
	};
	
	
	
	//模块的点击事件
	function modelClickEvent(e){
		var target =  e.data.target;
		var model = e.data.model;
		var opts = $.data(target,'QuickModel').options;
		var modelCallBack = opts.modelCallBack;
		//添加选中样式
		$(target).find('li[jqpoint="modelItem"]').removeClass("selected");
		$(this).addClass('selected');
		//执行回调函数
		if(modelCallBack!=null && typeof(modelCallBack)=="function"){
			modelCallBack(model);
		}
	};
	
	//设置大小的方法
	function resize(target,callback){
		//得到模块数据
		var models = $.data(target,'QuickModel').models;
		var model_nums = models.length;
		
		var opts = $.data(target,'QuickModel').options;
		var preBt = $.data(target,'QuickModel').preBt ; 
		var nextBt = $.data(target,'QuickModel').nextBt ;
		var pageNums = opts.pageNums;
		var itemWidth = opts.itemWidth;
		var itemHeight = opts.itemHeight;
		var wrap_w = pageNums * itemWidth;
		var wrap = $.data(target,'QuickModel').wrap;
		
		wrap.width(wrap_w);
		wrap.height(itemHeight);
		
		
		
		//此时都需要将两个左右移动的按钮显示出来
		if(model_nums >= pageNums){
			preBt.css('display',"block");
			nextBt.css('display',"block");
		}else{
			preBt.css('display',"none");
			nextBt.css('display',"none");
		}
		var container = $.data(target,'QuickModel').container;
		var container_w = (model_nums + 1) * itemWidth;
		container.width(container_w);
		container.height(itemHeight);
		//执行回调函数
		if(callback!=undefined && typeof(callback)=="function"){
			callback();
		}
	};
	
	
	//插件类
	$.fn.QuickModel = function(options,arg1,arg2){
		if (typeof options == 'string'){
			switch(options){
				case 'options':
					return $.data(this[0], 'QuickModel').options;
				case 'setData':
					return this.each(function(){
						setData(this,arg1,arg2);
					});
				case 'addModel':
					return this.each(function(){
						addModel(this,arg1,arg2);
					});
				case 'deleteMode'://通过模型对象删除
					return this.each(function(){
						deleteMode(this,arg1);
					});
				case 'deleteModeById'://通过模型对象的id删除
					return this.each(function(){
						deleteModeById(this,arg1);
					});
				case 'deleteModeByIndex'://通过模型对象的在数组中的索引进行删除
					return this.each(function(){
						deleteModeByIndex(this,arg1);
					});
			}
		};
			
		options = options || {};

		return this.each(function(){
			var state = $.data(this, 'QuickModel');
			var opts;
			if (state){
				opts = $.extend(state.options, options);
			}else{
				opts = $.extend({}, $.fn.QuickModel.defaults, options);		
				state = $.data(this, 'QuickModel', {
					options: opts
				});
			}
			init(this);
		});
	};
	

	//默认构造函数制定
	$.fn.QuickModel.defaults = {
		idIndex:'QuickModel_',
		modelCallBack:undefined,
		addCallBack:undefined,
		deleteAction:undefined,
		modelData:[],
		pageNums:7,
		itemWidth:139,
		itemHeight:100
	};

})(jQuery);
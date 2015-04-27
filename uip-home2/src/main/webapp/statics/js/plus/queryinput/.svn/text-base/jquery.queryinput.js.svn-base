/**
 * 描述：查询输入框插件
 * 作者：温泉
 * */
(function($){
	//初始化函数
	function init(target){
		var opts = $.data(target,'queryInput').options;
		$(target).empty();//清空内容
		//渲染函数
		rander(target);
	};
	
	//渲染函数
	function rander(target){
		var opts = $.data(target,'queryInput').options;
		var skin = opts.skin;//得到配置的皮肤
		
		$(target).addClass('plat-jq-queryInput');//添加样式
		$(target).addClass(skin);//配置皮肤样式
		
		var wrapHtml = '<table class="plat-jq-queryInput-layout" cellpadding="0" cellspacing="0" border="0">' +
					   ' 	<tr>' +
						'		<td jqpoint="leftWrap" class="left">&nbsp;</td>' +
						'		<td class="center">' +
						'			<table cellpadding="0" cellspacing="0" border="0">' +
						'				<tr>' +
						'					<td jqpoint="queryInputTdWrap">' +
						'						<div class="queryInputWrap">' +
						'							<input class="queryInput" type="text" jqpoint="input" watermark="'+opts.watermark+'"  />' +
						'							<div class="cancelBt"></div>' +
						'						</div>' +
						'					</td>' +
						'					<td jqpoint="queryBtTdWrap"><div class="queryBt"></div></td>' +
						'				</tr>' +
						'			</table>' +
						'		</td>' +
						'		<td jqpoint="rightWrap" class="right">&nbsp;</td>' +
						'	</tr>' +
						'</table>';
		
		var wrapDom = $(wrapHtml).appendTo(target);		
		var queryInputTdWrap = $.data(target,'queryInput').queryInputTdWrap = $(target).find('td[jqpoint="queryInputTdWrap"]');
		var queryBtTdWrap = $.data(target,'queryInput').queryBtTdWrap = $(target).find('td[jqpoint="queryBtTdWrap"]');
		var queryInputWrap = $.data(target,'queryInput').queryInputWrap = $(target).find('div.queryInputWrap');
		var queryInput = $.data(target,'queryInput').queryInput = $(target).find('input.queryInput');
		var cancelBt = $.data(target,'queryInput').cancelBt = $(target).find('div.cancelBt');
		var queryBt = $.data(target,'queryInput').queryBt = $(target).find('div.queryBt');
		if (queryInput.attr("watermark") != null) {
			queryInput.watermark('watermark',queryInput.attr("watermark"));
		}
		//绑定事件
		$(queryInput).bind('keyup',{target:target},queryInputKeyupEvent);
		$(cancelBt).bind('click',{target:target},cancelBtEvent);
		$(queryBt).bind('click',{target:target},queryBtEvent);
		if(opts.autoQuery){
			$(queryInput).bind('input propertychange',{target:target},queryInputChangedEvent);
		}
		//设置宽度
		setWidth(target,opts.width);
	};
	//输入框内容改变事件
	function queryInputChangedEvent(e){
		var target = e.data.target;
		enterAction(target);
	}
	//输入框键盘抬起事件
	function queryInputKeyupEvent(e){
		var target = e.data.target;
		var cancelBt = $.data(target,'queryInput').cancelBt;
		var queryInput = $.data(target,'queryInput').queryInput;
		
		var queryInputWidth = $.data(target,'queryInput').queryInputWidth;
		var queryInputNewWidth = $.data(target,'queryInput').queryInputNewWidth;
		
		if(e.keyCode==13){
			enterAction(target);
		}
		var v = $(this).val();
		if(v!=null && v.length > 0){
			$(queryInput).width(queryInputNewWidth);
			$(cancelBt).show();
		}else{
			$(queryInput).width(queryInputWidth);
			$(cancelBt).hide();
		}
	};
	
	//取消按钮点击事件
	function cancelBtEvent(e){
		var target = e.data.target;
		var opts = $.data(target,'queryInput').options;
		var queryInputWidth = $.data(target,'queryInput').queryInputWidth;
		var cancelCallBack = opts.cancelCallBack;
		var queryInput = $.data(target,'queryInput').queryInput;
		var cancelBt = $.data(target,'queryInput').cancelBt;
		$(queryInput).val('');//将输入框清空
		$(queryInput).width(queryInputWidth);
		$(cancelBt).hide();
		//执行回调函数
		if(cancelCallBack!=null && typeof(cancelCallBack)=="function"){
			cancelCallBack(queryInput);
		}
	};
	//搜索按钮点击事件
	function queryBtEvent(e){
		var target = e.data.target;
		enterAction(target);
	};
	
	//执行确定的动作
	function enterAction(target){
		var opts = $.data(target,'queryInput').options;
		var enterCallBack = opts.enterCallBack;
		var queryInput = $.data(target,'queryInput').queryInput;
		//执行回调函数
		if(enterCallBack!=null && typeof(enterCallBack)=="function"){
			enterCallBack(queryInput);
		}
	};
	
	
	//设置值的方法
	function setValue(target,val){
		var opts = $.data(target,'queryInput').options;
		var queryInput = $.data(target,'queryInput').queryInput;
		var queryInputNewWidth = $.data(target,'queryInput').queryInputNewWidth;
		var cancelBt = $.data(target,'queryInput').cancelBt;
		//设置内容
		$(queryInput).val(val);
		if(val!=null && val.length > 0){
			$(queryInput).width(queryInputNewWidth);
			$(cancelBt).show();
			$(queryInput).removeClass("watermark");
		}else{
			if(!$(queryInput).hasClass("watermark")){
				$(queryInput).addClass("watermark");
			}
			
		}
		if(opts.autoQuery){
			enterAction(target);
		}
	};
	//得到值的方法
	function getValue(target){
		var opts = $.data(target,'queryInput').options;
		var queryInput = $.data(target,'queryInput').queryInput;
		if(queryInput.hasClass("watermark")){
			return "";
		}else{
			return $.trim($(queryInput).val());
		}
	};
	
	//设置宽度的方法
	function setWidth(target,width){
		//给组件设置宽度
		$(target).width(width);
		var leftWrap = $(target).find('td[jqpoint="leftWrap"]');
		var rightWrap = $(target).find('td[jqpoint="rightWrap"]');
		var leftWrap_w = $(leftWrap).width();
		var rightWrap_w = $(rightWrap).width();
		//得到中间wrap的宽度
		var centerWrap = width - leftWrap_w - leftWrap_w;
		
		var queryBt = $.data(target,'queryInput').queryBt;
		var queryInputTdWrap = $.data(target,'queryInput').queryInputTdWrap;
		var queryInputWrap = $.data(target,'queryInput').queryInputWrap;
		var queryInput = $.data(target,'queryInput').queryInput;
		var queryBtTdWrap = $.data(target,'queryInput').queryBtTdWrap;
		
		var queryBt_w = $(queryBt).width();
		var queryBt_h = $(queryBt).height();
		var queryBtTdWrap_w = queryBt_w;
		$(queryBtTdWrap).width(queryBtTdWrap_w);
		//设置输入域tdwrap的宽度
		var queryInputTdWrap_w = centerWrap - queryBtTdWrap_w;
		$(queryInputTdWrap).width(queryInputTdWrap_w);
		$(queryInputWrap).width(queryInputTdWrap_w);
		$(queryInputWrap).height(queryBt_h);
		
		var queryInput_h = $(queryInput).height();
		var queryInput_margin_top = (queryBt_h - queryInput_h )/2;
		
		//输入框的原始宽度
		$.data(target,'queryInput').queryInputWidth = queryInputTdWrap_w;
		$.data(target,'queryInput').queryInputNewWidth = queryInputTdWrap_w - queryBt_w;
		
		$(queryInput).width(queryInputTdWrap_w);
		$(queryInput).css('margin-top',queryInput_margin_top + "px");
	};
	
	
	
	
	//插件类
	$.fn.queryInput = function(options,arg1,arg2){
		if (typeof options == 'string'){
			switch(options){
				case 'options':
					return $.data(this[0], 'queryInput').options;
				case 'setWidth'://设置宽度的方法
					return this.each(function(){
						setWidth(this,arg1);
					});
				case 'setValue'://设置内容的方法
					return this.each(function(){
						setValue(this,arg1);
					});
				case 'getValue'://获取内容的方法
					return getValue(this[0]);
			}
		};
			
		options = options || {};
		
		
		return this.each(function(){
			var state = $.data(this, 'queryInput');
			var opts;
			if (state){
				opts = $.extend(state.options, options);
			}else{
				opts = $.extend({}, $.fn.queryInput.defaults, options);		
				state = $.data(this, 'queryInput', {
					options: opts
				});
			}
			
			//初始化taskPanel内容
			init(this);
		});
	};
	

	//默认构造函数制定
	$.fn.queryInput.defaults = {
		idIndex:'queryInput_',
		skin:'plat-jq-queryInput-skin1',
		width:200,//宽度
		enterCallBack:function(modelItem){},
		cancelCallBack:function(modelItem){},
		autoQuery:false,/*autoQuery=true:检测查询框的内容变化，自动进行查询*/
		watermark:""/*文本框为空时，默认显示的文字*/
	};

})(jQuery);
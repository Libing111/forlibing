//首页入口函数
$(document).ready(function(){
	var quickMenus = [];
	$.ajax({
		type: "POST",
		url: "home/quickMenu/getList",
		dataType:'json',
		cache: false,
		async:false,
		success: function(data){
			quickMenus = data;
		}
	});
	
	//加载快捷插件
	$('#quickPanel').QuickModel({
		modelData:quickMenus,
		addCallBack:function(t){//添加按钮的点击事件
			 var diag = new top.Dialog();

			 diag.Title = "添加常用功能";

			 diag.URL = "home/quickMenu/add/index";

			 diag.OKEvent = function(){
				 
			    var resourceId = diag.innerFrame.contentWindow.document.getElementById('resourceId').getAttribute("relValue");
			    $.ajax({
					type: "POST",
					url: "home/quickMenu/add",
					dataType:'json',
					data:{resourceId:resourceId},
					cache: false,
					async:false,
					success: function(data){
						var quickMenu = data;
						$('#quickPanel').QuickModel('addModel', quickMenu);
						diag.close();
					}
				});
			 };

			 diag.show();
		},
		modelCallBack:function(item){//模块的点击事件
			customAddTaskPanel(item);
		},
		deleteAction:function(model){//删除按钮的点击事件动作方法，可以在这里操作删除的功能
			//在这里可以进行判断是否允许删除，或者添加提示信息
			top.Dialog.confirm("确定要删除这个快捷菜单吗？",function(){
				//可以先调用ajax的方法异步删除数据的数据，数据删除成功之后再调用删除模块的方法
				//例如下面的代码 ，异步加载html片段
//				$.ajax({
//					type: "POST",
//					url: "home/quickMenu/delete",
//					dataType:'json',
//					data:{id:model.id},
//					cache: false,
//					success: function(data){
//						$('#quickPanel').QuickModel('deleteMode',model);//调用删除的方法删除
//					}
//				});
				$('#quickPanel').QuickModel('deleteMode',model);//调用删除的方法删除
			});
		}
	});
	

	
	//加载消息列表
	var homeMessageExtensionList = [];
	$.ajax({
		type: "POST",
		url: "home/messageModuleList",
		dataType:'json',
		cache: false,
		async:false,
		success: function(data){
			homeMessageExtensionList = data;
		}
	});
	
	var $li = undefined;
	var divId = undefined;
	var resourceCode = undefined;
	for(var index=0; index < homeMessageExtensionList.length; index++){
		resourceCode = homeMessageExtensionList[index].resourceCode;
		divId = "plat-page-content_" + resourceCode;
		$li = $("<li class='plat-home-waterfall-item'>" + 
				    "	<div class='plat-page-content' clk='1' id='" + divId + "'>" +
				    "	<p class='title'>" + homeMessageExtensionList[index].title + "</p>" +
				    "	</div>" +
				    "</li>");
		$('#homeContainer').append($li);
		
		$.ajax({
			type: "POST",
			url: "home/messageList",
			dataType:'json',
			data:{resourceCode:resourceCode},
			cache: false,
			async:false,
			success: function(messageList){
				var pId = undefined;
				var messageUrl = undefined;
				for(var index2=0; index2 < messageList.length; index2++){
					pId = resourceCode + messageList[index2].id;
					messageUrl = messageList[index2].url+"?"+ messageList[index2].id;
					$('#' + divId).append("<p id=" + pId + ">" + messageList[index2].msgText + "</p>");
					messageList[index2].url = messageUrl;
					$('#' + pId).bind("click",{msg:messageList[index2]}, customMessageClickEvent);
				}
			}
		});
	}
	
	
	//初始化瀑布插件
	$('#homeContainer li').wookmark({
        autoResize: true, // This will auto-update the layout when the browser window is resized.
        container: $('#homewrap'), // Optional, used for some extra CSS styling
        offset: 5, // Optional, the distance between grid items
        outerOffset: 5, // Optional, the distance to the containers border
        itemWidth: 320 // Optional, the width of a grid item
    });
	
	
	
});


/**
 * 自定义的首页弹出框事件
 * @param target
 * @param id
 * @returns {Boolean}
 */
function customMessageClickEvent(e){
	customAddTaskPanel(e.data.msg);
};
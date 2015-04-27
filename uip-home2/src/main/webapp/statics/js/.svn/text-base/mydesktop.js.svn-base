(function(){
	$.ajax({
		type: "POST",
		url: "mydesktop/getMyDesktop",
		dataType:'json',
		cache: false,
		async:false,
		success: function(data){
			myDesktopMenus = data;
			renderContent(data);
		},
		error:function(){
			renderContent([]);
		}
	});
	function renderContent(data1){
		$("#desktop1").desktop({
			data:data1,
			layout:'horizontal',//设置纵向还是横向，此属性不设置，默认为横向 ；纵向为vertical，横向为horizontal
			addBtClickCallBack:function(target){//添加按钮的回调函数
				 var diag = new top.Dialog();

				 diag.ShowMaxButton=true;

				 diag.ShowMinButton=true;

				 diag.Title = "添加常用功能";

				 diag.URL = "mydesktop/add/index";

				 diag.OKEvent = function(){
				    var resourceIds =  diag.innerFrame.contentWindow.getResource();
				   // alert(resourceIds);
				    $.ajax({
						type: "POST",
						url: "mydesktop/add",
						dataType:'json',
						data:{resourceIds:resourceIds},
						cache: false,
						async:false,
						success: function(data){
							var quickMenu = data;
							for(var i = 0; i < quickMenu.length; i++){
								$(target).desktop('add', quickMenu[i]);
							}
							
							diag.close();
						}
					});
				 };

				 diag.show();
			},
			deleteAction:function(target,item){//删除按钮执行的回调函数
				$.ajax({
				type: "POST",
				url: "mydesktop/delete",
				dataType:'json',
				data:{id:item.id},
				cache: false,
				success: function(data){
						//删除收藏模块的方法
				$(target).desktop('delete',item);
				}
			});
			
			},
			onClick:function(target,item){//收藏夹模块的点击事件回调函数
				//父窗体的添加方法
				customAddTaskPanel(item);
				
			},
			onUpdate:function(target,event){//拖拽之后，位置一旦改变执行的事件
				//在这里可以对位置进行更新，执行ajax更新位置的函数
				var ids = $(target).desktop('getSortIds');
			//	alert(ids);
			}
		});
		hideContentLoading();
	}
})();







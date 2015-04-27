var iconData;
var $iconList;
var iconDiag;
var depth=500;
var taskBarShow=false;
var chatMainShow=false;
var currentScreenIdx=0;
var taskBarHeight=0;
$(function(){
	//初始化鱼眼菜单并处理菜单位置
	resizeHandler();
	window.onresize = function(){
		resizeHandler();
	};
	$('#dock').Fisheye(
		{
			maxWidth: 64,
			items: 'a',
			itemsText: 'span',
			container: '.dock-container',
			itemWidth: 80,
			proximity: 80,
			valign: 'bottom',
			halign : 'center'
		}
	);
	
	$("body").bind("dialogTaskShow",function(){
		taskBarShow=true;
		taskBarHeight=29;
		$('#mydock').css("bottom",taskBarHeight+"px");
		if(chatMainShow==true){
			$(".chat_main").css("bottom",taskBarHeight+"px");
		}
		else{
			$(".chat_main").css("bottom",-402+taskBarHeight+"px");
		}
	});
	$("body").bind("dialogTaskHide",function(){
		taskBarShow=false;
		taskBarHeight=0;
		$('#mydock').css("bottom","0px");
		if(chatMainShow==true){
			$(".chat_main").css("bottom","0");
		}
		else{
			$(".chat_main").css("bottom","-402px");
		}
		
	});
	
	
	
    var option = { width: 110, items: [
                        { text: "删除模块", icon: "/home/libs/icons/reload.gif", alias: "1-6", action: menuAction },
                        { text: "刷新桌面", icon: "/home/libs/icons/reload.gif", alias: "1-2", action: menuAction },
						{ type: "splitLine" },
						{ text: "背景设置", icon: "/home/libs/icons/img.gif", alias: "1-3", action: menuAction },
                       { text: "图标设置", icon: "/home/libs/images/icons/ico4.gif", alias: "1-4", type: "group", width: 100, items: [
                           { text: "大图标", icon: "/home/libs/images/icons/ico4-2.gif", alias: "1-4-1", action: menuAction },
                            { text: "小图标", icon: "/home/libs/images/icons/ico4-3.gif", alias: "1-4-2", action: menuAction }
                       ]
                       },
						{ type: "splitLine" },
						{ text: "锁定屏幕", icon: "/home/libs/icons/lock.gif", alias: "1-1", action: menuAction },
						{ text: "注销系统", icon: "/home/libs/icons/exit.gif", alias: "1-5", action: menuAction }
                        ], onShow: applyrule
        };
	$("body").contextmenu(option);

	         
	
	var myData={"list" : ["智能提示选项示例1","智能提示选项示例2","智能提示选项示例3","智能提示选项示例4"]};
	$('#searchInput').AutoComplete({
			data: myData,
			inputWidth:165,
			boxWidth:210,
			topDeff:12,
			onItemSelected: function(item){
				$("#searchPanel").hide();
				Dialog.alert("此处为查询结果处理。");
			}
		});
});
function resizeHandler(){
	var windowWidth=document.documentElement.clientWidth;
	$("#mydock").css({
		"left":(windowWidth-700)/2
	});
	$("#iconnav").css({
		"left":(windowWidth-$("#iconnav").width())/2
	});
	$("#searchPanel").css({
		"left":(windowWidth-$("#searchPanel").width())/2
	});
}
 function applyrule(menu) {               
	    menu.applyrule({ name: "all",
	            disable: true,
	            items: []
	        });
	}
 function menuAction() {
    if(this.data.alias=="1-1"){
		var diag = new top.Dialog();
		diag.Title = "系统锁屏";
		diag.ID = "a1";
		diag.URL="include/lock.html";
		diag.Width=350;
		diag.Height=150;
		diag.ShowCloseButton=false;
		diag.ShowCancelButton=false;
		diag.ShowOkButton=false;
		diag.AllowChangeIndex=false;
		diag.ButtonAlign="center";
		diag.show();
		diag.addButton("btn1","注销系统",function(){
			window.location="login.html";
		});
		diag.addButton("btn2","确定解锁",function(){
			top.document.getElementById("_DialogFrame_a1").contentWindow.validateForm();
		});
	}
	else  if(this.data.alias=="1-2"){
		window.location.reload();
	}
	else  if(this.data.alias=="1-3"){
		top.Dialog.open({URL:"include/skin.html",Title:"更换背景",Modal:false,ResizeResetPosition:false,ShowMinButton:true,Width:720,Height:460});
	}
	else  if(this.data.alias=="1-4-1"){
		$(".nav_icon_h_item").removeClass("nav_icon_h_item_small");
	}
	else  if(this.data.alias=="1-4-2"){
		$(".nav_icon_h_item").addClass("nav_icon_h_item_small");
	}
	else  if(this.data.alias=="1-5"){
		top.Dialog.confirm("确定要退出系统吗",function(){window.location="../login/login.html"});
	}else if(this.data.alias=="1-6"){
	//var app=$(this).text();
	top.Dialog.alert(this.data.name+"*********");
		
	}
}
 
function deleteIcon(){
	top.Dialog.alert(this.data.item.id);	
}

function changeStyle(bgsrc){
	$("body").css({
		"backgroundImage":"url("+bgsrc+")"
	});
	jQuery.jCookie('deskbg',bgsrc);
}
function createDeskIcon(data){
	var iconNavCon=$("#iconnavCon");
	$.each(data["list"],function(idx,item){
		var $screen=$('<div class="screen"></div>');
		$("body").append($screen);
		$screen.hide();
		var $iconNavItem=$('<li class="iconBg"></li>');
		var $iconNavItemA=$('<a></a>');
		if(idx==0){
			$iconNavItemA.addClass("current");
			$screen.show();
		}
		$iconNavItemA.text(item.id);
		$iconNavItem.append($iconNavItemA);
		iconNavCon.append($iconNavItem);		
		if(item.group){
			$.each(item.group,function(idx2,item2){
				var $groupTitle=$('<div class="subTitle"><div class="subTitleCon"></div></div>');
				$groupTitle.find(".subTitleCon").text(item2.name);
				var $groupContent=$('<div class="iconContent"></div>');
				$screen.append($groupTitle);
				$screen.append($groupContent);
				$groupContent.attr("id",item2.id);
				$.each(item2.children,function(idx3,item3){
					var $iconItem=$('<div class="nav_icon_h_item"><a><div class="nav_icon_h_item_img"><img/></div><div class="nav_icon_h_item_text"></div></a></div>');
					var $iconItemIMG=$iconItem.find("img");
					//$iconItemA.attr("link",item3.link);
					$iconItem.attr("id",item3.id);
					var $iconItemA=$iconItem.find("a");
					//$iconItemIMG.contextmenu({width: 110, items: [{ text: "删除模块", 
                      //  icon: "/home/libs/icons/reload.gif", 
                      //  alias: item3.id,
                       // name:item3.name, 
                       // item:item3,
                       // action:function deleteIcon(){
                       // 	top.Dialog.alert(this.data.item.id);	
                       // }}
                       //]});
					
					$iconItemA.click(function(){
						openContentWindow(item3.link,item3.name,item3.id);
					});
		
					if(item3.icon){
						$iconItem.find("img").attr("src",item3.icon);
					}
					
					$iconItem.find(".nav_icon_h_item_text").text(item3.name);
					$iconItem.find(".nav_icon_h_item_text").attr("title",item3.name);
					$groupContent.append($iconItem);
					
				});
				$groupContent.append($('<div class="clear"></div>'));
				
			});
		}
	});
	iconNavCon.append($('<div class="clear"></div>'));
	
	var windowWidth=document.documentElement.clientWidth;
	$("#iconnav").css({
		"left":(windowWidth-$("#iconnav").width())/2
	});
	
	iconNavCon.find("a").each(function(i){
		$(this).click(function(){
			iconNavCon.find("a").removeClass("current");
			$(this).addClass("current");
			$(".screen").hide();
			$(".screen").eq(i).fadeIn(500);
			currentScreenIdx=i;
		});
	});
	
	
}
function createDeskIconList(data){
	 $iconList=$('<div style="padding:0 100px 0 10px;"><table width="100%"><tr></tr></table></div>');
	var $tr=$iconList.find("tr");
    //构建自定义内容
	$.each(iconData["list"],function(idx,item){
		var $td = $('<td class="ver01"></td>');
		var $screen = $('<div class="screen"></div>');
		if(item.group){
			$.each(item.group,function(idx2,item2){
				$.each(item2.children,function(idx3,item3){
					var $iconItem=$('<div class="nav_icon_list"><a href="javascript:;"><div class="nav_icon_list_img"><img/></div><div class="nav_icon_list_text"></div><div class="clear"></div></a></div>');
					var $iconItemA=$iconItem.find("a");
					
					$iconItemA.click(function(){
						openContentWindow(item3.link,item3.name,item3.id);
						//iconDiag.close();
					});
					
					if(item3.icon){
						$iconItem.find("img").attr("src",item3.icon);
					}
					
					$iconItem.find(".nav_icon_list_text").text(item3.name);
					$td.append($iconItem);
					
					$iconItem.hover(function(){
						$(this).addClass("nav_icon_list_over");
					},function(){
						$(this).removeClass("nav_icon_list_over");
					});
				});
				$td.append($('<div class="clear"></div>'));
			});
		}
    	$tr.append($td);
	});
//	var option = { width: 110, items: [
	                             //      { text: "删除模块", icon: "/home/libs/icons/reload.gif", alias: "1-6", action: menuAction }
	                               //    ], onShow: applyrule,
	                               //    onContextMenu: BeforeContextMenu
	              //     };
	//$("#UserApp").contextmenu(option);
	// function applyrule(menu) {  
	//	    menu.applyrule({ name: "UserApp",
	//	            disable: true,
	//	            items: []
	//	        });
	//	}
}
function showAllIcon(){
	 //打开窗口
    iconDiag= new Dialog();
    iconDiag.Style="custom";
    iconDiag.InnerHtml= "";
    iconDiag.Width="100";
    iconDiag.Height="100";
    iconDiag.ID="c2";
	iconDiag.Alpha=90;
	iconDiag.AllowChangeIndex=false;
    iconDiag.show();
	
	 var $container = $(document.getElementById("_Container_c2"));
	 var $mask = $(document.getElementById("_DialogBGDiv"));
	 
	 createDeskIconList(iconData);
	 
	 //内容添加到窗口
	$container.append($iconList);
    var $close=$('<a href="javascript:;" class="backBtn">button</a>');
    $container.prepend($close);

    //点击关闭
    $close.click(function(){
	   $iconList=null;
	    iconDiag.close();
    });
}
function showSearchPanel(){
	if($("#searchPanel")[0].style.display=="none"){
		$("#searchPanel").show();
		$("#searchInput").val("");
	}
	else{
		$("#searchPanel").hide();
	}
}
function searchHandler(){
	$("#searchPanel").hide();
	Dialog.alert("此处为查询结果处理。");
}
function openContentWindow(link,name,winId){
	validateSession();
	
	if(link){
		var diag = new Dialog();
	    diag.URL= link;
	    diag.Title=name;
	    diag.ID=winId;
	    diag.Modal=false;
		diag.MinToTask=true;
		diag.Width=1024;
		diag.Height=800;
	    diag.ShowMinButton=true;
	    diag.ShowMaxButton=true;
	    diag.show();
	    diag.max();
	  //  $('_DialogTable_'+winId).height(800);

	}
}
var appSotreDiag;
var appSotreDiagTree;
function openAppStore(url){
	appSotreDiag = new top.Dialog();
    appSotreDiag.ID="c1";
    appSotreDiag.Width=650;
    appSotreDiag.Height=400;
	appSotreDiag.URL=url;
	appSotreDiag.Title="应用商店";
	//appSotreDiag.Modal=false;
	appSotreDiag.ResizeResetPosition=false;
    appSotreDiag.show();
}

function openAppStoreTree(link,name,winId,icon){
	$.ajax({
		type : 'POST',
		url : '/home/application/group-tree/query',
		data : null,
		success : function(result){
			nodes1 = result;
		},
		error : function(a){
			top.Dialog.alert("访问服务器端出错！");
		},
		dataType : 'json',
		async:false
	});
	
	appSotreDiag.close();
	appSotreDiagTree = new top.Dialog();
    appSotreDiagTree.ID="c2";
    appSotreDiagTree.Width=300;
    appSotreDiagTree.Height=400;
	//appSotreDiagTree.URL="include/appstoreTree.html";
	appSotreDiagTree.Title="选择分类";
	//appSotreDiagTree.Modal=false;
	appSotreDiagTree.ResizeResetPosition=false;
	appSotreDiagTree.ShowButtonRow=true;
	appSotreDiagTree.ButtonAlign="center";
	appSotreDiagTree.OkButtonText=" 确 定 ";
	appSotreDiagTree.CancelButtonText=" 取 消 ";
	appSotreDiagTree.OKEvent = function(){
		var zTree = $.fn.zTree.getZTreeObj("appStoreTreeId");
		var nodes = zTree.getSelectedNodes();
		
		var groupIds = "";
		var noGroupSelected = true;
		for(var i = 0; i <  nodes.length; i++){
			if(nodes[i].nodeType != "node.type.group"){
				continue;
			}
			groupIds = groupIds + "," + nodes[i].extendId;
			noGroupSelected = false;
		}
		
        if(noGroupSelected == true){
			alert("没有选择栏目");
			return;
		}
        groupIds = groupIds.substring(1, groupIds.length);
        
        $.ajax({
    		type : 'POST',
    		url : '/home/application/add',
    		data : {groupIds:groupIds, moduleId: winId},
    		success : function(result){
    			if(result != "SYS_SUCCESS"){
    				top.Dialog.alert("访问服务器端出错！");
    			}
    			window.location.reload();
    			appSotreDiagTree.close();
    		},
    		error : function(a){
    			top.Dialog.alert("访问服务器端出错！");
    		},
    		dataType : 'json',
    		async:false
    	});
        
//      var idx=nodes[0].parentScreen;
//		if(idx!=currentScreenIdx){
//			$("#iconnav").find("a").removeClass("current");
//			$("#iconnav").find("a").eq(idx).addClass("current");
//			$(".screen").hide();
//			$(".screen").eq(idx).fadeIn(500);
//			currentScreenIdx=idx;
//		}
//		
//		var groupId=nodes[0].parentGroup;
//		var $iconItem=$('<div class="nav_icon_h_item "><a ><div class="nav_icon_h_item_img"><img/></div><div class="nav_icon_h_item_text"></div></a></div>');
//		var $iconItemA=$iconItem.find("a");
//		$iconItemA.click(function(){
//			openContentWindow(link,name,winId,icon);
//		});
//		$iconItem.find("img").attr("src",icon);
//		$iconItem.find(".nav_icon_h_item_text").text(name);
//		$iconItem.find(".nav_icon_h_item_text").attr("title",name);
//		$("#"+groupId).find(".clear").before($iconItem);
//		appSotreDiagTree.close();
    };
    appSotreDiagTree.show();
	
	var $container=$(document.getElementById("_Container_c2"));
    var $tree=$(' <ul id="appStoreTreeId" class="ztree"></ul>');
    $.fn.zTree.init($tree, {}, nodes1);
    $container.append($tree);
}

function validateSession(){
	$.ajax({type: 'POST',
		url: 'session-timeout',
		success:function(result, textStatus){
			if(result == "SYS_SUCCESS"){
				return;
			}
			window.location.href = "j_spring_cas_security_logout";
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			window.location.href = "j_spring_cas_security_logout";
		},
		dataType: "json",
		async:false
	});	
}

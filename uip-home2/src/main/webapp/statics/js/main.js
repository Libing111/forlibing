//企业主页用到的js文件
//该页面的全局变量
var pageArgs = {
	pageMinHeight : 500,
	pageMinWidth : 1050
};
//全局缓存
var properGlobalCache = {
		_data:{},
		put:function(key,value){
			properGlobalCache._data[key]=value;
		},
		get:function(key){
			return properGlobalCache._data[key];
		}
};

// 首页入口函数
$(document)
		.ready(
				function() {
					// 页面初始化函数
					init();
					// 浏览器改变大小时执行的事件
					$(window).resize(function() {
						doLayout();
					});

					// 设置菜单功能
					$("#settingSelector")
							.find('li')
							.bind(
									'click',
									function() {
										var currentLi = $(this);
										var datatype = $(currentLi).attr(
												'datatype');
										switch (datatype) {
										case "changepass": {
											// 添加按钮的回调函数
											var diag = new top.Dialog();

											diag.ShowMaxButton = true;
											diag.Width = 450;
											diag.Height = 280;
											diag.ShowMinButton = true;

											diag.Title = "修改密码";

											diag.URL = "account/password";

											diag.OKEvent = function() {
												var oldPassword = $("#pwd")
														.val();
												var password = $("#password")
														.val();
												var confirmPassword = $(
														"#confirmPassword")
														.val();
												var valid = diag.innerFrame.contentWindow
														.valids();
												if (valid) {
													$
															.ajax({
																type : "POST",
																url : "account/savePassWord",
																dataType : 'json',
																data : diag.innerFrame.contentWindow
																		.getParams(),
																cache : false,
																async : false,
																success : function(
																		responseText,
																		statusText,
																		xhr,
																		$form) {
																	if (responseText == "SYS_SUCCESS") {
																		top.Dialog
																				.alert(
																						"修改成功",
																						function() {
																							top.Dialog
																									.close();
																							logout();
																						});
																	} else {
																		top.Dialog
																				.alert(
																						responseText,
																						function() {
																							window.location.href = "<%=basePath %>account/password?oldPassword="
																									+ oldPassword
																									+ "&password="
																									+ password
																									+ "&confirmPassword="
																									+ confirmPassword;

																						});
																	}
																}
															});
												}
											};

											diag.show();
										}
											break;
										case "theme": {
											var themeColor = $(currentLi).attr(
													'themecolor');
											$
													.ajax({
														type : "POST",
														url : "saveThemeColor",
														dataType : 'json',
														data : {
															themeColor : themeColor
														},
														cache : false,
														async : false,
														success : function(data) {
															if (data == "SYS_SUCCESS") {
																window.location.href = './index';
															}
														},

													});
										}
											break;
										}
										;

									});
					var themeColor0 = $("#theme").attr("themeColor");// 当前的主题颜色。
					$("#settingSelector").find('li').each(function() {
						var currentLi = $(this);
						var themeColor = $(currentLi).attr('themecolor');
						if (themeColor0 == themeColor) {
							$(currentLi).addClass("selected");
						} else {
							$(currentLi).removeClass("selected");
						}
					});

					// 显示/隐藏主题菜单。
					$("ul.plat-tools li.ThemeTools").bind("mouseover",
							function() {
								$(this).find(".plat-theme-list").show();
							}).bind("mouseout", function() {
						$(this).find(".plat-theme-list").hide();
					});
					$(".plat-theme-list li")
							.bind(
									"mouseover",
									function() {
										var currentLi = $(this);
										if (!currentLi.hasClass("selected")
												&& currentLi.attr("datatype") != 'subtitle') {
											currentLi.addClass("hover");
										}

									}).bind("mouseout", function() {
								var currentLi = $(this);
								if (!currentLi.hasClass("selected")) {
									currentLi.removeClass("hover");
								}
							});

					$("#logoutButton").bind("click", function() {
						var logoutUrls = [];
						
						$.ajax({
							type: "POST",
							url: "getLogoutUrls",
							dataType:'json',
							cache: false,
							async:false,
							success: function(data){
								logoutUrls = data;
							}
						});
						top.Dialog.confirm2({
							Msg : "确定要退出系统吗？",
							IconClass : "icon_confirm_exit",
							ShowCloseButton :false,
							OKEvent : function() {
								for(var i=0; i < logoutUrls.length; i++){
											$.ajax({type: "POST", url: logoutUrls[i]});
										}
									logout();
								}
						});

					});
				});

// 页面初始化函数
function init() {
	// 加载菜单数据
	var menus = [];

	$.ajax({
		type : "POST",
		url : "menus",
		dataType : 'json',
		cache : false,
		async : false,
		success : function(data) {
			if (data == null) {
				window.location.href = "/home/j_spring_cas_security_logout";
			} else {
				menus = data;
			}
		},

	});
	// 加载菜单控件
	$("#plat-menu-wrap").Menu({
		menuData : menus,
		menuActionCallBack : function(menuItem) {// 点击一级菜单局部刷新中央区域
			// 页面跳转的方法
			pageAjaxLocation(menuItem);
		}
	});

	// 加载给中央区域添加滚动条插件
	$('#contentwrap').mCustomScrollbar({
		theme : "minimal-dark"
	});
	// 初始化taskpanel插件
	$(document.body).TaskPanel({
		removeAble : false
	});
	// 计算页面大小
	doLayout();

	// 加载菜单的第一个菜单方法，此方法必须在页面加载最后步骤进行完成。
	if (menus != null && menus.length > 0) {
		// 为了避开页面框架的渲染时间，所以需要延迟执行这个函数
		setTimeout(function() {
			// 选中第一个菜单
			$("#plat-menu-wrap").Menu('select', menus[0]);
		}, 500);
	}
}

/*
 * 一级菜单的点击动作执行的页面跳转的方法，由于此操作属于页面的逻辑范围，所以为了不破坏menu控件的原子性因此在main页面中进行调用
 */
function pageAjaxLocation(menuItem) {
	if (menuItem != null) {
		var url = menuItem['url'];
		// $('#content').perfectScrollbar('destroy');
		$("#content").html("");
		// 异步加载html片段
		$(document.body).TaskPanel('dialogHideAll');
		showContentLoading();
		$.ajax({
			type : "POST",
			url : url,
			dataType : 'html',
			cache : false,
			success : function(htmlFragment) {
				var curItem=$("#plat-menu-wrap").Menu('select');
				if(curItem&&curItem==menuItem){
					$("#content").html(htmlFragment);
				}
			},
			error : function() {
				hideContentLoading();
			}
		});
	}
};

// 页面重新定义大小的方法
function doLayout() {
	// 为了解决ie8，右侧出现空白的问题
	if ($.browser.msie8) {
		$('body,html').addClass('nosrcoll');
	}
	// 得到header的高度
	var header_height = $("#header").height() + 5;
	// 得到footer的高度
	var footer_height = $("#footer").height();
	// 获取窗口的高度
	var window_height = $(window).height();
	// 获取窗口的宽度
	var window_width = $(window).width();
	// 获取页面应有的高度和宽度
	var pageHeight = window_height;
	var pageWidth = window_width;
	// 如果小于最小高度时，则取最小高度
	if (window_height < pageArgs.pageMinHeight) {
		pageHeight = pageArgs.pageMinHeight;
	}
	;
	// 如果小于最小宽度时，则取最小宽度
	if (pageWidth < pageArgs.pageMinWidth) {
		pageWidth = pageArgs.pageMinWidth;
	}
	;
	// 中央内容的高度为页面高度 - header高度 - footer高度
	var contentWrap_height = pageHeight - header_height - footer_height;
	$("#contentwrap").height(contentWrap_height + 1);
	$("#contentwrap").width(pageWidth);
	// 给页面的头设置宽度
	$("#header").width(pageWidth);
	$("#footer").width(pageWidth);
	// 给taskPanel对象设置当前页面的宽度和高度
	$(document.body).TaskPanel('setPageSize', pageWidth, pageHeight);
	// 计算当前位置和tabpanel的大小
	$(document.body).TaskPanel('resizeTaskContainer');
	if ($.browser.msie8) {
		$('body,html').removeClass('nosrcoll');
	}
}
// 修改iframe的高度
function myframeload(iframe) {
	var iframeHeight = Math.min(
			iframe.contentWindow.window.document.documentElement.scrollHeight,
			iframe.contentWindow.window.document.body.scrollHeight);
	var wrapHeight = $("#contentwrap").height();
	if (iframeHeight < wrapHeight) {
		iframeHeight = wrapHeight;
	}
	$(iframe).height(iframeHeight);
}

/**
 * 自定义的关闭窗口事件。
 * 
 * @param target
 * @param id
 * @returns {Boolean}
 */
function customDialogCloseEvent(target, id) {
	var TaskContainerInner = $.data(target, 'TaskPanel').TaskContainerInner;
	TaskContainerInner.TaskButton('delBtn', id, target);// 调用删除按钮的方法
	return true;
}

function customAddTaskPanel(item) {
	// console.log(JsonTools.encode(item));
	var $content = $(document).find("div#contentwrap");
	var $header = $(document).find("div#headerWrap");
	var height0 = $content.height() - 42;
	var width0 = $content.width();
	var left0 = 0;
	var top0 = $header.height() + 6;
	var hpadding = 10;
	var vpadding = 10;

	if (item.ptype == "form") {
		item.width = 926;
	} else {
		item.width = width0 - 2 * hpadding;
	}
	item.width -= 26;
	item.height = height0 - vpadding * 2;
	item.left = left0 + (width0 - item.width - 26) / 2 - 2;
	item.top = top0 + (height0 - item.height) / 2 - 2;

	
	$(document.body).TaskPanel('addTaskPanel', item);
}
function logout() {
	window.location.href = "/home/j_spring_cas_security_logout";
}
//显示加载按钮
function showContentLoading(){
	var $con1 = $("#contentwrap");
	$con1.mask(uncompile(quiLanguage.progressBar.title), 0, true,
	"transparent");
}
//隐藏加载按钮
function hideContentLoading(){
	var $con1 = $("#contentwrap");
	$con1.unmask();//取消加载进度。
}
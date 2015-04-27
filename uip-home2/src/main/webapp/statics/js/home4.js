(function(){
	doLayout();
	//页面重新定义大小的方法
	function doLayout() {
		var $contentWrap = $("#contentwrap");
		var $home4Wrap = $("#home4Wrap");
		var $home4Pages = $("#home4Pages");
		var $home4OnePage = $(".home4OnePage");
		var homeWidth = $contentWrap.width();
		var homeHeight = $contentWrap.height();
		var marginV = 20;
		var marginH = 80;
		
		$home4Wrap.width(homeWidth);
		$home4Wrap.height(homeHeight);
		$home4Wrap.css({
			"position" : "relative"
		});
		$home4Pages.width(homeWidth - 2 * marginH);
		$home4Pages.height(homeHeight - 2 * marginV);
		$home4Pages.css({
			"position" : "absolute",
			"top" : marginV + "px",
			"left" : marginH + "px"
		});

		$home4OnePage.width($home4Pages.width());
		$home4OnePage.height($home4Pages.height());
		$home4OnePage.css({});
		var bottomTableHeight=$home4OnePage.height()-80;
		
		var $home_news4 = $(".home_news4");
		var $home_news4_hd = $home_news4.find(".hd");
		var $home_news4_bd = $home_news4.find(".bd");
		var $home_news4_divchat = $home_news4.find(".divchat");
		var chatmaV = 10;
		var chatmaH = 10;
		$home_news4_divchat.css({
			"width" : ($home_news4.width() - chatmaH * 2) + "px",
			"height" : (bottomTableHeight
					- $home_news4_hd.outerHeight()
					- $home_news4_bd.outerHeight() - chatmaV * 2)
					+ "px",
			"margin-top" : chatmaV + "px",
			"margin-left" : chatmaH + "px"
		});
		var $home4_bottom1 = $("#home4_bottom1");
		var $div_col2=$(".div_col2");
		var col_height=((bottomTableHeight) / 3);
		$div_col2.css({
			"height" :  col_height+ "px"
		});
		var colM=2;
		$div_col2.find(".home_news4").height(col_height-colM);
		$div_col2.find(".home_news4 .hd").css({"height":30+"px","line-height":30+"px"});
		$div_col2.find(".home_news4 .bd,.home_news4 .bd ul").height(col_height-$div_col2.find(".home_news4 .hd").height()-colM);
		
		
	};
	var $home4OnePage = $(".home4OnePage");
	$home4OnePage.delegate("ul.ulist li","click",function(){
		var mydata=$(this).attr("mydata");
		if(mydata){
			try{
				customAddTaskPanel(JsonTools.decode(mydata));
			}catch(e){
				if(console){
					console.log(e);
				}
			}
			
		}
	});
	//待办工作等出现滚动条。
	$(".home_news4 .bd ul.ulist").mCustomScrollbar({
		theme : "minimal-dark",
	});
	hideContentLoading();
	
})();






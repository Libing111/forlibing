var nodes = [
    { id:1, parentId:0, name:"部门1", icon:"/home/libs/icons/user_group.gif",drag:false,open:true},
    { id:11, parentId:1, name:"员工1",icon:"/home/libs/icons/user.gif",drop:false},
    { id:12, parentId:1, name:"员工2",icon:"/home/libs/icons/user_female.gif",drop:false},
    { id:13, parentId:1, name:"员工3",icon:"/home/libs/icons/user_worker.gif",drop:false},
	{ id:14, parentId:1, name:"员工4",icon:"/home/libs/icons/user_off.gif",drop:false},
	{ id:15, parentId:1, name:"员工5",icon:"/home/libs/icons/user.gif",drop:false},
	{ id:16, parentId:1, name:"员工6",icon:"/home/libs/icons/user_off.gif",drop:false},
	{ id:17, parentId:1, name:"员工7",icon:"/home/libs/icons/user.gif",drop:false},
	{ id:18, parentId:1, name:"员工8",icon:"/home/libs/icons/user_worker.gif",drop:false},
    { id:2, parentId:0, name:"部门2", icon:"/home/libs/icons/user_group.gif",drag:false,open:true},
    { id:21, parentId:2, name:"员工9",icon:"/home/libs/icons/user.gif",drop:false},
    { id:22, parentId:2, name:"员工10",icon:"/home/libs/icons/user_female.gif",drop:false},
    { id:23, parentId:2, name:"员工11",icon:"/home/libs/icons/user_worker.gif",drop:false},
	{ id:24, parentId:2, name:"员工12",icon:"/home/libs/icons/user_off.gif",drop:false},
	{ id:25, parentId:2, name:"员工13",icon:"/home/libs/icons/user.gif",drop:false},
	{ id:26, parentId:2, name:"员工14",icon:"/home/libs/icons/user_off.gif",drop:false},
    { id:3, parentId:0, name:"部门3", icon:"/home/libs/icons/user_group.gif",drag:false,open:true},
    { id:31, parentId:3, name:"员工15",icon:"/home/libs/icons/user.gif",drop:false},
    { id:32, parentId:3, name:"员工16",icon:"/home/libs/icons/user_female.gif",drop:false},
	{ id:33, parentId:3, name:"员工17",icon:"/home/libs/icons/user_off.gif",drop:false},
	{ id:34, parentId:3, name:"员工18",icon:"/home/libs/icons/user_off.gif",drop:false}

];
var setting = {
    edit: {
        enable: false,
        showRemoveBtn: false,
        showRenameBtn: false
    },
	view: {
		fontCss: getFontCss,
        dblClickExpand: false
    },
    callback: {
	    onClick: onNodeClick,
		onDblClick:onNodDblClick
    }
};  
var zTree_Menu;
$(function(){
	$.fn.zTree.init($("#treeDemo"),setting, nodes);
	zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
	$("#searchKey").keydown(function(event){
	 	if(event.keyCode==13){
			findNode();
		}
	 })
	 
	  $("#topArr").toggle(function(){//点击主界面小箭头弹出或收回
	        chatMainShow=true;
			$("#chat_main").animate({
	            bottom: taskBarHeight+"px"
	        })
	        $("#icon_arr").removeClass("img_btn_up");
	        $("#icon_arr").addClass("img_btn_down");
	        return false;
	    }, function(){
	         chatMainShow=false;
			 $("#chat_main").animate({
	            bottom: -402+taskBarHeight+"px"
	        })
	       $("#icon_arr").removeClass("img_btn_down");
	        $("#icon_arr").addClass("img_btn_up");
	        return false;
	    })
})


function onNodeClick(event, treeId, treeNode){
    zTree_Menu.expandNode(treeNode);
}  
function beforeDrag(treeId, treeNodes) {
    for (var i = 0, l = treeNodes.length; i < l; i++) {
        if(treeNodes[i].drag === false) {
            return false;
        }
    }
    return true;
}
function beforeDrop(treeId, treeNodes, targetNode, moveType) {
    return targetNode ? targetNode.drop !== false : true;
}
function onDrag(event, treeId, treeNodes){
    zTree_Menu.expandAll(false);
}
function findNode(){
	var value = $.trim($("#searchKey").val());
	if(value!=""){
		getNodesByParamFuzzy('name', value)
	}
}
 //根据某一条件查找节点 模糊查询
function getNodesByParamFuzzy(key, value, parentNode){
    var nodes = zTree_Menu.getNodesByParamFuzzy(key, value, parentNode);  
    //取消之前的高亮显示
    highlightNodes(zTree_Menu, zTree_Menu.highlightNodeList, false);
    //高亮显示
    highlightNodes(zTree_Menu, nodes, true);
    zTree_Menu.highlightNodeList = nodes;
    //选中第一个
    if(null != nodes && nodes.length > 0){
        zTree_Menu.expandAll(false);
		var pNode=nodes[0].getParentNode()
		zTree_Menu.expandNode(pNode,true);
		zTree_Menu.expandNode(nodes[0],true);
    }
}

//高亮显示
function highlightNodes(zTree, nodes, highlight) {
    if(null == nodes)  return;
	for( var i = 0, l = nodes.length; i < l; i++) {
		nodes[i].highlight = highlight;
		zTree.updateNode(nodes[i]);
	}
}
function getFontCss(treeId, node){
    return (!!node.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}
function viewInfo(){
	top.Dialog.open({URL:"../../sample/demo/user-management-content2.html",Title:"查看信息",MinToTask:true,ID:"viewinfo",Width:500,Height:330,Modal:false,ResizeResetPosition:false,ShowMinButton:true}); 
}
function onNodDblClick(event, treeId, treeNode){
	if(!treeNode.isParent){
		var diag = new top.Dialog();
		diag.ID=treeNode.id;
	    diag.Title ="协同："+ treeNode.name;
	    diag.URL = "/home/im/chat/index";
	    diag.ShowButtonRow=true;
	    diag.OkButtonText=" 发 送 ";
	    diag.CancelButtonText=" 关 闭 ";
		diag.Modal=false;
		diag.ResizeResetPosition=false;
		diag.Width=530;
		diag.Height=390;
		diag.ShowMinButton=true;
		diag.ShowMaxButton=true;
		diag.MinToTask=true;
		diag.OKEvent = function(){
			diag.innerFrame.contentWindow.setUserName("张小三");
			diag.innerFrame.contentWindow.chatHandler();
		};
		diag.show();
	}
}
function addFriend(){
	top.Dialog.open({URL:"include/add.html",Title:"添加好友",MinToTask:true,ID:"addfriend",Width:300,Height:180,Modal:false,ResizeResetPosition:false,ShowMinButton:true}); 
}
var nodeid=100;
function addFriendHandler(gid,nodeName){
	var nodeid=parseInt(gid);
	var pnode=zTree_Menu.getNodeByParam("id",nodeid);
	if(pnode){
		nodeid++;
		var newNode = {id:nodeid,name:nodeName,icon:"/home/libs/icons/user.gif",drop:false};
		zTree_Menu.expandNode(pnode,true);
		zTree_Menu.addNodes(pnode,newNode);
	}
}
function searchRoom(){
	top.Dialog.open({URL:"include/room.html",Title:"搜索聊天室",Width:400,Height:260,Modal:false,MinToTask:true,ID:"searchroom",ResizeResetPosition:false,ShowMinButton:true}); 
}
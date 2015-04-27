/**
 * 描述：给jquery的ajax函数添加RequestHeader ,告知java后台此链接属于ajax请求
 */
$.ajaxSetup({
	type : "POST",
	beforeSend : function(XMLHttpRequest) {
		XMLHttpRequest.setRequestHeader("RequestType", "AJAX");
	}
});


/*判断IE浏览器的版本*/
$.browser.msie10 = $.browser.msie && /msie 10\.0/i.test(window.navigator.userAgent.toLowerCase());
$.browser.msie9 = $.browser.msie && /msie 9\.0/i.test(window.navigator.userAgent.toLowerCase()); 
$.browser.msie8 = $.browser.msie && /msie 8\.0/i.test(window.navigator.userAgent.toLowerCase());
$.browser.msie7 = $.browser.msie && /msie 7\.0/i.test(window.navigator.userAgent.toLowerCase());
$.browser.msie6 = !$.browser.msie8 && !$.browser.msie7 && $.browser.msie && /msie 6\.0/i.test(window.navigator.userAgent.toLowerCase());

// 禁止浏览器的回退键
$(document).keydown(
		function(event) {
			var elem = event.srcElement || event.target;
			if (elem.tagName) {
				var tagName = elem.tagName.toUpperCase();
				if (event.keyCode == 8) {
					if (tagName == "INPUT" || tagName == "SELECT"
							|| tagName == "TEXTAREA" || tagName == "TEXT") {
						// 当控件为readonly或者disabled时，也要屏蔽backspace键
						if ($(elem).attr("readonly")
								|| $(elem).attr("disabled")) {
							return false;
						}
					} else {
						return false;
					}
				}
			}
		});

// js的EndWith方法
String.prototype.EndWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substring(this.length - s.length) == s) {
		return true;
	} else {
		return false;
	}
	return true;
};
// js的startWith方法
String.prototype.StartWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substr(0, s.length) == s) {
		return true;
	} else {
		return false;
	}
	return true;
};
/** trim() method for String */
String.prototype.trim=function() {
	return this.replace(/(^\s*)|(\s*$)/g,'');
};

// 判断是否为数组的函数
function isArray(v) {
	return v && typeof v.length == 'number' && typeof v.splice == 'function';
}

// 扩展js的array，添加数组删除功能，n表示第几项，从0开始算起。
Array.prototype.del = function(n) {
	if (n < 0) // 如果n<0，则不进行任何操作。
		return this;
	else
		return this.slice(0, n).concat(this.slice(n + 1, this.length));
};
// 扩展js的array，获取数字数组中最大的数值
Array.prototype.max = function() {
	return Math.max.apply({}, this);
};
// 扩展js的array，获取数字数组中最小的数值
Array.prototype.min = function() {
	return Math.min.apply({}, this);
};

// 日期格式化函数
/*
 * 用法 var date = new Date(); var dateStr = date.format("yyyy-MM-dd");
 */
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
};

// ---------------------------------------------------
// 判断闰年
// ---------------------------------------------------
Date.prototype.isLeapYear = function() {
	return (0 == this.getYear() % 4 && ((this.getYear() % 100 != 0) || (this
			.getYear() % 400 == 0)));
};

// +---------------------------------------------------
// | 求两个时间的天数差 日期格式为 YYYY-MM-dd
// +---------------------------------------------------
Date.prototype.daysBetween = function(DateOne, DateTwo) {
	var OneMonth = DateOne.substring(5, DateOne.lastIndexOf('-'));
	var OneDay = DateOne
			.substring(DateOne.length, DateOne.lastIndexOf('-') + 1);
	var OneYear = DateOne.substring(0, DateOne.indexOf('-'));

	var TwoMonth = DateTwo.substring(5, DateTwo.lastIndexOf('-'));
	var TwoDay = DateTwo
			.substring(DateTwo.length, DateTwo.lastIndexOf('-') + 1);
	var TwoYear = DateTwo.substring(0, DateTwo.indexOf('-'));
	var cha = ((Date.parse(OneMonth + '/' + OneDay + '/' + OneYear) - Date
			.parse(TwoMonth + '/' + TwoDay + '/' + TwoYear)) / 86400000);
	return Math.abs(cha);
};

// +---------------------------------------------------
// | 日期计算 日期根据时间增加
// +---------------------------------------------------
Date.prototype.DateAdd = function(strInterval, Number, date) {
	var dtTmp = this;
	if (date != null) {
		dtTmp = date;
	}

	switch (strInterval) {
	case 's':
		return new Date(Date.parse(dtTmp) + (1000 * Number));
	case 'n':
		return new Date(Date.parse(dtTmp) + (60000 * Number));
	case 'h':
		return new Date(Date.parse(dtTmp) + (3600000 * Number));
	case 'd':
		return new Date(Date.parse(dtTmp) + (86400000 * Number));
	case 'w':
		return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));
	case 'q':
		return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number * 3,
				dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp
						.getSeconds());
	case 'm':
		return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp
				.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp
				.getSeconds());
	case 'y':
		return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp
				.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp
				.getSeconds());
	}
};

// +---------------------------------------------------
// | 日期计算 日期根据时间减少
// +---------------------------------------------------
Date.prototype.DateSubtraction = function(strInterval, Number, date) {
	var dtTmp = this;
	if (date != null) {
		dtTmp = date;
	}
	switch (strInterval) {
	case 's':
		return new Date(Date.parse(dtTmp) - (1000 * Number));
	case 'n':
		return new Date(Date.parse(dtTmp) - (60000 * Number));
	case 'h':
		return new Date(Date.parse(dtTmp) - (3600000 * Number));
	case 'd':
		return new Date(Date.parse(dtTmp) - (86400000 * Number));
	case 'w':
		return new Date(Date.parse(dtTmp) - ((86400000 * 7) * Number));
	case 'q':
		return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) - Number * 3,
				dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp
						.getSeconds());
	case 'm':
		return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) - Number, dtTmp
				.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp
				.getSeconds());
	case 'y':
		return new Date((dtTmp.getFullYear() - Number), dtTmp.getMonth(), dtTmp
				.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp
				.getSeconds());
	}
};

// +---------------------------------------------------
// | 比较日期差 dtEnd 格式为日期型或者 有效日期格式字符串
// +---------------------------------------------------
Date.prototype.DateDiff = function(strInterval, dtEnd) {
	var dtStart = this;
	if (typeof dtEnd == 'string')// 如果是字符串转换为日期型
	{
		dtEnd = StringToDate(dtEnd);
	}
	switch (strInterval) {
	case 's':
		return parseInt((dtEnd - dtStart) / 1000);
	case 'n':
		return parseInt((dtEnd - dtStart) / 60000);
	case 'h':
		return parseInt((dtEnd - dtStart) / 3600000);
	case 'd':
		return parseInt((dtEnd - dtStart) / 86400000);
	case 'w':
		return parseInt((dtEnd - dtStart) / (86400000 * 7));
	case 'm':
		return (dtEnd.getMonth() + 1)
				+ ((dtEnd.getFullYear() - dtStart.getFullYear()) * 12)
				- (dtStart.getMonth() + 1);
	case 'y':
		return dtEnd.getFullYear() - dtStart.getFullYear();
	}
};

// +---------------------------------------------------
// | 把日期分割成数组
// +---------------------------------------------------
Date.prototype.toArray = function() {
	var myDate = this;
	var myArray = Array();
	myArray[0] = myDate.getFullYear();
	myArray[1] = myDate.getMonth();
	myArray[2] = myDate.getDate();
	myArray[3] = myDate.getHours();
	myArray[4] = myDate.getMinutes();
	myArray[5] = myDate.getSeconds();
	return myArray;
};

// +---------------------------------------------------
// | 取得当前日期所在月的最大天数
// +---------------------------------------------------
Date.prototype.MaxDayOfDate = function() {
	var myDate = this;
	var ary = myDate.toArray();
	var date1 = (new Date(ary[0], ary[1] + 1, 1));
	var date2 = date1.dateAdd(1, 'm', 1);
	var result = DateDiff(date1.Format('yyyy-MM-dd'), date2
			.Format('yyyy-MM-dd'));
	return result;
};

// +---------------------------------------------------
// | 完美的js克隆函数
// +---------------------------------------------------
function common_object_clone(myObj) {
	var objClone;
	if (JsonTools.encode(myObj) == "[]") {
		return myObj;
	}
	if (myObj.constructor == Object) {
		objClone = new myObj.constructor();
	} else {
		objClone = new myObj.constructor(myObj.valueOf());
	}
	// 开始循环
	for ( var key in myObj) {
		if (objClone[key] != myObj[key]) {
			if (typeof (myObj[key]) == 'object') {
				objClone[key] = common_object_clone(myObj[key]);
			} else {
				objClone[key] = myObj[key];
			}
		}
	}
	objClone.toString = myObj.toString;
	objClone.valueOf = myObj.valueOf;
	return objClone;
}

/*
 * 两个数组删除函数比较函数 参数 arr1,arr2,key 如果key为空时，则说明是纯数组 返回 arr 描述
 * arr1和arr2进行比较，之后删除arr1中里面和arr2重复的项
 */
function common_compareDelArray(arr1, arr2, key) {
	for (var i = 0; i < arr2.length; i++) {
		for (var j = 0; j < arr1.length; j++) {
			var tmp1 = null;
			var tmp2 = null;
			if (key && key.length > 0) {
				tmp1 = arr2[i][key];
				tmp2 = arr1[j][key];
			} else {
				tmp1 = arr2[i];
				tmp2 = arr1[j];
			}
			if (tmp1 == tmp2) {
				var size = arr1.length;
				var len = size - j - 1;
				for (var k = 0; k < len; k++) {
					var tmp = arr1[j + k];
					arr1[j + k] = arr1[j + k + 1];
					arr1[j + k + 1] = tmp;
				}
				arr1 = arr1.del(size - 1);
			}
		}
	}
	return arr1;
}

// 通用工具类
SqUtil = new (function() {

	// 将ip转换成int类型
	this.ipToInt = function(IP) {
		if (IP == null || IP == "") {
			return 0;
		}
		var a = IP.split(".");
		for (var i = 0; i < 4; i++) {
			a[i] = parseInt(a[i]);
			if (isNaN(a[i]))
				a[i] = 0;
			if (a[i] < 0)
				a[i] += 256;
			if (a[i] > 255)
				a[i] -= 256;
		}
		return ((a[0] << 16) * 256) + ((a[1] << 16) | (a[2] << 8) | a[3]);
	};

	// 将int类型转换成ip
	this.intToIp = function(ipl) {
		var IP;
		var tmp;
		var val = ipl;

		tmp = val >>> 24;
		IP = tmp + ".";

		tmp = (val >>> 16) & 0xFF;
		IP = IP + tmp + ".";

		tmp = (val >>> 8) & 0xFF;
		IP = IP + tmp + ".";

		tmp = val & 0xFF;
		IP = IP + tmp;

		return IP;
	};

	this.ipV6ToExtension = function(ipV6) {
		if (ipV6 == null || ipV6 == "") {
			return 0;
		}
		var extension = ipV6;
		var arrLen = extension.split("::").length;
		if (1 != arrLen) {// 存在简写
			var arr = extension.split("::");
			var begin = arr[0];
			var end = arr[1];

			var beginArr = begin.split(":");
			var endArr = end.split(":");

			var beginLen = beginArr.length;
			var endLen = endArr.length;

			var conLen = beginLen + endLen;

			var abbreviationStr = "";

			for (var i = 0; i < 8 - conLen; i++) {
				abbreviationStr += "0000:";
			}
			abbreviationStr = abbreviationStr.substr(0,
					abbreviationStr.length - 1);
			extension = begin + ":" + abbreviationStr + ":" + end;
		}

		var returnVal = "";

		var arr = extension.split(":");

		for (var i = 0; i < arr.length; i++) {
			var len = 4 - arr[i].length;
			var left0 = "";
			for (var j = 0; j < len; j++) {
				left0 += "0";
			}
			returnVal += left0 + arr[i] + ":";
		}
		returnVal = returnVal.substr(0, returnVal.length - 1);
		return returnVal;
	};

	this.ipV6ToAbbreviation = function(ipV6) {
		if (ipV6 == null || ipV6 == "" || ipV6.indexOf(":") == -1) {
			return ipV6;
		}
		var ipStr = ipV6;
		var arr = ipStr.split(":");
		var abbreviation = "";
		for (var i = 0; i < arr.length; i++) {
			var s = arr[i].replace(/^(\s*)(0*)(.*?)/, "");
			if ("" == s) {
				abbreviation += "0:";
			} else {
				abbreviation += s + ":";
			}
		}
		abbreviation = abbreviation.substr(0, abbreviation.length - 1);
		abbreviation = abbreviation.replace(/(?:[0]{1}:)+/, "::");
		abbreviation = abbreviation.replace(":::", "::");
		return abbreviation;
	};

	// 某年某月某日是一年的第几周
	this.getYearWeek = function(y, m, d) {
		var date1 = new Date(y, parseInt(m) - 1, d);
		var date2 = new Date(y, 0, 1);
		var dd = Math.round((date1.valueOf() - date2.valueOf()) / 86400000);
		return Math.ceil((dd + ((date2.getDay() + 1) - 1)) / 7);
	};

	// 将时间转换成int类型
	this.timeToInt = function(time) {
		var timeArr = time.split(":");
		var hour = parseFloat(timeArr[0]);

		var mins = parseFloat(timeArr[1]);
		var sec = parseFloat(timeArr[2]);

		return (hour * 60 * 60) + (mins * 60) + sec;

	};

	// yubaoqi add begin 20110304
	// 将时间格式是hh:mm的值转换成int类型
	this.timeToInt2 = function(time) {
		var timeArr = time.split(":");
		var hour = parseFloat(timeArr[0]);
		var mins = parseFloat(timeArr[1]);
		return (hour * 60 * 60) + (mins * 60);
	};
	// yubaoqi add end 20110304

	// 得到当前时间
	this.getCurrentTime = function() {
		var date = new Date();
		return date.getTime();
	};

	// 判断是否为闰年
	this.isLeapYear = function(year) {
		var year = parseInt(year);
		if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
			return true;
		} else {
			return false;
		}
	};

	// 根据年份和月份得到当前月的天数
	this.getDays = function(year, month) {
		var year = parseInt(year);
		var month = parseInt(month);
		var days = 0;

		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			days = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			days = 30;
			break;
		case 2:
			if (this.isLeapYear(year)) {
				days = 29;
			} else {
				days = 28;
			}
			break;
		}
		return days;
	};

	// 判断字符串是否为空 如果为空 则返回true，否则返回false
	this.strIsEmpty = function(str) {
		if (str == null || str.length == 0 || str == "") {
			return true;
		}
		return false;
	};

	// 将时间字符串转换为date 即 2008-09-02 08:08:08.0
	this.strToDate = function(str) {
		var tmps = str.substr(str.length - 2, str.length);
		if (tmps == ".0") {
			str = str.substr(0, str.length - 2);
		}
		return new Date(Date.parse(str.replace(/-/g, "/")));
	};

})();

$(document).ready(function() {
	$(window).resize(function() {
		pageDoLayout();
	});
});

// 页面改变大小时执行的方法
// var oTime;
// window.onresize = function(){
// if (oTime)
// {
// clearTimeout(oTime);//取消循环
// }
// oTime = setTimeout("pageDoLayout()", 70); //延迟70毫秒执行，并且定制一个标志位，如果标志位存在则停止
// };

function pageDoLayout() {

};

// /////////输入框验证函数/////////////////////////////////////////////////////
function sq_number_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var exp = /^[0-9]*$/;
	var reg = val.match(exp);
	if (reg == null) {
		obj.result = false;
	}
	if (!obj.result) {
		obj.msg = '输入的整数不合法。';
	}
	return obj;
}

// 输入框正则表达式函数****************************************输入框正则表达式函数******************************输入框正则表达式函数*********************

function _sq_ipv6_validate(str) {
	return /::/.test(str) ? /^([\da-f]{1,4}(:|::)){1,6}[\da-f]{1,4}$/i
			.test(str) : /^([\da-f]{1,4}:){7}[\da-f]{1,4}$/i.test(str);
	;
}
// ipv6正则表达式验证
function sq_ipv6_validate(ipstr) {
	var obj = {
		result : true,
		msg : ''
	};
	var f = _sq_ipv6_validate(ipstr);
	if (f == false) {
		obj.result = false;
		obj.msg = '输入的IPv6地址不合法。';
	}
	return obj;
}
// ipv4的正则表达式验证
function sq_ipv4_validate(ipstr) {
	var obj = {
		result : true,
		msg : ''
	};
	var exp = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
	var reg = ipstr.match(exp);
	if (reg == null) {
		obj.result = false;
	}
	if (!obj.result) {
		obj.msg = '输入的IPv4地址不合法。';
	}
	return obj;
}
// ip验证
function sq_ip_validate(ipstr) {
	var obj = {
		result : true,
		msg : ''
	};
	var v1 = sq_ipv4_validate(ipstr);
	var v2 = sq_ipv6_validate(ipstr);
	if (v1.result == false && v2.result == false) {
		obj.result = false;
		obj.msg = '输入的IP地址不合法。';
	}
	return obj;
}

/* 密码验证正则表达式 */
function sq_password_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	if (val != null && val != "" && val.length > 0) {
		var ls = 0;

		if (val.match(/([a-z])+/)) {
			ls++;
		}

		if (val.match(/([0-9])+/)) {
			ls++;
		}

		if (val.match(/([A-Z])+/)) {
			ls++;
		}

		if (val.match(/[^a-zA-Z0-9]+/)) {
			ls++;
		}

		if (ls < 4) {
			obj.result = false;
		}
	}
	if (!obj.result) {
		obj.msg = '密码必须包含大小写字母、数字、特殊字符。';
	}
	return obj;
}

/* 输入框1的正则表达式 标题校验 */
function sq_input1_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /^([\u4E00-\u9FA5]|[\uFE30-\uFFA0]|[a-zA-Z0-9-_])*$/;
	if (val != null && val != "" && val.length > 0) {
		var reghead = /^[-_]/;
		var reglast = /[-_]$/;
		var t1 = reghead.test(val);
		var t2 = reglast.test(val);

		// 验证-和_是否在两头出现
		if (!t1 && !t2) {
			if (!regx.test(val)) {
				obj.result = false;
			}
		} else {
			obj.result = false;
		}
	}
	if (!obj.result) {
		obj.msg = '只允许输入汉字、字母、数字、“_”、“-”符号，且“_”和“-”只能位于中间位置。';
	}
	return obj;
}
/* 输入框2的正则表达式 */
function sq_input2_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /^([\u4E00-\u9FA5]|[\uFE30-\uFFA0]|[a-zA-Z0-9-_])*$/;

	if (val != null && val != "" && val.length > 0) {
		if (!regx.test(val)) {
			obj.result = false;
		}
	}
	if (!obj.result) {
		obj.msg = '只允许输入汉字、字母、数字、“_”、“-”符号。';
	}
	return obj;
}
/* 输入框3的正则表达式 备注校验 */
function sq_input3_validate(val) {
	return true;
}
/* 输入框4的正则表达式 */
function sq_input4_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /^([\u4E00-\u9FA5]|[\uFE30-\uFFA0]|[a-zA-Z0-9-_()])*$/;
	if (val != null && val != "" && val.length > 0) {
		var reghead = /^[-_]/;
		var reglast = /[-_]$/;
		var t1 = reghead.test(val);
		var t2 = reglast.test(val);

		// 验证-和_是否在两头出现
		if (!t1 && !t2) {
			if (!regx.test(val)) {
				obj.result = false;
			}
		} else {
			obj.result = false;
		}
	}
	if (!obj.result) {
		obj.msg = '只允许输入汉字、字母、数字、“_”、“-”、“(”、“)”符号，且“_”和“-”只能位于中间位置。';
	}
	return obj;
}

/* 账号输入框1的正则表达式 */
function sq_accountInput1_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /^([a-zA-Z0-9-_.])*$/;
	if (val != null && val != "" && val.length > 0) {
		var reghead = /^[-_.]/;
		var reglast = /[-_.]$/;
		var t1 = reghead.test(val);
		var t2 = reglast.test(val);
		// 验证-和_是否在两头出现
		if (!t1 && !t2) {
			if (!regx.test(val)) {
				obj.result = false;
			}
		} else {
			obj.result = false;
		}
	}
	if (!obj.result) {
		obj.msg = '只允许输入字母、数字和“_”“-”“.”符号，且“_”“-”“.”只能位于中间位置。';
	}
	return obj;
}

/* 账号输入框2的正则表达式 */
function sq_accountInput2_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /^([a-zA-Z0-9-_.])*$/;
	if (val != null && val != "" && val.length > 0) {
		if (!regx.test(val)) {
			obj.result = false;
		}
	}
	if (!obj.result) {
		obj.msg = '只允许输入字母、数字和“_”“-”“.”符号。';
	}
	return obj;
}

/* 账号输入框3的正则表达式 */
function sq_accountInput3_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /^([a-zA-Z0-9-_@.])*$/;
	if (val != null && val != "" && val.length > 0) {
		if (!regx.test(val)) {
			obj.result = false;
		}
	}
	if (!obj.result) {
		obj.msg = '只允许输入字母、数字和“_”“-”“@”“.”符号。';
	}
	return obj;
}

/* 名字输入框1的正则表达式 */
function sq_nameInput1_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /^([\u4E00-\u9FA5]|[\uFE30-\uFFA0]|[a-zA-Z0-9-_.])*$/;
	if (val != null && val != "" && val.length > 0) {
		var reghead = /^[-_.]/;
		var reglast = /[-_.]$/;
		var t1 = reghead.test(val);
		var t2 = reglast.test(val);
		// 验证-和_是否在两头出现
		if (!t1 && !t2) {
			if (!regx.test(val)) {
				obj.result = false;
			}
		} else {
			obj.result = false;
		}
	}
	if (!obj.result) {
		obj.msg = '只允许输入汉字、字母、数字、“_”“-”“.”符号，且“_”“-”“.”只能位于中间位置。';
	}
	return result;
}

/* 名字输入框2的正则表达式 */
function sq_nameInput2_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /^([\u4E00-\u9FA5]|[\uFE30-\uFFA0]|[a-zA-Z0-9-_.])*$/;
	if (val != null && val != "" && val.length > 0) {
		if (!regx.test(val)) {
			obj.result = false;
		}
	}
	if (!obj.result) {
		obj.msg = '只允许输入汉字、字母、数字、“_”“-”“.”符号。';
	}
	return obj;
}

/* 名字输入框3的正则表达式 */
function sq_nameInput3_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /^([\u4E00-\u9FA5]|[\uFE30-\uFFA0]|[a-zA-Z0-9-.])*$/;
	if (val != null && val != "" && val.length > 0) {
		if (!regx.test(val)) {
			obj.result = false;
		}
	}
	if (!obj.result) {
		obj.msg = '只允许输入汉字、字母、数字、“-”“.”符号。';
	}
	return obj;
}

/* 名字输入框3的正则表达式 */
function sq_nameInput4_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /^([a-zA-Z0-9-.])*$/;
	if (val != null && val != "" && val.length > 0) {
		if (!regx.test(val)) {
			obj.result = false;
		}
	}
	if (!obj.result) {
		obj.msg = '只允许输入字母、数字、“-”“.”符号。';
	}
	return obj;
}

/* 时间输入框1 格式hh:mm:ss */
function sq_timeInput1_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /^([0-1]\d|2[0-3]):[0-5]\d:[0-5]\d$/;
	if (val != null && val != "" && val.length > 0) {
		if (!regx.test(val)) {
			obj.result = false;
		}
	}
	if (!obj.result) {
		obj.msg = '时间格式不正确，正确的格式是hh:mm:ss。';
	}

	return obj;
}

/* 时间输入框2 格式hh:mm */
function sq_timeInput2_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /^([0-1]\d|2[0-3]):[0-5]\d$/;
	if (val != null && val != "" && val.length > 0) {
		if (!regx.test(val)) {
			obj.result = false;
		}
	}
	if (!obj.result) {
		obj.msg = '时间格式不正确，正确的格式是hh:mm。';
	}
	return obj;
}
/* mac地址输入框表达式 */
function sq_macInput_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /[A-Fa-f\d]{2}:[A-Fa-f\d]{2}:[A-Fa-f\d]{2}:[A-Fa-f\d]{2}:[A-Fa-f\d]{2}:[A-Fa-f\d]{2}/;
	if (val != null && val != "" && val.length > 0) {
		if (!regx.test(val)) {
			obj.result = false;
		}
	}
	if (!obj.result) {
		obj.msg = 'Mac地址格式不正确。例如：00:00:00:00:00:00';
	}
	return obj;
}

/* email输入框的验证函数(查询中) */
function sq_emailInputSearch_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /^[a-zA-Z-0-9-_.]*@?[a-zA-Z-0-9-_.]*$/;
	if (val != null && val != "" && val.length > 0) {
		if (!regx.test(val)) {
			obj.result = false;
		}
	}
	var errorMsg = '邮件格式不正确，该处用于查询因此最多有一个@符号，且@符号出现的位置可以是任意一个位置，也可以没有@符号。';
	if (!obj.result) {
		obj.msg = errorMsg;
	}

	return obj;
}

/* email输入框的验证函数(非查询) */
function sq_emailInput_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if (val != null && val != "" && val.length > 0) {
		if (!regx.test(val)) {
			obj.result = false;
		}
	}
	if (!obj.result) {
		obj.msg = '邮件格式不正确。';
	}
	return obj;
}

/* email输入框的验证函数(可以输入多个email的正则表达式函数，分隔符为半角分号) */
function sq_emailMoreInput_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if (val != null && val != "" && val.length > 0) {
		if (val.indexOf(";") == -1) {
			if (!regx.test(val)) {
				obj.result = false;
			}
		} else {
			var emails = val.split(";");
			var len = emails.length;
			for (var i = 0; i < len; i++) {
				if (i != len - 1) {
					if (emails[i] == null || emails[i] == "") {
						obj.result = false;
						break;
					} else {
						if (!regx.test(emails[i])) {
							obj.result = false;
							break;
						}
					}
				} else {
					if (!regx.test(emails[i])) {
						obj.result = false;
						break;
					}
				}
			}
		}
	}
	var errorMsg = '';
	errorMsg += "邮件格式不正确，正确格式如下:<br>";
	errorMsg += "<li>可以输入一个或多个电子邮件地址</li>";
	errorMsg += "<li>每个邮件地址必须用英文的分号分隔</li>";
	if (!result) {
		obj.msg = errorMsg;
	}
	return obj;
}

/* 端口的验证表达式方法 */
function sq_portInput_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /^([0-9]|[1-9][0-9]{0,4})$/;
	if (val != null && val != "" && val.length > 0) {
		if (!regx.test(val)) {
			obj.result = false;
		} else {
			var v = parseInt(val);
			if (v < 1 || v > 65535) {
				obj.result = false;
			}
		}
	}
	if (!obj.result) {
		obj.msg = '端口格式不正确，只能输入1~65535之间的整数。';
	}
	return obj;
}

/* 手机输入框验证函数 */
function sq_mobile_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /(^0{0,1}(13|15|18)[0-9]{9}$)/;
	if (val != null && val != "" && val.length > 0) {
		if (!regx.test(val)) {
			obj.result = false;
		}
	}
	if (!obj.result) {
		obj.msg = "手机号码格式不正确，只能输入数字并且最大长度为20。";
	}
	return obj;
}

/* 固定电话输入框验证函数 */
function sq_telphone_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	var regx = /^[0-9()-]{1,20}$/;
	if (val != null && val != "" && val.length > 0) {
		if (!regx.test(val)) {
			obj.result = false;
		}
	}
	var errorMsg = '';
	errorMsg += "固定电话格式不正确，正确格式如下:<br>";
	errorMsg += "<li>如果没有区号，电话号码为3到8位。例如23928485</li>";
	errorMsg += "<li>如果有区号用半角\"-\"隔开。例如：024-23928485</li>";
	errorMsg += "<li>如果有分机号，024-23928484-01</li>";
	if (!obj.result) {
		obj.msg = errorMsg;
	}
	return obj;
}

function sq_ip_or_dns_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	if (val != null && val != "" && val.length > 0) {
		var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
				+ "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
				+ "(([0-9]{1,3}.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
				+ "|" // 允许IP和DOMAIN（域名）
				+ "([0-9a-z_!~*'()-]+.)+" // 域名- www.
				+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]." // 二级域名
				+ "[a-z]{2,6})" // first level domain- .com or .museum
				+ "(:[0-9]{1,4})?" // 端口- :80
				+ "((/?)|" // a slash isn't required if there is no file name
				+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";

		var regx = new RegExp(strRegex);
		if (!regx.test(val)) {
			obj.result = false;
		}
	}
	var errorMsg = '服务器格式不正确，应该为域名或IP地址！';
	if (!obj.result) {
		obj.msg = errorMsg;
	}
	return obj;
}

/* url的正则表达式函数 */
function sq_url_validate(val) {
	var obj = {
		result : true,
		msg : ''
	};
	if (val != null && val != "" && val.length > 0) {
		var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
				+ "+(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
				+ "(([0-9]{1,3}.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
				+ "|" // 允许IP和DOMAIN（域名）
				+ "([0-9a-zA-Z_!~*'()-]+.)+" // 域名- www.
				+ "([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-z]." // 二级域名
				+ "[a-zA-Z]{2,6})" // first level domain- .com or .museum
				+ "(:[0-9]{1,5})?" // 端口- :80
				+ "((/+)|" // a slash isn't required if there is no file name
				+ "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/*)$";

		var regx = new RegExp(strRegex);
		if (!regx.test(val)) {
			obj.result = false;
		} else {
			var valArr = val.split("://");
			var tmpVal = valArr[1];
			var mhp = tmpVal.indexOf(":");
			if (mhp != -1) {
				var xxp = tmpVal.indexOf("/");
				var port = parseInt(tmpVal.substring(mhp + 1, xxp));
				if (port < 1 || port > 65535) {
					obj.result = false;
				}
			}
		}
	}
	var errorMsg = '';
	errorMsg += "url格式不正确，如：http://www.baidu.com/";
	if (!obj.result) {
		obj.msg = errorMsg;
	}
	return obj;
}

/*
 * setting的格式
 * 
 * setting = { idKey:'', pIdKey:'', childrenKey:'' }
 * 
 */
function transformTozTreeFormat(setting, sNodes) {
	var i, l, key = setting.idKey, parentKey = setting.pIdKey, childKey = setting.childrenKey;
	if (!key || key == "" || !sNodes)
		return [];

	if (isArray(sNodes)) {
		var r = [];
		var tmpMap = [];
		for (i = 0, l = sNodes.length; i < l; i++) {
			tmpMap[sNodes[i][key]] = sNodes[i];
		}
		for (i = 0, l = sNodes.length; i < l; i++) {
			if (tmpMap[sNodes[i][parentKey]]
					&& sNodes[i][key] != sNodes[i][parentKey]) {
				if (!tmpMap[sNodes[i][parentKey]][childKey])
					tmpMap[sNodes[i][parentKey]][childKey] = [];
				tmpMap[sNodes[i][parentKey]][childKey].push(sNodes[i]);
			} else {
				r.push(sNodes[i]);
			}
		}
		return r;
	} else {
		return [ sNodes ];
	}
}
/**
 * 对树形数组进行过滤
 * @param config
 *    idKey menuid
 *    pidKey 父id
 *    filterKey 要过滤的键
 *    filterFun  自定义过滤的函数,对每个treeData item 进行遍历，调用此方法，返回true的，满足过滤条件，添加到返回数组中。
 *      function(m,filterText){}  m 对应的treeData item, filterText 过滤文本
 * @param treeData 要过滤的整个菜单
 * @param filterText 过滤的文字
 * @returns
 */
function doTreeMenuFilter(config, treeData, filterText) {
	// 默认配置
	var treeQueryConfig = {
		idKey : 'id',
		pidKey : 'pid'

	};
	// 继承新配置
	config = $.extend({}, treeQueryConfig, config);
	if ((!config.filterKey) && (!config.filterFunc)) {
		alert("config.filterKey or config.filterFunc must be set one!");
		return null;
	}

	if (config.filterKey && (!config.filterFunc)) {// 如果有filterKey,且未设定filterFunc则按filterKey查找，使用默认函数
		// m为传入的菜单对象
		config.filterFunc = function(m, filterText1) {
			if (m[config.filterKey]) {
				return m[config.filterKey].indexOf(filterText1) >= 0;
			}
			return false;
		}
	}

	// 将要返回的数组
	var menus2 = [];
	if (filterText != null && filterText.length > 0) {// 进行查找
		var mapMenuIdx = config.mapMenuIdx;
		// 建立菜单id与在数组中的位置关系。
		if (!mapMenuIdx) {
			mapMenuIdx = {};
			for (var i = 0; i < treeData.length; i++) {
				mapMenuIdx[treeData[i][config.idKey]] = i;
			}
			config.mapMenuIdx = mapMenuIdx;// 缓存到config中，下次如果对应同一个config，可以拉高效率。
		}
		var mapMenu = {};
		for (var i = 0; i < treeData.length; i++) {
			var m = treeData[i];
			if (config.filterFunc(m, filterText)) {// 包含查询条件
				if (!mapMenu[m.id]) {// 还未添加进过滤后的菜单
					mapMenu[m.id] = m;// 添加到过滤后的菜单
				}
				// 循环调用加入父控件。
				var m2 = m;
				do {
					var pidx = undefined;
					pidx = mapMenuIdx[m2[config.pidKey]];// 获取对应的父菜单
					if (pidx >= 0) { // 是有效的菜单下标
						m2 = treeData[pidx];// 父菜单
						if (!mapMenu[m2[config.idKey]]) {// 还未添加进过滤后的菜单
							mapMenu[m2[config.idKey]] = m2;// 添加到过滤后的菜单
						}
					} else {
						m2 = null;
					}
				} while (m2 != null);

			}
		}// end of for
		for ( var k in mapMenu) {
			menus2.push(mapMenu[k]);
		}
	} else {// 全查
		menus2 = treeData
	}
	return menus2;
}

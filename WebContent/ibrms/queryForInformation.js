//传入SQL，并调用后台进行数据获取。
//返回一个字符串
function queryForResult(sql) {
	
	post = "DTTableName=" + "DTT0113";

	post = encodeURI(post);
	post = encodeURI(post);// 两次，很关键
	Ext.Ajax.request( {
		url :'queryForInformation.jsp',
		method :'post',
		success : function(response, options) {
		myAlert("OK!");
			handleSuccess(Ext.decode(response.responseText));
		},
		failure : function(response, options) {
			myAlert("failure");
		},
		headers : {
			"Content-Type" :"application/x-www-form-urlencoded",
			"cache-control" :"no-cache"
		},
		params :post
	});

}

function decodeStringToArray(result) {

	var resultArray = new Array();

	var rows = result.split("^");

	var columns = null;

	for ( var i = 0; i < rows.length; i++) {
		columns = rows[i].split("/");

		resultArray[i] = new Array();

		resultArray[i] = columns;
	}

	return resultArray;
}
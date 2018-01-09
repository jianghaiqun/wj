function queryForViewParameter(table, id) {
	var Request = null;
	var viewParameter = "";
	try {
		Request = new ActiveXObject("Microsoft.XMLHTTP");
	} catch (ex) {
		Request = new ActiveXObject("MSXML2.XMLHTTP");
	}

	Request.onreadystatechange = function() {

		if (Request.readyState == 4 && Request.status == 200) {
			viewParameter = new String(Request.responseText);
		}
	}
	var URL = "./JavaScript/viewParameter.jsp?tableName=" + table + "&id=" + id;
	Request.open("POST", URL, false);
	Request.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");
	Request.send(null);
	return viewParameter;
}
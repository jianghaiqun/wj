//var XMLHttpReq;
var maxTagPosts = 1; // 这个变量用来保存包含关联文章最多的那个Tag的文章数

/*function processResponseStyle2() {
	if (XMLHttpReq.readyState == 4) {
		if (XMLHttpReq.status == 200) {
			var tagCloud = JSON.parse( XMLHttpReq.responseText );
			for(var i=0; i< tagCloud.length;i++)
			{
				if(tagCloud[i].relatedPosts>maxTagPosts)
				{
					maxTagPosts = tagCloud[i].relatedPosts;
				}
			}
			
			var tagCloudHTML = "";
			for(var j=0; j< tagCloud.length;j++)
			{
				var searchCondition = encodeURI(tagCloud[j].categoryName);
				var classSize = getClass(tagCloud[j].relatedPosts);
				var aTag = "<span class='"+classSize+"'><a href='#?search="+searchCondition
					+ "' title='Posts: "+tagCloud[j].relatedPosts+"'"
					+ " style='color:" + getRandomColor() +"' >"
					+ tagCloud[j].categoryName + "</a></span>\n";
			
				tagCloudHTML += aTag;
			}			
			document.getElementById("tagcloudwithstyle2").innerHTML = tagCloudHTML;
		} else {
			window.alert("Page Exception!");
		}
	}
}

function getRandomColor()
{
	var r=Math.floor((Math.random()*256)).toString(16);
	var g=Math.floor((Math.random()*256)).toString(16);
	var b=Math.floor((Math.random()*256)).toString(16);
	var colorString="#"+r+g+b;
	return colorString;
}
function getTagCloud2() {
	sendRequest('getTagCloud',processResponseStyle2);
}*/

function getClass(relatedPosts)
{
	var presentage = Math.floor((relatedPosts/maxTagPosts)*100);
	var classSize;
	if(presentage<20)
	{
		classSize = 'smallest';
	}
	else if(20<=presentage && presentage <40)
	{
		classSize = 'small';
	}
	else if(40<=presentage && presentage <60)
	{
		classSize = 'medium';
	}
	else if(60<=presentage && presentage <80)
	{
		classSize = 'large';
	}
	else if(80<=presentage)
	{
		classSize = 'largest';
	}
	return classSize;
}

function createTagCloud(object){
	var tagCloud = object;
	for(var i=0; i< object.length;i++)
	{
		if(tagCloud[i].relatedPosts>maxTagPosts)
		{
			maxTagPosts = tagCloud[i].relatedPosts;
		}
	}

	var tagCloudHTML = "";
	for(var j=0; j< tagCloud.length;j++)
	{
		var searchCondition = encodeURI(tagCloud[j].url);
		var classSize = getClass(tagCloud[j].relatedPosts);
		var aTag = "<span class='"+classSize+"'><a href=\""+searchCondition
		+"\" title=\"Posts: "+tagCloud[j].relatedPosts+"\">"+tagCloud[j].categoryName+"</a></span>\n";
		
		tagCloudHTML += aTag;
	}
	
	document.getElementById("tagcloud").innerHTML = tagCloudHTML;
}


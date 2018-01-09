package com.sinosoft.statical;

import java.util.HashMap;

import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.ServletUtil;

public class TemplateData {
	protected String FirstFileName; // 第一页名称 如index.shtml

	protected String OtherFileName; // 其他页名称 如index_1.shtml

	protected int PageSize = 20;

	protected int PageIndex = 0;

	protected int Total;

	protected int PageCount;

	protected DataTable ListTable;
	
	/**
	 * 详细页分页列表标记(列表id, 是否标记)
	 */
	private HashMap<String, Boolean> detailAsList = new HashMap<String, Boolean>(1);
	
	/**
	 * 设置详细页分页列表标记
	 * 
	 * @param listId 列表id：list_0、list_1等
	 * @param detailAsList
	 */
	public void setDetailAsList(String listId, boolean detailAsList)
	{
		if ((null == listId) || (!listId.matches("list_\\d+")))
		{
			return;
		}
		this.detailAsList.put(listId, Boolean.valueOf(detailAsList));
	}
	
	/**
	 * 获取详细页分页列表标记
	 * 
	 * @param listIndex 列表id：list_0、list_1等
	 */
	public boolean getDetailAsList(String listIndex)
	{
		Boolean B = this.detailAsList.get(listIndex);
		if (B == null)
		{
			return false;
		}
		
		return B.booleanValue();
	}
	
	/**
	 * @author zhangjinquan 11180
	 * @description 增加模板全局map数据
	 * @createdate 2012-12-19
	 */
	protected HashMap<String, HashMap<String, String>> globalData = new HashMap<String, HashMap<String, String>>();
	
	/**
	 * @author zhangjinquan 11180
	 * @description 增加模板全局map数据元素增加方法
	 * @createdate 2012-12-19
	 * @param id
	 * @param key
	 * @param value
	 */
	public void addGlobalData(String id, String key, String value)
	{
		if ((null == id) || (null == key) || (null == value))
		{
			return;
		}
		
		id = id.toLowerCase();
		key = key.toLowerCase();
		
		if (this.globalData.containsKey(id))
		{
			this.globalData.get(id).put(key, value);
		}
		else
		{
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(key, value);
			this.globalData.put(id, map);
		}
	}
	public void addGlobalData(String id, String key, int value)
	{
		this.addGlobalData(id, key, String.valueOf(value));
	}
	/**
	 * @author zhangjinquan 11180
	 * @description 增加模板全局map数据元素增加方法
	 * @createdate 2012-12-24
	 * @param otherGlobalData 另一个已知的全局数据
	 */
	public void addGlobalData(HashMap<String, HashMap<String, String>> otherGlobalData)
	{
		this.globalData.putAll(otherGlobalData);
	}
	/**
	 * @author zhangjinquan 11180
	 * @description 增加模板全局map数据元素数据获取方法
	 * @createdate 2012-12-19
	 * @param id
	 * @param key
	 */
	public String getGlobalData(String id, String key)
	{
		if ((null == id) || (null == key))
		{
			return "";
		}
		
		id = id.toLowerCase();
		key = key.toLowerCase();
		
		if (this.globalData.containsKey(id))
		{
			String returnVal = this.globalData.get(id).get(key);
			return ((null == returnVal) ? "" : returnVal);
		}
		return "";
	}

	public DataTable getDataTable(String sql) {
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		return dt;
	}

	public DataTable getPagedDataTable(DataTable dt) {
		int count = PageSize;
		if ((PageIndex + 1) * PageSize > Total) {
			count = Total - PageIndex * PageSize;
		}
		Object[][] values = new Object[count][dt.getColCount()];
		for (int i = 0; i < count; i++) {
			values[i] = dt.getDataRow(PageIndex * PageSize + i).getDataValues();
		}
		return new DataTable(dt.getDataColumns(), values);
	}

	public String getPreviousPage() {
		if (PageIndex == 1) {
			if(FirstFileName.equals("index.shtml")){
				return "";
			}else{
				return FirstFileName;
			}
		} else if (PageIndex != 0) {
			return OtherFileName.replaceAll("@INDEX", String.valueOf(PageIndex));
		} else if (PageIndex == 0) {
			return "#";
		}

		return null;
	}
	public boolean getPreviousPageCheck() {
			if(FirstFileName.equals("index.shtml")){
				return true;
			}else{
				return false;
			}
	}
	
	public String getNowPage(int page) {
		if (page == 1) {
			if(FirstFileName.equals("index.shtml")){
				return ".";
			}else{
				return FirstFileName;
			}
		} else if (page != 0) {
			return OtherFileName.replaceAll("@INDEX", String.valueOf(page));
		} else if (page == 0) {
			return "#";
		}

		return null;
	}

	public int getPageCount() {
		return PageCount;
	}

	public void setPageCount(int pageCount) {
		PageCount = pageCount;
	}

	public int getPageIndex() {
		return PageIndex;
	}

	public void setPageIndex(int pageIndex) {
		PageIndex = pageIndex;
	}

	public int getTotal() {
		return Total;
	}

	public void setTotal(int total) {
		Total = total;
	}

	public String getNextPage() {
		if(PageCount==0){
			return "#";
		}
		if (PageIndex + 1 != PageCount) {
			return OtherFileName.replaceAll("@INDEX", String.valueOf(PageIndex + 2));
		} else {
			return "#";
		}
	}

	public String getFirstPage() {
		return FirstFileName;
	}

	public String getLastPage() {
		if(PageCount == 0){
			return "#";
		}
		if (PageCount == 1) {
			return FirstFileName;
		} else {
			return OtherFileName.replaceAll("@INDEX", String.valueOf(PageCount));
		}
	}

	public String getFirstFileName() {
		return FirstFileName;
	}

	public void setFirstFileName(String firstFileName) {
		FirstFileName = firstFileName;
	}

	public String getOtherFileName() {
		return OtherFileName;
	}

	public void setOtherFileName(String otherFileName) {
		OtherFileName = otherFileName;
	}

	public int getPageSize() {
		return PageSize;
	}

	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}
	
	
	
	public String getPageBar(int id) {
		
		StringBuffer sb = new StringBuffer();

		sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
		sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
		sb.append("共" + Total + "条记录，每页" + PageSize + "条，当前第<span class='fc_ch1'>" + ((Total == 0) ? 0: (PageIndex + 1)) 
				+ "</span>/<span class='fc_ch1'>" + PageCount + "</span>页&nbsp;&nbsp;&nbsp;&nbsp;");
		if (PageIndex > 0) {
		
			if(getPreviousPageCheck()){
				sb.append("<a href='.'><span class='fc_ch1'>第一页</span></a>|");
			}else{
				
				sb.append("<a href='" + getPreviousPage() + "'><span class='fc_ch1'>第一页</span></a>|");	
			}
			if(1==PageIndex){
				if(getPreviousPageCheck()){
				sb.append("<a href='.'><span class='fc_ch1'>上一页</span></a>|");
				}else{
				sb.append("<a href='" + getPreviousPage() + "'><span class='fc_ch1'>上一页</span></a>|");	
				}
			}else{
				sb.append("<a href='" + getPreviousPage() + "'><span class='fc_ch1'>上一页</span></a>|");
			}
			
		} else {
			sb.append("<span class='fc_hui2'>第一页</span>|");
			sb.append("<span class='fc_hui2'>上一页</span>|");
		}
		if (PageIndex + 1 != PageCount && PageCount>0) {
			sb.append("<a href='" + getNextPage() + "'><span class='fc_ch1'>下一页</span></a>|");
			sb.append("<a href='" + getLastPage() + "'><span class='fc_ch1'>最末页</span></a>");
		} else {
			sb.append("<span class='fc_hui2'>下一页</span>|");
			sb.append("<span class='fc_hui2'>最末页</span>");
		}

		sb.append("&nbsp;&nbsp;转到第<input id='_PageBar_Index_"+id+"' type='text' size='4' style='width:30px' ");
		sb.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">页");

		String listNameRule = getFirstFileName().substring(0, getFirstFileName().lastIndexOf("."));
		String ext = ServletUtil.getUrlExtension(getFirstFileName());
		sb.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_"
				+id+"').value)){alert('错误的页码');$('_PageBar_Index_"+id+"').focus();}else if(document.getElementById('_PageBar_Index_"
				+id+"').value>" + PageCount + "){alert('错误的页码');document.getElementById('_PageBar_Index_"
				+id+"').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_"
				+id+"').value)>0?document.getElementById('_PageBar_Index_"
				+id+"').value:1;if(PageIndex==1){window.location='"+getFirstFileName()+"'}else{window.location='"+listNameRule+"_'+PageIndex+'"+ext+"';}}\" style='' value='跳转'></td>");
		sb.append("</tr></table>");
		return sb.toString();
	}
	
	/**
	 * 新版列表页分页
	 * @param id
	 * @return
	 */
	public String getPageBar2() {
		StringBuffer sb = new StringBuffer();
		sb.append("<div id='pagination'><input type='hidden' id='listProductCount' value='"+Total+"'/><ul>");
		
		int j = 1;
		int pagec = (Total%PageSize==0?Total/PageSize:(Total/PageSize+1));
		if (PageIndex == 0) {
			sb.append("<li class='page_prev'><a href='"+getPreviousPage()+"'><span class='default'>上一页</span></a></li>");
			sb.append("<li class='now'><a href='"+getNowPage(1)+"'><span>"+1+"</span></a></li>");
		} else {
			sb.append("<li class='page_prev'><a href='"+getPreviousPage()+"'><span>上一页</span></a></li>");
			sb.append("<li><a href='"+getNowPage(1)+"'><span>"+ 1+"</span></a></li>");
		}
		for (j = 2;j< pagec; j++) {
			if(PageCount>6){
				if (PageIndex >= PageCount - 4) {
					if (j >= PageCount - 3) {
						if (j == PageCount - 3) {
							sb.append("<li class='omit'><span>...</span></li>");
						}
						if (j==(PageIndex+1)) {
							sb.append("<li class='now'>");
						} else {
							sb.append("<li>");
						}
						sb.append("<a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><span>"+j+"</span></a></li>");
						
					}
				} else if(PageIndex<3){
						if(j<5){
							if (j==(PageIndex+1)) {
								sb.append("<li class='now'>");
							} else {
								sb.append("<li>");
							}
							sb.append("<a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><span>"+ j+"</span></a></li>");
							if(j==4){						
								sb.append("<li class='omit'><span>...</span></li>");
							}
						}
				} else {
					if(PageIndex>2 && PageCount>(PageIndex+1)){
						if(j>(PageIndex-1)&&j<(PageIndex+3)){
							if (j==(PageIndex+1)) {
								sb.append("<li class='now'>");
							} else {
								sb.append("<li>");
							}
							sb.append("<a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><span>"+j+"</span></a></li>");
						}
						if(j==(PageIndex+2)&&j<PageCount-2){
							sb.append("<li class='omit'><span>...</span></li>");
						}
					}
				}
			}else if(PageCount<7){
				if (j==(PageIndex+1)) {
					sb.append("<li class='now'>");
				} else {
					sb.append("<li>");
				}
				sb.append("<a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><span>"+ j+"</span></a></li>");
			}
		}

		if (PageIndex + 1 == pagec) {
			if (pagec > 1) {
				sb.append("<li class='now'><a href='"+getNowPage(pagec)+"'><span>"+pagec+"</span></a></li>");
			}
			sb.append("<li class='page_next'><a href='"+getNextPage()+"'><span class='default'>下一页</span></a></li>");
		} else {
			sb.append("<li><a href='"+getNowPage(pagec)+"'><span>"+ pagec+"</span></a></li>");
			sb.append("<li class='page_next'><a href='"+getNextPage()+"'><span>下一页</span></a></li>");
		}
		
		sb.append("</ul></div>");
		return sb.toString();
	}
	
	/**
	 * 新版文章列表分页
	 * @param id
	 * @return
	 */
	public String getPageBarNew(int id) {
		StringBuffer sb = new StringBuffer();
		sb.append("<ul>");
		
		int j = 1;
		int pagec = (Total%PageSize==0?Total/PageSize:(Total/PageSize+1));
		if (PageIndex == 0) {
			sb.append("<li class='page_prev'><a href='"+getPreviousPage()+"'><span class='default'>上一页</span></a></li>");
			sb.append("<li class='now'><a href='"+getNowPage(1)+"'><span>"+1+"</span></a></li>");
		} else {
			sb.append("<li class='page_prev'><a href='"+getNowPage(PageIndex)+"'><span>上一页</span></a></li>");
			sb.append("<li><a href='"+getNowPage(1)+"'><span>"+ 1+"</span></a></li>");
		}
		if (pagec == 0) {
			sb.append("<li class='page_next'><a href='javascript:void(0);'><span class='default'>下一页</span></a></li>");
			sb.append("</ul>");
			return sb.toString();
		}
		for (j = 2;j< pagec; j++) {
			if(PageCount>6){
				if (PageIndex >= PageCount - 4) {
					if (j >= PageCount - 3) {
						if (j == PageCount - 3) {
							sb.append("<li class='omit'><span>...</span></li>");
						}
						if (j==(PageIndex+1)) {
							sb.append("<li class='now'>");
						} else {
							sb.append("<li>");
						}
						sb.append("<a href='" + getNowPage(j) + "'><span>"+j+"</span></a></li>");
						
					}
				} else if(PageIndex<3){
						if(j<5){
							if (j==(PageIndex+1)) {
								sb.append("<li class='now'>");
							} else {
								sb.append("<li>");
							}
							sb.append("<a href='" + getNowPage(j) + "'><span>"+ j+"</span></a></li>");
							if(j==4){						
								sb.append("<li class='omit'><span>...</span></li>");
							}
						}
				} else {
					if(PageIndex>2 && PageCount>(PageIndex+1)){
						if(j>(PageIndex-1)&&j<(PageIndex+3)){
							if (j==(PageIndex+1)) {
								sb.append("<li class='now'>");
							} else {
								sb.append("<li>");
							}
							sb.append("<a href='" + getNowPage(j) + "'><span>"+j+"</span></a></li>");
						}
						if(j==(PageIndex+2)&&j<PageCount-2){
							sb.append("<li class='omit'><span>...</span></li>");
						}
					}
				}
			}else if(PageCount<7){
				if (j==(PageIndex+1)) {
					sb.append("<li class='now'>");
				} else {
					sb.append("<li>");
				}
				sb.append("<a href='" + getNowPage(j) + "'><span>"+ j+"</span></a></li>");
			}
		}

		if (PageIndex + 1 == pagec) {
			if (pagec > 1) {
				sb.append("<li class='now'><a href='"+getNowPage(pagec)+"'><span>"+pagec+"</span></a></li>");
			}
			sb.append("<li class='page_next'><a href='"+getNextPage()+"'><span class='default'>下一页</span></a></li>");
		} else {
			sb.append("<li><a href='"+getNowPage(pagec)+"'><span>"+ pagec+"</span></a></li>");
			sb.append("<li class='page_next'><a href='"+getNextPage()+"'><span>下一页</span></a></li>");
		}
		
		sb.append("</ul>");
		return sb.toString();
	}
	
    public String getPageBar1(int id) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("<div class='plpage'>");
		sb.append("<div class='plpagecont'>");
		sb.append("<span class='plpage02'><a href='"+getNowPage(1)+"'>首页</a></span>");
		sb.append("<span class='plpage01'><a href='"+getPreviousPage()+"'><</a></span>");
		int j = 1;  
		for( j = 1;j<=(Total%PageSize==0?Total/PageSize:(Total/PageSize+1));j++){    
			if(j==(PageIndex+1)){
				sb.append("<span class='plpage04'><a href='"+getNowPage(j)+"'><font color = '#FFFFFF'> "+j+"</font></a></span>&nbsp;");
			}
			//如果总页面大于5
			else if(PageCount>5){
				if(PageIndex>3 && PageCount>(PageIndex+1)){
					if(j>(PageIndex-3)&&j<(PageIndex+3)){
						sb.append("<span class='plpage01'><a href='"+getNowPage(j)+"'> "+j+" </a></span>&nbsp;");
					}
					if(j==(PageIndex+2)&&j!=PageCount){
						sb.append("...&nbsp;");
					}else if(j==(PageCount-1)&&(PageIndex+1)==(PageCount-2)){
						sb.append("...&nbsp;");
					}
				}
				if(PageIndex>3&&PageCount<(PageIndex+2)){
					if(j>PageCount-5){					
						sb.append("<span class='plpage01'><a href='"+getNowPage(j)+"'>"+ j+"</a></span>&nbsp;");
					}
				}
				if(PageIndex<4){
					if(j<6){				
						sb.append("<span class='plpage01'><a href='"+getNowPage(j)+"'>"+ j+"</a></span>&nbsp;");
						if(j==5){						
							sb.append("...&nbsp;");
						}
					}
				}
				
			}
			//如果总页面小于5 则全部显示
			else if(PageCount<6){
				sb.append("<span class='plpage01'><a href='"+getNowPage(j)+"'>"+ j+"</a></span>&nbsp;");
			}
//				sb.append("<span class='plpage01' >" +
//						"<span class='plpage01'><a href='"+getNowPage(j)+"'>"+ j+"</span></span>");
			
		}
		
		sb.append("<span class='plpage01'><a href='"+getNextPage()+"'>></a></span>");
		sb.append("<span class='plpage02'><a href='"+getLastPage()+"'>尾页</a></span>");
		sb.append("&nbsp;&nbsp;共" + (Total%PageSize==0?Total/PageSize:(Total/PageSize+1)) + "页&nbsp;&nbsp;");
		sb.append("&nbsp;&nbsp;到第&nbsp;<input id='_PageBar_Index_"+id+"' type='text' size='4' style='width:30px' class='pageinput'");
		sb.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">&nbsp;页");

		String listNameRule = getFirstFileName().substring(0, getFirstFileName().lastIndexOf("."));
		String ext = ServletUtil.getUrlExtension(getFirstFileName());
		sb.append("&nbsp;&nbsp;<input type='button' class='btn300' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_"
				+id+"').value)){alert('错误的页码');$('_PageBar_Index_"+id+"').focus();}else if(document.getElementById('_PageBar_Index_"
				+id+"').value>" + PageCount + "){alert('错误的页码');document.getElementById('_PageBar_Index_"
				+id+"').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_"
				+id+"').value)>0?document.getElementById('_PageBar_Index_"
				+id+"').value:1;if(PageIndex==1){window.location='"+getFirstFileName()+"'}else{window.location='"+listNameRule+"_'+PageIndex+'"+ext+"';}}\" style='' value='确定'></td>");
		sb.append("</div>");
		sb.append("</div>");
//		sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
//		sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
//		sb.append("共" + Total + "条记录，每页" + PageSize + "条，当前第<span class='fc_ch1'>" + ((Total == 0) ? 0: (PageIndex + 1)) 
//				+ "</span>/<span class='fc_ch1'>" + PageCount + "</span>页&nbsp;&nbsp;&nbsp;&nbsp;");
//		if (PageIndex > 0) {
//		
//				
//			sb.append("<a href='.'><span class='fc_ch1'>第一页</span></a>|");
//			if(1==PageIndex){
//				sb.append("<a href='.'><span class='fc_ch1'>上一页</span></a>|");
//			}else{
//				sb.append("<a href='" + getPreviousPage() + "'><span class='fc_ch1'>上一页</span></a>|");
//			}
//			
//		} else {
//			sb.append("<span class='fc_hui2'>第一页</span>|");
//			sb.append("<span class='fc_hui2'>上一页</span>|");
//		}
//		if (PageIndex + 1 != PageCount && PageCount>0) {
//			sb.append("<a href='" + getNextPage() + "'><span class='fc_ch1'>下一页</span></a>|");
//			sb.append("<a href='" + getLastPage() + "'><span class='fc_ch1'>最末页</span></a>");
//		} else {
//			sb.append("<span class='fc_hui2'>下一页</span>|");
//			sb.append("<span class='fc_hui2'>最末页</span>");
//		}
//
		
		return sb.toString();
	}
    /**
     * 车险分页
     */
 public String getPageCarBar(int id) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("<div class='plpage'>");
		sb.append("<div class='plpagecont'>");
		sb.append("<span><a href='"+getNowPage(1)+"'>首页</a></span>");
		sb.append("<span><a href='"+getPreviousPage()+"'><</a></span>");
		int j = 1;  
		for( j = 1;j<=(Total%PageSize==0?Total/PageSize:(Total/PageSize+1));j++){    
			if(j==(PageIndex+1)){
				sb.append("<span><a class='on' href='"+getNowPage(j)+"'>"+j+"</a></span>&nbsp;");
			}
			//如果总页面大于5
			else if(PageCount>5){
				if(PageIndex>3 && PageCount>(PageIndex+1)){
					if(j>(PageIndex-3)&&j<(PageIndex+3)){
						sb.append("<span><a href='"+getNowPage(j)+"'> "+j+" </a></span>&nbsp;");
					}
					if(j==(PageIndex+2)&&j!=PageCount){
						sb.append("...&nbsp;");
					}else if(j==(PageCount-1)&&(PageIndex+1)==(PageCount-2)){
						sb.append("...&nbsp;");
					}
				}
				if(PageIndex>3&&PageCount<(PageIndex+2)){
					if(j>PageCount-5){					
						sb.append("<span><a href='"+getNowPage(j)+"'>"+ j+"</a></span>&nbsp;");
					}
				}
				if(PageIndex<4){
					if(j<6){				
						sb.append("<span><a href='"+getNowPage(j)+"'>"+ j+"</a></span>&nbsp;");
						if(j==5){						
							sb.append("...&nbsp;");
						}
					}
				}
				
			}
			//如果总页面小于5 则全部显示
			else if(PageCount<6){
				sb.append("<span><a href='"+getNowPage(j)+"'>"+ j+"</a></span>&nbsp;");
			}
			
		}
		
		sb.append("<span><a href='"+getNextPage()+"'>></a></span>");
		sb.append("<span><a href='"+getLastPage()+"'>尾页</a></span>");
		sb.append("&nbsp;&nbsp;共" + (Total%PageSize==0?Total/PageSize:(Total/PageSize+1)) + "页&nbsp;&nbsp;");
		sb.append("&nbsp;&nbsp;到第&nbsp;<input id='_PageBar_Index_"+id+"' type='text' size='4' style='width:30px' class='pageinput'");
		sb.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">&nbsp;页");

		String listNameRule = getFirstFileName().substring(0, getFirstFileName().lastIndexOf("."));
		String ext = ServletUtil.getUrlExtension(getFirstFileName());
		sb.append("&nbsp;&nbsp;<input type='button' class='btn300' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_"
				+id+"').value)){alert('错误的页码');$('_PageBar_Index_"+id+"').focus();}else if(document.getElementById('_PageBar_Index_"
				+id+"').value>" + PageCount + "){alert('错误的页码');document.getElementById('_PageBar_Index_"
				+id+"').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_"
				+id+"').value)>0?document.getElementById('_PageBar_Index_"
				+id+"').value:1;if(PageIndex==1){window.location='"+getFirstFileName()+"'}else{window.location='"+listNameRule+"_'+PageIndex+'"+ext+"';}}\" style='' value='确定'></td>");
		sb.append("</div>");
		sb.append("</div>");
		return sb.toString();
	}
	
	/**
	 * 文章分页
	 * @param id
	 * @return
	 */
	public String getPageBreakBar(int id) {
		StringBuffer sb = new StringBuffer();
		if(this.getTotal()>1){
			sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
			sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
			//sb.append("共 " + Total + " 页 ");
			if (PageIndex > 0) {
				sb.append("<a href='" + getPreviousPage() + "'><span class='fc_ch1'>上一页</span></a>&nbsp;&nbsp;");
			} else {
				sb.append("<span class='fc_hui2'>上一页</span>&nbsp;&nbsp;");
			}
			
			StringBuffer pageList = new StringBuffer();
			for (int i = 0; i < PageCount; i++) {
				String href = null;
				if(i==0){
					href = this.FirstFileName;
				}else{
					href = OtherFileName.replaceAll("@INDEX", String.valueOf(i + 1));
				}
				
				if(i==this.PageIndex){
					pageList.append(" "+(i + 1));
				}else{
					pageList.append("  <a href=");
					pageList.append(href);
					pageList.append(">" + (i + 1) + "</a>");
				}
			}
			
		    sb.append(pageList);

			if (PageIndex + 1 != PageCount) {
				sb.append("&nbsp;&nbsp;<a href='" + getNextPage() + "'><span class='fc_ch1'>下一页</span></a>&nbsp;");
			} else {
				sb.append("&nbsp;&nbsp;<span class='fc_hui2'>下一页</span>&nbsp;");
			}

			sb.append("&nbsp;&nbsp;</td>");
			sb.append("</tr></table>");
		}
		return sb.toString();
	}
	
	public String getPageBreakBarEx(int id) {
		StringBuffer sb = new StringBuffer();
		if(this.getTotal()>1){
			//sb.append("<table width='100%' border='1px' class='noBorder' align='center'><tr>");
			sb.append("<tr>");
			sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
			sb.append("<div style='float:left;'>");
			sb.append("<span><a class='linka' href=\""+getFirstPage()+"\">封面</a></span>");
			sb.append("<span><a class='linka' href=\""+getLastPage()+"\">封底</a></span>");
			sb.append("</div>");
			sb.append("<div style='float:right;'>");
			if (PageIndex > 0) {
				sb.append("<span><a class='linka' href=\""+getPreviousPage()+"\">上一页</a></span>");
			} else {
				sb.append("<span><a class='linka' href='#'>上一页</a></span>");
			}
			
			StringBuffer pageList = new StringBuffer();
			for (int i = 0; i < PageCount; i++) {
				String href = null;
				if(i==0){
					href = this.FirstFileName;
				}else{
					href = OtherFileName.replaceAll("@INDEX", String.valueOf(i + 1));
				}
				
				if(i==this.PageIndex){
					pageList.append("<span><a class='linka' href='#' style=''>"+(i + 1)+"</a></span>");
				}else{
//					pageList.append("  <a class='linka' href=");
//					pageList.append(href);
//					pageList.append(">" + (i + 1) + "</a>");
				}
			}
			
		    sb.append(pageList);
		    
			if (PageIndex + 1 != PageCount) {
				sb.append("<span><a class='linka' href='" + getNextPage() + "'>下一页</a></span>");
			} else {
				sb.append("<span><a class='linka' href='#'>下一页</a></span>");
			}
			sb.append("<span><a class='linka' href='#'>共 " + Total + " 页</a></span>");
			sb.append("&nbsp;<span><input id='_PageBar_Index_"+id+"' type='text' size='4' style='width:30px' ");
			sb.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\"></span>");
			String listNameRule = getFirstFileName().substring(0, getFirstFileName().lastIndexOf("."));
			String ext = ServletUtil.getUrlExtension(getFirstFileName());
			sb.append("&nbsp;<span><input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_"
					+id+"').value)){alert('错误的页码');$('_PageBar_Index_"+id+"').focus();}else if(document.getElementById('_PageBar_Index_"
					+id+"').value>" + PageCount + "){alert('错误的页码');document.getElementById('_PageBar_Index_"
					+id+"').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_"
					+id+"').value)>0?document.getElementById('_PageBar_Index_"
					+id+"').value:1;if(PageIndex==1){window.location='"+getFirstFileName()+"'}else{window.location='"+listNameRule+"_'+PageIndex+'"+ext+"';}}\" style='' value='跳转'></span></td>");
			
			
			sb.append("</div>");
			sb.append("&nbsp;&nbsp;</td>");
			sb.append("</tr>");
			//sb.append("</tr></table>");
		}
		return sb.toString();
	}

	public DataRow getPageRow() {
		StringBuffer pageList = new StringBuffer();
		for (int i = 0; i < PageCount; i++) {
			String href = null;
			if(i==0){
				href = this.FirstFileName;
			}else{
				href = OtherFileName.replaceAll("@INDEX", String.valueOf(i + 1));
			}
			
			if(i==this.PageIndex){
				pageList.append(" "+(i + 1));
			}else{
				pageList.append("  <a href=");
				pageList.append(href);
				pageList.append(">" + (i + 1) + "</a>");
			}
		}

		DataTable dataPage = new DataTable();
		String[] cols = { "Total", "PageCount", "PageSize", "FirstPage", "PrevPage", "NextPage", "LastPage", "PageIndex",
				"PageList" };
		String[] values = { Total + "", PageCount + "", PageSize + "", getFirstPage(), getPreviousPage(), getNextPage(),
				getLastPage(), (PageIndex + 1) + "", pageList.toString() };
		dataPage.insertColumns(cols);
		dataPage.insertRow(values);

		return dataPage.getDataRow(0);
	}

	public DataTable getListTable() {
		return ListTable;
	}

	public void setListTable(DataTable listTable) {
		ListTable = listTable;
	}
}

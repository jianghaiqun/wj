/**
 * 
 */
package com.sinosoft.search;

import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;

/**
 * @author wangcaiyun
 *
 */
public class ClaimsInfoSearch extends Ajax {
	
	/**
	 * 按保险公司下载
	 * @return
	 */
	public static DataTable comSearch() {
		DataTable dt = new QueryBuilder("select comCode, comClassifyName from ClaimsInfoCompanyInfo order by orderFlag asc").executeDataTable();
		return dt;
	}
	
	public static DataTable hotInfo() {
		String path = Config.getValue("StaticResourcePath");
		DataTable dt = new QueryBuilder("select name, CONCAT('"+path+"/',path,fileName) as Link from ClaimsInfo where isHot = 'Y' order by orderFlag asc").executeDataTable();
		return dt;
	}
	
	public static DataTable ClassityInfo(String comCode) {
		if (StringUtil.isEmpty(comCode)) {
			return null;
		}
		DataTable dt = new QueryBuilder("select id, name,comCode from ClaimsInfoCompanyClassify where comCode = ?  order by orderFlag asc", comCode).executeDataTable();
		return dt;
	}
	
	public static DataTable claimsInfo(String comCode, String claType, String query) {
		String path = Config.getValue("StaticResourcePath");
		QueryBuilder qb = new QueryBuilder("select name,CONCAT('"+path+"/',path,fileName) as Link from ClaimsInfo where 1=1 ");
		if (StringUtil.isNotEmpty(comCode)) {
			qb.append(" and comCode = ?", comCode);
		}
		if (StringUtil.isNotEmpty(claType)) {
			qb.append(" and find_in_set(?, classifyId) ", claType);
		}
		if (StringUtil.isNotEmpty(query)) {
			qb.append(" and name like '%"+query+"%' ");
		}
		qb.append(" order by orderFlag asc ");
		DataTable dt = qb.executeDataTable();
		User.setValue("claimsDT", dt);
		return dt;
	}
	
	public static String getClaimsInfo() {
		StringBuilder sb = new StringBuilder();
		String path = Config.getValue("StaticResourcePath");
		DataTable dt = new QueryBuilder("select a.comCode,b.name, CONCAT('"+path+"/',b.path,b.fileName) as Link, a.comName from ClaimsInfoCompanyInfo a, ClaimsInfo b where a.recommendFlag = 'Y' and a.comCode = b.comCode order by a.orderFlag asc, b.orderFlag asc ").executeDataTable();
		String whereSql = "";
		String url = Config.getServerContext()+"/Search/SearchClaimsInfo.jsp?comCode=";
		if (dt != null && dt.getRowCount() > 0) {
			int count = dt.getRowCount();
			String comCode = dt.getString(0, 0);
			whereSql = " where comCode not in ('" + comCode + "'";
			sb.append("<div class=\"clamis_zlbox\">");
			sb.append("<dl><dt><span class=\"icon_C");
			sb.append(comCode);
			sb.append(" clamis_logos\"><a href=\"");
			sb.append(url+comCode);
			sb.append("\"></a></span></dt><dd>");
			boolean flag = false;
			for (int i = 0; i < count; i++) {
				if (comCode.equals(dt.getString(i, 0))) {
					sb.append("<a href=\""+dt.getString(i, 2)+"\">"+dt.getString(i, 1)+"</a>");
				} else {
					if (flag) {
						break;
					}
					comCode = dt.getString(i, 0);
					whereSql += (",'" + comCode + "'");
					sb.append("</dd></dl>");
					sb.append("<dl><dt><span class=\"icon_C");
					sb.append(comCode);
					sb.append(" clamis_logos\"><a href=\"");
					sb.append(url+comCode);
					sb.append("\"></a></span></dt><dd>");
					sb.append("<a href=\""+dt.getString(i, 2)+"\">"+dt.getString(i, 1)+"</a>");
					flag = true;
				}
			}
			sb.append("</dd></dl></div>");
			whereSql += ") ";
		}
		dt = new QueryBuilder("select comCode,comClassifyName from ClaimsInfoCompanyInfo "+whereSql + "order by orderFlag asc limit 0, 7").executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			sb.append("<div class=\"clamis_gs_list\">");
			sb.append("<ul>");
			int count = dt.getRowCount();
			for (int i = 0; i < count; i++) {
				sb.append("<li><a href=\"");
				sb.append(url+dt.getString(i, 0));
				sb.append("\">");
				sb.append(dt.getString(i, 1));
				sb.append("</a></li>");
			}
			sb.append("<li><a href=\"");
			sb.append(Config.getServerContext()+"/Search/SearchClaimsInfo.jsp");
			sb.append("\" class=\"more_calamisgs\">更多</a></li>");
			sb.append("</ul></div>");
		}
		
		return sb.toString();
	}

	public static String getNewClamisCase() {
        StringBuilder sb = new StringBuilder();
        DataTable dt = new QueryBuilder("SELECT a.policyNo, " +
                "(SELECT c.productName FROM sdinformation c  WHERE c.orderSn = a.orderSn) productName," +
                "a.claimsItemsName,a.cycle,a.claimsMoney,b.ComplateDate FROM PaymentClaimsInfo a LEFT JOIN fmrisk b ON a.productId = b.RiskCode " +
                "WHERE a.isShowFlag = 'Y' AND a.status = '06' AND a.remark1 NOT IN ('b2b_app', 'b2b_html5') " +
                "ORDER BY a.applicationDate DESC").executeDataTable();

        if (dt != null && dt.getRowCount() > 0) {
            // 保单号
            String  policyNo;
            for (int i = 0; i < dt.getRowCount(); i++) {
                policyNo = dt.getString(i,"policyNo");
                if (StringUtil.isNotEmpty(policyNo)) {
                    policyNo = policyNo.substring(0,5) + "***";
                }
                sb.append("<li><span class=\"clamisNum\">");
                sb.append(policyNo);
                sb.append("</span><span class=\"clamisName \">");
				if ("1".equals(dt.getString(i,"ComplateDate"))) {
                	sb.append("<i class=\"clamistag\"></i>");
				}
                sb.append(dt.getString(i,"productName"));
                sb.append("</span><span class=\"clamisCass\">");
                sb.append(dt.getString(i,"claimsItemsName"));
                sb.append("</span><span class=\"clamisDay\">");
                if (StringUtil.isNotEmpty(dt.getString(i,"cycle"))) {
                    sb.append(dt.getString(i,"cycle") + "天");
                }
                sb.append("</span><span class=\"clamisMoney\">");
                if (StringUtil.isNotEmpty(dt.getString(i,"claimsMoney"))) {
                    sb.append("¥" + dt.getString(i,"claimsMoney"));
                }
                sb.append("</span></>");
            }
        }
        return sb.toString();
	}

	public void SearchClaims(){
		int pageindex = Request.getInt("pageIndex");
		if(pageindex!=0){
			pageindex = pageindex - 1;
		}
		int nextPage = pageindex + 2;
		int start = pageindex*15;
		
		DataTable dt = (DataTable)User.getValue("claimsDT");
		int total = 0;
		
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		String claimsInfo = "";
		String pageInfo = "";
		if (dt != null && dt.getRowCount() > 0) {
			total = dt.getRowCount();
			int pageCount = new Double(Math.ceil(total * 1.0 / 10)).intValue();
			
			for(int i=start;i<start+15&&i<dt.getRowCount();i++){
				sb.append("<li class=\"clearfix\"><span class=\"fl-l\">");
				sb.append(dt.get(i).getString("name")+"</span>");
				sb.append("<a class=\"fl-r\" href=\""+dt.get(i).getString("Link")+"\" ");
				sb.append("rel=\"nofollow\"><i class=\"claim-icon fl-l\"></i>点击下载</a></li>");
			}
			claimsInfo = sb.toString();
			int onePageCount = 10;
			// 分页
			sb2.append("<div id='pagination'><input type='hidden' id='listProductCount' value='"+total+"'/><ul>");
			
			if (pageindex > 0) {
				sb2.append("<li class='page_prev'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageindex + "\");'><span>上一页</span></a></li>");
				sb2.append("<li><a href='javascript:void(0)' onClick='doOrder2(\"" + 1 + "\");'><span>"+ 1+"</span></a></li>");
			} else {
				sb2.append("<li class='page_prev'><a href='javascript:void(0)' onClick='doOrder2(\"" + 1 + "\");'><span class='default'>上一页</span></a></li>");
				sb2.append("<li class='now'><a href='javascript:void(0)' onClick='doOrder2(\"" + 1 + "\");'><span>"+ 1+"</span></a></li>");
			}
			int j = 2;
			for (j = 2;j< (total % onePageCount == 0 ? total / onePageCount : (total / onePageCount + 1)); j++) {
				if(pageCount>6){
					if (pageindex >= pageCount - 4) {
						if (j >= pageCount - 3) {
							if (j == pageCount - 3) {
								sb2.append("<li class='omit'><span>...</span></li>");
							}
							if (j==(pageindex+1)) {
								sb2.append("<li class='now'>");
							} else {
								sb2.append("<li>");
							}
							sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><span>"+j+"</span></a></li>");
							
						}
					} else if(pageindex<3){
							if(j<5){
								if (j==(pageindex+1)) {
									sb2.append("<li class='now'>");
								} else {
									sb2.append("<li>");
								}
								sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><span>"+ j+"</span></a></li>");
								if(j==4){						
									sb2.append("<li class='omit'><span>...</span></li>");
								}
							}
					} else {
						if(pageindex>2 && pageCount>(pageindex+1)){
							if(j>(pageindex-1)&&j<(pageindex+3)){
								if (j==(pageindex+1)) {
									sb2.append("<li class='now'>");
								} else {
									sb2.append("<li>");
								}
								sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><span>"+j+"</span></a></li>");
							}
							if(j==(pageindex+2)&&j<pageCount-2){
								sb2.append("<li class='omit'><span>...</span></li>");
							}
						}
					}
				}else if(pageCount<7){
					if (j==(pageindex+1)) {
						sb2.append("<li class='now'>");
					} else {
						sb2.append("<li>");
					}
					sb2.append("<a href='javascript:void(0)' onClick='doOrder2(\"" + j + "\");'><span>"+ j+"</span></a></li>");
				}
			}

			if (pageindex + 1 == pageCount) {
				if (pageCount > 1) {
					sb2.append("<li class='now'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'><span>"+pageCount+"</span></a></li>");
				}
				sb2.append("<li class='page_next'><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'><span class='default'>下一页</span></a></li>");
			} else {
				sb2.append("<li><a href='javascript:void(0)' onClick='doOrder2(\"" + pageCount + "\");'><span>"+pageCount+"</span></a></li>");
				sb2.append("<li class='page_next'><a href='javascript:void(0)' onClick='doOrder2(\"" + nextPage + "\");'><span>下一页</span></a></li>");
			}
			
			sb2.append("</ul></div>");
			pageInfo = sb2.toString();
		}
		
		Response.setStatus(1);
		Response.put("claimsInfo",claimsInfo);
		Response.put("pageInfo",pageInfo);
	}
}

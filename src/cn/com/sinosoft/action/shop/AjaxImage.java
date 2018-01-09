package cn.com.sinosoft.action.shop;

import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.site.ImagePlayerBasic;
import com.sinosoft.cms.site.ImagePlayerRela;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.schema.ZCImagePlayerSchema;
import com.sinosoft.schema.ZCImagePlayerSet;

public class AjaxImage {

	private static String levelStr;
	private static final String SEPARATE = "|";

	/**
	 * 动态广告位.
	 * 
	 * @param name
	 *            代码名称,例如：bxbk_detail_right
	 * @param width
	 *            宽.
	 * @param height
	 *            高.
	 * @param count
	 *            展示数量.
	 * @param charwidth
	 *            字符宽度.
	 * @return
	 */
	public static String getImagePlayer(String name, String width,
			String height, String count, String charwidth) {
		String site = "221";
		String cunDate = DateUtil.getCurrentDateTime();
		if (StringUtil.isEmpty(charwidth) || "null".equalsIgnoreCase(charwidth)) {
			charwidth = "100";
		}
		if (StringUtil.isEmpty(width)) {
			width = "300";
		}
		if (StringUtil.isEmpty(height)) {
			width = "250";
		}
		StringBuffer sb = new StringBuffer();
		ZCImagePlayerSchema imagePlayer = new ZCImagePlayerSchema();
		imagePlayer.setName(name);
		imagePlayer.setSiteID(site);
		ZCImagePlayerSet set = imagePlayer.query();
		if (set.size() > 0) {
			imagePlayer = set.get(0);
		} else {
			imagePlayer = new ZCImagePlayerSchema();
			imagePlayer.setCode(name);
			imagePlayer.setSiteID(site);
			set = imagePlayer.query();
			if (set.size() > 0) {
				imagePlayer = set.get(0);
			} else {
				sb.append("没有" + name + "对应的图片播放器，请检查" + name + "是否正确。");
				return sb.toString();
			}
		}

		int imageCount = 6;
		if (StringUtil.isDigit(count)) {
			imageCount = Integer.parseInt(count);
		}

		StringBuffer pics = new StringBuffer();// 图片
		StringBuffer links = new StringBuffer();// 链接
		StringBuffer texts = new StringBuffer();// 显示文本
		DataTable dt = null;

		String type = imagePlayer.getRemark1();

		// 和栏目关联，取文章里面的图片
		if (ImagePlayerBasic.IMAGESOURCE_CATALOG_FIRST.equals(imagePlayer
				.getImageSource())) {
			String catalogStr = " and cataloginnercode like '%'";
			if (StringUtil.isNotEmpty(imagePlayer.getRelaCatalogInnerCode())
					&& !"null".equalsIgnoreCase(imagePlayer
							.getRelaCatalogInnerCode())) {
				catalogStr = " and cataloginnercode like '"
						+ imagePlayer.getRelaCatalogInnerCode() + "%'";
			}

			String statusStr = " and status in(" + Article.STATUS_TOPUBLISH
					+ "," + Article.STATUS_PUBLISHED + ") and (publishdate<='"
					+ DateUtil.getCurrentDateTime()
					+ "' or publishdate is null)";

			String attributeSql = " and attribute like '%image%'";
			String typeStr = " order by publishdate desc,orderflag desc, id desc";
			dt = new QueryBuilder("select * from zcarticle where siteID=?"
					+ catalogStr + statusStr + attributeSql + typeStr,
					imagePlayer.getSiteID()).executePagedDataTable(imageCount,
					0);
			dt.insertColumns(new String[] { "FirstImagePath" });
			PubFun.dealArticleMedia(dt, getServerContextPath(), "image");

			for (int i = 0; i < dt.getRowCount(); i++) {
				String imagePath = dt.getString(i, "FirstImagePath");
				if (StringUtil.isEmpty(imagePath)
						|| imagePath.toLowerCase().indexOf("nopicture.jpg") >= 0) {
					continue;// 没有图片不显示
				}
				if (i != 0) {
					pics.append(SEPARATE);
					links.append(SEPARATE);
					texts.append(SEPARATE);
				}
				imagePath = dt.getString(i, "FirstImagePath").substring(
						dt.getString(i, "FirstImagePath").indexOf("upload/"));
				pics.append(getServerContextPath() + imagePath);
				String siteUrl = SiteUtil.getURL(dt.getString(i, "SiteID"));
				if (siteUrl.endsWith("shtml")) {
					siteUrl = siteUrl.substring(0, siteUrl.lastIndexOf("/"));
				}

				if (!siteUrl.endsWith("/")) {
					siteUrl = siteUrl + "/";
				}

				links.append(siteUrl + PubFun.getDocURL(dt.getDataRow(i)));
				if (StringUtil.isNotEmpty(dt.getString(i, "ShortTitle"))) {
					texts.append(StringUtil.subStringEx(dt.getString(i,
							"ShortTitle"), Integer.parseInt(charwidth)));
				} else {
					texts.append(StringUtil
							.subStringEx(dt.getString(i, "Title"), Integer
									.parseInt(charwidth)));
				}
			}
		} else {
			String sql = "select b.* from ZCImageRela a,zcimage b where a.id = b.id  and a.RelaID="
					+ imagePlayer.getID()
					+ " and a.RelaType='"
					+ ImagePlayerRela.RELATYPE_IMAGEPLAYER
					+ "' order by a.orderflag desc, a.addtime desc";
			dt = new QueryBuilder(sql).executePagedDataTable(imageCount, 0);
			boolean flag = false;
			String endDateRecord = "";
			int countNo = 0; 
			for (int i = 0; i < dt.getRowCount(); i++) {
				
				if (i != 0) {
					if(flag){
						pics.append(SEPARATE);
						links.append(SEPARATE);
						texts.append(SEPARATE);
					}
				}
				flag = false;
					String startDate = dt.getString(i, "Prop3");
					String endDate = dt.getString(i, "Prop4");
					if(StringUtil.isNotEmpty(endDateRecord) && StringUtil.isNotEmpty(endDate)){
						countNo = DateUtil.compare(endDate, endDateRecord, "yyyy-MM-dd HH:mm:ss")==-1?countNo:i;
					}
							
					endDateRecord = dt.getString(i, "Prop4");
					if(!StringUtil.isEmpty(startDate)){
						if(DateUtil.compare(cunDate, startDate, "yyyy-MM-dd HH:mm:ss")==1){
							if(!StringUtil.isEmpty(endDate)){
								if(DateUtil.compare(cunDate, endDate, "yyyy-MM-dd HH:mm:ss")==-1){
									flag = true;
								}
							}else{
								flag = true;
							}
						}
					}else{
						flag = true;
					}
					if(i==dt.getRowCount()-1 && StringUtil.isEmpty(pics.toString().replace(SEPARATE, ""))){
						if (StringUtil.isEmpty(imagePlayer.getOriginalPicture())
								|| "N".equalsIgnoreCase(imagePlayer
										.getOriginalPicture())) {
							pics.append(Config.getValue("StaticResourcePath") +"/"+dt.getString(countNo, "path")
									+ "1_" + dt.getString(countNo, "FileName"));
						} else {
							pics.append(Config.getValue("StaticResourcePath") +"/"+ dt.getString(countNo, "path")
									+ "" + dt.getString(countNo, "SrcFileName"));
						}
						
						
						
						if (StringUtil.isNotEmpty(Config.getValue("StaticResourcePath")
								+ dt.getString(countNo, "LinkURL"))) {
							links.append(dt.getString(countNo, "LinkURL"));
						}
						if (StringUtil.isNotEmpty(dt.getString(countNo, "LinkText"))) {
							texts.append(StringUtil.subStringEx(dt.getString(countNo,
									"LinkText"), Integer.parseInt(charwidth)));
						}
					}
				if(flag){
					if (StringUtil.isEmpty(imagePlayer.getOriginalPicture())
							|| "N".equalsIgnoreCase(imagePlayer
									.getOriginalPicture())) {
						pics.append(Config.getValue("StaticResourcePath") +"/"+dt.getString(i, "path")
								+ "1_" + dt.getString(i, "FileName"));
					} else {
						pics.append(Config.getValue("StaticResourcePath") +"/"+ dt.getString(i, "path")
								+ "" + dt.getString(i, "SrcFileName"));
					}
					
					
					
					if (StringUtil.isNotEmpty(Config.getValue("StaticResourcePath")
							+ dt.getString(i, "LinkURL"))) {
						links.append(dt.getString(i, "LinkURL"));
					}
					if (StringUtil.isNotEmpty(dt.getString(i, "LinkText"))) {
						texts.append(StringUtil.subStringEx(dt.getString(i,
								"LinkText"), Integer.parseInt(charwidth)));
					}
				}
				
			}
		}

		int pWidth = 320, pHeight = 240;
		if (!StringUtil.isDigit(width)) {
			pWidth = (int) imagePlayer.getWidth();
		} else {
			pWidth = Integer.parseInt(width);
		}

		if (!StringUtil.isDigit(height)) {
			pHeight = (int) imagePlayer.getHeight();
		} else {
			pHeight = Integer.parseInt(height);
		}

		int imgHeight = pHeight;
		if ("Y".equals(imagePlayer.getIsShowText())) {
			imgHeight = pHeight - 22;
		}
		String showText = "";
		if ("Y".equals(imagePlayer.getIsShowText())) {
			showText = "&show_text=1&textheight=22";
		} else {
			showText = "&show_text=0";
		}

		if ("03".equals(type)) {
			String picsStr = pics.toString();
			String linksStr = links.toString();
			String textsStr = texts.toString();
			
			if(picsStr.endsWith(SEPARATE)){
				picsStr = picsStr.substring(0, picsStr.length()-1);
			}
			if(linksStr.endsWith(SEPARATE)){
				linksStr = linksStr.substring(0, linksStr.length()-1);
			}
			if(textsStr.endsWith(SEPARATE)){
				textsStr = textsStr.substring(0, textsStr.length()-1);
			}

			sb.append("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" width=\""
							+ pWidth + "\" height=\"" + pHeight + "\">");
			sb.append("<param name=\"movie\" value=\"" + getServerContextPath()
					+ "images/ImagePlayer.swf\">");
			sb.append("<param name=\"quality\" value=\"high\">");
			sb.append("<param name=\"wmode\" value=\"transparent\">");
			sb.append("<param name=\"FlashVars\" value=\"pics="
					+ picsStr + "&links=" + linksStr
					+ "&texts=" + textsStr + "&borderwidth=" + pWidth
					+ "&borderheight=" + imgHeight + showText
					+ "&overtxtcolor=FFFF00&txtcolor=FFFF00\"/>");
			sb
					.append("<embed src=\""
							+ getServerContextPath()
							+ "images/ImagePlayer.swf\" type=\"application/x-shockwave-flash\" wmode=\"transparent\" "
							+ "FlashVars=\"wmode=transparent&pics="
							+ picsStr + "&links=" + linksStr
							+ "&texts=" + textsStr + "&borderwidth="
							+ pWidth + "&borderheight=" + imgHeight + showText);

			sb
					.append("&button_pos=4&overtxtcolor=FFFF00&txtcolor=FFFF00\" width=\""
							+ pWidth + "\" height=\"" + pHeight + "\">");
			sb.append("</embed>");
			sb.append("</object>");

		} else if ("04".equals(type)) {
			String sql1 = "select * from zdconfig where Type=?";
			QueryBuilder qb1 = new QueryBuilder(sql1, "StaticResourcePath");
			DataTable dt1 = qb1.executeDataTable();
			sb.append("document.write('<div class=\"ad_jiaodian\">');\n");
			sb
					.append("document.write('<div class=\"changeBox_a1\" id=\"change_33\">');\n");
			String sql2 = "select * from ZCImagePlayer where ID="
					+ imagePlayer.getID() + "";
			QueryBuilder qb2 = new QueryBuilder(sql2);
			DataTable dt2 = qb2.executeDataTable();
			boolean flag1;
			boolean mark1 = false;
			String endDateRecord = "";
			int countNo = 0; 
			for (int i = 0; i < dt.getRowCount(); i++) {
				flag1 = false;
				if (i != 0) {
					pics.append(SEPARATE);
					links.append(SEPARATE);
					texts.append(SEPARATE);
				}
				String Alias = "/";
				String imgpath = "";
				String imglink = "";
					String startDate = dt.getString(i, "Prop3");
					String endDate = dt.getString(i, "Prop4");
					if(StringUtil.isNotEmpty(endDateRecord) && StringUtil.isNotEmpty(endDate)){
						countNo = DateUtil.compare(endDate, endDateRecord, "yyyy-MM-dd HH:mm:ss")==-1?countNo:i;
					}
					endDateRecord = dt.getString(i, "Prop4");
					if(!StringUtil.isEmpty(startDate)){
						if(DateUtil.compare(cunDate, startDate, "yyyy-MM-dd HH:mm:ss")==1){
							if(!StringUtil.isEmpty(endDate)){
								if(DateUtil.compare(cunDate, endDate, "yyyy-MM-dd HH:mm:ss")==-1){
									flag1 = true;
									mark1 = true;
								}
							}else{
								flag1 = true;
								mark1 = true;
							}
						}
					}else{
						flag1 = true;
						mark1 = true;
					}
					if(i==dt.getRowCount()-1 && !mark1){
						if (StringUtil.isEmpty(imagePlayer.getOriginalPicture())
								|| "N".equalsIgnoreCase(imagePlayer
										.getOriginalPicture())) {
							imgpath = dt1.getString(0, "Value") + Alias
									+ dt.getString(countNo, "path") + "1_"
									+ dt.getString(countNo, "FileName");
						} else {
							imgpath = dt1.getString(0, "Value") + Alias
									+ dt.getString(countNo, "path") + ""
									+ dt.getString(countNo, "SrcFileName");
						}
						if (StringUtil.isNotEmpty(Alias + dt.getString(countNo, "LinkURL"))) {
							imglink = dt.getString(countNo, "LinkURL");
						}
						sb.append("document.write('<div class=\"changeDiv\">');\n");
						String text = "";
						if ("Y".equals(imagePlayer.getIsShowText())) {
							text = dt.getString(countNo, "Info");
						}
						if("Y".equals(imagePlayer.getIsShowNofollow())){
							
							if(StringUtil.isNotEmpty(dt.getString(countNo, "Prop1"))){
								sb.append("document.write('<a href=\"###\"  onclick =\"getNowCpslink()\" rel=\"nofollow\" >');\n");
								sb.append("document.write('<input type=\"hidden\" id=\"comUrl\" value=\""+imglink+"\"/>');\n");
								sb.append("document.write('<input type=\"hidden\" id=\"comName\" value=\""+dt.getString(countNo, "Prop2")+"\"/>');\n");
								sb.append("document.write('<input type=\"hidden\" id=\"comGuoDu\" value=\""+dt.getString(countNo, "Prop1")+"\"/>');\n");
							}else{
								sb.append("document.write('<a href=\"" + imglink + "\"  rel=\"nofollow\" target=\"_blank\">');\n");
							}
						}else{
							if(StringUtil.isNotEmpty(dt.getString(countNo, "Prop1"))){
								sb.append("document.write('<a href=\"###\"  onclick =\"getNowCpslink()\">');\n");
								sb.append("document.write('<input type=\"hidden\" id=\"comUrl\" value=\""+imglink+"\"/>');\n");
								sb.append("document.write('<input type=\"hidden\" id=\"comName\" value=\""+dt.getString(countNo, "Prop2")+"\"/>');\n");
								sb.append("document.write('<input type=\"hidden\" id=\"comGuoDu\" value=\""+dt.getString(countNo, "Prop1")+"\"/>');\n");
							}else{
								sb.append("document.write('<a href=\"" + imglink + "\"  target=\"_blank\">');\n");
							}
							
						}
						sb.append("document.write('<img src=\"" + imgpath + "\" width=\"" + dt2.getString(0, "Width") + "\" height=\"" + dt2.getString(0, "Height") + "\" alt=\""+text+"\" /></a>');\n");
						sb.append("document.write('</div>');\n");
					}
				if(flag1){
					if (StringUtil.isEmpty(imagePlayer.getOriginalPicture())
							|| "N".equalsIgnoreCase(imagePlayer
									.getOriginalPicture())) {
						imgpath = dt1.getString(0, "Value") + Alias
								+ dt.getString(i, "path") + "1_"
								+ dt.getString(i, "FileName");
					} else {
						imgpath = dt1.getString(0, "Value") + Alias
								+ dt.getString(i, "path") + ""
								+ dt.getString(i, "SrcFileName");
					}
					if (StringUtil.isNotEmpty(Alias + dt.getString(i, "LinkURL"))) {
						imglink = dt.getString(i, "LinkURL");
					}
					sb.append("document.write('<div class=\"changeDiv\">');\n");
					String text = "";
					if ("Y".equals(imagePlayer.getIsShowText())) {
						text = dt.getString(i, "Info");
					}
					if("Y".equals(imagePlayer.getIsShowNofollow())){
						
						if(StringUtil.isNotEmpty(dt.getString(i, "Prop1"))){
							sb.append("document.write('<a href=\"###\"  onclick =\"getNowCpslink()\" rel=\"nofollow\" >');\n");
							sb.append("document.write('<input type=\"hidden\" id=\"comUrl\" value=\""+imglink+"\"/>');\n");
							sb.append("document.write('<input type=\"hidden\" id=\"comName\" value=\""+dt.getString(i, "Prop2")+"\"/>');\n");
							sb.append("document.write('<input type=\"hidden\" id=\"comGuoDu\" value=\""+dt.getString(i, "Prop1")+"\"/>');\n");
						}else{
							sb.append("document.write('<a href=\"" + imglink + "\"  rel=\"nofollow\" target=\"_blank\">');\n");
						}
					}else{
						if(StringUtil.isNotEmpty(dt.getString(i, "Prop1"))){
							sb.append("document.write('<a href=\"###\"  onclick =\"getNowCpslink()\">');\n");
							sb.append("document.write('<input type=\"hidden\" id=\"comUrl\" value=\""+imglink+"\"/>');\n");
							sb.append("document.write('<input type=\"hidden\" id=\"comName\" value=\""+dt.getString(i, "Prop2")+"\"/>');\n");
							sb.append("document.write('<input type=\"hidden\" id=\"comGuoDu\" value=\""+dt.getString(i, "Prop1")+"\"/>');\n");
						}else{
							sb.append("document.write('<a href=\"" + imglink + "\"  target=\"_blank\">');\n");
						}
						
					}
					sb.append("document.write('<img src=\"" + imgpath + "\" width=\"" + dt2.getString(0, "Width") + "\" height=\"" + dt2.getString(0, "Height") + "\" alt=\""+text+"\" /></a>');\n");
					sb.append("document.write('</div>');\n");
				}
				}
				
				
			if (dt.getRowCount() > 0) {
				sb.append("document.write('<ul class=\"ul_change_a2\">');\n");
				boolean flag2;
				int countName = 0;
				for (int i = 0; i < dt.getRowCount(); i++) {
					flag2 = false;
					if(i==dt.getRowCount()-1 && countName == 0){
						flag2 = true;
					}else{
						String startDate = dt.getString(i, "Prop3");
						String endDate = dt.getString(i, "Prop4");
						if(!StringUtil.isEmpty(startDate)){
							if(DateUtil.compare(cunDate, startDate, "yyyy-MM-dd HH:mm:ss")==1){
								if(!StringUtil.isEmpty(endDate)){
									if(DateUtil.compare(cunDate, endDate, "yyyy-MM-dd HH:mm:ss")==-1){
										flag2 = true;
									}
								}else{
									flag2 = true;
								}
							}
						}else{
							flag2 = true;
						}
					}
					if(flag2){
						countName++;
						sb.append("document.write('<li><span>&nbsp;</span></li>');\n");
					}
					
				}
				sb.append("document.write('</ul>');\n");
			}
			sb.append("document.write('</div>');\n");
			sb.append("document.write('</div>');\n");
		}

		return type + sb.toString();
	}

	/**
	 * 路径. FrontServerContextPath.
	 * 
	 * @return
	 */
	public static String getServerContextPath() {
		levelStr = Config.getFrontServerContextPath() + "/";
		return levelStr;
	}

}

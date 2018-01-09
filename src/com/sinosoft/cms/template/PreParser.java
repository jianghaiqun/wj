package com.sinosoft.cms.template;

import com.sinosoft.cms.dataservice.AdvertiseLayout;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCAdPositionSchema;
import com.sinosoft.schema.ZCImagePlayerSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 模板预处理 处理模板中的预定义标签，如广告位置定义等
 * 
 * @author lanjun 2009-4-16
 */
public class PreParser {
	private static final Logger logger = LoggerFactory.getLogger(PreParser.class);
	// 广告
	private static final Pattern cmsAD = Pattern.compile("<cms:ad\\s(.*?)(/>|>(.*?)</cms:ad>)",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private static final Pattern cmsImagePlayer = Pattern.compile(
			"<cms:imageplayer\\s(.*?)(/>|>(.*?)</cms:imageplayer>)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// 列表
	private static final Pattern cmsList = Pattern.compile("<cms:list\\s(.*?)>(.*?)</cms:List>",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private static final Pattern pAttr1 = Pattern.compile("\\s*?(\\w+?)\\s*?=\\s*?(\\\"|\\\')(.*?)\\2",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private static final Pattern pAttr2 = Pattern.compile("\\s*?(\\w+?)\\s*?=\\s*?([^\\\'\\\"\\s]+)",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private String templateFileName;

	private String content;

	private long siteID;

	public PreParser() {
	}

	public boolean parse() {
		content = FileUtil.readText(templateFileName);
		if (StringUtil.isEmpty(content)) {
			return true;
		}
		if (!parseAD() || !parseImagePlayer()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 解析list所引用的catalogID
	 * 
	 * @return
	 */
	public ArrayList parseList() {
		content = FileUtil.readText(templateFileName);
		ArrayList idList = new ArrayList();
		Matcher m = cmsList.matcher(content);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String item = ((String) map.get("item")).toLowerCase();

			if ("article".equalsIgnoreCase(item) || "image".equalsIgnoreCase(item) || "video".equalsIgnoreCase(item)
					|| "audio".equalsIgnoreCase(item) || "attachment".equalsIgnoreCase(item)
					|| "goods".equalsIgnoreCase(item)) {
				String catalog = (String) map.get("name");
				String parent = (String) map.get("parent");

				String catalogID = null;
				if (StringUtil.isNotEmpty(catalog)) {
					if (StringUtil.isDigit(catalog)) {
						catalogID = catalog;
					} else {
						if (StringUtil.isNotEmpty(parent)) {
							catalogID = CatalogUtil.getIDByName(siteID, parent, catalog);
						} else {
							try {
								if (catalog.indexOf(",") != -1) {
									catalogID = CatalogUtil.getIDsByName(siteID + "", catalog);
								} else if (catalog.indexOf("/") != -1) {
									catalogID = CatalogUtil.getIDByNames(siteID + "", catalog);
								} else {
									catalogID = CatalogUtil.getIDByName(siteID, catalog);
								}
							} catch (Exception e) {
								logger.error(e.getMessage(), e);
							}
						}
					}
				}

				if (StringUtil.isNotEmpty(catalogID)) {
					String[] ids = catalogID.split("\\,");
					for (int i = 0; i < ids.length; i++) {
						idList.add(ids[i]);
					}
				}
			}
		}

		return idList;
	}

	private boolean parseAD() {
		Matcher m = cmsAD.matcher(content);
		int lastEndIndex = 0;
		Transaction trans = new Transaction();
		while (m.find(lastEndIndex)) {
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String name = (String) map.get("name");
			String type = (String) map.get("type");
			String size = (String) map.get("size");
			String description = (String) map.get("description");

			ZCAdPositionSchema ad = new ZCAdPositionSchema();
			int NameCount = new QueryBuilder("select count(*) from zcadposition where PositionName = ? and siteid=?",
					name, siteID).executeInt();
			if (NameCount > 0) {
				continue;
			}
			ad.setID(NoUtil.getMaxID("AdPositionID"));
			ad.setCode(ad.getID() + "");
			if (User.getCurrent() != null && User.getUserName() != null) {
				ad.setAddUser("SYSTEM");
			} else {
				ad.setAddUser(User.getUserName());
			}
			ad.setAddTime(new Date());
			ad.setJsName(AdvertiseLayout.createJS("add", ad)); // 新增js

			ad.setSiteID(siteID);
			ad.setPositionName(name);
			ad.setDescription(description);
			ad.setPositionType(type);
			if (StringUtil.isNotEmpty(size)) {
				// 大小自定义

				String[] arr = size.split("\\*");
				ad.setPositionWidth(arr[0]);
				ad.setPositionHeight(arr[1]);
			}
			trans.add(ad, Transaction.INSERT);
		}

		if (trans.commit()) {
			return true;
		} else {
			return false;
		}
	}

	private boolean parseImagePlayer() {
		Matcher m = cmsImagePlayer.matcher(content);
		int lastEndIndex = 0;
		Transaction trans = new Transaction();
		while (m.find(lastEndIndex)) {
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String name = (String) map.get("name");
			if (StringUtil.isEmpty(name)) {
				name = (String) map.get("code");
			}
			String code = name;
			String width = (String) map.get("width");
			if (StringUtil.isEmpty(width)) {
				width = "100";
			}
			String height = (String) map.get("height");
			if (StringUtil.isEmpty(height)) {
				height = "100";
			}
			String count = (String) map.get("count");
			if (StringUtil.isEmpty(count)) {
				count = "5";
			}

			ZCImagePlayerSchema imagePlayer = new ZCImagePlayerSchema();
			int NameCount = new QueryBuilder("select count(*) from ZCImagePlayer where name = ? and siteid=?", name,
					siteID).executeInt();
			int CodeCount = new QueryBuilder("select count(*) from ZCImagePlayer where code = ? and siteid=?", code,
					siteID).executeInt();
			if (NameCount > 0 || CodeCount > 0) {
				continue;
			}
			imagePlayer.setID(NoUtil.getMaxID("ImagePlayerID"));
			imagePlayer.setCode(code);
			imagePlayer.setName(name);
			imagePlayer.setImageSource("0");
			imagePlayer.setIsShowText("N");
			if (User.getCurrent() != null && User.getUserName() != null) {
				imagePlayer.setAddUser("SYSTEM");
			} else {
				imagePlayer.setAddUser(User.getUserName());
			}
			imagePlayer.setAddTime(new Date());
			imagePlayer.setSiteID(siteID);
			imagePlayer.setDisplayType("0");
			imagePlayer.setWidth(width);
			imagePlayer.setHeight(height);
			imagePlayer.setDisplayCount(count);

			trans.add(imagePlayer, Transaction.INSERT);
		}

		if (trans.commit()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 转换一个形如 a="1" c="2" 的字符串为Mapx
	 */
	private static Mapx getAttrMap(String str) {
		Mapx map = new Mapx();
		Matcher m = pAttr1.matcher(str);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String value = m.group(3);
			if (value != null) {
				value = value.trim();
			}
			map.put(m.group(1).toLowerCase(), value);
			lastEndIndex = m.end();
		}

		m = pAttr2.matcher(str);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String value = m.group(2);
			if (value != null) {
				value = value.trim();
			}
			map.put(m.group(1).toLowerCase(), value);
			lastEndIndex = m.end();
		}
		return map;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public long getSiteID() {
		return siteID;
	}

	public void setSiteID(long siteID) {
		this.siteID = siteID;
	}
}

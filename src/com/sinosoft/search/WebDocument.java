package com.sinosoft.search;

import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class WebDocument {
	private static final Logger logger = LoggerFactory.getLogger(WebDocument.class);

	private String Url;

	private String RefUrl;

	private String charset;

	private boolean isTextContent;

	private Date lastmodifiedDate;

	private long lastDownloadTime;

	private int level;

	private transient DocumentList List;

	private transient byte[] Content;

	private String ErrorMessage;

	private Mapx form;

	private boolean isPageUrl;

	public String getErrorMessage() {
		return ErrorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		ErrorMessage = errorMessage;
	}

	public byte[] getContent() {
		if (Content == null) {
			if (List != null) {
				Content = List.getContent(this.Url);
			}
		}
		return Content;
	}

	public String getContentText() {
		try {
			if (charset == null) {
				charset = "utf-8";
			}
			byte[] bs = getContent();
			if (bs == null) {
				return null;
			}
			return new String(bs, charset);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Date getLastmodifiedDate() {
		return lastmodifiedDate;
	}

	public void setLastmodifiedDate(Date lastmodifiedDate) {
		this.lastmodifiedDate = lastmodifiedDate;
	}

	public String getRefUrl() {
		return RefUrl;
	}

	public void setRefUrl(String refUrl) {
		RefUrl = refUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setContent(byte[] content) {
		Content = content;
	}

	public long getLastDownloadTime() {
		return lastDownloadTime;
	}

	public void setLastDownloadTime(long lastDownloadTime) {
		this.lastDownloadTime = lastDownloadTime;
	}

	public boolean isTextContent() {
		return isTextContent;
	}

	public void setTextContent(boolean isTextContent) {
		this.isTextContent = isTextContent;
	}

	public DocumentList getList() {
		return List;
	}

	public void setList(DocumentList list) {
		this.List = list;
	}

	public Mapx getForm() {
		return form;
	}

	public void setForm(Mapx form) {
		this.form = form;
	}

	public boolean isPageUrl() {
		return isPageUrl;
	}

	public void setPageUrl(boolean isPageUrl) {
		this.isPageUrl = isPageUrl;
	}

	public byte[] toBytes() {
		Mapx map = new Mapx();
		map.put("Url", Url);
		map.put("RefUrl", RefUrl);
		map.put("charset", charset);
		map.put("isTextContent", new Boolean(isTextContent));
		map.put("lastmodifiedDate", lastmodifiedDate);
		map.put("lastDownloadTime", new Long(lastDownloadTime));
		map.put("level", new Integer(level));
		map.put("ErrorMessage", ErrorMessage);
		map.put("isPageUrl", new Boolean(isPageUrl));
		map.put("form", form);
		return FileUtil.mapToBytes(map);
	}

	public void parseBytes(byte[] bs) {
		Mapx map = FileUtil.bytesToMap(bs);
		Url = map.getString("Url");
		RefUrl = map.getString("RefUrl");
		charset = map.getString("charset");
		isTextContent = ((Boolean) map.get("isTextContent")).booleanValue();
		lastmodifiedDate = (Date) map.get("lastmodifiedDate");
		lastDownloadTime = ((Long) map.get("lastDownloadTime")).longValue();
		level = ((Integer) map.get("level")).intValue();
		ErrorMessage = map.getString("ErrorMessage");
		isPageUrl = ((Boolean) map.get("isPageUrl")).booleanValue();
		form = (Mapx) map.get("form");
	}
}

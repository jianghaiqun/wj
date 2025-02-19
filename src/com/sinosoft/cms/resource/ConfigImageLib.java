package com.sinosoft.cms.resource;

import com.sinosoft.framework.Constant;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConfigImageLib extends Page {

	public void save() {
		long siteID = Application.getCurrentSiteID();
		SAXReader sax = new SAXReader();
		Document doc = null;
		try {
			String configXML = new QueryBuilder("select ConfigXML from zcsite where id = ?", siteID).executeString();
			StringReader reader = new StringReader(configXML);
			doc = sax.read(reader);
		} catch (DocumentException e1) {
			Response.setLogInfo(0, "保存失败");
			logger.error(e1.getMessage(), e1);
			return;
		}
		Element root = doc.getRootElement();
		Element ImageLibConfig = root.element("ImageLibConfig");
		if (ImageLibConfig != null) {
			root.remove(ImageLibConfig);
		}
		ImageLibConfig = root.addElement("ImageLibConfig");
		ImageLibConfig.addComment("原图水印");
		Element WaterMark = ImageLibConfig.addElement("WaterMark").addAttribute("HasWaterMark",
				$V("HasWaterMark") == null ? "0" : $V("HasWaterMark")).addAttribute("WaterMarkType",
				$V("WaterMarkType")).addAttribute("Position", $V("Position"));
		WaterMark.addElement("Image").addAttribute("src", $V("Image"));
		WaterMark.addElement("Text").addAttribute("FontName", "宋体").addAttribute("FontSize", $V("FontSize"))
				.addAttribute("FontColor", $V("FontColor")).addText($V("Text"));

		ImageLibConfig.addComment("多缩略图");
		int count = Integer.parseInt($V("Count"));
		Element AbbrImages = ImageLibConfig.addElement("AbbrImages").addAttribute("Count", count + "");
		for (int i = 1; i <= count; i++) {
			String index = $V("AbbrImageIndex" + i);
			Element AbbrImage = AbbrImages.addElement("AbbrImage").addAttribute("ID", i + "").addAttribute(
					"HasAbbrImage", $V("HasAbbrImage" + index)).addAttribute("SizeType", $V("SizeType" + index))
					.addAttribute("Width", $V("Width" + index)).addAttribute("Height", $V("Height" + index));
			WaterMark = AbbrImage.addElement("WaterMark").addAttribute("HasWaterMark",
					$V("HasWaterMark" + index) == null ? "0" : $V("HasWaterMark" + index)).addAttribute(
					"WaterMarkType", $V("WaterMarkType" + index)).addAttribute("Position", $V("Position" + index));
			WaterMark.addElement("Image").addAttribute("src", $V("Image" + index));
			WaterMark.addElement("Text").addAttribute("FontName", "宋体")
					.addAttribute("FontSize", $V("FontSize" + index))
					.addAttribute("FontColor", $V("FontColor" + index)).addText($V("Text" + index));
		}

		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding(Constant.GlobalCharset);
			StringWriter sw = new StringWriter();
			XMLWriter writer = new XMLWriter(sw, format);
			writer.write(doc);
			writer.flush();
			writer.close();
			new QueryBuilder("update zcsite set ConfigXML=? where ID = ?", sw.toString(), siteID).executeNoQuery();
		} catch (IOException e) {
			Response.setLogInfo(0, "保存失败");
			logger.error(e.getMessage(), e);
			return;
		}
		imageLibConfigMap.remove(siteID + "");
		getImageLibConfig(siteID);
		Response.setLogInfo(1, "保存成功");
	}

	public void saveSeparate() {
		long siteID = Application.getCurrentSiteID();
		SAXReader sax = new SAXReader();
		Document doc = null;
		try {
			String configXML = new QueryBuilder("select ConfigXML from zcsite where id = ?", siteID).executeString();
			StringReader reader = new StringReader(configXML);
			doc = sax.read(reader);
		} catch (DocumentException e1) {
			Response.setLogInfo(0, "保存失败");
			logger.error(e1.getMessage(), e1);
			return;
		}
		Element root = doc.getRootElement();
		Element Separate = root.element("Separate");
		if (Separate != null) {
			root.remove(Separate);
		}

		// 增加分离部署设置
		Separate = root.addElement("Separate");
		Separate.addComment("媒体库分离部署");
		Element is = Separate.addElement("config");
		is.addAttribute("name", "ImageSeparate");
		is.addAttribute("flag", $V("ImageSeparateFlag"));
		if (StringUtil.isNotEmpty($V("ImageSeparateURLPrefix"))) {
			is.addText($V("ImageSeparateURLPrefix"));
		}

		is = Separate.addElement("config");
		is.addAttribute("name", "VideoSeparate");
		is.addAttribute("flag", $V("VideoSeparateFlag"));
		if (StringUtil.isNotEmpty($V("VideoSeparateURLPrefix"))) {
			is.addText($V("VideoSeparateURLPrefix"));
		}

		is = Separate.addElement("config");
		is.addAttribute("name", "AudioSeparate");
		is.addAttribute("flag", $V("AudioSeparateFlag"));
		if (StringUtil.isNotEmpty($V("AudioSeparateURLPrefix"))) {
			is.addText($V("AudioSeparateURLPrefix"));
		}

		is = Separate.addElement("config");
		is.addAttribute("name", "AttachmentSeparate");
		is.addAttribute("flag", $V("AttachmentSeparateFlag"));
		if (StringUtil.isNotEmpty($V("AttachmentSeparateURLPrefix"))) {
			is.addText($V("AttachmentSeparateURLPrefix"));
		}

		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding(Constant.GlobalCharset);
			StringWriter sw = new StringWriter();
			XMLWriter writer = new XMLWriter(sw, format);
			writer.write(doc);
			writer.flush();
			writer.close();
			new QueryBuilder("update zcsite set ConfigXML=? where ID = ?", sw.toString(), siteID).executeNoQuery();
		} catch (IOException e) {
			Response.setLogInfo(0, "保存失败");
			logger.error(e.getMessage(), e);
			return;
		}
		imageLibConfigMap.remove(siteID + "");
		getImageLibConfig(siteID);
		Response.setLogInfo(1, "保存成功");
	}

	public static String imageLibConfigDefault = "<?xml version=\"1.0\" encoding=\"" + Constant.GlobalCharset
			+ "\"?>\n" + "<config>\n" + "<!--图片库配置-->\n" + "<ImageLibConfig>\n" + "<!--原图水印-->\n"
			+ "<WaterMark HasWaterMark=\"0\" WaterMarkType=\"Image\" Position=\"9\">\n"
			+ "<Image src=\"upload/Image/WaterMarkImage.png\"/>\n"
			+ "<Text FontName=\"宋体\" FontSize=\"14\" FontColor=\"-256\">全维智码软件</Text>\n" + "</WaterMark>\n"
			+ "<!--多缩略图-->\n" + "<AbbrImages Count=\"1\">\n"
			+ "<AbbrImage ID=\"1\" HasAbbrImage=\"1\" SizeType=\"0\" Width=\"450\" Height=\"450\">\n"
			+ "<WaterMark HasWaterMark=\"0\" WaterMarkType=\"Image\" Position=\"9\">\n"
			+ "<Image src=\"upload/Image/WaterMarkImage1.png\"/>\n"
			+ "<Text FontName=\"宋体\" FontSize=\"9\" FontColor=\"-1\"></Text>\n" + "</WaterMark>\n" + "</AbbrImage>\n"
			+ "</AbbrImages>\n" + "</ImageLibConfig>\n" + "</config>\n";

	private static Map imageLibConfigMap = new HashMap();

	public static Mapx getImageLibConfig(long siteID) {
		return getImageLibConfig(String.valueOf(siteID));
	}

	public static Mapx getImageLibConfig(String siteID) {
		Mapx map = (Mapx) imageLibConfigMap.get(siteID);
		if (map != null && !map.equals("")) {
			return map;
		}

		map = new Mapx();
		SAXReader sax = new SAXReader();
		Document doc = null;
		try {
			String configXML = new QueryBuilder("select ConfigXML from zcsite where id = ?", Long.parseLong(siteID))
					.executeString();
			if (StringUtil.isEmpty(configXML)) {
				return null;
			}
			StringReader reader = new StringReader(configXML);
			doc = sax.read(reader);
		} catch (DocumentException e1) {
			logger.error(e1.getMessage(), e1);
			return null;
		}
		Element root = doc.getRootElement();
		Element ImageLibConfig = root.element("ImageLibConfig");

		Element WaterMark = ImageLibConfig.element("WaterMark");
		map.put("HasWaterMark", WaterMark.attributeValue("HasWaterMark"));
		map.put("WaterMarkType", WaterMark.attributeValue("WaterMarkType"));
		map.put("Position", WaterMark.attributeValue("Position"));
		Element Image = WaterMark.element("Image");
		map.put("Image", Image.attributeValue("src"));

		// 分离部署
		Element Separate = root.element("Separate");
		if (Separate == null || Separate.elements().size() == 0) {
			map.put("ImageSeparateFlag", "N");
			map.put("VideoSeparateFlag", "N");
			map.put("AudioSeparateFlag", "N");
			map.put("AttachmentSeparateFlag", "N");
		} else {
			List list = Separate.elements();
			for (int i = 0; i < list.size(); i++) {
				Element ele = (Element) list.get(i);
				String name = ele.attributeValue("name");
				String flag = ele.attributeValue("flag");
				String value = ele.getTextTrim();
				map.put(name + "Flag", StringUtil.isEmpty(flag) || flag.equals("N") ? "N" : "Y");
				map.put(name + "URLPrefix", value);
			}
		}

		Element Text = WaterMark.element("Text");
		map.put("Text", Text.getText());
		map.put("FontName", Text.attributeValue("FontName"));
		map.put("FontSize", Text.attributeValue("FontSize"));
		map.put("FontColor", Text.attributeValue("FontColor"));

		Element AbbrImages = ImageLibConfig.element("AbbrImages");
		map.put("Count", AbbrImages.attributeValue("Count"));

		List elements = AbbrImages.elements();
		Element AbbrImage = null;
		for (int i = 0; i < elements.size(); i++) {
			AbbrImage = (Element) elements.get(i);
			String ID = AbbrImage.attributeValue("ID");
			map.put("HasAbbrImage" + ID, AbbrImage.attributeValue("HasAbbrImage"));
			map.put("SizeType" + ID, AbbrImage.attributeValue("SizeType"));
			map.put("Width" + ID, AbbrImage.attributeValue("Width"));
			map.put("Height" + ID, AbbrImage.attributeValue("Height"));

			WaterMark = AbbrImage.element("WaterMark");
			map.put("HasWaterMark" + ID, WaterMark.attributeValue("HasWaterMark"));
			map.put("WaterMarkType" + ID, WaterMark.attributeValue("WaterMarkType"));
			map.put("Position" + ID, WaterMark.attributeValue("Position"));

			Image = WaterMark.element("Image");
			map.put("Image" + ID, Image.attributeValue("src"));

			Text = WaterMark.element("Text");
			map.put("Text" + ID, Text.getText());
			map.put("FontName" + ID, Text.attributeValue("FontName"));
			map.put("FontSize" + ID, Text.attributeValue("FontSize"));
			map.put("FontColor" + ID, Text.attributeValue("FontColor"));
		}
		imageLibConfigMap.put(siteID, map);
		return map;
	}

	public static Mapx initSeparate(Mapx map) {
		return getImageLibConfig(Application.getCurrentSiteID());
	}
}

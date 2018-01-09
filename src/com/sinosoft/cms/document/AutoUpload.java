package com.sinosoft.cms.document;

import com.sinosoft.cms.datachannel.Deploy;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.site.Catalog;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.ImageUtilEx;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCImageSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class AutoUpload {
	private static final Logger logger = LoggerFactory.getLogger(AutoUpload.class);

	public static ZCImageSchema dealImage(String path, String filename, Transaction trans) {
		String autoSaveLib = Config.getValue("AutoSaveImageLib");
		if (StringUtil.isEmpty(autoSaveLib)) {
			autoSaveLib = "默认图片";
		}
		String catalogID = new QueryBuilder("select ID from ZCCatalog where type=" + Catalog.IMAGELIB
				+ " and Name =?  and siteid=?", autoSaveLib, Application.getCurrentSiteID()).executeString();

		if (StringUtil.isEmpty(catalogID)) {
			catalogID = new QueryBuilder("select ID from ZCCatalog where type=" + Catalog.IMAGELIB + " and siteid=?",
					Application.getCurrentSiteID()).executeString();
		}

		boolean uploadFlag = true;
		String absolutePath = path +"defaultTemp/"+ filename;
		String ext = filename.substring(filename.lastIndexOf(".") + 1);
		filename = filename.substring(0, filename.lastIndexOf("."));
		long imageID = NoUtil.getMaxID("DocID");
		int random = NumberUtil.getRandomInt(10000);
		String newFileName = imageID + "" + random + "." + ext;
		new File(absolutePath).renameTo(new File(path +CatalogUtil.getPath(catalogID)+ newFileName));// 直接重命名

		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);

		ZCImageSchema image = new ZCImageSchema();
		image.setID(imageID);
		image.setCatalogID(catalogID);
		image.setCatalogInnerCode(catalog.getInnerCode());
		image.setName(filename);
		image.setOldName(filename);
		image.setSiteID(Application.getCurrentSiteID());
		image.setPath("upload/Image/" + CatalogUtil.getPath(catalogID));
		image.setFileName(newFileName);
		image.setSrcFileName(newFileName);
		image.setSuffix(ext);
		image.setCount(0);
		image.setWidth(0);
		image.setHeight(0);
		image.setHitCount(0);
		image.setStickTime(0);
		image.setAuthor("articleEditor");
		image.setSystem("CMS");
		image.setAddTime(new Date());
		image.setAddUser(User.getUserName());
		image.setOrderFlag(OrderUtil.getDefaultOrder());
		image.setStatus(Article.STATUS_TOPUBLISH);
		try {
			ImageUtilEx.afterUploadImage(image, path+CatalogUtil.getPath(catalogID));
			ArrayList imageList = ImageUtilEx.afterUploadImage(image, path+CatalogUtil.getPath(catalogID));
			Deploy d = new Deploy();
			d.addJobs(Application.getCurrentSiteID(), imageList, Deploy.OPERATION_COPY);
			uploadFlag = true;
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			uploadFlag = false;
		}
		if (uploadFlag) {
			trans.add(image, Transaction.INSERT);
			return image;
		} else {
			return new ZCImageSchema();
		}
	}
}

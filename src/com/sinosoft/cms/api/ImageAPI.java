package com.sinosoft.cms.api;

import com.sinosoft.cms.datachannel.Deploy;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.site.Catalog;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.User.UserData;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.platform.pub.ImageUtilEx;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCImageSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class ImageAPI implements APIInterface {

	private static final Logger logger = LoggerFactory.getLogger(ImageAPI.class);

	private boolean isCreateSchema = false; // 标志是否把schema的值都值上了

	private String ImageHtml;

	private ZCImageSchema image;

	public static void main(String[] args) {
		UserData u = new UserData();
		User.setCurrent(u);
		User.setUserName("admin");
		User.setBranchInnerCode("86");
		// Transaction trans = new Transaction();

		ImageAPI image = new ImageAPI();
		image.setFileName("i:/1.jpg"); // 设置上传的图片绝对路径
		image.setCatalogID(5725); // 设置上传的图片专辑
		String filePath = image.getImageHtml(); // 得到图片的路径给文章中用
		image.insert();

		image = new ImageAPI();
		image.setFileName("i:/2.jpg"); // 设置上传的图片绝对路径
		image.setCatalogID(6038); // 设置上传的图片专辑
		image.insert();
	}

	public ImageAPI() {
		this.image = new ZCImageSchema();
	}

	public ImageAPI(ZCImageSchema image) {
		this.image = image;
	}

	public void setFileName(String fileName) {
		image.setFileName(fileName);
	}

	public void setCatalogID(long catalogID) {
		image.setCatalogID(catalogID);
	}

	/**
	 * 返回一个图片路径，给文章中调用
	 * 
	 * @return
	 */
	public String getImageHtml() {
		if (!isCreateSchema) {
			createSchema();
		}
		return ImageHtml;
	}

	private void createSchema() {
		if (!isCreateSchema) {
			String fileName = image.getFileName();
			fileName = fileName.replaceAll("\\\\", "/");
			String name = fileName.substring(fileName.lastIndexOf("/") + 1,
					fileName.lastIndexOf("."));
			image.setFileName(name);
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

			long imageID = NoUtil.getMaxID("DocID");
			image.setID(imageID);
			image.setName(name);
			image.setOldName(image.getName());
			if (image.getCatalogID() == 0) {
				logger.error("必须CatalogID不能为空");
				return;
			}
			image.setSiteID(CatalogUtil.getSiteID(image.getCatalogID()));
			String innerCode = CatalogUtil.getInnerCode(image.getCatalogID());
			image.setCatalogInnerCode(innerCode);
			image.setPath("upload/Image/"
					+ CatalogUtil.getPath(image.getCatalogID()));
			image.setFileName(imageID + "" + NumberUtil.getRandomInt(10000)
					+ ".jpg");
			image.setSrcFileName(imageID + "" + NumberUtil.getRandomInt(10000)
					+ ".jpg");
			image.setSuffix(suffix);
			image.setWidth(0);
			image.setHeight(0);
			image.setCount(2);
			image.setOrderFlag(OrderUtil.getDefaultOrder());
			image.setAddTime(new Date());
			image.setAddUser("admin");
			ImageHtml = ".." + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(image.getSiteID()) + "/"
					+ image.getPath() + image.getSrcFileName();
			isCreateSchema = true;
			dealImageFile(fileName);
		}
	}

	public long insert() {
		Transaction trans = new Transaction();
		if (insert(trans) > 0) {
			if (trans.commit()) {
				return 1;
			} else
				return -1;
		} else {
			return -1;
		}
	}

	public long insert(Transaction trans) {
		if (!isCreateSchema) {
			createSchema();
		}
		trans.add(image, Transaction.INSERT);
		trans.add(new QueryBuilder(
				"update zccatalog set total = (select count(*) from zcimage where catalogID = zccatalog.ID) where type=? and id =?",
				Catalog.IMAGELIB, image.getID()));
		return 1;
	}

	private boolean dealImageFile(String srcFileName) {
		String dir = Config.getContextRealPath() + Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(image.getSiteID()) + "/" + image.getPath();
		dir = dir.replaceAll("//", "/");
		new File(dir).mkdirs();
		if (FileUtil.copy(srcFileName, dir + image.getSrcFileName())) {
			try {
				ArrayList imageList = ImageUtilEx.afterUploadImage(image, dir);
				Deploy d = new Deploy();
				d.addJobs(image.getSiteID(), imageList);
				return true;
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
				return false;
			}
		}
		return false;
	}

	public boolean delete() {
		return false;
	}

	public boolean setSchema(Schema schema) {
		this.image = (ZCImageSchema) schema;
		return false;
	}

	public ZCImageSchema getSchema() {
		return this.image;
	}

	public boolean update() {
		return false;
	}
}

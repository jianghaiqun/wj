package com.sinosoft.framework.utility;

import cn.com.sinosoft.action.shop.ImgTool;
import cn.com.sinosoft.entity.Member;
import com.sinosoft.cms.resource.uploader.OSSUploadFile;
import com.sinosoft.framework.data.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 联合登录--工具类
 *
 * @author sinosoft
 *
 */
public class UnionLoginUtil {
	private static final Logger logger = LoggerFactory.getLogger(UnionLoginUtil.class);

	public boolean checkBind(String cOpenId,String cComCode){

		QueryBuilder tQueryBuilder = new QueryBuilder();

		StringBuffer bSQL = new StringBuffer("");

		bSQL.append(" Select Count(1) from BindInfoForLogin Where OpenID=? And comCode=? AND ((kxbUserEmail IS NOT NULL AND kxbUserEmail <> '')OR (kxbUserPhone IS NOT NULL AND kxbUserPhone <> '')) AND( kxbUserEmail IN(SELECT email FROM member) OR kxbUserPhone IN(SELECT member.`mobileNO` FROM member))");
		tQueryBuilder.setSQL(bSQL.toString());
		tQueryBuilder.add(cOpenId);
		tQueryBuilder.add(cComCode);

		int tCount = Integer.parseInt(tQueryBuilder.executeOneValue().toString());
		if(tCount<=0){
			return false;
		}

		return true;
	}
	/**
	* 下载指定URL路径的图片
	* @param path
	 * @throws Exception
	*/
	public static String getImage(String path,Member member) throws Exception {
		if(StringUtil.isEmpty(path)){
			return "";
		}
		URL url = new URL(path);

		BufferedInputStream bis = null;

		HttpURLConnection urlconnection = null;

		urlconnection = (HttpURLConnection) url.openConnection();
		urlconnection.connect();
		String serverPath = findServerPath();
		String realPath = "memberImg" + File.separator + "lh"+File.separator+member.getId()+".png";
		String mkpath = serverPath + realPath;
		File f = new File(mkpath);
		if(f.exists()){
			f.delete();
		}
		File parent = f.getParentFile();
		if(!parent.exists()){
			parent.mkdirs();
		}
		if (urlconnection.getResponseCode() == 200) {
			bis = new BufferedInputStream(urlconnection.getInputStream());
			File file = new File(mkpath);
			FileOutputStream outStream = new FileOutputStream(file);
			int len = -1;
			byte[] b = new byte[2048];
			while((len = bis.read(b)) != -1){
				outStream.write(b, 0, len);
			}
			outStream.flush();
			outStream.close();
			bis.close();
            OSSUploadFile.uploadFile(mkpath);
		}
		return realPath;
	}
	public static String findServerPath() {
		String path = null;
		try {
			String folderPath = ImgTool.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			if (folderPath.indexOf("WEB-INF") > 0) {
				path = folderPath.substring(0, folderPath
						.indexOf("WEB-INF/classes"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return path;
	}

}

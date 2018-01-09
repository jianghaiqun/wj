package com.sinosoft.email;

import java.io.File;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Part;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.ZipUtil;

/**
 * 邮件解析
 *
 */
public abstract class ParseMail {

	/**
	 * 获取保单号
	 * 
	 * <b>@param title 标题
	 * 
	 * <b>@param attachName 附件
	 * 
	 * <b>@param mailBody 正文
	 * 
	 * <b>@param company 保险公司
	 * 
	 * @return 保单号
	 */
	public abstract String dealPolicyno(String title, String attachName, String mailBody, String company) throws Exception;

	/**
	 * 解析附件,根据不同保险公司的附件类型去判断是否需要特殊解析操作
	 * 
	 * <b>@param part 邮件主体
	 * 
	 * <b>@param filepath 最终文件保存路径
	 * 
	 * <b>@param attachName 附件名称
	 * 
	 * <b>@param company 保险公司编码
	 * 
	 * <b>@param policyno 保单号
	 * 
	 * @return true 成功 / false 失败
	 */
	public boolean dealAttach(Part part, String filepath, String attachName, String company, String policyno) throws Exception {
		if (StringUtil.isEmpty(attachName)) {
			throw new Exception("附件名称为空.");
		}

		if (!new File(filepath).exists()) {
			new File(new File(filepath).getParent()).mkdirs();

		}

		// 根据附件后缀名进行解析
		if (attachName.toLowerCase().endsWith(".pdf")) {
			saveAttachMent(part, filepath);
			return true;

		} else if (attachName.toLowerCase().endsWith(".zip") || attachName.toLowerCase().endsWith(".rar")) {
			// 解压文件默认，默认里面存在一个文件，如果是多个，只取第一个，

			// 文件缓存目录
			String newPolicyPath = Config.getValue("newPolicyPath") + File.separatorChar + "temp";
			if (!new File(newPolicyPath).exists())
				new File(newPolicyPath).mkdirs();

			String attachpath = newPolicyPath + File.separatorChar + attachName;
			// 附件存放临时位置
			saveAttachMent(part, attachpath);

			// 附件解压
			String sourcepath = newPolicyPath + File.separatorChar + company + File.separatorChar + policyno;
			boolean unzipflag = ZipUtil.unzip(attachpath, sourcepath);
			if (!unzipflag) {
				throw new Exception("文件解压失败.");
			}

			// 获取解压后的文件，读取第一个
			List<String> list = FileUtil.readfile(sourcepath);
			if (list == null || list.size() == 0) {
				throw new Exception("文件解压后,内容为空.");
			}
			// 文件复制并删除
			boolean moveflag = FileUtil.move(list.get(0), filepath);

			// 删除解压的文件夹
			FileUtil.delete(sourcepath);

			// 删除压缩文件
			FileUtil.delete(attachpath);

			return moveflag;
		} else {
			throw new Exception("文件类型不存在.");
		}

	}

	/**
	 * 邮件附件保存
	 * 
	 * <b>@param part 邮件主体<br>
	 * <b>@param filepath 保存路径<br>
	 * <b>@param attachName 附件名称<br>
	 * <b>@param company 保险公司编码<br>
	 * 
	 * @throws Exception
	 */
	private void saveAttachMent(Part part, String filepath) throws Exception {
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mpart = mp.getBodyPart(i);
				String disposition = mpart.getDisposition();
				if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
					FileUtil.saveFile(filepath, mpart.getInputStream());

				} else if (mpart.isMimeType("multipart/*")) {
					saveAttachMent(mpart, filepath);

				} else {
					FileUtil.saveFile(filepath, mpart.getInputStream());
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			saveAttachMent((Part) part.getContent(), filepath);

		}

	}

}

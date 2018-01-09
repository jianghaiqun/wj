package com.sinosoft.email;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDParseMailLogSchema;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.tenpay.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class AttachMail {
	private static final Logger logger = LoggerFactory.getLogger(AttachMail.class);

	IMAPFolder folder = null;
	Store store = null;

	public static void main(String[] args) {
		AttachMail am = new AttachMail();
		am.fetchMail();

	}

	/**
	 * 邮箱主方法
	 * 
	 * @param username
	 * @param password
	 * @param path
	 */
	public void fetchMail() {
		try {

			init();

			if (folder == null) {
				return;
			}

			// 获取邮件信息
			Message[] messages = folder.getMessages();

			for (Message message : messages) {

				try {
					// 判断是否存在附件
					boolean isAttach = isContainAttach(message);

					// 如果不存在附件，直接返回
					if (!isAttach) {
						// 删除邮件
						message.setFlag(Flags.Flag.DELETED, true);
						continue;
					}

					// 读取邮件正文
					String mailBody = "";
					Object content = message.getContent();
					if (content instanceof MimeMultipart) {
						MimeMultipart multipart = (MimeMultipart) content;
						mailBody = parseMultipart(multipart);
					}
					// 读取发件人
					String from = getFrom((InternetAddress[]) message.getFrom());
					// 标题
					String title = message.getSubject();

					// 根据邮件内容区分保险公司
					String company = getCompany(from, title, mailBody);
					if (StringUtil.isEmpty(company)) {
						throw new Exception("邮件存在附件,但是无法解析.邮件标题==>" + title + " 发件人==>" + from);

					}
					// 附件名称
					String attachName = getAttachName(message);

					// 获取保单号
					String policyno = getPolicyno(title, attachName, mailBody, company);

					// 根据保单号，获取保存路径
					String filepath = getSavePath(policyno, company);
					if (StringUtil.isEmpty(filepath))
						throw new Exception("附件保存路径为空. 保单号==>" + policyno + " 邮件标题==>" + title);

					// 根据保险公司，保存不同附件类型
					saveAttach(message, filepath, attachName, company, policyno);

					// 删除邮件
					message.setFlag(Flags.Flag.DELETED, true);

					// System.out.println("========================================================");
					// System.out.println("发送时间：" +
					// DateUtil.toString(message.getSentDate(),
					// DateUtil.Format_DateTime));
					// System.out.println("主题：" + title);
					// System.out.println("是否包含附件：" + isAttach);
					// System.out.println("附件名称：" + attachName);
					// System.out.println("保存路径：" + filepath);
					// System.out.println("保单号：" + policyno);
					// System.out.println("发件人： " + from);
					// System.out.println("保险公司：" + company);
					// System.out.println("========================================================");

					Transaction tran = new Transaction();
					SDParseMailLogSchema tMailLog = new SDParseMailLogSchema();
					tMailLog.setID(NoUtil.getMaxID("tSDParseMailLog"));
					tMailLog.setCreateDate(new Date());
					tMailLog.setSendDate(message.getSentDate());
					tMailLog.setMailFrom(from);
					tMailLog.setTitle(title);
					tMailLog.setPolicyNo(policyno);
					tMailLog.setCompany(company);
					tMailLog.setFilePath(filepath);

					tran.add(new QueryBuilder("update sdinformationrisktype set electronicpath=? where policyNo=? ", filepath, policyno));
					tran.add(tMailLog, Transaction.INSERT);
					tran.commit();

				} catch (Exception e) {
					logger.error("邮件解析异常==>" + e.getMessage(), e);

				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			try {
				// 释放资源,并且提交处理请求
				if (folder != null)
					folder.close(true);

				if (store != null)
					store.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 根据保单号，获取保存路径
	 * 
	 * <b>@param policyno 保单号 </b><br/>
	 * <b>@param company 保险公司 </b><br/>
	 * <b>@return 保单路径</b>
	 */
	private String getSavePath(String policyno, String company) throws Exception {

		// 查询保单现有路径
		String sql = "select createDate,insureDate,riskCode,electronicpath from sdinformationrisktype where policyNo=? ";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(policyno);
		DataTable dt = qb.executeDataTable();

		if (dt == null || dt.getRowCount() != 1) {
			logger.error("邮件解析获取保单信息异常.保单号不存在.policyno==>{}", policyno);
			throw new Exception("邮件解析获取保单信息异常.保单号不存在.policyno==>" + policyno);
		}

		String riskcode = dt.get(0).getString("riskCode");
		String createDate = dt.get(0).getString("createDate");
		String insureDate = dt.get(0).getString("insureDate");
		String electronicpath = dt.get(0).getString("electronicpath");

		if (StringUtil.isEmpty(riskcode) || !riskcode.startsWith(company)) {
			logger.error("邮件解析获取保单信息异常.保险公司信息比对失败.policyno==>{} 解析邮件company==>{}", policyno, company);
			throw new Exception("邮件解析获取保单信息异常.保险公司信息比对失败.policyno==>" + policyno + " 解析邮件company==>" + company);
		}

		// 如果路径存在，则直接覆盖，如果路径不存在则生成
		if (StringUtil.isNotEmpty(electronicpath))
			return electronicpath;

		String insure_year = "";
		String insure_month = "";

		// /保险公司编码/承保日年/承保日月/保单
		if (StringUtil.isNotEmpty(insureDate)) {
			insure_year = DateUtil.getYear(insureDate);
			insure_month = DateUtil.getMonth(insureDate);

		} else {
			insure_year = DateUtil.getYear(createDate);
			insure_month = DateUtil.getMonth(createDate);
		}

		String newPolicyPath = Config.getValue("newPolicyPath") + File.separatorChar + company + File.separatorChar + insure_year + File.separatorChar + insure_month;

		// 判断路径是否存在，如果不存在则创建
		if (!new File(newPolicyPath).exists()) {
			new File(newPolicyPath).mkdirs();
		}

		String filename = MD5Util.MD5Encode(policyno, Constant.GlobalCharset) + ".pdf";

		return newPolicyPath + File.separatorChar + filename;
	}

	/**
	 * 获取保单号
	 * 
	 * <b>@param title 标题</b><br/>
	 * <b>@param attachName 附件 </b><br/>
	 * <b>@param mailBody 正文 </b><br/>
	 * <b>@param company 保险公司 </b><br/>
	 * <b>@return</b>
	 */
	private String getPolicyno(String title, String attachName, String mailBody, String company) throws Exception {
		try {
			String className = "com.sinosoft.email.ParseMail" + company;
			Class<?> mailParse = Class.forName(className);
			ParseMail mParse = (ParseMail) mailParse.newInstance();
			return mParse.dealPolicyno(title, attachName, mailBody, company);

		} catch (Exception e) {
			logger.error("解析保单号异常.原因：" + e.getMessage() + " 保险公司编码：" + company, e);
			throw new Exception("解析保单号异常.原因：" + e.getMessage() + " 保险公司编码：" + company + " 邮件标题：" + title);
		}

	}

	/**
	 * 判断邮件所属保险公司 太平、安盛、人保使用
	 * 
	 * @param from
	 * @param title
	 * @param content
	 * @return
	 * @throws Exception
	 */
	private String getCompany(String from, String title, String content) throws Exception {
		// 发件人->标题->正文
		String sql = "select CodeValue,CodeName,prop1,prop2 from zdcode  where ParentCode='AttachCompany'";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String company = dt.get(i).getString("CodeValue");

			String from_validate = dt.get(i).getString("CodeName");
			String title_validate = dt.get(i).getString("prop1");
			String content_validate = dt.get(i).getString("prop2");

			if (StringUtil.isNotEmpty(from) && from.indexOf(from_validate) != -1)
				return company;

			else if (StringUtil.isNotEmpty(title) && title.indexOf(title_validate) != -1)
				return company;

			else if (StringUtil.isNotEmpty(content) && content.indexOf(content_validate) != -1)
				return company;

		}

		return "";
	}

	/**
	 * 获得发件人的地址和姓名
	 */
	public String getFrom(InternetAddress address[]) throws Exception {
		String from = address[0].getAddress();
		// if (from == null)
		// from = "";
		// String personal = address[0].getPersonal();
		// if (personal == null)
		// personal = "";
		// String fromaddr = personal + "<" + from + ">";
		// return fromaddr;
		return from;
	}

	/**
	 * 邮箱初始化登录
	 * 
	 * @param username
	 * @param password
	 */
	private void init() {
		try {
			Properties props = System.getProperties();
			props.setProperty("mail.store.protocol", "imap");
			props.setProperty("mail.imap.host", "imap.kaixinbao.com");
			props.setProperty("mail.imap.port", "143");

			Session session = Session.getInstance(props, null);
			// 使用imap会话机制，连接服务器
			store = (IMAPStore) session.getStore("imap");
			store.connect(Config.getValue("PolicyMailUserName"), Config.getValue("PolicyMailPassword"));

			// 收件箱
			folder = (IMAPFolder) store.getFolder("INBOX");
			if (!folder.isOpen())
				folder.open(Folder.READ_WRITE);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return;
		}
	}

	/**
	 * 判断一封邮件是否含有附件<br>
	 * 首先要满足:msg的mimetype是multipart/*类型的,然后要有某bodyPart的disposition是attachment
	 */
	private boolean isContainAttach(Part part) throws Exception {
		try {
			boolean attachflag = false;
			// String contentType = part.getContentType();
			if (part.isMimeType("multipart/*")) {
				Multipart mp = (Multipart) part.getContent();
				for (int i = 0; i < mp.getCount(); i++) {
					BodyPart mpart = mp.getBodyPart(i);
					String disposition = mpart.getDisposition();
					if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE))))
						attachflag = true;
					else if (mpart.isMimeType("multipart/*")) {
						attachflag = isContainAttach((Part) mpart);
					} else {
						String contype = mpart.getContentType();
						if (contype.toLowerCase().indexOf("application") != -1)
							attachflag = true;
						if (contype.toLowerCase().indexOf("name") != -1)
							attachflag = true;
					}
				}
			} else if (part.isMimeType("message/rfc822")) {
				attachflag = isContainAttach((Part) part.getContent());
			}

			return attachflag;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("判断此邮件是否包含附件异常.");
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
	private void saveAttach(Part part, String filepath, String attachName, String company, String policyno) throws Exception {
		try {
			// 保存附件成功后，根据不同的保险公司类型处理附件
			String className = "com.sinosoft.email.ParseMail" + company;
			Class<?> mailParse = Class.forName(className);
			ParseMail mParse = (ParseMail) mailParse.newInstance();
			boolean saveflag = mParse.dealAttach(part, filepath, attachName, company, policyno);
			if (!saveflag) {
				throw new Exception("邮件附件保存失败.");

			}
		} catch (Exception e) {
			logger.error("保存路径为：" + filepath + " 附件名称：" + attachName + " 保险公司：" + company + e.getMessage(), e);
			throw new Exception("邮件附件保存异常.具体原因：" + e.getMessage());

		}

	}

	/**
	 * 对复杂邮件的解析
	 * 
	 * @param multipart
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String parseMultipart(Multipart multipart) throws MessagingException, IOException {
		int count = multipart.getCount();
		StringBuffer sb = new StringBuffer();
		for (int idx = 0; idx < count; idx++) {
			BodyPart bodyPart = multipart.getBodyPart(idx);
			if (bodyPart.isMimeType("text/plain")) {
				sb.append(bodyPart.getContent());

			} else if (bodyPart.isMimeType("text/html")) {
				sb.append(bodyPart.getContent());

			} else if (bodyPart.isMimeType("multipart/*")) {
				sb.append(parseMultipart((Multipart) bodyPart.getContent()));

			} else if (bodyPart.isMimeType("application/octet-stream")) {
				sb.append(bodyPart.getDisposition());

			}
		}
		return sb.toString();
	}

	/**
	 * 获取附件名称
	 */
	public String getAttachName(Part part) throws Exception {
		try {
			String fileName = "";
			if (part.isMimeType("multipart/*")) {
				Multipart mp = (Multipart) part.getContent();
				for (int i = 0; i < mp.getCount(); i++) {
					BodyPart mpart = mp.getBodyPart(i);
					String disposition = mpart.getDisposition();
					if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
						fileName = mpart.getFileName();
						if (fileName.toLowerCase().indexOf("gb2312") != -1) {
							fileName = MimeUtility.decodeText(fileName);
						}

						if (StringUtil.isNotEmpty(fileName))
							return fileName;

					} else if (mpart.isMimeType("multipart/*")) {
						getAttachName(mpart);

					} else {
						fileName = mpart.getFileName();
						if ((fileName != null) && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
							fileName = MimeUtility.decodeText(fileName);
						}
						
						if (StringUtil.isNotEmpty(fileName))
							return fileName;
					}
				}
			} else if (part.isMimeType("message/rfc822")) {
				getAttachName((Part) part.getContent());
			}

			return "";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("获取附件名称异常.异常信息" + e.getMessage());
		}
	}

}

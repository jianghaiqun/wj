package com.sinosoft.member;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.memberSchema;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ImportFile extends Page {

	// **************读取excel文件
	public void getexcel() {
		String fileaddress = $V("fileaddress");  
		String ip = $V("ip");
		// System.out.println(fileaddress+"************************************");
		FileInputStream finput = null;
		try {
			finput = new FileInputStream(fileaddress);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			Response.setError("导入失败!请选择正确模板");
		}
		// 设定FileINputStream读取Excel档
		POIFSFileSystem fs = null;
		try {
			fs = new POIFSFileSystem(finput);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			Response.setError("导入失败!请选择正确模板");
		}
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			Response.setError("导入失败!请选择正确模板");
		}
		HSSFSheet sheet = wb.getSheetAt(0);
		// 读取第一个工作表，宣告其为sheet
		try {
			finput.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			Response.setError("导入失败!请选择正确模板");
		}
		HSSFRow row = null;
		// 宣告一列
//		HSSFCell cell = null;
		// 宣告一个储存格
		short i = 0;
//		short y = 0;

		// 以巢状迴圈读取所有储存格资料
		for (i = 1; i <= sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			// cell = row.getCell(y);

			// 判断储存格的格式
			String email = "";
			String mobileNO = "";
			String telephoneNO = "";
			String faxNO = "";
			String IDType = "";
			String IDNO = "";

			String QQNO = "";
			String username = "";
			String realName = "";
			String sex = "";
			String birthday = "";
			int point = 0;

			int usedpoint = 0;
			int currentValidatePoint = 0;
			String expiricalValue = "";
			String isAccountLocked = "";
			String isAccountEnabled = "";
			String isEmailBinding = "";
			String isMobileNOBinding = "";
			String password = "";

			try {
				email = row.getCell(0).getStringCellValue();
				if(!this.checkEmail(email)){
					Response.setMessage("第"+i+"行邮件已存在，请修改！");
					return;
				}
				// mobileNO =
				// String.valueOf(row.getCell(1).getNumericCellValue());
				mobileNO = String.valueOf(row.getCell(1).getStringCellValue());
				if(!this.checkMobile(mobileNO)){
					Response.setMessage("第"+i+"行手机号已存在，请修改！");
					return;
				}
				telephoneNO = String.valueOf(row.getCell(2).getStringCellValue());
				faxNO = String.valueOf(row.getCell(3).getStringCellValue());
				IDType = row.getCell(4).getStringCellValue();
				IDNO = String.valueOf(row.getCell(5).getStringCellValue());

				QQNO = String.valueOf(row.getCell(6).getStringCellValue());
				username = row.getCell(7).getStringCellValue();
				if(!this.checkName(username)){
					Response.setMessage("第"+i+"行用户名已存在，请修改！");
					return;
				}
				realName = row.getCell(8).getStringCellValue();

				sex = row.getCell(9).getStringCellValue();
				birthday = row.getCell(10).getStringCellValue();

				point = Integer.parseInt(row.getCell(11).getStringCellValue());
				// /////////////////////////yiyongjifen
				usedpoint = Integer.parseInt(row.getCell(12).getStringCellValue());
				// //////////////////////////dangqiankeyongjifan
				currentValidatePoint = Integer.parseInt(row.getCell(13).getStringCellValue());
				expiricalValue = row.getCell(14).getStringCellValue();
				isAccountEnabled = row.getCell(15).getStringCellValue();
				isAccountLocked = row.getCell(16).getStringCellValue();
				isEmailBinding = row.getCell(17).getStringCellValue();
				isMobileNOBinding = row.getCell(18).getStringCellValue();
			} catch (Exception e) {
				Response.setError("导入失败!请选择正确模板");
				logger.error(e.getMessage(), e);
			}

			// String s = String.valueOf( value);
			// String score=String.valueOf(row.getCell((short)
			// 5).getNumericCellValue());
			// System.out.println(score);
			try {
				if (StringUtil.isNotEmpty(username)) {
					memberSchema member = new memberSchema();
					member.setemail(email);
					member.setmobileNO(mobileNO);
					member.settelephoneNO(telephoneNO);
					member.setfaxNO(faxNO);
					member.setIDType(IDType);
					member.setIDNO(IDNO);

					member.setQQNO(QQNO);
					member.setusername(username);
					member.setrealName(realName);
					member.setsex(sex);
					member.setbirthday(birthday);
					member.setpoint(point);

					member.setusedPoint(usedpoint);
					member.setcurrentValidatePoint(currentValidatePoint);
					member.setexpiricalValue(expiricalValue);
					member.setisAccountEnabled(isAccountEnabled + "");
					member.setisAccountLocked(isAccountLocked + "");
					
					member.setisEmailBinding(isEmailBinding);
					member.setisMobileNOBinding(isMobileNOBinding);

					password = new Getpassword().getPassword();
					// member.setpassword(password);
					member.setpassword(StringUtil.md5Hex(password));
					long menberID = NoUtil.getMaxID("MemberID");
					member.setid(menberID + "");
					member.setmemberRank_id("40288083382e8a2701382e994f5f000a");
					member.setloginFailureCount(0);
					member.setpoint(0);
					member.setcreateDate(new Date());
					member.setregisterIp("127.0.0.1");
					member.setgrade("K0");
					member.setFromWap("wj");
					member.setvipFlag("N");
					BigDecimal deposit;
					double temp = 0.0000;
					deposit = new BigDecimal(temp);
					member.setdeposit(deposit);

					Map<String, Object> map = new HashMap<String, Object>();
					HttpServletRequest request = null;
					map.put("UserName", member.getusername());
					map.put("PassWord", password);
					map.put("ToMobileNO", member.getmobileNO());
					map.put("ToName", member.getrealName());
					if (member.insert()) {
						ActionUtil.dealAction("wj00028", map,request);//发短信
						cn.com.sinosoft.entity.Member tMember = new cn.com.sinosoft.entity.Member();
						tMember.setEmail(member.getemail());
						map.put("Member", tMember);
						map.put("MemberName", member.getrealName());
						String toEmail = member.getemail();
						ActionUtil.dealAction("wj00027", map,request);
						if(StringUtil.isNotEmpty(toEmail)){
							map.remove("Member");
							ActionUtil.sendMail("wj00027", toEmail, map);//发邮件
						}
						Response.setMessage("导入成功!");
					} else {
						Response.setError("导入失败!");
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				Response.setError("导入失败!");
			}
		}
	}
	
	private boolean checkMobile(String mobileNO) {
		int count = new QueryBuilder(
				"select count(*) from member where mobileNO=?", mobileNO).executeInt();
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}
	private boolean checkName(String UserName) {
		int count = new QueryBuilder(
				"select count(*) from member where UserName=?", UserName).executeInt();
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}
	private boolean checkEmail(String email) {
		int count = new QueryBuilder(
				"select count(*) from member where email=?", email).executeInt();
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	public String getPassword() {
		int index = 0;
		String[] p_array = new String[62];
		/**
		 * 填充数组
		 */
		for (int i = 0; i <= 9; i++) {
			p_array[index++] = i + "";
		}

		for (int i = 'A'; i <= 'Z'; i++) {
			char c = (char) i;
			p_array[index++] = new String(c + "");
		}

		for (int i = 'a'; i <= 'z'; i++) {
			char c = (char) i;
			p_array[index++] = new String(c + "");
		}

		/**
		 * 返回6位的随机密码
		 */
		StringBuffer password = new StringBuffer();

		for (int i = 0; i < 6; i++) {
			int random = (int) (Math.random() * 62);
			password.append(p_array[random]);
		}

		boolean rule = this.rule(password.toString());

		if (rule) {
			return password.toString();
		} else {
			return this.getPassword();
		}
	}

	private boolean rule(String password) {
		char[] p_array = password.toCharArray();

		int rule = p_array[0];
		for (int i = 0; i < 6; i++) {
			if (p_array[i] != rule + i) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {

		ImportFile p = new ImportFile();
		p.getexcel();
	}
}

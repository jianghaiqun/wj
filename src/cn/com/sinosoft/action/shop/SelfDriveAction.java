/**
 * Project Name:wj
 * File Name:SelfDriveAction.java
 * Package Name:cn.com.sinosoft.action.shop
 * Date:2017年3月9日下午9:11:55
 * Copyright (c) 2017, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.action.shop;

import java.util.Date;

import org.apache.struts2.convention.annotation.ParentPackage;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.schema.selfdriveactivitySchema;
import com.tenpay.util.MD5Util;

/**
 * ClassName:SelfDriveAction <br/>
 * Function:TODO 描述类是干什么的. <br/>
 * Date:2017年3月9日 下午9:11:55 <br/>
 *
 * @author:liuhongyu 
 */
@ParentPackage("shop")
public class SelfDriveAction extends BaseShopAction {
	
	/**
	 * visitBackOnLine:发送同意在线回访报文. <br/>
	 *
	 * @author liuhongyu
	 */
	public String saveInsureData() {

		String realName = getParameter("realName");
		String mobileNo = getParameter("mobileNo");
		String email = getParameter("email");
		String idCode = getParameter("idCode");
		
		
		String id = MD5Util.MD5Encode(realName + "-" + mobileNo + DateUtil.getCurrentDate(), "UTF-8");
		
		Transaction transaction = new Transaction();
		
		selfdriveactivitySchema schema = new selfdriveactivitySchema();
		schema.setid(id);
		schema.setrealname(realName);
		schema.setmobileno(mobileNo);
		schema.setemail(email);
		schema.setidcode(idCode);
		schema.setcreatedate(new Date());
		transaction.add(schema, Transaction.INSERT);
		if (transaction.commit()) {
			return ajaxJson("1");
		} else {
			return ajaxJson("2");
		}
	}
}


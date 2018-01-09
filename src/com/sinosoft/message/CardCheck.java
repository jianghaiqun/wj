/**
 * Project Name:wj
 * File Name:cardcheck.java
 * Package Name:com.sinosoft.message
 * Date:2016年12月26日上午9:14:30
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.message;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;

/**
 * ClassName:cardcheck <br/>
 * Function:TODO 描述类是干什么的. <br/>
 * Date:2016年12月26日 上午9:14:30 <br/>
 *
 * @author:liuhongyu
 */

public class CardCheck extends Page {

	public static void dg1DataBind(DataGridAction dga) {

		String accname = dga.getParams().getString("accname");
		String bankaccno = dga.getParams().getString("bankaccno");

		QueryBuilder qb = new QueryBuilder("select s.AccName,s.BankAccNo,z.CodeName from sdcardbind s,zdcode z "
				+ "where s.Bindflag = 'N' and s.SendCheckCount = '3' "
				+ "and z.parentcode = 'LicaiBaoxianBankCode' "
				+ "and s.bankcode = z.codevalue "
				+ "and s.prop2 is NULL ");

		if (StringUtil.isNotEmpty(accname)) {
			qb.append(" and s.AccName like ? ", "%" + accname + "%");
		}
		if (StringUtil.isNotEmpty(bankaccno)) {
			qb.append(" and s.bankaccno =? ", bankaccno);
		}
		DataTable dt = qb.executeDataTable();
		dga.bindData(dt);

	}
}

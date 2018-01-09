/**
 * Project Name:wj
 * File Name:Mark.java
 * Package Name:com.sinosoft.cms.memberreport
 * Date:2016年11月7日上午10:18:02
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.cms.memberreport;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;

/**
 * ClassName:Mark <br/>
 * Function:TODO 选择标记人信息展示. <br/>
 * Date:2016年11月7日 上午10:18:02 <br/>
 *
 * @author:taoqiwen 
 */
public class Mark extends Page{
	
	
    /**
     * 初始化回购查询页面.
     *
     * @param params
     * @return
     */
    public static Mapx init(Mapx params) {


    
        return params;
    }

    
    /**
     * 客服人员查询
     *
     * @param dga
     */
	public static void dg1DataBind(DataGridAction dga) {
		String searchUserName = (String) dga.getParam("SearchUserName");
		QueryBuilder qb = new QueryBuilder(" SELECT a.UserName ,a.RealName ,c.RoleName FROM ZDUser a ,ZDUserRole b,ZDRole c  ");
	  
		qb.append("   WHERE STATUS = 'N' AND a.UserName=b.UserName AND c.RoleCode=b.RoleCode ");
		qb.append("  AND c.`RoleName`='客服岗'");
		qb.append(" order by a.AddTime desc");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("RoleNames");
		dga.setTotal(qb);
		dga.bindData(dt);
	}

}


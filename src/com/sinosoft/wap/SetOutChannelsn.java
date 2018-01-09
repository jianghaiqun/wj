package com.sinosoft.wap;

import java.util.Arrays;
import java.util.List;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;

public class SetOutChannelsn  extends Page{
	public static Mapx init(Mapx params) {
		String channelsn = Config.getValue("OutProductList");
		String[] arr_channel = channelsn.split(",");
		String productId = (String) params.get("productId");
		String sql = " select prop5  from productrelainfo  WHERE productid = "+productId+" ";
		QueryBuilder qb = new QueryBuilder(sql);
		String res = String.valueOf( qb.executeOneValue());
		String[] checkedArray = null;
		if (res != null) {
			checkedArray = res.split(",");
		}
		params.put("chs", res);
		String check = HtmlUtil.arrayToCheckboxes("ch", arr_channel, checkedArray, "chclick()", false);
		params.put("te", check);
		return params;
	}
	
	public void updateOutChannel(){
		String chs = $V("chs");
		String productId = $V("productId");
		String sql = " UPDATE productrelainfo SET prop5 = '"+chs+"' WHERE productid = '"+productId+"' ";
		QueryBuilder qb = new QueryBuilder(sql);
		int row = qb.executeNoQuery();
		if (row > 0) {
			Response.setStatus(1);
			Response.setMessage("对外渠道设置成功!");
		}else{
			Response.setStatus(0);
			Response.setMessage("对外渠道设置失败!");
		}
	}
}

package com.sinosoft.framework.controls;

import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.Current;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.utility.StringUtil;

/**
 * 响应Select.js中的loadData请求
 * 
 */
public class CodeSourcePage extends Ajax {
	public void getData() {
		String codeType = $V("CodeType");
		if (StringUtil.isEmpty($V("ConditionField"))) {
			Request.put("ConditionField", "1");
			Request.put("ConditionValue", "1");
		}
		DataTable dt = null;
		String method = $V("Method");
		if (StringUtil.isEmpty(method) && codeType.startsWith("#")) {
			method = codeType.substring(1);
		}
		if (StringUtil.isNotEmpty(method)) {			
			String className = method.substring(0, method.lastIndexOf("."));
			String methodName = method.substring(method.lastIndexOf(".") + 1);
			try {
				Object o = Current.invokeMethod(method, new Object[] { Request });
				dt = (DataTable) o;
			} catch (Exception e) {
				throw new RuntimeException("确认类" + className + "的方法" + methodName + "返回值是DataTable类型!");
			}
		} else {
			CodeSource cs = SelectTag.getCodeSourceInstance();
			dt = cs.getCodeData(codeType, Request);
		}
		$S("DT", dt);
	}
}
